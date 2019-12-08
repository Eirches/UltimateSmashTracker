FROM openjdk:8-jre-alpine
VOLUME /app
EXPOSE 8080
ADD ./build/libs/ultimatesmashtracker-0.0.1-SNAPSHOT.jar .
ENV SSK=test
ENV tokenExpirySeconds=1200
CMD ["java","-jar", "ultimatesmashtracker-0.0.1-SNAPSHOT.jar"]