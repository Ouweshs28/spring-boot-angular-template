FROM openjdk:17
WORKDIR /usr/local/bin/
COPY project-template-@project.version@.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]