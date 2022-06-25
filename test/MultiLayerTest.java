import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.ColorPixel;
import model.Layer;
import model.MultiLayer;
import model.Pixel;
import model.PositionPixel;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * This class represents a test class for {@code MultiLayer} to ensure methods work correctly.
 */
public class MultiLayerTest {
  MultiLayer model = new MultiLayer();

  // test getCurrentState method
  @Test
  public void testGetCurrentState() {
    assertEquals(0, model.getCurrentState().size());
    model.addLayer(new Layer("layer"));
    assertEquals(1, model.getCurrentState().size());
    assertTrue(model.getLayer(0).isCurrentLayer("layer"));
    assertEquals(0, model.getLayer(0).layerImage().size());
    assertTrue(model.getLayer(0).isTopMostVisible());
  }

  // test getMultiLayerSize method
  @Test
  public void testGetMultiLayerSize() {
    assertEquals(0, model.getMultiLayerSize());
    model.addLayer(new Layer("first"));
    assertEquals(1, model.getMultiLayerSize());
    model.addLayer((new Layer("second")));
    assertEquals(2, model.getMultiLayerSize());
  }

  // test getLayer method - invalid index
  @Test(expected = IllegalArgumentException.class)
  public void testGetLayerInvalidIndex() {
    model.getLayer(0);
  }

  // test getLayer method
  @Test
  public void testGetLayer() {
    model.addLayer(new Layer("first"));
    model.addLayer(new Layer("second"));
    assertTrue(model.getLayer(1).isCurrentLayer("second"));
    assertFalse(model.getLayer(0).isCurrentLayer("second"));
    assertTrue(model.getLayer(0).isCurrentLayer("first"));
    model.getLayer(1).setLayerInvisible();
    assertFalse(model.getLayer(1).isTopMostVisible());
    assertTrue(model.getLayer(0).isTopMostVisible());
    model.getLayer(0).setLayerImage("test/images/imageTest.ppm", "ppm");
    List<List<Pixel>> image = new ArrayList<List<Pixel>>();
    image.add(Arrays.asList(new Pixel(new PositionPixel(0, 0),
            new ColorPixel(0, 0, 0))));
    assertArrayEquals(image.toArray(), model.getLayer(0).layerImage().toArray());
  }

  // test setState method - other state is null
  @Test(expected = IllegalArgumentException.class)
  public void testSetStateNullStateInvalid() {
    model.setState(null);
  }

  // test setState method
  @Test
  public void testSetState() {
    assertEquals(0, model.getMultiLayerSize());
    model.addLayer(new Layer("first"));
    assertEquals(1, model.getMultiLayerSize());
    model.addLayer((new Layer("second")));
    assertEquals(2, model.getMultiLayerSize());
    List<Layer> otherState = new ArrayList<Layer>();
    model.setState(otherState);
    assertEquals(0, model.getMultiLayerSize());
  }

  // test removeLayer method - null layer to remove
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveLayerNullInvalidLayer() {
    model.removeLayer(null);
  }

  // test removeLayer method
  @Test
  public void testRemoveLayer() {
    model.addLayer(new Layer("first"));
    model.addLayer(new Layer("second"));
    model.addLayer(new Layer("third"));
    assertEquals(3, model.getMultiLayerSize());
    model.removeLayer(model.getLayer(1));
    assertEquals(2, model.getMultiLayerSize());
    assertTrue(model.getLayer(0).isCurrentLayer("first"));
    assertTrue(model.getLayer(1).isCurrentLayer("third"));
    model.removeLayer(new Layer("4th")); // does not exist
    assertEquals(2, model.getMultiLayerSize());
    assertTrue(model.getLayer(0).isCurrentLayer("first"));
    assertTrue(model.getLayer(1).isCurrentLayer("third"));
  }

  // test isAllInvisibleLayer method
  @Test
  public void testIsAllInvisibleLayer() {
    assertTrue(model.isAllInvisibleLayer());
    model.addLayer(new Layer("first"));
    assertFalse(model.isAllInvisibleLayer());
    model.getLayer(0).setLayerInvisible();
    assertTrue(model.isAllInvisibleLayer());
  }

  // test getTopMostLayer method - no layer exists
  @Test(expected = IllegalArgumentException.class)
  public void testGetTopMostLayerEmpty() {
    model.getTopMostLayer();
  }

  // test getTopMostLayer method - no visible layer exists
  @Test(expected = IllegalArgumentException.class)
  public void testGetTopMostLayerAllInvisible() {
    model.addLayer(new Layer("first"));
    model.getLayer(0).setLayerInvisible();
    model.getTopMostLayer();
  }

  // test getTopMostLayer method
  @Test
  public void testGetTopMostLayer() {
    model.addLayer(new Layer("first"));
    model.getLayer(0).setLayerInvisible();
    model.addLayer(new Layer("second"));
    model.getLayer(1).setLayerInvisible();
    model.addLayer(new Layer("third"));
    assertTrue(model.getTopMostLayer().isCurrentLayer("third"));
    model.getLayer(0).setLayerVisible();
    assertTrue(model.getTopMostLayer().isCurrentLayer("first"));
  }

  // test addLayer method - null layer to add
  @Test(expected = IllegalArgumentException.class)
  public void testAddLayerNullInvalidLayer() {
    model.addLayer(null);
  }

  // test addLayer method
  @Test
  public void testAddLayer() {
    assertEquals(0, model.getMultiLayerSize());
    model.addLayer(new Layer("first"));
    assertEquals(1, model.getMultiLayerSize());
    assertTrue(model.getLayer(0).isCurrentLayer("first"));
  }
}