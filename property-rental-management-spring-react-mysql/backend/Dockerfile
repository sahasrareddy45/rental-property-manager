
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY . .
RUN if [ -f mvnw ]; then chmod +x mvnw && ./mvnw clean package -DskipTests; else apt-get update && apt-get install -y maven && mvn clean package -DskipTests; fi

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
CMD ["sh", "-c", "java -jar app.jar --server.port=${PORT:-8080}"]
