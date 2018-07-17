FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ARG JAR_FILE
ADD kfwl-0.0.1-SNAPSHOT.jar app.jar
RUN bash -c 'touch app.jar'
EXPOSE 9090
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","-Denv=DEV","/app.jar"]
