# Docker Multi-Stage Build para KuichiWeb
# Optimizado para producción con Spring Boot

# ============================================
# STAGE 1: Build (Compilación)
# ============================================
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copiar solo pom.xml primero (mejor cache de Docker)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar código fuente
COPY src ./src

# Compilar aplicación (skip tests para build más rápido)
RUN mvn clean package -DskipTests

# ============================================
# STAGE 2: Runtime (Ejecución)
# ============================================
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Crear usuario no-root para seguridad
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copiar JAR desde stage build
COPY --from=build /app/target/*.jar app.jar

# Puerto de exposición
EXPOSE 8080

# Variables de entorno por defecto (se sobrescriben en deploy)
ENV SPRING_PROFILES_ACTIVE=prod
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Comando de inicio
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
