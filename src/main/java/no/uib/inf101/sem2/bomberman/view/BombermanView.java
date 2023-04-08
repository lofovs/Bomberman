package no.uib.inf101.sem2.bomberman.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import no.uib.inf101.sem2.bomberman.model.GameState;
import no.uib.inf101.sem2.grid.CellPosition;
import no.uib.inf101.sem2.grid.GridCell;
import no.uib.inf101.sem2.grid.GridDimension;

public class BombermanView extends JPanel {

  private static final double OUTERMARGIN = 0;
  private static final double INNERMARGIN = 2;
  private static final double SQUARESIZE = 30;
  private static final double SCOREBOARDHEIGHT = 50;

  private ViewableBombermanModel model;
  private ColorTheme theme;
  private double width;
  private double height;

  private BufferedImage bombermanLogo;
  private BufferedImage bombImage;
  private BufferedImage wallImage;
  private BufferedImage brokenWallImage;
  private BufferedImage floorImage;
  private BufferedImage explosionImage;

  private BufferedImage player1FrontImage;
  private BufferedImage player1BackImage;
  private BufferedImage player1LeftImage;
  private BufferedImage player1RightImage;

  private BufferedImage player2FrontImage;
  private BufferedImage player2BackImage;
  private BufferedImage player2LeftImage;
  private BufferedImage player2RightImage;

  private BufferedImage player3FrontImage;
  private BufferedImage player3BackImage;
  private BufferedImage player3LeftImage;
  private BufferedImage player3RightImage;

  private BufferedImage player4FrontImage;
  private BufferedImage player4BackImage;
  private BufferedImage player4LeftImage;
  private BufferedImage player4RightImage;

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
      OUTERMARGIN +
      SCOREBOARDHEIGHT;
    this.setPreferredSize(new Dimension((int) width, (int) height));
    this.theme = new DefaultColorTheme();
    this.setBackground(getBackground());

    this.bombermanLogo =
      Inf101Graphics.loadImageFromResources("bombermanLogo.png");
    this.bombImage = Inf101Graphics.loadImageFromResources("bomb.png");

    this.wallImage = Inf101Graphics.loadImageFromResources("wall.png");
    this.brokenWallImage =
      Inf101Graphics.loadImageFromResources("wallBroken.png");
    this.floorImage = Inf101Graphics.loadImageFromResources("floor.png");
    this.explosionImage =
      Inf101Graphics.loadImageFromResources("explosion.png");

    this.player1FrontImage =
      Inf101Graphics.loadImageFromResources("player1Front.png");

    this.player2FrontImage =
      Inf101Graphics.loadImageFromResources("player2Front.png");

    this.player3FrontImage =
      Inf101Graphics.loadImageFromResources("player3Front.png");

    this.player4FrontImage =
      Inf101Graphics.loadImageFromResources("player4Front.png");
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
    double gameWidth = this.getWidth() - (2 * OUTERMARGIN);
    double gameHeight = this.getHeight() - (2 * OUTERMARGIN) - SCOREBOARDHEIGHT;
    double windowWidth = this.getWidth();
    double windowHeight = this.getHeight();

    // variables for the scoreboard
    int playerLives = model.getPlayerLives();
    int player2Lives = model.getPlayer2Lives();
    int player3Lives = model.getPlayer3Lives();
    int player4Lives = model.getPlayer4Lives();
    double textWidth = gameWidth / 5;
    double textHeight = SCOREBOARDHEIGHT / 2;
    double textX = x;
    double textY = y + gameHeight + textHeight;

    GridDimension gd = model.getDimension();

    Iterable<GridCell<Character>> grid = model.getTilesOnBoard();

    Iterable<GridCell<Character>> playerModel = model.getPlayerTile();
    Iterable<GridCell<Character>> player2Model = model.getPlayer2Tile();
    Iterable<GridCell<Character>> player3Model = model.getPlayer3Tile();
    Iterable<GridCell<Character>> player4Model = model.getPlayer4Tile();

    Iterable<GridCell<Character>> bomb1Model = model.getBombTile();
    Iterable<GridCell<Character>> bomb2Model = model.getBomb2Tile();
    Iterable<GridCell<Character>> bomb3Model = model.getBomb3Tile();
    Iterable<GridCell<Character>> bomb4Model = model.getBomb4Tile();

    Rectangle2D windowRectangle = new Rectangle2D.Double(
      0,
      0,
      windowWidth,
      windowHeight
    );

    Rectangle2D tileRectangle = new Rectangle2D.Double(
      x,
      y,
      gameWidth,
      gameHeight
    );

