FROM maven:3.8.1-jdk-11 AS build
WORKDIR /build
COPY src ./src
COPY pom.xml ./
RUN mvn clean package -e -DskipTests

FROM openjdk:11.0.11-jdk
WORKDIR /app
COPY --from=build /build/target/imurab-0.0.1-SNAPSHOT*jar imurab.jar
EXPOSE 3000
CMD ["java", "-jar", "imurab.jar"]