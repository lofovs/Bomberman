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
  private PlayerAI player2;
  private PlayerAI player3;
  private PlayerAI player4;

  private int playerLives;
  private int player2Lives;
  private int player3Lives;
  private int player4Lives;

  private int player1MoveCount;

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

  private int player1Sprite;
  private int player2Sprite;
  private int player3Sprite;
  private int player4Sprite;

  private Clock clock;
  private int clockTick;

  private static final CellPosition PLAYER_DEAD_POS = new CellPosition(-2, -2);

  private Random random;

  public BombermanModel(BombermanBoard board, BombFactory bombFactory) {
    this.board = board;
    this.player1MoveCount = 0;

    this.bombFactory = bombFactory;

    this.gameState = GameState.NEW_GAME;
    this.explosionTimer = 0;
    this.random = new Random();

    this.playerBombCount = 0;
    this.player2BombCount = 0;
    this.player3BombCount = 0;
    this.player4BombCount = 0;

    this.player1Sprite = 0;
    this.player2Sprite = 0;
    this.player3Sprite = 0;
    this.player4Sprite = 0;

    newGame();
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
      } else if (player == this.player2 && player2BombCount < 1) {
        this.bomb2 = newBomb;
        addBombToBoard(newBomb);
        player2BombCount++;
        return true;
      } else if (player == this.player3 && player3BombCount < 1) {
        this.bomb3 = newBomb;
        addBombToBoard(newBomb);
        player3BombCount++;
        return true;
      } else if (player == this.player4 && player4BombCount < 1) {
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
   * 
   * @param bomb the bomb to add
   */
  private void addBombToBoard(Bomb bomb) {
    for (GridCell<Character> gridCell : bomb) {
      this.board.set(gridCell.pos(), gridCell.value());
    }
  }

  /**
   * Replaces the bomb tile with an explosion also covering the 4 adjacent tiles,
   * if the tiles are destructible.
   * Also removes the bomb object from the board, after creating the explosion
   * 
   * @param bomb the bomb to explode
   */
  private void explodeBomb(Bomb bomb) {
    for (GridCell<Character> gridCell : bomb) {
      this.board.set(gridCell.pos(), 'E');
      if (this.board.isDestructible(
          new CellPosition(gridCell.pos().row() - 1, gridCell.pos().col()))) {
        this.board.set(
            new CellPosition(gridCell.pos().row() - 1, gridCell.pos().col()),
            'E');
      }
      if (this.board.isDestructible(
          new CellPosition(gridCell.pos().row() + 1, gridCell.pos().col()))) {
        this.board.set(
            new CellPosition(gridCell.pos().row() + 1, gridCell.pos().col()),
            'E');
      }
      if (this.board.isDestructible(
          new CellPosition(gridCell.pos().row(), gridCell.pos().col() - 1))) {
        this.board.set(
            new CellPosition(gridCell.pos().row(), gridCell.pos().col() - 1),
            'E');
      }
      if (this.board.isDestructible(
          new CellPosition(gridCell.pos().row(), gridCell.pos().col() + 1))) {
        this.board.set(
            new CellPosition(gridCell.pos().row(), gridCell.pos().col() + 1),
            'E');
      }
    }
    if (bomb == this.bomb) {
      this.bomb = bombFactory.createNewBomb();
      this.explodedBomb = bomb;
    } else if (bomb == this.bomb2) {
      this.bomb2 = bombFactory.createNewBomb();
      this.explodedBomb2 = bomb;
    } else if (bomb == this.bomb3) {
      this.bomb3 = bombFactory.createNewBomb();
      this.explodedBomb3 = bomb;
    } else if (bomb == this.bomb4) {
      this.bomb4 = bombFactory.createNewBomb();
      this.explodedBomb4 = bomb;
    }
  }

  @Override
  public boolean movePlayer(int deltaRow, int deltaCol) {
    if (player1MoveCount < 1) {
      Player newPlayer = this.player.shiftedBy(deltaRow, deltaCol);
      if (this.board.canPlace(newPlayer.getPos())) {
        this.player = newPlayer;
        this.player1MoveCount++;
        return true;
      }
    }
    return false;
  }

  /**
   * Moves the AI in a random direction
   * 
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

    // The AI will check if the chosen position is in danger of being an explosion,
    // and if true it will not move.
    // If the AI's position is currently on a bomb or in the vicinity of an
    // explosion, it will ignore this precondition
    // and move anyway
    if (this.board.isPotentialExplosion(new CellPosition(playerAI.getPos().row() + deltaRow,
        playerAI.getPos().col() + deltaCol))
        && !playerAI.getPos().equals(this.bomb.getPos())
        && !playerAI.getPos().equals(this.bomb2.getPos())
        && !playerAI.getPos().equals(this.bomb3.getPos())
        && !playerAI.getPos().equals(this.bomb4.getPos())
        && !this.board.isNextToBomb(playerAI.getPos())) {
      return;
    }

    // If the AI is right next to a bomb, it will move to the first safe tile
    // around it
    if (this.board.isNextToBomb(playerAI.getPos())) {
      if (this.board.canPlace(new CellPosition(playerAI.getPos().row() - 1,
          playerAI.getPos().col()))) {
        deltaRow = -1;
        deltaCol = 0;
      } else if (this.board.canPlace(new CellPosition(playerAI.getPos().row() + 1,
          playerAI.getPos().col()))) {
        deltaRow = 1;
        deltaCol = 0;
      } else if (this.board.canPlace(new CellPosition(playerAI.getPos().row(),
          playerAI.getPos().col() - 1))) {
        deltaRow = 0;
        deltaCol = -1;
      } else if (this.board.canPlace(new CellPosition(playerAI.getPos().row(),
          playerAI.getPos().col() + 1))) {
        deltaRow = 0;
        deltaCol = 1;
      }
    }

    PlayerAI newAI = playerAI.shiftedBy(deltaRow, deltaCol);
    if (this.board.canPlace(newAI.getPos())) {
      if (playerAI == this.player2) {
        this.player2 = newAI;
      } else if (playerAI == this.player3) {
        this.player3 = newAI;
      } else if (playerAI == this.player4) {
        this.player4 = newAI;
      }
      return;
    }

    // if the AI fails to move, it will try to move again. If it fails 3 times, it
    // will stop trying
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
    return player2;
  }

  @Override
  public Iterable<GridCell<Character>> getPlayer3Tile() {
    return player3;
  }

  @Override
  public Iterable<GridCell<Character>> getPlayer4Tile() {
    return player4;
  }

  @Override
  public int getTimerInterval() {
    return 500;
  }

  @Override
  public void clockTick() {
    if (this.gameState == GameState.ACTIVE_GAME) {
      player1ClockTick();
      player2ClockTick();
      player3ClockTick();
      player4ClockTick();
      checkWin();
      clockTick++;
      if (oneSecondRealTimeHasPassed()) {
        this.clock.tick();
      }
    }
  }

  /**
   * Checks if one second has passed in real time
   * 
   * @return true if one second has passed, false if it hasn't
   */
  private boolean oneSecondRealTimeHasPassed() {
    return clockTick % 2 == 0;
  }

  /**
   * The events that happen for player 4 when the clock ticks
   */
  private void player4ClockTick() {
    // checks if the bomb has exploded and if it has it will remove the explosion
    // tiles, create a new bomb and reset the explosion timer
    if (explosionTimer4 == 1) {
      removeExplodedTiles(explodedBomb4);
      this.bomb4 = bombFactory.createNewBomb();
      explosionTimer4 = 0;
      player4BombCount--;
    }
    // checks if the bomb's timer has reached 3 ticks, and if it has it will explode
    if (this.bomb4.getClock() == 3) {
      explodeBomb(this.bomb4);
      explosionTimer4++;
    }
    // checks if the bomb is on the grid and not outside, if it is it will tick and
    // get added to the board
    if (board.positionIsOnGrid(this.bomb4.getPos())) {
      this.bomb4.tick();
    }
    // checks if the player should take damage and reduces the player's lives if
    // they should
    damagePlayer(player4);

    // checks if the player is alive and if true it will move and place bombs
    if (isAlive(player4)) {
      moveAI(player4);
      if (this.random.nextInt(100) < 33) {
        placeBomb(player4, bomb4);
      }
    }

    // checks if the player is dead and if true it will remove the player from the
    // board
    removeDeadPlayerFromBoard(this.player4);
  }

  /**
   * The events that happen for player 3 when the clock ticks
   */
  private void player3ClockTick() {
    // checks if the bomb has exploded and if it has it will remove the explosion
    // tiles, create a new bomb and reset the explosion timer
    if (explosionTimer3 == 1) {
      removeExplodedTiles(explodedBomb3);
      this.bomb3 = bombFactory.createNewBomb();
      explosionTimer3 = 0;
      player3BombCount--;
    }
    // checks if the bomb's timer has reached 3 ticks, and if it has it will explode
    if (this.bomb3.getClock() == 3) {
      explodeBomb(this.bomb3);
      explosionTimer3++;
    }
    // checks if the bomb is on the grid and not outside, if it is it will tick and
    // get added to the board
    if (board.positionIsOnGrid(this.bomb3.getPos())) {
      this.bomb3.tick();
    }
    // checks if the player should take damage and reduces the player's lives if
    // they should
    damagePlayer(this.player3);

    // checks if the player is alive and true it will move and place bombs
    if (isAlive(player3)) {
      moveAI(player3);
      if (this.random.nextInt(100) < 33) {
        placeBomb(player3, bomb3);
      }
    }

    // checks if the player is dead and if true it will remove the player from the
    // board
    removeDeadPlayerFromBoard(this.player3);
  }

  /**
   * The events that happen for player 2 when the clock ticks
   */
  private void player2ClockTick() {
    // checks if the bomb has exploded and if it has it will remove the explosion
    // tiles, create a new bomb and reset the explosion timer
    if (explosionTimer2 == 1) {
      removeExplodedTiles(explodedBomb2);
      this.bomb2 = bombFactory.createNewBomb();
      explosionTimer2 = 0;
      player2BombCount--;
    }
    // checks if the bomb's timer has reached 3 ticks, and if it has it will explode
    if (this.bomb2.getClock() == 3) {
      explodeBomb(this.bomb2);
      explosionTimer2++;
    }
    // checks if the bomb is on the grid and not outside, if it is it will tick and
    // get added to the board
    if (board.positionIsOnGrid(this.bomb2.getPos())) {
      this.bomb2.tick();
    }
    // checks if the player should take damage and reduces the player's lives if
    // they should
    damagePlayer(this.player2);

    // checks if the player is alive, and if true it will move and place bombs
    if (isAlive(player2)) {
      moveAI(player2);
      if (this.random.nextInt(100) < 33) {
        placeBomb(player2, bomb2);
      }
    }

    // checks if the player is dead and if true it will remove the player from the
    // board
    removeDeadPlayerFromBoard(this.player2);
  }

  /**
   * The events that happen for player 1 when the clock ticks
   */
  private void player1ClockTick() {
    // checks if the bomb has exploded and if it has it will remove the explosion
    // tiles, create a new bomb and reset the explosion timer
    if (explosionTimer == 1) {
      removeExplodedTiles(explodedBomb);
      this.bomb = bombFactory.createNewBomb();
      explosionTimer = 0;
      playerBombCount--;
    }
    // checks if the bomb's timer has reached 3 ticks, and if it has it will explode
    if (this.bomb.getClock() == 3) {
      explodeBomb(this.bomb);
      explosionTimer++;
    }
    // checks if the bomb is on the grid and not outside, if it is it will tick and
    // get added to the board
    if (board.positionIsOnGrid(this.bomb.getPos())) {
      this.bomb.tick();
    }
    damagePlayer(this.player);

    removeDeadPlayerFromBoard(this.player);

    // resets the player's movement count
    this.player1MoveCount = 0;
  }

  private void checkWin() {
    if (this.playerLives > 0 &&
        this.player2Lives == 0 &&
        this.player3Lives == 0 &&
        this.player4Lives == 0) {
      this.gameState = GameState.PLAYER1_WON;
    } else if (this.playerLives == 0 &&
        this.player2Lives > 0 &&
        this.player3Lives == 0 &&
        this.player4Lives == 0) {
      this.gameState = GameState.PLAYER2_WON;
    } else if (this.playerLives == 0 &&
        this.player2Lives == 0 &&
        this.player3Lives > 0 &&
        this.player4Lives == 0) {
      this.gameState = GameState.PLAYER3_WON;
    } else if (this.playerLives == 0 &&
        this.player2Lives == 0 &&
        this.player3Lives == 0 &&
        this.player4Lives > 0) {
      this.gameState = GameState.PLAYER4_WON;
    } else if (this.playerLives == 0 &&
        this.player2Lives == 0 &&
        this.player3Lives == 0 &&
        this.player4Lives == 0 ||
        this.clock.getTime() == 0) {
      this.gameState = GameState.DRAW;
    }
  }

  @Override
  public void changePlayerSprite(IPlayer player, int row, int col) {
    if (player == this.player) {
      if (row == 1) {
        player1Sprite = 0;
      } else if (row == -1) {
        player1Sprite = 1;
      } else if (col == 1) {
        player1Sprite = 3;
      } else if (col == -1) {
        player1Sprite = 2;
      }
    }
    if (player == this.player2) {
      if (row == 1) {
        player2Sprite = 0;
      } else if (row == -1) {
        player2Sprite = 1;
      } else if (col == 1) {
        player2Sprite = 3;
      } else if (col == -1) {
        player2Sprite = 2;
      }
    }
    if (player == this.player3) {
      if (row == 1) {
        player3Sprite = 0;
      } else if (row == -1) {
        player3Sprite = 1;
      } else if (col == 1) {
        player3Sprite = 3;
      } else if (col == -1) {
        player3Sprite = 2;
      }
    }
    if (player == this.player4) {
      if (row == 1) {
        player4Sprite = 0;
      } else if (row == -1) {
        player4Sprite = 1;
      } else if (col == 1) {
        player4Sprite = 3;
      } else if (col == -1) {
        player4Sprite = 2;
      }
    }
  }

  /**
   * Checks if the player should take damage and reduces the player's lives if
   * true
   * 
   * @param player
   */
  private void damagePlayer(Iterable<GridCell<Character>> player) {
    for (GridCell<Character> gridCell : player) {
      if (player == this.player && isAlive(this.player)) {
        if (this.board.isExplosion(gridCell.pos())) {
          this.playerLives--;
        }
      }
      if (player == this.player2 && isAlive(this.player2)) {
        if (this.board.isExplosion(gridCell.pos())) {
          this.player2Lives--;
        }
      }
      if (player == this.player3 && isAlive(this.player3)) {
        if (this.board.isExplosion(gridCell.pos())) {
          this.player3Lives--;
        }
      }
      if (player == this.player4 && isAlive(this.player4)) {
        if (this.board.isExplosion(gridCell.pos())) {
          this.player4Lives--;
        }
      }
    }
  }

  /**
   * Checks if the given player is alive
   * 
   * @param player the player to check
   * @return true if the player is alive, false otherwise
   */
  private boolean isAlive(IPlayer player) {
    if (player == this.player) {
      return this.playerLives > 0;
    } else if (player == this.player2) {
      return this.player2Lives > 0;
    } else if (player == this.player3) {
      return this.player3Lives > 0;
    } else if (player == this.player4) {
      return this.player4Lives > 0;
    }
    return false;
  }

  /**
   * Replaces the explosion tiles with empty tiles, if the tiles are destructible
   * 
   * @param bomb the bomb that has exploded
   */
  private void removeExplodedTiles(Bomb explodedBomb) {
    for (GridCell<Character> gridCell : explodedBomb) {
      this.board.set(gridCell.pos(), '-');
      if (this.board.isDestructible(
          new CellPosition(gridCell.pos().row() - 1, gridCell.pos().col()))) {
        this.board.set(
            new CellPosition(gridCell.pos().row() - 1, gridCell.pos().col()),
            '-');
      }
      if (this.board.isDestructible(
          new CellPosition(gridCell.pos().row() + 1, gridCell.pos().col()))) {
        this.board.set(
            new CellPosition(gridCell.pos().row() + 1, gridCell.pos().col()),
            '-');
      }
      if (this.board.isDestructible(
          new CellPosition(gridCell.pos().row(), gridCell.pos().col() - 1))) {
        this.board.set(
            new CellPosition(gridCell.pos().row(), gridCell.pos().col() - 1),
            '-');
      }
      if (this.board.isDestructible(
          new CellPosition(gridCell.pos().row(), gridCell.pos().col() + 1))) {
        this.board.set(
            new CellPosition(gridCell.pos().row(), gridCell.pos().col() + 1),
            '-');
      }
    }
  }

  /**
   * Checks if the player is dead, and if true it will remove the player from the
   * board
   * 
   * @param player the dead player
   * @return the dead player
   */
  private void removeDeadPlayerFromBoard(IPlayer player) {
    if (!isAlive(player)) {
      if (player == this.player) {
        this.player = this.player.shiftedToPosition(PLAYER_DEAD_POS);
      } else if (player == this.player2) {
        this.player2 = this.player2.shiftedToPosition(PLAYER_DEAD_POS);
      } else if (player == this.player3) {
        this.player3 = this.player3.shiftedToPosition(PLAYER_DEAD_POS);
      } else if (player == this.player4) {
        this.player4 = this.player4.shiftedToPosition(PLAYER_DEAD_POS);
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
    gameState = GameState.ACTIVE_GAME;
  }

  @Override
  public void newGame() {
    if (this.gameState == GameState.PLAYER1_WON ||
        this.gameState == GameState.PLAYER2_WON ||
        this.gameState == GameState.PLAYER3_WON ||
        this.gameState == GameState.PLAYER4_WON ||
        this.gameState == GameState.DRAW) {
      this.gameState = GameState.ACTIVE_GAME;
    } else {
      this.gameState = GameState.NEW_GAME;
    }

    this.playerLives = 3;
    this.player2Lives = 3;
    this.player3Lives = 3;
    this.player4Lives = 3;

    this.player = new Player(new CellPosition(board.rows() - 2, 1));
    this.player2 = new PlayerAI(new CellPosition(1, 1), 'b');
    this.player3 = new PlayerAI(new CellPosition(board.rows() - 2, board.cols() - 2), 'r');
    this.player4 = new PlayerAI(new CellPosition(1, board.cols() - 2), 'p');

    this.bomb = bombFactory.createNewBomb();
    this.bomb2 = bombFactory.createNewBomb();
    this.bomb3 = bombFactory.createNewBomb();
    this.bomb4 = bombFactory.createNewBomb();

    this.clock = new Clock();

    this.board.clear();

    // fill the outer walls with 'G'
    for (int i = 0; i < board.getRows(); i++) {
      for (int j = 0; j < board.getCols(); j++) {
        if (i == 0 ||
            i == board.getRows() - 1 ||
            j == 0 ||
            j == board.getCols() - 1) {
          board.set(new CellPosition(i, j), 'G');
        }
      }
    }

    // create a maze of walls
    for (int i = 0; i < board.getRows(); i++) {
      for (int j = 0; j < board.getCols(); j++) {
        if (i % 2 == 0 && j % 2 == 0) {
          board.set(new CellPosition(i, j), 'G');
        }
      }
    }

    // create random placements of 'X' within the outer walls
    for (int i = 0; i < board.getRows(); i++) {
      for (int j = 0; j < board.getCols(); j++) {
        if (board.get(new CellPosition(i, j)) == '-') {
          if (Math.random() < 0.2) {
            board.set(new CellPosition(i, j), 'X');
          }
        }
      }
    }

    // create empty tiles around the corners
    board.set(new CellPosition(1, 1), '-');
    board.set(new CellPosition(1, 2), '-');
    board.set(new CellPosition(2, 1), '-');

    board.set(new CellPosition(1, board.getCols() - 2), '-');
    board.set(new CellPosition(1, board.getCols() - 3), '-');
    board.set(new CellPosition(2, board.getCols() - 2), '-');

    board.set(new CellPosition(board.getRows() - 2, 1), '-');
    board.set(new CellPosition(board.getRows() - 3, 1), '-');
    board.set(new CellPosition(board.getRows() - 2, 2), '-');

    board.set(new CellPosition(board.getRows() - 2, board.getCols() - 2), '-');
    board.set(new CellPosition(board.getRows() - 3, board.getCols() - 2), '-');
    board.set(new CellPosition(board.getRows() - 2, board.getCols() - 3), '-');
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

  @Override
  public int getPlayerLives() {
    return this.playerLives;
  }

  @Override
  public int getPlayer2Lives() {
    return this.player2Lives;
  }

  @Override
  public int getPlayer3Lives() {
    return this.player3Lives;
  }

  @Override
  public int getPlayer4Lives() {
    return this.player4Lives;
  }

  @Override
  public String getTime() {
    String timeString = "" + this.clock.getTime();
    return timeString;
  }

  @Override
  public int getPlayer1Sprite() {
    return this.player1Sprite;
  }

  @Override
  public int getPlayer2Sprite() {
    return this.player2Sprite;
  }

  @Override
  public int getPlayer3Sprite() {
    return this.player3Sprite;
  }

  @Override
  public int getPlayer4Sprite() {
    return this.player4Sprite;
  }
}
