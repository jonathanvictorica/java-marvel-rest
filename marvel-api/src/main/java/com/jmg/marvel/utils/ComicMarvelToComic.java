package com.jmg.marvel.utils;

import com.jmg.marvel.model.Comic;
import com.jmg.marvel.model.Constants;
import com.jmg.marvel.client.marvel.MarvelType;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ComicMarvelToComic {

    public static Comic converter(MarvelType.ComicMarvel comicResponse){
        Comic comicNew = new Comic(comicResponse.getId(), comicResponse.getTitle());
        comicNew.setColorists(filterCollaboratorByRole(comicResponse, Constants.COLORIST).map(MarvelType.ComicMarvel.DataContainerCollaborator.Collaborator::getName).collect(Collectors.toList()));
        comicNew.setEditors(filterCollaboratorByRole(comicResponse, Constants.EDITOR).map(MarvelType.ComicMarvel.DataContainerCollaborator.Collaborator::getName).collect(Collectors.toList()));
        comicNew.setWriters(filterCollaboratorByRole(comicResponse, Constants.WRITER).map(MarvelType.ComicMarvel.DataContainerCollaborator.Collaborator::getName).collect(Collectors.toList()));
        comicNew.setCharacters(comicResponse.getCharacters().getItems().stream().map(character -> {
            Integer id = Integer.parseInt(character.getResourceURI().split("/")[ character.getResourceURI().split("/").length-1]);
            return new Comic.CharacterComic(id,character.getName());
        }).collect(Collectors.toList()));

        return comicNew;
    }
    private static Stream<MarvelType.ComicMarvel.DataContainerCollaborator.Collaborator> filterCollaboratorByRole(MarvelType.ComicMarvel comic, String colorist) {
        return comic.getCreators().getItems().stream().filter(creator -> creator.getRole().equals(colorist));
    }


}
