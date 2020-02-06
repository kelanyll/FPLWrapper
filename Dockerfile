FROM adoptopenjdk/openjdk11:alpine-slim

WORKDIR /usr/src
COPY target/fpl-wrapper.jar fpl-wrapper.jar
COPY server-config.yml server-config.yml

CMD ["java", "-jar", "fpl-wrapper.jar", "server", "server-config.yml"]