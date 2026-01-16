# Projet SOA - Gestion Cabinet Médical

## 1. Module Patient

### 1.1 Partie Service (PatientService)
Dans le service `PatientService`, j’ai ajouté deux fonctionnalités principales :

- **Récupérer un patient par son identifiant (`getPatient`)**  
  - Recherche un patient dans la base de données grâce à son ID.  
  - Retourne toutes ses informations si le patient existe.  
  - Lève une exception si le patient est introuvable.  
  - Utilise la méthode `findById()` du repository avec `Optional` pour simplifier le traitement.  

- **Modifier un patient existant (`update`)**  
  - Récupère le patient existant avec `getPatient(id)` pour s’assurer qu’il existe.  
  - Met à jour les champs : nom, prénom, téléphone et date de naissance.  
  - Sauvegarde le patient modifié avec `save()`.  
  - Lombok génère automatiquement les getters et setters.  

Ces ajouts permettent de gérer toutes les opérations CRUD sur les patients.

### 1.2 Partie Controller (PatientController)
- **GET `/internal/api/v1/patients/{id}`** : récupère les informations d’un patient spécifique.  
- **PUT `/internal/api/v1/patients/{id}`** : modifie un patient existant.  

Ces endpoints respectent l’architecture SOA : le controller sert d’interface pour accéder aux données, tandis que le service contient la logique métier.

---

## 2. Module Médecin

### 2.1 Partie Service (MedecinService)
Fonctionnalités principales :

- **Récupérer un médecin par son identifiant (`getMedecin`)**  
  - Recherche un médecin dans la base grâce à son ID.  
  - Retourne ses informations si le médecin existe.  
  - Lève une exception si le médecin est introuvable.  

- **Modifier un médecin existant (`update`)**  
  - Récupère le médecin existant avec `getMedecin(id)`.  
  - Met à jour : nom, spécialité, email et téléphone.  
  - Sauvegarde le médecin modifié.  
  - Lombok simplifie le code via la génération automatique des getters et setters.

### 2.2 Partie Controller (MedecinController)
- **GET `/internal/api/v1/medecins/{id}`** : récupère un médecin spécifique.  
- **PUT `/internal/api/v1/medecins/{id}`** : modifie un médecin existant.

---

## 3. Module RendezVous

### 3.1 Partie Service (RendezVousService)
Au départ, le service ne contenait que `create` et `list`. J’ai ajouté les fonctionnalités suivantes :  

- **Récupération d’un rendez-vous par ID (`getRendezVous`)**  
- **Modification d’un rendez-vous (`update`)**  
  - La date doit être future.  
  - Le statut doit être `PLANIFIE`, `ANNULE` ou `TERMINE`.  
- **Modification partielle du statut (`updateStatut`)**  
- **Filtrage des rendez-vous par patient et par médecin**  
  - `listByPatient(idPatient)`  
  - `listByMedecin(idMedecin)`  
- **Suppression d’un rendez-vous (`delete`)**  
- Statut initial par défaut : `PLANIFIE` lors de la création.

### 3.2 Partie Controller (RendezVousController)
- **GET `/internal/api/v1/rendezvous/{id}`** : récupère un rendez-vous par ID.  
- **PUT `/internal/api/v1/rendezvous/{id}`** : modifie un rendez-vous.  
- **PATCH `/internal/api/v1/rendezvous/{id}/statut`** : modifie uniquement le statut.  
- **GET `/internal/api/v1/rendezvous/patient/{id}`** : liste des rendez-vous d’un patient.  
- **GET `/internal/api/v1/rendezvous/medecin/{id}`** : liste des rendez-vous d’un médecin.  
- **DELETE `/internal/api/v1/rendezvous/{id}`** : supprime un rendez-vous.  

J’ai également ajouté les **DTO (`RdvUpdateRequest`)** pour séparer les données échangées via l’API des entités internes.

### 3.3 Partie Repository (RendezVousRepository)
- `List<RendezVous> findByPatientId(Long idPatient)`  
- `List<RendezVous> findByMedecinId(Long idMedecin)`  

Spring Data JPA génère automatiquement les requêtes correspondantes.

---

## 4. Module Consultation

### 4.1 Partie Service (ConsultationService)
Fonctionnalités principales :

- **Créer une consultation**  
  - Vérifie que le rendez-vous existe.  
  - Vérifie que la date de consultation est renseignée et postérieure ou égale à la date du rendez-vous.  
  - Vérifie que le rapport contient au moins 10 caractères.  

- **Récupérer une consultation**  
  - Par ID ou par rendez-vous.  
  - Lève une exception si elle n’existe pas.  

- **Modifier une consultation**  
  - Met à jour la date et le rapport avec les mêmes validations.  

- **Supprimer une consultation**  
  - Supprime la consultation existante si elle existe.  

- **Lister toutes les consultations**  
- **Filtrage par rendez-vous**  
  - `findByRendezVousId(idRendezVous)`

### 4.2 Partie Controller (ConsultationController)
- **GET `/internal/api/v1/consultations`** : liste toutes les consultations.  
- **GET `/internal/api/v1/consultations/{id}`** : récupère une consultation spécifique.  
- **GET `/internal/api/v1/consultations/rendezvous/{id}`** : liste les consultations d’un rendez-vous.  
- **POST `/internal/api/v1/consultations`** : crée une consultation.  
- **PUT `/internal/api/v1/consultations/{id}`** : modifie une consultation.  
- **DELETE `/internal/api/v1/consultations/{id}`** : supprime une consultation.  

J’ai également utilisé des **DTO (`ConsultationRequest`)** pour séparer les données échangées via l’API des entités internes.

### 4.3 Partie Repository (ConsultationRepository)
- `List<Consultation> findByRendezVousId(Long idRendezVous)`  
Spring Data JPA génère automatiquement les requêtes.

### 4.4 Ajouts pour Spring Boot
```java
@SpringBootApplication(scanBasePackages = "ma.fsr.soa")
@EnableJpaRepositories(basePackages = "ma.fsr.soa.cabinetrepo.repository")
@EntityScan(basePackages = "ma.fsr.soa.cabinetrepo.model")



