package com.ashwin.ConnectFour.web;


import com.ashwin.ConnectFour.domain.Game;
import com.ashwin.ConnectFour.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/game")
public class GameController {

    @Autowired
    GameService gameService;

    @GetMapping("/START")
    public Long createNewGame()
    {
        Game game = new Game();
        Game gameReturned = gameService.saveOrUpdateGame(game);
        return gameReturned.getId();
    }

    @GetMapping("/{game_id}/printGame")
    public String printGame(@PathVariable Long game_id)
    {
       return gameService.printGame(game_id);
    }

    @GetMapping("/{game_id}/drop/{column}")
    public String dropCoin(@PathVariable Long game_id,@PathVariable int column)
    {
        if(column>=0 && column<=6) {
            return gameService.dropCoin(game_id, column);
        }
        else
            return "Invalid move, Please enter value between column 0 and column 6";
    }

}
