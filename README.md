neo4j - Spring - REST - Kickstart!
=====================================

Ok…setting up a simple neo4j standalone project is pretty straightforward…but quite frequently there are some old fashioned Java-Spring-Dinosaurs trying to create "the perfect Eclipse project" enabling these guys to do everything within their IDE. And then you usually end up in POM-hunting, applicationContext.xml-tuning, web.xml modifications etc. and tudaloo: a few hours have passed until you lean back and finally start focusing on your Java services instead of configuring your project/IDE…

This project is dedicated to all Java-Dinos who want to start coding immediately, simply follow the steps below and you can start coding in about five minutes…time's running now!

Environment
-------------------------------------
* Eclipse 4.2 (J2EE IDE including Web Tools Platform etc.)
* Spring IDE Eclipse Plugin 3.2
* Maven 3.04, somewhere around on my Mac

Application stack (bottom up)
-------------------------------------
* neo4j backend 
* Repository layer
* REST layer
* client interface

1: Create Spring MVC project
-------------------------------------
After adding Spring IDE plugin you can setup a new Spring project, chose __Spring Template__ and select __Spring MVC project__ as project type…name the project etc. and you will end up in a new helloNeo-project within your IDE…

![alt tag](https://github.com/kiteflo/neo4jSpringEclipseSkeleton/blob/Master/screenies/Bildschirmfoto%202013-05-14%20um%2006.43.39.png)

2: Wire in neo using pom.xml & Maven magic
--------------------------------------------
Ok, the next step is the most important one - use our pom.xml template as this template contains __everything you need__ in order to wire in neo4j as well as several further dependencies as there is nothing worse than starting up your tomcat and tomcat complaining about some