    CellPositionToPixelConverter cellPositionToPixelConverter = new CellPositionToPixelConverter(
      tileRectangle,
      gd,
      INNERMARGIN
    );

    if (this.model.getGameState() == GameState.NEW_GAME) {
      // draws a black background
      g2.setColor(Color.BLACK);
      g2.fill(windowRectangle);

      // draws the bomberman logo in the upper middle of the screen
      Inf101Graphics.drawCenteredImage(
        g2,
        bombermanLogo,
        (x + windowWidth / 2),
        (y + windowHeight / 3),
        1.5
      );

      // draws the new game text
      g2.setColor(theme.getNewGameTextColor());
      g2.setFont(new Font("Monospaced", Font.BOLD, 20));
      Inf101Graphics.drawCenteredString(
        g2,
        "PRESS ENTER TO PLAY",
        tileRectangle.getX(),
        tileRectangle.getY(),
        gameWidth,
        gameHeight * 1.5
      );
    }

    if (this.model.getGameState() == GameState.ACTIVE_GAME) {
      // draws the board
      g2.setColor(theme.getFrameColor());
      g2.fill(tileRectangle);

      // draws the grid
      drawImageCells(g2, grid, cellPositionToPixelConverter, theme);
      // drawCells(g2, grid, cellPositionToPixelConverter, theme);

      // draws the players
      drawCells(
        g2,
        playerModel,
        cellPositionToPixelConverter,
        player1FrontImage
      );
      drawCells(
        g2,
        player2Model,
        cellPositionToPixelConverter,
        player2FrontImage
      );
      drawCells(
        g2,
        player3Model,
        cellPositionToPixelConverter,
        player3FrontImage
      );
      drawCells(
        g2,
        player4Model,
        cellPositionToPixelConverter,
        player4FrontImage
      );

      // draws the bombs
      drawCells(g2, bomb1Model, cellPositionToPixelConverter, bombImage);
      drawCells(g2, bomb2Model, cellPositionToPixelConverter, bombImage);
      drawCells(g2, bomb3Model, cellPositionToPixelConverter, bombImage);
      drawCells(g2, bomb4Model, cellPositionToPixelConverter, bombImage);

      // draw the scoreboard under the game
      Rectangle2D scoreboardRectangle = new Rectangle2D.Double(
        x,
        y + gameHeight,
        gameWidth,
        SCOREBOARDHEIGHT
      );
      g2.setColor(theme.getScoreBoardColor());
      g2.fill(scoreboardRectangle);

      g2.setFont(new Font("Arial", Font.BOLD, 20));
      g2.setColor(theme.getScoreBoardTextColor());

      //draw the player lives
      g2.drawString(
        "" + playerLives,
        (float) (textX + textWidth),
        (float) textY
      );
      g2.drawString(
        "" + player2Lives,
        (float) (textX + 2 * textWidth),
        (float) textY
      );
      g2.drawString(
        "" + player3Lives,
        (float) (textX + 3 * textWidth),
        (float) textY
      );
      g2.drawString(
        "" + player4Lives,
        (float) (textX + 4 * textWidth),
        (float) textY
      );
    }

    if (this.model.getGameState() == GameState.PLAYER1_WON) {
      // draws a black background
      g2.setColor(Color.BLACK);
      g2.fill(windowRectangle);

      // draws the player 1 won text
      g2.setColor(theme.getGameWonTextColor());
      g2.setFont(new Font("Monospaced", Font.BOLD, 20));
      Inf101Graphics.drawCenteredString(
        g2,
        "PLAYER 1 WON",
        tileRectangle.getX(),
        tileRectangle.getY(),
        gameWidth,
        gameHeight
      );

      // draws the new game text
      g2.setColor(theme.getNewGameTextColor());
      g2.setFont(new Font("Monospaced", Font.BOLD, 20));
      Inf101Graphics.drawCenteredString(
        g2,
        "PRESS ENTER TO PLAY AGAIN",
        tileRectangle.getX(),
        tileRectangle.getY(),
        gameWidth,
        gameHeight * 1.5
      );
    }

    if (this.model.getGameState() == GameState.PLAYER2_WON) {
      // draws a black background
      g2.setColor(Color.BLACK);
      g2.fill(windowRectangle);

      // draws the player 2 won text
      g2.setColor(theme.getGameWonTextColor());
      g2.setFont(new Font("Monospaced", Font.BOLD, 20));
      Inf101Graphics.drawCenteredString(
        g2,
        "PLAYER 2 WON",
        tileRectangle.getX(),
        tileRectangle.getY(),
        gameWidth,
        gameHeight
      );

      // draws the new game text
      g2.setColor(theme.getNewGameTextColor());
      g2.setFont(new Font("Monospaced", Font.BOLD, 20));
      Inf101Graphics.drawCenteredString(
        g2,
        "PRESS ENTER TO PLAY AGAIN",
        tileRectangle.getX(),
        tileRectangle.getY(),
        gameWidth,
        gameHeight * 1.5
      );
    }

