package no.uib.inf101.sem2.bomberman.model;

import no.uib.inf101.sem2.grid.CellPosition;
import no.uib.inf101.sem2.grid.Grid;

public class BombermanBoard extends Grid<Character> {

  private int row;
  private int col;

  /**
   * Creates a new BombermanBoard with the specified rows and cols
   *
   * @param row how many rows the this should have
   * @param col how many cols the this should have
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
   * Clears the board, filling it up with the chosen default value
   */
  public void clear() {
    super.fillGrid(this.row, this.col, this.defaultValue);
  }

  /**
   * Gets the number of rows in the this
   *
   * @return the number of rows in the this
   */
  public int getRows() {
    return this.row;
  }

  /**
   * Gets the number of cols in the this
   *
   * @return the number of cols in the this
   */
  public int getCols() {
    return this.col;
  }

  /**
   * Checks if the cell at the specified position is placeable
   * 
   * @param pos the position of the cell
   * @return true if the cell is placeable, false otherwise
   */
  boolean canPlace(CellPosition pos) {
    if (pos.row() < 0 ||
        pos.row() >= this.row ||
        pos.col() < 0 ||
        pos.col() >= this.col) {
      return false;
    } else if (get(pos) != '-' && get(pos) != 'E') {
      return false;
    }
    return true;
  }

  /**
   * Checks if the cell at the specified position is destructible
   * 
   * @param pos the position of the cell
   * @return true if the cell is destructible, false otherwise
   */
  boolean isDestructible(CellPosition pos) {
    if (get(pos) == 'X' || get(pos) == 'E' || get(pos) == '-' || get(pos) == 'B') {
      return true;
    }
    return false;
  }

  /**
   * Checks if the cell at the specified position is an explosion
   * 
   * @param pos the position of the cell
   * @return true if the cell is an explosion, false otherwise
   */
  boolean isExplosion(CellPosition pos) {
    return get(pos) == 'E';
  }

  private boolean isBomb(CellPosition pos) {
    if (positionIsOnGrid(pos)) {
      return get(pos) == 'B';
    }
    return false;
  }

  /**
   * Checks if the cell at the specified position will become an explosion
   * 
   * @param pos
   * @return
   */
  boolean isPotentialExplosion(CellPosition pos) {

    if (isBomb(pos)) {
      return true;
    }
    if (isExplosion(pos)) {
      return true;
    }
    // check if the immeditate neighbours are bombs
    else if (isBomb(new CellPosition(pos.row() + 1, pos.col())) ||
        isBomb(new CellPosition(pos.row() - 1, pos.col())) ||
        isBomb(new CellPosition(pos.row(), pos.col() + 1)) ||
        isBomb(new CellPosition(pos.row(), pos.col() - 1))) {
      // if immediate neighbours are bombs and the chosen cell is a bomb it will
      // consider it not dangerous
      if (get(pos) == 'B') {
        return false;
      }
      return true;
    }
    return false;
  }

  void createMap() {

    this.clear();

    // fill the outer walls with 'G'
    for (int i = 0; i < this.getRows(); i++) {
      for (int j = 0; j < this.getCols(); j++) {
        if (i == 0 ||
            i == this.getRows() - 1 ||
            j == 0 ||
            j == this.getCols() - 1) {
          this.set(new CellPosition(i, j), 'G');
        }
      }
    }

    // create a maze of walls
    for (int i = 0; i < this.getRows(); i++) {
      for (int j = 0; j < this.getCols(); j++) {
        if (i % 2 == 0 && j % 2 == 0) {
          this.set(new CellPosition(i, j), 'G');
        }
      }
    }

    // create random placements of 'X' within the outer walls
    for (int i = 0; i < this.getRows(); i++) {
      for (int j = 0; j < this.getCols(); j++) {
        if (this.get(new CellPosition(i, j)) == '-') {
          if (Math.random() < 0.2) {
            this.set(new CellPosition(i, j), 'X');
          }
        }
      }
    }

    // create empty tiles around the corners so the players can spawn safely
    this.set(new CellPosition(1, 1), '-');
    this.set(new CellPosition(1, 2), '-');
    this.set(new CellPosition(2, 1), '-');

    this.set(new CellPosition(1, this.getCols() - 2), '-');
    this.set(new CellPosition(1, this.getCols() - 3), '-');
    this.set(new CellPosition(2, this.getCols() - 2), '-');

    this.set(new CellPosition(this.getRows() - 2, 1), '-');
    this.set(new CellPosition(this.getRows() - 3, 1), '-');
    this.set(new CellPosition(this.getRows() - 2, 2), '-');

    this.set(new CellPosition(this.getRows() - 2, this.getCols() - 2), '-');
    this.set(new CellPosition(this.getRows() - 3, this.getCols() - 2), '-');
    this.set(new CellPosition(this.getRows() - 2, this.getCols() - 3), '-');
  }

}
