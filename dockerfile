FROM maven:3.6.3-openjdk-17-slim AS stage1 
WORKDIR /home/app
COPY . /home/app/
RUN mvn -f /home/app/pom.xml clean package


FROM openjdk:17-alpine
COPY --from=stage1 /home/app/target/*.jar docker-conatiner-java-jar-file.jar
ENTRYPOINT [ "sh", "-c", "java -jar /docker-conatiner-java-jar-file.jar" ]