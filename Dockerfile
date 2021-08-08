FROM openjdk:8-jdk-alpine

EXPOSE 8080
ENV JAVA_OPTS -Xms256m -Xmx512m

COPY target/*.jar app.jar
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /app.jar" ]