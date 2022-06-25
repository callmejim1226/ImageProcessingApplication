import org.junit.Test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.CheckerBoardImage;
import model.ColorPixel;
import model.ImageUtil;
import model.Pixel;
import model.PositionPixel;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * This class represents a test class for utility class {@code ImageUtil} to ensure
 * methods work correctly.
 */
public class ImageUtilTest {

  // test writePPM method - null image
  @Test(expected = IllegalArgumentException.class)
  public void testWritePPMNullImage() {
    ImageUtil.writePPM(null, "ood.ppm");
  }

  // test writePPM method - null filename
  @Test(expected = IllegalArgumentException.class)
  public void testWritePPMNullFilename() {
    ImageUtil.writePPM(new CheckerBoardImage(1, 1, 1,
            Arrays.asList(new ColorPixel(0, 0, 0),
                    new ColorPixel(255, 255, 255))).createImageProgram(), null);
  }

  // test createImage - empty filename
  @Test(expected = IllegalArgumentException.class)
  public void testWritePPMEmptyFilename() {
    ImageUtil.writePPM(new CheckerBoardImage(1, 1, 1,
            Arrays.asList(new ColorPixel(0, 0, 0),
                    new ColorPixel(255, 255, 255))).createImageProgram(), "");
  }

  // test writePPM method - null filename and image
  @Test(expected = IllegalArgumentException.class)
  public void testWritePPMNullImageFilename() {
    ImageUtil.writePPM(null, null);
  }

  // test writePPM method - empty image
  @Test(expected = IllegalArgumentException.class)
  public void testWritePPMEmptyImage() {
    List<List<Pixel>> empty = new ArrayList<List<Pixel>>();
    ImageUtil.writePPM(empty, "help.ppm");
  }

  // test writePPM method - a 2D list contains an empty list
  @Test(expected = IllegalArgumentException.class)
  public void testWritePPMEmptyListImage() {
    List<List<Pixel>> listWithEmpty = new ArrayList<List<Pixel>>();
    listWithEmpty.add(new ArrayList<>());
    ImageUtil.writePPM(listWithEmpty, "cs3500.ppm");
  }

  // test writePPM method - 2D list contains a null
  @Test(expected = IllegalArgumentException.class)
  public void testWritePPMImageContainsNull() {
    List<List<Pixel>> listWithNull = new ArrayList<List<Pixel>>();
    listWithNull.add(null);
    ImageUtil.writePPM(listWithNull, "cs.ppm");
  }

  // test writePPM - a list contains a null
  @Test(expected = IllegalArgumentException.class)
  public void testImageListContainsNull() {
    List<List<Pixel>> listWithNull = new ArrayList<List<Pixel>>();
    listWithNull.add(Arrays.asList(new Pixel(new PositionPixel(0, 0),
            new ColorPixel(0, 0, 0)), null));
    ImageUtil.writePPM(listWithNull, "sleep.ppm");
  }

  // test writePPM method - image -> ppm
  @Test
  public void testWritePPM() {
    List<List<Pixel>> image = new ArrayList<List<Pixel>>();
    image.add(Arrays.asList(new Pixel(new PositionPixel(0, 0),
            new ColorPixel(0, 0, 0))));
    ImageUtil.writePPM(image, "test/images/imageTest.ppm");
    assertArrayEquals(image.toArray(),
            ImageUtil.createImage("test/images/imageTest.ppm").toArray());
  }

  // test createImage method -ppm->image
  @Test
  public void testCreateImage() {
    List<List<Pixel>> image = new ArrayList<List<Pixel>>();
    image.add(Arrays.asList(new Pixel(new PositionPixel(0, 0),
            new ColorPixel(0, 0, 0))));
    assertArrayEquals(image.toArray(),
            ImageUtil.createImage("test/images/imageTest.ppm").toArray());
  }

  // test createImage - null filename
  @Test(expected = IllegalArgumentException.class)
  public void testCreateImageNullFilename() {
    ImageUtil.createImage(null);
  }

  // test createImage - empty filename
  @Test(expected = IllegalArgumentException.class)
  public void testCreateImageEmptyFilename() {
    ImageUtil.createImage("");
  }

