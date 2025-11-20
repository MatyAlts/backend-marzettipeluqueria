# Usar una imagen base oficial de Java
FROM eclipse-temurin:17-jdk-alpine

# Crear directorio de la app
WORKDIR /app

# Copiar el JAR generado al contenedor
COPY target/peluqueria-backend-1.0.0.jar app.jar

# Comando para ejecutar la app
CMD ["java", "-jar", "app.jar"]
