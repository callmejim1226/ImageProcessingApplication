package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import model.Blur;
import model.CheckerBoardImage;
import model.ColorPixel;
import model.Grayscale;
import model.ImageModel;
import model.ImageOperation;
import model.Layer;
import model.MultiLayer;
import model.Sepia;
import model.Sharpen;
import view.ImageViewer;
import view.ImageTextViewer;

/**
 * The {@code SimpleImageController} represents the controller of the image model. It takes the
 * user's script or direct input from code in order to create several files as requested by the
 * user.
 */
public class SimpleImageController implements ImageController {
  protected ImageOperation operationModel;
  protected Readable rd;
  protected ImageViewer view;
  protected MultiLayer model;

  /**
   * Constructs the controller for Image model, where the user can create image
   * programmatically.
   *
   * @param input the input from the user that represents either file name or script in string.
   * @param ap    the output that will display in the console.
   * @param model the multilayer model
   * @throws IllegalArgumentException if any given arguments is null.
   */
  public SimpleImageController(Readable input, Appendable ap, MultiLayer model) throws
          IllegalArgumentException {
    if (input == null || ap == null || model == null) {
      throw new IllegalArgumentException("Null arguments");
    }
    try {
      this.rd = new FileReader(String.valueOf(input));
    } catch (FileNotFoundException e) {
      this.rd = input;
    }
    this.view = new ImageTextViewer(ap);
    this.model = model;
  }

  /**
   * Constructs the controller for image model, particularly for GUI, where the user can
   * be interactive with modeling their images.
   *
   * @param ap    the message that will be kept in string builder form.
   * @param model the multilayer model
   * @throws IllegalArgumentException if one of arguments is null.
   */
  public SimpleImageController(Appendable ap, MultiLayer model) throws IllegalArgumentException {
    if (ap == null || model == null) {
      throw new IllegalArgumentException("Null arguments");
    }
    this.view = new ImageTextViewer(ap);
    this.model = model;
  }

  @Override
  public void modelMultiLayers() {
    Scanner scanner = new Scanner(this.rd);
    boolean terminate = false;
    while (!terminate && scanner.hasNextLine()) {
      String commandLine = scanner.nextLine();
      String[] commandLineList = commandLine.split("\\s+");
      String command = commandLineList[0];
      if (command.equals("quit")) {
        terminate = true;
      } else {
        commandSwitch(command, commandLineList);
      }
    }
  }

  /**
   * Checks whether the transmission fails for rendering the given message and handle it.
   *
   * @param msg the given message as a string.
   */
  private void checkRenderMsgException(String msg) {
    try {
      this.view.renderMessage(msg);
    } catch (IOException e) {
      throw new IllegalStateException("Transmits fails.");
    }
  }

  /**
   * Handles the commands and chooses the appropriate operation to complete.
   *
   * @param command         the command to complete
   * @param commandLineList the list of commands
   */
  protected void commandSwitch(String command, String[] commandLineList) {
    switch (command) {
      case "create":
        createImageHelper(commandLineList);
        break;
      case "remove":
        removeLayer(commandLineList);
        break;
      case "current":
        currentImageHelper(commandLineList);
        break;
      case "load":
        loadImageHelper(commandLineList);
        break;
      case "save":
        saveImageHelper(commandLineList);
        break;
      case "blur":
        imageOperationHelper("blur");
        break;
      case "grayscale":
        imageOperationHelper("grayscale");
        break;
      case "sharpen":
        imageOperationHelper("sharpen");
        break;
      case "sepia":
        imageOperationHelper("sepia");
        break;
      case "invisible":
        visibilityHelper("invisible", commandLineList);
        break;
      case "visible":
        visibilityHelper("visible", commandLineList);
        break;
      case "save_all":
        this.saveAllLayer(commandLineList);
        break;
      default:
        this.checkRenderMsgException("No such command exists for layer operations. Try again.");
    }
  }

