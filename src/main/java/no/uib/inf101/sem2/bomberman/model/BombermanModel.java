package no.uib.inf101.sem2.bomberman.model;

import java.util.Random;
import no.uib.inf101.sem2.bomberman.controller.ControllableBombermanModel;
import no.uib.inf101.sem2.bomberman.model.bomb.Bomb;
import no.uib.inf101.sem2.bomberman.model.bomb.BombFactory;
import no.uib.inf101.sem2.bomberman.model.player.IPlayer;
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
  private Bomb explodedBomb2;
  private Bomb explodedBomb3;
  private Bomb explodedBomb4;
  private BombFactory bombFactory;
  private GameState gameState;

  private int explosionTimer;
  private int explosionTimer2;
  private int explosionTimer3;
  private int explosionTimer4;

  private int playerBombCount;
  private int player2BombCount;
  private int player3BombCount;
  private int player4BombCount;

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
    this.random = new Random();

    this.playerBombCount = 0;
    this.player2BombCount = 0;
    this.player3BombCount = 0;
    this.player4BombCount = 0;
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
  public boolean placeBomb(IPlayer player, Bomb bomb) {
    Bomb newBomb = bomb.shiftedToPosition(player.getPos());

    if (this.board.canPlace(newBomb.getPos())) {
      if (player == this.player && playerBombCount < 1) {
        this.bomb = newBomb;
        addBombToBoard(newBomb);
        playerBombCount++;
        return true;
      } else if (player == this.playerAI && player2BombCount < 1) {
        this.bomb2 = newBomb;
        addBombToBoard(newBomb);
        player2BombCount++;
        return true;
      } else if (player == this.playerAI2 && player3BombCount < 1) {
        this.bomb3 = newBomb;
        addBombToBoard(newBomb);
        player3BombCount++;
        return true;
      } else if (player == this.playerAI3 && player4BombCount < 1) {
        this.bomb4 = newBomb;
        addBombToBoard(newBomb);
        player4BombCount++;
        return true;
      }
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
   * Replaces the bomb tile with an explosion also covering the 4 adjacent tiles, if the tiles are destructible.
   * Also removes the bomb object from the board, after creating the explosion
   * @param bomb the bomb to explode
   */
  private void explodeBomb(Bomb bomb) {
    for (GridCell<Character> gridCell : bomb) {
      this.board.set(gridCell.pos(), 'E');
      if (
        this.board.isDestructible(
            new CellPosition(gridCell.pos().row() - 1, gridCell.pos().col())
          )
      ) {
        this.board.set(
            new CellPosition(gridCell.pos().row() - 1, gridCell.pos().col()),
            'E'
          );
      }
      if (
        this.board.isDestructible(
            new CellPosition(gridCell.pos().row() + 1, gridCell.pos().col())
          )
      ) {
        this.board.set(
            new CellPosition(gridCell.pos().row() + 1, gridCell.pos().col()),
            'E'
          );
      }
      if (
        this.board.isDestructible(
            new CellPosition(gridCell.pos().row(), gridCell.pos().col() - 1)
          )
      ) {
        this.board.set(
            new CellPosition(gridCell.pos().row(), gridCell.pos().col() - 1),
            'E'
          );
      }
      if (
        this.board.isDestructible(
            new CellPosition(gridCell.pos().row(), gridCell.pos().col() + 1)
          )
      ) {
        this.board.set(
            new CellPosition(gridCell.pos().row(), gridCell.pos().col() + 1),
            'E'
          );
      }
    }
    if (bomb == this.bomb) {
      this.explodedBomb = bomb;
    } else if (bomb == this.bomb2) {
      this.explodedBomb2 = bomb;
    } else if (bomb == this.bomb3) {
      this.explodedBomb3 = bomb;
    } else if (bomb == this.bomb4) {
      this.explodedBomb4 = bomb;
    }
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

  /**
   * Moves the AI in a random direction
   * @return true if the AI moved, false if it didn't
   */
  private void moveAI(PlayerAI playerAI) {
    // the AI will choose a direction to try to move in
    int deltaRow = 0;
    int deltaCol = 0;
    int direction = this.random.nextInt(4);
    if (direction == 0) {
      deltaRow = -1;
    } else if (direction == 1) {
      deltaRow = 1;
    } else if (direction == 2) {
      deltaCol = -1;
    } else if (direction == 3) {
      deltaCol = 1;
    }

    PlayerAI newAI = playerAI.shiftedBy(deltaRow, deltaCol);
    if (this.board.canPlace(newAI.getPos())) {
      if (playerAI == this.playerAI) {
        this.playerAI = newAI;
      } else if (playerAI == this.playerAI2) {
        this.playerAI2 = newAI;
      } else if (playerAI == this.playerAI3) {
        this.playerAI3 = newAI;
      }
      return;
    }

    // if the AI fails to move, it will try to move again. If it fails 3 times, it will stop trying
    int tries = 0;
    while (!this.board.canPlace(newAI.getPos()) && tries < 4) {
      direction = this.random.nextInt(4);
      if (direction == 0) {
        deltaRow = -1;
      } else if (direction == 1) {
        deltaRow = 1;
      } else if (direction == 2) {
        deltaCol = -1;
      } else if (direction == 3) {
        deltaCol = 1;
      }
      newAI = playerAI.shiftedBy(deltaRow, deltaCol);
      tries++;
    }
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
    player1ClockTick();
    player2ClockTick();
    player3ClockTick();
    player4ClockTick();

    moveAI(playerAI);
    moveAI(playerAI2);
    moveAI(playerAI3);

    // make the AI place bombs randomly
    if (this.random.nextInt(100) < 33) {
      placeBomb(playerAI, bomb2);
    }
    if (this.random.nextInt(100) < 33) {
      placeBomb(playerAI2, bomb3);
    }
    if (this.random.nextInt(100) < 33) {
      placeBomb(playerAI3, bomb4);
    }
  }

  private void player4ClockTick() {
    // checks if the bomb has exploded and if it has it will remove the explosion tiles, create a new bomb and reset the explosion timer
    if (explosionTimer4 == 1) {
      removeExplodedTiles(explodedBomb4);
      this.bomb4 = bombFactory.createNewBomb();
      explosionTimer4 = 0;
      player4BombCount--;
    }
    // checks if the bomb's timer has reached 3 seconds, and if it has it will explode
    if (this.bomb4.getClock() == 3) {
      explodeBomb(this.bomb4);
      explosionTimer4++;
    }
    // checks if the bomb is on the grid and not outside, if it is it will tick and get added to the board
    if (board.positionIsOnGrid(this.bomb4.getPos())) {
      this.bomb4.tick();
    }
  }

  private void player3ClockTick() {
    // checks if the bomb has exploded and if it has it will remove the explosion tiles, create a new bomb and reset the explosion timer
    if (explosionTimer3 == 1) {
      removeExplodedTiles(explodedBomb3);
      this.bomb3 = bombFactory.createNewBomb();
      explosionTimer3 = 0;
      player3BombCount--;
    }
    // checks if the bomb's timer has reached 3 seconds, and if it has it will explode
    if (this.bomb3.getClock() == 3) {
      explodeBomb(this.bomb3);
      explosionTimer3++;
    }
    // checks if the bomb is on the grid and not outside, if it is it will tick and get added to the board
    if (board.positionIsOnGrid(this.bomb3.getPos())) {
      this.bomb3.tick();
    }
  }

  private void player2ClockTick() {
    // checks if the bomb has exploded and if it has it will remove the explosion tiles, create a new bomb and reset the explosion timer
    if (explosionTimer2 == 1) {
      removeExplodedTiles(explodedBomb2);
      this.bomb2 = bombFactory.createNewBomb();
      explosionTimer2 = 0;
      player2BombCount--;
    }
    // checks if the bomb's timer has reached 3 seconds, and if it has it will explode
    if (this.bomb2.getClock() == 3) {
      explodeBomb(this.bomb2);
      explosionTimer2++;
    }
    // checks if the bomb is on the grid and not outside, if it is it will tick and get added to the board
    if (board.positionIsOnGrid(this.bomb2.getPos())) {
      this.bomb2.tick();
    }
  }

  private void player1ClockTick() {
    // checks if the bomb has exploded and if it has it will remove the explosion tiles, create a new bomb and reset the explosion timer
    if (explosionTimer == 1) {
      removeExplodedTiles(explodedBomb);
      this.bomb = bombFactory.createNewBomb();
      explosionTimer = 0;
      playerBombCount--;
    }
    // checks if the bomb's timer has reached 3 seconds, and if it has it will explode
    if (this.bomb.getClock() == 3) {
      explodeBomb(this.bomb);
      explosionTimer++;
    }
    // checks if the bomb is on the grid and not outside, if it is it will tick and get added to the board
    if (board.positionIsOnGrid(this.bomb.getPos())) {
      this.bomb.tick();
    }
  }

  /**
   * Replaces the explosion tiles with empty tiles, if the tiles are destructible
   * @param bomb the bomb that has exploded
   */
  private void removeExplodedTiles(Bomb bomb) {
    for (GridCell<Character> gridCell : bomb) {
      this.board.set(gridCell.pos(), '-');
      if (
        this.board.isDestructible(
            new CellPosition(gridCell.pos().row() - 1, gridCell.pos().col())
          )
      ) {
        this.board.set(
            new CellPosition(gridCell.pos().row() - 1, gridCell.pos().col()),
            '-'
          );
      }
      if (
        this.board.isDestructible(
            new CellPosition(gridCell.pos().row() + 1, gridCell.pos().col())
          )
      ) {
        this.board.set(
            new CellPosition(gridCell.pos().row() + 1, gridCell.pos().col()),
            '-'
          );
      }
      if (
        this.board.isDestructible(
            new CellPosition(gridCell.pos().row(), gridCell.pos().col() - 1)
          )
      ) {
        this.board.set(
            new CellPosition(gridCell.pos().row(), gridCell.pos().col() - 1),
            '-'
          );
      }
      if (
        this.board.isDestructible(
            new CellPosition(gridCell.pos().row(), gridCell.pos().col() + 1)
          )
      ) {
        this.board.set(
            new CellPosition(gridCell.pos().row(), gridCell.pos().col() + 1),
            '-'
          );
      }
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
