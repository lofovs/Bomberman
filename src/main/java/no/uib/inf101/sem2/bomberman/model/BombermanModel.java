package no.uib.inf101.sem2.bomberman.model;

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import no.uib.inf101.sem2.bomberman.controller.ControllableBombermanModel;
import no.uib.inf101.sem2.bomberman.model.bomb.Bomb;
import no.uib.inf101.sem2.bomberman.model.player.Player;
import no.uib.inf101.sem2.bomberman.model.player.HumanPlayer;
import no.uib.inf101.sem2.bomberman.model.player.AIPlayer;
import no.uib.inf101.sem2.bomberman.view.ViewableBombermanModel;
import no.uib.inf101.sem2.grid.CellPosition;
import no.uib.inf101.sem2.grid.GridCell;
import no.uib.inf101.sem2.grid.GridDimension;

public class BombermanModel
    implements ViewableBombermanModel, ControllableBombermanModel {

  private BombermanBoard board;

  private Player[] players = new Player[4];

  private GameState gameState;

  private Clock clock;
  private int clockTick;

  private static final CellPosition PLAYER_DEAD_POS = new CellPosition(-2, -2);

  private Random random;

  public BombermanModel(BombermanBoard board) {
    this.board = board;
    this.gameState = GameState.NEW_GAME;
    this.random = new Random();
    this.newGame();

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
  public boolean placeBomb(Player player) {
    Bomb newBomb = player.getBomb().shiftedToPosition(player.getPos());

    if (this.board.canPlace(newBomb.getPos())) {
      if (player.getBombCount() < 1) {
        player.setBomb(newBomb);
        addBombToBoard(newBomb);
        player.incrementBombCount();
        return true;
      }
    }
    return false;
  }

  private void addBombToBoard(Bomb bomb) {
    for (GridCell<Character> gridCell : bomb) {
      this.board.set(gridCell.pos(), gridCell.value());
    }
  }

  /**
   * Explodes the bomb, creating an explosion covering the bomb tile and the 4
   * adjacent tiles.
   * Gets a new bomb for the player and sets the player's exploded bomb to the old
   * bomb.
   * 
   * @param player the player who placed the bomb
   */
  void explodeBomb(Player player) {
    Bomb bomb = player.getBomb();
    for (GridCell<Character> gridCell : bomb) {
      setExplodedTile(gridCell.pos());
      for (Direction direction : Direction.values()) {
        CellPosition adjacentPos = gridCell.pos().shiftedBy(direction);
        if (this.board.isDestructible(adjacentPos)) {
          setExplodedTile(adjacentPos);
        }
      }
    }
    player.getNewBomb();
    player.setExplodedBomb(bomb);
  }

  private void setExplodedTile(CellPosition pos) {
    this.board.set(pos, 'E');
  }

  @Override
  public boolean movePlayer(int deltaRow, int deltaCol) {
    if (((HumanPlayer) this.players[0]).getMoveCount() < 1) {
      CellPosition newPosition = players[0].getPos().shiftedBy(deltaRow, deltaCol);
      if (this.board.canPlace(newPosition)) {
        this.players[0].setPosition(newPosition);
        ((HumanPlayer) this.players[0]).incrementMoveCount();
        damagePlayer(this.players[0]);
        return true;
      }
    }
    return false;
  }

  private void moveAI(AIPlayer playerAI) {
    int deltaRow = 0;
    int deltaCol = 0;

    // Choose a random direction to move in
    Direction direction = chooseRandomDirection(playerAI.getPos());
    if (direction != null) {
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
    } else {
      deltaRow = 0;
      deltaCol = 0;
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
    CellPosition newPositionAI = playerAI.getPos().shiftedBy(deltaRow, deltaCol);
    if (canMoveToPosition(newPositionAI)) {
      updatePlayerPositionAndDamage(playerAI, newPositionAI, deltaRow, deltaCol);
      return;
    }

    // If the AI is boxed in, do not move.
    if (isBoxedIn(playerAI.getPos())) {
      return;
    }

    // If the AI fails to move, try again 3 times.
    for (int tries = 0; tries < 3; tries++) {
      moveAI(playerAI);
    }
  }

  private void updatePlayerPositionAndDamage(AIPlayer playerAI, CellPosition newPosition, int deltaRow, int deltaCol) {
    playerAI.setPosition(newPosition);
    changePlayerSprite(playerAI, deltaRow, deltaCol);
    damagePlayer(playerAI);
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
    for (Player player : players) {
      Bomb bomb = player.getBomb();
      if (positionToCheck.equals(bomb.getPos())) {
        return true;
      }
    }
    return this.board.isPotentialExplosion(positionToCheck)
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
  public Iterable<GridCell<Character>> getPlayerTile(int playerNumber) {
    return this.players[playerNumber - 1];
  }

  @Override
  public int getTimerInterval() {
    return 500;
  }

  @Override
  public void clockTick() {
    if (this.gameState == GameState.ACTIVE_GAME) {
      for (Player player : this.players) {
        playerClockTick(player);
      }
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

  private void playerClockTick(Player player) {
    // checks if the bomb has exploded and if it has it will remove the explosion
    // tiles, create a new bomb and reset the explosion timer
    if (player.getExplosionTimer() == 1) {
      removeExplodedTiles(player.getExplodedBomb());
      player.getNewBomb();
      player.resetExplosionTimer();
      player.decrementBombCount();
    }
    // checks if the bomb's timer has reached 3 ticks, and if it has it will explode
    if (player.getBomb().getClock() == 3) {
      explodeBomb(player);
      player.incrementExplosionTimer();
    }
    // checks if the bomb is on the grid and not outside, if it is it will tick

    if (this.board.positionIsOnGrid(player.getBomb().getPos())) {
      player.getBomb().tick();
    }

    damagePlayer(player);

    // checks if the player is alive and if true it will move and place bombs
    if (player instanceof AIPlayer) {
      if (isAlive(player)) {
        moveAI((AIPlayer) player);
        if (this.random.nextInt(100) < 33) {
          placeBomb(player);
        }
      }
    }

    if (player instanceof HumanPlayer) {
      ((HumanPlayer) player).resetMoveCount();
    }

    // checks if the player is dead and if true it will remove the player from the
    // board
    removeDeadPlayerFromBoard(player);
  }

  private void checkWin() {
    int numPlayers = this.players.length;
    int[] playerLives = new int[this.players.length];
    for (int i = 0; i < this.players.length; i++) {
      playerLives[i] = this.players[i].getLives();
    }
    int numAlivePlayers = 0;
    int lastAlivePlayerIndex = -1;

    // Count the number of alive players and the last alive player index
    for (int i = 0; i < numPlayers; i++) {
      if (playerLives[i] > 0) {
        numAlivePlayers++;
        lastAlivePlayerIndex = i;
      }
    }

    // Set the game state based on the number of alive players
    switch (numAlivePlayers) {
      case 1:
        switch (lastAlivePlayerIndex) {
          case 0:
            this.gameState = GameState.PLAYER1_WON;
            break;
          case 1:
            this.gameState = GameState.PLAYER2_WON;
            break;
          case 2:
            this.gameState = GameState.PLAYER3_WON;
            break;
          case 3:
            this.gameState = GameState.PLAYER4_WON;
            break;
        }
        break;
      case 0:
        this.gameState = GameState.DRAW;
        break;
    }

    // Check for end of game based on time
    if (this.clock.getTime() == 0 && this.gameState == GameState.ACTIVE_GAME) {
      this.gameState = GameState.DRAW;
    }
  }

  @Override
  public void changePlayerSprite(Player player, int row, int col) {
    if (row == 1) {
      player.setSprite(Direction.DOWN);
    } else if (row == -1) {
      player.setSprite(Direction.UP);
    } else if (col == 1) {
      player.setSprite(Direction.RIGHT);
    } else if (col == -1) {
      player.setSprite(Direction.LEFT);
    }
  }

  /**
   * Checks if the player is on an explosion tile and if true it will damage
   * the player
   * 
   * @param player
   */
  private void damagePlayer(Player player) {
    if (isAlive(player)) {
      if (this.board.isExplosion(player.getPos())) {
        player.decrementLives();
      }
    }
  }

  /**
   * Checks if the given player is alive
   * 
   * @param player the player to check
   * @return true if the player is alive, false otherwise
   */
  private boolean isAlive(Player player) {
    return player.getLives() > 0;
  }

  /**
   * Replaces the explosion tiles with empty tiles, if the tiles are destructible
   * 
   * @param bomb the bomb that has exploded
   */
  private void removeExplodedTiles(Bomb explodedBomb) {
    Set<CellPosition> affectedCells = new HashSet<>();
    for (GridCell<Character> gridCell : explodedBomb) {
      affectedCells.add(gridCell.pos());
      CellPosition[] neighbors = new CellPosition[] {
          new CellPosition(gridCell.pos().row() - 1, gridCell.pos().col()),
          new CellPosition(gridCell.pos().row() + 1, gridCell.pos().col()),
          new CellPosition(gridCell.pos().row(), gridCell.pos().col() - 1),
          new CellPosition(gridCell.pos().row(), gridCell.pos().col() + 1)
      };
      for (CellPosition neighbor : neighbors) {
        if (this.board.isDestructible(neighbor)) {
          affectedCells.add(neighbor);
        }
      }
    }
    for (CellPosition affectedCell : affectedCells) {
      this.board.set(affectedCell, '-');
    }
  }

  /**
   * Checks if the player is dead, and if true it will remove the player from the
   * board
   * 
   * @param player the dead player
   * @return the dead player
   */
  private void removeDeadPlayerFromBoard(Player player) {
    if (!isAlive(player)) {
      player.setPosition(PLAYER_DEAD_POS);
    }
  }

  @Override
  public Iterable<GridCell<Character>> getBombTile(int bombNumber) {
    return this.players[bombNumber - 1].getBomb();
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
    this.createPlayers();
    this.resetBombCounts();
    this.resetPlayerLives();
    this.resetSprites();
    ((HumanPlayer) this.players[0]).resetMoveCount();
    this.clock = new Clock();
  }

  private void resetSprites() {
    for (Player player : this.players) {
      player.setSprite(Direction.DOWN);
    }
  }

  private void resetBombCounts() {
    for (Player player : this.players) {
      player.resetBombCount();
    }
  }

  private void createPlayers() {
    players[0] = new HumanPlayer(new CellPosition(board.rows() - 2, 1));
    players[1] = new AIPlayer(new CellPosition(1, 1), 'b');
    players[2] = new AIPlayer(new CellPosition(board.rows() - 2, board.cols() - 2), 'r');
    players[3] = new AIPlayer(new CellPosition(1, board.cols() - 2), 'p');

  }

  private void resetPlayerLives() {
    for (Player player : this.players) {
      player.resetLives();
    }
  }

  @Override
  public String getTime() {
    String timeString = "" + this.clock.getTime();
    return timeString;
  }

  BombermanBoard getBoard() {
    return this.board;
  }

  @Override
  public Player getPlayer(int playerNumber) {
    return this.players[playerNumber - 1];
  }

  @Override
  public Player[] getPlayers() {
    return this.players;
  }

}
