# Data Simulator

This is a part of a microstream project.

## Purpose 
This microservice serves to generate fake data and push them into a message broker.

### TODO:
- [ ] Create stateless data-simulator
- [ ] Create simple Kafka Cluster with 3+- brokers (Use Helm Charts)
  - [ ] Setup topics
- [ ] Create stateful data-aggregator to consume messages from kafka.
  - [ ] Create Stream 
  - [ ] Create KTable (Create calculation of how price changes {current price})
  - [ ] Create mongo-db
  - [ ] Create socket communication
- [ ] Create basic FE app with stock charts 
- [ ] Deploy everything in Kubernetes cluster on AWS
- [ ] Create simple deploy jobs with SNAPSHOT versioning  

### Stage2:
- [ ] Make data-simulator stateful. Spring Quartz vs Redis + Kafka?
  - Save latest stock prices to Kafka topics.
  - Retrieve latest stock prices from Kafka topics after start-up.
  - Use Spring Quartz and database to store the jobs dedicated to particular instances?
- [ ] Add tools to monitor Kafka Cluster to scale up consumers. - https://www.youtube.com/watch?v=hsJ2qtwoWZw&list=WL

```bash
mvnw clean package
java -jar target/data-simulator-0.0.1-SNAPSHOT.jar
docker build -t data-simulator:latest .
docker run --rm -p 8080:8080 data-simulator:latest
kubectl apply -f .
```

