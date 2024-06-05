package controllers;

import exceptions.DuplicateSymbolException;
import exceptions.MoreThanOneBotException;
import exceptions.PlayerCountDimensionMismatchedException;
import models.Game;
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

    void makeMove(Game game) {

    }

    void checkState(Game game) {

    }

    void undo(Game game) {

    }

    void getWinner(Game game) {

    }

    void printBoard(Game game) {

    }
}
