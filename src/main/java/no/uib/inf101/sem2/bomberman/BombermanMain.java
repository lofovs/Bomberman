package no.uib.inf101.sem2.bomberman;

import javax.swing.JFrame;

import no.uib.inf101.sem2.bomberman.view.SampleView;

public class BombermanMain {
  public static void main(String[] args) {
    SampleView view = new SampleView();

    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setTitle("INF101");
    frame.setContentPane(view);
    frame.pack();
    frame.setVisible(true);
  }
}
