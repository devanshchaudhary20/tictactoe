import controllers.GameController;
import models.Bot;
import models.Game;
import models.Player;
import strategies.winningstrategies.WinningStrategy;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        GameController gameController = new GameController();
        System.out.println("GAME IS STARTING");
        try {
            int dimension = 3;
            List<Player> players = new ArrayList<>();
            players.add(new Player());
            players.add(new Bot());

            List<WinningStrategy> winningStrategies = new ArrayList<>();

            Game game = gameController.startGame(
                dimension,
                players,
                winningStrategies
            );
        }
        catch (Exception e){
            System.out.println("Somthing went wrong");
        }
    }
}