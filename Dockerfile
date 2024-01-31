FROM openjdk:17.0.2-oracle

# Add Maintainer Info
LABEL maintainer="opeyemi.kabiru@yahoo.com"

ARG JAR_FILE=target/abc-ecommerce-api.jar

WORKDIR /opt/app

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","app.jar"]