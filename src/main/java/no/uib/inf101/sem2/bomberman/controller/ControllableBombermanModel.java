package no.uib.inf101.sem2.bomberman.controller;

import no.uib.inf101.sem2.bomberman.model.GameState;

public interface ControllableBombermanModel {
  /**
   * Place a bomb at the player's position.
   *
   * @return true if a bomb was placed, false otherwise
   */
  boolean placeBomb();

  /**
   * Move the player.
   *
   * @param deltaRow the row delta
   * @param deltaCol the column delta
   * @return true if the player was moved, false otherwise
   */
  boolean movePlayer(int deltaRow, int deltaCol);

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
}
