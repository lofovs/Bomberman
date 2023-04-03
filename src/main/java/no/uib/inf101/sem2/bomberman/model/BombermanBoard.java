package no.uib.inf101.sem2.bomberman.model;

import no.uib.inf101.sem2.bomberman.model.bomb.Bomb;
import no.uib.inf101.sem2.bomberman.model.player.Player;
import no.uib.inf101.sem2.grid.CellPosition;
import no.uib.inf101.sem2.grid.Grid;
import no.uib.inf101.sem2.grid.GridDimension;

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

  public boolean canPlace(Player player) {
    CellPosition pos = player.getPos();
    if (
      pos.row() < 0 ||
      pos.row() >= this.row ||
      pos.col() < 0 ||
      pos.col() >= this.col
    ) {
      return false;
    } else if (get(pos) != '-') {
      System.out.println("FALSE!");
      return false;
    }
    return true;
  }

  public boolean canPlace(Bomb bomb) {
    CellPosition pos = bomb.getPos();
    if (
      pos.row() < 0 ||
      pos.row() >= this.row ||
      pos.col() < 0 ||
      pos.col() >= this.col
    ) {
      return false;
    } else if (get(pos) != '-') {
      return false;
    }
    return true;
  }

  /**
   * Spawns the player at the start of the game in the lower left corner of the
   * map
   *
   * @param gd the grid dimension of the board
   * @return the player
   */
  public Player spawn(GridDimension gd) {
    CellPosition pos = new CellPosition(this.rows() - 2, 1);
    return new Player(pos);
  }
}
