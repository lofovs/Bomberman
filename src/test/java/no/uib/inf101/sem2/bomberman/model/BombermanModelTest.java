package no.uib.inf101.sem2.bomberman.model;

import org.junit.jupiter.api.Test;

import no.uib.inf101.sem2.bomberman.model.bomb.Bomb;
import no.uib.inf101.sem2.bomberman.model.player.IPlayer;
import no.uib.inf101.sem2.bomberman.model.player.Player;
import no.uib.inf101.sem2.grid.CellPosition;

public class BombermanModelTest {

    @Test
    public void testPlaceBomb() {
        // Set up a test board and players
        BombermanBoard board = new BombermanBoard(10, 10);
        IPlayer player1 = new Player(new CellPosition(0, 0));
        IPlayer player2 = new Player(new CellPosition(9, 9));
        BombermanModel model = new BombermanModel(board);

        // Test placing a bomb for player 1
        Bomb bomb1 = new Bomb(player1.getPos());
        assertTrue(model.placeBomb(player1, bomb1));
        assertEquals(1, model.getPlayerBombCount(player1));
        assertEquals(bomb1, model.getBombAt(player1.getPos()));

        // Test placing a second bomb for player 1
        Bomb bomb2 = new Bomb();
        assertFalse(game.placeBomb(player1, bomb2));
        assertEquals(1, game.getPlayerBombCount(player1));

        // Test placing a bomb for player 2
        Bomb bomb3 = new Bomb(player2.getPos(), 3);
        assertTrue(game.placeBomb(player2, bomb3));
        assertEquals(1, game.getPlayerBombCount(player2));
        assertEquals(bomb3, game.getBombAt(player2.getPos()));

        // Test placing a bomb out of bounds
        Bomb bomb4 = new Bomb(new Position(20, 20), 3);
        assertFalse(game.placeBomb(player1, bomb4));
        assertEquals(1, game.getPlayerBombCount(player1));
    }

}
