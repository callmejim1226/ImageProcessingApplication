package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class represents one of the operations for filtering. It contains the method that filters
 * the image by blurring it.
 */
public class Blur extends Filter implements ImageOperation {
  private final List<List<Double>> kernel;

  /**
   * Constructs a blur filter operation with a specific kernel and an image.
   *
   * @param image the image to be blurred.
   * @throws IllegalArgumentException if the provided image is null.
   */
  public Blur(List<List<Pixel>> image) throws IllegalArgumentException {
    super(image);
    this.kernel = createKernel();
  }

  @Override
  public List<List<Pixel>> apply() {
    return filter(kernel);
  }

  /**
   * Creates a specific for blurring kernel.
   *
   * @return a blur filter in a matrix form.
   */
  private List<List<Double>> createKernel() {
    List<List<Double>> kernel = new ArrayList<List<Double>>();
    List<Double> rowOne = Arrays.asList(0.0625, 0.125, 0.0625);
    List<Double> rowTwo = Arrays.asList(0.125, 0.25, 0.125);
    List<Double> rowThree = Arrays.asList(0.0625, 0.125, 0.0625);
    kernel.add(rowOne);
    kernel.add(rowTwo);
    kernel.add(rowThree);
    return kernel;
  }
}
