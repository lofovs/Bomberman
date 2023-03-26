package no.uib.inf101.sem2.grid;

/**
 * A record class for storing a cell position and a value.
 */
public record GridCell<E>(CellPosition pos, E value) {

}
