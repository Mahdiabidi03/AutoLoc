# Note technique : couche Service, IoC et injection de dépendances (Atelier 4)

## A. Modes d'injection comparés

| Critère | Constructeur (`@RequiredArgsConstructor`, champ `final`) | Attribut (`@Autowired` sur le champ) |
|---|---|---|
| Champ `final` possible | Oui | Non |
| Dépendance visible dans la signature de la classe | Oui (paramètre du constructeur) | Non (dépendance cachée) |
| Classe utilisable avec `new` hors conteneur sans dépendance `null` | Oui : le constructeur impose de fournir le repository | Non : le champ reste `null` (NullPointerException) |
| Signalé par SonarQube for IDE | Non | Oui : règle java:S6813 |

Constat : l'application démarre dans les deux cas, mais seule l'injection par constructeur garantit un objet toujours complet. C'est la convention retenue pour tous les services AutoLoc.

## B. Services

| Service | Dépendances injectées | Mode | Justification |
|---|---|---|---|
| ContratServiceImpl | IContratRepository | Constructeur | CRUD complet ; refuse un montant négatif ; la suppression d'un contrat supprime ses paiements par cascade. |
| AgenceServiceImpl | IAgenceRepository | Constructeur | CRUD complet ; refuse un nom d'agence vide. |
| EmployeServiceImpl | IEmployeRepository | Constructeur | CRUD complet ; refuse un employé sans nom ou prénom. |
| VehiculeServiceImpl | IVehiculeRepository | Constructeur | CRUD complet ; refuse un tarif journalier négatif. |
| EquipementServiceImpl | IEquipementRepository | Constructeur | CRUD complet ; refuse un équipement sans libellé. |
| ClientServiceImpl | IClientRepository | Constructeur | CRUD complet ; refuse un client sans nom ou e-mail. |
| ReservationServiceImpl | IReservationRepository | Constructeur | CRUD complet ; refuse une date de fin antérieure à la date de début. |
| PaiementServiceImpl | IPaiementRepository | Constructeur | Lecture seule (findById, findAll) : la création et le retrait d'un paiement passent par le Contrat (composition). |
| MaintenanceServiceImpl | IMaintenanceRepository | Constructeur | CRUD complet ; refuse une date de fin antérieure à la date de début. |

Tous les services sont des singletons sans état, annotés `@Service`, avec des champs `final` typés par interface.
Chaque `update` charge l'entité existante puis recopie les attributs modifiables ; chaque `create` refuse un identifiant non null ; chaque `deleteById` vérifie l'existence.

## C. Messages d'erreur du conteneur

**Message 1** : `NoSuchBeanDefinitionException` (enveloppée dans une `UnsatisfiedDependencyException`). Le bean concerné est `IContratService`, demandé par `ContratController`. Causes probables côté `ContratServiceImpl` : (1) l'annotation `@Service` est oubliée ; (2) la classe est placée en dehors du package racine `tn.esprit.AutoLoc`, donc jamais détectée par le scan. Correction : ajouter `@Service` ou déplacer la classe sous le package racine.

**Message 2** : `NoUniqueBeanDefinitionException`. Deux beans du même type (`emailNotificateur`, `smsNotificateur`) sont candidats pour le paramètre du constructeur de `NotificationController`. Correction 1 : `@Primary` sur la classe du bean à utiliser par défaut. Correction 2 : `@Qualifier("emailNotificateur")` sur le paramètre du constructeur du contrôleur (choix explicite à l'injection).

**Message 3** : `BeanCurrentlyInCreationException` (dépendance circulaire entre `clientServiceImpl` et `reservationServiceImpl`). Avec l'injection par constructeur, chaque bean doit être entièrement construit avant d'être fourni à l'autre : aucun des deux ne peut être créé en premier, le cycle est donc impossible à contourner. Piste de conception : extraire la responsabilité commune dans un troisième service dont les deux dépendent, ou faire passer l'un des deux par le repository plutôt que par l'autre service.

## D. Anomalies SonarQube for IDE

| Anomalie | Règle / explication | Correction apportée |
|---|---|---|
| Injection par attribut (`@Autowired` sur le champ) dans `ContratServiceImpl` | java:S6813 : l'injection par attribut est déconseillée (champ non `final`, dépendance cachée, objet possiblement invalide hors conteneur) | Retour à l'injection par constructeur (`@RequiredArgsConstructor`, champ `final`) |
| Import inutilisé `lombok.RequiredArgsConstructor` après le passage à l'injection par attribut | java:S1128 : les imports inutiles doivent être supprimés (code mort) | Import de nouveau utilisé après la restauration de l'injection par constructeur |
| `RuntimeException` levée dans `ContratServiceImpl.findById` (expérience) | java:S112 : il ne faut pas lever d'exception générique ; elle ne dit rien du problème métier | Retour à `ResourceNotFoundException`, exception métier explicite |

Après correction, une nouvelle analyse de l'ensemble du code ne signale plus aucune anomalie.

## E. Questions de compréhension

1. **Où est le `new` de `ContratServiceImpl` et qui l'exécute ?** Il n'est pas dans le code : c'est le conteneur Spring qui l'exécute au démarrage, après avoir détecté `@Service` lors du scan, et qui fournit le proxy de `IContratRepository` au constructeur.
2. **Pourquoi un contrôleur dépendra-t-il de `IContratService` et non de `ContratServiceImpl` ?** Pour dépendre d'une abstraction : on peut changer ou remplacer l'implémentation (autre version, double de test) sans toucher au contrôleur, et le couplage reste faible.
3. **Pourquoi un service singleton doit-il rester sans état ?** Une seule instance est partagée par tous les threads. Exemple de bug : un attribut `contratCourant` modifié par une requête est écrasé par une requête concurrente, qui modifie alors le mauvais contrat.
4. **Pourquoi `update` charge-t-il l'entité existante avant `save` ?** Passer directement l'objet reçu à `save` écraserait toutes les colonnes (y compris celles non transmises) et, avec `orphanRemoval`, pourrait vider la collection de paiements. On charge, on recopie les attributs modifiables, puis on enregistre.
5. **Différences.** `@Component` détecte une classe par le scan alors que `@Bean` déclare explicitement un bean via une méthode (classe de bibliothèque ou construction particulière). `@Primary` désigne un candidat par défaut, posé sur la classe ; `@Qualifier` choisit un bean précis, posé à l'injection. `IPaiementService` n'expose ni création, ni modification, ni suppression car un paiement est une composition du contrat : ces opérations passent par le `Contrat` (cascade et orphanRemoval).