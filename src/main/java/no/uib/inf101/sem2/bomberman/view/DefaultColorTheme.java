package no.uib.inf101.sem2.bomberman.view;

import java.awt.Color;

public class DefaultColorTheme implements ColorTheme {

  @Override
  public Color getCellColor(Character value) {
    if (value == '\0') {
      throw new IllegalArgumentException("Argument cannot be null");
    }
    Color color = switch (value) {
      // colors for the grid cells
      case 'r' -> Color.RED;
      case 'b' -> Color.BLUE;
      case 'y' -> Color.YELLOW;
      case 'g' -> Color.GREEN;
      case 'p' -> Color.PINK;
      case 'm' -> Color.MAGENTA;
      case 'c' -> Color.CYAN;
      case 'o' -> Color.ORANGE;
      case 'G' -> Color.GRAY;
      case 'X' -> Color.DARK_GRAY;
      case 'W' -> Color.WHITE;
      case 'B' -> Color.BLACK;
      case 'E' -> Color.ORANGE;
      case '-' -> getFloorColor();
      default -> throw new IllegalArgumentException(
          "No available color for '" + value + "'");
    };
    return color;
  }

  @Override
  public Color getFloorColor() {
    return Color.GREEN.darker();
  }

  @Override
  public Color getFrameColor() {
    return Color.BLACK;
  }

  @Override
  public Color getScoreBoardColor() {
    return Color.ORANGE;
  }

  @Override
  public Color getScoreBoardTextColor() {
    return Color.BLACK;
  }

  @Override
  public Color getNewGameColor() {
    return Color.BLACK;
  }

  @Override
  public Color getNewGameTextColor() {
    return Color.WHITE;
  }

  @Override
  public Color getWonGameColor() {
    return new Color(0, 0, 0, 128);
  }

  @Override
  public Color getGameWonTextColor() {
    return Color.WHITE;
  }

  @Override
  public Color getClockColor() {
    return Color.WHITE;
  }

  @Override
  public Color getPausedTextColor() {
    return Color.WHITE;
  }

  @Override
  public Color getTransparentScreenColor() {
    return new Color(0, 0, 0, 128);
  }

  @Override
  public Color getDrawTextColor() {
    return Color.WHITE;
  }

  @Override
  public Color getPlayAgainTextColor() {
    return Color.WHITE;
  }
}
