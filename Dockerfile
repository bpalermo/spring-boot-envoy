FROM gcr.io/distroless/java:11
ARG ARTIFACT_ID=spring-boot-template
COPY  /target/${ARTIFACT_ID}.jar /app/app.jar
WORKDIR /app
CMD ["app.jar"]
