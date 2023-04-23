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

    @Test
    void testMapBoundaries() {
        // check that the outer walls of the board are indestructible
        BombermanBoard board = new BombermanBoard(13, 11);
        board.createMap();
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                if (i == 0 || i == board.getRows() - 1 || j == 0 || j == board.getCols() - 1) {
                    assertEquals('G', board.get(new CellPosition(i, j)));
                }
            }
        }
    }

    @Test
    void testMaze() {
        // check that the maze is created with indestructible tiles
        BombermanBoard board = new BombermanBoard(13, 11);
        board.createMap();
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                if (i % 2 == 0 && j % 2 == 0) {
                    assertEquals('G', board.get(new CellPosition(i, j)));
                }
            }
        }
    }

    @Test
    void testDestructibleTiles() {
        // check that there are some destructible tiles created randomly inside the
        // board
        BombermanBoard board = new BombermanBoard(13, 11);
        board.createMap();
        boolean destructibleTilesExist = false;
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                if (board.get(new CellPosition(i, j)) == 'X') {
                    destructibleTilesExist = true;
                    break;
                }
            }
        }
        assertTrue(destructibleTilesExist);
    }

    @Test
    void testEmptyTiles() {
        // check that there are empty tiles created around the corners of the board
        BombermanBoard board = new BombermanBoard(13, 11);
        board.createMap();
        assertEquals('-', board.get(new CellPosition(1, 1)));
        assertEquals('-', board.get(new CellPosition(1, 2)));
        assertEquals('-', board.get(new CellPosition(2, 1)));

        assertEquals('-', board.get(new CellPosition(1, board.getCols() - 2)));
        assertEquals('-', board.get(new CellPosition(1, board.getCols() - 3)));
        assertEquals('-', board.get(new CellPosition(2, board.getCols() - 2)));

        assertEquals('-', board.get(new CellPosition(board.getRows() - 2, 1)));
        assertEquals('-', board.get(new CellPosition(board.getRows() - 3, 1)));
        assertEquals('-', board.get(new CellPosition(board.getRows() - 2, 2)));

        assertEquals('-', board.get(new CellPosition(board.getRows() - 2, board.getCols() - 2)));
        assertEquals('-', board.get(new CellPosition(board.getRows() - 3, board.getCols() - 2)));
        assertEquals('-', board.get(new CellPosition(board.getRows() - 2, board.getCols() - 3)));
    }
}
