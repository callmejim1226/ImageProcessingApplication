package model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a color transformation operation with images.
 * A color transformation modifies the color of a pixel based on its own color.
 */
public abstract class ColorTransformation {
  private final List<List<Pixel>> image;

  /**
   * Constructs an image operation color transformation.
   *
   * @param image the image to apply color transformation on
   * @throws IllegalArgumentException if any of the parameters is null or contains null
   */
  public ColorTransformation(List<List<Pixel>> image) throws IllegalArgumentException {
    validate(image);
    this.image = image;
  }

  /**
   * Constructs the current image state in a sequence of pixel form.
   *
   * @return the current image state in sequence of pixels
   */
  public List<List<Pixel>> imageObserver() {
    return this.image;
  }

  /**
   * Creates a new color transformed image with given color transformation in matrix form.
   *
   * @param matrix a color transformation in matrix form.
   * @return a new color transformed image.
   */
  protected List<List<Pixel>> transformingImage(List<List<Double>> matrix) {
    List<List<Pixel>> transformedImage = new ArrayList<List<Pixel>>();
    for (List<Pixel> lop : image) {
      List<Pixel> transformedRow = new ArrayList<Pixel>();
      for (Pixel pixel : lop) {
        transformedRow.add(pixel.transformColor(matrix));
      }
      transformedImage.add(transformedRow);
    }
    return transformedImage;
  }

  /**
   * Validates the constructor of the class by verifying that all arguments are valid.
   *
   * @param img the image to check
   * @throws IllegalArgumentException if any of the arguments is null (inside the list and pixel)
   */
  protected static void validate(List<List<Pixel>> img) throws IllegalArgumentException {
    if (img == null || img.size() < 1) {
      throw new IllegalArgumentException("Invalid argument");
    }
    for (int i = 0; i < img.size(); i++) {
      if (img.get(i) == null || img.get(i).size() < 1) {
        throw new IllegalArgumentException("Invalid row");
      }
      for (int j = 0; j < img.get(i).size(); j++) {
        if (img.get(i).get(j) == null) {
          throw new IllegalArgumentException("Invalid null pixel");
        }
      }
    }
  }
}
