package com.jmg.marvel.client;

import com.jmg.marvel.client.MarvelClient;
import com.jmg.marvel.client.marvel.CharacterResponse;
import com.jmg.marvel.client.marvel.ComicResponse;
import com.jmg.marvel.utils.ClientRestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class MavenClientImpl implements MarvelClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${com.jmg.marvel.keyPrivate}")
    private String keyPrivate;
    @Value("${com.jmg.marvel.keyPublic}")
    private String keyPublic;
    @Value("${com.jmg.marvel.host}")
    private String host;

    @Value("${com.jmg.marvel.endpointGetCharacters}")
    private String endpointGetCharacters;

    @Value("${com.jmg.marvel.endpointGetComicByCharacterId}")
    private String endpointGetComicByCharacterId;


    private String getUrl(String endpoint, Object... parameters) {
        //timestamp + private key + api key
        String timestamp = new Date().getTime() + "";
        String valueHash = timestamp + keyPrivate + keyPublic;
        String md5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(valueHash);
        return host + endpoint + "?apikey=" + keyPublic + "&ts=" + timestamp + "&hash=" + md5 + Arrays.stream(parameters).map(param -> "&" + param).collect(Collectors.joining());
    }


    @Override
    public CharacterResponse findCharacter(String... search) {
        ResponseEntity<CharacterResponse> response =
                restTemplate.getForEntity(getUrl(endpointGetCharacters, search),
                        CharacterResponse.class);
        return response.getBody();
    }

    @Override
    public ComicResponse findComicByCharacterId(Integer characterId, String parameters) {
        ResponseEntity<ComicResponse> response =
                restTemplate.getForEntity(getUrl(ClientRestUtils.getPathUrl(endpointGetComicByCharacterId, characterId), parameters),
                        ComicResponse.class);
        return response.getBody();
    }
}
