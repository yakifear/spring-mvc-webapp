FROM tomcat:9-jdk17
COPY /home/webapp/build/spring-mvc.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080