package model;

import java.util.List;
import java.util.Objects;
import java.util.ArrayList;

/**
 * The {@code model.Color} represents a color of the pixel. The color is represented in RGB value,
 * where each red, green, and blue are represented as integers from 0 to 255.
 */
public final class ColorPixel {
  private final int red;
  private final int green;
  private final int blue;

  /**
   * Constructs a color of the pixel considering the clamping.
   *
   * @param red   the integer value of the red color channel in a range 0 to 255.
   * @param green the integer value of the green color channel in a range 0 to 255.
   * @param blue  the integer value of the blue color channel in a range 0 to 255.
   */
  public ColorPixel(int red, int green, int blue) {
    this.red = setValue(red);
    this.green = setValue(green);
    this.blue = setValue(blue);
  }

  /**
   * Returns the value for the color channel considering the clamping. If the given color value is
   * less than 0, then the color is set to be 0. if it is greater than 255,
   * then it is set to be 255. Otherwise, the value is set to be the given color value.
   *
   * @param colorValue the given color value.
   * @return the clamped color value.
   */
  private static int setValue(int colorValue) {
    if (colorValue < 0) {
      return 0;
    } else if (colorValue > 255) {
      return 255;
    } else {
      return colorValue;
    }
  }

  /**
   * Constructs a new color with updated RGB values.
   *
   * @param matrix the color transformation in matrix format.
   * @return a new color with updated RGB values.
   */
  protected ColorPixel updateRGB(List<List<Double>> matrix) {
    return new ColorPixel(this.updateColorValue(matrix.get(0)),
            this.updateColorValue(matrix.get(1)), this.updateColorValue(matrix.get(2)));
  }

  /**
   * Constructs a new color value with given color transformation in row format.
   *
   * @param row a list of doubles that is picked from color transformation in matrix format.
   * @return a new color value after transforming the color.
   */
  private int updateColorValue(List<Double> row) {
    return (int) Math.round((this.red * row.get(0) + this.green * row.get(1)
            + this.blue * row.get(2)));
  }

  /**
   * Gets the red channel/value of this color.
   *
   * @return the red color value of this color.
   */
  public int getRedChannel() {
    return this.red;
  }

  /**
   * Gets the green channel/value of this color.
   *
   * @return the green color value of this color.
   */
  public int getGreenChannel() {
    return this.green;
  }

  /**
   * Gets the blue channel/value of this color.
   *
   * @return the blue color value of this color.
   */
  public int getBlueChannel() {
    return this.blue;
  }

  /**
   * Computes new RGB value by multiplying each RGB with given value.
   *
   * @param value the given value used to compute new RGB values.
   * @return a sequence of updated RGB values.
   */
  protected List<Double> getNewColor(double value) {
    List<Double> newRGB = new ArrayList<Double>();
    newRGB.add(this.red * value);
    newRGB.add(this.green * value);
    newRGB.add(this.blue * value);
    return newRGB;
  }

  /**
   * Sums each RGB values individually in given list of RGB values.
   *
   * @param colors the sequence of RGB values.
   * @return the sum of RGB values.
   */
  protected static ColorPixel sumChannels(List<List<Double>> colors) {
    double sumRedValue = 0.0;
    double sumGreenValue = 0.0;
    double sumBlueValue = 0.0;
    for (List<Double> rgb : colors) {
      sumRedValue = sumRedValue + rgb.get(0);
      sumGreenValue = sumGreenValue + rgb.get(1);
      sumBlueValue = sumBlueValue + rgb.get(2);
    }
    return new ColorPixel((int) Math.round(sumRedValue),
            (int) Math.round(sumGreenValue), (int) Math.round(sumBlueValue));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ColorPixel)) {
      return false;
    }
    ColorPixel col = (ColorPixel) o;
    return this.red == col.red && this.green == col.green && this.blue == col.blue;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.red, this.green, this.blue);
  }
}
