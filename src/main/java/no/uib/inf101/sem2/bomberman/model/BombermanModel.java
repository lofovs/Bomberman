package no.uib.inf101.sem2.bomberman.model;

import java.util.Random;
import no.uib.inf101.sem2.bomberman.controller.ControllableBombermanModel;
import no.uib.inf101.sem2.bomberman.model.bomb.Bomb;
import no.uib.inf101.sem2.bomberman.model.bomb.BombFactory;
import no.uib.inf101.sem2.bomberman.model.player.Player;
import no.uib.inf101.sem2.bomberman.model.player.PlayerAI;
import no.uib.inf101.sem2.bomberman.view.ViewableBombermanModel;
import no.uib.inf101.sem2.grid.CellPosition;
import no.uib.inf101.sem2.grid.GridCell;
import no.uib.inf101.sem2.grid.GridDimension;

public class BombermanModel
  implements ViewableBombermanModel, ControllableBombermanModel {

  private BombermanBoard board;

  private Player player;
  private PlayerAI playerAI;
  private PlayerAI playerAI2;
  private PlayerAI playerAI3;

  private Bomb bomb;
  private Bomb bomb2;
  private Bomb bomb3;
  private Bomb bomb4;
  private Bomb explodedBomb;
  private BombFactory bombFactory;
  private GameState gameState;
  private int explosionTimer;
  private int bombCount;
  private Random random;

  public BombermanModel(BombermanBoard board, BombFactory bombFactory) {
    this.board = board;

    this.player = new Player(new CellPosition(board.rows() - 2, 1));
    this.playerAI = new PlayerAI(new CellPosition(1, 1), 'b');
    this.playerAI2 =
      new PlayerAI(new CellPosition(board.rows() - 2, board.cols() - 2), 'r');
    this.playerAI3 = new PlayerAI(new CellPosition(1, board.cols() - 2), 'p');

    this.bombFactory = bombFactory;

    this.bomb = bombFactory.createNewBomb();
    this.bomb2 = bombFactory.createNewBomb();
    this.bomb3 = bombFactory.createNewBomb();
    this.bomb4 = bombFactory.createNewBomb();

    this.gameState = GameState.ACTIVE_GAME;
    this.explosionTimer = 0;
    this.bombCount = 0;
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
  public boolean placeBomb(Bomb bomb) {
    Bomb newBomb = bomb.shiftedToPosition(player.getPos());

    if (this.board.canPlace(newBomb.getPos())) {
      this.bomb = newBomb;
      this.bombCount += 1;
      return true;
    }
    return false;
  }

  /**
   * Adds the bomb to the board.
   *@param bomb the bomb to add
   */
  private void addBombToBoard(Bomb bomb) {
    for (GridCell<Character> gridCell : bomb) {
      this.board.set(gridCell.pos(), gridCell.value());
    }
  }

  /**
   * Replaces the bomb tile with an explosion also covering the 4 adjacent tiles.
   * Also removes the bomb object from the board, after creating the explosion
   * @param bomb the bomb to explode
   */
  private void explodeBomb(Bomb bomb) {
    for (GridCell<Character> gridCell : bomb) {
      this.board.set(gridCell.pos(), 'E');
      this.board.set(
          new CellPosition(gridCell.pos().row() - 1, gridCell.pos().col()),
          'E'
        );
      this.board.set(
          new CellPosition(gridCell.pos().row() + 1, gridCell.pos().col()),
          'E'
        );
      this.board.set(
          new CellPosition(gridCell.pos().row(), gridCell.pos().col() - 1),
          'E'
        );
      this.board.set(
          new CellPosition(gridCell.pos().row(), gridCell.pos().col() + 1),
          'E'
        );
    }
    this.explodedBomb = bomb;
    bomb = bombFactory.createNewBomb();
  }

  @Override
  public boolean movePlayer(int deltaRow, int deltaCol) {
    Player newPlayer = this.player.shiftedBy(deltaRow, deltaCol);
    if (this.board.canPlace(newPlayer.getPos())) {
      this.player = newPlayer;
      return true;
    }
    return false;
  }

  @Override
  public boolean moveAI(PlayerAI playerAI, int deltaRow, int deltaCol) {
    PlayerAI newAI = playerAI.shiftedBy(deltaRow, deltaCol);
    if (this.board.canPlace(newAI.getPos())) {
      playerAI = newAI;
      return true;
    }
    return false;
  }

  @Override
  public Iterable<GridCell<Character>> getPlayerTile() {
    return this.player;
  }

  @Override
  public Iterable<GridCell<Character>> getPlayer2Tile() {
    return playerAI;
  }

  @Override
  public Iterable<GridCell<Character>> getPlayer3Tile() {
    return playerAI2;
  }

  @Override
  public Iterable<GridCell<Character>> getPlayer4Tile() {
    return playerAI3;
  }

  @Override
  public int getTimerInterval() {
    return 1000;
  }

  @Override
  public void clockTick() {
    // checks if the bomb has exploded and if it has it will remove the explosion tiles, create a new bomb and reset the explosion timer
    if (explosionTimer == 1) {
      removeExplodedTiles(explodedBomb);
      this.bomb = bombFactory.createNewBomb();
      explosionTimer = 0;
      bombCount = 0;
    }
    // checks if the bomb's timer has reached 3 seconds, and if it has it will explode
    if (this.bomb.getClock() == 3) {
      explodeBomb(this.bomb);
      explosionTimer++;
    }
    // checks if the bomb is on the grid and not outside, if it is it will tick and get added to the board
    if (board.positionIsOnGrid(this.bomb.getPos())) {
      this.bomb.tick();
      addBombToBoard(this.bomb);
    }
  }

  /**
   * Replaces the explosion tiles with empty tiles.
   * @param bomb the bomb that has exploded
   */
  private void removeExplodedTiles(Bomb bomb) {
    for (GridCell<Character> gridCell : bomb) {
      this.board.set(gridCell.pos(), '-');
      this.board.set(
          new CellPosition(gridCell.pos().row() - 1, gridCell.pos().col()),
          '-'
        );
      this.board.set(
          new CellPosition(gridCell.pos().row() + 1, gridCell.pos().col()),
          '-'
        );
      this.board.set(
          new CellPosition(gridCell.pos().row(), gridCell.pos().col() - 1),
          '-'
        );
      this.board.set(
          new CellPosition(gridCell.pos().row(), gridCell.pos().col() + 1),
          '-'
        );
    }
  }

  @Override
  public Iterable<GridCell<Character>> getBombTile() {
    return this.bomb;
  }

  @Override
  public Iterable<GridCell<Character>> getBomb2Tile() {
    return this.bomb2;
  }

  @Override
  public Iterable<GridCell<Character>> getBomb3Tile() {
    return this.bomb3;
  }

  @Override
  public Iterable<GridCell<Character>> getBomb4Tile() {
    return this.bomb4;
  }

  @Override
  public GameState getGameState() {
    return this.gameState;
  }

  @Override
  public void pauseGame() {
    if (this.gameState == GameState.ACTIVE_GAME) {
      this.gameState = GameState.PAUSED_GAME;
    }
  }

  @Override
  public void playGame() {
    this.gameState = GameState.ACTIVE_GAME;
  }

  @Override
  public int getBombCount() {
    return this.bombCount;
  }

  @Override
  public Player getPlayer() {
    return this.player;
  }

  @Override
  public PlayerAI getPlayerAI() {
    return this.playerAI;
  }

  @Override
  public PlayerAI getPlayerAI2() {
    return this.playerAI2;
  }

  @Override
  public PlayerAI getPlayerAI3() {
    return this.playerAI3;
  }

  @Override
  public Bomb getBomb() {
    return this.bomb;
  }

  @Override
  public Bomb getBomb2() {
    return this.bomb2;
  }

  @Override
  public Bomb getBomb3() {
    return this.bomb3;
  }

  @Override
  public Bomb getBomb4() {
    return this.bomb4;
  }
}
