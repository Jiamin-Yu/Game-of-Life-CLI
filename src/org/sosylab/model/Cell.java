package org.sosylab.model;

import java.util.Objects;

/**
 * A two dimensional coordinate within a cell grid.
 */
public class Cell {

  /**
   * The x-coordinate of the cel.
   */
  private final int column;

  /**
   * The y-coordinate of the cell.
   */
  private final int row;

  /**
   * The status of the cell. If the cell is alive, the status is true;
   * if the cell is dead, the status is false.
   */
  private boolean status;

  /**
   * Constructs a new cell.
   *
   * @param column The x-coordinate (column of cell).
   * @param row    The y-coordinate (row of cell).
   */
  public Cell(int column, int row) {
    if (column < 0 || row < 0) {
      throw new IllegalArgumentException("Cell must not have negative coordinates");
    }

    this.column = column;
    this.row = row;
    status = false;
  }

  /**
   * Get the status of a cell.
   *
   * @return the status of a cell.
   */

  public boolean getStatus() {
    return status;
  }

  /**
   * Set the status of a cell to alive.
   */
  public void setStatusAlive() {
    status = true;
  }

  /**
   * Set the status of a cell to dead.
   */
  public void setStatusDead() {
    status = false;
  }


  /**
   * Get the x-coordinate of a cell.
   *
   * @return The x-coordinate.
   */
  public int getColumn() {
    return column;
  }

  /**
   * Get the y-coordinate of a cell.
   *
   * @return The y-coordinate.
   */
  public int getRow() {
    return row;
  }

  @Override
  public int hashCode() {
    return Objects.hash(column, row);
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (other == null || getClass() != other.getClass()) {
      return false;
    }
    Cell cell = (Cell) other;
    return column == cell.column && row == cell.row;
  }

  @Override
  public String toString() {
    return String.format("<%s, %s>", column, row);
  }

}

