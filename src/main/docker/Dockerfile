FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD kfwl-0.0.1-SNAPSHOT.jar app.jar
RUN bash -c 'touch /app.jar'
RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo "Asia/Shanghai" > /etc/timezone
EXPOSE 9090
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]