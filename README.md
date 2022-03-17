# Task #1: Accounts Microservice

The challenge is to implement a java microservice that provide the functionality described by the OpenAPI spec below. Take in account you may need python installed to run some scripts. This microservice is part of a system for users to rent scooters in a city, like Paris, France. The code challenge itselft is part of a research and any feedback will be approciated.

**Note**: please use the auto-generated code as much you can and add unit test for your code in order to reach the 80%.

## OpenAPI Specification

[Specification](/src/main/resources/openapi/Accounts.yaml)

Additionally, we include the following functional tests for the microservice:

[RESTest package](/tests/restest-1.2.0-test1.zip)


1. Go to `/tests`, unzip file, and change to `restest-1.2.0-test1` directory
2. Download [this file](https://github.com/isa-group/RESTest/releases/download/restest-1.2.0/restest-1.2.0.zip) to directory and unzip it (this is pretty heavy file and contains basically a release of [this open source project](https://github.com/isa-group/RESTest))
3. Run script `./restest.sh` (app must be running in port `8080`)
4. See reports in target directory
5. See reports in browser 
```
    cd target/allure-reports/account
    
    python -m http.server 8081
    
    open http://localhost:8081/ in browser
```

## Context

The microservice is a backend service that gives support to the following user stories:

 - **US3.** *As a scooter user I want to create an account to use the electric scooter rental service.*
   - The user must exist. This can be checked calling UserManagement service.
 - **US4.** *As a scooter user I want to associate my user with an account to add funds to the account to use the electric scooter rental service.*
   - The amount must be positive.
 - **US5.** *As a scooter user I want to register in the system to associate my user with an account with credit*
   - The user must exist. This can be checked calling UserManagement service.
   - The user and the account must not be already associated.
 - **US6.** *As a scooter user I want to link my account to MercadoPago to buy credits*
   - The user must exist. This can be checked calling UserManagement service.
   - The MercadoPago account must exist.

A high-level design of the endpoint, including its internal functions (i.e., operations) and repository, is provided below:

![](/diagrams/accounts-task1-design.png)

The data model (i.e., entities) for the repository is given below:

![](/diagrams/accounts-task1-erd.png)

## Steps

You should carry out the task according to the following instructions:

1. Fork and clone this repo.
2. Execute the Maven scripts to generate a Java code skeleton for the microservice and the database schema.
3. **Implement the functions required by the microservice** (Feel free to mock something if you need).
4. Use the auto generated code crated by maven once you compile the project (`mvn compile` right after clone the repo).
5. Execute the tests provided for the microservice, along with any additional tests you would like to include.
6. When done, submit (push) all your artifacts to the forked repo.
7. Add unit tests in order to reach at least the 80% of coverage.
8. After your final submission, please complete the following [questionnaire](https://docs.google.com/forms/d/e/1FAIpQLSc2dunMEOzIFLQZe9a7LBaQ8jVOx33jIGChISkACr2VCsVjHg/viewform).

You must consider the following conditions when submitting the task:
- Check that all the tests for the microservice passed (successfully)
- Achieve an 80% of code coverage (we will check the coverage using `mvn test`).
- Write clean and well-structured code, whenever possible.
- All the parts that can be variables (eg: ip of external services) must be in a configuration file.
- If needed, include a **readme** file with further instructions to build and execute the microservice and its associated tests.

## Pre-requisite to run the AccountsApplication (One-time-setup)
- install mysql database server
- create a database `accounts`
- create the tables using the queries in schema.sql (src/main/resources/schema.sql)

## To generate the model classes
- make sure `openapi-generator-maven-plugin` and `maven-resources-plugin` plugins are uncommented in pom.xml
- run command `mvn generate-sources` (models classes as per the API Specification will be generated and copied to `/src/main/java/com/globant/tc/scooter/accounts/model`)

## Steps to run AccountsApplication
- Update database credentials in application.properties (src/main/resources/application.properties) as per your own.
  - spring.datasource.url=jdbc:mysql://<mysql-server-url>:<mysql-server-port>/accounts
  - spring.datasource.username=`<username>`
  - spring.datasource.password=`<password>`
- For using Mock implementations of external services update following in application.properties.
  - Set `isUserServiceEnabled` and `isMercadoPagoServiceEnabled` to false.
- For connecting to external services update following in application.properties.
  - Set `isUserServiceEnabled` and `isMercadoPagoServiceEnabled` to true
  - Update `service.user.url=<user-service-endpoint>` and `service.mercadoPago.url=<mercadopago-endpoint>` as per service endpoints 
- If using IDE
  - Run the main method from AccountsApplication.java
- For running without IDE
  - run command `mvn clean install` in the project-directory. `scooter-accounts-1.0-SNAPSHOT.jar` will be generated on success.
  - run command `java -jar target\scooter-accounts-1.0-SNAPSHOT.jar`

## Contact

In case you might have questions, drop us a line to <tcloud.research@gmail.com>. Because we are executing this challenge with several developers please add all the details that you need in order to identify you and the question context. Thanks!
