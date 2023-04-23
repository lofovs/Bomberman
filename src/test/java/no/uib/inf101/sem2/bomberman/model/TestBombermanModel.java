package no.uib.inf101.sem2.bomberman.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import no.uib.inf101.sem2.bomberman.model.player.Player;
import no.uib.inf101.sem2.grid.CellPosition;
import no.uib.inf101.sem2.bomberman.model.player.HumanPlayer;

public class TestBombermanModel {

    @Test
    public void testPlaceBomb() {
        // Set up a test board and players
        BombermanBoard board = new BombermanBoard(10, 10);
        TestableBombermanModel model = new BombermanModel(board);
        Player player1 = model.getPlayer(1);

        // Test placing a bomb for player 1
        assertTrue(model.placeBomb(player1));
        assertEquals(1, player1.getBombCount());
        assertEquals(player1.getPos(), player1.getBomb().getPos());

        // Test placing a second bomb for player 1
        model.movePlayer1(-1, 0);
        assertFalse(model.placeBomb(player1));
        assertEquals(1, player1.getBombCount());
    }

    @Test
    public void testNewGame_ResetsGame() {
        BombermanBoard board = new BombermanBoard(10, 10);
        TestableBombermanModel model = new BombermanModel(board);

        // Set up the model with some state
        model.setGameState(GameState.PLAYER1_WON);

        model.newGame();

        // Check that the state has been reset
        assertEquals(GameState.ACTIVE_GAME, model.getGameState());
        assertEquals(4, model.getPlayers().length);
        assertEquals(0, model.getRealTimeElapsed());
        for (Player player : model.getPlayers()) {
            assertEquals(3, player.getLives());
            assertEquals(0, player.getBombCount());
        }
    }

    @Test
    public void testNewGame_player1WonState() {
        BombermanBoard board = new BombermanBoard(10, 10);
        TestableBombermanModel model = new BombermanModel(board);
        model.setGameState(GameState.PLAYER1_WON);

        model.newGame();

        assertEquals(GameState.ACTIVE_GAME, model.getGameState());
    }

    @Test
    public void testNewGame_player2WonState() {
        BombermanBoard board = new BombermanBoard(10, 10);
        TestableBombermanModel model = new BombermanModel(board);
        model.setGameState(GameState.PLAYER2_WON);

        model.newGame();

        assertEquals(GameState.ACTIVE_GAME, model.getGameState());
    }

    @Test
    public void testNewGame_player3WonState() {
        BombermanBoard board = new BombermanBoard(10, 10);
        TestableBombermanModel model = new BombermanModel(board);
        model.setGameState(GameState.PLAYER3_WON);

        model.newGame();

        assertEquals(GameState.ACTIVE_GAME, model.getGameState());
    }

    @Test
    public void testNewGame_player4WonState() {
        BombermanBoard board = new BombermanBoard(10, 10);
        TestableBombermanModel model = new BombermanModel(board);
        model.setGameState(GameState.PLAYER4_WON);

        model.newGame();

        assertEquals(GameState.ACTIVE_GAME, model.getGameState());
    }

    @Test
    public void testNewGame_drawState() {
        BombermanBoard board = new BombermanBoard(10, 10);
        TestableBombermanModel model = new BombermanModel(board);
        model.setGameState(GameState.DRAW);

        model.newGame();

        assertEquals(GameState.ACTIVE_GAME, model.getGameState());
    }

    @Test
    public void testNewGame_activeGameState() {
        BombermanBoard board = new BombermanBoard(10, 10);
        TestableBombermanModel model = new BombermanModel(board);

        model.newGame();

        assertEquals(GameState.NEW_GAME, model.getGameState());
    }

    @Test
    public void testNewGame_newGameState() {
        BombermanBoard board = new BombermanBoard(10, 10);
        TestableBombermanModel model = new BombermanModel(board);

        model.newGame();

        assertEquals(GameState.NEW_GAME, model.getGameState());
    }

    @Test
    void testMovePlayer1ValidMove() {
        BombermanBoard board = new BombermanBoard(10, 10);
        TestableBombermanModel model = new BombermanModel(board);
        model.newGame();
        assertTrue(model.movePlayer1(-1, 0));
    }

