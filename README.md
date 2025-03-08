# **Gisma University ACM Demo**

[![Java](https://img.shields.io/badge/Java-21-orange)](https://openjdk.org/projects/jdk/21/)
[![Spring](https://img.shields.io/badge/SpringBoot-3.4.2-green)](https://spring.io/blog/2025/01/23/spring-boot-3-4-2-available-now)

## What is The ACM Demo

This project aims to simulate ACM competitions for Gisma University of Applied Sciences and is related to the final
project of the Advanced Programming course. This is an MVP that allows users to register, view the list of competitions,
and solve and submit their code. Currently, only the Java programming language is supported, but more languages will be
added later. The project is written in Java using the Spring Boot framework.

Basically, there are two roles: <i>Admin</i> and <i>Standard</i> users, each with different levels of access. For
example, only admins
can submit competitions, while users are not allowed to do so.

## Try out The Application

To run the application make sure <b>Java 21</b> is installed and `JAVA_HOME` environment variable is set
correctly. Additionally, this application requires a <b>MySQL</b> server and <b>MongoDB</b> to store data.
<b>The corresponding tables must be created</b> and pass through the environment variables to the application.

### 1. Run using Maven Wrapper

To run this application using the Maven wrapper, first, ensure you edit the `env.sh` file, which contains the required
environment variables for the application to start.

After editing the mentioned file, run the following command to set the environment variables:

```bash
source env.sh
```

Next, you need to build the project. The `gisma-acm-executor` module is an external library that will be used as a .jar
file and copied automatically during the packaging phase. To build the project, run:

```bash
./mvnw clean package
```

Once the project is built, you can start the application by running the following command:

```bash
./mvnw -pl gisma-acm-server spring-boot:run
```

### 2. Run using Docker Compose

To easily run the application, you can use Docker. Simply run the following command:

```bash
docker-compose up
```

All the required services are included in the `docker-compose.yaml` file.

## Postman Collection

The default postman collection which contains data and samples for all endpoints is located at <i>docs</i> folder.

## Swagger

After running the application, the Swagger UI and API documentation can be viewed using the following link:

<a href="http://localhost:8080/swagger-ui/">Swagger UI</a>

## License

This project is licensed under the [MIT License](LICENSE).  
You are free to use, modify, and distribute this software as long as you include the original copyright and license
notice.