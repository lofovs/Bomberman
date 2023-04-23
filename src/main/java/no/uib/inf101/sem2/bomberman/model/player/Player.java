package no.uib.inf101.sem2.bomberman.model.player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import no.uib.inf101.sem2.bomberman.model.Direction;
import no.uib.inf101.sem2.bomberman.model.bomb.Bomb;
import no.uib.inf101.sem2.bomberman.model.bomb.BombFactory;
import no.uib.inf101.sem2.grid.CellPosition;
import no.uib.inf101.sem2.grid.GridCell;

/**
 * A class for representing a player
 */
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

  /**
   * Creates a new player at the given position
   *
   * @param pos the position of the player
   */
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

  /**
   * Gets the position of the player
   * 
   * @return the position of the player
   */
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

  /**
   * Shifts the player's position by the given amount
   * 
   * @param deltaRow the amount to shift the row by
   * @param deltaCol the amount to shift the column by
   */
  public void shiftPositionBy(int deltaRow, int deltaCol) {
    this.pos = this.pos.shiftedBy(deltaRow, deltaCol);
  }

  /**
   * Sets the position of the player
   * 
   * @param pos the new position of the player
   */
  public void setPosition(CellPosition pos) {
    this.pos = pos;
  }

  /**
   * Gets the player's bomb
   * 
   * @return the player's bomb
   */
  public Bomb getBomb() {
    return this.bomb;
  }

  /**
   * Sets the player's bomb
   * 
   * @param bomb the new bomb
   */
  public void setBomb(Bomb bomb) {
    this.bomb = bomb;
  }

  /**
   * Get the player's exploded bomb
   * 
   * @return the player's exploded bomb
   */
  public Bomb getExplodedBomb() {
    return this.explodedBomb;
  }

  /**
   * Sets the player's exploded bomb
   * 
   * @param explodedBomb the new exploded bomb
   */
  public void setExplodedBomb(Bomb explodedBomb) {
    this.explodedBomb = explodedBomb;
  }

  /**
   * Gets the player's explosion timer
   * 
   * @return the player's explosion timer
   */
  public int getExplosionTimer() {
    return this.explosionTimer;
  }

  /**
   * Increments the player's explosion timer
   */
  public void incrementExplosionTimer() {
    this.explosionTimer++;
  }

  /**
   * Resets the player's explosion timer
   */
  public void resetExplosionTimer() {
    this.explosionTimer = 0;
  }

  /**
   * Gets the player's bomb count
   * 
   * @return the player's bomb count
   */
  public int getBombCount() {
    return this.bombCount;
  }

  /**
   * Increments the player's bomb count
   */
  public void incrementBombCount() {
    this.bombCount++;
  }

  /**
   * Decrements the player's bomb count
   */
  public void decrementBombCount() {
    this.bombCount--;
  }

  /**
   * Gets the player's lives
   * 
   * @return the player's lives
   */
  public int getLives() {
    return this.lives;
  }

  /**
   * Decrements the player's lives
   */
  public void decrementLives() {
    this.lives--;
  }

  /**
   * Gets the player's sprite
   * 
   * @return the player's sprite
   */
  public Direction getSprite() {
    return this.sprite;
  }

  /**
   * Sets the player's sprite
   * 
   * @param sprite the new sprite
   */
  public void setSprite(Direction sprite) {
    this.sprite = sprite;
  }

  /**
   * Resets the player's bomb count
   */
  public void resetBombCount() {
    this.bombCount = 0;
  }

  /**
   * Resets the player's lives
   */
  public void resetLives() {
    this.lives = 3;
  }

  /**
   * Gets a new bomb from the bomb factory
   */
  public void getNewBomb() {
    this.bomb = this.bombFactory.createNewBomb();
  }

  /**
   * Gets the player's damage cooldown
   * 
   * @return the player's damage cooldown
   */
  public int getDamageCooldown() {
    return this.damageCoolDown;
  }

  /**
   * Decrements the player's damage cooldown
   */
  public void decrementDamageCooldown() {
    this.damageCoolDown--;
  }

  /**
   * Starts the player's damage cooldown
   */
  public void startDamageCooldown() {
    this.damageCoolDown = DAMAGE_COOLDOWN_DURATION;
  }
}
