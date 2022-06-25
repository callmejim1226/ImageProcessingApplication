package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import model.ImageUtil;
import model.Layer;
import model.MultiLayer;
import view.ISwingViewer;

/**
 * This class represents a controller for GUI. It calls the multilayer image model and
 * view for swing, performs actions after certain command was used.
 */
public class SwingController extends SimpleImageController implements ActionListener {
  private ISwingViewer swingView;
  private String selectedLayerName;
  private String currentOperation;
  private String loadedFileName;
  private String[] commandOperation;
  private String[] commandBoard;
  private String[] commandSaveFile;
  private String[] commandSaveAll;

  /**
   * Constructs the controller for Image model and Swing frame.
   *
   * @param ap the output that will display in the console.
   * @param sView the swing view needed for GUI
   * @param model the multilayer image model
   * @throws IllegalArgumentException if any given arguments is null.
   */
  public SwingController(Appendable ap, ISwingViewer sView, MultiLayer model)
          throws IllegalArgumentException {
    super(ap, model);
    if (sView == null) {
      throw new IllegalArgumentException("Null argument");
    }
    sView.setListeners(this);
    swingView = sView;
    this.selectedLayerName = "";
    this.loadedFileName = "Default";
    this.currentOperation = "";
    this.commandOperation = new String[]{};
    this.commandBoard = new String[]{};
    this.commandSaveFile = new String[]{};
    this.commandSaveAll = new String[]{};
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e == null) {
      return;
    }
    actionSwitcher(e);
    stateChange();
  }

  /**
   * Performs a state change by setting an image filename of the top most visible layer,
   * setting a name of the top most visible layer, and updating an image with its filename.
   */
  private void stateChange() {
    swingView.setNewFileName(loadedFileName); // a image file name of the top most visible layer
    if (isTopMostVisibleLayerExist()) {
      // name of the top most visible layer
      swingView.setLayerNameOutput(model.getTopMostLayer().getName());
      // update image with updated file name.
      swingView.setLayerImage(this.getBufferedImage(model.getTopMostLayer()));
    } else {
      swingView.setLayerNameOutput("blank");
      swingView.setLayerImage(this.getBufferedImage(new Layer("empty layer")));
    }
  }

  /**
   * Switches the action events when a certain command appears. If the command does not exist,
   * does nothing.
   * @param e the action event needed for determining the command
   */
  private void actionSwitcher(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "create layer":
        createLayerHelper();
        break;
      case "Load File":
        loadFilesHelper();
        break;
      case "board inputs":
        checkerboardInputHelper();
        break;
      case "Load CheckerBoard":
        checkerboardLoadHelper();
        break;
      case "fileName input":
        filenameInputHelper();
        break;
      case "fileNames inputs":
        filenamesInputsHelper();
        break;
      case "Save File":
        saveFileHelper();
        break;
      case "Save All":
        saveAllHelper();
        break;
      case "Names of layers":
        nameLayersHelper(e);
        break;
      case "Operation options":
        operationsHelper(e);
        break;
      case "apply":
        applyHelper();
        break;
      default:
        break;
    }
  }

  /**
   * Helps to perform an action related to saving all the files. During the operation, the text
   * messages are sent to a user to complete a certain tasks like entering
   * a script file and filenames.
   */
  private void saveAllHelper() {
    try {
      super.commandSwitch(commandSaveAll[0], commandSaveAll);
      swingView.sendMessage(view.getMessage());
    } catch (IndexOutOfBoundsException a) {
      swingView.sendMessage("Enter a script file text and file names first");
    }
  }

  /**
   * Helps to perform an action related to a single file saving. During the operation, the text
   * messages are sent to a user if something is needed (an error or entering a filename panel).
   */
  private void saveFileHelper() {
    try {
      super.commandSwitch(commandSaveFile[0], commandSaveFile);
      swingView.sendMessage(view.getMessage());
    } catch (IndexOutOfBoundsException a) {
      swingView.sendMessage("Enter a file name first");
    } catch (NullPointerException a) {
      swingView.sendMessage("Invalid to save file. Please provide file name");
    }
  }

  /**
   * determines whether the top most visible layer exists.
   *
   * @return whether the top most visible layer exists.
   */
  private boolean isTopMostVisibleLayerExist() {
    return model.getMultiLayerSize() != 0 && !model.isAllInvisibleLayer();
  }

  /**
   * Produces the list of layer names.
   *
   * @return the list of layer names.
   */
  private List<String> getLayerNames() {
    List<String> layerNames = new ArrayList<String>();
    for (Layer layer : model.getCurrentState()) {
      layerNames.add(layer.getName());
    }
    return layerNames;
  }

  /**
   * Produces the image of the top most visible layer.
   *
   * @param layer the top most visible layer.
   * @return the buffered image of the top most visible layer.
   */
  private BufferedImage getBufferedImage(Layer layer) {
    BufferedImage result = new BufferedImage(50, 50, BufferedImage.TYPE_INT_RGB);
    if (layer.layerImage().size() == 0) {
      Color white = new Color(255, 255, 255);
      int rgb = white.getRGB();
      for (int i = 0; i < 50; i++) {
        for (int j = 0; j < 50; j++) {
          result.setRGB(i, j, rgb);
        }
      }
    } else {
      result = ImageUtil.produceImage(layer.layerImage());
    }
    return result;
  }

  /**
   * Helps to deal with performing the checkerboard inputs action. After activation, shows a dialog
   * that offers to enter the inputs needed for checkerboard creation according to the rules.
   */
  private void checkerboardInputHelper() {
    String boardInputs = swingView.askInfo("Please enter square tile size, width, length, " +
            "2 RGB values in order, all each with a single space");
    String combinedBoardInputs = "load " + "checkerboard " + boardInputs;
    commandBoard = combinedBoardInputs.split("\\s+");
    swingView.setBoardInputs(boardInputs);
  }

  /**
   * Helps to deal with loading the checkerboard. The process is unavailable if the inputs are not
   * entered. The user should correctly enter the inputs first.
   */
  private void checkerboardLoadHelper() {
    try {
      super.commandSwitch(commandBoard[0], commandBoard);
      swingView.sendMessage(view.getMessage());
    } catch (IndexOutOfBoundsException a) {
      swingView.sendMessage("Invalid process. Please, enter inputs first");
    }
  }

  /**
   * Helps to deal with image operations given an action event that is supposed to be a combo box.
   *
   * @param e the action event to do for image operations
   */
  private void operationsHelper(ActionEvent e) {
    if (e.getSource() instanceof JComboBox) {
      JComboBox<String> box = (JComboBox<String>) e.getSource();
      currentOperation = (String) box.getSelectedItem();
    }
  }

  /**
   * Helps to deal with filename inputs action. Asks to enter the filename with its format to the
   * dialog, and it saves the file using the given name.
   */
  private void filenameInputHelper() {
    String fileSaveInput = swingView.askInfo("Please enter "
            + "the name of file name to be saved(including file format)");
    commandSaveFile = new String[]{"save", fileSaveInput};
    swingView.setSavingFileName(fileSaveInput);
  }

  /**
   * Helps to deal with filenames needed for saving several images at the same time (when a script
   * is used). Shows the dialog to enter the filenames following the rules.
   */
  private void filenamesInputsHelper() {
    String filesSaveInput = swingView.askInfo("Please first enter "
            + "the name of the script txt file and then image file names to be saved, each"
            + "by space!");
    String combined = "save_all " + filesSaveInput;
    commandSaveAll = combined.split("\\s+");
    swingView.setMultipleFileNames(filesSaveInput);
  }

  /**
   * Helps to deal with apply action. Apply is needed if a user wants to apply some option from the
   * menu (such as remove layer, current, blur, and so on).
   */
  private void applyHelper() {
    try {
      String combined = currentOperation + " " + selectedLayerName;
      String[] commandLine = combined.split("\\s+");
      commandOperation = commandLine;
      super.commandSwitch(commandLine[0], commandLine);
      swingView.sendMessage(view.getMessage());
      swingView.setLayersNames(getLayerNames());
    } catch (IndexOutOfBoundsException a) {
      swingView.sendMessage("Select image operation and layer first");
    }
  }

  /**
   * Helps to perform the action of loading files of different extensions like jpeg, png, ppm.
   * After clicking on a button with load files, offers to choose the file to load image from.
   */
  private void loadFilesHelper() {
    final JFileChooser fchooser = swingView.fileChooser();
    int retvalue = swingView.getRetValue(fchooser);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      loadedFileName = f.getAbsolutePath();
    }
    String[] loadCommand = {"load", loadedFileName};
    super.commandSwitch(loadCommand[0], loadCommand);
    swingView.sendMessage(view.getMessage());
  }

  /**
   * Helps to perform action of creating a layer. After clicking on a button "create layer"
   * produces a panel to enter the name of the layer. if the name is not entered, produces a
   * error message to re-enter the name.
   */
  private void createLayerHelper() {
    String output = swingView.askInfo("Please enter the name of the layer");
    if (output != null && output.length() > 0) {
      String[] createInputs = {"create", "layer", output};
      super.commandSwitch(createInputs[0], createInputs);
      swingView.setLayersNames(getLayerNames());
      swingView.sendMessage(view.getMessage());
    } else {
      swingView.sendMessage("Invalid name for a layer. Try again");
    }
  }

  /**
   * Helps to deal with names of layers given an action event that is supposed to be a combo box.
   *
   * @param e the action event to perform for names of layers
   */
  private void nameLayersHelper(ActionEvent e) {
    if (e.getSource() instanceof JComboBox) {
      JComboBox<String> box = (JComboBox<String>) e.getSource();
      selectedLayerName = (String) box.getSelectedItem();
    }
  }

}
