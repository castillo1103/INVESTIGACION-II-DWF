# Etapa de build
FROM maven:3.9.9-eclipse-temurin-17 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa de ejecución
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copiar JAR generado en la etapa anterior
COPY --from=builder /app/target/Ferreteria-0.0.1-SNAPSHOT.jar app.jar

# Exponer puerto
EXPOSE 8080

# Crear usuario sin privilegios
RUN addgroup --system appgroup && adduser --system --ingroup appgroup appuser
USER appuser

# Activar H2 webAllowOthers
ENV JAVA_OPTS="-Dh2.server.webAllowOthers=true"

# Ejecutar aplicación
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar app.jar"]
