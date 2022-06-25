package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Layer} represents a image with constant dimension. The layer is used to represent
 * image in the program like Photoshop, where it is based on layers of images.
 */
public class Layer {
  private List<List<Pixel>> image;
  private boolean isVisible;
  private final String name;

  /**
   * Constructs a layer with given image in sequence of pixel form.
   *
   * @param name the user-defined name for the layer.
   * @throws IllegalArgumentException if name is invalid
   */
  public Layer(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Invalid name for a layer");
    }
    this.image = new ArrayList<List<Pixel>>();
    this.isVisible = true;
    this.name = name;
  }

  /**
   * Exports this layer image and save it.
   *
   * @param format   the provided image format for saving image.
   * @param fileName the provided image file name.
   * @throws IllegalStateException if transmission fails
   */
  public void exportLayer(String format, String fileName) throws IllegalStateException {
    if (fileName == null || format == null) {
      throw new IllegalArgumentException("Invalid null arguments");
    }
    try {
      if (format.equals("ppm")) {
        ImageUtil.writePPM(image, fileName);
      } else {
        ImageUtil.exportImage(image, fileName, format);
      }
    } catch (IOException e) {
      throw new IllegalStateException("Transmits fails.");
    }
  }

  /**
   * Sets this layer's image with image file.
   *
   * @param fileName the name of the file to load.
   * @param format   the format of the file.
   * @throws IllegalStateException    if transmission fails
   * @throws IllegalArgumentException if any argument is null
   */
  public void setLayerImage(String fileName, String format)
          throws IllegalStateException, IllegalArgumentException {
    if (fileName == null || format == null) {
      throw new IllegalArgumentException("Invalid null arguments");
    }
    try {
      if (format.equals("ppm")) {
        this.image = ImageUtil.createImage(fileName);
      } else {
        this.image = ImageUtil.importImage(fileName);
      }
    } catch (IOException e) {
      throw new IllegalStateException("Transmits fails.");
    }
  }

  /**
   * Sets this layer's image to the provided image.
   *
   * @param image the provided image in sequence of pixel form.
   * @throws IllegalArgumentException if the provided image is invalid.
   */
  public void setLayerImageToAnotherImage(List<List<Pixel>> image) throws IllegalArgumentException {
    ColorTransformation.validate(image); // check the image
    this.image = image;
  }

  /**
   * Determines whether the top most layer is visible.
   *
   * @return whether this layer is visible.
   */
  public boolean isTopMostVisible() {
    return this.isVisible;
  }

  /**
   * Determines the image of this layer.
   *
   * @return the image of this layer in sequence of pixel form.
   */
  public List<List<Pixel>> layerImage() {
    List<List<Pixel>> imageLayer = new ArrayList<List<Pixel>>();
    for (int i = 0; i < this.image.size(); i++) {
      List<Pixel> row = new ArrayList<Pixel>();
      for (int j = 0; j < this.image.get(i).size(); j++) {
        Pixel current = this.image.get(i).get(j);
        ColorPixel currentColor = current.getColor();
        PositionPixel pos = new PositionPixel(i, j);
        ColorPixel color = new ColorPixel(currentColor.getRedChannel(),
                currentColor.getGreenChannel(), currentColor.getBlueChannel());
        Pixel pixelNew = new Pixel(pos, color);
        row.add(pixelNew);
      }
      imageLayer.add(row);
    }
    return imageLayer;
  }

  /**
   * Determines whether this layer's name is equal to given name.
   *
   * @param layerName the given layer name.
   * @return whether this layer's name is equal to given name.
   * @throws IllegalArgumentException if the given is invalid
   */
  public boolean isCurrentLayer(String layerName) {
    if (layerName == null || layerName.length() < 1) {
      throw new IllegalArgumentException("Wrong name");
    }
    return this.name.equals(layerName);
  }

  /**
   * Sets this layer to be invisible.
   */
  public void setLayerInvisible() {
    this.isVisible = false;
  }

  /**
   * Sets this layer to be visible.
   */
  public void setLayerVisible() {
    this.isVisible = true;
  }

  /**
   * Produces the name of the layer.
   * @return the name of the layer.
   */
  public String getName() {
    return this.name;
  }

}