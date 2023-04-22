package no.uib.inf101.sem2.bomberman.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import no.uib.inf101.sem2.bomberman.model.player.Player;
import no.uib.inf101.sem2.grid.CellPosition;

public class TestBombermanModel {

    @Test
    public void testPlaceBomb() {
        // Set up a test board and players
        BombermanBoard board = new BombermanBoard(10, 10);
        BombermanModel model = new BombermanModel(board);
        Player player1 = model.getPlayer(1);

        // Test placing a bomb for player 1
        assertTrue(model.placeBomb(player1));
        assertEquals(1, player1.getBombCount());
        assertEquals(player1.getPos(), player1.getBomb().getPos());

        // Test placing a second bomb for player 1
        model.movePlayer(-1, 0);
        assertFalse(model.placeBomb(player1));
        assertEquals(1, player1.getBombCount());
    }

    @Test
    public void testExplodeBomb() {
        // create a new game with a board that has destructible tiles
        BombermanBoard board = new BombermanBoard(10, 10);
        BombermanModel model = new BombermanModel(board);

        board.clear();

        // place a bomb and explode it
        Player player1 = model.getPlayer(1);
        model.placeBomb(player1);

        // explode the bomb and check that the surrounding tiles have turned to 'E'
        model.explodeBomb(player1);

        board = model.getBoard();

        assertEquals('E', board.get(new CellPosition(9, 1)));
        assertEquals('E', board.get(new CellPosition(8, 1)));
        assertEquals('E', board.get(new CellPosition(8, 0)));
        assertEquals('E', board.get(new CellPosition(8, 2)));
        assertEquals('E', board.get(new CellPosition(7, 1)));

        // check that the bomb has been removed from the board and that the bomb has
        // been replaced by the exploded bomb
        assertNotEquals(player1.getExplodedBomb().getPos(), player1.getBomb().getPos());
        assertEquals(player1.getExplodedBomb().getPos(), player1.getPos());
        assertEquals(player1.getBomb().getPos(), new CellPosition(-1, -1));

        // check that the bomb has

    }

}
