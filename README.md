# Task #1: Accounts Microservice

The mission is to implement a java microservice that provide the functionality described by the OpenAPI spec below. Take in account you may need python installed to run some scripts.

This microservice is part of a system for users to rent scooters in a city, like Lima, Peru.

## OpenAPI Specification

[Specification](https://github.com/talent-cloud/test-1/blob/main/src/main/resources/openapi/Accounts.yaml)

Additionally, we include the following functional tests for the microservice:

[RESTest package](https://github.com/talent-cloud/test-1/blob/main/tests/restest-1.2.0-test1.zip)

1. Go to **/tests**, unzip file, and change to **restest-1.2.0-test1** directory
2. Download [jar file](https://github.com/isa-group/RESTest/releases/download/restest-1.2.0/restest-1.2.0.zip) to directory and unzip it
3. Run script **./restest.sh** (app must be running in port **8080**)
4. See reports in target directory
5. See reports in browser 

    **cd target/allure-reports/account** 
    
    **python -m http.server 8081**
    
    open **http://localhost:8081/** in browser

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

![](https://github.com/talent-cloud/test-1/blob/main/diagrams/accounts-task1-design.png)

The data model (i.e., entities) for the repository is given below:

![](https://github.com/talent-cloud/test-1/blob/main/diagrams/accounts-task1-erd.png)

## Steps

You should carry out the task according to the following instructions:

1. Fork and clone this repo.
2. Execute the Maven scripts to generate a Java code skeleton for the microservice and the database schema.
3. **Implement the functions required by the microservice**.
4. Execute the tests provided for the microservice, along with any additional tests you would like to include.
5. When done, submit (push) all your artifacts to the forked repo.
6. After your final submission, please complete the following [questionnaire](https://docs.google.com/forms/d/e/1FAIpQLSc2dunMEOzIFLQZe9a7LBaQ8jVOx33jIGChISkACr2VCsVjHg/viewform).

You must consider the following conditions when submitting the task:
- Check that all the tests for the microservice passed (successfully)
- Achieve an 80% of code coverage
- Write clean and well-structured code, whenever possible.
- All the parts that can be variables (eg: ip of external services) must be in a configuration file.
- If needed, include a **readme** file with further instructions to build and execute the microservice and its associated tests.

## Contact

In case you might have questions, drop us a line to <talentcloud@globant.com>.
