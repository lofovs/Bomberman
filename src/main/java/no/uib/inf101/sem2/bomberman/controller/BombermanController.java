package no.uib.inf101.sem2.bomberman.controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.Timer;

import no.uib.inf101.sem2.bomberman.model.GameState;
import no.uib.inf101.sem2.bomberman.sound.midi.BombermanSong;
import no.uib.inf101.sem2.bomberman.view.BombermanView;

public class BombermanController implements java.awt.event.KeyListener {

  private ControllableBombermanModel model;
  private BombermanView view;
  private Timer timer;
  private BombermanSong song;

  public BombermanController(
      ControllableBombermanModel model,
      BombermanView view) {
    this.model = model;
    this.view = view;
    this.view.addKeyListener(this);
    this.timer = new Timer(model.getTimerInterval(), this::clockTick);
    this.timer.start();
    this.song = new BombermanSong("battle.mid");
    // this.song.run();
  }

  @Override
  public void keyTyped(KeyEvent e) {
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (this.model.getPlayer(1).getLives() > 0 &&
        this.model.getGameState() == GameState.ACTIVE_GAME) {
      if (e.getKeyCode() == KeyEvent.VK_UP) {
        model.movePlayer(-1, 0);
        model.changePlayerSprite(this.model.getPlayer(1), -1, 0);
      } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
        model.movePlayer(1, 0);
        model.changePlayerSprite(this.model.getPlayer(1), 1, 0);
      } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
        model.movePlayer(0, -1);
        model.changePlayerSprite(this.model.getPlayer(1), 0, -1);
      } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
        model.movePlayer(0, 1);
        model.changePlayerSprite(this.model.getPlayer(1), 0, 1);
      } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
        model.placeBomb(model.getPlayer(1));
      }
    }

    if (this.model.getGameState() == GameState.NEW_GAME) {
      if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        this.model.playGame();
      }
    }

    if (this.model.getGameState() == GameState.PLAYER1_WON ||
        this.model.getGameState() == GameState.PLAYER2_WON ||
        this.model.getGameState() == GameState.PLAYER3_WON ||
        this.model.getGameState() == GameState.PLAYER4_WON ||
        this.model.getGameState() == GameState.DRAW) {
      if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        this.model.newGame();
      }
    }

    if (e.getKeyCode() == KeyEvent.VK_P) {
      if (this.model.getGameState() == GameState.ACTIVE_GAME) {
        this.model.pauseGame();
        this.song.pause();
      } else if (this.model.getGameState() == GameState.PAUSED_GAME) {
        this.model.playGame();
        this.song.doUnpauseMidiSounds();
      }
    }

    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
      System.exit(0);
    } else if (e.getKeyCode() == KeyEvent.VK_M) {
      if (this.song.isRunning()) {
        this.song.pause();
      } else {
        this.song.doUnpauseMidiSounds();
      }
    }
    this.view.repaint();
  }

  @Override
  public void keyReleased(KeyEvent e) {
  }

  /**
   * Event that is called when the timer ticks.
   */
  private void clockTick(ActionEvent ae) {
    this.model.clockTick();
    setTimerDelay();
    this.view.repaint();
  }

  /**
   * Sets the timer delay to the value in the model
   */
  private void setTimerDelay() {
    this.timer.setInitialDelay(this.model.getTimerInterval());
    this.timer.setDelay(this.model.getTimerInterval());
  }
}
