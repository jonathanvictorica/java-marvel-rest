package com.jmg.marvel.controller;


import com.jmg.marvel.service.FindCharactersByInteraction;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/characters")
public class CharacterController {


    private final  FindCharactersByInteraction findCharactersByInteraction;

    public CharacterController(FindCharactersByInteraction findCharactersByInteraction) {
        this.findCharactersByInteraction = findCharactersByInteraction;
    }


    @ApiOperation(value = "List the characters along with the comics where they interacted")
    @GetMapping("/{name}")
    public ResponseEntity<FindCharactersByInteraction.FindCharactersByInteractionResponse> getCharactersByInteraction(@PathVariable String name) throws Exception {
        return ResponseEntity.ok().body(findCharactersByInteraction.execute(name));
    }


}
