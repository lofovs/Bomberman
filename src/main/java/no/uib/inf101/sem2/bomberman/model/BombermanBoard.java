package no.uib.inf101.sem2.bomberman.model;

import no.uib.inf101.sem2.grid.CellPosition;
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
     * Gets the grid in string format
     * 
     * @return a string representation of the grid
     */
    public String prettyString() {
        String stringBoard = "";
        for (int i = 0; i < this.row; i++) {
            if (stringBoard != "") {
                stringBoard += "\n";
            }
            for (int j = 0; j < this.col; j++) {
                CellPosition pos = new CellPosition(i, j);
                Character value = get(pos);
                stringBoard += value;
            }
        }
        return stringBoard;
    }

    /**
     * Clears the board
     */
    public void clear() {
        super.fillGrid(this.row, this.col, this.defaultValue);
    }
}