  // test createImage - file not found
  @Test(expected = IllegalArgumentException.class)
  public void testCreateImageFileNotFound() {
    ImageUtil.createImage("Money.ppm");
  }

  // test createImage - invalid ppm image - no P3
  @Test(expected = IllegalArgumentException.class)
  public void testCreateImageInvalidImageNoP3() {
    ImageUtil.createImage("test/images/invalidImage.ppm");
  }

  // test createImage - invalid max value (should be 255 for ppm)
  @Test(expected = IllegalArgumentException.class)
  public void testCreateImageInvalidMaxValue() {
    ImageUtil.createImage("test/images/imageInvalidMaxValue.ppm");
  }

  // test createImage - invalid green color value
  @Test(expected = IllegalArgumentException.class)
  public void testCreateImageInvalidGreenColorValue() {
    ImageUtil.createImage("test/images/imageInvalidGreenColorValue.ppm");
  }

  // test createImage - invalid red color value
  @Test(expected = IllegalArgumentException.class)
  public void testCreateImageInvalidRedColorValue() {
    ImageUtil.createImage("test/images/imageInvalidRedColorValue.ppm");
  }

  // test createImage - invalid blue color value
  @Test(expected = IllegalArgumentException.class)
  public void testCreateImageInvalidBlueColorValue() {
    ImageUtil.createImage("test/images/imageInvalidBlueColorValue.ppm");
  }

  // test createImage - invalid width of the image
  @Test(expected = IllegalArgumentException.class)
  public void testCreateImageInvalidWidth() {
    ImageUtil.createImage("test/images/imageInvalidWidth.ppm");
  }

  // test createImage - invalid height of the image
  @Test(expected = IllegalArgumentException.class)
  public void testCreateImageInvalidHeight() {
    ImageUtil.createImage("test/images/imageInvalidHeight.ppm");
  }

  // test exportImage method - null image
  @Test(expected = IllegalArgumentException.class)
  public void testExportImageNullImage() throws IOException {
    ImageUtil.exportImage(null, "ood.ppm", "png");
  }

  // test exportImage method - null filename
  @Test(expected = IllegalArgumentException.class)
  public void testExportImageNullFilename() throws IOException {
    ImageUtil.exportImage(new CheckerBoardImage(1, 1, 1,
                    Arrays.asList(new ColorPixel(0, 0, 0),
                            new ColorPixel(255, 255, 255))).createImageProgram(), null,
            "jpeg");
  }

  // test exportImage - empty filename
  @Test(expected = IllegalArgumentException.class)
  public void testExportImageEmptyFilename() throws IOException {
    List<List<Pixel>> image = new CheckerBoardImage(1, 1, 1,
            Arrays.asList(new ColorPixel(0, 0, 0),
                    new ColorPixel(255, 255, 255))).createImageProgram();
    ImageUtil.exportImage(image, "", "PNG");
  }

  // test exportImage - null format
  @Test(expected = IllegalArgumentException.class)
  public void testExportImageNullFormat() throws IOException {
    List<List<Pixel>> image = new CheckerBoardImage(1, 1, 1,
            Arrays.asList(new ColorPixel(0, 0, 0),
                    new ColorPixel(255, 255, 255))).createImageProgram();
    ImageUtil.exportImage(image, "test/OOD.png", null);
  }

  // test exportImage - empty format
  @Test(expected = IllegalArgumentException.class)
  public void testExportImageEmptyFormat() throws IOException {
    List<List<Pixel>> image = new CheckerBoardImage(1, 1, 1,
            Arrays.asList(new ColorPixel(0, 0, 0),
                    new ColorPixel(255, 255, 255))).createImageProgram();
    ImageUtil.exportImage(image, "test/ok.jpeg", "");
  }

  // test exportImage method - null filename and image
  @Test(expected = IllegalArgumentException.class)
  public void testExportImageNullImageFilename() throws IOException {
    ImageUtil.exportImage(null, null, "JPEG");
  }

  // test exportImage method - empty image
  @Test(expected = IllegalArgumentException.class)
  public void testExportImageEmptyImage() throws IOException {
    List<List<Pixel>> empty = new ArrayList<List<Pixel>>();
    ImageUtil.exportImage(empty, "help.png", "PNG");
  }

