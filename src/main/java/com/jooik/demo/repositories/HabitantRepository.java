package com.jooik.demo.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import com.jooik.demo.domain.Habitant;

/**
 * Graph access layer to Springfield...
 */
@Repository
public interface HabitantRepository extends GraphRepository<Habitant>
{
	@Query("start simpsons=node:__types__(className='Habitant') " +
		   "return simpsons")
	public Page<Habitant> findAllHabitants(Pageable page);
}
