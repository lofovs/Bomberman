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

  /**
   * Gets the number of rows in the board
   *
   * @return the number of rows in the board
   */
  public int getRows() {
    return this.row;
  }

  /**
   * Gets the number of cols in the board
   *
   * @return the number of cols in the board
   */
  public int getCols() {
    return this.col;
  }

  /**
   * Checks if the cell at the specified position is placeable
   * @param pos the position of the cell
   * @return true if the cell is placeable, false otherwise
   */
  boolean canPlace(CellPosition pos) {
    if (
      pos.row() < 0 ||
      pos.row() >= this.row ||
      pos.col() < 0 ||
      pos.col() >= this.col
    ) {
      return false;
    } else if (get(pos) != '-' && get(pos) != 'E') {
      return false;
    }
    return true;
  }

  /**
   * Checks if the cell at the specified position is destructible
   * @param pos the position of the cell
   * @return true if the cell is destructible, false otherwise
   */
  boolean isDestructible(CellPosition pos) {
    if (get(pos) == 'X' || get(pos) == 'E' || get(pos) == '-') {
      return true;
    }
    return false;
  }

  /**
   * Checks if the cell at the specified position is an explosion
   * @param pos the position of the cell
   * @return true if the cell is an explosion, false otherwise
   */
  boolean isExplosion(CellPosition pos) {
    return get(pos) == 'E';
  }
}