  // test exportImage method - a 2D list contains an empty list
  @Test(expected = IllegalArgumentException.class)
  public void testExportImageEmptyListImage() throws IOException {
    List<List<Pixel>> listWithEmpty = new ArrayList<List<Pixel>>();
    listWithEmpty.add(new ArrayList<>());
    ImageUtil.exportImage(listWithEmpty, "cs3500.png", "PNG");
  }

  // test exportImage method - 2D list contains a null
  @Test(expected = IllegalArgumentException.class)
  public void testExportImageImageContainsNull() throws IOException {
    List<List<Pixel>> listWithNull = new ArrayList<List<Pixel>>();
    listWithNull.add(null);
    ImageUtil.exportImage(listWithNull, "cs.jpeg", "JPEG");
  }

  // test exportImage - a list contains a null
  @Test(expected = IllegalArgumentException.class)
  public void testExportImageListContainsNull() throws IOException {
    List<List<Pixel>> listWithNull = new ArrayList<List<Pixel>>();
    listWithNull.add(Arrays.asList(new Pixel(new PositionPixel(0, 0),
            new ColorPixel(0, 0, 0)), null));
    ImageUtil.exportImage(listWithNull, "sleep.png", "PNG");
  }

  // test exportImage - invalid short format
  @Test(expected = IllegalArgumentException.class)
  public void testExportImageInvalidShortFormat() throws IOException {
    List<List<Pixel>> image = new CheckerBoardImage(1, 1, 1,
            Arrays.asList(new ColorPixel(0, 0, 0),
                    new ColorPixel(255, 255, 255))).createImageProgram();
    ImageUtil.exportImage(image, "test/images/ok.jpeg", "ok");
  }

  // test exportImage - invalid format
  @Test(expected = IllegalArgumentException.class)
  public void testExportImageInvalidFormat() throws IOException {
    List<List<Pixel>> image = new CheckerBoardImage(1, 1, 1,
            Arrays.asList(new ColorPixel(0, 0, 0),
                    new ColorPixel(255, 255, 255))).createImageProgram();
    ImageUtil.exportImage(image, "test/images/ok.jpeg", "jpegvvv");
  }

  // test exportImage - invalid formats
  @Test(expected = IllegalArgumentException.class)
  public void testExportImageInvalidFormats() throws IOException {
    List<List<Pixel>> image = new CheckerBoardImage(1, 1, 1,
            Arrays.asList(new ColorPixel(0, 0, 0),
                    new ColorPixel(255, 255, 255))).createImageProgram();
    ImageUtil.exportImage(image, "test/images/imageTest2.nope", "hgh");
  }

  // test exportImage method - image (a sequence of pixels) -> jpeg and png formats
  @Test
  public void testExportImage() throws IOException {
    List<List<Pixel>> image = new ArrayList<List<Pixel>>();
    image.add(Arrays.asList(new Pixel(new PositionPixel(0, 0),
            new ColorPixel(0, 0, 0))));
    ImageUtil.exportImage(image, "test/images/imageTestJPEG.jpeg", "jpeg");
    assertArrayEquals(image.toArray(),
            ImageUtil.importImage("test/images/imageTestJPEG.jpeg").toArray());
    ImageUtil.exportImage(image, "test/images/imageTestJPEG1.jpeg", "JPEG");
    assertArrayEquals(image.toArray(),
            ImageUtil.importImage("test/images/imageTestJPEG1.jpeg").toArray());
    ImageUtil.exportImage(image, "test/images/imageTestPNG.png", "png");
    assertArrayEquals(image.toArray(),
            ImageUtil.importImage("test/images/imageTestPNG.png").toArray());
    ImageUtil.exportImage(image, "test/images/imageTestPNG1.png", "PNG");
    assertArrayEquals(image.toArray(),
            ImageUtil.importImage("test/images/imageTestPNG1.png").toArray());
  }

  // test exportImage - file not found
  @Test(expected = IOException.class)
  public void testExportImageNotFoundFile() throws IOException {
    List<List<Pixel>> image = new ArrayList<List<Pixel>>();
    image.add(Arrays.asList(new Pixel(new PositionPixel(0, 0),
            new ColorPixel(0, 0, 0))));
    ImageUtil.exportImage(image, "test/images1/imageTestJPEG.jpeg", "jpeg");
  }

