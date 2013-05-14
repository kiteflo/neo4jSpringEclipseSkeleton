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