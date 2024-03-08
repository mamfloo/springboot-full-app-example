FROM amazoncorretto:17
COPY target/demo-0.0.1-SNAPSHOT.jar library.jar
ENTRYPOINT ["java", "-jar", "/library.jar"]