  /**
   * Saves all the layers and export image file for each of them.
   *
   * @param commandLineList the command line that represents the user inputs for saving all layers.
   */
  private void saveAllLayer(String[] commandLineList) {
    if (validCommandLine(commandLineList, "save_all")) {
      try {
        List<Integer> layerOrderList = readLocateFile(commandLineList[1]);
        if (isValidOrderList(layerOrderList)) {
          List<Layer> newState = new ArrayList<Layer>();
          for (Integer index : layerOrderList) {
            newState.add(model.getLayer(index - 1));
          }
          model.setState(newState);
          for (int i = 2; i < model.getMultiLayerSize() + 2; i++) {
            model.getLayer(i - 2).exportLayer(getFormat(commandLineList[i]),
                    commandLineList[i]);
            this.checkRenderMsgException("The layer image has successfully saved and exported!");
          }
        } else {
          this.checkRenderMsgException("Invalid script inputs. Try again.");
        }
      } catch (IllegalArgumentException e) {
        this.checkRenderMsgException(e.getMessage());
      } catch (IllegalStateException e) {
        this.checkRenderMsgException("No such Image exists. Try again.");
      }
    } else {
      this.checkRenderMsgException("Invalid input to save multi layered image. Try again.");
    }
  }

  /**
   * Helps to deal with image operations when they are called in their commands.
   */
  private void imageOperationHelper(String nameOfOperation) {
    if (model.isAllInvisibleLayer()) {
      this.checkRenderMsgException("No visible layer to operate. Try again.");
    } else {
      try {
        if (nameOfOperation.equals("blur")) {
          operationModel = new Blur(model.getTopMostLayer().layerImage());
        } else if (nameOfOperation.equals("grayscale")) {
          operationModel = new Grayscale(model.getTopMostLayer().layerImage());
        } else if (nameOfOperation.equals("sharpen")) {
          operationModel = new Sharpen(model.getTopMostLayer().layerImage());
        } else if (nameOfOperation.equals("sepia")) {
          operationModel = new Sepia(model.getTopMostLayer().layerImage());
        }
        model.getTopMostLayer().setLayerImageToAnotherImage(operationModel.apply());
        this.checkRenderMsgException("The layer image has been successfully updated!");
      } catch (IllegalArgumentException e) {
        this.checkRenderMsgException("Layer doesn't have image.");
      }
    }
  }

  /**
   * Helps to deal with commands related to visibility.
   *
   * @param command         the visibility command
   * @param commandLineList the list of commands
   */
  private void visibilityHelper(String command, String[] commandLineList) {
    if (validCommandLine(commandLineList, command)) {
      for (int i = 0; i < model.getMultiLayerSize(); i++) {
        Layer layer = model.getLayer(i);
        if (layer.isCurrentLayer(commandLineList[1])) {
          if (command.equals("visible")) {
            layer.setLayerVisible();
          } else if (command.equals("invisible")) {
            layer.setLayerInvisible();
          }
          this.checkRenderMsgException("The layer has been successfully set " + command + "!");
        }
      }
    } else {
      this.checkRenderMsgException("Either invalid inputs or "
              + "no selected layer exists. Try again.");
    }
  }

  /**
   * Determines whether the scanner could read next 9 integer inputs for creating board.
   *
   * @param commandLine the provided command line into sequence of strings.
   * @return whether the scanner could read next 9 integers.
   */
  private boolean isValidBoardInputs(String[] commandLine) {
    boolean result = false;
    if (commandLine.length == 11) {
      try {
        for (int i = 2; i < commandLine.length; i++) {
          int checkIfInt = Integer.parseInt(commandLine[i]);
        }
        result = true;
      } catch (NumberFormatException e) {
        result = false;
      }
    }
    return result;
  }

  /**
   * Load a check board image to the provided layer.
   *
   * @param layer       the provided layer that will be loaded.
   * @param commandLine the line of inputs in a sequence of strings form.
   */
  private void loadBoard(Layer layer, String[] commandLine) {
    int tileSize = Integer.parseInt(commandLine[2]);
    int width = Integer.parseInt(commandLine[3]);
    int length = Integer.parseInt(commandLine[4]);
    ColorPixel firstColor = new ColorPixel(Integer.parseInt(commandLine[5]),
            Integer.parseInt(commandLine[6]), Integer.parseInt(commandLine[7]));
    ColorPixel secondColor = new ColorPixel(Integer.parseInt(commandLine[8]),
            Integer.parseInt(commandLine[9]), Integer.parseInt(commandLine[10]));
    List<ColorPixel> colors = Arrays.asList(firstColor, secondColor);
    try {
      ImageModel imageModel = new CheckerBoardImage(tileSize, width, length, colors);
      layer.setLayerImageToAnotherImage(imageModel.createImageProgram());
      this.checkRenderMsgException("Checkerboard image has been successfully loaded to layer!");
    } catch (IllegalArgumentException e) {
      this.checkRenderMsgException("Invalid inputs for creating checkerboard image.");
    }
  }

