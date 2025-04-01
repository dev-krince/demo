FROM openjdk:17

EXPOSE 58080

ARG JAR_FILE=build/libs/*.jar

ADD ${JAR_FILE} /fiss-api.jar
#ADD keystore.p12 /keystore.p12

ENV JAVA_OPTS="-Dspring.profiles.active=prod -Duser.timezone=Asia/Seoul"

RUN echo ${JAVA_OPTS}

CMD ["java", "-jar", "fiss-api.jar", "${JAVA_OPTS}"]