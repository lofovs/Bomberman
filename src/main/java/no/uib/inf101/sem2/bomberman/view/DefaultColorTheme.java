package no.uib.inf101.sem2.bomberman.view;

import java.awt.Color;

public class DefaultColorTheme implements ColorTheme {

  @Override
  public Color getCellColor(Character value) {
    if (value == '\0') {
      throw new IllegalArgumentException("Argument cannot be null");
    }
    Color color =
      switch (value) {
        // colors for the grid cells
        case 'r' -> Color.RED;
        case 'b' -> Color.BLUE;
        case 'y' -> Color.YELLOW;
        case 'g' -> Color.GREEN;
        case 'p' -> Color.PINK;
        case 'm' -> Color.MAGENTA;
        case 'c' -> Color.CYAN;
        case 'o' -> Color.ORANGE;
        // color for the indestructible walls
        case 'G' -> Color.GRAY;
        // color for the destructible walls
        case 'X' -> Color.DARK_GRAY;
        // color for the player
        case 'W' -> Color.WHITE;
        // color for the bombs
        case 'B' -> Color.BLACK;
        case 'E' -> Color.ORANGE;
        case '-' -> Color.GREEN.darker();
        default -> throw new IllegalArgumentException(
          "No available color for '" + value + "'"
        );
      };
    return color;
  }

  @Override
  public Color getFrameColor() {
    return Color.BLACK;
  }
}
