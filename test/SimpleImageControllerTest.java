import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import controller.ImageController;
import controller.SimpleImageController;
import model.CheckerBoardImage;
import model.ColorPixel;
import model.ImageUtil;
import model.MultiLayer;
import model.Pixel;
import model.PositionPixel;

import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Tests class for {@code controller.SimpleImageController}: unit tests to ensure that image
 * controller operates correctly and otherwise behave correctly.
 */
public class SimpleImageControllerTest {

  Readable in;
  Appendable out = new StringBuilder();
  ImageController controller;
  MultiLayer model = new MultiLayer();

  // test constructor - invalid null appendable
  @Test(expected = IllegalArgumentException.class)
  public void testControllerNullAppendable() {
    ImageController controller =
            new SimpleImageController(new StringReader("test/images/imageTest.ppm"), null,
                    new MultiLayer());
  }

  // test constructor - invalid null readable
  @Test(expected = IllegalArgumentException.class)
  public void testControllerNullReadable() {
    ImageController controller =
            new SimpleImageController(null, new StringBuilder(), new MultiLayer());
  }

  // test constructor - invalid null readable and appendable
  @Test(expected = IllegalArgumentException.class)
  public void testControllerNullReadableAppendable() {
    ImageController controller = new SimpleImageController(null, null, new MultiLayer());
  }

  // test constructor - invalid null model
  @Test(expected = IllegalArgumentException.class)
  public void testControllerNullModel() {
    ImageController controller =
            new SimpleImageController(new StringReader("test/images/imageTest.ppm"),
                    new StringBuilder(), null);
  }

  // test constructor - invalid appendable model
  @Test(expected = IllegalStateException.class)
  public void testControllerInvalidAppendable() {
    ImageController controller =
            new SimpleImageController(new StringReader("test/images/imageTest.ppm"),
                    new AppendableFail(), new MultiLayer());
    controller.modelMultiLayers();
  }

  // test convenience constructor - invalid null appendable
  @Test(expected = IllegalArgumentException.class)
  public void testConvenienceInvalidNullAppendable() {
    new SimpleImageController(null, new MultiLayer());
  }

  // test convenience constructor - invalid null model
  @Test(expected = IllegalArgumentException.class)
  public void testConvenienceInvalidNullModel() {
    new SimpleImageController(new StringBuilder(), null);
  }

  /**
   * Models the multi layers with given input.
   *
   * @param input the given input that represents the script or the user types.
   */
  private void createImage(String input) {
    in = new StringReader(input);
    controller = new SimpleImageController(in, out, model);
    controller.modelMultiLayers();
  }

  // test modelMultiLayers method - checks for invalid command
  @Test
  public void testInvalidCommand() {
    assertEquals(0, model.getMultiLayerSize());
    String expectedMsg = "No such command exists for layer operations. Try again.\n";
    createImage("blend layer1 layer2");
    assertEquals(0, model.getMultiLayerSize());
    assertEquals(expectedMsg, out.toString());
  }

  // test modelMultiLayers method - invalid create command
  @Test
  public void testCreateInvalidCommand() {
    assertEquals(0, model.getMultiLayerSize());
    String expectedMsg = "Invalid to create layer. Try again.\n";
    createImage("create image layer2");
    assertEquals(0, model.getMultiLayerSize());
    assertEquals(expectedMsg, out.toString());
  }

  // test create command
  @Test
  public void testCreateCommand() {
    assertEquals(0, model.getMultiLayerSize());
    String expectedMsg = "The layer has been successfully created!\n";
    createImage("create layer first");
    assertEquals(1, model.getMultiLayerSize());
    assertEquals(0, model.getLayer(0).layerImage().size());
    assertFalse(model.isAllInvisibleLayer());
    assertTrue(model.getLayer(0).isCurrentLayer("first"));
    assertEquals(expectedMsg, out.toString());
  }


  // test modelMultiLayers method - invalid create command, missing name
  @Test
  public void testCreateInvalidCommandNoLayerName() {
    assertEquals(0, model.getMultiLayerSize());
    String expectedMsg = "Invalid to create layer. Try again.\n";
    createImage("create image");
    assertEquals(0, model.getMultiLayerSize());
    assertEquals(expectedMsg, out.toString());
  }

