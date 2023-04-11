package no.uib.inf101.sem2.bomberman.controller;

import no.uib.inf101.sem2.bomberman.model.GameState;
import no.uib.inf101.sem2.bomberman.model.bomb.Bomb;
import no.uib.inf101.sem2.bomberman.model.player.IPlayer;
import no.uib.inf101.sem2.bomberman.model.player.Player;

public interface ControllableBombermanModel {
  /**
   * Place a bomb at the player's position.
   *
   * @return true if a bomb was placed, false otherwise
   */
  boolean placeBomb(IPlayer player, Bomb bomb);

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

  /**
   * Start a new game.
   */
  void newGame();

  /**
   * Get the player.
   * 
   * @return the player
   */
  Player getPlayer();

  /**
   * Get the player lives.
   * 
   * @return the player lives
   */
  int getPlayerLives();

  /**
   * Get the bomb.
   * 
   * @return the bomb
   */
  Bomb getBomb();

  /**
   * Get the second bomb.
   * 
   * @return the bomb
   */
  Bomb getBomb2();

  /**
   * Get the third bomb.
   * 
   * @return the bomb
   */
  Bomb getBomb3();

  /**
   * Get the fourth bomb.
   * 
   * @return the bomb
   */
  Bomb getBomb4();

  /**
   * Changes the player sprite depending on which direction the player is moving
   *
   * @param i the row
   * @param j the column
   */
  void changePlayerSprite(IPlayer player, int i, int j);
}
