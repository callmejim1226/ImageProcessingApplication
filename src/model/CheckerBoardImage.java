package model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents an image of a checkerboard pattern. It has a width, length, tile size,
 * and a list of 2 possible colors.
 */
public class CheckerBoardImage implements ImageModel {
  private final int tileSize;
  private final int width;
  private final int length;
  private final List<ColorPixel> possibleColorPixels;

  /**
   * Constructs the checkerboard image programmatically with given each square tile size,
   * num of tiles, and possible colors to use.
   *
   * @param tileSize       the size of each square tile in checkerboard.
   * @param width          the width of the checkerboard.
   * @param length         the length of the checkerboard.
   * @param possibleColorPixels the two possible colors in checkerboard.
   * @throws IllegalArgumentException if the size of the tile < 1 or if the size of
   *                                  possible colors is not 2 or width < 1 or length < 1
   *                                  or the list of colors is null or one of the colors is null.
   */
  public CheckerBoardImage(int tileSize, int width, int length,
                           List<ColorPixel> possibleColorPixels) {
    if (tileSize < 1 || width < 1 || possibleColorPixels == null || possibleColorPixels.size() != 2
            || length < 1 || possibleColorPixels.get(0) == null
            || possibleColorPixels.get(1) == null) {
      throw new IllegalArgumentException("Invalid arguments to create check board image.");
    }
    this.tileSize = tileSize;
    this.width = width;
    this.length = length;
    this.possibleColorPixels = possibleColorPixels;
  }

  /**
   * Creates the check board image programmatically with given each square tile size, num of tiles,
   * and possible colors to use.
   *
   * @return the sequence of pixels that represents a check board image.
   */
  public List<List<Pixel>> createImageProgram() {
    boolean isWidthEven = false;
    if (width % 2 == 0) {
      isWidthEven = true;
    }
    List<List<Pixel>> checkBoard = new ArrayList<List<Pixel>>();
    int colorIndex = 0;
    for (int i = 1; i <= length * tileSize; i++) {
      List<Pixel> row = new ArrayList<Pixel>();
      colorIndex = changeColor(colorIndex, tileSize, i, true);
      for (int j = 1; j <= width * tileSize; j++) {
        colorIndex = changeColor(colorIndex, tileSize, j, isWidthEven);
        row.add(new Pixel(new PositionPixel(i, j), possibleColorPixels.get(colorIndex)));
      }
      checkBoard.add(row);
    }
    return checkBoard;
  }

  /**
   * Changes the given color index when appropriate to either 0 or 1.
   *
   * @param index       the given number that represents a color index from list of colors.
   * @param tileSize    the size of square tile in checker board.
   * @param pos         the position of the pixel in the checkerboard.
   * @param isWidthEven whether the width of checker board is even.
   * @return the color index from list of colors, either 0 or 1.
   */
  private int changeColor(int index, int tileSize, int pos, boolean isWidthEven) {
    if (isWidthEven) {
      if ((pos - 1) % tileSize == 0) {
        if (index == 1) {
          return 0;
        } else {
          return 1;
        }
      }
    } else {
      if ((pos - 1) % tileSize == 0 && pos - 1 != 0) {
        if (index == 1) {
          return 0;
        } else {
          return 1;
        }
      }
    }
    return index;
  }
}
