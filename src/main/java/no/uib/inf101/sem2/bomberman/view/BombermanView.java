package no.uib.inf101.sem2.bomberman.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import no.uib.inf101.sem2.bomberman.model.player.Player;
import no.uib.inf101.sem2.grid.CellPosition;
import no.uib.inf101.sem2.grid.GridCell;
import no.uib.inf101.sem2.grid.GridDimension;

public class BombermanView extends JPanel {

  private static final double OUTERMARGIN = 0;
  private static final double INNERMARGIN = 2;
  private static final double SQUARESIZE = 30;
  private ViewableBombermanModel model;
  private ColorTheme theme;
  private double width;
  private double height;

  /** Construct a new BombermanView */
  public BombermanView(ViewableBombermanModel model) {
    this.model = model;
    this.setFocusable(true);
    this.width =
      (SQUARESIZE + INNERMARGIN) *
      model.getDimension().cols() +
      INNERMARGIN +
      OUTERMARGIN;
    this.height =
      (SQUARESIZE + INNERMARGIN) *
      model.getDimension().rows() +
      INNERMARGIN +
      OUTERMARGIN;
    this.setPreferredSize(new Dimension((int) width, (int) height));
    this.theme = new DefaultColorTheme();
    this.setBackground(getBackground());
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    drawGame(g2);
  }

  /**
   * Draws the game
   *
   * @param g2 the Graphics2D object to draw on
   */
  public void drawGame(Graphics2D g2) {
    // variables for the board
    double x = OUTERMARGIN;
    double y = OUTERMARGIN;
    double width = this.getWidth() - (2 * OUTERMARGIN);
    double height = this.getHeight() - (2 * OUTERMARGIN);

    Rectangle2D tileRectangle = new Rectangle2D.Double(x, y, width, height);
    g2.setColor(theme.getFrameColor());
    g2.fill(tileRectangle);

    GridDimension gd = model.getDimension();

    BufferedImage image = Inf101Graphics.loadImageFromResources(
      "bomberman.png"
    );
    Iterable<GridCell<Character>> grid = model.getTilesOnBoard();

    Iterable<GridCell<Character>> playerModel = model.getPlayerTile();
    Iterable<GridCell<Character>> player2Model = model.getPlayer2Tile();
    Iterable<GridCell<Character>> player3Model = model.getPlayer3Tile();
    Iterable<GridCell<Character>> player4Model = model.getPlayer4Tile();

    Iterable<GridCell<Character>> bombModel = model.getBombTile();
    CellPositionToPixelConverter cellPositionToPixelConverter = new CellPositionToPixelConverter(
      tileRectangle,
      gd,
      INNERMARGIN
    );

    // draws the grid
    drawCells(g2, grid, cellPositionToPixelConverter, theme);

    // draws the players
    drawCells(g2, playerModel, cellPositionToPixelConverter, theme);
    drawCells(g2, player2Model, cellPositionToPixelConverter, theme);
    drawCells(g2, player3Model, cellPositionToPixelConverter, theme);
    drawCells(g2, player4Model, cellPositionToPixelConverter, theme);

    // draws the bombs
    drawCells(g2, bombModel, cellPositionToPixelConverter, theme);
    // TODO: draws the player image

    // Player player = model.getPlayer();
    // double playerX =
    // cellPositionToPixelConverter.getBoundsForCell(player.getPos()).getBounds().x;
    // double playerY =
    // cellPositionToPixelConverter.getBoundsForCell(player.getPos()).getBounds().y;

    // double playerWidth =
    // cellPositionToPixelConverter.getBoundsForCell(player.getPos()).getBounds().width;
    // double playerHeight =
    // cellPositionToPixelConverter.getBoundsForCell(player.getPos()).getBounds().height;
    // double playerScale = 0.05 * (playerWidth / playerHeight);

    // Inf101Graphics.drawImage(g2, image, playerX, playerY, playerScale);
  }

  private void drawCells(
    Graphics2D g2,
    Iterable<GridCell<Character>> cells,
    CellPositionToPixelConverter cellPositionToPixelConverter,
    ColorTheme theme2
  ) {
    for (GridCell<Character> cell : cells) {
      Character value = cell.value();
      CellPosition pos = cell.pos();
      Rectangle2D r = cellPositionToPixelConverter.getBoundsForCell(pos);
      Color color = theme.getCellColor(value);

      g2.setColor(color);
      g2.fill(r);
    }
  }
}
