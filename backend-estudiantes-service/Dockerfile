FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} backend-estudiantes-service.jar
ENTRYPOINT ["java","-jar","/backend-estudiantes-service.jar"]