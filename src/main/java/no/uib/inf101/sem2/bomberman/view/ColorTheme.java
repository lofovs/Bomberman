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
   * @return the color of the new game screen
   */
  Color getNewGameColor();

  /**
   * Get the color of the new game text
   * @return the color of the new game text
   */
  Color getNewGameTextColor();
}
