package model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * This class contains utility methods to create an image (import) from PPM, JPEG, and PNG files,
 * write (export) a new PPM, JPEG, and PNG files.
 * It imports and exports the file and provides different operations for the image,
 * such as filtering or transforming the color.
 */
public class ImageUtil {

  /**
   * Writes a ppm file with given image object and a file directory/name.
   *
   * @param image    the sequence of pixel that represents an image.
   * @param filename a file name of the ppm file or a list of list of pixels is invalid
   * @throws IllegalArgumentException if an image is invalid or a filename is null or empty
   */
  public static void writePPM(List<List<Pixel>> image, String filename)
          throws IllegalArgumentException {
    checkForArgumentException((filename == null || filename.length() < 1),
            "Invalid filename");
    ColorTransformation.validate(image); // to check that image is valid
    File file = new File(filename);
    try {
      FileOutputStream res = new FileOutputStream(file);
      OutputStreamWriter w = new OutputStreamWriter(res);
      BufferedWriter writer = new BufferedWriter(w);
      writer.write("P3");
      writer.newLine();
      int height = image.size();
      int width = image.get(0).size();
      writer.write(width + " " + height);
      writer.newLine();
      writer.write("255");
      writer.newLine();
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          ColorPixel p = image.get(i).get(j).getColor();
          writer.write(p.getRedChannel() + " ");
          writer.write(p.getGreenChannel() + " ");
          writer.write(p.getBlueChannel() + " ");
          if (j < width - 1) {
            writer.write(" ");
          }
        }
        writer.newLine();
      }
      writer.close();
    } catch (IOException e) {
      System.out.print("Cannot create a file.");
    }
  }

  /**
   * Returns a sequence of pixel from the given PPM file.
   *
   * @param fileName the given PPM file's name.
   * @return a sequence of pixel that represents an image.
   * @throws IllegalArgumentException if the given file is an invalid ppm file or filename is null
   *                                  or empty or width or height is invalid or the file cannot
   *                                  be found or max color value or color values are invalid.
   */
  public static List<List<Pixel>> createImage(String fileName)
          throws IllegalArgumentException {
    checkForArgumentException((fileName == null || fileName.length() < 1),
            "Invalid filename");
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream(fileName));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File " + fileName + " not found!");
    }
    StringBuilder builder = new StringBuilder();
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }
    sc = new Scanner(builder.toString());
    String token;
    token = sc.next();
    checkForArgumentException(!token.equals("P3"),
            "Invalid PPM file: plain RAW file should begin with P3");
    int width = sc.nextInt();
    int height = sc.nextInt();
    checkForArgumentException((width <= 0 || height <= 0), "Width and height should be positive");
    int maxValue = sc.nextInt();
    checkForArgumentException((maxValue != 255), "The maximum value should be 255.");
    List<List<Pixel>> image = new ArrayList<List<Pixel>>();
    for (int i = 0; i < height; i++) {
      List<Pixel> row = new ArrayList<Pixel>();
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        if (r > maxValue || g > maxValue || b > maxValue) {
          throw new IllegalArgumentException("The rgb value should be less or equal"
                  + "than the max value provided in ppm file.");
        }
        Pixel pixel = new Pixel(new PositionPixel(i, j), new ColorPixel(r, g, b));
        row.add(pixel);
      }
      image.add(row);
    }
    return image;
  }

  /**
   * Throws argument if given argument is true.
   *
   * @param argument the given argument that will cause argument exception.
   * @param message  the given message to display as an error message.
   * @throws IllegalArgumentException if the given argument is true.
   */
  private static void checkForArgumentException(boolean argument, String message)
          throws IllegalArgumentException {
    if (argument) {
      throw new IllegalArgumentException(message);
    }
  }

  /**
   * Imports an image (as a sequence of pixels) from the given filename (for png and jpeg).
   *
   * @param fileName the name of the file to take image from
   * @return an image (as a 2D list of pixels)
   * @throws IOException              if reading fails
   * @throws IllegalArgumentException if a filename is illegal, if read image is null
   */
  public static List<List<Pixel>> importImage(String fileName) throws IOException,
          IllegalArgumentException {
    checkForArgumentException((fileName == null || fileName.length() < 1),
            "Invalid filename");
    BufferedImage img = ImageIO.read(new File(fileName));
    checkForArgumentException((img == null),
            "Invalid file - cannot be read");
    List<List<Pixel>> result = new ArrayList<List<Pixel>>();
    for (int r = 0; r < img.getHeight(); r++) {
      List<Pixel> row = new ArrayList<Pixel>();
      for (int c = 0; c < img.getWidth(); c++) {
        Color color = new Color(img.getRGB(c, r));
        ColorPixel colorForPixel =
                new ColorPixel(color.getRed(), color.getGreen(), color.getBlue());
        Pixel pixel = new Pixel(new PositionPixel(r, c), colorForPixel);
        row.add(pixel);
      }
      result.add(row);
    }
    return result;
  }

  /**
   * Exports an image to a new file of a given format.
   *
   * @param image    an image to save and export
   * @param filename a name of a file where to export to
   * @param format   a format of an image (png or jpeg)
   * @throws IOException              if an error occurs with output stream or writing an image
   * @throws IllegalArgumentException if any argument supplied is invalid
   */
  public static void exportImage(List<List<Pixel>> image, String filename, String format)
          throws IOException, IllegalArgumentException {
    checkForArgumentException((format == null || format.length() < 3),
            "Invalid format");
    checkForArgumentException((filename == null || filename.length() < 1),
            "Invalid filename");
    checkForArgumentException((!(format.equals("PNG") || format.equals("png")
                    || format.equals("jpeg") || format.equals("JPEG"))),
            "Invalid image format");
    BufferedImage img = ImageUtil.produceImage(image);
    OutputStream output = new FileOutputStream(filename);
    ImageIO.write(img, format, output);
    output.close();
  }

  /**
   * Produces a buffered image with provided layer's image.
   * @param image the image of provided layer in sequence of pixel form.
   * @return the buffered image of layer image.
   * @throws IllegalArgumentException if the provided layer image is null.
   */
  public static BufferedImage produceImage(List<List<Pixel>> image)
          throws IllegalArgumentException {
    ColorTransformation.validate(image);
    int width = image.get(0).size();
    int height = image.size();
    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    for (int r = 0; r < height; r++) {
      for (int c = 0; c < width; c++) {
        Pixel pixel = image.get(r).get(c);
        java.awt.Color color = new Color(pixel.getColor().getRedChannel(),
                pixel.getColor().getGreenChannel(), pixel.getColor().getBlueChannel());
        img.setRGB(c, r, color.getRGB());
      }
    }
    return img;
  }
}

