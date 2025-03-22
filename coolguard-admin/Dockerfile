FROM eclipse-temurin:17-jre-alpine

WORKDIR /coolguard

# 复制 JAR 并设置权限（镜像层优化）
COPY ./target/coolguard-admin.jar app.jar

# 暴露端口（保持与应用配置一致）
EXPOSE 8081

# 环境变量配置
ENV TZ=Asia/Shanghai \
    JAVA_OPTS="-Xms512m -Xmx512m -Djava.security.egd=file:/dev/./urandom"

# 启动命令
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar
