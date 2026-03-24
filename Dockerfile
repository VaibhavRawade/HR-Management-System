FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY target/Company-Managment.jar app.jar
EXPOSE 8086
ENTRYPOINT ["java", "-jar", "app.jar"]