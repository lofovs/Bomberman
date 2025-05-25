package no.uib.inf101.sem2.bomberman;

import javax.swing.JFrame;
import no.uib.inf101.sem2.bomberman.controller.BombermanController;
import no.uib.inf101.sem2.bomberman.model.BombermanBoard;
import no.uib.inf101.sem2.bomberman.model.BombermanModel;
import no.uib.inf101.sem2.bomberman.view.BombermanView;

public class Main {

  public static void main(String[] args) {
    BombermanBoard board = new BombermanBoard(13, 11);

    BombermanModel model = new BombermanModel(board);
    BombermanView view = new BombermanView(model);
    new BombermanController(model, view);

    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setTitle("INF101 BOMBERMAN");
    frame.setContentPane(view);
    frame.pack();
    frame.setVisible(true);
    frame.setResizable(false);
  }
}
