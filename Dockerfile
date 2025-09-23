# Etapa 1: Build con Java 21 + Maven instalado manualmente
FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

# Copiar archivos de configuración
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

# Descargar dependencias (se cachea si no cambio pom.xml)
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

# Copiar código fuente
COPY src ./src

# Compilar aplicación
RUN ./mvnw clean package -DskipTests -B


# Etapa 2: Runtime con JRE ligero
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Instalar herramientas útiles para debugging (opcional)
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Crear usuario no root por seguridad
RUN groupadd -r appuser && useradd -r -g appuser appuser

# Copiar el JAR desde la etapa de build
COPY --from=builder /app/target/*.jar app.jar

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
