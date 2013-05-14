neo4j - Spring - REST - Eclipse - Kickstart!
===============================================

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

![Spring MVC project](/screenies/screenie01.png)

2: Wire in neo & JAX-RS using pom.xml & Maven magic
--------------------------------------------
Ok, the next step is the most important one - use our pom.xml template as this template contains __everything you need__ in order to wire in neo4j as well as several further dependencies (JAX-RS) as there is nothing worse than starting up your tomcat and tomcat complaining about some missing jersey jars etc.. The pom.xml includes as well several dependencies to useful add-ons (JUnit testing, tomcat launcher etc.) which you might use later on.

The pom.xml is directly within the root of this Git repository, simply download the pom.xml and replace the pom.xml within your project. As I'm not gonna maintain this Git project during the next 50 years you might have to update versions but for 2013/2014 you should be fine using our versions ;-)

After replacing pom run:
<pre>mvn eclipse:clean</pre>

then run:
<pre>mvn eclipse:eclipse -Dwtpversion=2.0</pre>

3: Start coding neo…
---------------------------------------------
Ok, now it's your turn…dive into the documentation and create some neo repositories, thanx to Spring Data for Neo you can do sth. like this:

<pre>
@Repository
public interface HabitantRepository extends GraphRepository<Habitant>
{
	@Query("start simpsons=node:__types__(className='Habitant') " +
		   "return simpsons")
	public Page<Habitant> findAllHabitants(Pageable page);
}
</pre>


4: Create your business logic & cover with REST layer
------------------------------------------------------
Ok the rest is pretty straightforward: use your Java experience and create your powerful businesslogic! This logic might access your repositories, for simplicity we simply access a repository from our REST service layer directly:

<pre>
@Path("/dummy")
@Service
public class DummyService
{
	@Autowired
	private HabitantRepository habitantRepository;
	
	// Allows to insert contextual objects into the class
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
   
    // Return the list of orders for applications with json or xml formats
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Habitant> getHabitants() {
    	
    	Habitant hab = new Habitant();
    	hab.setFirstname("Test");
    	habitantRepository.save(hab);
    	
    	List<Habitant> habitants = CollectionsUtil.asList(habitantRepository.findAllHabitants(null));
    	return habitants;
    }
}
</pre>

5: Create applicationContext.xml
-------------------------------------------------------------
Usually this is the most annoying step during project creation…Spring configuration can be a nightmare if you are try to challenge yourself setup the configs on your own. In case you simply wanna "get it to work" use our configs! (you might have to adapt your package structure, search for "jooik" and you will find all positions we are currently refering to our own structure)

Add a file applicationContext.xml into the META-INF directory of your project (_src/main/resources_), either copy from our repository or use the file below:
<pre>
<code>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:context="http://www.springframework.org/schema/context"
	xmlns:tx="http://www.springframework.org/schema/tx" xmlns:mvc="http://www.springframework.org/schema/mvc"
	xmlns:neo4j="http://www.springframework.org/schema/data/neo4j"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                           http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
                           http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd
                           http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd
                           http://www.springframework.org/schema/data/neo4j http://www.springframework.org/schema/data/neo4j/spring-neo4j-2.0.xsd">

	<context:spring-configured />
    	<context:annotation-config />
	<context:component-scan base-package="com.jooik.demo" />

	<neo4j:repositories base-package="com.jooik.demo.repositories" />

	<mvc:annotation-driven />

	<bean id="jspViewResolver"
		class="org.springframework.web.servlet.view.InternalResourceViewResolver">
		<property name="viewClass"
			value="org.springframework.web.servlet.view.JstlView" />
		<property name="prefix" value="/WEB-INF/jsp/" />
		<property name="suffix" value=".jsp" />
	</bean>

	<!-- Cypher query server... -->
	<bean id="serverWrapper" class="org.neo4j.server.WrappingNeoServerBootstrapper" init-method="start" destroy-method="stop">
	   <constructor-arg ref="graphDatabaseService"/>
	</bean>
	
	<tx:annotation-driven />

	<beans profile="default">
		<neo4j:config storeDirectory="myneo" />
	</beans>

	<beans profile="prod">
		<neo4j:config graphDatabaseService="graphDatabaseService" />
	</beans>
</beans>
</code>
</pre>

6: Modify web.xml
------------------------------------------
Almost done! Finally you have to make your web application accessible to REST calls AND you have to load the Spring context during startup so simply make your web.xml looking like this:

<pre>
<web-app version="2.5" xmlns="http://java.sun.com/xml/ns/javaee"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd">

	<!-- The definition of the Root Spring Container shared by all Servlets 
		and Filters -->
	<context-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>classpath:META-INF/applicationContext.xml</param-value>
	</context-param>

	<!-- Creates the Spring Container shared by all Servlets and Filters -->
	<listener>
		<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
	</listener>

	<!-- Processes application requests -->
	<servlet>
		<servlet-name>appServlet</servlet-name>
		<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
		<init-param>
			<param-name>contextConfigLocation</param-name>
			<param-value>/WEB-INF/spring/appServlet/servlet-context.xml</param-value>
		</init-param>
		<load-on-startup>1</load-on-startup>
	</servlet>

	<servlet-mapping>
		<servlet-name>appServlet</servlet-name>
		<url-pattern>/</url-pattern>
	</servlet-mapping>

	<servlet>
		<servlet-name>Jersey REST Service</servlet-name>
		<servlet-class>com.sun.jersey.spi.spring.container.servlet.SpringServlet</servlet-class>
		<init-param>
			<param-name>com.sun.jersey.config.property.packages</param-name>
			<param-value>com.jooik.demo.rest.v0</param-value>
		</init-param>
		<load-on-startup>1</load-on-startup>
	</servlet>
	<servlet-mapping>
		<servlet-name>Jersey REST Service</servlet-name>
		<url-pattern>/rest/*</url-pattern>
	</servlet-mapping>

</web-app>
</pre>

7: Enjoy Java coding!
-----------------------------------------
Now you've setup the boiler plate so simply start coding!!!

8: Cool things…
-----------------------------------------
Somewhere we've wired in a jsimione dependency…this is cool stuff and you can ship your project to anybody without forcing him/her to install a tomcat. You can simply checkout our project from the repository and fire up a tomcat container running the whole stack using the following commands:

<pre>
mvn clean install
</pre>

<pre>
java -jar target/dependency/webapp-runner.jar target/*.war
</pre>

P.S.: and the Neo console of course can be accessed as well thanx to clever dependency configuration! localhost:7474...