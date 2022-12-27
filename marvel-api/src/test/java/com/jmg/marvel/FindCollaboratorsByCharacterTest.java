package com.jmg.marvel;

import com.jmg.marvel.service.FindCollaboratorsByCharacter;
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

public class FindCollaboratorsByCharacterTest {

    private FindCharacter findCharacterSD = mock(FindCharacter.class);
    private FindComics findComicsSD = mock(FindComics.class);

    @Test
    public void WhenSpidermanHasComics_shouldReturnHisCollaborators() throws Exception {

        var spiderman = generateCharacterEntity(Constants.SPIDERMAN);
        when(findCharacterSD.getByName(Constants.SPIDERMAN)).thenReturn(
                spiderman
        );

        when(findComicsSD.findByCharacter(spiderman)).thenReturn(
                generateComicsForThis()
        );

        FindCollaboratorsByCharacter findCollaboratorsByCharacter = new FindCollaboratorsByCharacter(findCharacterSD, findComicsSD);

        FindCollaboratorsByCharacter.FindCollaboratorsByCharacterResponse result = findCollaboratorsByCharacter.execute(Constants.SPIDERMAN);

        assertEquals(4, result.getWriters().size());
        assertEquals(2, result.getColorists().size());
        assertEquals(3, result.getEditors().size());


    }

    private List<Comic> generateComicsForThis() {
        List<Comic> data = new ArrayList<>();


        data.add(Comic.builder().id(1).title(Constants.TitleJavaWithGO).writers(Constants.WRITE1).editors(Constants.EDITOR1).build());
        data.add(Comic.builder().id(2).title(Constants.TitleJava).writers(Constants.WRITE2).editors(Constants.EDITOR1).build());
        data.add(Comic.builder().id(3).title(Constants.TitleJava).writers(Constants.WRITE3).editors(Constants.EDITOR2).colorists(Constants.COLORIST1).build());
        data.add(Comic.builder().id(4).title(Constants.TitleJava).writers(Constants.WRITE4).editors(Constants.EDITOR3).colorists(Constants.COLORIST2).build());


        return data;
    }


    private Character generateCharacterEntity(String nameCharacter) {
        return Character.builder().id(1).name(nameCharacter).lastSync(new Date()).build();
    }
}
