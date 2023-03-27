package no.uib.inf101.sem2.bomberman.model;

import no.uib.inf101.sem2.bomberman.controller.ControllableBombermanModel;
import no.uib.inf101.sem2.bomberman.view.ViewableBombermanModel;
import no.uib.inf101.sem2.grid.GridCell;
import no.uib.inf101.sem2.grid.GridDimension;

public class BombermanModel implements ViewableBombermanModel, ControllableBombermanModel {

    private BombermanBoard board;

    public BombermanModel(BombermanBoard board) {
        this.board = board;
    }

    @Override
    public GridDimension getDimension() {
        return this.board;
    }

    @Override
    public Iterable<GridCell<Character>> getTilesOnBoard() {
        return this.board;
    }

}
