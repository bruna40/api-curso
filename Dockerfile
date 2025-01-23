FROM eclipse-temurin:17-jdk-jammy as build-image
WORKDIR /to-build-app
COPY . .
RUN ./mvnw install -DskipTests=true
RUN ./mvnw clean package -DskipTests=true

FROM eclipse-temurin:17-jre-jammy
COPY --from=build-image /to-build-app/target/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]