  // test modelMultiLayers method - create command but with duplicates
  @Test
  public void testCreateInvalidCommandDuplicates() {
    assertEquals(0, model.getMultiLayerSize());
    String expectedMsg = "The layer has been successfully created!\n" +
            "Invalid to create layer. Try again.\n";
    createImage("create layer first\ncreate layer first");
    assertEquals(1, model.getMultiLayerSize());
    assertEquals(0, model.getLayer(0).layerImage().size());
    assertFalse(model.isAllInvisibleLayer());
    assertTrue(model.getLayer(0).isCurrentLayer("first"));
    assertEquals(expectedMsg, out.toString());
  }

  // test modelMultiLayers method - current command - invalid
  @Test
  public void testCurrentInvalidCommand() {
    assertEquals(0, model.getMultiLayerSize());
    String expectedMsg = "The layer has been successfully created!\n" +
            "No layers to set as current. Try again.\n";
    createImage("create layer first\ncurrent second");
    assertEquals(1, model.getMultiLayerSize());
    assertTrue(model.getLayer(0).isCurrentLayer("first"));
    assertFalse(model.getLayer(0).isCurrentLayer("second"));
    assertEquals(expectedMsg, out.toString());
  }

  // test modelMultiLayers method - current command - invalid
  @Test
  public void testCurrentInvalidCommandFirstCommand() {
    assertEquals(0, model.getMultiLayerSize());
    String expectedMsg = "No layers to set as current. Try again.\n";
    createImage("current first");
    assertEquals(0, model.getMultiLayerSize());
    assertEquals(expectedMsg, out.toString());
  }

  // test modelMultiLayers method - current command
  @Test
  public void testCurrentCommand() {
    assertEquals(0, model.getMultiLayerSize());
    String expectedMsg = "The layer has been successfully created!\n" +
            "The layer has been set to current.\n";
    createImage("create layer first\ncurrent first");
    assertEquals(1, model.getMultiLayerSize());
    assertTrue(model.getTopMostLayer().isCurrentLayer("first"));
    assertEquals(expectedMsg, out.toString());
  }

  // test modelMultiLayers method - load command - there is no visible layer exists
  @Test
  public void testLoadInvalidCommandCannotBeSavedNoVisibleLayer() {
    assertEquals(0, model.getMultiLayerSize());
    String expectedMsg = "No visible layers to load an image. Try again.\n";
    createImage("load first");
    assertEquals(0, model.getMultiLayerSize());
    assertEquals(expectedMsg, out.toString());
  }

  // test modelMultiLayers method - load command - invalid image to load it to layer
  @Test
  public void testLoadInvalidCommandInvalidImageToLoadItToLayer() {
    assertEquals(0, model.getMultiLayerSize());
    String expectedMsg = "The layer has been successfully created!\n" +
            "Invalid image to load it to layer. Try again.\n";
    createImage("create layer first\nload test/images/invalidImage.ppm");
    assertEquals(1, model.getMultiLayerSize());
    assertEquals(0, model.getTopMostLayer().layerImage().size());
    assertEquals(expectedMsg, out.toString());
  }

  // test modelMultiLayers method - load command - No such Image exists
  @Test
  public void testLoadInvalidCommandInvalidNoSuchImageExists() {
    assertEquals(0, model.getMultiLayerSize());
    String expectedMsg = "The layer has been successfully created!\n" +
            "No such Image exists. Try again.\n";
    createImage("create layer first\nload test/images/maybe.jpeg");
    assertEquals(1, model.getMultiLayerSize());
    assertEquals(0, model.getTopMostLayer().layerImage().size());
    assertEquals(expectedMsg, out.toString());
  }

  // test modelMultiLayers method - load command - Invalid inputs to load an image
  @Test
  public void testLoadInvalidInputsToLoadAnImage() {
    assertEquals(0, model.getMultiLayerSize());
    String expectedMsg = "The layer has been successfully created!\n" +
            "Invalid inputs to load an image. Try again.\n";
    createImage("create layer first\nload create image");
    assertEquals(1, model.getMultiLayerSize());
    assertEquals(0, model.getTopMostLayer().layerImage().size());
    assertEquals(expectedMsg, out.toString());
  }

  // test modelMultiLayers method - load command - ppm
  @Test
  public void testLoadValidFilePPM() {
    assertEquals(0, model.getMultiLayerSize());
    String expectedMsg = "The layer has been successfully created!\n" +
            "The layer image has been successfully loaded!\n";
    createImage("create layer first\nload test/images/imageTest.ppm");
    assertEquals(1, model.getMultiLayerSize());
    List<List<Pixel>> image = new ArrayList<List<Pixel>>();
    image.add(Arrays.asList(new Pixel(new PositionPixel(0, 0),
            new ColorPixel(0, 0, 0))));
    assertArrayEquals(image.toArray(), model.getTopMostLayer().layerImage().toArray());
    assertEquals(expectedMsg, out.toString());
  }

