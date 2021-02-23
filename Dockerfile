FROM java:8
EXPOSE 8080
ARG JAR_FILE=target/ms_category-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]