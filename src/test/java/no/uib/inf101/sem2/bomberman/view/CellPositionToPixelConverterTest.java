package no.uib.inf101.sem2.bomberman.view;

import org.junit.jupiter.api.Test;

import no.uib.inf101.sem2.bomberman.model.BombermanBoard;
import no.uib.inf101.sem2.grid.CellPosition;
import no.uib.inf101.sem2.grid.GridDimension;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.geom.Rectangle2D;

public class CellPositionToPixelConverterTest {

    @Test
    public void sanityTest() {
        GridDimension gd = new BombermanBoard(3, 4);
        CellPositionToPixelConverter converter = new CellPositionToPixelConverter(
                new Rectangle2D.Double(29, 29, 340, 240), gd, 30);
        Rectangle2D expected = new Rectangle2D.Double(214, 129, 47.5, 40);
        assertEquals(expected, converter.getBoundsForCell(new CellPosition(1, 2)));
    }

}
