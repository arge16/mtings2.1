FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} backend-gateway-service.jar
ENTRYPOINT ["java","-jar","/backend-gateway-service.jar"]