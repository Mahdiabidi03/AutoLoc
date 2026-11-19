# Note technique : API REST, tests Postman et documentation Swagger (Atelier 5)

## A. Endpoints

| Contrôleur | Verbe et route | Service appelé | Succès | Erreurs |
|---|---|---|---|---|
| ContratController | GET /api/contrats | findAll() | 200 | — |
| ContratController | GET /api/contrats/{id} | findById(id) | 200 | 404 |
| ContratController | POST /api/contrats | create(contrat) | 201 | 400 |
| ContratController | PUT /api/contrats/{id} | update(id, contrat) | 200 | 404, 400 |
| ContratController | DELETE /api/contrats/{id} | deleteById(id) | 204 | 404 |
| PaiementController | GET /api/paiements | findAll() | 200 | — |
| PaiementController | GET /api/paiements/{id} | findById(id) | 200 | 404 |
| AgenceController | GET /api/agences | findAll() | 200 | — |
| AgenceController | GET /api/agences/{id} | findById(id) | 200 | 404 |
| AgenceController | POST /api/agences | create(agence) | 201 | 400 |
| AgenceController | PUT /api/agences/{id} | update(id, agence) | 200 | 404, 400 |
| AgenceController | DELETE /api/agences/{id} | deleteById(id) | 204 | 404 |
| EmployeController | GET /api/employes | findAll() | 200 | — |
| EmployeController | GET /api/employes/{id} | findById(id) | 200 | 404 |
| EmployeController | POST /api/employes | create(employe) | 201 | 400 |
| EmployeController | PUT /api/employes/{id} | update(id, employe) | 200 | 404, 400 |
| EmployeController | DELETE /api/employes/{id} | deleteById(id) | 204 | 404 |
| VehiculeController | GET /api/vehicules | findAll() | 200 | — |
| VehiculeController | GET /api/vehicules/{id} | findById(id) | 200 | 404 |
| VehiculeController | POST /api/vehicules | create(vehicule) | 201 | 400 |
| VehiculeController | PUT /api/vehicules/{id} | update(id, vehicule) | 200 | 404, 400 |
| VehiculeController | PUT /api/vehicules/{idVehicule}/agence/{idAgence} | affecterAAgence(idVehicule, idAgence) | 200 | 404 |
| VehiculeController | DELETE /api/vehicules/{id} | deleteById(id) | 204 | 404 |
| EquipementController | GET /api/equipements | findAll() | 200 | — |
| EquipementController | GET /api/equipements/{id} | findById(id) | 200 | 404 |
| EquipementController | POST /api/equipements | create(equipement) | 201 | 400 |
| EquipementController | PUT /api/equipements/{id} | update(id, equipement) | 200 | 404, 400 |
| EquipementController | DELETE /api/equipements/{id} | deleteById(id) | 204 | 404 |
| ClientController | GET /api/clients | findAll() | 200 | — |
| ClientController | GET /api/clients/{id} | findById(id) | 200 | 404 |
| ClientController | POST /api/clients | create(client) | 201 | 400 |
| ClientController | PUT /api/clients/{id} | update(id, client) | 200 | 404, 400 |
| ClientController | DELETE /api/clients/{id} | deleteById(id) | 204 | 404 |
| ReservationController | GET /api/reservations | findAll() | 200 | — |
| ReservationController | GET /api/reservations/{id} | findById(id) | 200 | 404 |
| ReservationController | POST /api/reservations | create(reservation) | 201 | 400 |
| ReservationController | PUT /api/reservations/{id} | update(id, reservation) | 200 | 404, 400 |
| ReservationController | DELETE /api/reservations/{id} | deleteById(id) | 204 | 404 |
| MaintenanceController | GET /api/maintenances | findAll() | 200 | — |
| MaintenanceController | GET /api/maintenances/{id} | findById(id) | 200 | 404 |
| MaintenanceController | POST /api/maintenances | create(maintenance) | 201 | 400 |
| MaintenanceController | PUT /api/maintenances/{id} | update(id, maintenance) | 200 | 404, 400 |
| MaintenanceController | DELETE /api/maintenances/{id} | deleteById(id) | 204 | 404 |

