package no.uib.inf101.sem2.bomberman.view;

import no.uib.inf101.sem2.bomberman.model.bomb.Bomb;
import no.uib.inf101.sem2.bomberman.model.player.PlayerAI;
import no.uib.inf101.sem2.grid.GridCell;
import no.uib.inf101.sem2.grid.GridDimension;

public interface ViewableBombermanModel {
  /**
   * Gets the dimension
   *
   * @return the dimension
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
   * Get the AI tile
   *
   * @return an iterable of grid cells
   */
  Iterable<GridCell<Character>> getPlayer2Tile();

  /**
   * Get the AI tile
   *
   * @return an iterable of grid cells
   */
  Iterable<GridCell<Character>> getPlayer3Tile();

  /**
   * Get the AI tile
   *
   * @return an iterable of grid cells
   */
  Iterable<GridCell<Character>> getPlayer4Tile();

  /**
   * Get the bomb tile
   *
   * @return an iterable of grid cells
   */
  Iterable<GridCell<Character>> getBombTile();

  /**
   * Get the bomb 2 tile
   *
   * @return an iterable of grid cells
   */
  Iterable<GridCell<Character>> getBomb2Tile();

  /**
   * Get the bomb 3 tile
   *
   * @return an iterable of grid cells
   */
  Iterable<GridCell<Character>> getBomb3Tile();

  /**
   * Get the bomb 4 tile
   *
   * @return an iterable of grid cells
   */
  Iterable<GridCell<Character>> getBomb4Tile();
}
