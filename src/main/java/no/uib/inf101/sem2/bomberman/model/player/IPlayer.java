package no.uib.inf101.sem2.bomberman.model.player;

import no.uib.inf101.sem2.grid.CellPosition;

public interface IPlayer {
  /**
   * Get the number of lives the player has left.
   * @return the number of lives the player has left
   */
  int getLives();

  /**
   * Get the position of the player.
   * @return the position of the player
   */
  CellPosition getPos();
}
