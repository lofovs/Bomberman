package no.uib.inf101.sem2.bomberman.model.player;

import no.uib.inf101.sem2.grid.CellPosition;

public interface IPlayer {
  /**
   * Get the position of the player.
   * @return the position of the player
   */
  CellPosition getPos();

  /**
   * Move the player in the given direction.
   * @param deltaRow
   * @param deltaCol
   * @return a new player with the new position
   */
  IPlayer shiftedBy(int deltaRow, int deltaCol);

  /**
   * Move the player to the given position.
   * @param pos the new position
   * @return a new player with the new position
   */
  IPlayer shiftedToPosition(CellPosition pos);
}
