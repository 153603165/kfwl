FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD kfwl-0.0.1-SNAPSHOT.jar app.jar
RUN sh -c 'touch app.jar'
ENV JAVA_OPTS=""
EXPOSE 9090
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar app.jar" ]