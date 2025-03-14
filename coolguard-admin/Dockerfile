FROM eclipse-temurin:17-jre-alpine

WORKDIR /home/app

ARG JAR_FILE=./target/coolguard-admin.jar

COPY ${JAR_FILE} app.jar

EXPOSE 8081

ENV TZ=Asia/Shanghai \
    JAVA_OPTS="-Xms512m -Xmx512m -Djava.security.egd=file:/dev/./urandom"

ENTRYPOINT exec java $JAVA_OPTS -jar app.jar
