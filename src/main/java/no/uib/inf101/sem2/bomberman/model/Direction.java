package no.uib.inf101.sem2.bomberman.model;

public enum Direction {
    UP, DOWN, LEFT, RIGHT;

    public int getDeltaRow() {
        if (this == UP) {
            return -1;
        } else if (this == DOWN) {
            return 1;
        }
        return 0;
    }

    public int getDeltaCol() {
        if (this == LEFT) {
            return -1;
        } else if (this == RIGHT) {
            return 1;
        }
        return 0;
    }
}
