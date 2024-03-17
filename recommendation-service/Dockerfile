# PRODUCTION
FROM eclipse-temurin:21-jdk as BUILDER
WORKDIR /ddd-main-service
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
RUN ./mvnw package -DskipTests
COPY target/*.jar ddd-main-service.jar

FROM eclipse-temurin:21-jre
COPY --from=BUILDER /ddd-main-service/ddd-main-service.jar ddd-main-service.jar
ENTRYPOINT ["java", "-jar", "ddd-main-service.jar"]

# TESTING
#FROM eclipse-temurin:21-jdk
#WORKDIR /ddd-main-service
#COPY target/*.jar ddd-main-service.jar
#ENTRYPOINT ["java", "-jar", "ddd-main-service.jar"]