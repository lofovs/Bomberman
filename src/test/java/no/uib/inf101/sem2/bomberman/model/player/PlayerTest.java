package no.uib.inf101.sem2.bomberman.model.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import no.uib.inf101.sem2.grid.CellPosition;

public class PlayerTest {

    @Test
    public void testShiftPositionBy() {
        // Create a player object with position (2, 2)
        Player player = new HumanPlayer(new CellPosition(2, 2));

        // Shift the player by 1 row and 1 column
        Player shiftedPlayer = new HumanPlayer(new CellPosition(2, 2));
        shiftedPlayer.shiftPositionBy(1, 1);

        // Check that the new player has the correct position
        assertEquals(new CellPosition(3, 3), shiftedPlayer.getPos());
        assertNotEquals(new CellPosition(3, 3), player.getPos());
    }

    @Test
    public void testSetPosition() {
        CellPosition pos1 = new CellPosition(1, 2);
        CellPosition pos2 = new CellPosition(3, 4);
        Player player1 = new HumanPlayer(pos1);
        Player player2 = new HumanPlayer(pos1);
        Player player3 = new HumanPlayer(pos2);
        assertEquals(player1, player2);
        assertNotEquals(player1, player3);
    }

}
