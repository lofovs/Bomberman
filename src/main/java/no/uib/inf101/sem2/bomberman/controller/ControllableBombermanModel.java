package no.uib.inf101.sem2.bomberman.controller;

import no.uib.inf101.sem2.bomberman.model.GameState;
import no.uib.inf101.sem2.bomberman.model.player.Player;

/**
 * The interface for the controllable model.
 */
public interface ControllableBombermanModel {
  /**
   * Place a bomb at the player's position.
   *
   * @param player the player to place a bomb for
   * @return true if a bomb was placed, false otherwise
   */
  boolean placeBomb(Player player);

  /**
   * Move player1 by the given row and column.
   *
   * @param deltaRow the row delta
   * @param deltaCol the column delta
   * @return true if the player was moved, false otherwise
   */
  boolean movePlayer1(int deltaRow, int deltaCol);

  /**
   * Get the interval between clock ticks in milliseconds.
   *
   * @return the interval between clock ticks in milliseconds
   */
  int getTimerInterval();

  /**
   * Called when the clock ticks.
   */
  void clockTick();

  /**
   * Get the current game state.
   *
   * @return the current game state
   */
  GameState getGameState();

  /**
   * Pause the game.
   */
  void pauseGame();

  /**
   * Resume the game.
   */
  void playGame();

  /**
   * Start a new game.
   */
  void newGame();

  /**
   * Get the player with the given number.
   * 
   * @param playerNumber the player number
   * @return the player
   */
  Player getPlayer(int playerNumber);

  /**
   * Changes the player sprite depending on which direction the player is moving
   *
   * @param player   the player to change sprite for
   * @param deltaRow the row
   * @param deltaCol the column
   */
  void changePlayerSprite(Player player, int deltaRow, int deltaCol);

}
