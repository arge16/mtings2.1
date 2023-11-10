FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} backend-config-service.jar
ENTRYPOINT ["java","-jar","/backend-config-service.jar"]