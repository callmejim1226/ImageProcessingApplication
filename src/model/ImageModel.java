package model;

import java.util.List;

/**
 * This interface represents an image model that contains an operation for creating images
 * programmatically.
 */
public interface ImageModel {
  /**
   * Creates an image of different pattern programmatically.
   *
   * @return a created image as a sequence of pixels.
   */
  List<List<Pixel>> createImageProgram();
}
