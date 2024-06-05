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
            this.dimension = 3;
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

    public void printBoard() {
        board.printBoard();
    }

    private boolean validateMove(Move move) {
        int row = move.getCell().getRow();
        int col = move.getCell().getCol();

        if (row>=board.getSize() || col>=board.getSize()){
            return false;
        }

        if (board.getBoard().get(row).get(col).getCellState().equals(CellState.EMPTY)){
            return true;
        }
        return false;
    }

    private boolean checkWinner(Board board, Move move) {
        for (WinningStrategy winningStrategy : winningStrategies) {
            if (winningStrategy.checkWinner(board, move)){
                return true;
            }
        }
        return false;
    }

    public void makeMove() {
        Player currentMovePlayer = players.get(nextMovePlayerIndex);
        System.out.println("It is " + currentMovePlayer.getName() + "'s turn. Please make your move!");
        Move move = currentMovePlayer.makeMove(board);
        System.out.println(currentMovePlayer.getName() + " has made a move at row: "+ move.getCell().getRow() + " and col: "+ move.getCell().getCol());

        if (!validateMove(move)){
            System.out.println("Please try again with a valid move!");
            return;
        }
        int row = move.getCell().getRow();
        int col = move.getCell().getCol();

        Cell cellToChange = board.getBoard().get(row).get(col);
        cellToChange.setCellState(CellState.FILLED);
        cellToChange.setPlayer(currentMovePlayer);
        Move finalMoveObject = new Move(cellToChange, currentMovePlayer);
        moves.add(finalMoveObject);

        nextMovePlayerIndex += 1;
        nextMovePlayerIndex %= players.size();

        if (checkWinner(board, finalMoveObject)) {
            gameState = GameState.WIN;
            winner = currentMovePlayer;
        }

        if (moves.size() == this.board.getSize()*this.board.getSize()){
            gameState = GameState.DRAW;
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
