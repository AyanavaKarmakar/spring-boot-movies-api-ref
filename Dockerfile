FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
RUN apt-get update && apt-get install -y maven
COPY --from=build /movies-0.0.1-SNAPSHOT.jar movies.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "movies.jar"]