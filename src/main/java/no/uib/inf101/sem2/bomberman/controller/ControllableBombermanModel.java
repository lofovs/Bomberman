package no.uib.inf101.sem2.bomberman.controller;

import no.uib.inf101.sem2.bomberman.model.GameState;
import no.uib.inf101.sem2.bomberman.model.bomb.Bomb;
import no.uib.inf101.sem2.bomberman.model.player.Player;
import no.uib.inf101.sem2.bomberman.model.player.PlayerAI;

public interface ControllableBombermanModel {
  /**
   * Place a bomb at the player's position.
   *
   * @return true if a bomb was placed, false otherwise
   */
  boolean placeBomb(Bomb bomb);

  /**
   * Move the player.
   *
   * @param deltaRow the row delta
   * @param deltaCol the column delta
   * @return true if the player was moved, false otherwise
   */
  boolean movePlayer(int deltaRow, int deltaCol);

  /**
   * Move the AI.
   *
   * @param playerAI the AI to move
   * @param deltaRow the row delta
   * @param deltaCol the column delta
   * @return true if the AI was moved, false otherwise
   */
  boolean moveAI(PlayerAI playerAI, int deltaRow, int deltaCol);

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
   * Get the number of bombs currently on the board.
   *
   * @return the number of bombs currently on the board
   */
  int getBombCount();

  /**
   * Get the player.
   * @return the player
   */
  Player getPlayer();

  /**
   * Get the AI.
   * @return the AI
   */
  PlayerAI getPlayerAI();

  /**
   * Get the second AI.
   * @return the AI
   */
  PlayerAI getPlayerAI2();
  /**
   * Get the third AI.
   * @return
   */
  PlayerAI getPlayerAI3();

  /**
   * Get the bomb.
   * @return the bomb
   */
  Bomb getBomb();

  /**
   * Get the second bomb.
   * @return the bomb
   */
  Bomb getBomb2();

  /**
   * Get the third bomb.
   * @return the bomb
   */
  Bomb getBomb3();

  /**
   * Get the fourth bomb.
   * @return the bomb
   */
  Bomb getBomb4();
}
