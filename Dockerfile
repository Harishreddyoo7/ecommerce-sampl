# Use official Tomcat image
FROM tomcat:9.0.83-jdk17

# Remove default ROOT app (optional, keeps it clean)
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy the WAR file built by Maven (rename to ROOT.war so it runs at "/")
COPY target/ecommerce-sample-war.war /usr/local/tomcat/webapps/ROOT.war

# Expose Tomcat port
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]
