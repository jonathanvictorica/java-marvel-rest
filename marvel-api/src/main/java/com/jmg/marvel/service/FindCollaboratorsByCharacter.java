package com.jmg.marvel.service;

import com.jmg.marvel.model.Character;
import com.jmg.marvel.model.Comic;
import com.jmg.marvel.core.FindCharacter;
import com.jmg.marvel.core.FindComics;
import com.jmg.marvel.utils.DateUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class FindCollaboratorsByCharacter {


    private FindCharacter findCharacterSD;
    private FindComics findComicsSD;

    @Autowired
    public FindCollaboratorsByCharacter(FindCharacter findCharacterSD, FindComics findComicsSD) {
        this.findCharacterSD = findCharacterSD;
        this.findComicsSD = findComicsSD;
    }


    public FindCollaboratorsByCharacterResponse execute(String characterName) throws Exception {
        Character character = findCharacterSD.getByName(characterName);
        List<Comic> comics = findComicsSD.findByCharacter(character);
        FindCollaboratorsByCharacterResponse resp = new FindCollaboratorsByCharacterResponse();
        resp.lastSync = DateUtils.formatDateTime(character.getLastSync());
        resp.writers = comics.stream().filter(comic -> comic.getWriters()!=null).map(Comic::getWriters).flatMap(Collection::stream).distinct().collect(Collectors.toList());
        resp.editors = comics.stream().filter(comic -> comic.getEditors()!=null).map(Comic::getEditors).flatMap(Collection::stream).distinct().collect(Collectors.toList());
        resp.colorists = comics.stream().filter(comic -> comic.getColorists()!=null).map(Comic::getColorists).flatMap(Collection::stream).distinct().collect(Collectors.toList());
        return resp;
    }

    @Getter
    @Setter
    public static class FindCollaboratorsByCharacterResponse implements Serializable {
        private String lastSync;
        private List<String> writers;
        private List<String> editors;
        private List<String> colorists;
    }


}
