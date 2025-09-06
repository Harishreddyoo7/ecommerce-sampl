FROM tomcat:9.0.83-jdk17
EXPOSE 8080
COPY target/ecommerce-sample-war.war /usr/local/tomcat/webapps/
COPY tomcat-users.xml /usr/local/tomcat/conf/