    if (this.model.getGameState() == GameState.PLAYER3_WON) {
      // draws a black background
      g2.setColor(Color.BLACK);
      g2.fill(windowRectangle);

      // draws the player 3 won text
      g2.setColor(theme.getGameWonTextColor());
      g2.setFont(new Font("Monospaced", Font.BOLD, 20));
      Inf101Graphics.drawCenteredString(
        g2,
        "PLAYER 3 WON",
        tileRectangle.getX(),
        tileRectangle.getY(),
        gameWidth,
        gameHeight
      );

      // draws the new game text
      g2.setColor(theme.getNewGameTextColor());
      g2.setFont(new Font("Monospaced", Font.BOLD, 20));
      Inf101Graphics.drawCenteredString(
        g2,
        "PRESS ENTER TO PLAY AGAIN",
        tileRectangle.getX(),
        tileRectangle.getY(),
        gameWidth,
        gameHeight * 1.5
      );
    }

    if (this.model.getGameState() == GameState.PLAYER4_WON) {
      // draws a black background
      g2.setColor(Color.BLACK);
      g2.fill(windowRectangle);

      // draws the player 4 won text
      g2.setColor(theme.getGameWonTextColor());
      g2.setFont(new Font("Monospaced", Font.BOLD, 20));
      Inf101Graphics.drawCenteredString(
        g2,
        "PLAYER 4 WON",
        tileRectangle.getX(),
        tileRectangle.getY(),
        gameWidth,
        gameHeight
      );

      // draws the new game text
      g2.setColor(theme.getNewGameTextColor());
      g2.setFont(new Font("Monospaced", Font.BOLD, 20));
      Inf101Graphics.drawCenteredString(
        g2,
        "PRESS ENTER TO PLAY AGAIN",
        tileRectangle.getX(),
        tileRectangle.getY(),
        gameWidth,
        gameHeight * 1.5
      );
    }
  }

  private void drawCells(
    Graphics2D g2,
    Iterable<GridCell<Character>> cells,
    CellPositionToPixelConverter cellPositionToPixelConverter,
    ColorTheme theme
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

  private void drawCells(
    Graphics2D g2,
    Iterable<GridCell<Character>> cells,
    CellPositionToPixelConverter cellPositionToPixelConverter,
    BufferedImage image
  ) {
    for (GridCell<Character> cell : cells) {
      CellPosition pos = cell.pos();
      Rectangle2D r = cellPositionToPixelConverter.getBoundsForCell(pos);
      double imageX = r.getX() + r.getWidth() / 2;
      double imageY = r.getY() + r.getHeight() / 2;

      Inf101Graphics.drawCenteredImage(g2, image, imageX, imageY, 1.0);
    }
  }

  private void drawImageCells(
    Graphics2D g2,
    Iterable<GridCell<Character>> cells,
    CellPositionToPixelConverter cellPositionToPixelConverter,
    ColorTheme theme
  ) {
    for (GridCell<Character> cell : cells) {
      Character value = cell.value();
      CellPosition pos = cell.pos();
      Rectangle2D r = cellPositionToPixelConverter.getBoundsForCell(pos);
      if (value == 'G') {
        BufferedImage image = this.wallImage;
        double imageX = r.getX() + r.getWidth() / 2;
        double imageY = r.getY() + r.getHeight() / 2;
        Inf101Graphics.drawCenteredImage(g2, image, imageX, imageY, 1.0);
        continue;
      } else if (value == 'X') {
        BufferedImage image = this.brokenWallImage;
        double imageX = r.getX() + r.getWidth() / 2;
        double imageY = r.getY() + r.getHeight() / 2;
        Inf101Graphics.drawCenteredImage(g2, image, imageX, imageY, 1.0);
        continue;
      } else if (value == 'E') {
        BufferedImage image = this.explosionImage;
        double imageX = r.getX() + r.getWidth() / 2;
        double imageY = r.getY() + r.getHeight() / 2;

        Inf101Graphics.drawCenteredImage(g2, image, imageX, imageY, 1.0);
        continue;
      } else {
        BufferedImage image = this.floorImage;

        double imageX = r.getX() + r.getWidth() / 2;
        double imageY = r.getY() + r.getHeight() / 2;

        Inf101Graphics.drawCenteredImage(g2, image, imageX, imageY, 1.0);
      }
    }
  }
}
