import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JFileChooser;

import view.ISwingViewer;

/**
 * This class is creating for testing purposes only. This is a mock class that imitates the
 * behaviour of a view but does not actually produce anything for the view. Needed to see whether
 * the controller calls the right things.
 */
public class MockView implements ISwingViewer {
  @Override
  public void setListeners(ActionListener listener) throws IllegalArgumentException {
    if (listener == null) {
      throw new IllegalArgumentException("Null action listener");
    }
  }

  @Override
  public void setLayerNameOutput(String layerName) throws IllegalArgumentException {
    if (layerName == null) {
      throw new IllegalArgumentException("Null name of a layer");
    }
  }

  @Override
  public void setNewFileName(String fileName) throws IllegalArgumentException {
    if (fileName == null) {
      throw new IllegalArgumentException("Null file name");
    }
  }

  @Override
  public void setLayerImage(BufferedImage image) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("Null image");
    }
  }

  @Override
  public void setMultipleFileNames(String fileNames) throws IllegalArgumentException {
    if (fileNames == null) {
      throw new IllegalArgumentException("Null file name");
    }
  }

  @Override
  public void setLayersNames(List<String> layersNames) throws IllegalArgumentException {
    if (layersNames == null) {
      throw new IllegalArgumentException("Null list");
    }
  }

  @Override
  public void setBoardInputs(String boardInputs) throws IllegalArgumentException {
    if (boardInputs == null) {
      throw new IllegalArgumentException("Null inputs");
    }
  }

  @Override
  public void sendMessage(String message) throws IllegalArgumentException {
    if (message == null) {
      throw new IllegalArgumentException("Null message");
    }
  }

  @Override
  public int getRetValue(JFileChooser fchooser) throws IllegalArgumentException {
    if (fchooser == null) {
      throw new IllegalArgumentException("Null argument");
    }
    return 0;
  }

  @Override
  public void setSavingFileName(String saveFileName) throws IllegalArgumentException {
    if (saveFileName == null) {
      throw new IllegalArgumentException("Null file name");
    }
  }

  @Override
  public String askInfo(String ins) throws IllegalArgumentException {
    if (ins == null) {
      throw new IllegalArgumentException("Null instruction");
    }
    if (ins.equals("Please enter the name of the layer")) {
      return "layer1";
    } else if (ins.equals("Please enter square tile size, width, length, " +
            "2 RGB values in order, all each with a single space")) {
      return "1 2 1 255 153 203 0 255 255";
    } else if (ins.equals("Please enter "
            + "the name of file name to be saved(including file format)")) {
      return "test/images/sampleImage.png";
    } else if (ins.equals("Please first enter "
            + "the name of the script txt file and then image file names to be saved, each"
            + "by space!")) {
      return "test/images/scriptEmpty.txt test/images/sampleImage1.png";
    } else {
      return "";
    }
  }

  @Override
  public JFileChooser fileChooser() {
    return null;
  }
}
