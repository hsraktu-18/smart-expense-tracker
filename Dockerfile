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
