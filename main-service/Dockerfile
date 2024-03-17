## PRODUCTION
#FROM eclipse-temurin:21-jdk as BUILDER
#WORKDIR /ddd-recommendation-service
#
#COPY mvnw .
#COPY .mvn .mvn
#COPY pom.xml .
#COPY src src
#RUN ./mvnw package -DskipTests
#COPY target/*.jar ddd-recommendation-service.jar
#
#FROM eclipse-temurin:21-jre
#COPY --from=BUILDER /ddd-recommendation-service/ddd-recommendation-service.jar ddd-recommendation-service.jar
#ENTRYPOINT ["java", "-jar", "ddd-recommendation-service.jar"]

# QUICK
FROM eclipse-temurin:21-jdk
WORKDIR /ddd-recommendation-service
COPY target/*.jar ddd-recommendation-service.jar
ENTRYPOINT ["java", "-jar", "ddd-recommendation-service.jar"]