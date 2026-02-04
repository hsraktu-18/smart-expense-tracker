# 1️⃣ Use Java 17 base imagel
FROM eclipse-temurin:17-jdk

# 2️⃣ Create app directory inside container
WORKDIR /app

# 3️⃣ Copy jar file into container
COPY target/*.jar app.jar

# 4️⃣ Expose application port
EXPOSE 8080

# 5️⃣ Run the application
ENTRYPOINT ["java", "-jar", "app.jar","--spring.profiles.active=prod"]

# ---------- BUILD STAGE ----------
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# ---------- RUN STAGE ----------
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar","--spring.profiles.active=prod"]

