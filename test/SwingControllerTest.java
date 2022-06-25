import org.junit.Test;

import java.awt.event.ActionEvent;
import java.io.IOException;

import controller.SwingController;
import model.ImageUtil;
import model.MultiLayer;


import static org.junit.Assert.assertEquals;

/**
 * This class is a test class for {@code SwingController} class to ensure that methods work
 * properly.
 */

public class SwingControllerTest {

  // test the constructor - invalid null appendable
  @Test(expected = IllegalArgumentException.class)
  public void testSwingConstructorInvalidNullAppendable() {
    new SwingController(null, new MockView(), new MultiLayer());
  }

  // test the constructor - invalid null model
  @Test(expected = IllegalArgumentException.class)
  public void testSwingConstructorInvalidNullModel() {
    new SwingController(new StringBuilder(), new MockView(), null);
  }

  // test the constructor - invalid null view
  @Test(expected = IllegalArgumentException.class)
  public void testSwingConstructorInvalidNullView() {
    new SwingController(new StringBuilder(), null, new MultiLayer());
  }

  // test actionPerformed method - null
  @Test
  public void testActionPerformedNull() {
    MultiLayer model = new MultiLayer();
    assertEquals(0, model.getMultiLayerSize());
    MockView v = new MockView();
    SwingController controller = new SwingController(new StringBuilder(), v, model);
    controller.actionPerformed(null);
    assertEquals(0, model.getMultiLayerSize());
  }

  // test actionPerformed method - creating a new layer
  @Test
  public void testActionPerformedCreateLayer() {
    MultiLayer model = new MultiLayer();
    assertEquals(0, model.getMultiLayerSize());
    MockView v = new MockView();
    SwingController controller = new SwingController(new StringBuilder(), v, model);
    controller.actionPerformed(new ActionEvent(v, 1, "create layer"));
    assertEquals(1, model.getMultiLayerSize());
    assertEquals(0, model.getLayer(0).layerImage().size());
  }

  // test actionPerformed method - it is not allowing the user to create 2 layers with the same name
  @Test
  public void testActionPerformedSameLayerNames() {
    MultiLayer model = new MultiLayer();
    assertEquals(0, model.getMultiLayerSize());
    MockView v = new MockView();
    SwingController controller = new SwingController(new StringBuilder(), v, model);
    controller.actionPerformed(new ActionEvent(v, 1, "create layer"));
    controller.actionPerformed(new ActionEvent(v, 1, "create layer"));
    assertEquals(1, model.getMultiLayerSize());
  }

  // test actionPerformed method - the action that does not exist cannot change the state of model
  @Test
  public void testActionPerformedDefaultCase() {
    MultiLayer model = new MultiLayer();
    assertEquals(0, model.getMultiLayerSize());
    MockView v = new MockView();
    SwingController controller = new SwingController(new StringBuilder(), v, model);
    controller.actionPerformed(new ActionEvent(v, 1, "some action"));
    assertEquals(0, model.getMultiLayerSize());
  }

  // test actionPerformed method - loading checkerboard
  @Test
  public void testActionPerformedCheckerboard() {
    MultiLayer model = new MultiLayer();
    assertEquals(0, model.getMultiLayerSize());
    MockView v = new MockView();
    SwingController controller = new SwingController(new StringBuilder(), v, model);
    controller.actionPerformed(new ActionEvent(v, 1, "create layer"));
    assertEquals(1, model.getMultiLayerSize());
    assertEquals(0, model.getLayer(0).layerImage().size());
    controller.actionPerformed(new ActionEvent(v, 2, "board inputs"));
    assertEquals(0, model.getLayer(0).layerImage().size());
    controller.actionPerformed(new ActionEvent(v, 2, "Load CheckerBoard"));
    assertEquals(ImageUtil.createImage("test/images/checkerboardTest.ppm"),
            model.getLayer(0).layerImage());
    assertEquals("layer1", model.getTopMostLayer().getName());
  }

  // test actionPerformed method - saving file
  @Test
  public void testActionPerformedSavingFile() {
    MultiLayer model = new MultiLayer();
    assertEquals(0, model.getMultiLayerSize());
    MockView v = new MockView();
    SwingController controller = new SwingController(new StringBuilder(), v, model);
    controller.actionPerformed(new ActionEvent(v, 1, "create layer"));
    assertEquals(1, model.getMultiLayerSize());
    assertEquals(0, model.getLayer(0).layerImage().size());
    controller.actionPerformed(new ActionEvent(v, 2, "board inputs"));
    assertEquals(0, model.getLayer(0).layerImage().size());
    controller.actionPerformed(new ActionEvent(v, 2, "Load CheckerBoard"));
    assertEquals(ImageUtil.createImage("test/images/checkerboardTest.ppm"),
            model.getLayer(0).layerImage());
    controller.actionPerformed(new ActionEvent(v, 2, "fileName input"));
    controller.actionPerformed(new ActionEvent(v, 2, "Save File"));
    try {
      assertEquals(ImageUtil.importImage("test/images/sampleImage.png"),
              model.getLayer(0).layerImage());
    } catch (IOException e) {
      throw new IllegalStateException("fail");
    }
  }

  // test actionPerformed method - saving all
  @Test
  public void testActionPerformedSavingAll() {
    MultiLayer model = new MultiLayer();
    assertEquals(0, model.getMultiLayerSize());
    MockView v = new MockView();
    SwingController controller = new SwingController(new StringBuilder(), v, model);
    controller.actionPerformed(new ActionEvent(v, 1, "create layer"));
    assertEquals(1, model.getMultiLayerSize());
    assertEquals(0, model.getLayer(0).layerImage().size());
    controller.actionPerformed(new ActionEvent(v, 2, "board inputs"));
    assertEquals(0, model.getLayer(0).layerImage().size());
    controller.actionPerformed(new ActionEvent(v, 2, "Load CheckerBoard"));
    assertEquals(ImageUtil.createImage("test/images/checkerboardTest.ppm"),
            model.getLayer(0).layerImage());
    controller.actionPerformed(new ActionEvent(v, 2, "fileNames inputs"));
    controller.actionPerformed(new ActionEvent(v, 2, "Save All"));
    try {
      assertEquals(ImageUtil.importImage("test/images/sampleImage1.png"),
              model.getLayer(0).layerImage());
    } catch (IOException e) {
      throw new IllegalStateException("fail");
    }
  }

  // test actionPerformed method - apply no effect chosen
  @Test
  public void testActionPerformedApply() {
    MultiLayer model = new MultiLayer();
    assertEquals(0, model.getMultiLayerSize());
    MockView v = new MockView();
    SwingController controller = new SwingController(new StringBuilder(), v, model);
    controller.actionPerformed(new ActionEvent(v, 1, "create layer"));
    assertEquals(1, model.getMultiLayerSize());
    assertEquals(0, model.getLayer(0).layerImage().size());
    controller.actionPerformed(new ActionEvent(v, 2, "board inputs"));
    assertEquals(0, model.getLayer(0).layerImage().size());
    controller.actionPerformed(new ActionEvent(v, 2, "Load CheckerBoard"));
    assertEquals(ImageUtil.createImage("test/images/checkerboardTest.ppm"),
            model.getLayer(0).layerImage());
    controller.actionPerformed(new ActionEvent(v, 2, "apply"));
    assertEquals(1, model.getMultiLayerSize());
    assertEquals(ImageUtil.createImage("test/images/checkerboardTest.ppm"),
            model.getLayer(0).layerImage());
  }
}