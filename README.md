# WIP: Applied use of the Criteria API

Student: **Kevin Schmid**  
Supervisor: **FH-Prof. Dipl.-Ing. Dr. Egon Teiniker**

## Exposé

The Criteria API was declared as a standard in JPA 2.0. It's an alternative way to define JPQL queries. Compared to JPQL, the Criteria API takes advantage of the type-safe nature of Java. With the release of JPA 2.1, bulk updates and deletes were added and JPA itself gained Java 8 support with the JPA 2.2 release in 2017.
The goal of this project work is to answer the question, if the Criteria API can be used exclusively in a project. For that, a working prototype will be implemented that considers several use cases and compares solutions written with SQL, JPQL and with the use of the Criteria API. The comparison needs to take memory usage, database load and timing into account. The use cases need to be defined in such a way, that a great amount of requirements are covered such as: 
- Simple operations like select, update and delete from single tables
- Queries joining multiple tables and filtering it's records
- Usage of subqueries
- Conversion between data types

To get meaningful comparable values, different JPA provider and database management systems in their latest versions will be used.

## JPA providers
- OpenJPA
- Hibernate
- EclipseLink

## Database Management Systems
- Oracle Database
- Postgres
- MariaDB

Besides functionality, adequate use of Software Design principles and patterns will be part of this project work. Separation of concerns, restriction of visibility and use of data access objects to name a few. As an example, a data access object should provide the functionality to execute a simple select query by primary key. The result should not be the entity itself (as it provides public setter methods) but rather an interface with restricted visibility. 


### Reference:
Keith, Mike, Merrick Schincariol, Massimo Nardone (2018). Pro JPA 2 in Java EE 8: An In-Depth Guide to Java Persistence APIs. Third. Apress. ISBN: 978-1484234198

## Setup

> TODO: Docker Container

User credentials for all databases:

User: **student**  
Password: **student**    
Schema: **testdb**

### DB scripts

> TODO: Will be automated

#### MariaDB
```shell
mysql -u root -p <rootPassword>
```
```sql
create user 'student'@'localhost' identified by 'student';
create database testdb;
grant all privileges on testdb.* to 'student'@'localhost' identified by 'student';
```
#### Postgres
```shell
sudo -u postgres psql
```
```sql
create database testdb;
create user student with encrypted password 'student';
grant all privileges on database testdb to student;
```

## Versions used
- Java 11
- MariaDB 10.4
- Postgres 11.5
- Oracle Database Enterprise Edition 12.2.0.1
