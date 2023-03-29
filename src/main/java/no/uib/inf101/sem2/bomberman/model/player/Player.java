package no.uib.inf101.sem2.bomberman.model.player;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import no.uib.inf101.sem2.bomberman.model.BombermanBoard;
import no.uib.inf101.sem2.grid.CellPosition;
import no.uib.inf101.sem2.grid.GridCell;
import no.uib.inf101.sem2.grid.GridDimension;

public class Player implements Iterable<GridCell<Character>> {

    private int lives;
    private int speed;
    private int range;
    private String effect;
    private CellPosition pos;
    private BombermanBoard board;
    private Character symbol;

    public Player(CellPosition pos) {
        this.lives = 3;
        this.pos = pos;
        this.symbol = 'y';

    }

    public int getLives() {
        return this.lives;
    }

    public int getSpeed() {
        return speed;
    }

    public int getRange() {
        return range;
    }

    public String getEffect() {
        return effect;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + lives;
        result = prime * result + speed;
        result = prime * result + range;
        result = prime * result + ((effect == null) ? 0 : effect.hashCode());
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
        Player other = (Player) obj;
        if (lives != other.lives)
            return false;
        if (speed != other.speed)
            return false;
        if (range != other.range)
            return false;
        if (effect == null) {
            if (other.effect != null)
                return false;
        } else if (!effect.equals(other.effect))
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
    public Player shiftedBy(int deltaRow, int deltaCol) {
        CellPosition newPos = new CellPosition(this.pos.row() + deltaRow, this.pos.col() + deltaCol);
        return new Player(newPos);
    }

    public BombermanBoard getBoard() {
        return board;
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
}
