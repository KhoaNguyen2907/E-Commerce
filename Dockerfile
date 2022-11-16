FROM openjdk:11.0.15-jre-slim-buster
COPY target/*.jar /app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
