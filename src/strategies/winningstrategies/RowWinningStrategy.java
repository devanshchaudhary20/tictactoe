package strategies.winningstrategies;

import models.Board;
import models.Move;
import models.Symbol;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RowWinningStrategy implements WinningStrategy{
    private final Map<Integer, HashMap<Symbol, Integer>> counts = new HashMap<>();

    @Override
    public boolean checkWinner(Board board, Move move) {
        int row = move.getCell().getRow();
        Symbol symbol = move.getPlayer().getSymbol();

        if (!counts.containsKey(row)) {
            counts.put(row, new HashMap<>());
        }

        Map<Symbol, Integer> symbolCounts = counts.get(row);
        if (!symbolCounts.containsKey(symbol)) {
            symbolCounts.put(symbol, 0);
        }

        symbolCounts.put(symbol, symbolCounts.get(symbol) + 1);

        return symbolCounts.get(symbol).equals(board.getSize());
    }

    @Override
    public void handleUndo(Board board, Move move) {
        int row = move.getCell().getRow();
        Symbol symbol = move.getPlayer().getSymbol();

        Map<Symbol, Integer> symbolCounts = counts.get(row);

        symbolCounts.put(symbol, symbolCounts.get(symbol) - 1);
    }
}
