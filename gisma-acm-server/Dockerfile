# Build Stage
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /source
COPY . .
RUN mvn clean package -DskipTests

# Runtime Stage
FROM eclipse-temurin:21.0.6_7-jdk-alpine AS runtime

WORKDIR /app

ARG JAR_FILE=/source/gisma-acm-server/target/*.jar
ARG EXECUTOR_JAR_FILE=/source/gisma-acm-executor/target/*with-dependencies.jar

COPY --from=build ${JAR_FILE} app.jar
COPY --from=build ${EXECUTOR_JAR_FILE} libs/gisma-acm-executor.jar

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

ENTRYPOINT ["java","-jar","app.jar"]