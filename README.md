# ITC Geo Application


Author: [ZHU, Chenfeng](http://about.me/zhuchenfeng)


Table of contents
-----------------

* [Introduction](#introduction)
* [Requirements](#requirements)
  * [Web Service Application](#web-service-application)
  * [Frontend](#frontend)


## Introduction




## Requirements

### Web Service Application

Xls file contains list of geological sections. Section has structure:

```
{
name: “Section 1”,
geologicalClasses: [{
name: “Geo Class 1”,
Code: “GC1”
}, ...]
}
```
We need CRUD rest api to get sections. We need api to import/export xls file that contains sections
Example:

```
Section name | Class 1 name | Class 1 code | Class 2 name | Class 2 code
Section 1|Geo Class 1|GC1|Geo Class 2|GC2
Section 2|Geo Class 2|GC2
Section 3|Geo Class 5|GCX7
```
Create web based application for processing XLS files.

Requirements
1. small restAPI web-application
2. all data (except files) should be in JSON format
3. should have API for adding a job for file parsing ( “register-job”)
4. “register-job” should return id of the Job
5. File should be parsed in asynchronous way, result should be stored id db
6. should have API for getting result of parsed file by Job ID
7. should have API for searching results by name, code
8. Basic Authorization should be supported (optional)
9. Page for jobs adding and result view (optional)

Technology stack(optional)
1. Spring
2. Hibernate
3. Spring Data
4. gradle/maven

### Frontend

Please create a single page web application using AngularJS 1.X.

The application should be a simple chart widgets dashboard. On a dashboard user can put 1 to 4 charts. Optional: Make charts as directive. On the top bar it should be possible to set a date period that will be applied to all charts at the same time.

Every chart should be configurable:
- type: line or bar chart
- selection of sensors
- color of lines

For data shown in charts generate random values or use some public data API (optional).
Example: There are N sensors, some of them measure temperature, some - humidity, some - light. We need to show that data on different charts. Sometimes we can combine temperature from 2 sensors and humidity and all in 1 chart.

Other libraries that could be used:
- angular ui bootstrap or analogue
- angular ui router or analogue
- highcharts or analogue


## Design


## Manual



## Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.1.7.RELEASE/maven-plugin/)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/{bootVersion}/reference/htmlsingle/#boot-features-jpa-and-spring-data)
* [Spring Web Starter](https://docs.spring.io/spring-boot/docs/{bootVersion}/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring Web Services](https://docs.spring.io/spring-boot/docs/{bootVersion}/reference/htmlsingle/#boot-features-webservices)
* [AngularJS](https://angularjs.org/)
