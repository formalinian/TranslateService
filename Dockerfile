FROM maven:3.6-openjdk-17 AS test
COPY src /src
COPY pom.xml /
ENV YA_TOKEN=$YA_TOKEN
RUN mvn -f /pom.xml clean package

FROM openjdk:17
WORKDIR /test
EXPOSE 8080
COPY --from=test target/*.jar test.jar
ENTRYPOINT ["java","-jar","test.jar"]