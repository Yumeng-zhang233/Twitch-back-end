package com.laioffer.jupiter.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

//et’s first create the FavoriteRequestBody class
// which is used for deserializing add/remove favorite requests
public class FavoriteRequestBody {
    private final Item favoriteItem;
    @JsonCreator
    public FavoriteRequestBody (
            @JsonProperty("favorite") Item favoriteItem){
        this.favoriteItem = favoriteItem;
    }
    public Item getFavoriteItem(){
        return favoriteItem;
    }

}
