package com.jmg.marvel.model;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comic {

    @Id
    private Integer id;
    private String title;
    private List<String> editors;
    private List<String> writers;
    private List<String> colorists;
    private List<CharacterComic> characters;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CharacterComic {
        private Integer id;
        private String name;
    }

    public Comic(Integer id, String title) {
        this.id = id;
        this.title = title;
    }


    public static List<String> filterComicByCharacter(List<Comic> comics, final Character character) {
        return comics.stream().filter(comic -> comic.isCharacterInComic(character)).map(Comic::getTitle).collect(Collectors.toList());
    }

    public boolean isCharacterInComic(Character character) {
        return characters != null && characters.stream().anyMatch(character1 -> character.getId().equals(character1.getId()));
    }


}
