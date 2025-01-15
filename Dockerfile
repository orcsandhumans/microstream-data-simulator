FROM amazoncorretto:17.0.13-al2023-headless

MAINTAINER Max Chukhnov "4myself.max@gmail.com"

ADD target/data-simulator-0.0.1-SNAPSHOT.jar app.jar

CMD ["java","-jar","app.jar"]