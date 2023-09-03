FROM maven:latest

WORKDIR /app

RUN apt-get update; \
    apt-get install -y python2.7;

COPY . .
RUN mvn clean install

CMD mvn spring-boot:run
