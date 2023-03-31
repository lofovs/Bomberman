package no.uib.inf101.sem2.bomberman.model;

import no.uib.inf101.sem2.bomberman.controller.ControllableBombermanModel;
import no.uib.inf101.sem2.bomberman.model.bomb.Bomb;
import no.uib.inf101.sem2.bomberman.model.bomb.BombFactory;
import no.uib.inf101.sem2.bomberman.model.player.Player;
import no.uib.inf101.sem2.bomberman.view.ViewableBombermanModel;
import no.uib.inf101.sem2.grid.CellPosition;
import no.uib.inf101.sem2.grid.GridCell;
import no.uib.inf101.sem2.grid.GridDimension;

public class BombermanModel implements ViewableBombermanModel, ControllableBombermanModel {

    private BombermanBoard board;
    private Player player;
    private Bomb bomb;
    private BombFactory bombFactory;

    public BombermanModel(BombermanBoard board, BombFactory bombFactory) {
        this.board = board;
        this.player = new Player(new CellPosition(0, 0));
        this.player = board.spawn(board);
        this.bombFactory = bombFactory;
        this.bomb = bombFactory.createBomb();
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
        Bomb bomb = this.bombFactory.createBomb();
        Bomb newBomb = bomb.shiftedToPosition(player.getPos());
        if (this.board.canPlace(newBomb)) {
            this.bomb = newBomb;
            addBombToBoard();
            return true;
        }
        return false;
    }

    public void addBombToBoard() {
        for (GridCell<Character> gridCell : this.bomb) {
            this.board.set(gridCell.pos(), gridCell.value());
        }

        this.bomb = bombFactory.createBomb();

    }

    @Override
    public boolean movePlayer(int deltaRow, int deltaCol) {
        Player newPlayer = this.player.shiftedBy(deltaRow, deltaCol);
        if (this.board.canPlace(newPlayer)) {
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

    @Override
    public Bomb getBomb() {
        return this.bomb;
    }

    @Override
    public int getTimerInterval() {
        return 1000;
    }

    @Override
    public void clockTick() {
        // explode bomb after 3 seconds
        if (this.bomb.getTimer() == 3) {
            this.bomb.explode();
        }
    }

    @Override
    public Iterable<GridCell<Character>> getBombTile() {
        return this.bomb;
    }
}
