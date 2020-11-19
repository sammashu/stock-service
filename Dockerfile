FROM adoptopenjdk/openjdk8:alpine
VOLUME /stock-service
ARG JAR_FILE=target/stock-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} stock-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/stock-service.jar"]