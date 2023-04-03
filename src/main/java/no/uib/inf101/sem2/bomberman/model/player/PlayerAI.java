package no.uib.inf101.sem2.bomberman.model.player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import no.uib.inf101.sem2.grid.CellPosition;
import no.uib.inf101.sem2.grid.GridCell;

public class PlayerAI implements Iterable<GridCell<Character>>, IPlayer {

  private CellPosition pos;
  private Character symbol;
  private int lives;
  private int bombCount;

  public PlayerAI(CellPosition pos, Character symbol) {
    this.lives = 3;
    this.pos = pos;
    this.symbol = symbol;
    this.bombCount = 0;
  }

  /**
   * Move the AI in the given direction.
   * @param deltaRow
   * @param deltaCol
   * @return a new AI with the new position
   */
  public PlayerAI shiftedBy(int deltaRow, int deltaCol) {
    CellPosition newPos = new CellPosition(
      this.pos.row() + deltaRow,
      this.pos.col() + deltaCol
    );
    return new PlayerAI(newPos, this.symbol);
  }

  @Override
  public int getLives() {
    return this.lives;
  }

  @Override
  public CellPosition getPos() {
    return this.pos;
  }

  @Override
  public int getBombCount() {
    return this.bombCount;
  }

  @Override
  public Iterator<GridCell<Character>> iterator() {
    List<GridCell<Character>> list = new ArrayList<>();
    list.add(new GridCell<>(this.pos, this.symbol));
    return list.iterator();
  }
}
