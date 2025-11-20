# Imagen runtime con Java 17 (igual que en el pom)
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# OJO: asegúrate que el nombre del jar coincida con el generado por Maven
# Puedes usar el comodín * si quieres.
COPY target/microservice-cartservice-levelup-0.0.1-SNAPSHOT.jar app.jar
# o:
# COPY target/microservice-cartservice-levelup-*.jar app.jar

# Opciones recomendadas para correr en contenedor
ENV JAVA_TOOL_OPTIONS="-XX:+ExitOnOutOfMemoryError -XX:+UseContainerSupport"

# Puerto interno del contenedor
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
