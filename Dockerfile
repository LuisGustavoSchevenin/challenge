FROM openjdk:17-slim
WORKDIR /app
ARG JAR_FILE=build/libs/challenge-1.0.0.jar
COPY data/certs/keystore.p12 /usr/local/share/ca-certificates/
COPY ${JAR_FILE} challenge.jar
EXPOSE 8443
RUN update-ca-certificates
CMD ["java", "-jar", "/app/challenge.jar"]
