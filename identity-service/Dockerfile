FROM tomcat:9.0.13-jre11-slim

RUN rm -rf /usr/local/tomcat/webapps/*

# The application's war file
ARG JAR_FILE=target/identityservice.war

# Add the application's war to the container
COPY ${JAR_FILE} /usr/local/tomcat/webapps/ROOT.war

CMD ["catalina.sh", "run"]