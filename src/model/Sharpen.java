package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class represents one of the operations for filtering. It contains the method that filters
 * the image by sharpening it.
 */
public class Sharpen extends Filter implements ImageOperation {
  private final List<List<Double>> kernel;

  /**
   * Constructs a sharpen filter operation with a specific kernel and an image.
   *
   * @param image the image to sharpen
   * @throws IllegalArgumentException if the provided image is null.
   */
  public Sharpen(List<List<Pixel>> image) throws IllegalArgumentException {
    super(image);
    this.kernel = createKernel();
  }

  @Override
  public List<List<Pixel>> apply() {
    return filter(kernel);
  }

  /**
   * Creates a specific kernel for sharpening.
   *
   * @return a sharpen filter
   */
  private List<List<Double>> createKernel() {
    List<List<Double>> kernel = new ArrayList<List<Double>>();
    List<Double> row1 = Arrays.asList(-0.125, -0.125, -0.125, -0.125, -0.125);
    List<Double> row2 = Arrays.asList(-0.125, 0.25, 0.25, 0.25, -0.125);
    List<Double> row3 = Arrays.asList(-0.125, 0.25, 1.0, 0.25, -0.125);
    kernel.add(row1);
    kernel.add(row2);
    kernel.add(row3);
    kernel.add(row2);
    kernel.add(row1);
    return kernel;
  }
}
