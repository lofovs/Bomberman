package no.uib.inf101.sem2.bomberman.view;

import java.awt.Color;

public interface ColorTheme {
  /**
   * Get the color of a cell
   *
   * @param value the value of the cell
   * @return the color of the cell
   */
  Color getCellColor(Character value);

  /**
   * Get the color of the floor
   *
   * @return the color of the floor
   */
  Color getFloorColor();

  /**
   * Get the color of the frame
   *
   * @return the color of the frame
   */
  Color getFrameColor();

  /**
   * Get the color of the score board
   *
   * @return the color of the score board
   */
  Color getScoreBoardColor();

  /**
   * Get the color of the text in the score board
   *
   * @return the color of the text in the score board
   */
  Color getScoreBoardTextColor();

  /**
   * Get the color of the new game screen
   * 
   * @return the color of the new game screen
   */
  Color getNewGameColor();

  /**
   * Get the color of the new game text
   * 
   * @return the color of the new game text
   */
  Color getNewGameTextColor();

  /**
   * Get the color of the game won screen
   * 
   * @return the color of the game won screen
   */
  Color getWonGameColor();

  /**
   * Get the color of the game won text
   * 
   * @return the color of the game won text
   */
  Color getGameWonTextColor();

  /**
   * Get the color of the clock
   * 
   * @return the color of the clock
   */
  Color getClockColor();

  /**
   * Get the color of the text in the paused screen
   * 
   * @return the color of the text in the paused screen
   */
  Color getPausedTextColor();

  /**
   * Get the color of the transparent screen
   * 
   * @return the color of the transparent screen
   */
  Color getTransparentScreenColor();

  /**
   * Get the color of the text in the draw screen
   * 
   * @return the color of the text in the draw screen
   */
  Color getDrawTextColor();

  /**
   * Get the color of the play again text
   * 
   * @return the color of the play again text
   */
  Color getPlayAgainTextColor();
}
