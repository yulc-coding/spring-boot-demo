#基础镜像，如果本地没有，会从仓库拉取。
FROM openjdk:8-jdk-alpine

# 创建挂载，在主机的 /var/lib/docker下创建一个临时文件，并链接到容器的/tmp。
VOLUME ["/tmp"]

# 设置编译镜像时加入的参数。这里的JAR_FILE就是 Maven 插件中的<JAR_FILE>target/${project.build.finalName}.jar</JAR_FILE>
ARG JAR_FILE

# 将打包的文件放入容器
ADD ${JAR_FILE} app.jar

# 运行命令，既： java -jar spring-boot-demo-0.0.1.jar
CMD ["java", "-jar","spring-boot-demo-0.0.1.jar"]