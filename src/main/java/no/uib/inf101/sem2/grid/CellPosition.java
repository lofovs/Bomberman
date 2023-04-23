package no.uib.inf101.sem2.grid;

import no.uib.inf101.sem2.bomberman.model.Direction;

/**
 * A record class for storing a cell position.
 */
public record CellPosition(int row, int col) {

    /**
     * Create a new cell position shifted by the given direction.
     * 
     * @param direction
     * @return the new cell position
     */
    public CellPosition shiftedBy(Direction direction) {
        return new CellPosition(row + direction.getDeltaRow(), col + direction.getDeltaCol());
    }

    /**
     * Create a new cell position shifted by the given deltas.
     * 
     * @param deltaRow
     * @param deltaCol
     * @return the new cell position
     */
    public CellPosition shiftedBy(int deltaRow, int deltaCol) {
        return new CellPosition(row + deltaRow, col + deltaCol);
    }
}
