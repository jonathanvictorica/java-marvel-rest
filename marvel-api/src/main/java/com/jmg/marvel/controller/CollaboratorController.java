package com.jmg.marvel.controller;


import com.jmg.marvel.service.FindCollaboratorsByCharacter;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/collaborators")
public class CollaboratorController {

    private final FindCollaboratorsByCharacter findCollaboratorsByCharacter;

    public CollaboratorController(FindCollaboratorsByCharacter findCollaboratorsByCharacter) {
        this.findCollaboratorsByCharacter = findCollaboratorsByCharacter;
    }

    @ApiOperation(value = "List of colorists, editors and writers who participated in the comics of this character")
    @GetMapping("/{name}")
    public ResponseEntity<FindCollaboratorsByCharacter.FindCollaboratorsByCharacterResponse> getCollaborators(@PathVariable String name) throws Exception {
        return ResponseEntity.ok().body(findCollaboratorsByCharacter.execute(name));
    }


}
