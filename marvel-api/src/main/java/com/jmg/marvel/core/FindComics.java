package com.jmg.marvel.core;

import com.jmg.marvel.client.MarvelClient;
import com.jmg.marvel.client.marvel.ComicResponse;
import com.jmg.marvel.model.Character;
import com.jmg.marvel.model.Comic;
import com.jmg.marvel.model.Constants;
import com.jmg.marvel.utils.ComicMarvelToComic;
import com.jmg.marvel.repository.CharacterRepository;
import com.jmg.marvel.repository.ComicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FindComics {

    private  ComicRepository comicRepository;
    private CharacterRepository characterRepository;
    private MarvelClient marvelClient;

    @Autowired
    public FindComics(ComicRepository comicRepository, CharacterRepository characterRepository, MarvelClient marvelClient) {
        this.comicRepository = comicRepository;
        this.characterRepository = characterRepository;
        this.marvelClient = marvelClient;
    }

    public List<Comic> findByCharacter(Character character) {
        List<Comic> comics = null;
        if (character.isNeedUpdateHisComics()) {
            comics = updateComicsForThisCharacter(character);
        } else {
            comics = comicRepository.findByCharacters_id(character.getId());
        }

        return comics;
    }

    private List<Comic> updateComicsForThisCharacter(Character character) {
        List<Comic> comics;
        comics = new ArrayList<>();
        ComicResponse comicsResponse = null;
        int offset = 0;
        do {
            comicsResponse = marvelClient.findComicByCharacterId(character.getId(), "offset=" + offset + "&orderBy=title");
            comics.addAll(comicsResponse.getData().getResults().stream().map(ComicMarvelToComic::converter).collect(Collectors.toList()));
            offset = comicsResponse.getData().getOffset() + comicsResponse.getData().getCount();
        } while (comicsResponse.getCode() == 200 && offset < Constants.TOP_COMICS_BY_CHARACTER && offset < comicsResponse.getData().getTotal());

        comics.stream().forEach(comic -> {
            comicRepository.save(comic);
        });

        character.setLastSyncComics(new Date());
        characterRepository.save(character);


        return comics;
    }


}
