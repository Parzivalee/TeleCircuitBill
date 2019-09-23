FROM java:8-jre
MAINTAINER lwj
VOLUME /tmp
ADD /target/teleCircuitBill.war teleCircuitBill.war
CMD ["java","-Xms512m","-jar","teleCircuitBill.war"]

EXPOSE 8081