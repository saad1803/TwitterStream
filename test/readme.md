## READ ME GUIDE FOR APPLICATION

This is a maven based application built in spring boot.
Spring Boot is used to leverage for dependency injections that are needed.
Executor Service is used so that multiple threads can be used to write to multiple files and its all not sequential.

consumer and access keys are added in the TwitterProperties.java. Would have added those to application.properties
but spring doesnt work well with beans injections with parameters in the thread environment.

Lombok and sl4j is used for logging.

Just download the app to your machine and run it like hava application or build it using maven and run the jar like below.
java -jar *.jar

Once the console starts, it asks for 5 topics. You can write as below 
Cat, Dog, Chicken, Burger, Icecream