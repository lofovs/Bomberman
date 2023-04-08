package no.uib.inf101.sem2.bomberman.view;

import java.awt.geom.Rectangle2D;
import no.uib.inf101.sem2.grid.CellPosition;
import no.uib.inf101.sem2.grid.GridDimension;

/**
 * Converts a cell position to a pixel position
 *
 */
public class CellPositionToPixelConverter {

  private Rectangle2D box;
  private GridDimension gd;
  private double margin;

  /**
   * Create a new converter
   *
   * @param box    The box to draw in
   * @param gd     The grid dimension
   * @param margin The margin between cells
   */
  public CellPositionToPixelConverter(
    Rectangle2D box,
    GridDimension gd,
    double margin
  ) {
    this.box = box;
    this.gd = gd;
    this.margin = margin;
  }

  /**
   * Get the bounds of a cell
   *
   * @param cp The cell position
   * @return The bounds of the cell
   */
  public Rectangle2D getBoundsForCell(CellPosition cp) {
    double cellWidth = (box.getWidth() - (gd.cols() + 1) * margin) / gd.cols();
    double cellHeight =
      (box.getHeight() - (gd.rows() + 1) * margin) / gd.rows();
    double cellX =
      box.getX() + (cp.col() * cellWidth) + ((cp.col() + 1) * margin);
    double cellY =
      box.getY() + (cp.row() * cellHeight) + ((cp.row() + 1) * margin);

    return new Rectangle2D.Double(cellX, cellY, cellWidth, cellHeight);
  }
}
