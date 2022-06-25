package view;

import java.io.IOException;

/**
 * The {@code ImageTextViewer} class represents a textual view of creating image. It supports a
 * view of error message that occurs from the user's script or types.
 */
public class ImageTextViewer implements ImageViewer {
  private Appendable ap;

  /**
   * Constructs a textual view that supports the error message and exporting images.
   *
   * @param ap the object that will visualize the state of creating layer image.
   * @throws IllegalArgumentException if the appendable is null
   */
  public ImageTextViewer(Appendable ap) {
    if (ap == null) {
      throw new IllegalArgumentException("Appendable cannot be null");
    }
    this.ap = ap;
  }

  @Override
  public void renderMessage(String message) throws IOException {
    if (message == null) {
      throw new IllegalArgumentException("Message cannot be null");
    }
    this.ap.append(message + "\n");
  }

  @Override
  public String getMessage() {
    String[] messages = this.ap.toString().split("\n");
    return messages[messages.length - 1];
  }
}