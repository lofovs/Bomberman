package no.uib.inf101.sem2.bomberman.controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.Timer;
import no.uib.inf101.sem2.bomberman.midi.BombermanSong;
import no.uib.inf101.sem2.bomberman.model.GameState;
import no.uib.inf101.sem2.bomberman.view.BombermanView;

public class BombermanController implements java.awt.event.KeyListener {

  private ControllableBombermanModel model;
  private BombermanView view;
  private Timer timer;
  private BombermanSong song;

  public BombermanController(
    ControllableBombermanModel model,
    BombermanView view
  ) {
    this.model = model;
    this.view = view;
    this.view.addKeyListener(this);
    this.timer = new Timer(model.getTimerInterval(), this::clockTick);
    this.timer.start();
    this.song = new BombermanSong();
    // this.song.run();
  }

  @Override
  public void keyTyped(KeyEvent e) {}

  @Override
  public void keyPressed(KeyEvent e) {
    if (this.model.getPlayerLives() > 0) {
      if (e.getKeyCode() == KeyEvent.VK_UP) {
        model.movePlayer(-1, 0);
      } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
        model.movePlayer(1, 0);
      } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
        model.movePlayer(0, -1);
      } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
        model.movePlayer(0, 1);
      } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
        model.placeBomb(model.getPlayer(), model.getBomb());
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
    } else if (e.getKeyCode() == KeyEvent.VK_P) {
      if (this.model.getGameState() == GameState.ACTIVE_GAME) {
        this.model.pauseGame();
        this.song.pause();
      } else if (this.model.getGameState() == GameState.PAUSED_GAME) {
        this.model.playGame();
        this.song.doUnpauseMidiSounds();
      }
    }
    view.repaint();
  }

  @Override
  public void keyReleased(KeyEvent e) {}

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
