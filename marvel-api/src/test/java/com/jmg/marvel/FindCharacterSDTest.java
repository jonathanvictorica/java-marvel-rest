package com.jmg.marvel;

import com.jmg.marvel.client.MarvelClient;
import com.jmg.marvel.client.marvel.CharacterResponse;
import com.jmg.marvel.client.marvel.MarvelType;
import com.jmg.marvel.model.Character;
import com.jmg.marvel.core.FindCharacter;
import com.jmg.marvel.repository.CharacterRepository;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FindCharacterSDTest {
    private MarvelClient marvelClient = mock(MarvelClient.class);
    private CharacterRepository characterRepository= mock(CharacterRepository.class);

    @Test
    public void whenSpidermanDoesNotExistInBBDD_shouldGoToMarvelService() throws Exception {
       when(marvelClient.findCharacter("name=" + Constants.SPIDERMAN)).thenReturn(
                generateCharacterResponse(Constants.SPIDERMAN)
        );
        when(characterRepository.findByName(Constants.SPIDERMAN)).thenReturn(null);
        FindCharacter findCharacterSD = new FindCharacter(marvelClient, characterRepository);
        assertThat(findCharacterSD.getByName(Constants.SPIDERMAN)).hasFieldOrPropertyWithValue("name", Constants.SPIDERMAN);
    }

    @Test
    public void whenSpidermanExistInBBDD_notShouldGoToMarvelService() throws Exception {
       when(characterRepository.findByName(Constants.SPIDERMAN)).thenReturn(generateCharacterEntity(Constants.SPIDERMAN));
        FindCharacter findCharacterSD = new FindCharacter(marvelClient, characterRepository);
        assertThat(findCharacterSD.getByName(Constants.SPIDERMAN)).hasFieldOrPropertyWithValue("name", Constants.SPIDERMAN);
    }

    @Test
    public void whenSpidermanHasSynchronizationDateGreaterThan24Hours_ShouldGoToMarvelService() throws Exception {
         when(marvelClient.findCharacter("name=" + Constants.SPIDERMAN)).thenReturn(
                generateCharacterResponse(Constants.SPIDERMAN)
        );
        when(characterRepository.findByName(Constants.SPIDERMAN)).thenReturn(generateCharacterEntityWithLastSyncGrant24hs(Constants.SPIDERMAN));
        FindCharacter findCharacterSD = new FindCharacter(marvelClient, characterRepository);
        assertThat(findCharacterSD.getByName(Constants.SPIDERMAN)).hasFieldOrPropertyWithValue("name", Constants.SPIDERMAN);
    }

    private Character generateCharacterEntityWithLastSyncGrant24hs(String nameCharacter) throws ParseException {
        return Character.builder().id(1).name(nameCharacter).lastSync(new SimpleDateFormat("dd/MM/yyyy").parse("24/01/1996")).build();
    }

    private Character generateCharacterEntity(String nameCharacter) {
        return Character.builder().id(1).name(nameCharacter).lastSync(new Date()).build();
    }

    private CharacterResponse generateCharacterResponse(String nameCharacter) {
        return CharacterResponse.builder().code(200).status("OK").data(CharacterResponse.DataContainer.builder().count(1).offset(0).total(1).results(Arrays.asList(MarvelType.CharacterMarvel.builder().id(1).name(nameCharacter).build())).build()).build();
    }


}
