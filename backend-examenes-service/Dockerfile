FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} backend-examenes-service.jar
ENTRYPOINT ["java","-jar","/backend-examenes-service.jar"]