FROM java:8-jre
MAINTAINER lwj
VOLUME /tmp
ADD /target/teleCircuitBill.war /teleCircuitBill/
CMD ["java","-Xms512m","-jar","/teleCircuitBill/teleCircuitBill.war"]

EXPOSE 8088