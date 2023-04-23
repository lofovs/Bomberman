package no.uib.inf101.sem2.bomberman.model.bomb;

/**
 * A factory for creating bombs
 */
public class BombFactory {

  /**
   * Creates a new bomb
   *
   * @return a new bomb
   */
  public Bomb createNewBomb() {
    return Bomb.newBomb();
  }
}
