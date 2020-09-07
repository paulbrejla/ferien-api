FROM openjdk:11-alpine

ENV APP_HOME /usr/src/app
ENV APP_NAME holidays-api

WORKDIR $APP_HOME

RUN wget https://download.newrelic.com/newrelic/java-agent/newrelic-agent/current/newrelic-java.zip -O newrelic.zip
ADD . $APP_HOME

RUN ./gradlew build

EXPOSE 80

CMD ["java","-Xms156m","-Xmx500M","-javaagent:newrelic.jar","-jar","/usr/src/app/build/libs/app-0.0.1-SNAPSHOT.jar"]
