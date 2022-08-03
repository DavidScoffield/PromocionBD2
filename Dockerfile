FROM maven:3.6.3-openjdk-17-slim AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package -DskipTests

FROM openjdk:17-alpine AS app
# run under a user
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
# run app
COPY --from=build /usr/src/app/target/PromocionBD2-0.0.1-SNAPSHOT.jar /usr/app/app.jar
# EXPOSE 8081
ENTRYPOINT ["java","-jar","/usr/app/app.jar"]
