package com.ashwin.ConnectFour.services;


import com.ashwin.ConnectFour.domain.Game;
import com.ashwin.ConnectFour.exceptions.GameException;
import com.ashwin.ConnectFour.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import java.util.logging.Logger;

@Service
public class GameService {

    @Autowired
    GameRepository gameRepository;



    Logger logger =  Logger.getLogger(this.getClass().getName());

    /**
     * Used to save or update game
     * @param game Game to be saved or updated
     * @return Game Game object saved
     */
    public Game saveOrUpdateGame(Game game) {
        return gameRepository.save(game);
    }

    /**
     * Used to find game by ID
     * @param gameId Id of game to be found
     * @return Game Game object found
     */
    public Game findGameById(Long gameId) {
        try {
            Optional<Game> game = gameRepository.findById(gameId);
            return game.get();
        } catch (Exception e) {
            throw new GameException("Game with ID " + gameId + " not found");
        }
    }


    public String printGame(Long game_id) {
        Game gameById = findGameById(game_id);
        String[][] layout = gameById.getLayout();

        String s = formHtmlTable(game_id);

        StringBuilder sb = new StringBuilder();
        for (int i =0;i<layout.length;i++) {
            for (int j=0;j<layout[i].length;j++) {
//                sb.append(layout[i][j]);
                System.out.print(layout[i][j]);
            }
//            sb.append('\n');
            System.out.println();
        }
        return s;
    }

    public String[][] dropRedPattern(String[][] f, int c) {
        c = 2*c+1;
        for (int i =5;i>=0;i--) {
            if (f[i][c].equals(" ")) {
                f[i][c] = "R";
                break;
            }
        }
        return f;
    }

    public String[][] dropYellowPattern(String[][] f, int c) {
        c = 2*c+1;
        for (int i =5;i>=0;i--) {
            if (f[i][c].equals(" ")) {
                f[i][c] = "Y";
                break;
            }
        }
        return f;
    }

    public static String checkWinner(String[][] f) {
        for (int i =0;i<6;i++) {
            for (int j=0;j<7;j+=2) {
                if (( !f[i][j+1].equals(" ") )
                        && (!f[i][j+3].equals(" "))
                        && (!f[i][j+5].equals(" "))
                        && (!f[i][j+7].equals(" "))
                        && ((f[i][j+1] .equals( f[i][j+3]))
                        && (f[i][j+3] .equals( f[i][j+5]))
                        && (f[i][j+5] .equals( f[i][j+7]))))

                    return f[i][j+1];
            }
        }

        for (int i=1;i<15;i+=2) {
            for (int j =0;j<3;j++) {
                if((!f[j][i].equals(" "))
                        && (!f[j+1][i].equals(" "))
                        && (!f[j+2][i].equals(" "))
                        && (!f[j+3][i].equals(" "))
                        && ((f[j][i].equals( f[j+1][i]))
                        && (f[j+1][i].equals(f[j+2][i]))
                        && (f[j+2][i].equals(f[j+3][i]))))
                    return f[j][i];
            }
        }

        for (int i=0;i<3;i++) {

            for (int j=1;j<9;j+=2) {
                if((!f[i][j].equals(" "))
                        && (!f[i+1][j+2].equals(" "))
                        && (!f[i+2][j+4].equals(" "))
                        && (!f[i+3][j+6].equals(" "))
                        && ((f[i][j].equals(f[i+1][j+2]))
                        && (f[i+1][j+2].equals(f[i+2][j+4]))
                        && (f[i+2][j+4].equals(f[i+3][j+6]))))
                    return f[i][j];
            }
        }

        for (int i=0;i<3;i++) {
            for (int j=7;j<15;j+=2) {
                if((!f[i][j].equals(" "))
                        && (!f[i+1][j-2].equals(" "))
                        && (!f[i+2][j-4].equals(" "))
                        && (!f[i+3][j-6].equals(" "))
                        && ((f[i][j].equals(f[i+1][j-2]))
                        && (f[i+1][j-2].equals(f[i+2][j-4]))
                        && (f[i+2][j-4].equals(f[i+3][j-6]))))
                    return f[i][j];
            }
        }

        return null;
    }

    public String dropCoin(Long game_id,int c) {

        Game gameById = findGameById(game_id);
        String[][] f = gameById.getLayout();
        int count = gameById.getCount();

        if (count % 2 == 0)
            f = dropRedPattern(f,c);
        else
            f = dropYellowPattern(f,c);

        count++;
        gameById.setLayout(f);
        gameById.setCount(count);
        saveOrUpdateGame(gameById);
            printGame(game_id);
        String s = formHtmlTable(game_id);
        StringBuilder stringBuilder = new StringBuilder(s);
        if (checkWinner(f) != null) {
                if (checkWinner(f).equals("R")){
                    stringBuilder.append("\nRed won");
                    System.out.println("Red won.");
                } else if (checkWinner(f).equals("Y")) {
                    stringBuilder.append("\nYellow won");
                    System.out.println("Yellow won.");
                }
            }

        return stringBuilder.toString();

    }

    public String formHtmlTable(Long game_id) {
        StringBuilder ticketBuilder = new StringBuilder();
        ticketBuilder.append("<html>");
        ticketBuilder.append("<head>" +
                "<style>" +
                "table, th, td {" +
                "  border: 1px solid black;" +
                "  border-collapse: collapse;" +
                "}" +
                "th, td {" +
                "  padding: 5px;" +
                "  text-align: left;" +
                "}" +
                "</style>" +
                "</head>");
        ticketBuilder.append("<body>");
        ticketBuilder.append("<h2>"+"Game number is : "+game_id+"</h2>");
        ticketBuilder.append("<table>");

        Game gameById = findGameById(game_id);
        String[][] ticketValues = gameById.getLayout();

        for (int i =0;i<ticketValues.length;i++) {
            ticketBuilder.append("<tr>");
            for (int j =0;j<ticketValues[i].length;j++) {
                if (j% 2 != 0) {
                    ticketBuilder.append("<td>" + ticketValues[i][j] + "</td>");
                }

//                if (i==6) ticketValues[i][j]= "-";
            }
            ticketBuilder.append("</tr>");
        }

//        for (int i = 0; i < 5; i++) {
//            ticketBuilder.append("<tr>");
//            for (int j = 0; j < 5; j++) {
//                if (j% 2 != 0) {
//                    ticketBuilder.append("<td>" + ticketValues[i][j] + "</td>");
//                }
//            }
//            ticketBuilder.append("</tr>");
//        }

        ticketBuilder.append("</body></table></html>");
        return ticketBuilder.toString();
    }
}
