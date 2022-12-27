package com.jmg.marvel.client.marvel;


import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CharacterResponse {

    private Integer code;
    private String status;
    private CharacterResponse.DataContainer data;

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    public static class DataContainer {
        private Integer offset;
        private Integer count;
        private Integer total;
        public List<MarvelType.CharacterMarvel> results;


    }
}
