# Etapa 1: Compilar la aplicación con Maven
FROM maven:3.9-eclipse-temurin-17-alpine AS build
WORKDIR /build

# Copiar archivos de configuración de Maven
COPY pom.xml .
COPY src ./src

# Compilar el proyecto y generar el JAR
RUN mvn clean package -DskipTests

# Etapa 2: Crear la imagen final con el JAR compilado
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copiar el JAR desde la etapa de compilación
COPY --from=build /build/target/*.jar app.jar

# Exponer el puerto
EXPOSE 8080

# Comando para ejecutar la app
CMD ["java", "-jar", "app.jar"]
