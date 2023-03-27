package no.uib.inf101.sem2.bomberman;

import javax.swing.JFrame;

import no.uib.inf101.sem2.bomberman.model.BombermanBoard;
import no.uib.inf101.sem2.bomberman.model.BombermanModel;
import no.uib.inf101.sem2.bomberman.view.BombermanView;
import no.uib.inf101.sem2.bomberman.view.ViewableBombermanModel;
import no.uib.inf101.sem2.grid.CellPosition;

public class BombermanMain {
  public static void main(String[] args) {

    BombermanBoard board = new BombermanBoard(13, 11);

    // test case
    // fill the outer walls with 'G'

    for (int i = 0; i < board.getRows(); i++) {
      for (int j = 0; j < board.getCols(); j++) {
        if (i == 0 || i == board.getRows() - 1 || j == 0 || j == board.getCols() - 1) {
          board.set(new CellPosition(i, j), 'G');
        }
      }
    }

    ViewableBombermanModel model = new BombermanModel(board);
    BombermanView view = new BombermanView(model);

    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setTitle("BOMBERMAN");
    frame.setContentPane(view);
    frame.pack();
    frame.setVisible(true);
  }
}
