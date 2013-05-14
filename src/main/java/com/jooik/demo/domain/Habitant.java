package com.jooik.demo.domain;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import com.jooik.demo.relations.Relationships;

/**
 * Springfield habitant representation...
 */
@NodeEntity
@XmlRootElement
public class Habitant
{
	// ------------------------------------------------------------------------
	// members
	// ------------------------------------------------------------------------
	
	@GraphId
	private Long id;
	
	private String firstname;
	private String lastname;
	private String imagePath;
	private int age;
	private String description;
	
	// image - ugly but for the Springfield case totally fine...
	private byte[] image;
	
	
	@RelatedTo(type = Relationships.SPOKEN_BY, direction = Direction.INCOMING)
	private Set<VoiceActor> voiceActors = new HashSet<VoiceActor>();

	// ------------------------------------------------------------------------
	// overridden stuff
	// ------------------------------------------------------------------------
	
	@Override
	public String toString()
	{
		return firstname + " " +lastname;
	}
	
	// ------------------------------------------------------------------------
	// GETTER & SETTER
	// ------------------------------------------------------------------------
		
	public String getFirstname()
	{
		return firstname;
	}

	public void setFirstname(String firstname)
	{
		this.firstname = firstname;
	}

	public String getLastname()
	{
		return lastname;
	}

	public void setLastname(String lastname)
	{
		this.lastname = lastname;
	}

	public int getAge()
	{
		return age;
	}

	public void setAge(int age)
	{
		this.age = age;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getImagePath()
	{
		return imagePath;
	}

	public void setImagePath(String imagePath)
	{
		this.imagePath = imagePath;
	}

	public Set<VoiceActor> getVoiceActors()
	{
		return voiceActors;
	}

	public void setVoiceActors(Set<VoiceActor> voiceActors)
	{
		this.voiceActors = voiceActors;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public byte[] getImage()
	{
		return image;
	}

	public void setImage(byte[] image)
	{
		this.image = image;
	}
}
