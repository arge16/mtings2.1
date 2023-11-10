FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} backend-eureka-service.jar
ENTRYPOINT ["java","-jar","/backend-eureka-service.jar"]