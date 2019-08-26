FROM openjdk:8-jre-alpine3.9

WORKDIR /etc/keyper

COPY build/libs/keyper-1.0.0.jar .

ENV AWS_DEFAULT_REGION ""
ENV AWS_REGION ""
ENV AWS_ACCESS_KEY_ID ""
ENV AWS_SECRET_ACCESS_KEY ""

ENTRYPOINT ["java", "-cp", "/etc/keyper/keyper-1.0.0.jar", "com.everythingbiig.keyper.Keyper"]