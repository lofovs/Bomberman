package no.uib.inf101.sem2.bomberman.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import no.uib.inf101.sem2.bomberman.model.Direction;
import no.uib.inf101.sem2.bomberman.model.GameState;
import no.uib.inf101.sem2.grid.CellPosition;
import no.uib.inf101.sem2.grid.GridCell;
import no.uib.inf101.sem2.grid.GridDimension;

public class BombermanView extends JPanel {

        private static final double OUTERMARGIN = 0;
        private static final double INNERMARGIN = 2;
        private static final double SQUARESIZE = 30;
        private static final double SCOREBOARDHEIGHT = 50;
        private static final double LOGO_SCALE = 1.5;
        private static final double UNIT_SCALE = 1.5;
        private static final Font MAIN_TEXT_FONT = new Font("Monospaced", Font.BOLD, 40);
        private static final Font UNDER_TEXT_FONT = new Font("Monospaced", Font.BOLD, 20);

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

        private BufferedImage player1IconImage;
        private BufferedImage player1FrontImage;
        private BufferedImage player1BackImage;
        private BufferedImage player1LeftImage;
        private BufferedImage player1RightImage;

        private BufferedImage player2IconImage;
        private BufferedImage player2FrontImage;
        private BufferedImage player2BackImage;
        private BufferedImage player2LeftImage;
        private BufferedImage player2RightImage;

        private BufferedImage player3IconImage;
        private BufferedImage player3FrontImage;
        private BufferedImage player3BackImage;
        private BufferedImage player3LeftImage;
        private BufferedImage player3RightImage;

        private BufferedImage player4IconImage;
        private BufferedImage player4FrontImage;
        private BufferedImage player4BackImage;
        private BufferedImage player4LeftImage;
        private BufferedImage player4RightImage;

        @SuppressWarnings("unchecked")
        private final Iterable<GridCell<Character>>[] playerTiles = new Iterable[4];
        @SuppressWarnings("unchecked")
        private final Iterable<GridCell<Character>>[] bombTiles = new Iterable[4];

        /**
         * Creates a new view for the given model
         *
         * @param model the model to view
         */
        public BombermanView(ViewableBombermanModel model) {
                this.model = model;
                this.setFocusable(true);
                this.width = (SQUARESIZE + INNERMARGIN) *
                                model.getDimension().cols() +
                                INNERMARGIN +
                                OUTERMARGIN;
                this.height = (SQUARESIZE + INNERMARGIN) *
                                model.getDimension().rows() +
                                INNERMARGIN +
                                OUTERMARGIN +
                                SCOREBOARDHEIGHT;
                this.setPreferredSize(new Dimension((int) width, (int) height));
                this.theme = new DefaultColorTheme();
                this.setBackground(getBackground());

                this.bombermanLogo = Inf101Graphics.loadImageFromResources("bombermanLogo.png");
                this.bombImage = Inf101Graphics.loadImageFromResources("bomb.png");

                this.wallImage = Inf101Graphics.loadImageFromResources("wall.png");
                this.brokenWallImage = Inf101Graphics.loadImageFromResources("wallBroken.png");
                this.floorImage = Inf101Graphics.loadImageFromResources("floor.png");
                this.explosionImage = Inf101Graphics.loadImageFromResources("explosion.png");

                this.player1IconImage = Inf101Graphics.loadImageFromResources("player1Icon.png");
                this.player1FrontImage = Inf101Graphics.loadImageFromResources("player1Front.png");
                this.player1BackImage = Inf101Graphics.loadImageFromResources("player1Back.png");
                this.player1LeftImage = Inf101Graphics.loadImageFromResources("player1Left.png");
                this.player1RightImage = Inf101Graphics.loadImageFromResources("player1Right.png");

                this.player2IconImage = Inf101Graphics.loadImageFromResources("player2Icon.png");
                this.player2FrontImage = Inf101Graphics.loadImageFromResources("player2Front.png");
                this.player2BackImage = Inf101Graphics.loadImageFromResources("player2Back.png");
                this.player2LeftImage = Inf101Graphics.loadImageFromResources("player2Left.png");
                this.player2RightImage = Inf101Graphics.loadImageFromResources("player2Right.png");

                this.player3IconImage = Inf101Graphics.loadImageFromResources("player3Icon.png");
                this.player3FrontImage = Inf101Graphics.loadImageFromResources("player3Front.png");
                this.player3BackImage = Inf101Graphics.loadImageFromResources("player3Back.png");
                this.player3LeftImage = Inf101Graphics.loadImageFromResources("player3Left.png");
                this.player3RightImage = Inf101Graphics.loadImageFromResources("player3Right.png");

                this.player4IconImage = Inf101Graphics.loadImageFromResources("player4Icon.png");
                this.player4FrontImage = Inf101Graphics.loadImageFromResources("player4Front.png");
                this.player4BackImage = Inf101Graphics.loadImageFromResources("player4Back.png");
                this.player4LeftImage = Inf101Graphics.loadImageFromResources("player4Left.png");
                this.player4RightImage = Inf101Graphics.loadImageFromResources("player4Right.png");
        }

