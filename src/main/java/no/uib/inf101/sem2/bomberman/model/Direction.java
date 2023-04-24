package no.uib.inf101.sem2.bomberman.model;

/**
 * The direction of a move.
 */
public enum Direction {
    UP, DOWN, LEFT, RIGHT;

    /**
     * Get the row delta for this direction.
     */
    public int getDeltaRow() {
        if (this == UP) {
            return -1;
        } else if (this == DOWN) {
            return 1;
        }
        return 0;
    }

    /**
     * Get the column delta for this direction.
     */
    public int getDeltaCol() {
        if (this == LEFT) {
            return -1;
        } else if (this == RIGHT) {
            return 1;
        }
        return 0;
    }
}
