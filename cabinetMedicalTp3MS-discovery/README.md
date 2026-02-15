# Consultation Service

## C'est quoi ?

Un service pour enregistrer les consultations médicales après un rendez-vous. Le médecin note ce qui s'est passé pendant la consultation : symptômes, diagnostic, traitement, etc.

## Les règles importantes

Avant d'enregistrer une consultation, le service vérifie 4 choses :

**1. Le rendez-vous doit exister**  
On ne peut pas créer une consultation pour un rendez-vous qui n'existe pas.  
*Erreur : "Rendez-vous introuvable."*

**2. La date est obligatoire**  
Il faut préciser quand la consultation a eu lieu.  
*Erreur : "La date de consultation est obligatoire."*

**3. La consultation se passe après le rendez-vous**  
On ne peut pas faire une consultation avant que le patient soit venu.  
*Erreur : "Date de consultation invalide."*

**4. Le rapport doit être complet**  
Minimum 10 caractères. "OK" ne suffit pas.  
*Erreur : "Rapport de consultation insuffisant."*

## Configuration

Le service tourne sur le **port 8085** et s'appelle `consultation-service`.

Base de données H2 en mémoire : `consultationDB`  
Console H2 disponible sur : `http://localhost:8085/h2-console`  
Enregistrement Eureka : `http://localhost:8761/eureka`

## Actions disponibles

Tous les appels commencent par `/internal/api/v1/consultations`

**Créer une consultation** - POST /  
**Lister toutes les consultations** - GET /  
**Voir une consultation** - GET /{id}  
**Consultations d'un rendez-vous** - GET /rendezvous/{id}  
**Modifier une consultation** - PUT /{id}  
**Supprimer une consultation** - DELETE /{id}

## Comment ça communique ?

Le service parle avec le **service rendez-vous** pour :
- Vérifier que le rendez-vous existe
- Récupérer la date du rendez-vous

Toute la communication passe par l'**API Gateway**. Le service utilise **RestTemplate avec LoadBalancing** pour trouver automatiquement les autres services via Eureka.

## Structure du code

**client/** - Communication avec les autres services  
**exception/** - Gestion des erreurs  
**model/** - Entité Consultation  
**repository/** - Sauvegarde en base de données  
**service/** - Logique métier et validation des règles  
**web/** - API REST avec les 6 endpoints

## Démarrage

Ordre important :

1. Eureka Server (port 8761)
2. API Gateway (port 8080)
3. Patient Service (port 8082)
4. Medecin Service (port 8083)
5. Rendez-vous Service (port 8084)
6. **Consultation Service (port 8085)**


## En résumé

Un service qui vérifie les 4 règles métier avant de sauvegarder une consultation. Il communique avec le service rendez-vous via l'API Gateway et s'enregistre automatiquement dans Eureka.