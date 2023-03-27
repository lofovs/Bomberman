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
    board.set(new CellPosition(0, 0), 'g');
    board.set(new CellPosition(0, 10), 'b');
    board.set(new CellPosition(12, 0), 'r');
    board.set(new CellPosition(12, 10), 'y');

    ViewableBombermanModel model = new BombermanModel(board);
    BombermanView view = new BombermanView(model);

    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setTitle("INF101");
    frame.setContentPane(view);
    frame.pack();
    frame.setVisible(true);
  }
}
