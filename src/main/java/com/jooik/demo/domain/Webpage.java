package com.jooik.demo.domain;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

/**
 * Webpage representation...
 */
@NodeEntity
@XmlRootElement
public class Webpage
{
	// ------------------------------------------------------------------------
	// members
	// ------------------------------------------------------------------------
	
	@GraphId
	private Long id;
	
	private String url;
	
	// ------------------------------------------------------------------------
	// overridden stuff
	// ------------------------------------------------------------------------
	
	@Override
	public String toString()
	{
		return url;
	}

	// ------------------------------------------------------------------------
	// GETTER & SETTER
	// ------------------------------------------------------------------------

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}
}
