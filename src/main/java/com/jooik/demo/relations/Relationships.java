package com.jooik.demo.relations;

/**
 * This class is used to store all relationships used in the Springfield graph.
 * Accessing relationships via common members is much better than wiring these
 * as Strings into Cypher...
 */
public class Relationships
{
	public static final String SPOKEN_BY = "SPOKEN_BY"; // Habitant <- VoceActor
	public static final String WEBLINKS = "WEBLINKS"; // VoiceActor -> Webpage
}
