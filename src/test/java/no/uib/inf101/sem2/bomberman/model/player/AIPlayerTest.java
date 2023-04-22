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
}
