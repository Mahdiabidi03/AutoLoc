\# Note technique : couche Repository (Atelier 3)



\## 1. Choix de l'interface



Les neuf repositories du package `tn.esprit.AutoLoc.repository` étendent `JpaRepository<Entité, Long>`. 

Ce choix est retenu pour AutoLoc car `JpaRepository` réunit le CRUD, les collections renvoyées en `List`, 

le tri, la pagination et les méthodes propres à JPA (`flush`, `saveAndFlush`, `getReferenceById`).



| Interface | Étend | Justification |

|---|---|---|

| IAgenceRepository | JpaRepository<Agence, Long> | CRUD complet, `findAll` renvoie une `List` ; agences listées et paginées. |

| IEmployeRepository | JpaRepository<Employe, Long> | CRUD complet ; tri possible par nom ou rôle. |

| IVehiculeRepository | JpaRepository<Vehicule, Long> | CRUD complet ; tri et pagination utiles pour le catalogue de véhicules. |

| IEquipementRepository | JpaRepository<Equipement, Long> | CRUD complet ; équipements partagés entre véhicules, lus et réutilisés. |

| IClientRepository | JpaRepository<Client, Long> | CRUD complet ; pagination utile pour la liste des clients. |

| IReservationRepository | JpaRepository<Reservation, Long> | CRUD complet ; `saveAndFlush` disponible, tri par date de début. |

| IContratRepository | JpaRepository<Contrat, Long> | CRUD complet, `findAll` renvoie une `List`, `saveAndFlush` disponible ; la cascade ALL et l'orphanRemoval s'appliquent aux paiements. |

| IPaiementRepository | JpaRepository<Paiement, Long> | Créé pour lire les paiements ; la création et le retrait d'un paiement passent par le Contrat (composition). |

| IMaintenanceRepository | JpaRepository<Maintenance, Long> | CRUD complet ; historique des maintenances d'un véhicule. |



\## 2. Points d'attention



\- `save()` : identifiant `null` = `persist` (INSERT) ; identifiant renseigné = `merge` (SELECT puis UPDATE). 

&#x20; On utilise l'objet retourné.

\- `deleteById()` et `delete()` passent par le contexte de persistance : la cascade REMOVE et l'orphanRemoval 

&#x20; s'appliquent (supprimer un contrat supprime ses paiements).

\- `deleteAllInBatch()` et `deleteAllByIdInBatch()` contournent le contexte de persistance : pas de cascade ni 

&#x20; d'orphanRemoval. Ils ne sont pas utilisés sur `Contrat`, au risque de laisser des paiements orphelins ou de 

&#x20; violer la contrainte de clé étrangère.

\- `findAll()` renvoie une `List` avec `JpaRepository` (et un `Iterable` avec `CrudRepository`).

\- Aucune annotation `@Repository` : Spring Data génère le proxy au démarrage. Au lancement, les logs indiquent 

&#x20; `Found 9 JPA repository interfaces`.



\## 3. Analyse SonarQube for IDE



Analyse de l'ensemble du projet (clic droit sur le projet, \*Analyze with SonarQube for IDE\*), 

sur les entités du package `domain` et les interfaces du package `repository`.



| Anomalie SonarQube for IDE | Règle / explication | Correction apportée |

|---|---|---|

| Aucune anomalie détectée | Rapport vide sur l'ensemble du projet | Aucune correction nécessaire |



Le code respecte les conventions suivies : Lombok ciblé (`@Getter`, `@Setter`, `@NoArgsConstructor`, 

`@AllArgsConstructor`) sans `@Data`, interfaces préfixées par `I`, pas de code mort.

