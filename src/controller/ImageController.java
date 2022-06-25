package controller;

/**
 * Interface for creating the layer image based on user's script or types. An implementation will
 * work with the ImageModel and ImageOperation interface to provide a image for the layer.
 */
public interface ImageController {

  /**
   * Creates the layer image based on user's script or type. It renders the error message to the
   * ImageViewer interface if the error occurs, and it also renders the status of creating the
   * image.
   */
  void modelMultiLayers();
}
