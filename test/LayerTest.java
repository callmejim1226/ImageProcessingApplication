import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.CheckerBoardImage;
import model.ColorPixel;
import model.ImageUtil;
import model.Layer;
import model.Pixel;
import model.PositionPixel;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * This class is a test class for {@code Layer} class to ensure all methods work properly.
 */
public class LayerTest {

  // test constructor - null name
  @Test(expected = IllegalArgumentException.class)
  public void testLayerConstructorNullName() {
    new Layer(null);
  }

  // test constructor - empty name
  @Test(expected = IllegalArgumentException.class)
  public void testLayerConstructorEmptyName() {
    new Layer("");
  }

  // test setLayerInvisible, setLayerVisible, and isTopMostVisible methods
  @Test
  public void testSetLayerVisibility() {
    Layer layer = new Layer("layer1");
    assertTrue(layer.isTopMostVisible());
    layer.setLayerInvisible();
    assertFalse(layer.isTopMostVisible());
    layer.setLayerVisible();
    assertTrue(layer.isTopMostVisible());
  }

  // test isCurrentLayer method
  @Test
  public void testIsCurrentLayer() {
    Layer layer = new Layer("layer1");
    assertTrue(layer.isCurrentLayer("layer1"));
    assertFalse(layer.isCurrentLayer("layer"));
  }

  // test isCurrentLayer method - invalid null layerName
  @Test(expected = IllegalArgumentException.class)
  public void testIsCurrentLayerNullLayerName() {
    Layer layer = new Layer("layer1");
    layer.isCurrentLayer(null);
  }

  // test isCurrentLayer method - invalid empty layerName
  @Test(expected = IllegalArgumentException.class)
  public void testIsCurrentLayerEmptyLayerName() {
    Layer layer = new Layer("layer1");
    layer.isCurrentLayer("");
  }

  // test setLayerImageToAnotherImage and layerImage methods
  @Test
  public void testSetLayerImageToAnotherImageAndLayerImage() {
    List<List<Pixel>> image = new ArrayList<List<Pixel>>();
    Layer layer = new Layer("layer1");
    assertArrayEquals(image.toArray(), layer.layerImage().toArray()); // empty image
    image.add(Arrays.asList(new Pixel(new PositionPixel(0, 0),
            new ColorPixel(0, 0, 0))));
    layer.setLayerImageToAnotherImage(image);
    assertArrayEquals(image.toArray(), layer.layerImage().toArray());
  }

  // test setLayerImageToAnotherImage method - null image
  @Test(expected = IllegalArgumentException.class)
  public void testSetLayerImageToAnotherImageAndLayerImageInvalidImageNull() {
    Layer layer = new Layer("layer1");
    layer.setLayerImageToAnotherImage(null);
  }

  // test setLayerImageToAnotherImage method - empty image
  @Test(expected = IllegalArgumentException.class)
  public void testSetLayerImageToAnotherImageAndLayerImageEmptyImage() {
    List<List<Pixel>> empty = new ArrayList<List<Pixel>>();
    Layer layer = new Layer("layer1");
    layer.setLayerImageToAnotherImage(empty);
  }

  // test setLayerImageToAnotherImage method - a 2D list contains an empty list
  @Test(expected = IllegalArgumentException.class)
  public void testSetLayerImageToAnotherImageAndLayerImageEmptyListImage() {
    List<List<Pixel>> listWithEmpty = new ArrayList<List<Pixel>>();
    listWithEmpty.add(new ArrayList<>());
    Layer layer = new Layer("layer1");
    layer.setLayerImageToAnotherImage(listWithEmpty);
  }

  // test setLayerImageToAnotherImage method - 2D list contains a null
  @Test(expected = IllegalArgumentException.class)
  public void testSetLayerImageToAnotherImageAndLayerImageImageContainsNull() {
    List<List<Pixel>> listWithNull = new ArrayList<List<Pixel>>();
    listWithNull.add(null);
    Layer layer = new Layer("layer1");
    layer.setLayerImageToAnotherImage(listWithNull);
  }

  // test setLayerImageToAnotherImage method - a list contains a null
  @Test(expected = IllegalArgumentException.class)
  public void testSetLayerImageToAnotherImageAndLayerImageImageListContainsNull() {
    List<List<Pixel>> listWithNull = new ArrayList<List<Pixel>>();
    listWithNull.add(Arrays.asList(new Pixel(new PositionPixel(0, 0),
            new ColorPixel(0, 0, 0)), null));
    Layer layer = new Layer("layer1");
    layer.setLayerImageToAnotherImage(listWithNull);
  }

