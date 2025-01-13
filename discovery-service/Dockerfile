FROM maven:3.9.4-eclipse-temurin-17

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

EXPOSE 8761

CMD ["mvn", "spring-boot:run"]
