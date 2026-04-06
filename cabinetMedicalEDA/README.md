# TP4 – Architecture Event-Driven (EDA) avec Kafka
## Gestion d’un Cabinet Médical

Cours assuré par : Jaouad OUHSSAINE  
Master IPS — Module : Systèmes Distribués Basés sur les Microservices  
Contact : jaouad.ouhs@gmail.com | jaouad_ouhssaine@um5.ac.ma

---

## Contexte

Dans le TP3, les microservices communiquaient via des appels REST synchrones.

### Limites observées

- Couplage fort entre services
- Dépendance à la disponibilité des autres services
- Risque d’erreurs en cascade
- Scalabilité limitée
- Temps de réponse dépendant des autres services

---

## Objectif du TP4

Faire évoluer l’architecture vers une :

Architecture Event-Driven (EDA) basée sur Apache Kafka

### Principes introduits

- Communication asynchrone
- Découplage fort entre services
- Modèle Publish / Subscribe
- Consistance éventuelle
- Saga distribuée par chorégraphie

---

## Architecture globale

L’architecture repose désormais sur :

- Un API Gateway (exposition REST externe uniquement)
- Un Kafka Event Bus
- Des microservices autonomes
- Une base de données par microservice
- Une communication exclusivement événementielle entre services

Les services ne s’appellent plus via REST.  
Ils communiquent via des événements Kafka.

---

## Structure du projet

```text
cabinetMedicalTp4EDA/
│
├── docker-compose.yml           # Infrastructure Kafka (Zookeeper + Kafka)
│
├── api-gateway                  # Exposition REST externe uniquement
│
├── patient-service              # Event Producer
├── medecin-service              # Event Producer
├── rendezvous-service           # Event Producer & Consumer
├── consultation-service         # Event Consumer & Producer
├── billing-service              # Event Consumer & Producer
│
└── pom.xml                      # Projet parent (packaging pom)
```

## Infrastructure Kafka

L’infrastructure Kafka est déployée via Docker Compose.

Elle comprend :

- Un broker Kafka
- (Optionnel) Zookeeper selon la configuration choisie
- Kafka UI
- AKHQ

---

### Ports exposés

- Kafka interne Docker : `kafka:9092`
- Kafka accès machine locale : `localhost:29092`
- Kafka UI : http://localhost:8088/
- AKHQ : http://localhost:8087/ui/local/node

---

### Configuration des microservices

#### Depuis les microservices (dans Docker)

```properties
spring.kafka.bootstrap-servers=kafka:9092
```
# Kafka Cheat Sheet - Spring Boot Project

## Configuration de l'application
Depuis la machine locale (hors Docker) :
`spring.kafka.bootstrap-servers=localhost:29092`

---

## Gestion de l'infrastructure Docker
* **Démarrage de l’infrastructure :** `docker compose up -d`
* **Vérification des conteneurs :** `docker ps`
* **Logs Kafka :** `docker logs kafka`
* **Arrêt de l’infrastructure :** `docker compose down`

---

## Commandes Kafka utiles

### Accéder au conteneur Kafka
```bash
docker exec -it kafka bash
```
### Lister les topics
```bash
kafka-topics --bootstrap-server localhost:9092 --list
```
### Créer un topic
```bash
kafka-topics --create \
  --topic patient.created \
  --bootstrap-server localhost:9092 \
  --replication-factor 1 \
  --partitions 1
```
### Consommer un topic
```bash
kafka-console-consumer \
  --bootstrap-server localhost:9092 \
  --topic patient.created \
  --from-beginning
```