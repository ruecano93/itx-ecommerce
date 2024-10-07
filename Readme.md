# ReadMe

## Introduction

Springboot service that returns the available prices for a product and brand

## Feature

The system only exposes one endpoint which, knowing the brand, product and date, 
returns the active price at that time.

## API REST

Service created with openApi 3.0 (apiFirst)

[here](./application/src/main/resources/ecommerceAPI.yaml) 
to check the openapiSwagger

| Resource      | Method | Path                              | Descripción                                               |
|---------------|--------|-----------------------------------|-----------------------------------------------------------|
| getPriceInfo  | GET    | ecommerce/brand/:id/product/price | Get the active price for selected brand, product and date |

## Build

This project is built using the Maven tool. If you want to run all phases,
just run from the root of the project

````shell script
mvn clean install
````

### Running locally

The project can be run from the IDE or by executing the following command:

````shell script
mvn spring-boot:run
````

### Running the test

The project has unit and integration test (requested in proof)

If u want to run all test

````shell script
mvn test
````

If u want to run integration test

````shell script
mvn -Dtest=PriceControllerTest test 
````