# flightinfoapi

flightinfoapi a Java Springboot Demo REST Api based on the Flight Aware site

## General Info ====

  1) clone the Springboot Java8 REST project and go to the root folder containing the mvn pom and run:
  2) mvn clean install -Dmaven.test.skip=true
  3) mvn spring-boot:run
  4) provide the username and apikey of the FlightAware site at the application.property file at Sources.

  The server runs on port 8085 (test-mode), and try e.g. the next url: http://localhost:8085/flightInfoTemplate?ident=EC-MYT&howMany=15&offset=0
  
  You´ll find three Spring Profiles: dev, prod and test present at the application.
  
  The current profile is set to test, running on an H2 database: so there is no need for installing a DB.
  
  In case you'd like to go for another profile (dev or prod) you have to install a DB. See the src\main\resources\application.properties and the src\main\java\com\artsgard\flightinfoapi\DBConfig class for futher configuration.
  
 ## About the Flight Info Application
 
 The idea of the demo api is the following:
 
     1) You enter a large chunk of flight data bassed on the airplane`s tail-number EC-MYT by running this url: http://localhost:8085/findFlightInfoByTailnumber/EC-MYT The data will be stored into two tables called flight_info and airport_display.
     
     2) You further may filter the data by, for instance, the flight-number IBB2081, using the next url: http://localhost:8085/findFlightInfosByIdent/IBB2081 

The first url calls an external service (FlightAware/ HttpURLConnection) to obtain the flight data. And the second (internal) service will filter the data using Spring-data JPA. 

A third url:

  http://localhost:8085/flightInfoTemplate?ident=EC-MYT&howMany=15&offset=0
  
implements the Spring Rest Template as an alternative to the previous used HttpURLConnection.

Most test-classes are added but I have to repair some errors still.
    
    





