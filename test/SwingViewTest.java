import org.junit.Test;

import view.ISwingViewer;
import view.SwingView;

/**
 * This test class is for {@code SwingView} class to ensure methods work correctly.
 */
public class SwingViewTest {
  ISwingViewer view = new SwingView();

  // test setListeners - invalid input
  @Test(expected = IllegalArgumentException.class)
  public void testSetListenersNullInvalid() {
    view.setListeners(null);
  }

  // test setLayerNameOutput - invalid input
  @Test(expected = IllegalArgumentException.class)
  public void testSetLayerNameOutputNullInvalid() {
    view.setLayerNameOutput(null);
  }

  // test setNewFileName - invalid input
  @Test(expected = IllegalArgumentException.class)
  public void testSetNewFileNameNullInvalid() {
    view.setNewFileName(null);
  }

  // test setLayerImage -  invalid input
  @Test(expected = IllegalArgumentException.class)
  public void testSetLayerImageNullInvalid() {
    view.setLayerImage(null);
  }


  // test setMultipleFileNames -  invalid input
  @Test(expected = IllegalArgumentException.class)
  public void testSetMultipleFileNames() {
    view.setMultipleFileNames(null);
  }

  // test setLayersNames -  invalid input
  @Test(expected = IllegalArgumentException.class)
  public void testSetLayersNames() {
    view.setLayersNames(null);
  }

  // test setBoardInputs -  invalid input
  @Test(expected = IllegalArgumentException.class)
  public void testSetBoardInputs() {
    view.setBoardInputs(null);
  }

  // test sendMessage -  invalid input
  @Test(expected = IllegalArgumentException.class)
  public void testSendMessage() {
    view.sendMessage(null);
  }

  // test getRetValue -  invalid input
  @Test(expected = IllegalArgumentException.class)
  public void testGetRetValue() {
    view.getRetValue(null);
  }

  // test setSavingFileName -  invalid input
  @Test(expected = IllegalArgumentException.class)
  public void testSetSavingFileName() {
    view.setSavingFileName(null);
  }

  // test askInfo -  invalid input
  @Test(expected = IllegalArgumentException.class)
  public void testAskInfo() {
    view.askInfo(null);
  }

}