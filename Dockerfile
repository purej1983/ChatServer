FROM gradle:7-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle installDist

FROM openjdk:11
WORKDIR /app
COPY --from=build /home/gradle/src/build/install/ChatServer .
EXPOSE 8080:8080
CMD ["./bin/ChatServer"]
#RUN mkdir /app
#COPY --from=build /home/gradle/src/build/libs/*.jar /app/ChatServer.jar
#ENTRYPOINT ["java","-jar","/app/ChatServer.jar"]
