FROM openjdk:8-alpine

ENV APP_HOME /usr/src/app
ENV APP_NAME holidays-api

WORKDIR $APP_HOME
ADD . $APP_HOME

RUN ./gradlew build

EXPOSE 80

CMD ["java","-Xms156m","-Xmx500M","-javaagent:newrelic.jar","-jar","/usr/src/app/build/libs/app-0.0.1-SNAPSHOT.jar"]
