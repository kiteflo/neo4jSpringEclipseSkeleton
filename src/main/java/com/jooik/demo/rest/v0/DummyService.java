package com.jooik.demo.rest.v0;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jooik.demo.domain.Habitant;
import com.jooik.demo.repositories.HabitantRepository;
import com.jooik.demo.util.CollectionsUtil;

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