  /**
   * Determines the image file format of given name of the file.
   *
   * @param fileName the provided image file name as a string format.
   * @return the image file format.
   */
  private String getFormat(String fileName) {
    StringBuilder format = new StringBuilder();
    boolean point = false;
    for (int i = 1; i < fileName.length(); i++) {
      if (fileName.charAt(i - 1) == '.' || point) {
        format.append(fileName.charAt(i));
        point = true;
      }
    }
    return format.toString();
  }

  /**
   * Determines if the command line from the user's input is valid.
   *
   * @param commandLine the command line inputted from the user in sequence of string form.
   * @param command     the command that user used for operating the layers.
   * @return whether the command line is valid to use.
   */
  private boolean validCommandLine(String[] commandLine, String command) {
    boolean result = false;
    switch (command) {
      case "create":
        result = !hasDuplicateLayer(commandLine) && commandLine[1].equals("layer")
                && commandLine.length == 3;
        break;
      case "remove":
        result = hasDuplicateLayer(commandLine) && commandLine[1].equals("layer")
                && commandLine.length == 3;
        break;
      case "current":
        try {
          result = visibleHelper(commandLine);
        } catch (IndexOutOfBoundsException e) {
          result = false;
        }
        break;
      case "load":
        if (commandLine[1].equals("checkerboard")) {
          result = isValidBoardInputs(commandLine);
        } else {
          result = commandLine.length == 2;
        }
        break;
      case "save":
        result = commandLine.length == 2;
        break;
      case "visible":
        result = visibleHelper(commandLine);
        break;
      case "invisible":
        result = visibleHelper(commandLine);
        break;
      case "save_all":
        result = getFormat(commandLine[1]).equals("txt")
                && commandLine.length == model.getMultiLayerSize() + 2;
        break;
      default:
        return false;
    }
    return result;
  }

  /**
   * Determines whether the given layer from the command line exists in multi layers.
   *
   * @param commandLine the line of commands
   * @return whether the given layer from the command line exists in multi layers.
   */
  private boolean hasDuplicateLayer(String[] commandLine) {
    boolean duplicateLayerExist = false;
    boolean result = false;
    try {
      for (int i = 0; i < model.getMultiLayerSize(); i++) {
        Layer layer = model.getLayer(i);
        if (layer.isCurrentLayer(commandLine[2])) {
          duplicateLayerExist = true;
        }
      }
      result = duplicateLayerExist;
    } catch (IndexOutOfBoundsException e) {
      result = false;
    }
    return result;
  }

  /**
   * Helps to determine whether the commands like visible name are valid when they are inputted.
   *
   * @param commandLine the line of commands
   * @return true if the command is valid, otherwise false
   */
  private boolean visibleHelper(String[] commandLine) {
    boolean layerExist = false;
    for (int i = 0; i < model.getMultiLayerSize(); i++) {
      if (model.getLayer(i).isCurrentLayer(commandLine[1])) {
        layerExist = true;
      }
    }
    return layerExist && commandLine.length == 2;
  }

  /**
   * Reads the script where the user sets the location of the layers.
   *
   * @param fileName the provided name of the scrip file in string form.
   * @return the list of index that will represent the location of each layer.
   * @throws IllegalArgumentException if the provided file name does not exists or
   *                                  there is any duplicate layer or there are invalid inputs.
   */
  private List<Integer> readLocateFile(String fileName) throws IllegalArgumentException {
    Scanner sc;
    List<Integer> order = new ArrayList<Integer>();
    try {
      sc = new Scanner(new FileInputStream(fileName));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File " + fileName + " not found!");
    }
    while (sc.hasNext()) {
      if (sc.next().equals("l")) {
        int num = sc.nextInt();
        if (!(order.contains(num))) {
          order.add(num);
        } else {
          throw new IllegalArgumentException("Cannot have duplicates!");
        }
      } else {
        throw new IllegalArgumentException("invalid inputs");
      }
    }
    return order;
  }

  /**
   * Determines if provided list of layer numbers are valid index of the list of layers in the
   * controller.
   *
   * @param order the provided list of index that will be used to locate layers.
   * @return whether the provided list of index is valid to use for locating layers.
   */
  private boolean isValidOrderList(List<Integer> order) {
    boolean result = true;
    List<Integer> validInputs = new ArrayList<Integer>();
    for (int i = 1; i <= order.size(); i++) {
      validInputs.add(i);
    }
    for (Integer num : order) {
      result = result && validInputs.contains(num);
    }
    return model.getMultiLayerSize() == order.size() && result;
  }

