FROM openjdk:8
EXPOSE 8080
ADD target/finartz-ticket.jar finartz-ticket.jar
ENTRYPOINT  ["java", "-jar", "finartz-ticket.jar"]