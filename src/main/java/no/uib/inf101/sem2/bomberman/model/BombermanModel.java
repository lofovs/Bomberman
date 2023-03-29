package no.uib.inf101.sem2.bomberman.model;

import no.uib.inf101.sem2.bomberman.controller.ControllableBombermanModel;
import no.uib.inf101.sem2.bomberman.model.player.Player;
import no.uib.inf101.sem2.bomberman.view.ViewableBombermanModel;
import no.uib.inf101.sem2.grid.CellPosition;
import no.uib.inf101.sem2.grid.GridCell;
import no.uib.inf101.sem2.grid.GridDimension;

public class BombermanModel implements ViewableBombermanModel, ControllableBombermanModel {

    private BombermanBoard board;
    private Player player;

    public BombermanModel(BombermanBoard board) {
        this.board = board;
        this.player = new Player(new CellPosition(0, 0));
        this.player = board.spawn(board);
    }

    @Override
    public GridDimension getDimension() {
        return this.board;
    }

    @Override
    public Iterable<GridCell<Character>> getTilesOnBoard() {
        return this.board;
    }

    @Override
    public boolean placeBomb() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'placeBomb'");
    }

    @Override
    public boolean movePlayer(int deltaRow, int deltaCol) {
        Player newPlayer = this.player.shiftedBy(deltaRow, deltaCol);
        if (this.board.isLegalMove(newPlayer)) {
            this.player = newPlayer;
            return true;
        }
        return false;

    }

    @Override
    public Iterable<GridCell<Character>> getPlayerTile() {
        return this.player;
    }

    @Override
    public Player getPlayer() {
        return this.player;
    }
}
