package no.uib.inf101.sem2.bomberman.model;

import no.uib.inf101.sem2.grid.Grid;

public class BombermanBoard extends Grid<Character> {

    private int row;
    private int col;

    /**
     * Creates a new BombermanBoard with the specified rows and cols
     * 
     * @param row how many rows the board should have
     * @param col how many cols the board should have
     */
    public BombermanBoard(int row, int col) {
        super(row, col);
        this.row = row;
        this.col = col;
        this.defaultValue = '-';
        this.clear();

    }

    /**
     * Clears the board
     */
    public void clear() {
        super.fillGrid(this.row, this.col, this.defaultValue);
    }
}
