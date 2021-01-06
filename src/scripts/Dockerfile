FROM openjdk:8-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY ./target/dependency /opt/demo/dependency
COPY ./target/resources /opt/demo/resources
COPY ./target/demo-0.0.1-SNAPSHOT.jar /opt/demo
COPY ./target/run.sh /opt/demo

WORKDIR /
RUN chmod +x /opt/demo/run.sh
ENTRYPOINT ["./opt/demo/run.sh"]