  // test modelMultiLayers method - load command - jpeg
  @Test
  public void testLoadValidFileJPEG() {
    assertEquals(0, model.getMultiLayerSize());
    String expectedMsg = "The layer has been successfully created!\n" +
            "The layer image has been successfully loaded!\n";
    createImage("create layer first\nload test/images/imageTestJPEG.jpeg");
    assertEquals(1, model.getMultiLayerSize());
    List<List<Pixel>> image = new ArrayList<List<Pixel>>();
    image.add(Arrays.asList(new Pixel(new PositionPixel(0, 0),
            new ColorPixel(0, 0, 0))));
    assertArrayEquals(image.toArray(), model.getTopMostLayer().layerImage().toArray());
    assertEquals(expectedMsg, out.toString());
  }

  // test modelMultiLayers method - load command - png
  @Test
  public void testLoadValidFilePNG() {
    assertEquals(0, model.getMultiLayerSize());
    String expectedMsg = "The layer has been successfully created!\n" +
            "The layer image has been successfully loaded!\n";
    createImage("create layer first\nload test/images/imageTestPNG.png");
    assertEquals(1, model.getMultiLayerSize());
    List<List<Pixel>> image = new ArrayList<List<Pixel>>();
    image.add(Arrays.asList(new Pixel(new PositionPixel(0, 0),
            new ColorPixel(0, 0, 0))));
    assertArrayEquals(image.toArray(), model.getTopMostLayer().layerImage().toArray());
    assertEquals(expectedMsg, out.toString());
  }

  // test modelMultiLayers method - load command - valid checkerboard
  @Test
  public void testLoadCheckerBoard() {
    assertEquals(0, model.getMultiLayerSize());
    String expectedMsg = "The layer has been successfully created!\n" +
            "Checkerboard image has been successfully loaded to layer!\n";
    createImage("create layer first\nload checkerboard 5 10 10 255 255 255 0 0 0");
    assertEquals(1, model.getMultiLayerSize());
    List<List<Pixel>> image = new CheckerBoardImage(5, 10, 10,
            Arrays.asList(new ColorPixel(255, 255, 255),
                    new ColorPixel(0, 0, 0))).createImageProgram();
    for (int i = 0; i < image.size(); i++) {
      for (int j = 0; j < image.get(0).size(); j++) {
        assertEquals(image.get(i).get(j).getColor(),
                model.getTopMostLayer().layerImage().get(i).get(j).getColor());
      }
    }
    assertEquals(expectedMsg, out.toString());
  }

  // test modelMultiLayers method - load command - invalid checkerboard (invalid tile size)
  @Test
  public void testLoadCheckerBoardInvalidTileSize() {
    assertEquals(0, model.getMultiLayerSize());
    String expectedMsg = "The layer has been successfully created!\n" +
            "Invalid inputs for creating checkerboard image.\n";
    createImage("create layer first\nload checkerboard -1 10 10 255 255 255 0 0 0");
    assertEquals(1, model.getMultiLayerSize());
    assertEquals(0, model.getTopMostLayer().layerImage().size());
    assertEquals(expectedMsg, out.toString());
  }

  // test modelMultiLayers method - load command - invalid checkerboard (invalid width)
  @Test
  public void testLoadCheckerboardInvalidWidth() {
    assertEquals(0, model.getMultiLayerSize());
    String expectedMsg = "The layer has been successfully created!\n" +
            "Invalid inputs for creating checkerboard image.\n";
    createImage("create layer first\nload checkerboard 1 0 10 255 255 255 0 0 0");
    assertEquals(1, model.getMultiLayerSize());
    assertEquals(0, model.getTopMostLayer().layerImage().size());
    assertEquals(expectedMsg, out.toString());
  }

  // test modelMultiLayers method - load command - invalid checkerboard (invalid length)
  @Test
  public void testLoadCheckerboardInvalidLength() {
    assertEquals(0, model.getMultiLayerSize());
    String expectedMsg = "The layer has been successfully created!\n" +
            "Invalid inputs for creating checkerboard image.\n";
    createImage("create layer first\nload checkerboard 1 10 0 255 255 255 0 0 0");
    assertEquals(1, model.getMultiLayerSize());
    assertEquals(0, model.getTopMostLayer().layerImage().size());
    assertEquals(expectedMsg, out.toString());
  }