        @Override
        protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                drawGame(g2);
        }

        private void drawGame(Graphics2D g2) {

                // variables for the board
                double x = OUTERMARGIN;
                double y = OUTERMARGIN;
                double gameWidth = this.getWidth() - (2 * OUTERMARGIN);
                double gameHeight = this.getHeight() - (2 * OUTERMARGIN) - SCOREBOARDHEIGHT;
                double windowWidth = this.getWidth();
                double windowHeight = this.getHeight();
                GridDimension gd = model.getDimension();
                Iterable<GridCell<Character>> grid = model.getTilesOnBoard();

                // player tiles
                for (int i = 0; i < 4; i++) {
                        playerTiles[i] = model.getPlayerTile(i + 1);
                }

                // bomb tiles
                for (int i = 0; i < 4; i++) {
                        bombTiles[i] = model.getBombTile(i + 1);
                }

                // variables for the scoreboard
                double textHeight = SCOREBOARDHEIGHT / 2;

                // shapes
                Rectangle2D windowRectangle = new Rectangle2D.Double(
                                0,
                                0,
                                windowWidth,
                                windowHeight);
                Rectangle2D tileRectangle = new Rectangle2D.Double(
                                x,
                                y,
                                gameWidth,
                                gameHeight);

                Rectangle2D scoreboardRectangle = new Rectangle2D.Double(
                                x,
                                y + gameHeight,
                                gameWidth,
                                SCOREBOARDHEIGHT);

                CellPositionToPixelConverter cellPositionToPixelConverter = new CellPositionToPixelConverter(
                                tileRectangle,
                                gd,
                                INNERMARGIN);

                GameState gameState = this.model.getGameState();
                switch (gameState) {
                        case NEW_GAME:
                                drawNewGame(g2, windowRectangle, tileRectangle, x, y, windowWidth, windowHeight,
                                                gameWidth, gameHeight);
                                break;
                        case PAUSED_GAME:
                                drawPausedGame(g2, windowRectangle, tileRectangle, x, y, gameWidth, gameHeight,
                                                scoreboardRectangle, textHeight, cellPositionToPixelConverter, grid);
                                break;
                        case ACTIVE_GAME:
                                drawActiveGame(g2, windowRectangle, tileRectangle, x, y, windowWidth, windowHeight,
                                                scoreboardRectangle, textHeight, cellPositionToPixelConverter, grid);
                                break;
                        case DRAW:
                                drawDraw(g2, windowRectangle, tileRectangle, x, y, gameWidth, gameHeight, grid,
                                                scoreboardRectangle, cellPositionToPixelConverter,
                                                textHeight);
                                break;
                        case PLAYER1_WON:
                                drawWonGame(g2, windowRectangle, tileRectangle, x, y, windowWidth, windowHeight,
                                                gameWidth, gameHeight, grid, scoreboardRectangle,
                                                cellPositionToPixelConverter, textHeight, gameState);
                                break;
                        case PLAYER2_WON:
                                drawWonGame(g2, windowRectangle, tileRectangle, x, y, windowWidth, windowHeight,
                                                gameWidth, gameHeight, grid, scoreboardRectangle,
                                                cellPositionToPixelConverter, textHeight, gameState);
                                break;
                        case PLAYER3_WON:
                                drawWonGame(g2, windowRectangle, tileRectangle, x, y, windowWidth, windowHeight,
                                                gameWidth, gameHeight, grid, scoreboardRectangle,
                                                cellPositionToPixelConverter, textHeight, gameState);
                                break;
                        case PLAYER4_WON:
                                drawWonGame(g2, windowRectangle, tileRectangle, x, y, windowWidth, windowHeight,
                                                gameWidth, gameHeight, grid, scoreboardRectangle,
                                                cellPositionToPixelConverter, textHeight, gameState);
                                break;
                        default:
                                break;
                }

        }

        private void drawTextInCenter(Graphics2D g2, String text, Rectangle2D tileRectangle, double gameWidth,
                        double gameHeight) {
                g2.setFont(MAIN_TEXT_FONT);
                Inf101Graphics.drawCenteredString(
                                g2,
                                text,
                                tileRectangle.getX(),
                                tileRectangle.getY(),
                                gameWidth,
                                gameHeight);

        }

        private void drawTransparentScreen(Graphics2D g2, Rectangle2D windowRectangle, ColorTheme theme) {
                g2.setColor(theme.getTransparentScreenColor());
                g2.fill(windowRectangle);
        }

        private void drawScoreboard(Graphics2D g2, Rectangle2D scoreboardRectangle, double textHeight, double gameWidth,
                        double gameHeight) {
                // Draw the scoreboard area
                g2.setColor(theme.getScoreBoardColor());
                g2.fill(scoreboardRectangle);

                // Draw the clock background
                Rectangle2D clockRect = new Rectangle2D.Double(
                                scoreboardRectangle.getX() + scoreboardRectangle.getWidth() / 2 - 50,
                                scoreboardRectangle.getY() + scoreboardRectangle.getHeight() / 10,
                                100,
                                40);
                g2.setColor(Color.BLACK);
                g2.fill(clockRect);

                // Draw the clock text
                g2.setFont(UNDER_TEXT_FONT);
                g2.setColor(theme.getClockColor());
                Inf101Graphics.drawCenteredString(
                                g2,
                                "" + model.getTime(),
                                scoreboardRectangle.getX(),
                                scoreboardRectangle.getY(),
                                scoreboardRectangle.getWidth(),
                                SCOREBOARDHEIGHT);

                g2.setColor(theme.getScoreBoardTextColor());
                g2.setFont(UNDER_TEXT_FONT);

                Inf101Graphics.drawCenteredString(
                                g2,
                                "" + model.getPlayer(1).getLives(),
                                scoreboardRectangle.getX(),
                                scoreboardRectangle.getY(),
                                gameWidth / 4,
                                SCOREBOARDHEIGHT);

                Inf101Graphics.drawCenteredString(
                                g2,
                                "" + model.getPlayer(2).getLives(),
                                scoreboardRectangle.getX(),
                                scoreboardRectangle.getY(),
                                gameWidth / 2,
                                SCOREBOARDHEIGHT);

                // draw the player 3 and 4 lives next to each other horizontally on the right
                // side of the clock

                Inf101Graphics.drawCenteredString(
                                g2,
                                "" + model.getPlayer(3).getLives(),
                                scoreboardRectangle.getX() + gameWidth / 2,
                                scoreboardRectangle.getY(),
                                gameWidth / 2,
                                SCOREBOARDHEIGHT);

                Inf101Graphics.drawCenteredString(
                                g2,
                                "" + model.getPlayer(4).getLives(),
                                scoreboardRectangle.getX() + gameWidth / 2,
                                scoreboardRectangle.getY(),
                                gameWidth / 4 * 3,
                                SCOREBOARDHEIGHT);

                // draw the player 1 and player 2 icons to the left of their lives horizontally
                Inf101Graphics.drawCenteredImage(
                                g2,
                                player1IconImage,
                                scoreboardRectangle.getX() + gameWidth / 8 - gameWidth / 16,
                                scoreboardRectangle.getY() + SCOREBOARDHEIGHT / 2,
                                0.75);

                Inf101Graphics.drawCenteredImage(
                                g2,
                                player2IconImage,
                                scoreboardRectangle.getX() + gameWidth / 4 + gameWidth / 16,
                                scoreboardRectangle.getY() + SCOREBOARDHEIGHT / 2,
                                0.75);

                // draw the player 3 and player 4 icons to the right of their lives horizontally
                Inf101Graphics.drawCenteredImage(
                                g2,
                                player3IconImage,
                                scoreboardRectangle.getX() + gameWidth / 4 * 3 - gameWidth / 16,
                                scoreboardRectangle.getY() + SCOREBOARDHEIGHT / 2,
                                0.75);

                Inf101Graphics.drawCenteredImage(
                                g2,
                                player4IconImage,
                                scoreboardRectangle.getX() + gameWidth - gameWidth / 16,
                                scoreboardRectangle.getY() + SCOREBOARDHEIGHT / 2,
                                0.75);

        }

        private void drawBombs(Graphics2D g2, CellPositionToPixelConverter cellPositionToPixelConverter) {
                for (Iterable<GridCell<Character>> bombTile : bombTiles) {
                        drawCells(g2, bombTile, cellPositionToPixelConverter, bombImage);
                }
        }

        private void drawPlayers(Graphics2D g2, CellPositionToPixelConverter cellPositionToPixelConverter) {
                BufferedImage[][] playerImages = new BufferedImage[][] {
                                { player1FrontImage, player1BackImage, player1LeftImage, player1RightImage },
                                { player2FrontImage, player2BackImage, player2LeftImage, player2RightImage },
                                { player3FrontImage, player3BackImage, player3LeftImage, player3RightImage },
                                { player4FrontImage, player4BackImage, player4LeftImage, player4RightImage }
                };

                // loop through each player tile and draw their corresponding image based on
                // their direction
                for (int i = 0; i < playerTiles.length; i++) {
                        Iterable<GridCell<Character>> playerTile = playerTiles[i];
                        if (playerTile != null) {
                                BufferedImage[] images = playerImages[i];
                                Direction direction = model.getPlayer(i + 1).getSprite();
                                int imageIndex = 0;
                                if (direction == Direction.UP) {
                                        imageIndex = 1;
                                } else if (direction == Direction.LEFT) {
                                        imageIndex = 2;
                                } else if (direction == Direction.RIGHT) {
                                        imageIndex = 3;
                                }
                                drawCells(g2, playerTile, cellPositionToPixelConverter, images[imageIndex]);
                        }
                }
        }

        private void drawWonGame(Graphics2D g2, Rectangle2D windowRectangle, Rectangle2D tileRectangle, double x,
                        double y, double windowWidth, double windowHeight, double gameWidth, double gameHeight,
                        Iterable<GridCell<Character>> grid, Rectangle2D scoreboardRectangle,
                        CellPositionToPixelConverter cellPositionToPixelConverter, double textHeight,
                        GameState gameState) {

                drawDarkenedBackground(g2, windowRectangle, tileRectangle, x, y, gameWidth,
                                gameHeight,
                                grid, scoreboardRectangle, cellPositionToPixelConverter, textHeight);

                String string = "";
                switch (gameState) {
                        case PLAYER1_WON:
                                string = "PLAYER 1 WON!";
                                break;
                        case PLAYER2_WON:
                                string = "PLAYER 2 WON!";
                                break;
                        case PLAYER3_WON:
                                string = "PLAYER 3 WON!";
                                break;
                        case PLAYER4_WON:
                                string = "PLAYER 4 WON!";
                                break;
                        default:
                                break;
                }

                // draws the player won text
                g2.setColor(theme.getGameWonTextColor());
                drawTextInCenter(g2, string, tileRectangle, gameWidth, gameHeight);

                // draws the press enter to play again text
                g2.setColor(theme.getPlayAgainTextColor());
                drawTextUnderCenter(g2, "Press ENTER to play again", gameWidth, gameHeight, windowRectangle);

        }

        private void drawDarkenedBackground(Graphics2D g2, Rectangle2D windowRectangle, Rectangle2D tileRectangle,
                        double x, double y, double gameWidth,
                        double gameHeight, Iterable<GridCell<Character>> grid, Rectangle2D scoreboardRectangle,
                        CellPositionToPixelConverter cellPositionToPixelConverter, double textHeight) {

                drawBaseGame(g2, windowRectangle, tileRectangle, x, y, gameWidth, gameHeight, grid, scoreboardRectangle,
                                cellPositionToPixelConverter, textHeight);

                drawTransparentScreen(g2, windowRectangle, theme);

        }

        private void drawDraw(Graphics2D g2, Rectangle2D windowRectangle, Rectangle2D tileRectangle, double x,
                        double y, double gameWidth, double gameHeight, Iterable<GridCell<Character>> grid,
                        Rectangle2D scoreboardRectangle, CellPositionToPixelConverter cellPositionToPixelConverter,
                        double textHeight) {

                drawDarkenedBackground(g2, windowRectangle, tileRectangle, x, y, gameWidth, gameHeight,
                                grid, scoreboardRectangle, cellPositionToPixelConverter, textHeight);

                // draws the paused text
                g2.setColor(theme.getPausedTextColor());
                drawTextInCenter(g2, "DRAW", tileRectangle, gameWidth, gameHeight);

                // draws the press enter to play again text
                g2.setColor(theme.getPlayAgainTextColor());
                drawTextUnderCenter(g2, "Press ENTER to play again", gameWidth, gameHeight, windowRectangle);
        }

        private void drawTextUnderCenter(Graphics2D g2, String string, double gameWidth,
                        double gameHeight, RectangularShape windowRectangle) {
                g2.setFont(UNDER_TEXT_FONT);
                Inf101Graphics.drawCenteredString(
                                g2,
                                string,
                                windowRectangle.getX(),
                                windowRectangle.getY() + windowRectangle.getHeight() / 4,
                                gameWidth,
                                gameHeight);

        }

        private void drawActiveGame(Graphics2D g2, Rectangle2D windowRectangle, Rectangle2D tileRectangle, double x,
                        double y, double windowWidth, double windowHeight, Rectangle2D scoreboardRectangle,
                        double textHeight, CellPositionToPixelConverter cellPositionToPixelConverter,
                        Iterable<GridCell<Character>> grid) {
                drawBaseGame(g2, windowRectangle, tileRectangle, x, y, windowWidth, windowHeight, grid,
                                scoreboardRectangle, cellPositionToPixelConverter, textHeight);
        }

        private void drawBoard(Graphics2D g2, Rectangle2D tileRectangle) {
                g2.setColor(theme.getFrameColor());
                g2.fill(tileRectangle);
        }

        private void drawPausedGame(Graphics2D g2, Rectangle2D windowRectangle, Rectangle2D tileRectangle, double x,
                        double y, double gameWidth, double gameHeight, Rectangle2D scoreboardRectangle,
                        double textHeight, CellPositionToPixelConverter cellPositionToPixelConverter,
                        Iterable<GridCell<Character>> grid) {

                drawDarkenedBackground(g2, windowRectangle, tileRectangle, x, y, gameWidth,
                                gameHeight, grid, scoreboardRectangle,
                                cellPositionToPixelConverter, textHeight);

                // draws the paused text
                g2.setColor(theme.getPausedTextColor());
                g2.setFont(MAIN_TEXT_FONT);
                drawTextInCenter(g2, "PAUSED", tileRectangle, gameWidth, gameHeight);
        }

        private void drawBaseGame(Graphics2D g2, Rectangle2D windowRectangle, Rectangle2D tileRectangle, double x,
                        double y, double gameWidth, double gameHeight, Iterable<GridCell<Character>> grid,
                        Rectangle2D scoreboardRectangle, CellPositionToPixelConverter cellPositionToPixelConverter,
                        double textHeight) {
                // draws the board
                drawBoard(g2, tileRectangle);

                // draws the grid
                drawImageCells(g2, grid, cellPositionToPixelConverter, theme);
                drawPlayers(g2, cellPositionToPixelConverter);
                drawBombs(g2, cellPositionToPixelConverter);
                drawScoreboard(g2, scoreboardRectangle, textHeight, gameWidth, gameHeight);
        }

        private void drawNewGame(Graphics2D g2, Rectangle2D windowRectangle, Rectangle2D tileRectangle, double x,
                        double y, double windowWidth, double windowHeight, double gameWidth, double gameHeight) {
                double halfWindowWidth = windowWidth / 2;
                double thirdWindowHeight = windowHeight / 3;
                int playerOffset = 100;

                // draws a black background
                g2.setColor(Color.BLACK);
                g2.fill(windowRectangle);

                // draws the bomberman logo in the upper middle of the screen
                Inf101Graphics.drawCenteredImage(
                                g2,
                                bombermanLogo,
                                (x + halfWindowWidth),
                                (y + thirdWindowHeight),
                                LOGO_SCALE);

                // Draw player sprites in a loop
                BufferedImage[] playerImages = { player1FrontImage, player2FrontImage, player3FrontImage,
                                player4FrontImage };
                int[] playerPositions = { -150, -50, 50, 150 };
                for (int i = 0; i < playerImages.length; i++) {
                        Inf101Graphics.drawCenteredImage(
                                        g2,
                                        playerImages[i],
                                        (x + halfWindowWidth) + playerPositions[i],
                                        (y + thirdWindowHeight) + playerOffset,
                                        UNIT_SCALE);
                }

                // draws the new game text
                g2.setColor(theme.getNewGameTextColor());
                g2.setFont(UNDER_TEXT_FONT);
                drawTextUnderCenter(g2, "Press ENTER to start", gameWidth, gameHeight,
                                windowRectangle);
        }

        private void drawCells(
                        Graphics2D g2,
                        Iterable<GridCell<Character>> cells,
                        CellPositionToPixelConverter cellPositionToPixelConverter,
                        BufferedImage image) {
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
                        ColorTheme theme) {
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
