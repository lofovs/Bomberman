package no.uib.inf101.sem2.bomberman.model;

import no.uib.inf101.sem2.bomberman.model.player.Player;

/**
 * The interface for the testable bomberman model.
 * 
 */
public interface TestableBombermanModel {

    /**
     * Get the real time elapsed.
     *
     * @return the real time elapsed
     */
    int getRealTimeElapsed();

    /**
     * Get the board.
     *
     * @return the board
     */
    BombermanBoard getBoard();

    /**
     * Set the game state to an active game
     */
    void playGame();

    /**
     * Get the player with the given number.
     * 
     * @param playerNumber the number of the player
     * @return the player with the given number
     */
    Player getPlayer(int playerNumber);

    /**
     * Place a bomb for the given player.
     * 
     * @param player
     * @return true if the bomb was placed, false otherwise
     */
    boolean placeBomb(Player player);

    /**
     * Move player1 with the given deltaRow and deltaCol.
     * 
     * @param deltaRow
     * @param deltaCol
     * @return true if the player was moved, false otherwise
     */
    boolean movePlayer1(int deltaRow, int deltaCol);

    /**
     * The method that runs when the clock ticks
     */
    void clockTick();

    /**
     * Set the game state to the given state.
     * 
     * @param gameState
     */
    void setGameState(GameState gameState);

    /**
     * Start a new game.
     */
    void newGame();

    /**
     * Get the players.
     * 
     * @return the players
     */
    Player[] getPlayers();

    /**
     * Get the game state.
     * 
     * @return the game state
     */
    GameState getGameState();

    /**
     * Change the player sprite depending on the direction the player is moving
     * 
     * @param player   the player to change sprite for
     * @param deltaRow the row delta
     * @param deltaCol the column delta
     */
    void changePlayerSprite(Player player, int i, int j);
}
