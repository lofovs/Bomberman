package no.uib.inf101.sem2.grid;

import no.uib.inf101.sem2.bomberman.model.Direction;

/**
 * A record class for storing a cell position.
 */
public record CellPosition(int row, int col) {

    public CellPosition shiftedBy(Direction direction) {
        return new CellPosition(row + direction.getDeltaRow(), col + direction.getDeltaCol());
    }
}
