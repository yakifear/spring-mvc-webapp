FROM tomcat:9-jdk17
COPY spring-mvc.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080