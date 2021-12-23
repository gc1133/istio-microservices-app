FROM openjdk:18-ea-11-jdk-alpine3.15
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /usr/app.jar
ENTRYPOINT ["java","-jar","/usr/app.jar"]