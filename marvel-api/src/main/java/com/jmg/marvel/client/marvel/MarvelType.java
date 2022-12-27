package com.jmg.marvel.client.marvel;

import lombok.*;

import java.io.Serializable;
import java.util.List;

public class MarvelType {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class CharacterMarvel implements Serializable {
        private Integer id;
        private String name;
    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ComicMarvel implements Serializable {
        private Integer id;
        private String title;
        private DataContainerCollaborator creators;
        private DataContainerCharacter characters;


        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        public static class DataContainerCollaborator implements Serializable {
            public List<DataContainerCollaborator.Collaborator> items;

            @Getter
            @Setter
            @AllArgsConstructor
            @NoArgsConstructor
            public static class Collaborator implements Serializable {
                private String name;
                private String role;
            }
        }

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        public static class DataContainerCharacter implements Serializable {
            public List<DataContainerCharacter.Character> items;

            @Getter
            @Setter
            @AllArgsConstructor
            @NoArgsConstructor
            public static class Character implements Serializable {
                private String resourceURI;
                private String name;
            }
        }

    }
}
