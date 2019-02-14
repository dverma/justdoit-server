# justdoit-server

## Prerequisites
* A supported Java Development Kit [JDK 8](https://www.oracle.com/technetwork/pt/java/javase/downloads/jdk8-downloads-2133151.html)
* Apache's [Maven](https://maven.apache.org/), Version 3).
* A [Git](https://git-scm.com/downloads) client.

## To Run locally

* Clone the repo
* cd into the repo
* Buld the jar
```shell
mvn clean package
```
* When the web app has been created, start the web app using Maven; for example:
```shell
mvn spring-boot:run
```
* The app should now be running at port 8080
* In memory database H2 can be accessed at http://localhost:8080/h2-console
