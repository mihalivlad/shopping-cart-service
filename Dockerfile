FROM tomcat:jdk11
ADD target/demo.war /usr/local/tomcat/webapps/
CMD ["catalina.sh", "run"]