  // test modelMultiLayers method - load command - invalid checkerboard (invalid color)
  @Test
  public void testLoadCheckerboardInvalidColor() {
    assertEquals(0, model.getMultiLayerSize());
    String expectedMsg = "The layer has been successfully created!\n" +
            "Invalid inputs for creating checkerboard image.\n";
    createImage("create layer first\nload checkerboard 1 10 0 256 257 255 0 -9 0");
    assertEquals(1, model.getMultiLayerSize());
    assertEquals(0, model.getTopMostLayer().layerImage().size());
    assertEquals(expectedMsg, out.toString());
  }

  // test modelMultiLayers method - save command - invalid save (no file like that exists)
  @Test
  public void testSaveInvalidNoFileExists() {
    assertEquals(0, model.getMultiLayerSize());
    String expectedMsg = "The layer has been successfully created!\n" +
            "Invalid File Name for saving a file. Try again.\n";
    createImage("create layer first\nsave test/image/layer1.ppm");
    assertEquals(1, model.getMultiLayerSize());
    assertEquals(0, model.getTopMostLayer().layerImage().size());
    List<List<Pixel>> expected = null;
    try {
      expected = ImageUtil.createImage("test/image/layer1.ppm");
    } catch (IllegalArgumentException e) {
      //
    }
    assertNull(expected);
    assertEquals(expectedMsg, out.toString());
  }

  // test modelMultiLayers method - save command - png
  @Test
  public void testSaveCommandPNG() {
    assertEquals(0, model.getMultiLayerSize());
    String expectedMsg = "The layer has been successfully created!\n" +
            "The layer image has been successfully loaded!\n" +
            "The layer image has successfully saved and exported!\n";
    createImage("create layer first\nload test/images/imageTest.ppm" +
            "\nsave test/images/testController.png");
    assertEquals(1, model.getMultiLayerSize());
    assertEquals(1, model.getTopMostLayer().layerImage().size());
    List<List<Pixel>> saved = null;
    try {
      saved = ImageUtil.importImage("test/images/testController.png");
    } catch (IOException e) {
      //
    }
    assertArrayEquals(saved.toArray(), model.getTopMostLayer().layerImage().toArray());
    assertEquals(expectedMsg, out.toString());
  }

  // test modelMultiLayers method - save command - jpeg
  @Test
  public void testSaveCommandJPEG() {
    assertEquals(0, model.getMultiLayerSize());
    String expectedMsg = "The layer has been successfully created!\n" +
            "The layer image has been successfully loaded!\n" +
            "The layer image has successfully saved and exported!\n";
    createImage("create layer first\nload test/images/imageTest.ppm" +
            "\nsave test/images/testController.jpeg");
    assertEquals(1, model.getMultiLayerSize());
    assertEquals(1, model.getTopMostLayer().layerImage().size());
    List<List<Pixel>> saved = null;
    try {
      saved = ImageUtil.importImage("test/images/testController.jpeg");
    } catch (IOException e) {
      //
    }
    assertArrayEquals(saved.toArray(), model.getTopMostLayer().layerImage().toArray());
    assertEquals(expectedMsg, out.toString());
  }

  // test modelMultiLayers method - save command - ppm
  @Test
  public void testSaveCommandPPM() {
    assertEquals(0, model.getMultiLayerSize());
    String expectedMsg = "The layer has been successfully created!\n" +
            "The layer image has been successfully loaded!\n" +
            "The layer image has successfully saved and exported!\n";
    createImage("create layer first\nload test/images/imageTest.ppm" +
            "\nsave test/images/testController.ppm");
    assertEquals(1, model.getMultiLayerSize());
    assertEquals(1, model.getTopMostLayer().layerImage().size());
    List<List<Pixel>> saved = null;
    try {
      saved = ImageUtil.createImage("test/images/testController.ppm");
    } catch (IllegalArgumentException e) {
      //
    }
    assertArrayEquals(saved.toArray(), model.getTopMostLayer().layerImage().toArray());
    assertEquals(expectedMsg, out.toString());
  }

  // test modelMultiLayers method - save command - invalid save, the layer is invisible
  @Test
  public void testSaveInvalidInvisibleLayer() {
    assertEquals(0, model.getMultiLayerSize());
    String expectedMsg = "The layer has been successfully created!\n" +
            "The layer image has been successfully loaded!\n" +
            "The layer has been successfully set invisible!\n" +
            "No visible layers to save an image. Try again.\n";
    createImage("create layer first\nload test/images/imageTest.ppm" +
            "\ninvisible first\nsave test/images/testControllerEmpty.ppm");
    assertEquals(1, model.getMultiLayerSize());
    assertTrue(model.isAllInvisibleLayer());
    List<List<Pixel>> saved = null;
    try {
      saved = ImageUtil.createImage("test/images/testControllerEmpty.ppm");
    } catch (IllegalArgumentException e) {
      //
    }
    assertNull(saved);
    assertEquals(expectedMsg, out.toString());
  }

