# event-driven-architecture-cqrs
Demonstrated Event Driven Architecture! Implemented CQRS &amp; Event Sourcing with Apache Kafka.


## CQRS:
- It is software design pattern that stands for "Command Query Responsibility Segregation". It suggest 
  application should be divided into command and query part
- "Command" -> Alter the state an of object or entity
- "Queries" -> Retrieves the state of an object or entity
- Why?
  - Data is often more frequently queried than altered, or visa versa.
  - Segregating commands and queries enables us to optimize each for **high performance**.
  - Read and write "representation of data often differ" substantially.
  - Execution command and query operation on the same model can cause **data contention**.
  - Segregating read and write concerns enables you to manage read and write **security** separately. 


## EventSourcing: 
It defines an approach where all the changes that are made to an object or entity are stored as sequence of immutable events to an event store, as oppose to saving the current state of an object or entity.
- Benefits:
  - The event store provides **complete log of every state change**.
  - The state of an object/aggregate **can be recreated by** replaying the event store.
  - **Improve write performance.** All event types are simply appended to the event store. There are no update or delete operations.
  - In the case of failure, the event store can be used to **restore read database**.

## Apache Kafka:
- Distributed event streaming platform created by LinkedIn 2011, later donated to Apache foundation.
- Enables the development of real-time, event driven applications.
- Process trillions of records per day without any noticeable performance leg.

### Command:
- It is a combination of expressed intent, in other word it describes something that you want to be done.
- It also contain the information required to undertake action based on that intent.
- Commands are named with a verb in the imperative mood, ex: OpenAccountCommand, DepositFund


### Events:
- Events are objects that describe something that has occurred in the application.
- Typical source of events is the aggregate. When something important has occurred within the
   aggregate, it will raise an event.
- Events are named with a verb in the Past Participle ex: AccountOpenedEvent, FundDeposited



### Aggregate:
- It is an entity or group of entities that is always kept in a consistent state. 
- The aggregate Root is the entity within the aggregate that is responsible for maintaining this consistent state.
- This makes the aggregate the primary building block for implementing a command model in any CQRS based application.


#
# Docker Commands
#

## Docker commands to run the require softwares including Apache Kafka, Zookeeper, Databases, DB Client etc..

Note: Run docker command using sudo -s (as admin in iOS)

### 1. Create docker network for the application
  docker network create --attachable -d bridge tech-bank-network

### 2. Run docker compose - For Kafka and Zookeper
  
  Using docker compose file: under dockerfiles folder:
    download bitnami by VMWare image for zookeeper
    download bitnami by VMWare image for kafka

  docker-compose up -d

### 3. Run docker command for MongoDB
  docker run -it -d --name mongodb-container -p 27017:27017 --network tech-bank-network --restart always -v mongodb_data_container:/data/db mongo:latest

  ROBO 3T Mongo DB client:
  Download ROBO Studio 3T for mongo db connection

  <<DoNotUseBelow mongoDB client - Work in progress>>
  run CLI - docker exec -it mongodb-container mongo
    > cls for clear 
    sudo -s docker run -d --name mongo-client -p 3000:3000 --network tech-bank-network mongoclient/mongoclient


### 4. Run docker command for MySQL

  docker run -it -d --name mysqldb-container -p 3306:3306 --network tech-bank-network -e MYSQL_ROOT_PASSWORD=msqlAdmin1! --restart always -v mysql_data_container:/var/lib/mysql mysql:latest   


### 5. Run docker command for Adminer (MySQL db client)
  docker run -it -d --name adminer-container -p 9080:8080 --network tech-bank-network -e ADMINER_DEFAULT_SERVER=mysqldb-container --restart always adminer:latest
  
 
  
#
# Spring Initializer and Project creation with required packages
#
  

### 6. Create spring boot project for Command API
	include following dependency - Spring web, Spring for Apache Kafka, Spring Mongo DB, Lombok

#### 7. Create spring boot project for Query API:
	include following dependency - Spring web, Spring for Apache Kafka, Lombok, Spring Data JPA, Spring MySQL Driver

### 8. Create spring boot project for Common API, Shared project:
	include following dependency - Lombok

### 9. Create spring boot project for cqrs core:
	include following dependency - Spring Data Mongo DB, Lombok
