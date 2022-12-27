package com.jmg.marvel.core;

import com.jmg.marvel.client.MarvelClient;
import com.jmg.marvel.client.marvel.CharacterResponse;
import com.jmg.marvel.exception.BusinessException;
import com.jmg.marvel.model.Character;
import com.jmg.marvel.repository.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class FindCharacter {

    private MarvelClient marvelClient;
    private CharacterRepository characterRepository;

    @Autowired
    public FindCharacter(MarvelClient marvelClient, CharacterRepository characterRepository) {
        this.marvelClient = marvelClient;
        this.characterRepository = characterRepository;
    }

    public Character getByName(String characterName) throws Exception {
        Character character = characterRepository.findByName(characterName);
        if (character == null || character.isNeedUpdate()) {
            character = findCharacterInMarvel(characterName);
        }
        return character;
    }

    private Character findCharacterInMarvel(String characterName) throws Exception {
        CharacterResponse charactersResponse = marvelClient.findCharacter("name=" + characterName);
        if (charactersResponse.getData().getTotal() == 1) {
            return saveCharacter(charactersResponse);
        } else if (charactersResponse.getData().getTotal() == 0) {
            throw new BusinessException("No search results found");
        } else {
            throw new BusinessException("More than one search result was found");
        }
    }

    private Character saveCharacter(CharacterResponse charactersResponse) {
        var characterResponse = charactersResponse.getData().getResults().get(0);
        Character character = new Character(characterResponse.getId(), characterResponse.getName(), new Date());
        characterRepository.save(character);
        return character;
    }

}
