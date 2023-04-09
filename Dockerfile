# linux
FROM openjdk:8

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

RUN ./mvnw dependency:go-offline

COPY src ./src

CMD ["./mvnw", "spring-boot:run"]

# # win
# FROM mcr.microsoft.com/java/jdk:8-zulu-alpine

# WORKDIR /app
# COPY .mvn/ .mvn
# COPY mvnw pom.xml ./

# RUN ./mvnw dependency:go-offline

# COPY src ./src

# CMD ["./mvnw.cmd", "spring-boot:run"]
