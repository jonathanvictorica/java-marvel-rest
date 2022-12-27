package com.jmg.marvel.client.marvel;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ComicResponse {

    private Integer code;
    private String status;
    private ComicResponse.DataContainer data;

    @Getter
    @Setter
    public static class DataContainer {
        private Integer offset;
        private Integer count;
        private Integer total;
        public List<MarvelType.ComicMarvel> results;


    }
}
