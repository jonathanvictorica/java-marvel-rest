package com.jmg.marvel;

import com.jmg.marvel.service.FindCharactersByInteraction;
import com.jmg.marvel.model.Character;
import com.jmg.marvel.model.Comic;
import com.jmg.marvel.core.FindCharacter;
import com.jmg.marvel.core.FindComics;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FindCharactersByInteractionTest {

    private FindCharacter findCharacterSD = mock(FindCharacter.class);
    private FindComics findComicsSD= mock(FindComics.class);

    @Test
    public void WhenSpidermanHasComics_shouldReturnCharactersWithWhomHeInteracts() throws Exception {

        var spiderman = generateCharacterEntity(Constants.SPIDERMAN);

        when(findCharacterSD.getByName(Constants.SPIDERMAN)).thenReturn(
                spiderman
        );

        when(findComicsSD.findByCharacter(spiderman)).thenReturn(
                generateComicsForThis(Constants.SPIDERMAN_ID, Constants.SPIDERMAN)
        );

        FindCharactersByInteraction findCharactersByInteraction = new FindCharactersByInteraction(findCharacterSD, findComicsSD);

        FindCharactersByInteraction.FindCharactersByInteractionResponse result = findCharactersByInteraction.execute(Constants.SPIDERMAN);

        assertEquals(2,result.getCharacters().size());

        var goCharacter = result.getCharacters().stream().filter(character -> character.getCharacter().equals(Constants.GO)).findFirst().get();
        assertEquals(goCharacter.getComics().stream().anyMatch(s -> s.equals(Constants.TitleJavaWithGO)), true);

        var javaCharacter = result.getCharacters().stream().filter(character -> character.getCharacter().equals(Constants.JAVA)).findFirst().get();
        assertEquals(javaCharacter.getComics().stream().anyMatch(s -> s.equals(Constants.TitleJavaWithGO)), true);
        assertEquals(javaCharacter.getComics().stream().anyMatch(s -> s.equals(Constants.TitleJava)), true);

    }

    private List<Comic> generateComicsForThis(Integer id, String nameCharacter) {

        List<Comic> data = new ArrayList<>();
        List<Comic.CharacterComic> characterComics = new ArrayList<>();
        characterComics.add(new Comic.CharacterComic(Constants.GO_ID, Constants.GO));
        characterComics.add(new Comic.CharacterComic(Constants.JAVA_ID, Constants.JAVA));
        characterComics.add(new Comic.CharacterComic(id, nameCharacter));
        data.add(Comic.builder().id(1).title(Constants.TitleJavaWithGO).characters(characterComics).build());

        characterComics = new ArrayList<>();
        characterComics.add(new Comic.CharacterComic(Constants.JAVA_ID, Constants.JAVA));
        characterComics.add(new Comic.CharacterComic(id, nameCharacter));
        data.add(Comic.builder().id(2).title(Constants.TitleJava).characters(characterComics).build());


        return data;
    }


    private Character generateCharacterEntity(String nameCharacter) {
        return Character.builder().id(1).name(nameCharacter).lastSync(new Date()).build();
    }
}
