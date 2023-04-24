package no.uib.inf101.sem2.bomberman.model.bomb;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import no.uib.inf101.sem2.grid.CellPosition;
import no.uib.inf101.sem2.grid.GridCell;

/**
 * A class for representing a bomb
 */
public class Bomb implements Iterable<GridCell<Character>> {

  private CellPosition pos;
  private Character symbol;
  private int clock;

  /**
   * Creates a new bomb at the given position
   *
   * @param pos the position of the bomb
   */
  public Bomb(CellPosition pos) {
    this.pos = pos;
    this.symbol = 'B';
    this.clock = 0;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((pos == null) ? 0 : pos.hashCode());
    result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
    result = prime * result + clock;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Bomb other = (Bomb) obj;
    if (pos == null) {
      if (other.pos != null)
        return false;
    } else if (!pos.equals(other.pos))
      return false;
    if (symbol == null) {
      if (other.symbol != null)
        return false;
    } else if (!symbol.equals(other.symbol))
      return false;
    if (clock != other.clock)
      return false;
    return true;
  }

  /**
   * Returns the position of the bomb
   *
   * @return the position of the bomb
   */
  public CellPosition getPos() {
    return this.pos;
  }

  /**
   * Moves the bomb to the given position
   *
   * @param pos
   * @return a new Bomb object with the new position
   */
  public Bomb shiftedToPosition(CellPosition pos) {
    return new Bomb(pos);
  }

  @Override
  public Iterator<GridCell<Character>> iterator() {
    List<GridCell<Character>> list = new ArrayList<>();
    list.add(new GridCell<>(this.pos, this.symbol));
    return list.iterator();
  }

  /**
   * Creates a new bomb with a position outside the board
   *
   * @return a new bomb
   */
  static Bomb newBomb() {
    CellPosition pos = new CellPosition(-1, -1);
    return new Bomb(pos);
  }

  /**
   * Returns the clock of the bomb
   *
   * @return the clock of the bomb
   */
  public int getClock() {
    return this.clock;
  }

  /**
   * Increases the clock of the bomb by one
   */
  public void tick() {
    this.clock++;
  }

  /**
   * Returns the symbol of the bomb
   *
   * @return the symbol of the bomb
   */
  char getSymbol() {
    return this.symbol;
  }
}
