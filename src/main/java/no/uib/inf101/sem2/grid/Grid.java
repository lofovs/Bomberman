package no.uib.inf101.sem2.grid;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.lang.IndexOutOfBoundsException;

/**
 * A grid of elements of type E.
 * 
 * @param <E> The type of elements in the grid
 */
public class Grid<E> implements IGrid<E> {

    private int row;
    private int col;
    protected E defaultValue;
    private List<List<E>> grid;
    private List<E> cell;

    /**
     * Creates a grid with the specified number of rows and columns, and fills it
     * with the specified defaultValue
     * 
     * @param row          the number of rows in the grid
     * @param col          the number of cols in the grid
     * @param defaultValue the value to fill the grid with
     */
    public Grid(int row, int col, E defaultValue) {
        this.row = row;
        this.col = col;
        this.defaultValue = defaultValue;

        fillGrid(row, col, defaultValue);
    }

    /**
     * Creates a grid with the specified number of rows and columns, and fills it
     * with null
     * 
     * @param row the number of rows in the grid
     * @param col the number of cols in the grid
     */
    public Grid(int row, int col) {
        this.row = row;
        this.col = col;
        this.defaultValue = null;

        fillGrid(row, col, defaultValue);
    }

    /**
     * Fills the grid with the specificed defaultValue
     * 
     * @param row          the number of rows in the grid
     * @param col          the number of cols in the grid
     * @param defaultValue the value to fill the grid with
     */
    public void fillGrid(int row, int col, E defaultValue) {
        this.grid = new ArrayList<List<E>>();
        for (int i = 0; i < this.row; i++) {
            this.cell = new ArrayList<E>();
            this.grid.add(this.cell);
            for (int j = 0; j < this.col; j++) {
                this.cell.add(this.defaultValue);
            }
        }

    }

    @Override
    public int rows() {
        return this.row;
    }

    @Override
    public int cols() {
        return this.col;
    }

    @Override
    public Iterator<GridCell<E>> iterator() {
        ArrayList<GridCell<E>> list = new ArrayList<GridCell<E>>();
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {

                CellPosition pos = new CellPosition(i, j);
                E value = this.grid.get(i).get(j);
                GridCell<E> gc = new GridCell<E>(pos, value);
                list.add(gc);
            }
        }
        return list.iterator();
    }

    @Override
    public void set(CellPosition pos, E value) throws IndexOutOfBoundsException {
        int row = pos.row();
        int col = pos.col();
        this.grid.get(row).set(col, value);

    }

    @Override
    public E get(CellPosition pos) throws IndexOutOfBoundsException {
        int row = pos.row();
        int col = pos.col();
        return this.grid.get(row).get(col);

    }

    @Override
    public boolean positionIsOnGrid(CellPosition pos) throws IndexOutOfBoundsException {
        int row = pos.row();
        int col = pos.col();

        if (row > this.row - 1 || row < 0 || col > this.col - 1 || col < 0)
            return false;
        else
            return true;
    }

}
