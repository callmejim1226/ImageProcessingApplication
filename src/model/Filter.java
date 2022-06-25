package model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents one of the operations with images. Filter works with
 * every channel of every pixel in the image given the kernel.
 */
public abstract class Filter {
  private final List<List<Pixel>> image;

  /**
   * Constructs an image operation filter.
   *
   * @param image the given image to filter.
   * @throws IllegalArgumentException if any of the parameters is null or contains null.
   */
  public Filter(List<List<Pixel>> image) throws IllegalArgumentException {
    ColorTransformation.validate(image);
    this.image = image;
  }

  /**
   * Constructs the current image state in a sequence of pixel form.
   *
   * @return the current image state in sequence of
   */
  public List<List<Pixel>> imageObserver() {
    return this.image;
  }

  /**
   * Filters the image based on the kernel given.
   *
   * @param kernel the kernel needed for filter computation.
   * @return the filtered image.
   */
  protected List<List<Pixel>> filter(List<List<Double>> kernel) {
    ColorPixel pixelColorPixel;
    List<List<Pixel>> filteredImage = new ArrayList<List<Pixel>>();
    for (int i = 0; i < image.size(); i++) {
      List<Pixel> filteredRow = new ArrayList<Pixel>();
      for (int j = 0; j < image.get(i).size(); j++) {
        pixelColorPixel = constructNewColor(createImageMatrix(kernel, i, j), kernel);
        filteredRow.add(new Pixel(new PositionPixel(i, j), pixelColorPixel));
      }
      filteredImage.add(filteredRow);
    }
    return filteredImage;
  }

  /**
   * Constructs a sequence of pixel in a matrix form that has same size with given kernel.
   * This matrix is used to construct new RGB values after computing them with values from kernel.
   *
   * @param kernel the sequence of values in matrix form for the math operations.
   * @param rows   the row number of the pixel in the image.
   * @param column the column number of the pixel in the image.
   * @return the sequence of pixel in matrix form for computing new list of RGB values.
   */
  private List<List<Pixel>> createImageMatrix(List<List<Double>> kernel, int rows, int column) {
    int startingRow = rows - (int) (kernel.size() / 2.0);
    int startingColumn = column - (int) (kernel.size() / 2.0);

    List<List<Pixel>> imageMatrix = new ArrayList<List<Pixel>>();
    for (int i = 0; i < kernel.size(); i++) {
      List<Pixel> row = new ArrayList<Pixel>();
      for (int j = 0; j < kernel.size(); j++) {
        try {
          row.add(this.getPixel(image.get(startingRow + i).get(startingColumn + j)));
        } catch (IndexOutOfBoundsException e) {
          row.add(this.getPixel(null));
        }
      }
      imageMatrix.add(row);
    }
    return imageMatrix;
  }

  /**
   * Validates the pixel provided. If the pixel is invalid, returns a dummy pixel. Otherwise,
   * returns a valid pixel given.
   *
   * @param other the given pixel to check.
   * @return the valid pixel.
   */
  private Pixel getPixel(Pixel other) {
    if (other == null) {
      return new Pixel(new PositionPixel(0, 0), new ColorPixel(0, 0, 0));
    } else {
      return other;
    }
  }

  /**
   * Collects all the RGB values to compute pixel's updated RGB value after filtering, and
   * construct a new RGB value.
   *
   * @param imageMatrix the sequence of pixel in matrix form.
   * @param kernel      the sequence of values in matrix form.
   * @return a new updated RGB value of the pixel after filtering.
   */
  private ColorPixel constructNewColor(List<List<Pixel>> imageMatrix, List<List<Double>> kernel) {
    List<List<Double>> updatedColors = new ArrayList<List<Double>>();
    for (int i = 0; i < kernel.size(); i++) {
      for (int j = 0; j < kernel.size(); j++) {
        updatedColors.add(imageMatrix.get(i).get(j).calculateNewColor(kernel.get(i).get(j)));
      }
    }
    return ColorPixel.sumChannels(updatedColors);
  }
}
