package strategies.winningstrategies;

import models.Board;
import models.Move;
import models.Symbol;

import java.util.HashMap;
import java.util.Map;

public class DiagonalWinningStrategy implements WinningStrategy{
    private Map<Symbol, Integer> leftDiagonalCounts = new HashMap<>();
    private Map<Symbol, Integer> rightDiagonalCounts = new HashMap<>();

    @Override
    public boolean checkWinner(Board board, Move move) {
        int row = move.getCell().getRow();
        int col = move.getCell().getCol();

        Symbol symbol = move.getPlayer().getSymbol();

        //check for left diagonal, i==j
        if (row==col){
            if (!leftDiagonalCounts.containsKey(symbol)) {
                leftDiagonalCounts.put(symbol, 0);
            }
            leftDiagonalCounts.put(symbol, leftDiagonalCounts.get(symbol) + 1);
        }

        //check for right diagonal, i+j=size-1

        if (row+col==board.getSize()-1){
            if (!rightDiagonalCounts.containsKey(symbol)) {
                rightDiagonalCounts.put(symbol, 0);
            }
            rightDiagonalCounts.put(symbol, rightDiagonalCounts.get(symbol) + 1);
        }
        if (row==col){
            if (leftDiagonalCounts.get(symbol).equals(board.getSize())){
                return true;
            }
        }

        if (row+col==board.getSize()-1){
            if (rightDiagonalCounts.get(symbol).equals(board.getSize())){
                return true;
            }
        }

        return false;
    }

    @Override
    public void handleUndo(Board board, Move move) {
        int row = move.getCell().getRow();
        int col = move.getCell().getCol();

        Symbol symbol = move.getPlayer().getSymbol();

        //check for left diagonal, i==j
        if (row==col){
            leftDiagonalCounts.put(symbol, leftDiagonalCounts.get(symbol) - 1);
        }

        //check for right diagonal, i+j=size-1
        if (row+col==board.getSize()-1){
            rightDiagonalCounts.put(symbol, rightDiagonalCounts.get(symbol) - 1);
        }
    }
}
