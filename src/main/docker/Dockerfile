#FROM frolvlad/alpine-oraclejdk8:slim
#VOLUME /tmp
#ADD kfwl-0.0.1-SNAPSHOT.jar app.jar
#RUN sh -c 'touch /app.jar'
#ENV JAVA_OPTS=""
#ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]

FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
RUN sh -c 'touch /kfwl-0.0.1-SNAPSHOT.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /kfwl-0.0.1-SNAPSHOT.jar" ]