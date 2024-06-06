import controllers.GameController;
import models.*;
import strategies.winningstrategies.ColumnWinningStrategy;
import strategies.winningstrategies.DiagonalWinningStrategy;
import strategies.winningstrategies.RowWinningStrategy;
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

            List<WinningStrategy> winningStrategies = List.of(
                    new ColumnWinningStrategy(),
                    new RowWinningStrategy(),
                    new DiagonalWinningStrategy()
            );

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
            GameState gameState = gameController.checkState(game);
            System.out.println("Game is finished!");
            if (gameState.equals(GameState.DRAW)) {
                System.out.println("It is a Draw!");
            } else {
                System.out.println("Winner is " + gameController.getWinner(game).getName() + "!");
            }
        }
        catch (Exception e){
            System.out.println("Something went wrong");
        }
    }
}