    @Test
    void testMovePlayer1InvalidMove() {
        BombermanBoard board = new BombermanBoard(10, 10);
        TestableBombermanModel model = new BombermanModel(board);
        model.newGame();
        assertFalse(model.movePlayer1(0, -1));
    }

    @Test
    void testMovePlayer1MoveCountExceeded() {
        BombermanBoard board = new BombermanBoard(10, 10);
        TestableBombermanModel model = new BombermanModel(board);
        model.newGame();
        ((HumanPlayer) model.getPlayers()[0]).incrementMoveCount();
        assertFalse(model.movePlayer1(1, 0));
    }

    @Test
    public void testClockTick_GameNotActive() {
        BombermanBoard board = new BombermanBoard(10, 10);
        TestableBombermanModel model = new BombermanModel(board);
        model.setGameState(GameState.NEW_GAME);
        model.clockTick();
        assertEquals(GameState.NEW_GAME, model.getGameState());
    }

    @Test
    public void testClockTick_AllPlayersDead() {
        BombermanBoard board = new BombermanBoard(10, 10);
        TestableBombermanModel model = new BombermanModel(board);

        model.setGameState(GameState.ACTIVE_GAME);

        for (Player player : model.getPlayers()) {
            for (int i = 0; i < 3; i++) {
                player.decrementLives();
            }
        }
        model.clockTick();
        assertEquals(GameState.DRAW, model.getGameState());
    }

    @Test
    public void testClockTick_Player1Wins() {
        BombermanBoard board = new BombermanBoard(10, 10);
        TestableBombermanModel model = new BombermanModel(board);

        model.setGameState(GameState.ACTIVE_GAME);
        for (int i = 0; i < 3; i++) {
            model.getPlayers()[1].decrementLives();
            model.getPlayers()[2].decrementLives();
            model.getPlayers()[3].decrementLives();
        }
        model.clockTick();
        assertEquals(GameState.PLAYER1_WON, model.getGameState());
    }

    @Test
    public void testClockTick_RealTimePassed() {
        BombermanBoard board = new BombermanBoard(10, 10);
        TestableBombermanModel model = new BombermanModel(board);
        model.setGameState(GameState.ACTIVE_GAME);
        model.clockTick();
        model.clockTick();
        assertEquals(1, model.getRealTimeElapsed());
    }

    @Test
    public void testClockTick_OneSecondNotPassed() {
        BombermanBoard board = new BombermanBoard(10, 10);
        TestableBombermanModel model = new BombermanModel(board);
        model.setGameState(GameState.ACTIVE_GAME);

        model.clockTick();
        assertEquals(0, model.getRealTimeElapsed());
    }

    @Test
    public void testClockTick_ExplodeBombAndDamagePlayer() {

        BombermanBoard board = new BombermanBoard(10, 10);
        TestableBombermanModel model = new BombermanModel(board);

        // Set the model state to ACTIVE_GAME and add a bomb to the board
        model.setGameState(GameState.ACTIVE_GAME);
        Player player1 = model.getPlayers()[0];

        assertEquals(3, player1.getLives());
        model.placeBomb(player1);

        // Call the clockTick method and verify that the bomb explodes after 3 ticks
        model.clockTick();
        assertEquals(GameState.ACTIVE_GAME, model.getGameState());
        assertEquals('B', model.getBoard().get(player1.getPos()));

        model.clockTick();
        assertEquals(GameState.ACTIVE_GAME, model.getGameState());
        assertEquals('B', model.getBoard().get(player1.getPos()));

        model.clockTick();
        model.clockTick();
        assertEquals(GameState.ACTIVE_GAME, model.getGameState());
        assertEquals('E', model.getBoard().get(player1.getPos()));

        model.clockTick();
        model.clockTick();
        assertEquals(GameState.ACTIVE_GAME, model.getGameState());
        assertEquals('-', model.getBoard().get(player1.getPos()));

        assertEquals(2, player1.getLives());
    }

}
