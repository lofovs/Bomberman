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

        /** Construct a new BombermanView */
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
                double textWidth = gameWidth / 4 - 2 * INNERMARGIN;
                double textHeight = SCOREBOARDHEIGHT / 2;
                double textX = x + INNERMARGIN;
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
                                        1.5);

                        // draws the player sprites in the lower middle of the screen
                        Inf101Graphics.drawCenteredImage(
                                        g2,
                                        player1FrontImage,
                                        (x + windowWidth / 2) - 150,
                                        (y + windowHeight / 3) + 100,
                                        1.5);
                        Inf101Graphics.drawCenteredImage(
                                        g2,
                                        player2FrontImage,
                                        (x + windowWidth / 2) - 50,
                                        (y + windowHeight / 3) + 100,
                                        1.5);
                        Inf101Graphics.drawCenteredImage(
                                        g2,
                                        player3FrontImage,
                                        (x + windowWidth / 2) + 50,
                                        (y + windowHeight / 3) + 100,
                                        1.5);
                        Inf101Graphics.drawCenteredImage(
                                        g2,
                                        player4FrontImage,
                                        (x + windowWidth / 2) + 150,
                                        (y + windowHeight / 3) + 100,
                                        1.5);

                        // draws the new game text
                        g2.setColor(theme.getNewGameTextColor());
                        g2.setFont(new Font("Monospaced", Font.BOLD, 20));
                        Inf101Graphics.drawCenteredString(
                                        g2,
                                        "PRESS ENTER TO PLAY",
                                        tileRectangle.getX(),
                                        tileRectangle.getY(),
                                        gameWidth,
                                        gameHeight * 1.5);
                }

                if (this.model.getGameState() != GameState.NEW_GAME) {

                        // draws the board
                        g2.setColor(theme.getFrameColor());
                        g2.fill(tileRectangle);

                        // draws the grid
                        drawImageCells(g2, grid, cellPositionToPixelConverter, theme);
                        // drawCells(g2, grid, cellPositionToPixelConverter, theme);

                        // draws the players depending on their direction
                        if (this.model.getPlayer1Sprite() == 0) {

                                drawCells(
                                                g2,
                                                playerModel,
                                                cellPositionToPixelConverter,
                                                player1FrontImage);
                        }
                        if (this.model.getPlayer1Sprite() == 1) {

                                drawCells(
                                                g2,
                                                playerModel,
                                                cellPositionToPixelConverter,
                                                player1BackImage);
                        }
                        if (this.model.getPlayer1Sprite() == 2) {

                                drawCells(
                                                g2,
                                                playerModel,
                                                cellPositionToPixelConverter,
                                                player1LeftImage);
                        }
                        if (this.model.getPlayer1Sprite() == 3) {

                                drawCells(
                                                g2,
                                                playerModel,
                                                cellPositionToPixelConverter,
                                                player1RightImage);
                        }

                        if (this.model.getPlayer2Sprite() == 0) {

                                drawCells(
                                                g2,
                                                player2Model,
                                                cellPositionToPixelConverter,
                                                player2FrontImage);
                        }
                        if (this.model.getPlayer2Sprite() == 1) {

                                drawCells(
                                                g2,
                                                player2Model,
                                                cellPositionToPixelConverter,
                                                player2BackImage);
                        }
                        if (this.model.getPlayer2Sprite() == 2) {

                                drawCells(
                                                g2,
                                                player2Model,
                                                cellPositionToPixelConverter,
                                                player2LeftImage);
                        }

                        if (this.model.getPlayer2Sprite() == 3) {

                                drawCells(
                                                g2,
                                                player2Model,
                                                cellPositionToPixelConverter,
                                                player2RightImage);
                        }

                        if (this.model.getPlayer3Sprite() == 0) {

                                drawCells(
                                                g2,
                                                player3Model,
                                                cellPositionToPixelConverter,
                                                player3FrontImage);
                        }
                        if (this.model.getPlayer3Sprite() == 1) {

                                drawCells(
                                                g2,
                                                player3Model,
                                                cellPositionToPixelConverter,
                                                player3BackImage);
                        }
                        if (this.model.getPlayer3Sprite() == 2) {

                                drawCells(
                                                g2,
                                                player3Model,
                                                cellPositionToPixelConverter,
                                                player3LeftImage);
                        }

                        if (this.model.getPlayer3Sprite() == 3) {

                                drawCells(
                                                g2,
                                                player3Model,
                                                cellPositionToPixelConverter,
                                                player3RightImage);
                        }

                        if (this.model.getPlayer4Sprite() == 0) {

                                drawCells(
                                                g2,
                                                player4Model,
                                                cellPositionToPixelConverter,
                                                player4FrontImage);
                        }
                        if (this.model.getPlayer4Sprite() == 1) {

                                drawCells(
                                                g2,
                                                player4Model,
                                                cellPositionToPixelConverter,
                                                player4BackImage);
                        }
                        if (this.model.getPlayer4Sprite() == 2) {

                                drawCells(
                                                g2,
                                                player4Model,
                                                cellPositionToPixelConverter,
                                                player4LeftImage);
                        }

                        if (this.model.getPlayer4Sprite() == 3) {

                                drawCells(
                                                g2,
                                                player4Model,
                                                cellPositionToPixelConverter,
                                                player4RightImage);
                        }

                        // draws the bombs
                        drawCells(g2, bomb1Model, cellPositionToPixelConverter, bombImage);
                        drawCells(g2, bomb2Model, cellPositionToPixelConverter, bombImage);
                        drawCells(g2, bomb3Model, cellPositionToPixelConverter, bombImage);
                        drawCells(g2, bomb4Model, cellPositionToPixelConverter, bombImage);

                        // draw the scoreboard under the game
                        g2.setColor(theme.getScoreBoardColor());
                        g2.fill(scoreboardRectangle);

                        // draws a black rectangle behind the clock
                        Rectangle2D clockRectangle = new Rectangle2D.Double(
                                        x + gameWidth / 2 - 50,
                                        y + gameHeight + (scoreboardRectangle.getHeight() / 10),
                                        100,
                                        40);
                        g2.setColor(Color.BLACK);
                        g2.fill(clockRectangle);

                        // draw a clock in the middle of the scoreboard
                        g2.setFont(new Font("Monospaced", Font.BOLD, 20));
                        g2.setColor(theme.getClockColor());
                        Inf101Graphics.drawCenteredString(
                                        g2,
                                        "" + model.getTime(),
                                        scoreboardRectangle.getX(),
                                        scoreboardRectangle.getY(),
                                        gameWidth,
                                        SCOREBOARDHEIGHT);

                        g2.setColor(theme.getScoreBoardTextColor());
                        g2.setFont(new Font("Monospaced", Font.BOLD, 20));

                        // draw the player 1 and 2 lives next to each other horizontally on the left
                        // side of the clock

                        Inf101Graphics.drawCenteredString(
                                        g2,
                                        "" + model.getPlayerLives(),
                                        scoreboardRectangle.getX(),
                                        scoreboardRectangle.getY(),
                                        gameWidth / 4,
                                        SCOREBOARDHEIGHT);

                        Inf101Graphics.drawCenteredString(
                                        g2,
                                        "" + model.getPlayer2Lives(),
                                        scoreboardRectangle.getX(),
                                        scoreboardRectangle.getY(),
                                        gameWidth / 2,
                                        SCOREBOARDHEIGHT);

                        // draw the player 3 and 4 lives next to each other horizontally on the right
                        // side of the clock

                        Inf101Graphics.drawCenteredString(
                                        g2,
                                        "" + model.getPlayer3Lives(),
                                        scoreboardRectangle.getX() + gameWidth / 2,
                                        scoreboardRectangle.getY(),
                                        gameWidth / 2,
                                        SCOREBOARDHEIGHT);

                        Inf101Graphics.drawCenteredString(
                                        g2,
                                        "" + model.getPlayer4Lives(),
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

                        if (this.model.getGameState() == GameState.PAUSED_GAME
                                        || this.model.getGameState() == GameState.PLAYER1_WON
                                        || this.model.getGameState() == GameState.PLAYER2_WON
                                        || this.model.getGameState() == GameState.PLAYER3_WON
                                        || this.model.getGameState() == GameState.PLAYER4_WON
                                        || this.model.getGameState() == GameState.DRAW) {

                                // draw a transparent layer over the game
                                g2.setColor(theme.getTransparentScreenColor());
                                g2.fill(windowRectangle);

                                if (this.model.getGameState() == GameState.DRAW) {
                                        // draw the draw text
                                        g2.setColor(theme.getDrawTextColor());
                                        g2.setFont(new Font("Monospaced", Font.BOLD, 40));
                                        Inf101Graphics.drawCenteredString(
                                                        g2,
                                                        "DRAW",
                                                        tileRectangle.getX(),
                                                        tileRectangle.getY(),
                                                        gameWidth,
                                                        gameHeight);
                                }
                                if (this.model.getGameState() == GameState.PAUSED_GAME) {
                                        // draw the paused text
                                        g2.setColor(theme.getPausedTextColor());
                                        g2.setFont(new Font("Monospaced", Font.BOLD, 40));
                                        Inf101Graphics.drawCenteredString(
                                                        g2,
                                                        "PAUSED",
                                                        tileRectangle.getX(),
                                                        tileRectangle.getY(),
                                                        gameWidth,
                                                        gameHeight);

                                }
                                if (this.model.getGameState() == GameState.PLAYER1_WON) {
                                        // draw the player 1 won text
                                        g2.setColor(theme.getGameWonTextColor());
                                        g2.setFont(new Font("Monospaced", Font.BOLD, 40));
                                        Inf101Graphics.drawCenteredString(
                                                        g2,
                                                        "PLAYER 1 WON",
                                                        tileRectangle.getX(),
                                                        tileRectangle.getY(),
                                                        gameWidth,
                                                        gameHeight);
                                }
                                if (this.model.getGameState() == GameState.PLAYER2_WON) {
                                        // draw the player 2 won text
                                        g2.setColor(theme.getGameWonTextColor());
                                        g2.setFont(new Font("Monospaced", Font.BOLD, 40));
                                        Inf101Graphics.drawCenteredString(
                                                        g2,
                                                        "PLAYER 2 WON",
                                                        tileRectangle.getX(),
                                                        tileRectangle.getY(),
                                                        gameWidth,
                                                        gameHeight);
                                }
                                if (this.model.getGameState() == GameState.PLAYER3_WON) {
                                        // draw the player 3 won text
                                        g2.setColor(theme.getGameWonTextColor());
                                        g2.setFont(new Font("Monospaced", Font.BOLD, 40));
                                        Inf101Graphics.drawCenteredString(
                                                        g2,
                                                        "PLAYER 3 WON",
                                                        tileRectangle.getX(),
                                                        tileRectangle.getY(),
                                                        gameWidth,
                                                        gameHeight);
                                }
                                if (this.model.getGameState() == GameState.PLAYER4_WON) {
                                        // draw the player 4 won text
                                        g2.setColor(theme.getGameWonTextColor());
                                        g2.setFont(new Font("Monospaced", Font.BOLD, 40));
                                        Inf101Graphics.drawCenteredString(
                                                        g2,
                                                        "PLAYER 4 WON",
                                                        tileRectangle.getX(),
                                                        tileRectangle.getY(),
                                                        gameWidth,
                                                        gameHeight);
                                }
                                if (this.model.getGameState() == GameState.DRAW
                                                || this.model.getGameState() == GameState.PLAYER1_WON
                                                || this.model.getGameState() == GameState.PLAYER2_WON
                                                || this.model.getGameState() == GameState.PLAYER3_WON
                                                || this.model.getGameState() == GameState.PLAYER4_WON) {
                                        // draw the play again text
                                        g2.setColor(theme.getPlayAgainTextColor());
                                        g2.setFont(new Font("Monospaced", Font.BOLD, 20));
                                        Inf101Graphics.drawCenteredString(
                                                        g2,
                                                        "Press ENTER to play again",
                                                        tileRectangle.getX(),
                                                        windowRectangle.getY() + windowRectangle.getHeight() / 4,
                                                        gameWidth,
                                                        gameHeight);
                                }
                        }

                }
        }

        private void drawCells(
                        Graphics2D g2,
                        Iterable<GridCell<Character>> cells,
                        CellPositionToPixelConverter cellPositionToPixelConverter,
                        ColorTheme theme) {
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