  // test modelMultiLayers method - save command - invalid save, the input is wrong
  @Test
  public void testSaveInvalidInput() {
    assertEquals(0, model.getMultiLayerSize());
    String expectedMsg = "The layer has been successfully created!\n" +
            "The layer image has been successfully loaded!\n" +
            "Invalid inputs to save an image. Try again.\n";
    createImage("create layer first\nload test/images/imageTest.ppm\n" +
            "save layer test/images/first.jpeg");
    assertEquals(1, model.getMultiLayerSize());
    assertEquals(1, model.getTopMostLayer().layerImage().size());
    List<List<Pixel>> saved = null;
    try {
      saved = ImageUtil.importImage("test/images/first.jpeg");
    } catch (IOException e) {
      //
    }
    assertNull(saved);
    assertEquals(expectedMsg, out.toString());
  }

  // test modelMultiLayers method - blur command - invisible layer
  @Test
  public void testBlurInvalidInvisible() {
    assertEquals(0, model.getMultiLayerSize());
    String expectedMsg = "The layer has been successfully created!\n" +
            "The layer image has been successfully loaded!\n" +
            "The layer has been successfully set invisible!\n" +
            "No visible layer to operate. Try again.\n";
    createImage("create layer first\nload test/images/imageTest.ppm\n" +
            "invisible first\nblur");
    assertEquals(1, model.getMultiLayerSize());
    List<List<Pixel>> expected = ImageUtil.createImage("test/images/imageTest.ppm");
    assertArrayEquals(expected.toArray(), model.getLayer(0).layerImage().toArray());
    assertTrue(model.isAllInvisibleLayer());
    assertEquals(expectedMsg, out.toString());
  }

  // test modelMultiLayers method - blur command - no image to operate with
  @Test
  public void testBlurNoImageInLayer() {
    assertEquals(0, model.getMultiLayerSize());
    String expectedMsg = "The layer has been successfully created!\n" +
            "Layer doesn't have image.\n";
    createImage("create layer first\nblur");
    assertEquals(1, model.getMultiLayerSize());
    assertEquals(0, model.getTopMostLayer().layerImage().size());
    assertEquals(expectedMsg, out.toString());
  }

  // test modelMultiLayers method - blur command
  @Test
  public void testBlurCommand() {
    assertEquals(0, model.getMultiLayerSize());
    String expectedMsg = "The layer has been successfully created!\n" +
            "The layer image has been successfully loaded!\n" +
            "The layer image has been successfully updated!\n";
    createImage("create layer first\nload res/Boston.ppm\nblur");
    assertEquals(1, model.getMultiLayerSize());
    assertEquals(517, model.getTopMostLayer().layerImage().size());
    List<List<Pixel>> expected = ImageUtil.createImage("res/BostonBlur.ppm");
    assertArrayEquals(expected.toArray(), model.getTopMostLayer().layerImage().toArray());
    assertEquals(expectedMsg, out.toString());
  }


  // test modelMultiLayers method - grayscale command - invisible layer
  @Test
  public void testGrayscaleInvalidInvisible() {
    assertEquals(0, model.getMultiLayerSize());
    String expectedMsg = "The layer has been successfully created!\n" +
            "The layer image has been successfully loaded!\n" +
            "The layer has been successfully set invisible!\n" +
            "No visible layer to operate. Try again.\n";
    createImage("create layer first\nload test/images/imageTest.ppm\n" +
            "invisible first\ngrayscale");
    assertEquals(1, model.getMultiLayerSize());
    List<List<Pixel>> expected = ImageUtil.createImage("test/images/imageTest.ppm");
    assertArrayEquals(expected.toArray(), model.getLayer(0).layerImage().toArray());
    assertTrue(model.isAllInvisibleLayer());
    assertEquals(expectedMsg, out.toString());
  }

  // test modelMultiLayers method - grayscale command - no image to operate with
  @Test
  public void testGrayscaleNoImageInLayer() {
    assertEquals(0, model.getMultiLayerSize());
    String expectedMsg = "The layer has been successfully created!\n" +
            "Layer doesn't have image.\n";
    createImage("create layer first\ngrayscale");
    assertEquals(1, model.getMultiLayerSize());
    assertEquals(0, model.getTopMostLayer().layerImage().size());
    assertEquals(expectedMsg, out.toString());
  }

