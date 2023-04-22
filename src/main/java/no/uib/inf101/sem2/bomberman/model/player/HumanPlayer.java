package no.uib.inf101.sem2.bomberman.model.player;

import no.uib.inf101.sem2.grid.CellPosition;

public class HumanPlayer extends Player {

    private int moveCount;

    public HumanPlayer(CellPosition pos) {
        super(pos);
        this.symbol = 'W';
        this.moveCount = 0;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void incrementMoveCount() {
        this.moveCount++;
    }

    public void resetMoveCount() {
        this.moveCount = 0;
    }
}
