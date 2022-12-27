package com.jmg.marvel.repository;

import com.jmg.marvel.model.Character;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CharacterRepository extends MongoRepository<Character, Integer> {

    Character findByName(String characterName);
}
