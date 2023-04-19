package no.uib.inf101.sem2.bomberman.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import no.uib.inf101.sem2.grid.CellPosition;

public class TestBombermanBoard {

    /**
     * Create a BombermanBoard with the given contents.
     * 
     * @param s The contents of the board, one string per row.
     * @return a BombermanBoard with the given contents.
     */
    public BombermanBoard getBombermanBoarddWithContents(String[] s) {
        BombermanBoard board = new BombermanBoard(s.length, s[0].length());
        for (int y = 0; y < s.length; y++) {
            for (int x = 0; x < s[y].length(); x++) {
                board.set(new CellPosition(y, x), s[y].charAt(x));
            }
        }
        return board;
    }

    @Test
    public void testPrettyString() {
        BombermanBoard board = new BombermanBoard(3, 4);
        board.set(new CellPosition(0, 0), 'g');
        board.set(new CellPosition(0, 3), 'y');
        board.set(new CellPosition(2, 0), 'r');
        board.set(new CellPosition(2, 3), 'b');
        String expected = String.join("\n", new String[] {
                "g--y",
                "----",
                "r--b"
        });
        assertEquals(expected, board.prettyString());
    }

    @Test
    public void testIsPotentialExplosion() {
        BombermanBoard board = getBombermanBoarddWithContents(new String[] {
                "----",
                "-B--",
                "----"
        });
        assertTrue(board.isPotentialExplosion(new CellPosition(1, 1)));
        assertFalse(board.isPotentialExplosion(new CellPosition(0, 0)));
        assertFalse(board.isPotentialExplosion(new CellPosition(2, 2)));
        assertTrue(board.isPotentialExplosion(new CellPosition(1, 2)));
        assertTrue(board.isPotentialExplosion(new CellPosition(1, 0)));
        assertTrue(board.isPotentialExplosion(new CellPosition(0, 1)));
        assertTrue(board.isPotentialExplosion(new CellPosition(2, 1)));
    }
}
