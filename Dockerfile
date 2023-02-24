FROM openjdk:8-jdk-alpine
COPY target/epaper-0.0.1-SNAPSHOT.jar epaper-0.0.1.jar
COPY devops/entrypoint.sh entrypoint.sh
ENTRYPOINT ["/entrypoint.sh"]
