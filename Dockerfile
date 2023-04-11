# FROM openjdk:8-jdk-alpine
#
# WORKDIR /app
#
# # Copy Maven files and download dependencies
# COPY . .
#
#
# RUN ./mvnw dependency:go-offline -B
#
# # Package the application into a JAR file
# RUN mvn spring-boot:run




# linux
FROM openjdk:8-jdk-alpine

WORKDIR /app

COPY .mvn/ ./.mvn
COPY mvnw pom.xml ./

RUN apk add --no-cache maven
RUN mvn dependency:go-offline

COPY src ./src

CMD ["mvn", "spring-boot:run"]

# # win
# FROM mcr.microsoft.com/java/jdk:8-zulu-alpine

# WORKDIR /app
# COPY .mvn/ .mvn
# COPY mvnw pom.xml ./

# RUN ./mvnw dependency:go-offline

# COPY src ./src

# CMD ["./mvnw.cmd", "spring-boot:run"]
