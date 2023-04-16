FROM openjdk:latest
COPY target/HomeWork-0.0.1-SNAPSHOT.jar /HomeWork.jar
WORKDIR /app
EXPOSE 8001
RUN sh -c 'touch HomeWork-0.0.1-SNAPSHOT.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","HomeWork-0.0.1-SNAPSHOT.jar"]
