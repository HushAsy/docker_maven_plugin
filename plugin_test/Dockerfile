FROM anapsix/alpine-java:latest

#添加本地jar包
RUN mkdir -p /home/admin/app/
RUN mkdir -p /home/admin/tomcat/
ENV CATALINA_HOME /home/admin/tomcat/
#
ARG JAR_FILE

ADD target/${JAR_FILE} /home/admin/app/
#
#
#
# 将启动命令写入启动脚本 start.sh
RUN echo "$JAVA_HOME/bin/java -jar $JAVA_OPTS -Ddubbo.address.ip=$POD_IP -Djava.security.egd=file:/dev/./urandom  /home/admin/app/${JAR_FILE} --spring.profiles.active=prod" > /home/admin/start.sh && chmod +x /home/admin/start.sh
WORKDIR $CATALINA_HOME
ENTRYPOINT ["/bin/bash", "/home/admin/start.sh"]