  // test modelMultiLayers method - grayscale command
  @Test
  public void testGrayscaleCommand() {
    assertEquals(0, model.getMultiLayerSize());
    String expectedMsg = "The layer has been successfully created!\n" +
            "The layer image has been successfully loaded!\n" +
            "The layer image has been successfully updated!\n";
    createImage("create layer first\nload res/Boston.ppm\ngrayscale");
    assertEquals(1, model.getMultiLayerSize());
    assertEquals(517, model.getTopMostLayer().layerImage().size());
    List<List<Pixel>> expected = ImageUtil.createImage("res/BostonGrayscale.ppm");
    assertArrayEquals(expected.toArray(), model.getTopMostLayer().layerImage().toArray());
    assertEquals(expectedMsg, out.toString());
  }

  // test modelMultiLayers method - sharpen command - invisible layer
  @Test
  public void testSharpenInvalidInvisible() {
    assertEquals(0, model.getMultiLayerSize());
    String expectedMsg = "The layer has been successfully created!\n" +
            "The layer image has been successfully loaded!\n" +
            "The layer has been successfully set invisible!\n" +
            "No visible layer to operate. Try again.\n";
    createImage("create layer first\nload test/images/imageTest.ppm\n" +
            "invisible first\nsharpen");
    assertEquals(1, model.getMultiLayerSize());
    List<List<Pixel>> expected = ImageUtil.createImage("test/images/imageTest.ppm");
    assertArrayEquals(expected.toArray(), model.getLayer(0).layerImage().toArray());
    assertTrue(model.isAllInvisibleLayer());
    assertEquals(expectedMsg, out.toString());
  }

  // test modelMultiLayers method - sharpen command - no image to operate with
  @Test
  public void testSharpenNoImageInLayer() {
    assertEquals(0, model.getMultiLayerSize());
    String expectedMsg = "The layer has been successfully created!\n" +
            "Layer doesn't have image.\n";
    createImage("create layer first\nsharpen");
    assertEquals(1, model.getMultiLayerSize());
    assertEquals(0, model.getTopMostLayer().layerImage().size());
    assertEquals(expectedMsg, out.toString());
  }

  // test modelMultiLayers method - sharpen command
  @Test
  public void testSharpenCommand() {
    assertEquals(0, model.getMultiLayerSize());
    String expectedMsg = "The layer has been successfully created!\n" +
            "The layer image has been successfully loaded!\n" +
            "The layer image has been successfully updated!\n";
    createImage("create layer first\nload res/Boston.ppm\nsharpen");
    assertEquals(1, model.getMultiLayerSize());
    assertEquals(517, model.getTopMostLayer().layerImage().size());
    List<List<Pixel>> expected = ImageUtil.createImage("res/BostonSharpen.ppm");
    assertArrayEquals(expected.toArray(), model.getTopMostLayer().layerImage().toArray());
    assertEquals(expectedMsg, out.toString());
  }

  // test modelMultiLayers method - sepia command - invisible layer
  @Test
  public void testSepiaInvalidInvisible() {
    assertEquals(0, model.getMultiLayerSize());
    String expectedMsg = "The layer has been successfully created!\n" +
            "The layer image has been successfully loaded!\n" +
            "The layer has been successfully set invisible!\n" +
            "No visible layer to operate. Try again.\n";
    createImage("create layer first\nload test/images/imageTest.ppm\n" +
            "invisible first\nsepia");
    assertEquals(1, model.getMultiLayerSize());
    List<List<Pixel>> expected = ImageUtil.createImage("test/images/imageTest.ppm");
    assertArrayEquals(expected.toArray(), model.getLayer(0).layerImage().toArray());
    assertTrue(model.isAllInvisibleLayer());
    assertEquals(expectedMsg, out.toString());
  }

  // test modelMultiLayers method - sepia command - no image to operate with
  @Test
  public void testSepiaNoImageInLayer() {
    assertEquals(0, model.getMultiLayerSize());
    String expectedMsg = "The layer has been successfully created!\n" +
            "Layer doesn't have image.\n";
    createImage("create layer first\nsepia");
    assertEquals(1, model.getMultiLayerSize());
    assertEquals(0, model.getTopMostLayer().layerImage().size());
    assertEquals(expectedMsg, out.toString());
  }

