package model;

import java.util.List;
import java.util.Objects;

/**
 * This class represents a pixel of the image.
 * Each pixel has a position in the image (row, column) and a color.
 */
public class Pixel {
  private final PositionPixel pos;
  private final ColorPixel colorPixel;

  /**
   * Constructs an pixel for the image.
   *
   * @param pos   the position of the pixel in the image.
   * @param colorPixel the color (RGB value) of the pixel.
   * @throws IllegalArgumentException if any of the parameters is null.
   */
  public Pixel(PositionPixel pos, ColorPixel colorPixel) throws IllegalArgumentException {
    if (pos == null || colorPixel == null) {
      throw new IllegalArgumentException("Parameters cannot be null");
    }
    this.pos = pos;
    this.colorPixel = colorPixel;
  }

  /**
   * Constructs a new pixel with updated channel.
   *
   * @param matrix the color transformation in matrix format.
   * @return a new pixel with updated channel.
   */
  protected Pixel transformColor(List<List<Double>> matrix) {
    return new Pixel(this.pos, this.colorPixel.updateRGB(matrix));
  }

  /**
   * Computes a new RGB value by multipying each RGB value to given value.
   *
   * @param value the given value to compute new RGB values.
   * @return a sequence of RGB value.
   */
  protected List<Double> calculateNewColor(Double value) {
    return this.colorPixel.getNewColor(value);
  }

  /**
   * Returns a color of the pixel.
   *
   * @return the color of the pixel
   */
  public ColorPixel getColor() {
    return this.colorPixel;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Pixel)) {
      return false;
    }
    Pixel pix = (Pixel) o;
    return this.pos.equals(pix.pos) && this.colorPixel.equals(pix.colorPixel);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.pos, this.colorPixel);
  }
}
