package no.uib.inf101.sem2.bomberman.model.player;

import no.uib.inf101.sem2.grid.CellPosition;

public class HumanPlayer extends Player {

    public HumanPlayer(CellPosition pos) {
        super(pos);

    }

    @Override
    public HumanPlayer shiftedBy(int deltaRow, int deltaCol) {
        CellPosition newPos = new CellPosition(
                this.getPos().row() + deltaRow,
                this.getPos().col() + deltaCol);
        return new HumanPlayer(newPos);
    }

    @Override
    public HumanPlayer shiftedToPosition(CellPosition pos) {
        return new HumanPlayer(pos);

    }

}