  // test modelMultiLayers method - sepia command
  @Test
  public void testSepiaCommand() {
    assertEquals(0, model.getMultiLayerSize());
    String expectedMsg = "The layer has been successfully created!\n" +
            "The layer image has been successfully loaded!\n" +
            "The layer image has been successfully updated!\n";
    createImage("create layer first\nload res/Boston.ppm\nsepia");
    assertEquals(1, model.getMultiLayerSize());
    assertEquals(517, model.getTopMostLayer().layerImage().size());
    List<List<Pixel>> expected = ImageUtil.createImage("res/BostonSepia.ppm");
    assertArrayEquals(expected.toArray(), model.getTopMostLayer().layerImage().toArray());
    assertEquals(expectedMsg, out.toString());
  }

  // test modelMultiLayers method - invisible command - invalid inputs
  @Test
  public void testInvisibleCommandInvalidInputs() {
    assertEquals(0, model.getMultiLayerSize());
    String expectedMsg = "The layer has been successfully created!\n" +
            "Either invalid inputs or no selected layer exists. Try again.\n";
    createImage("create layer first\ninvisible first second");
    assertEquals(1, model.getMultiLayerSize());
    assertFalse(model.isAllInvisibleLayer());
    assertEquals(expectedMsg, out.toString());
  }

  // test modelMultiLayers method - invisible command - no such layer exists
  @Test
  public void testInvisibleCommandNoLayerExists() {
    assertEquals(0, model.getMultiLayerSize());
    String expectedMsg = "The layer has been successfully created!\n" +
            "Either invalid inputs or no selected layer exists. Try again.\n";
    createImage("create layer first\ninvisible second");
    assertEquals(1, model.getMultiLayerSize());
    assertFalse(model.isAllInvisibleLayer());
    assertEquals(expectedMsg, out.toString());
  }

  // test modelMultiLayers method - invisible command
  @Test
  public void testInvisibleCommand() {
    assertEquals(0, model.getMultiLayerSize());
    String expectedMsg = "The layer has been successfully created!\n" +
            "The layer has been successfully set invisible!\n";
    createImage("create layer first\ninvisible first");
    assertEquals(1, model.getMultiLayerSize());
    assertTrue(model.isAllInvisibleLayer());
    assertEquals(expectedMsg, out.toString());
  }

  // test modelMultiLayers method - visible command - invalid inputs
  @Test
  public void testVisibleCommandInvalidInputs() {
    assertEquals(0, model.getMultiLayerSize());
    String expectedMsg = "The layer has been successfully created!\n" +
            "The layer has been successfully set invisible!\n" +
            "Either invalid inputs or no selected layer exists. Try again.\n";
    createImage("create layer first\ninvisible first\nvisible first second");
    assertEquals(1, model.getMultiLayerSize());
    assertTrue(model.isAllInvisibleLayer());
    assertEquals(expectedMsg, out.toString());
  }

  // test modelMultiLayers method - visible command - no such layer exists
  @Test
  public void testVisibleCommandNoLayerExists() {
    assertEquals(0, model.getMultiLayerSize());
    String expectedMsg = "The layer has been successfully created!\n" +
            "The layer has been successfully set invisible!\n" +
            "Either invalid inputs or no selected layer exists. Try again.\n";
    createImage("create layer first\ninvisible first\nvisible second");
    assertEquals(1, model.getMultiLayerSize());
    assertTrue(model.isAllInvisibleLayer());
    assertEquals(expectedMsg, out.toString());
  }

  // test modelMultiLayers method - visible command
  @Test
  public void testVisibleCommand() {
    assertEquals(0, model.getMultiLayerSize());
    String expectedMsg = "The layer has been successfully created!\n" +
            "The layer has been successfully set visible!\n";
    createImage("create layer first\nvisible first");
    assertEquals(1, model.getMultiLayerSize());
    assertFalse(model.isAllInvisibleLayer());
    assertEquals(expectedMsg, out.toString());
  }

  // test modelMultiLayers method - save_all command - empty script
  @Test
  public void testSaveAllEmptyScript() {
    assertEquals(0, model.getMultiLayerSize());
    String expectedMsg = "The layer has been successfully created!\n" +
            "Invalid script inputs. Try again.\n";
    createImage("create layer first\nsave_all test/images/scriptEmpty.txt " +
            "test/images/empty.png");
    assertEquals(1, model.getMultiLayerSize());
    assertEquals(expectedMsg, out.toString());
  }

  // test modelMultiLayers method - save_all command - invalid input
  @Test
  public void testSaveAllInvalidInput() {
    assertEquals(0, model.getMultiLayerSize());
    String expectedMsg = "Invalid input to save multi layered image. Try again.\n";
    createImage("save_all test/images/scriptEmpty.txt test/images/empty.png");
    assertEquals(0, model.getMultiLayerSize());
    assertEquals(expectedMsg, out.toString());
  }

