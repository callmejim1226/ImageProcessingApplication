package model;

import java.util.List;

/**
 * This interface represents an operation that can be done with an image.
 */
public interface ImageOperation {
  /**
   * Returns a new image which some image operation was applied on.
   * Operation list:
   * <p><li>filter: blur, sharp</li>
   * <li>color transformation: grayscale, sepia tone</li></p>
   *
   * @return a new image (which is represented as a 2D list of pixels) with operations applied.
   */
  List<List<Pixel>> apply();
}
