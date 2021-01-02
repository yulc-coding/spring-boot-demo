FROM centos:7
RUN yum -y install java
CMD ["java", "-jar","spring-boot-demo-0.0.1.jar"]