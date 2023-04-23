package no.uib.inf101.sem2.bomberman.view;

import no.uib.inf101.sem2.bomberman.model.GameState;
import no.uib.inf101.sem2.bomberman.model.player.Player;
import no.uib.inf101.sem2.grid.GridCell;
import no.uib.inf101.sem2.grid.GridDimension;

public interface ViewableBombermanModel {
  /**
   * Gets the dimension of the grid
   *
   * @return the dimension of the grid
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
  Iterable<GridCell<Character>> getPlayerTile(int playerNumber);

  /**
   * Get the bomb tile
   *
   * @return an iterable of grid cells
   */
  Iterable<GridCell<Character>> getBombTile(int playerNumber);

  /**
   * Get the game state
   * 
   * @return the game state
   */
  GameState getGameState();

  /**
   * Get the time
   * 
   * @return the time
   */
  String getTime();

  /**
   * Get the player with the given number
   * 
   * @return the player
   */
  Player getPlayer(int playerNumber);

  /**
   * Get all the players
   * 
   * @return the players
   */
  Player[] getPlayers();
}
