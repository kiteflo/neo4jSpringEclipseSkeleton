package com.jooik.demo.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

import com.jooik.demo.domain.VoiceActor;

/**
 * Graph access layer to Springfield...
 */
@Repository
public interface VoiceActorRepository extends GraphRepository<VoiceActor>
{
	@Query("start actors=node:__types__(className='VoiceActor') " +
		   "return actors")
	public Page<VoiceActor> findAllVoiceActors(Pageable page);
}
