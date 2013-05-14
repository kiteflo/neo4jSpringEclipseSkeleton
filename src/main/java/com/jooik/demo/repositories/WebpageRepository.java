package com.jooik.demo.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import com.jooik.demo.domain.Webpage;

/**
 * Graph access layer to Springfield...
 */
@Repository
public interface WebpageRepository extends GraphRepository<Webpage>
{
	@Query("start webpages=node:__types__(className='Webpage') " +
		   "return webpages")
	public Page<Webpage> findAllWebpages(Pageable page);
}
