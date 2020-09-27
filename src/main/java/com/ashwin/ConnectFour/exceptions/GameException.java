package com.ashwin.ConnectFour.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * <h1>GameException</h1>
 * Exception related specifically to the game
 * @author  Ashwin Raghunath
 * @version 1.0
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GameException extends RuntimeException{

    public GameException(String message)
    {
        super(message);
    }

}