  // test setLayerImage method - jpeg format
  @Test
  public void testSetLayerImageJPEG() {
    List<List<Pixel>> image = new ArrayList<List<Pixel>>();
    image.add(Arrays.asList(new Pixel(new PositionPixel(0, 0),
            new ColorPixel(0, 0, 0))));
    Layer layer = new Layer("layer1");
    layer.setLayerImage("test/images/imageTestJPEG.jpeg", "jpeg");
    assertArrayEquals(image.toArray(),
            layer.layerImage().toArray());
  }

  // test setLayerImage method - ppm format
  @Test
  public void testSetLayerImagePPM() {
    List<List<Pixel>> image = new ArrayList<List<Pixel>>();
    image.add(Arrays.asList(new Pixel(new PositionPixel(0, 0),
            new ColorPixel(0, 0, 0))));
    Layer layer = new Layer("layer1");
    layer.setLayerImage("test/images/imageTest.ppm", "ppm");
    assertArrayEquals(image.toArray(),
            layer.layerImage().toArray());
  }

  // test setLayerImage method - png format
  @Test
  public void testSetLayerImagePNG() {
    List<List<Pixel>> image = new ArrayList<List<Pixel>>();
    image.add(Arrays.asList(new Pixel(new PositionPixel(0, 0),
            new ColorPixel(0, 0, 0))));
    Layer layer = new Layer("layer1");
    layer.setLayerImage("test/images/imageTestPNG.png", "png");
    assertArrayEquals(image.toArray(),
            layer.layerImage().toArray());
  }

  // test setLayerImage method - null filename
  @Test(expected = IllegalArgumentException.class)
  public void testSetLayerImagePPMNullFilename() {
    Layer layer = new Layer("layer1");
    layer.setLayerImage(null, "ppm");
  }

  // test setLayerImage method - null filename
  @Test(expected = IllegalArgumentException.class)
  public void testSetLayerImagePNGNullFilename() {
    Layer layer = new Layer("layer1");
    layer.setLayerImage(null, "png");
  }

  // test setLayerImage method - null filename
  @Test(expected = IllegalArgumentException.class)
  public void testSetLayerImageJPEGNullFilename() {
    Layer layer = new Layer("layer1");
    layer.setLayerImage(null, "jpeg");
  }

  // test setLayerImage method - null format
  @Test(expected = IllegalArgumentException.class)
  public void testSetLayerImageNullFormat() {
    Layer layer = new Layer("layer1");
    layer.setLayerImage("test/images/testImage.ppm", null);
  }

  // test setLayerImage method - empty filename
  @Test(expected = IllegalArgumentException.class)
  public void testSetLayerImagePPMEmptyFilename() {
    Layer layer = new Layer("layer1");
    layer.setLayerImage("", "ppm");
  }

  // test setLayerImage method - empty filename
  @Test(expected = IllegalArgumentException.class)
  public void testSetLayerImageJPEGEmptyFilename() {
    Layer layer = new Layer("layer1");
    layer.setLayerImage("", "JPEG");
  }

  // test setLayerImage method - empty filename
  @Test(expected = IllegalArgumentException.class)
  public void testSetLayerImagePNGEmptyFilename() {
    Layer layer = new Layer("layer1");
    layer.setLayerImage("", "PNG");
  }

  // test setLayerImage method - file not found
  @Test(expected = IllegalArgumentException.class)
  public void testSetLayerImagePPMFileNotFound() {
    Layer layer = new Layer("layer1");
    layer.setLayerImage("Money.ppm", "ppm");
  }

  // test setLayerImage method - invalid ppm image - no P3
  @Test(expected = IllegalArgumentException.class)
  public void testSetLayerImagePPMInvalidImageNoP3() {
    Layer layer = new Layer("layer1");
    layer.setLayerImage("test/images/invalidImage.ppm", "ppm");
  }

  // test setLayerImage method - invalid max value (should be 255 for ppm)
  @Test(expected = IllegalArgumentException.class)
  public void testSetLayerImagePPMInvalidMaxValue() {
    Layer layer = new Layer("layer1");
    layer.setLayerImage("test/images/imageInvalidMaxValue.ppm", "ppm");
  }

