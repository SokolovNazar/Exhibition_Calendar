# Exhibition Calendar

## Description

System Calendar of Exhibitions. There is a list of exhibition halls in each of which there is a list of expositions. The visitor buys Tickets by making a Payment and choosing the Theme of the exhibition.

## Software

* JDK/JRE 8 or later
* Apache Maven
* MySQL 
* Apache Tomcat 7 or higher

## Installation

* Download project from GitHub
* Unpack .zip
* Specify values of "database.user" and "database.password" keys in ***Exhubitions/src/main/resources/DBinfo.properties***
* Start your MySQL server and execute script 'expositions.sql' from ***Exhubitions/src/main/resources/scripts***
* cd to toor project folder and execute command `mvn clean install`
* copy **ROOT.war** from rarget to %TOMCAT%\webapps 
>if in %TOMCAT%\webapps  already exist folder **ROOT**, then please move it for temporarily.

## Running

* Start Tomcat server by running the script `%TOMCAT%\bin\startup.bat` for Windows or `startup.sh` for Linux
* After server start, application will be available by [URL: localhost](http://localhost:8080)
* Use login **"admin"** and password **"password"** to log in with administrator rights. For log in as regular user just sign up.
* To stop server run script %TOMCAT%\bin\ `shutdown.bat` for Windows ot `shutdown.sh` for Linux 

### Used technology
* Servlet 
* JUnit
* Log4j
* JSP + JSTL
* JDBC 
* Mockito

