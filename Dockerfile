FROM openjdk:11
ARG PROFILE
ARG ADDITIONAL_OPTS

ENV PROFILE = ${PROFILE}
ENV ADDITIONAL_OPTS = ${ADDITIONAL_OPTS}

WORKDIR /opt/spring_boot
COPY /target/springboot2*.jar springboot2_essentials.jar

SHELL ["/bin/sh", "-c"]
EXPOSE 5005
EXPOSE 8080

CMD java ${ADDITIONAL_OPTS} -jar springboot2_essentials.jar --spring.profiles.active=${PROFILE}
