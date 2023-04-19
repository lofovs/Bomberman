package no.uib.inf101.sem2.bomberman.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import no.uib.inf101.sem2.bomberman.controller.ControllableBombermanModel;
import no.uib.inf101.sem2.bomberman.model.bomb.Bomb;
import no.uib.inf101.sem2.bomberman.model.bomb.BombFactory;
import no.uib.inf101.sem2.bomberman.model.player.IPlayer;
import no.uib.inf101.sem2.bomberman.model.player.Player;
import no.uib.inf101.sem2.bomberman.model.player.PlayerAI;
import no.uib.inf101.sem2.bomberman.sound.wav.WavPlayer;
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
  private WavPlayer wavPlayer;

  public BombermanModel(BombermanBoard board) {
    this.board = board;
    this.bombFactory = new BombFactory();
    this.gameState = GameState.NEW_GAME;
    this.random = new Random();
    this.newGame();
    this.wavPlayer = new WavPlayer();

    // TODO: fix invisible tile bug
    // TODO: fix bomb explosion removing tiles after new game is started
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
  void explodeBomb(Bomb bomb) {

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
        damagePlayer(this.player);
        return true;
      }
    }
    return false;
  }

  private void moveAI(PlayerAI playerAI) {
    int deltaRow = 0;
    int deltaCol = 0;

    // Choose a random direction to move in
    Direction direction = chooseRandomDirection(playerAI.getPos());
    switch (direction) {
      case UP:
        deltaRow = -1;
        break;
      case DOWN:
        deltaRow = 1;
        break;
      case LEFT:
        deltaCol = -1;
        break;
      case RIGHT:
        deltaCol = 1;
        break;
    }

    // If the chosen position is in danger of being an explosion, and the player
    // is not already on a bomb or in an explosion, do not move.
    CellPosition newPosition = new CellPosition(playerAI.getPos().row() + deltaRow,
        playerAI.getPos().col() + deltaCol);
    if (shouldAvoidPosition(newPosition, playerAI.getPos())) {
      return;
    }

    // If the player is right next to a bomb, move to the first safe tile around it.
    if (isAdjacentToBomb(playerAI.getPos())) {
      Direction safeDirection = findSafeDirection(playerAI.getPos());
      if (safeDirection != null) {
        deltaRow = safeDirection.getDeltaRow();
        deltaCol = safeDirection.getDeltaCol();
      }
    }

    // If the AI can move to the new position, update the player's position and
    // cause damage.
    PlayerAI newAI = playerAI.shiftedBy(deltaRow, deltaCol);
    if (canMoveToPosition(newAI.getPos())) {
      updatePlayerPositionAndDamage(playerAI, newAI, deltaRow, deltaCol);
      return;
    }

    // If the AI is boxed in, do not move.
    if (isBoxedIn(playerAI.getPos())) {
      return;
    }

    // If the AI fails to move, try again.
    moveAI(playerAI);
  }

  private void updatePlayerPositionAndDamage(PlayerAI playerAI, PlayerAI newAI, int deltaRow, int deltaCol) {
    if (playerAI == this.player2) {
      this.player2 = newAI;
      changePlayerSprite(this.player2, deltaRow, deltaCol);
      damagePlayer(this.player2);
    } else if (playerAI == this.player3) {
      this.player3 = newAI;
      changePlayerSprite(this.player3, deltaRow, deltaCol);
      damagePlayer(this.player3);
    } else if (playerAI == this.player4) {
      this.player4 = newAI;
      changePlayerSprite(this.player4, deltaRow, deltaCol);
      damagePlayer(this.player4);
    }
  }

  private Direction chooseRandomDirection(CellPosition currentPosition) {
    List<Direction> validDirections = new ArrayList<>();
    for (Direction direction : Direction.values()) {
      CellPosition nextPosition = new CellPosition(currentPosition.row() + direction.getDeltaRow(),
          currentPosition.col() + direction.getDeltaCol());
      if (this.board.canPlace(nextPosition) && !isBoxedIn(nextPosition)) {
        validDirections.add(direction);
      }
    }
    if (validDirections.isEmpty()) {
      // The AI is boxed in, cannot move
      return null;
    }
    int index = this.random.nextInt(validDirections.size());
    return validDirections.get(index);
  }

  private boolean shouldAvoidPosition(CellPosition positionToCheck, CellPosition currentPlayerPosition) {
    return this.board.isPotentialExplosion(positionToCheck)
        && !positionToCheck.equals(this.bomb.getPos())
        && !positionToCheck.equals(this.bomb2.getPos())
        && !positionToCheck.equals(this.bomb3.getPos())
        && !positionToCheck.equals(this.bomb4.getPos())
        && !this.board.isPotentialExplosion(currentPlayerPosition);
  }

  private boolean isAdjacentToBomb(CellPosition currentPlayerPosition) {
    return this.board.isPotentialExplosion(currentPlayerPosition);
  }

  private Direction findSafeDirection(CellPosition currentPlayerPosition) {
    Direction[] directions = Direction.values();
    for (Direction direction : directions) {
      CellPosition adjacentPosition = new CellPosition(currentPlayerPosition.row() + direction.getDeltaRow(),
          currentPlayerPosition.col() + direction.getDeltaCol());
      if (this.board.canPlace(adjacentPosition)
          && !this.board.isPotentialExplosion(adjacentPosition)) {
        return direction;
      }
    }
    return null;
  }

  private boolean canMoveToPosition(CellPosition positionToCheck) {
    return this.board.canPlace(positionToCheck);
  }

  private boolean isBoxedIn(CellPosition currentPlayerPosition) {
    CellPosition leftOfPlayer = new CellPosition(currentPlayerPosition.row(),
        currentPlayerPosition.col() - 1);
    CellPosition rightOfPlayer = new CellPosition(currentPlayerPosition.row(),
        currentPlayerPosition.col() + 1);
    CellPosition abovePlayer = new CellPosition(currentPlayerPosition.row() - 1,
        currentPlayerPosition.col());
    CellPosition belowPlayer = new CellPosition(currentPlayerPosition.row() + 1,
        currentPlayerPosition.col());

    return !this.board.canPlace(belowPlayer)
        && !this.board.canPlace(abovePlayer)
        && !this.board.canPlace(leftOfPlayer)
        && !this.board.canPlace(rightOfPlayer);
  }

  @Override
  public Iterable<GridCell<Character>> getPlayerTile() {
    return this.player;
  }

  @Override
  public Iterable<GridCell<Character>> getPlayer2Tile() {
    return this.player2;
  }

  @Override
  public Iterable<GridCell<Character>> getPlayer3Tile() {
    return this.player3;
  }

  @Override
  public Iterable<GridCell<Character>> getPlayer4Tile() {
    return this.player4;
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
      this.clockTick++;
      if (oneSecondRealTimeHasPassed()) {
        this.clock.tick();
      }
    }
  }

  private boolean oneSecondRealTimeHasPassed() {
    return this.clockTick % 2 == 0;
  }

  private void player4ClockTick() {
    // checks if the bomb has exploded and if it has it will remove the explosion
    // tiles, create a new bomb and reset the explosion timer
    if (this.explosionTimer4 == 1) {
      removeExplodedTiles(this.explodedBomb4);
      this.bomb4 = this.bombFactory.createNewBomb();
      this.explosionTimer4 = 0;
      this.player4BombCount--;
    }
    // checks if the bomb's timer has reached 3 ticks, and if it has it will explode
    if (this.bomb4.getClock() == 3) {
      explodeBomb(this.bomb4);
      this.explosionTimer4++;
    }
    // checks if the bomb is on the grid and not outside, if it is it will tick and
    // get added to the board
    if (this.board.positionIsOnGrid(this.bomb4.getPos())) {
      this.bomb4.tick();
    }

    damagePlayer(this.player4);

    // checks if the player is alive and if true it will move and place bombs
    if (isAlive(this.player4)) {
      moveAI(this.player4);
      if (this.random.nextInt(100) < 33) {
        placeBomb(this.player4, this.bomb4);
      }
    }

    // checks if the player is dead and if true it will remove the player from the
    // board
    removeDeadPlayerFromBoard(this.player4);
  }

  private void player3ClockTick() {
    // checks if the bomb has exploded and if it has it will remove the explosion
    // tiles, create a new bomb and reset the explosion timer
    if (this.explosionTimer3 == 1) {
      removeExplodedTiles(this.explodedBomb3);
      this.bomb3 = this.bombFactory.createNewBomb();
      this.explosionTimer3 = 0;
      this.player3BombCount--;
    }
    // checks if the bomb's timer has reached 3 ticks, and if it has it will explode
    if (this.bomb3.getClock() == 3) {
      explodeBomb(this.bomb3);
      this.explosionTimer3++;
    }
    // checks if the bomb is on the grid and not outside, if it is it will tick and
    // get added to the board
    if (this.board.positionIsOnGrid(this.bomb3.getPos())) {
      this.bomb3.tick();
    }

    // checks if the player is alive and true it will move and place bombs
    if (isAlive(this.player3)) {
      moveAI(this.player3);
      if (this.random.nextInt(100) < 33) {
        placeBomb(this.player3, this.bomb3);
      }
    }

    damagePlayer(this.player3);

    // checks if the player is dead and if true it will remove the player from the
    // board
    removeDeadPlayerFromBoard(this.player3);
  }

  private void player2ClockTick() {
    // checks if the bomb has exploded and if it has it will remove the explosion
    // tiles, create a new bomb and reset the explosion timer
    if (this.explosionTimer2 == 1) {
      removeExplodedTiles(this.explodedBomb2);
      this.bomb2 = this.bombFactory.createNewBomb();
      this.explosionTimer2 = 0;
      this.player2BombCount--;
    }
    // checks if the bomb's timer has reached 3 ticks, and if it has it will explode
    if (this.bomb2.getClock() == 3) {
      explodeBomb(this.bomb2);
      this.explosionTimer2++;
    }
    // checks if the bomb is on the grid and not outside, if it is it will tick and
    // get added to the board
    if (this.board.positionIsOnGrid(this.bomb2.getPos())) {
      this.bomb2.tick();
    }
    // checks if the player is alive, and if true it will move and place bombs
    if (isAlive(this.player2)) {
      moveAI(this.player2);
      if (this.random.nextInt(100) < 33) {
        placeBomb(this.player2, this.bomb2);
      }
    }

    damagePlayer(this.player2);

    // checks if the player is dead and if true it will remove the player from the
    // board
    removeDeadPlayerFromBoard(this.player2);
  }

  private void player1ClockTick() {
    // checks if the bomb has exploded and if it has it will remove the explosion
    // tiles, create a new bomb and reset the explosion timer
    if (this.explosionTimer == 1) {
      removeExplodedTiles(this.explodedBomb);
      this.bomb = this.bombFactory.createNewBomb();
      this.explosionTimer = 0;
      this.playerBombCount--;
    }
    // checks if the bomb's timer has reached 3 ticks, and if it has it will explode
    if (this.bomb.getClock() == 3) {
      explodeBomb(this.bomb);
      this.explosionTimer++;
    }
    // checks if the bomb is on the grid and not outside, if it is it will tick and
    // get added to the board
    if (this.board.positionIsOnGrid(this.bomb.getPos())) {
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
   * Checks if the player is on an explosion tile and if true it will damage
   * the player
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
    this.gameState = GameState.PAUSED_GAME;
  }

  @Override
  public void playGame() {
    this.gameState = GameState.ACTIVE_GAME;
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

    this.resetGame();
    this.board.createMap();
  }

  private void resetGame() {
    this.resetSprites();
    this.resetBombCounts();
    this.resetPlayerLives();
    this.createPlayers();
    this.createBombs();
    this.player1MoveCount = 0;
    this.clock = new Clock();
  }

  private void resetSprites() {
    this.player1Sprite = 0;
    this.player2Sprite = 0;
    this.player3Sprite = 0;
    this.player4Sprite = 0;
  }

  private void resetBombCounts() {
    this.playerBombCount = 0;
    this.player2BombCount = 0;
    this.player3BombCount = 0;
    this.player4BombCount = 0;
  }

  private void createPlayers() {
    this.player = new Player(new CellPosition(board.rows() - 2, 1));
    this.player2 = new PlayerAI(new CellPosition(1, 1), 'b');
    this.player3 = new PlayerAI(new CellPosition(board.rows() - 2, board.cols() - 2), 'r');
    this.player4 = new PlayerAI(new CellPosition(1, board.cols() - 2), 'p');
  }

  private void createBombs() {
    this.bomb = bombFactory.createNewBomb();
    this.bomb2 = bombFactory.createNewBomb();
    this.bomb3 = bombFactory.createNewBomb();
    this.bomb4 = bombFactory.createNewBomb();
  }

  private void resetPlayerLives() {
    this.playerLives = 3;
    this.player2Lives = 3;
    this.player3Lives = 3;
    this.player4Lives = 3;
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

  int getPlayer1MoveCount() {
    return this.player1MoveCount;
  }

  int getPlayerBombCount(IPlayer player) {
    try {
      if (player == this.player) {
        return this.playerBombCount;
      } else if (player == this.player2) {
        return this.player2BombCount;
      } else if (player == this.player3) {
        return this.player3BombCount;
      } else if (player == this.player4) {
        return this.player4BombCount;
      }
      if (player == null) {
        throw new IllegalArgumentException("Player cannot be null");
      }
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    }
    return 0;
  }

  Bomb getExplodedBomb() {
    return this.explodedBomb;
  }

  BombermanBoard getBoard() {
    return this.board;
  }

}
