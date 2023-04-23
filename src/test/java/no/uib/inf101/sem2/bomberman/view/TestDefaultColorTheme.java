package no.uib.inf101.sem2.bomberman.view;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.Test;

class DefaultColorThemeTest {

    @Test
    void testGetCellColor() {
        ColorTheme theme = new DefaultColorTheme();

        // Test for a valid input
        assertEquals(Color.RED, theme.getCellColor('r'));

        // Test for an invalid input
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> theme.getCellColor('\0'));
        assertEquals("Argument cannot be null", exception.getMessage());
    }

    @Test
    void testGetFloorColor() {
        DefaultColorTheme theme = new DefaultColorTheme();
        assertEquals(Color.GREEN.darker(), theme.getFloorColor());
    }

    @Test
    void testGetFrameColor() {
        DefaultColorTheme theme = new DefaultColorTheme();
        assertEquals(Color.BLACK, theme.getFrameColor());
    }

    @Test
    void testGetScoreBoardColor() {
        DefaultColorTheme theme = new DefaultColorTheme();
        assertEquals(Color.ORANGE, theme.getScoreBoardColor());
    }

    @Test
    void testGetScoreBoardTextColor() {
        DefaultColorTheme theme = new DefaultColorTheme();
        assertEquals(Color.BLACK, theme.getScoreBoardTextColor());
    }

    @Test
    void testGetNewGameColor() {
        DefaultColorTheme theme = new DefaultColorTheme();
        assertEquals(Color.BLACK, theme.getNewGameColor());
    }

    @Test
    void testGetNewGameTextColor() {
        DefaultColorTheme theme = new DefaultColorTheme();
        assertEquals(Color.WHITE, theme.getNewGameTextColor());
    }

    @Test
    void testGetGameWonTextColor() {
        DefaultColorTheme theme = new DefaultColorTheme();
        assertEquals(Color.WHITE, theme.getGameWonTextColor());
    }

    @Test
    void testGetClockColor() {
        DefaultColorTheme theme = new DefaultColorTheme();
        assertEquals(Color.WHITE, theme.getClockColor());
    }

    @Test
    void testGetPausedTextColor() {
        DefaultColorTheme theme = new DefaultColorTheme();
        assertEquals(Color.WHITE, theme.getPausedTextColor());
    }

    @Test
    void testGetTransparentScreenColor() {
        DefaultColorTheme theme = new DefaultColorTheme();
        assertEquals(new Color(0, 0, 0, 128), theme.getTransparentScreenColor());
    }

    @Test
    void testGetDrawTextColor() {
        DefaultColorTheme theme = new DefaultColorTheme();
        assertEquals(Color.WHITE, theme.getDrawTextColor());
    }

    @Test
    void testGetPlayAgainTextColor() {
        DefaultColorTheme theme = new DefaultColorTheme();
        assertEquals(Color.WHITE, theme.getPlayAgainTextColor());
    }

}