  // test setLayerImage method - invalid green color value
  @Test(expected = IllegalArgumentException.class)
  public void testSetLayerImagePPMInvalidGreenColorValue() {
    Layer layer = new Layer("layer1");
    layer.setLayerImage("test/images/imageInvalidGreenColorValue.ppm", "ppm");
  }

  // test setLayerImage method - invalid red color value
  @Test(expected = IllegalArgumentException.class)
  public void testSetLayerImagePPMInvalidRedColorValue() {
    Layer layer = new Layer("layer1");
    layer.setLayerImage("test/images/imageInvalidRedColorValue.ppm", "ppm");
  }

  // test setLayerImage method - invalid blue color value
  @Test(expected = IllegalArgumentException.class)
  public void testSetLayerImagePPMInvalidBlueColorValue() {
    Layer layer = new Layer("layer1");
    layer.setLayerImage("test/images/imageInvalidBlueColorValue.ppm", "ppm");
  }

  // test setLayerImage method - invalid width of the image
  @Test(expected = IllegalArgumentException.class)
  public void testSetLayerImagePPMInvalidWidth() {
    Layer layer = new Layer("layer1");
    layer.setLayerImage("test/images/imageInvalidWidth.ppm", "ppm");
  }

  // test setLayerImage method - invalid height of the image
  @Test(expected = IllegalArgumentException.class)
  public void testSetLayerImageInvalidHeight() {
    Layer layer = new Layer("layer1");
    layer.setLayerImage("test/images/imageInvalidHeight.ppm", "ppm");
  }

  // test setLayerImage method
  @Test
  public void testSetLayerImage() throws IOException {
    List<List<Pixel>> image = new ArrayList<List<Pixel>>();
    image.add(Arrays.asList(new Pixel(new PositionPixel(0, 0),
            new ColorPixel(0, 0, 0))));
    Layer layer = new Layer("layer1");
    layer.setLayerImage("test/images/imageTestJPEG.jpeg", "jpeg");
    assertArrayEquals(image.toArray(), layer.layerImage().toArray());
    layer.setLayerImage("res/Boston.ppm", "ppm");
    assertNotEquals(image.get(0).get(0), layer.layerImage().get(0).get(0));
    layer.setLayerImage("test/images/imageTestPNG.png", "png");
    assertArrayEquals(image.toArray(), layer.layerImage().toArray());
  }

  // test setLayerImage
  @Test(expected = IllegalStateException.class)
  public void testSetLayerImageFileNotFoundPNG() {
    Layer layer = new Layer("layer1");
    layer.setLayerImage("Money.png", "png");
  }

  // test setLayerImage
  @Test(expected = IllegalStateException.class)
  public void testSetLayerImageFileNotFoundJPEG() {
    Layer layer = new Layer("layer1");
    layer.setLayerImage("Money.jpeg", "jpeg");
  }

  // test setLayerImage - invalid format
  @Test(expected = IllegalArgumentException.class)
  public void testSetLayerImageInvalidFormat() {
    Layer layer = new Layer("layer1");
    layer.setLayerImage("test/images/imageTest.ppm", "hmm");
  }

  // test exportLayer method - null format
  @Test(expected = IllegalArgumentException.class)
  public void testWritePPMNullImage() {
    Layer layer = new Layer("layer1");
    List<List<Pixel>> image = new CheckerBoardImage(1, 2, 2,
            Arrays.asList(new ColorPixel(3, 6, 19),
                    new ColorPixel(240, 100, 80))).createImageProgram();
    layer.setLayerImageToAnotherImage(image);
    layer.exportLayer(null, "ood.ppm");
  }

  // test exportLayer method - null filename
  @Test(expected = IllegalArgumentException.class)
  public void testWritePPMNullFilename() {
    Layer layer = new Layer("layer1");
    List<List<Pixel>> image = new CheckerBoardImage(1, 2, 2,
            Arrays.asList(new ColorPixel(3, 6, 19),
                    new ColorPixel(240, 100, 80))).createImageProgram();
    layer.setLayerImageToAnotherImage(image);
    layer.exportLayer("ppm", null);
  }

  // test exportLayer - empty filename
  @Test(expected = IllegalArgumentException.class)
  public void testWritePPMEmptyFilename() {
    Layer layer = new Layer("layer1");
    List<List<Pixel>> image = new CheckerBoardImage(1, 2, 2,
            Arrays.asList(new ColorPixel(3, 6, 19),
                    new ColorPixel(240, 100, 80))).createImageProgram();
    layer.setLayerImageToAnotherImage(image);
    layer.exportLayer("ppm", "");
  }

