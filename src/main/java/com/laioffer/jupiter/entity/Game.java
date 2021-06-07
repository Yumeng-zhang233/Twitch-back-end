package com.laioffer.jupiter.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
//Finally add a few more annotations to the Game and
// Builder class to handle some edge cases.
@JsonIgnoreProperties(ignoreUnknown = true)//indicates that other fields in the response
//can be safely ignored.Without this, you will get an exception at runtime
@JsonInclude(JsonInclude.Include.NON_NULL)//indicates that null fields can be skipped
// and not inculded

@JsonDeserialize(builder = Game.Builder.class)//用builder来生成game的instance
// indicates thatJackson needs to use Game.Builder
// when constructing a Game object from JSON strings
//setter return type not void is for chaining
public class Game {
    //jsom snake case
    @JsonProperty("id")
    private final String id;

    @JsonProperty("name")
    private final String name;

    @JsonProperty("box_art_url")
    private final String boxArtUrl;

//Add a constructor to the Game class.
    private Game(Builder builder) {//private 外部只能用builder访问
        this.id = builder.id;
        this.name = builder.name;
        this.boxArtUrl = builder.boxArtUrl;
    }

//Add getters for the private fields of Game class.
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBoxArtUrl() {
        return boxArtUrl;
    }

/*
What’s the difference between with or without a builder pattern? Let’s compare:
Game game = new Game("12924", "World of Warcraft", "https://static-cdn.jtvnw.net/ttv-boxart/Warcraft%20III-{width}x{height}.jpg");
vs
Game game = new Game.Builder()
    .id("12924")
    .name("World of Warcraft")
    .boxArtUrl("[San Francisco](https://static-cdn.jtvnw.net/ttv-boxart/Warcraft%20III-{width}x{height}.jpg)")
    .build();
*/

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)

    public static class Builder {
        @JsonProperty("id")
        private String id;

        @JsonProperty("name")
        private String name;

        @JsonProperty("box_art_url")
        private String boxArtUrl;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder boxArtUrl(String boxArtUrl) {
            this.boxArtUrl = boxArtUrl;
            return this;
        }
        //this method initianates a new Game class
        public Game build() {
            return new Game(this);
        }
    }

}