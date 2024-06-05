package strategies.winningstrategies;

import models.Board;
import models.Move;
import models.Symbol;

import java.util.List;
import java.util.Map;

public class RowWinningStrategy implements WinningStrategy{
    private List<Map<Symbol, Integer>> symbolCount;
    @Override
    public boolean checkWinner(Board board, Move move) {
        return false;
    }
}