  // test exportLayer method - empty image
  @Test(expected = IllegalArgumentException.class)
  public void testWritePPMEmptyImage() {
    List<List<Pixel>> empty = new ArrayList<List<Pixel>>();
    Layer layer = new Layer("layer1");
    layer.setLayerImageToAnotherImage(empty);
    layer.exportLayer("ppm", "ood.ppm");
  }

  // test exportLayer method - a 2D list contains an empty list
  @Test(expected = IllegalArgumentException.class)
  public void testWritePPMEmptyListImage() {
    List<List<Pixel>> listWithEmpty = new ArrayList<List<Pixel>>();
    listWithEmpty.add(new ArrayList<>());
    Layer layer = new Layer("layer1");
    layer.setLayerImageToAnotherImage(listWithEmpty);
    layer.exportLayer("png", "ood.png");
  }

  // test exportLayer method - 2D list contains a null
  @Test(expected = IllegalArgumentException.class)
  public void testWritePPMImageContainsNull() {
    List<List<Pixel>> listWithNull = new ArrayList<List<Pixel>>();
    listWithNull.add(null);
    Layer layer = new Layer("layer1");
    layer.setLayerImageToAnotherImage(listWithNull);
    layer.exportLayer("jpeg", "cs.jpeg");
  }

  // test exportLayer - a list contains a null
  @Test(expected = IllegalArgumentException.class)
  public void testImageListContainsNull() {
    List<List<Pixel>> listWithNull = new ArrayList<List<Pixel>>();
    listWithNull.add(Arrays.asList(new Pixel(new PositionPixel(0, 0),
            new ColorPixel(0, 0, 0)), null));
    Layer layer = new Layer("layer1");
    layer.setLayerImageToAnotherImage(listWithNull);
    layer.exportLayer("jpeg", "cs.jpeg");
  }

  // test exportLayer method - ppm
  @Test
  public void testExportLayerPPM() {
    Layer layer = new Layer("layer1");
    List<List<Pixel>> image = new ArrayList<List<Pixel>>();
    image.add(Arrays.asList(new Pixel(new PositionPixel(0, 0),
            new ColorPixel(0, 0, 0))));
    layer.setLayerImageToAnotherImage(image);
    layer.exportLayer("ppm", "test/images/square.ppm");
    assertArrayEquals(image.toArray(),
            ImageUtil.createImage("test/images/square.ppm").toArray());
  }

  // test exportLayer method - jpeg
  @Test
  public void testExportLayerJPEG() throws IOException {
    Layer layer = new Layer("layer1");
    List<List<Pixel>> image = new ArrayList<List<Pixel>>();
    image.add(Arrays.asList(new Pixel(new PositionPixel(0, 0),
            new ColorPixel(0, 0, 0))));
    layer.setLayerImageToAnotherImage(image);
    layer.exportLayer("JPEG", "test/images/square.jpeg");
    assertArrayEquals(image.toArray(),
            ImageUtil.importImage("test/images/square.jpeg").toArray());
  }

  // test exportLayer method - png
  @Test
  public void testExportLayerPNG() throws IOException {
    Layer layer = new Layer("layer1");
    List<List<Pixel>> image = new ArrayList<List<Pixel>>();
    image.add(Arrays.asList(new Pixel(new PositionPixel(0, 0),
            new ColorPixel(0, 0, 0))));
    layer.setLayerImageToAnotherImage(image);
    layer.exportLayer("PNG", "test/images/square.png");
    assertArrayEquals(image.toArray(),
            ImageUtil.importImage("test/images/square.png").toArray());
  }

  // test exportLayer method - invalid format
  @Test(expected = IllegalArgumentException.class)
  public void testExportLayerInvalidFormat() {
    Layer layer = new Layer("layer1");
    List<List<Pixel>> image = new CheckerBoardImage(1, 2, 2,
            Arrays.asList(new ColorPixel(3, 6, 19),
                    new ColorPixel(240, 100, 80))).createImageProgram();
    layer.setLayerImageToAnotherImage(image);
    layer.exportLayer("huh", "test/images/checkerboard.png");
  }

  // test getName method
  @Test
  public void testGetName() {
    Layer layer = new Layer("first");
    assertEquals("first", layer.getName());
  }
}