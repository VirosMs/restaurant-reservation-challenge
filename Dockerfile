# Etapa 1: Build
FROM maven:3.9.1-eclipse-temurin-21 AS build

WORKDIR /app

# Copiar archivos de configuración
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

# Descargar dependencias (cache)
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

# Copiar código fuente
COPY src ./src

# Compilar aplicación
RUN ./mvnw clean package -DskipTests -B

# Etapa 2: Runtime
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Instalar curl en Alpine
RUN apk add --no-cache curl bash

# Crear usuario no root por seguridad
RUN addgroup -S appuser && adduser -S appuser -G appuser

# Copiar el JAR desde la etapa de build
COPY --from=build /app/target/*.jar app.jar

# Cambiar propiedad del archivo
RUN chown appuser:appuser app.jar

# Usar usuario no root
USER appuser

# Configurar JVM para contenedores
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -Djava.security.egd=file:/dev/./urandom"

# Puerto que expone la aplicación
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Comando para ejecutar la aplicación
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
