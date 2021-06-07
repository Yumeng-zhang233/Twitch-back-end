package com.laioffer.jupiter.external;
// Create a class named TwitchException.
// We’ll throw this exception if there is something wrong
// when calling Twitch API.
public class TwitchException extends RuntimeException {
    public TwitchException(String errorMessage){
        super(errorMessage);
    }
}
