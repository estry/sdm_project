FROM adoptopenjdk/openjdk8:latest
EXPOSE 8080
VOLUME /tmp
ARG JAR_FILE=target/Application-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
