package no.uib.inf101.sem2.bomberman.model.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import no.uib.inf101.sem2.grid.CellPosition;

public class PlayerAITest {

    @Test
    public void testHashCode() {
        CellPosition pos1 = new CellPosition(1, 2);
        CellPosition pos2 = new CellPosition(3, 4);
        PlayerAI player1 = new PlayerAI(pos1, 'P');
        PlayerAI player2 = new PlayerAI(pos1, 'P');
        PlayerAI player3 = new PlayerAI(pos2, 'P');
        assertEquals(player1.hashCode(), player2.hashCode());
        assertNotEquals(player1.hashCode(), player3.hashCode());
    }

    @Test
    public void testEquals() {
        CellPosition pos1 = new CellPosition(1, 2);
        CellPosition pos2 = new CellPosition(3, 4);
        PlayerAI player1 = new PlayerAI(pos1, 'P');
        PlayerAI player2 = new PlayerAI(pos1, 'P');
        PlayerAI player3 = new PlayerAI(pos2, 'P');
        PlayerAI player4 = new PlayerAI(pos1, 'Q');
        assertEquals(player1, player2);
        assertNotEquals(player1, player3);
        assertNotEquals(player1, player4);
    }

    @Test
    public void testShiftedBy() {
        CellPosition pos = new CellPosition(3, 4);
        char symbol = 'X';
        PlayerAI player = new PlayerAI(pos, symbol);

        // Shift player by deltaRow = 2 and deltaCol = -1
        PlayerAI shiftedPlayer = player.shiftedBy(2, -1);

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
        PlayerAI player = new PlayerAI(oldPos, 'X');
        PlayerAI shiftedPlayer = player.shiftedToPosition(newPos);
        assertEquals(newPos, shiftedPlayer.getPos());
        assertEquals('X', shiftedPlayer.getSymbol());
    }

}
