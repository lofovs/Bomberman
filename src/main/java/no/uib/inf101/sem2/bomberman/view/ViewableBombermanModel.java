package no.uib.inf101.sem2.bomberman.view;

import no.uib.inf101.sem2.grid.GridCell;
import no.uib.inf101.sem2.grid.GridDimension;

public interface ViewableBombermanModel {
  /**
   * Gets the dimension
   *
   * @return
   */
  GridDimension getDimension();

  /**
   * Get the grid of tiles on the board
   *
   * @return an iterable of grid cells
   */
  Iterable<GridCell<Character>> getTilesOnBoard();

  /**
   * Get the player tile
   *
   * @return an iterable of grid cells
   */
  Iterable<GridCell<Character>> getPlayerTile();

  /**
   * Get the bomb tile
   *
   * @return an iterable of grid cells
   */
  Iterable<GridCell<Character>> getBombTile();
}
