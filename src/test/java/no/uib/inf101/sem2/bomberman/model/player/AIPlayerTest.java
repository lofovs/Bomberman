package no.uib.inf101.sem2.bomberman.model.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import no.uib.inf101.sem2.grid.CellPosition;

public class AIPlayerTest {

    @Test
    public void testHashCode() {
        CellPosition pos1 = new CellPosition(1, 2);
        CellPosition pos2 = new CellPosition(3, 4);
        AIPlayer player1 = new AIPlayer(pos1, 'P');
        AIPlayer player2 = new AIPlayer(pos1, 'P');
        AIPlayer player3 = new AIPlayer(pos2, 'P');
        assertEquals(player1.hashCode(), player2.hashCode());
        assertNotEquals(player1.hashCode(), player3.hashCode());
    }

    @Test
    public void testEquals() {
        CellPosition pos1 = new CellPosition(1, 2);
        CellPosition pos2 = new CellPosition(3, 4);
        AIPlayer player1 = new AIPlayer(pos1, 'P');
        AIPlayer player2 = new AIPlayer(pos1, 'P');
        AIPlayer player3 = new AIPlayer(pos2, 'P');
        AIPlayer player4 = new AIPlayer(pos1, 'Q');
        assertEquals(player1, player2);
        assertNotEquals(player1, player3);
        assertNotEquals(player1, player4);
    }

    @Test
    public void testShiftedBy() {
        CellPosition pos = new CellPosition(3, 4);
        char symbol = 'X';
        AIPlayer player = new AIPlayer(pos, symbol);

        // Shift player by deltaRow = 2 and deltaCol = -1
        AIPlayer shiftedPlayer = (AIPlayer) player.shiftedBy(2, -1);

        // Verify that the original player is not modified
        assertEquals(pos, player.getPos());
        assertEquals(symbol, player.getSymbol());

        // Verify that the shifted player has the correct position and symbol
        assertEquals(new CellPosition(5, 3), shiftedPlayer.getPos());
        assertEquals(symbol, shiftedPlayer.getSymbol());
    }

    @Test
    public void testShiftedToPosition() {
        CellPosition oldPos = new CellPosition(2, 3);
        CellPosition newPos = new CellPosition(4, 5);
        AIPlayer player = new AIPlayer(oldPos, 'X');
        AIPlayer shiftedPlayer = (AIPlayer) player.shiftedToPosition(newPos);
        assertEquals(newPos, shiftedPlayer.getPos());
        assertEquals('X', shiftedPlayer.getSymbol());
    }

}
