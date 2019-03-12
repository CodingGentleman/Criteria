# Applied use of the Criteria API

_insert exposé here_

User credentials for all databases:
User: student  
Password: student  
Schema: testdb

Versions:

Java 11

Database Management Systems:

MariaDB 10.3


Installation:

MariaDB
```shell
mysql -u root -p <rootPassword>
```
```sql
create user 'student'@'localhost' identified by 'student';
create database testdb;
grant all privileges on testdb.* to 'student'@'localhost' identified by 'student';
```

PostgreSQL
```shell
sudo -u postgres psql
```
```sql
create database testdb;
create user student with encrypted password 'student';
grant all privileges on database testdb to student;
```