Le préfixe `/api` est défini une seule fois par `server.servlet.context-path=/api`. Les 404 viennent de `ResourceNotFoundException` et les 400 de `IllegalArgumentException`, traduites par le `GlobalExceptionHandler`. Un POST sur `/api/paiements` renvoie 405 (verbe non pris en charge).

## B. Annotations Jackson

| Association | Annotation retenue | Justification |
|---|---|---|
| Contrat → Paiement (`Contrat.paiements` / `Paiement.contrat`) | `@JsonManagedReference("contrat-paiements")` / `@JsonBackReference("contrat-paiements")` | Vraie composition : les paiements se créent dans le JSON de leur contrat (cascade ALL). La liste est sérialisée, la référence arrière est omise et reconstituée à la désérialisation. |
| Agence → Vehicule, Agence → Employe | `@JsonIgnore` sur `Agence.vehicules` et `Agence.employes` | L'enfant est créé seul et désigne son agence par identifiant (`"agence": {"idAgence": 1}`). |
| Client → Reservation | `@JsonIgnore` sur `Client.reservations` | La réservation est créée seule et désigne son client par identifiant. |
| Vehicule → Maintenance | `@JsonIgnore` sur `Vehicule.maintenances` | La maintenance désigne son véhicule par identifiant. |
| Vehicule ↔ Equipement | `@JsonIgnore` sur `Equipement.vehicules` (côté inverse) | Évite la boucle ; `Vehicule.equipements` reste sérialisé. |
| Reservation ↔ Contrat | `@JsonIgnore` sur `Reservation.contrat` (côté inverse) | Évite la boucle ; `Contrat.reservation` reste le côté propriétaire. |

**Différence :** `@JsonBackReference` est la moitié « retour » d'une paire avec `@JsonManagedReference` : la propriété n'est pas sérialisée mais Jackson la renseigne automatiquement à la désérialisation (le paiement retrouve son contrat). `@JsonIgnore` ignore simplement la propriété dans les deux sens, sans rien reconstituer : l'objet lié doit donc être désigné autrement (par son identifiant). `@JsonBackReference` n'est pas utilisé sur `Reservation.client` : la propriété serait ignorée à la lecture et le POST créerait une réservation sans client.
## C. Questions de compréhension

1. **Quel composant reçoit toutes les requêtes et comment trouve-t-il la méthode ?** Le `DispatcherServlet`. Il interroge le `HandlerMapping`, qui compare le verbe HTTP et l'URL (préfixe `/api` compris) aux annotations `@RequestMapping`, `@GetMapping`, etc., et retrouve ainsi le contrôleur et la méthode à appeler.
2. **Pourquoi `ContratController` dépend-il de `IContratService` ?** Le contrôleur reste mince : il délègue et ne contient aucune logique métier. Il ne touche jamais au repository (c'est le rôle du service) et dépend de l'interface plutôt que de `ContratServiceImpl` pour que l'implémentation puisse changer sans modifier le contrôleur.
3. **Pourquoi 201, 204, 404 et non 500 ?** 201 : une ressource a été créée. 204 : succès sans corps à renvoyer. Une ressource absente est une erreur du client (identifiant inexistant), pas une panne du serveur : `ResourceNotFoundException` est traduite en 404 par le `GlobalExceptionHandler`, alors qu'une exception non gérée donnerait un 500.
4. **Pourquoi `PaiementController` n'expose-t-il que des GET ?** Un paiement est une composition du contrat : il se crée, se modifie et se supprime via le contrat (cascade ALL et orphanRemoval). Un `POST /api/paiements` reçoit un 405 Method Not Allowed : la route existe mais pas ce verbe.
5. **Risques de l'exposition directe des entités et choix des annotations.** Couplage entre l'API et la base, champs internes exposés, boucles de sérialisation, problèmes de chargement paresseux, et le client peut renseigner des champs qu'il ne devrait pas contrôler (identifiants, associations). `Contrat.paiements` utilise `@JsonManagedReference` car les paiements se créent dans le JSON de leur contrat et doivent être sérialisés avec lui ; `Agence.vehicules` utilise `@JsonIgnore` car les véhicules se créent seuls et désignent leur agence par identifiant, la liste n'a donc pas à circuler.