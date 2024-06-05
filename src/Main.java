import controllers.GameController;
import models.*;
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
            players.add(
                    new Player(1L, new Symbol('X'), "Devansh", PlayerType.HUMAN)
            );
            players.add(
                    new Bot(2L, new Symbol('O'), "GPT", BotDifficultyLevel.MEDIUM)
            );

            List<WinningStrategy> winningStrategies = new ArrayList<>();

            Game game = gameController.startGame(
                dimension,
                players,
                winningStrategies
            );

            while (gameController.checkState(game).equals(GameState.IN_PROGRESS)){
                // 1. print the board
                // 2. x's turn
                // 3. ask to make move
                gameController.printBoard(game);
                gameController.makeMove(game);
            }
        }
        catch (Exception e){
            System.out.println("Somthing went wrong");
        }

        System.out.println("Game is Created!");
    }
}