package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class represents one of the operations for transforming the color of the image.
 * It contains a method that changes the color of the image to grayscale.
 */
public class Grayscale extends ColorTransformation implements ImageOperation {
  private final List<List<Double>> matrix;

  /**
   * Constructs a grayscale image operation with a specific matrix and an image.
   *
   * @param image the image for grayscale.
   * @throws IllegalArgumentException if the provided image is null.
   */
  public Grayscale(List<List<Pixel>> image) throws IllegalArgumentException {
    super(image);
    this.matrix = createMatrix();
  }

  @Override
  public List<List<Pixel>> apply() {
    return transformingImage(matrix);
  }

  /**
   * Creates a matrix for grayscale color transformation operation.
   *
   * @return a grayscale matrix.
   */
  private List<List<Double>> createMatrix() {
    List<List<Double>> transformMatrix = new ArrayList<List<Double>>();
    List<Double> row1 = Arrays.asList(0.2126, 0.7152, 0.0722);
    transformMatrix.add(row1);
    transformMatrix.add(row1);
    transformMatrix.add(row1);
    return transformMatrix;
  }
}
