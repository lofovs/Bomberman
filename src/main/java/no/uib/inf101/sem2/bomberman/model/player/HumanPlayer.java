package no.uib.inf101.sem2.bomberman.model.player;

import no.uib.inf101.sem2.grid.CellPosition;

/**
 * The class representing a human player.
 */
public class HumanPlayer extends Player {

    private int moveCount;

    /**
     * Creates a human player with a given position.
     * 
     * @param pos the position of the player
     */
    public HumanPlayer(CellPosition pos) {
        super(pos);
        this.symbol = 'W';
        this.moveCount = 0;
    }

    /**
     * Gets the number of moves the player has made.
     * 
     * @return the number of moves the player has made
     */
    public int getMoveCount() {
        return moveCount;
    }

    /**
     * Increments the number of moves the player has made.
     */
    public void incrementMoveCount() {
        this.moveCount++;
    }

    /**
     * Resets the number of moves the player has made.
     */
    public void resetMoveCount() {
        this.moveCount = 0;
    }
}
