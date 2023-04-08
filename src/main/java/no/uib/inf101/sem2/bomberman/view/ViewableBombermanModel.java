package no.uib.inf101.sem2.bomberman.view;

import no.uib.inf101.sem2.bomberman.model.GameState;
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

  /**
   * Get the player lives
   * @return the player lives
   */
  int getPlayerLives();

  /**
   * Get the AI lives
   * @return the AI lives
   */
  int getPlayer2Lives();

  /**
   * Get the AI 2 lives
   * @return the AI 2 lives
   */
  int getPlayer3Lives();

  /**
   * Get the AI 3 lives
   * @return the AI 3 lives
   */
  int getPlayer4Lives();

  /**
   * Get the game state
   * @return the game state
   */
  GameState getGameState();

  /**
   * Get the time
   * @return the time
   */
  String getTime();
}
