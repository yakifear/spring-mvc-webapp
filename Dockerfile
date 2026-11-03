FROM tomcat:9-jdk17
RUN rm -rf /usr/local/tomcat/webapps/*
COPY /var/jenkins/workspace/cicd/target/spring-mvc-app1.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080