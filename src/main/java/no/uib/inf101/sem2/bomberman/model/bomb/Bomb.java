package no.uib.inf101.sem2.bomberman.model.bomb;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import no.uib.inf101.sem2.bomberman.model.player.Player;
import no.uib.inf101.sem2.grid.CellPosition;
import no.uib.inf101.sem2.grid.GridCell;

public class Bomb implements Iterable<GridCell<Character>> {

    private CellPosition pos;
    private Character symbol;
    private static Player player;

    public Bomb(CellPosition pos) {
        this.pos = pos;
        this.symbol = 'B';
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((pos == null) ? 0 : pos.hashCode());
        result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Bomb other = (Bomb) obj;
        if (pos == null) {
            if (other.pos != null)
                return false;
        } else if (!pos.equals(other.pos))
            return false;
        if (symbol == null) {
            if (other.symbol != null)
                return false;
        } else if (!symbol.equals(other.symbol))
            return false;
        return true;
    }

    public CellPosition getPos() {
        return this.pos;
    }

    /**
     * Moves the player to a new position
     * 
     * @param deltaRow the row to move by (can be negative)
     * @param deltaCol the column to move by (can be negative)
     * @return a new Player object with the new position
     */
    public Bomb shiftedBy(int deltaRow, int deltaCol) {
        CellPosition newPos = new CellPosition(this.pos.row() + deltaRow, this.pos.col() + deltaCol);
        return new Bomb(newPos);
    }

    public Character getSymbol() {
        return symbol;
    }

    @Override
    public Iterator<GridCell<Character>> iterator() {
        List<GridCell<Character>> list = new ArrayList<>();
        list.add(new GridCell<>(this.pos, this.symbol));
        return list.iterator();
    }

    static Bomb newBomb() {
        return new Bomb(player.getPos());
    }
}
