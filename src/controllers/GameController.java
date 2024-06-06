package controllers;

import exceptions.DuplicateSymbolException;
import exceptions.MoreThanOneBotException;
import exceptions.PlayerCountDimensionMismatchedException;
import models.Game;
import models.GameState;
import models.Player;
import strategies.winningstrategies.WinningStrategy;

import java.util.List;

public class GameController {
    private Game game;

    public Game startGame (int dimensionOfBoard, List<Player> players, List<WinningStrategy> winningStrategies) throws DuplicateSymbolException, PlayerCountDimensionMismatchedException, MoreThanOneBotException {
        return Game.getBuilder().setPlayers(players)
                .setDimension(dimensionOfBoard)
                .setWinningStrategies(winningStrategies)
                .build();
    }

    public void makeMove(Game game) {
        game.makeMove();
    }

    public GameState checkState(Game game) {
        return game.getGameState();
    }

    void undo(Game game) {

    }

    public Player getWinner(Game game) {
        return game.getWinner();
    }

    public void printBoard(Game game) {
        game.printBoard();
    }
}
