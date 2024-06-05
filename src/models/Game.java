package models;

import exceptions.DuplicateSymbolException;
import exceptions.MoreThanOneBotException;
import exceptions.PlayerCountDimensionMismatchedException;
import strategies.winningstrategies.WinningStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
    private List<Player> players;
    private Board board;
    private List<Move> moves;
    private Player winner;
    private GameState gameState;
    private int nextMovePlayerIndex;
    private List<WinningStrategy> winningStrategies;

    public static Builder getBuilder() {
        return new Builder();
    }

    private Game(int dimension, List<Player> players, List<WinningStrategy> winningStrategies) {
        this.players = players;
        this.winningStrategies = winningStrategies;
        this.nextMovePlayerIndex = 0;
        this.gameState = GameState.IN_PROGRESS;
        this.board = new Board(dimension);
        this.moves = new ArrayList<>();
    }

    public static class Builder {
        private List<Player> players;
        private List<WinningStrategy> winningStrategies;
        private int dimension;

        private Builder() {
            this.players = new ArrayList<>();
            this.winningStrategies = new ArrayList<>();
            this.dimension = 0;
        }

        public Builder setDimension(int dimension) {
            this.dimension = dimension;
            return this;
        }

        public Builder setPlayers(List<Player> players) {
            this.players = players;
            return this;
        }


        public Builder setWinningStrategies(List<WinningStrategy> winningStrategies) {
            this.winningStrategies = winningStrategies;
            return this;
        }

        private void validateBotCounts() throws MoreThanOneBotException {
            int botCount = 0;

            for (Player player : players) {
                if (player.getPlayerType().equals(PlayerType.BOT)){
                    botCount+=1;
                }
            }

            if (botCount > 1){
                throw new MoreThanOneBotException();
            }
        }

        private void validateDimensionAndPlayers() throws PlayerCountDimensionMismatchedException {
            if (players.size()!=dimension-1){
                throw new PlayerCountDimensionMismatchedException();
            }
        }

        private void validateUniqueSymbolsForPlayers() throws DuplicateSymbolException {

            Map<Character, Integer> symbolCounts = new HashMap<>();

            for (Player player : players) {
                if (!symbolCounts.containsKey(player.getSymbol().getaChar())){
                    symbolCounts.put(player.getSymbol().getaChar(), 0);
                }

                symbolCounts.put(
                        player.getSymbol().getaChar(),
                        symbolCounts.get(player.getSymbol().getaChar())+1
                );

                if (symbolCounts.get(player.getSymbol().getaChar()) > 1){
                    throw new DuplicateSymbolException();
                }
            }
        }

        private void validate() throws DuplicateSymbolException, PlayerCountDimensionMismatchedException, MoreThanOneBotException {
                validateBotCounts();
                validateDimensionAndPlayers();
                validateUniqueSymbolsForPlayers();
        }

        public Game build() throws DuplicateSymbolException, PlayerCountDimensionMismatchedException, MoreThanOneBotException {
            try {
                validate();
            } catch (Exception e) {
                throw e;
            }
            return new Game(dimension, players, winningStrategies);
        }

    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public int getNextMovePlayerIndex() {
        return nextMovePlayerIndex;
    }

    public void setNextMovePlayerIndex(int nextMovePlayerIndex) {
        this.nextMovePlayerIndex = nextMovePlayerIndex;
    }

    public List<WinningStrategy> getWinningStrategies() {
        return winningStrategies;
    }

    public void setWinningStrategies(List<WinningStrategy> winningStrategies) {
        this.winningStrategies = winningStrategies;
    }
}
