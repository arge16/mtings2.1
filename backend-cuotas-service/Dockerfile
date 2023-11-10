FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} backend-cuotas-service.jar
ENTRYPOINT ["java","-jar","/backend-cuotas-service.jar"]