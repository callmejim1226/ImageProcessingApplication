package view;

import java.io.IOException;

/**
 * Interface of the image textual viewer. It provides a textual view of the error message that
 * occurs from the user's script or types, and a friednly message of status on creating image.
 */
public interface ImageViewer {
  /**
   * Render a specific message to the provided data destination.
   *
   * @param message the message to be transmitted
   * @throws IOException if transmission of the board to the provided data destination fails
   */
  void renderMessage(String message) throws IOException;

  /**
   * Gets the last recent message from the viewer.
   *
   * @return a last recent message from viewer in string form.
   */
  String getMessage();

}
