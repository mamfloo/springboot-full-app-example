# Library Application

This is a robust Spring Boot application that follows best practices in software development. It's a simple library management system that allows you to manage books and users.

## Features

- **RESTful API endpoints**: The application provides a set of RESTful API endpoints for managing books and users.
- **PostgreSQL Database**: The application uses PostgreSQL for data persistence.
- **Unit and Integration Tests**: The application includes a suite of unit and integration tests to ensure the quality of the code.
- **Docker Support**: The application can be easily deployed and distributed using Docker.

## Technologies Used

- **Java**: The application is written in Java, a popular and versatile programming language.
- **Spring Boot**: This is a framework that simplifies the setup of Spring applications.
- **Maven**: This is a build automation tool used primarily for Java projects.
- **PostgreSQL**: This is a powerful, open-source object-relational database system.
- **Docker**: This is a platform that enables developers to automate the deployment, scaling, and management of applications.

## Running the Application

(With Docker)To run the application, you need to have Docker and Docker Compose installed on your machine. Once you have these installed, you can start the application by running the following command in the terminal:

```bash
docker-compose up --build
```
This command will start the PostgreSQL database and the application. The application will be accessible at http://localhost:8080/api.


(Without Docker) You need to have installed PostgreSQL + set the access information inside application.properties
```bash
mvn spring-boot:run
```




## Testing
The application includes unit and integration tests to ensure the quality of the code. You can run the tests by executing the following command in the terminal:

```bash
mvn test
``` 

## Docker
The application includes a Dockerfile and a docker-compose.yml file for running the application in Docker. The Dockerfile defines how to build the Docker image for the application, and the docker-compose.yml file defines the services that make up the application.

## Conclusion
This application is a great starting point for anyone looking to learn about Spring Boot, Docker, and best practices in software development. It includes all the necessary components for a modern, production-ready application.