  // test importImage method - jpeg and png -> image
  @Test
  public void testImportImage() throws IOException {
    List<List<Pixel>> image = new ArrayList<List<Pixel>>();
    image.add(Arrays.asList(new Pixel(new PositionPixel(0, 0),
            new ColorPixel(0, 0, 0))));
    assertArrayEquals(image.toArray(),
            ImageUtil.importImage("test/images/imageTestJPEG.jpeg").toArray());
    assertArrayEquals(image.toArray(),
            ImageUtil.importImage("test/images/imageTestPNG.png").toArray());
    assertArrayEquals(image.toArray(),
            ImageUtil.importImage("test/images/imageTestPNG1.png").toArray());
    assertArrayEquals(image.toArray(),
            ImageUtil.importImage("test/images/imageTestJPEG1.jpeg").toArray());
  }

  // test importImage - null filename
  @Test(expected = IllegalArgumentException.class)
  public void testImportImageNullFilename() throws IOException {
    ImageUtil.importImage(null);
  }

  // test importImage - empty filename
  @Test(expected = IllegalArgumentException.class)
  public void testImportImageEmptyFilename() throws IOException {
    ImageUtil.importImage("");
  }

  // test importImage - file not found - png format
  @Test(expected = IOException.class)
  public void testImportImageFileNotFoundPNG() throws IOException {
    ImageUtil.importImage("Money.png");
  }

  // test importImage - file not found - jpeg format
  @Test(expected = IOException.class)
  public void testImportImageFileNotFoundJPEG() throws IOException {
    ImageUtil.importImage("Money.jpeg");
  }

  // test importImage -image that was read is null
  @Test(expected = IllegalArgumentException.class)
  public void testImportImageReadNull() throws IOException {
    ImageUtil.importImage("test/images/imageTest.ppm");
  }

  // test produceImage method - null image
  @Test(expected = IllegalArgumentException.class)
  public void testProduceImageNullImage() {
    ImageUtil.produceImage(null);
  }

  // test produceImage method - empty image
  @Test(expected = IllegalArgumentException.class)
  public void testProduceImageEmptyImage() {
    List<List<Pixel>> empty = new ArrayList<List<Pixel>>();
    ImageUtil.produceImage(empty);
  }

  // test produceImage method - a 2D list contains an empty list
  @Test(expected = IllegalArgumentException.class)
  public void testProduceImageEmptyList() {
    List<List<Pixel>> listWithEmpty = new ArrayList<List<Pixel>>();
    listWithEmpty.add(new ArrayList<>());
    ImageUtil.produceImage(listWithEmpty);
  }

  // test produceImage method - a 2D list contains a null
  @Test(expected = IllegalArgumentException.class)
  public void testProduceImageNullList() {
    List<List<Pixel>> listWithEmpty = new ArrayList<List<Pixel>>();
    listWithEmpty.add(new ArrayList<>());
    ImageUtil.produceImage(listWithEmpty);
  }

  // test produceImage method - a list contains a null
  @Test(expected = IllegalArgumentException.class)
  public void testProduceImageListWithNull() {
    List<List<Pixel>> listWithNull = new ArrayList<List<Pixel>>();
    listWithNull.add(Arrays.asList(new Pixel(new PositionPixel(0, 0),
            new ColorPixel(0, 0, 0)), null));
    ImageUtil.produceImage(listWithNull);
  }

  // test produceImage method - valid
  @Test
  public void testProduceImage() {
    List<List<Pixel>> image = new ArrayList<List<Pixel>>();
    image.add(Arrays.asList(new Pixel(new PositionPixel(0, 0),
            new ColorPixel(0, 0, 0))));
    BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    Color color = new Color(0, 0, 0);
    img.setRGB(0, 0, color.getRGB());
    assertEquals(img.getHeight(), ImageUtil.produceImage(image).getHeight());
    assertEquals(img.getWidth(), ImageUtil.produceImage(image).getWidth());
    assertEquals(img.getRGB(0, 0), ImageUtil.produceImage(image).getRGB(0, 0));
  }
}