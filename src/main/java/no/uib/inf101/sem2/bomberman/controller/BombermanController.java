package no.uib.inf101.sem2.bomberman.controller;

import java.awt.event.KeyEvent;
import java.util.Timer;

import no.uib.inf101.sem2.bomberman.midi.BombermanSong;
import no.uib.inf101.sem2.bomberman.view.BombermanView;

public class BombermanController implements java.awt.event.KeyListener {

    private ControllableBombermanModel model;
    private BombermanView view;
    private Timer timer;
    private BombermanSong song;

    public BombermanController(ControllableBombermanModel model, BombermanView view) {
        this.model = model;
        this.view = view;
        this.view.addKeyListener(this);

        this.timer = new Timer();
        this.song = new BombermanSong();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            model.movePlayer(0, -1);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            model.movePlayer(0, 1);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            model.movePlayer(-1, 0);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            model.movePlayer(1, 0);
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            model.placeBomb();
        }
        view.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    }

}
