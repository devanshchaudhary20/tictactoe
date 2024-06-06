package strategies.winningstrategies;

import models.Board;
import models.Move;
import models.Symbol;

import java.util.HashMap;
import java.util.Map;

public class ColumnWinningStrategy implements WinningStrategy{
    // In each col, each symbol is present how many times.
    private final Map<Integer, HashMap<Symbol, Integer>> counts = new HashMap<>();
    @Override
    public boolean checkWinner(Board board, Move move) {
        int col = move.getCell().getCol();
        Symbol symbol = move.getPlayer().getSymbol();

        if (!counts.containsKey(col)) {
            counts.put(col, new HashMap<>());
        }

        Map<Symbol, Integer> symbolCounts = counts.get(col);
        if (!symbolCounts.containsKey(symbol)) {
            symbolCounts.put(symbol, 0);
        }

        symbolCounts.put(symbol, symbolCounts.get(symbol) + 1);

        return symbolCounts.get(symbol).equals(board.getSize());
    }

    @Override
    public void handleUndo(Board board, Move move) {
        int col = move.getCell().getCol();
        Symbol symbol = move.getPlayer().getSymbol();

        Map<Symbol, Integer> symbolCounts = counts.get(col);

        symbolCounts.put(symbol, symbolCounts.get(symbol) - 1);
    }
}