  // test modelMultiLayers method - save_all command - empty result
  @Test
  public void testSaveAllEmptyResult() {
    assertEquals(0, model.getMultiLayerSize());
    String expectedMsg = "";
    createImage("save_all test/images/scriptEmpty.txt");
    assertEquals(0, model.getMultiLayerSize());
    assertEquals(expectedMsg, out.toString());
  }

  // test modelMultiLayers method - save_all command - invalid inputs
  @Test
  public void testSaveAllEmptyResultInvalid() {
    assertEquals(0, model.getMultiLayerSize());
    String expectedMsg = "invalid inputs\n";
    createImage("save_all res/script.txt");
    assertEquals(0, model.getMultiLayerSize());
    assertEquals(expectedMsg, out.toString());
  }

  // test save all
  @Test
  public void testSuccessfulSaveAll() {
    assertEquals(0, model.getMultiLayerSize());
    String expectedMsg =
            "The layer has been successfully created!\n" +
                    "The layer has been successfully created!\n" +
                    "The layer has been successfully created!\n" +
                    "The layer image has been successfully loaded!\n" +
                    "The layer has been set to current.\n" +
                    "The layer image has been successfully loaded!\n" +
                    "The layer has been set to current.\n" +
                    "The layer image has been successfully loaded!\n" +
                    "The layer image has successfully saved and exported!\n" +
                    "The layer image has successfully saved and exported!\n" +
                    "The layer image has successfully saved and exported!\n";
    String input = "create layer first\n" +
            "create layer second\n" +
            "create layer third\n" +
            "load res/Boston1.jpeg\n" +
            "current second\n" +
            "load test/images/imageTestJPEG.jpeg\n" +
            "current third\n" +
            "load res/Boston2.png\n" +
            "save_all res/input.txt test/images/pic1.png " +
            "test/images/pic2.png test/images/pic3.jpeg";
    createImage(input);
    assertEquals(3, model.getMultiLayerSize());
    try {
      assertArrayEquals(ImageUtil.importImage("res/Boston1.jpeg").toArray(),
              model.getLayer(0).layerImage().toArray());
      assertArrayEquals(ImageUtil.importImage("res/Boston2.png").toArray(),
              model.getLayer(1).layerImage().toArray());
      assertArrayEquals(ImageUtil.importImage("test/images/imageTestJPEG.jpeg").toArray(),
              model.getLayer(2).layerImage().toArray());
      assertArrayEquals(ImageUtil.importImage("res/Boston1.jpeg").toArray(),
              ImageUtil.importImage("test/images/pic1.png").toArray());
      assertArrayEquals(ImageUtil.importImage("res/Boston2.png").toArray(),
              ImageUtil.importImage("test/images/pic2.png").toArray());
      assertArrayEquals(ImageUtil.importImage("test/images/imageTestJPEG.jpeg").toArray(),
              ImageUtil.importImage("test/images/pic3.jpeg").toArray());
    } catch (IOException e) {
      //
    }
    assertEquals(expectedMsg, out.toString());
    // input.txt changes the order of the images
  }

  // test quit
  @Test
  public void testQuit() {
    assertEquals(0, model.getMultiLayerSize());
    String expectedMsg = "The layer has been successfully created!\n" +
            "The layer image has been successfully loaded!\n";
    createImage("create layer first\nload test/images/imageTest.ppm\nquit\n" +
            "create layer second\nload test/images/imageTest.ppm");
    assertEquals(expectedMsg, out.toString());
    assertEquals(1, model.getMultiLayerSize());
  }

  // test invalid removing layer
  @Test
  public void testInvalidRemovingLayer() {
    assertEquals(0, model.getMultiLayerSize());
    String expectedMsg = "Invalid to remove layer. Try again.\n";
    createImage("remove layer unknown_first");
    assertEquals(0, model.getMultiLayerSize());
    assertEquals(expectedMsg, out.toString());
  }

  @Test
  public void testRemovingLayer() {
    assertEquals(0, model.getMultiLayerSize());
    String expectedMsg = "The layer has been successfully created!\n" +
            "The layer has been successfully created!\n" +
            "Removed layer successfully!\n";
    String input = "create layer first\ncreate layer second\nremove layer first\n";
    createImage(input);
    assertEquals(1, model.getMultiLayerSize());
    assertTrue(model.getTopMostLayer().isCurrentLayer("second"));
    assertEquals(expectedMsg, out.toString());
  }
}
