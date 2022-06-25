package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class represents one of the color transformations methods. It contains a method that
 * changes the color of the image to sepia tone.
 */
public class Sepia extends ColorTransformation implements ImageOperation {
  private final List<List<Double>> matrix;

  /**
   * Constructs a sepia tone operation with a specific matrix and an image.
   *
   * @param image the image to be colored in sepia tone
   * @throws IllegalArgumentException if the provided image is null.
   */
  public Sepia(List<List<Pixel>> image) throws IllegalArgumentException {
    super(image);
    this.matrix = createMatrix();
  }

  @Override
  public List<List<Pixel>> apply() {
    return transformingImage(matrix);
  }

  /**
   * Creates a specific matrix for sepia tone image color transformation operation.
   *
   * @return a sepia matrix
   */
  private List<List<Double>> createMatrix() {
    List<List<Double>> transformMatrix = new ArrayList<List<Double>>();
    List<Double> row1 = Arrays.asList(0.393, 0.769, 0.189);
    List<Double> row2 = Arrays.asList(0.349, 0.686, 0.168);
    List<Double> row3 = Arrays.asList(0.272, 0.534, 0.131);
    transformMatrix.add(row1);
    transformMatrix.add(row2);
    transformMatrix.add(row3);
    return transformMatrix;
  }
}
