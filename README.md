# Develivery API

This project is a microservice API written with framework Spring Boot. The main purpose of this project is to 
demonstrate how to build and gain experience with challenges that Microservice Architecture can offer.

This is an API REST to handle product orders delivery and track the status of the product during the shipping. This API uses concepts as TDD, SOLID e Automated tests to
assure the quality of the code, the funcionality of the API and the maintenance capability of the system.

 - Ordering API REST

    A microservice to manage products orders of a ecommerce application. See at: [matheusinit/ordering-api-aspnet](https://github.com/matheusinit/ordering-api-aspnet)

## Why use Instant instead of LocalDateTime or ZonedDateTime?

`Instant` is a class to represent a instant of time, a moment, in seconds since 00:00:00 UTC on 1 January 1970 (Unix epoch). It is used to represent a moment in time since the 
Unix epoch, so do even has information about a date or time.

`LocalDateTime` has no information about time-zone, so it can represent different **instants** depending on
the time-zone the computer is localed. It is used more to represent date and time, such as Birthdays.

And `ZonedDateTime` can represent a date and time with the information about the time-zone, so more suited for working with dates that
change time-zone that want date and time to be consistent.

## How to run

To run the project:

    ./gradlew bootRun

To download the dependencies:

    ./gradlew build --refresh-dependencies

To run the unit tests:
    
    ./gradlew test

To run the integration tests:

    ./gradlew integrationTest


## Features

 + [x] Get an order from the message queue
 + [x] Add delivery stations
 + [ ] Get delivery stations
 + [ ] Inform product is in delivery
 + [ ] Inform product was delivered

## References

 - :link: [Spring Boot](https://spring.io/guides/gs/spring-boot/)
 - :link: [What are microservices?](https://microservices.io/index.html)
 - :book: Microservice Patterns: With Examples in Java
 - :link: [Best practices for REST API design](https://stackoverflow.blog/2020/03/02/best-practices-for-rest-api-design/)

 Built with :heart: by matheusinit

