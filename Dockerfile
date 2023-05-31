FROM gradle:7.6.1-jdk8 AS builder

COPY ./ /app
WORKDIR /app

RUN ./gradlew build

FROM tomcat:alpine

RUN ["rm", "-fr", "/usr/local/tomcat/webapps/ROOT"]
COPY --from=builder /app/build/libs/docker-tomcat-java-example.war /usr/local/tomcat/webapps/ROOT.war
