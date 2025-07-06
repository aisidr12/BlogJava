# Usa una imagen base con Java
FROM eclipse-temurin:17-jre-alpine

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el jar compilado de tu aplicación al contenedor
COPY target/*.jar app.jar

# Expone el puerto que usa tu app (ajústalo si no es el 8080)
EXPOSE 8080

# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]
