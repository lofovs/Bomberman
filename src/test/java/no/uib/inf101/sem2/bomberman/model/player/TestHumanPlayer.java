package no.uib.inf101.sem2.bomberman.model.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import no.uib.inf101.sem2.grid.CellPosition;

public class TestHumanPlayer {

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
}
