package no.uib.inf101.sem2.bomberman.model.player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import no.uib.inf101.sem2.bomberman.model.Direction;
import no.uib.inf101.sem2.bomberman.model.bomb.Bomb;
import no.uib.inf101.sem2.bomberman.model.bomb.BombFactory;
import no.uib.inf101.sem2.grid.CellPosition;
import no.uib.inf101.sem2.grid.GridCell;

public abstract class Player implements Iterable<GridCell<Character>> {

  private static final int DAMAGE_COOLDOWN_DURATION = 6;

  private CellPosition pos;
  protected Character symbol;
  private BombFactory bombFactory;
  protected Bomb bomb;
  protected Bomb explodedBomb;
  protected int explosionTimer;
  protected int bombCount;
  private int lives;
  private Direction sprite;
  private int damageCoolDown;

  public Player(CellPosition pos) {
    this.pos = pos;
    this.lives = 3;
    this.sprite = Direction.DOWN;
    this.bombFactory = new BombFactory();
    this.bomb = this.bombFactory.createNewBomb();
    this.explodedBomb = this.bombFactory.createNewBomb();
    this.explosionTimer = 0;
    this.bombCount = 0;
    this.damageCoolDown = 0;
  }

  public CellPosition getPos() {
    return this.pos;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((pos == null) ? 0 : pos.hashCode());
    result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
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
    Player other = (Player) obj;
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
    return true;
  }

  @Override
  public Iterator<GridCell<Character>> iterator() {
    List<GridCell<Character>> list = new ArrayList<>();
    list.add(new GridCell<>(this.pos, this.symbol));
    return list.iterator();
  }

  public void shiftPositionBy(int deltaRow, int deltaCol) {
    this.pos = this.pos.shiftedBy(deltaRow, deltaCol);
  }

  public void setPosition(CellPosition pos) {
    this.pos = pos;
  }

  public Bomb getBomb() {
    return this.bomb;
  }

  public void setBomb(Bomb bomb) {
    this.bomb = bomb;
  }

  public Bomb getExplodedBomb() {
    return this.explodedBomb;
  }

  public void setExplodedBomb(Bomb explodedBomb) {
    this.explodedBomb = explodedBomb;
  }

  public int getExplosionTimer() {
    return this.explosionTimer;
  }

  public void incrementExplosionTimer() {
    this.explosionTimer++;
  }

  public void resetExplosionTimer() {
    this.explosionTimer = 0;
  }

  public int getBombCount() {
    return this.bombCount;
  }

  public void incrementBombCount() {
    this.bombCount++;
  }

  public void decrementBombCount() {
    this.bombCount--;
  }

  public int getLives() {
    return this.lives;
  }

  public void decrementLives() {
    this.lives--;
  }

  public Direction getSprite() {
    return this.sprite;
  }

  public void setSprite(Direction sprite) {
    this.sprite = sprite;
  }

  public void resetBombCount() {
    this.bombCount = 0;
  }

  public void resetLives() {
    this.lives = 3;
  }

  public void getNewBomb() {
    this.bomb = this.bombFactory.createNewBomb();
  }

  public int getDamageCooldown() {
    return this.damageCoolDown;
  }

  public void decrementDamageCooldown() {
    this.damageCoolDown--;
  }

  public void startDamageCooldown() {
    this.damageCoolDown = DAMAGE_COOLDOWN_DURATION;
  }
}
