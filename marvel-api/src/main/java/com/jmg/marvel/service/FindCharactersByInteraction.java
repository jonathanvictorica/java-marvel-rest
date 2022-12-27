package com.jmg.marvel.service;

import com.jmg.marvel.model.Character;
import com.jmg.marvel.model.Comic;
import com.jmg.marvel.core.FindCharacter;
import com.jmg.marvel.core.FindComics;
import com.jmg.marvel.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class FindCharactersByInteraction {

    private FindCharacter findCharacterSD;
    private FindComics findComicsSD;

    @Autowired
    public FindCharactersByInteraction(FindCharacter findCharacterSD, FindComics findComicsSD) {
        this.findCharacterSD = findCharacterSD;
        this.findComicsSD = findComicsSD;
    }

    public FindCharactersByInteractionResponse execute(String characterName) throws Exception {
        Character character = findCharacterSD.getByName(characterName);
        List<Comic> comics = findComicsSD.findByCharacter(character);
        List<Character> characters = comics.stream().map(Comic::getCharacters).flatMap(Collection::stream).filter(character1 -> !character1.getId().equals(character.getId())).distinct().map(characterComic -> new Character(characterComic.getId(), characterComic.getName())).collect(Collectors.toList());
        FindCharactersByInteractionResponse resp = new FindCharactersByInteractionResponse();
        resp.lastSync = DateUtils.formatDateTime(character.getLastSync());
        resp.characters = characters.stream().map(characterUnit -> {
            return new FindCharactersByInteractionResponse.Character(characterUnit.getName(), Comic.filterComicByCharacter(comics, characterUnit));
        }).collect(Collectors.toList());
        return resp;
    }



    @Getter
    @Setter
    public static class FindCharactersByInteractionResponse implements Serializable {
        private String lastSync;
        private List<Character> characters;

        @AllArgsConstructor
        @NoArgsConstructor
        @Getter
        @Setter
        public static class Character implements Serializable {
            private String character;
            private List<String> comics;
        }
    }


}
