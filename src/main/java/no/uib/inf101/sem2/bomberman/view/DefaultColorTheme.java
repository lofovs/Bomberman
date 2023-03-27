package no.uib.inf101.sem2.bomberman.view;

import java.awt.Color;

public class DefaultColorTheme implements ColorTheme {

    @Override
    public Color getCellColor(Character value) {
        if (value == '\0') {
            throw new IllegalArgumentException(
                    "Argument cannot be null");
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

            case '-' -> Color.BLACK;

            default -> throw new IllegalArgumentException(
                    "No available color for '" + value + "'");
        };
        return color;
    }

    @Override
    public Color getFrameColor() {
        return Color.GRAY;
    }

}