  /**
   * Helps to deal with save command in the controller. Handles all exceptions by giving appropriate
   * messages.
   *
   * @param commandLineList the list of commands
   */
  private void saveImageHelper(String[] commandLineList) {
    if (validCommandLine(commandLineList, "save")) {
      if (model.isAllInvisibleLayer()) {
        this.checkRenderMsgException("No visible layers to save an image. Try again.");
      } else {
        String fileName = commandLineList[1];
        try {
          model.getTopMostLayer().exportLayer(getFormat(fileName), fileName);
          this.checkRenderMsgException("The layer image has successfully saved and exported!");
        } catch (IllegalArgumentException e) {
          this.checkRenderMsgException("Invalid File Name for saving a file. Try again.");
        } catch (IllegalStateException e) {
          this.checkRenderMsgException("No such Image exists. Try again.");
        }
      }
    } else {
      this.checkRenderMsgException("Invalid inputs to save an image. Try again.");
    }
  }

  /**
   * removes the specified layer.
   *
   * @param commandLineList the command line from user inputs.
   */
  private void removeLayer(String[] commandLineList) {
    if (validCommandLine(commandLineList, "remove")) {
      List<Layer> newState = new ArrayList<Layer>();
      for (int i = 0; i < model.getMultiLayerSize(); i++) {
        Layer layer = model.getLayer(i);
        if (!(layer.isCurrentLayer(commandLineList[2]))) {
          newState.add(layer);
        }
      }
      model.setState(newState);
      this.checkRenderMsgException("Removed layer successfully!");
    } else {
      this.checkRenderMsgException("Invalid to remove layer. Try again.");
    }
  }

  /**
   * Helps to deal with load the image command in the controller. Handles all exceptions by
   * giving appropriate messages.
   *
   * @param commandLineList the list of commands
   */
  private void loadImageHelper(String[] commandLineList) {
    if (validCommandLine(commandLineList, "load")) {
      if (model.isAllInvisibleLayer()) {
        this.checkRenderMsgException("No visible layers to load an image. Try again.");
      } else {
        if (commandLineList[1].equals("checkerboard")) {
          loadBoard(model.getTopMostLayer(), commandLineList);
        } else {
          try {
            model.getTopMostLayer().setLayerImage(commandLineList[1],
                    getFormat(commandLineList[1]));
            this.checkRenderMsgException("The layer image has been successfully loaded!");
          } catch (IllegalArgumentException e) {
            this.checkRenderMsgException("Invalid image to load it to layer. Try again.");
          } catch (IllegalStateException e) {
            this.checkRenderMsgException("No such Image exists. Try again.");
          }
        }
      }
    } else {
      this.checkRenderMsgException("Invalid inputs to load an image. Try again.");
    }
  }

  /**
   * Helps to deal with current layer command in the controller. Handles all exceptions by
   * showing the appropriate messages to user.
   *
   * @param commandLineList the list of commands
   */
  private void currentImageHelper(String[] commandLineList) {
    if (validCommandLine(commandLineList, "current")) {
      List<Layer> newState = new ArrayList<Layer>();
      Layer removingLayer = new Layer("");
      for (int i = 0; i < model.getMultiLayerSize(); i++) {
        Layer layer = model.getLayer(i);
        if (layer.isCurrentLayer(commandLineList[1])) {
          layer.setLayerVisible();
          newState.add(layer);
          removingLayer = layer;
          this.checkRenderMsgException("The layer has been set to current.");
        }
      }
      model.removeLayer(removingLayer);
      newState.addAll(model.getCurrentState());
      model.setState(newState);
    } else {
      this.checkRenderMsgException("No layers to set as current. Try again.");
    }
  }

  /**
   * Helps to deal with create layer command in the controller. Handles all exceptions by
   * showing appropriate messages to the user.
   *
   * @param commandLineList the list of commands
   */
  private void createImageHelper(String[] commandLineList) {
    if (validCommandLine(commandLineList, "create")) {
      model.addLayer(new Layer(commandLineList[2]));
      this.checkRenderMsgException("The layer has been successfully created!");
    } else {
      this.checkRenderMsgException("Invalid to create layer. Try again.");
    }
  }
}
