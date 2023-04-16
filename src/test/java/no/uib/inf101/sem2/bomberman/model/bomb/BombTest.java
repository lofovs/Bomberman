package no.uib.inf101.sem2.bomberman.model.bomb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import no.uib.inf101.sem2.grid.CellPosition;

public class BombTest {

    private Bomb bomb;
    private CellPosition pos;

    @Test
    public void testShiftedToPosition() {
        bomb = Bomb.newBomb();
        CellPosition newPos = new CellPosition(1, 2);
        Bomb newBomb = bomb.shiftedToPosition(newPos);
        assertEquals(newPos, newBomb.getPos());
        assertEquals(0, newBomb.getClock());
    }

    @Test
    public void testNewBomb() {
        Bomb newBomb = Bomb.newBomb();
        CellPosition expectedPos = new CellPosition(-1, -1);
        assertEquals(expectedPos, newBomb.getPos());
        assertEquals('B', newBomb.getSymbol());
        assertEquals(0, newBomb.getClock());
    }

    @Test
    public void testEquals() {
        // Create two bombs with the same position, symbol, and clock
        Bomb bomb1 = new Bomb(new CellPosition(2, 3));
        bomb1.tick();
        Bomb bomb2 = new Bomb(new CellPosition(2, 3));
        bomb2.tick();

        // Test that the two bombs are equal
        assertTrue(bomb1.equals(bomb2));
        assertTrue(bomb2.equals(bomb1));

        // Create a bomb with a different position, symbol, and clock
        Bomb bomb3 = new Bomb(new CellPosition(4, 5));
        bomb3.tick();
        bomb3.tick();

        // Test that the two bombs are not equal
        assertFalse(bomb1.equals(bomb3));
        assertFalse(bomb3.equals(bomb1));
    }

    @Test
    public void testHashCode() {
        CellPosition pos1 = new CellPosition(1, 2);
        CellPosition pos2 = new CellPosition(3, 4);
        Bomb bomb1 = new Bomb(pos1);
        Bomb bomb2 = new Bomb(pos1);
        Bomb bomb3 = new Bomb(pos2);
        assertEquals(bomb1.hashCode(), bomb2.hashCode());
        assertNotEquals(bomb1.hashCode(), bomb3.hashCode());
    }

}
