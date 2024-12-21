FROM openjdk:17
ARG JAR_FILE=build/libs/challenge-1.0.0.jar
COPY ${JAR_FILE} challenge.jar
ENTRYPOINT ["java", "-jar", "/challenge.jar"]