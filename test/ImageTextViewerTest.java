import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import view.ImageTextViewer;
import view.ImageViewer;

/**
 * Tests class for ImageViewerTest: unit tests to ensure that textual image view can operate
 * correctly and otherwise behave correctly.
 */
public class ImageTextViewerTest {
  ImageViewer imageView;
  Appendable object;


  /**
   * Initializes the image view.
   */
  private void startingImage() {
    object = new StringBuilder();
    imageView = new ImageTextViewer(object);
  }

  // testing for rendering the provided message + empty message
  @Test
  public void testRenderMessage() throws IOException {
    startingImage();
    imageView.renderMessage("Test succeed.");
    assertEquals("Test succeed.\n", object.toString());
    imageView.renderMessage("");
    assertEquals("Test succeed.\n\n", object.toString());
  }

  // test renderMessage method - get IOException
  @Test(expected = IOException.class)
  public void testInvalidRenderMessage() throws IOException {
    Appendable ap = new AppendableFail();
    ImageViewer view = new ImageTextViewer(ap);
    view.renderMessage("fail");
  }

  // test the constructor - invalid null argument
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstructor() {
    ImageViewer view = new ImageTextViewer(null);
  }

  // test renderMessage method - null message
  @Test(expected = IllegalArgumentException.class)
  public void testRenderMessageNullMessage() throws IOException {
    startingImage();
    imageView.renderMessage(null);
  }

  // test getMessage method
  @Test
  public void testGetMessage() {
    startingImage();
    assertEquals("", imageView.getMessage());
    try {
      imageView.renderMessage("Success!");
    } catch (IOException e) {
      throw new IllegalStateException("Illegal process");
    }
    assertEquals("Success!", imageView.getMessage());
  }
}
