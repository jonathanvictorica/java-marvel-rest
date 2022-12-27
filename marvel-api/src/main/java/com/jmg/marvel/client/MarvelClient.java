package com.jmg.marvel.client;

import com.jmg.marvel.client.marvel.CharacterResponse;
import com.jmg.marvel.client.marvel.ComicResponse;

public interface MarvelClient {


    CharacterResponse findCharacter(String... search);

    ComicResponse findComicByCharacterId(Integer characterId, String parameters);
}
