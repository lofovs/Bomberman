package no.uib.inf101.sem2.bomberman.model.player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import no.uib.inf101.sem2.grid.CellPosition;
import no.uib.inf101.sem2.grid.GridCell;

public class Player implements Iterable<GridCell<Character>>, IPlayer {

  private int lives;
  private CellPosition pos;
  private Character symbol;
  private int bombCount;

  public Player(CellPosition pos) {
    this.pos = pos;
    this.symbol = 'W';
  }

  @Override
  public CellPosition getPos() {
    return this.pos;
  }

  @Override
  public Iterator<GridCell<Character>> iterator() {
    List<GridCell<Character>> list = new ArrayList<>();
    list.add(new GridCell<>(this.pos, this.symbol));
    return list.iterator();
  }

  @Override
  public Player shiftedBy(int deltaRow, int deltaCol) {
    CellPosition newPos = new CellPosition(
      this.pos.row() + deltaRow,
      this.pos.col() + deltaCol
    );
    return new Player(newPos);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + lives;
    result = prime * result + ((pos == null) ? 0 : pos.hashCode());
    result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
    result = prime * result + bombCount;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Player other = (Player) obj;
    if (lives != other.lives) return false;
    if (pos == null) {
      if (other.pos != null) return false;
    } else if (!pos.equals(other.pos)) return false;
    if (symbol == null) {
      if (other.symbol != null) return false;
    } else if (!symbol.equals(other.symbol)) return false;
    if (bombCount != other.bombCount) return false;
    return true;
  }

  @Override
  public Player shiftedToPosition(CellPosition pos) {
    return new Player(pos);
  }
}
