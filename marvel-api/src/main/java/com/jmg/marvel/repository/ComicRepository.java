package com.jmg.marvel.repository;

import com.jmg.marvel.model.Comic;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ComicRepository extends MongoRepository<Comic, Integer> {
    List<Comic> findByCharacters_id(Integer characterId);

}
