FROM hseeberger/scala-sbt:8u181_2.12.8_1.2.8 as building
RUN mkdir /building
WORKDIR /building
COPY . /building
RUN sbt assembly

FROM openjdk:8u201-jre-alpine3.9
RUN mkdir /app
COPY --from=building /building/target/scala-2.12/challenge-back-end-assembly-*.jar /app/
WORKDIR /app
ENTRYPOINT java -jar challenge-back-end-assembly-*.jar