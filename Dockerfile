# Usamos imagen de Maven para compilar
FROM maven:3.9.1-eclipse-temurin-17 AS build

WORKDIR /app

# Copiar pom.xml y descargar dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar el código fuente y empaquetar
COPY src ./src
RUN mvn package -DskipTests -B

# Segunda fase: runtime con JRE ligero
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copiar jar generado
COPY --from=build /app/target/*.jar app.jar

# Puerto expuesto por tu aplicación
EXPOSE 8080

# Comando de inicio
ENTRYPOINT ["java","-jar","app.jar"]
