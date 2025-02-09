FROM maven:3.8.5-openjdk-17
EXPOSE 8080 5005

WORKDIR /demo
COPY . .
RUN mvn clean install

#CMD mvn spring-boot:run
CMD ["mvn", "spring-boot:run", "-Dspring-boot.run.jvmArguments=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"]
