package no.uib.inf101.sem2.bomberman.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import no.uib.inf101.sem2.bomberman.model.bomb.Bomb;
import no.uib.inf101.sem2.bomberman.model.player.IPlayer;
import no.uib.inf101.sem2.grid.CellPosition;

public class TestBombermanModel {

    @Test
    public void testPlaceBomb() {
        // Set up a test board and players
        BombermanBoard board = new BombermanBoard(10, 10);
        BombermanModel model = new BombermanModel(board);
        IPlayer player1 = model.getPlayer();

        // Test placing a bomb for player 1
        Bomb bomb = model.getBomb();
        assertTrue(model.placeBomb(player1, bomb));
        assertEquals(1, model.getPlayerBombCount(player1));
        assertEquals(model.getBomb().getPos(), player1.getPos());

        // Test placing a second bomb for player 1
        Bomb bomb2 = new Bomb(player1.getPos());
        model.movePlayer(-1, 0);
        assertFalse(model.placeBomb(model.getPlayer(), bomb2));
        assertEquals(1, model.getPlayerBombCount(model.getPlayer()));
    }

    @Test
    public void testExplodeBomb() {
        // create a new game with a board that has destructible tiles
        BombermanBoard board = new BombermanBoard(10, 10);
        BombermanModel model = new BombermanModel(board);

        board.clear();

        // place a bomb and explode it
        IPlayer player = model.getPlayer();
        Bomb bomb = model.getBomb();
        model.placeBomb(player, bomb);

        // explode the bomb and check that the surrounding tiles have turned to 'E'
        model.explodeBomb(model.getBomb());

        board = model.getBoard();

        assertEquals('E', board.get(new CellPosition(9, 1)));
        assertEquals('E', board.get(new CellPosition(8, 1)));
        assertEquals('E', board.get(new CellPosition(8, 0)));
        assertEquals('E', board.get(new CellPosition(8, 2)));
        assertEquals('E', board.get(new CellPosition(7, 1)));

        // check that the bomb has been removed from the board and that the bomb has
        // been replaced by the exploded bomb
        assertNotEquals(model.getExplodedBomb().getPos(), model.getBomb().getPos());
        assertEquals(model.getExplodedBomb().getPos(), model.getPlayer().getPos());
        assertEquals(model.getBomb().getPos(), new CellPosition(-1, -1));

        // check that the bomb has

    }

}
