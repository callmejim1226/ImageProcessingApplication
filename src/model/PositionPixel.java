package model;

import java.util.Objects;

/**
 * The {@code PositionPixel} represents the position of the pixel in the image. The position is
 * represented as a coordinate (row number, column number).
 */
public final class PositionPixel {
  private final int row;
  private final int column;

  /**
   * Constructs a position of the pixel in the image.
   *
   * @param row    the row number of the pixel in the image.
   * @param column the column number of the pixel in the image.
   * @throws IllegalArgumentException if any of position's parameters is negative
   */
  public PositionPixel(int row, int column) throws IllegalArgumentException {
    if (row < 0 || column < 0) {
      throw new IllegalArgumentException("Position cannot be negative");
    }
    this.row = row;
    this.column = column;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof PositionPixel)) {
      return false;
    }
    PositionPixel p = (PositionPixel) o;
    return this.row == p.row && this.column == p.column;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.row, this.column);
  }
}
