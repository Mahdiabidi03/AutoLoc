# Note technique : stratégies de fetch et de cascade (Atelier 2)

## 1. Principes retenus

- **Fetch** : toutes les associations sont en `LAZY`. Par défaut, `@ManyToOne` et `@OneToOne` sont en `EAGER`,
  ce qui charge toute la chaîne d'objets liés à chaque lecture ; on force donc `LAZY`. `@OneToMany` et `@ManyToMany`
  sont `LAZY` par défaut et le restent : une collection ne se charge que si on l'utilise.
- **Cascade** : une cascade n'est justifiée que si l'enfant ne peut pas vivre sans son parent (composition).
  Dans les autres cas, on évite `REMOVE` pour ne pas supprimer des données qui survivent à leur parent.
- **Côté propriétaire** : c'est le côté `@ManyToOne` (ou `@OneToOne` désigné) qui porte la clé étrangère ;
  le côté inverse utilise `mappedBy` et ne crée aucune colonne.
- **Lombok** : `@Getter`/`@Setter` ciblés, jamais `@Data`, pour éviter le `StackOverflowError`
  dû au `toString`/`hashCode` qui parcourent les deux côtés d'une association bidirectionnelle.

## 2. Tableau des associations

| Association | Côté propriétaire | Fetch | Cascade | Justification |
|---|---|---|---|---|
| Contrat → Paiement | Paiement (`contrat`) | LAZY | ALL + orphanRemoval | Un paiement n'existe que rattaché à son contrat (composition) ; supprimer le contrat supprime ses paiements, et retirer un paiement de la liste le supprime en base. |
| Agence → Vehicule | Vehicule (`agence`) | LAZY | Aucune | Un véhicule survit à la suppression de son agence ; aucune cascade de suppression ne doit être appliquée. |
| Agence → Employe | Employe (`agence`) | LAZY | Aucune | Un employé a une existence propre ; supprimer une agence ne doit pas effacer ses employés. |
| Vehicule ↔ Equipement | Vehicule (`@JoinTable vehicule_equipement`) | LAZY | Aucune | Les équipements sont partagés entre plusieurs véhicules : en supprimer un véhicule ne doit pas les supprimer. Un `Set` évite les doublons. |
| Client → Reservation | Reservation (`client`) | LAZY | PERSIST | Enregistrer un client avec de nouvelles réservations les enregistre aussi ; `REMOVE` est exclu pour conserver l'historique des réservations. |
| Reservation → Vehicule | Reservation (`vehicule`) | LAZY | Aucune | Le véhicule existe indépendamment de ses réservations ; relation unidirectionnelle. |
| Reservation ↔ Contrat | Contrat (`reservation`) | LAZY | ALL côté Reservation | Un contrat n'a de sens que rattaché à sa réservation : le cycle de vie du contrat suit celui de la réservation. |
| Vehicule → Maintenance | Maintenance (`vehicule`) | LAZY | PERSIST | Les maintenances créées avec un véhicule sont enregistrées avec lui ; elles sont gérées séparément ensuite. |

## 3. Remarques

- Colonnes de clé étrangère générées par Hibernate (convention par défaut) : `contrat_id_contrat`,
  `agence_id_agence`, `client_id_client`, `vehicule_id_vehicule`, `reservation_id_reservation`.
- Table de jointure `vehicule_equipement` (colonnes `id_vehicule` et `id_equipement`) pour la relation `@ManyToMany`.
- Avec `LAZY`, un accès à une association hors transaction peut lever une `LazyInitializationException` ;
  elle sera traitée par une requête dédiée dans un atelier ultérieur.
- Côté inverse d'un `@OneToOne`, Hibernate ne peut pas toujours charger paresseusement sans instrumentation du
  bytecode : on garde `LAZY` pour rester cohérent avec la recommandation, en sachant que le chargement effectif
  peut être immédiat.