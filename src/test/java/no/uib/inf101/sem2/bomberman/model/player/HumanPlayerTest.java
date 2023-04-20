package no.uib.inf101.sem2.bomberman.model.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import no.uib.inf101.sem2.grid.CellPosition;

public class HumanPlayerTest {

    @Test
    public void testShiftedBy() {
        // Create a player object with position (2, 2)
        Player player = new HumanPlayer(new CellPosition(2, 2));

        // Shift the player by 1 row and 1 column
        Player newPlayer = player.shiftedBy(1, 1);

        // Check that the new player has the correct position
        assertEquals(3, newPlayer.getPos().row());
        assertEquals(3, newPlayer.getPos().col());
    }

    @Test
    public void testHashCode() {
        CellPosition pos1 = new CellPosition(1, 2);
        CellPosition pos2 = new CellPosition(3, 4);
        Player player1 = new HumanPlayer(pos1);
        Player player2 = new HumanPlayer(pos1);
        Player player3 = new HumanPlayer(pos2);
        assertEquals(player1.hashCode(), player2.hashCode());
        assertNotEquals(player1.hashCode(), player3.hashCode());
    }

    @Test
    public void testEquals() {
        CellPosition pos1 = new CellPosition(1, 2);
        CellPosition pos2 = new CellPosition(3, 4);
        Player player1 = new HumanPlayer(pos1);
        Player player2 = new HumanPlayer(pos1);
        Player player3 = new HumanPlayer(pos2);
        assertEquals(player1, player2);
        assertNotEquals(player1, player3);
    }

    @Test
    public void testShiftedToPosition() {
        // Create a new player object
        Player player = new HumanPlayer(new CellPosition(2, 2));

        // Shift the player to a new position
        CellPosition newPosition = new CellPosition(3, 3);
        Player shiftedPlayer = player.shiftedToPosition(newPosition);

        // Check that the new player object has the correct position
        assertEquals(newPosition, shiftedPlayer.getPos());
    }

}
