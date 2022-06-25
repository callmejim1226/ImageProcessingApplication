package view;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.JFileChooser;

/**
 * Interface of the GUI viewer. It provides the image and labels in the GUI and set listeners to
 * all action commands in swing viewer class.
 */
public interface ISwingViewer {

  /**
   * Sets the action listener to all labels, button, and check boxes in swing viewer.
   * @param listener the action listener from the controller class.
   * @throws IllegalArgumentException if an argument is null
   */
  void setListeners(ActionListener listener) throws IllegalArgumentException;

  /**
   * Sets the name of the current top most visible layer.
   * @param layerName the name of the top most visible layer.
   * @throws IllegalArgumentException if an argument is null
   */
  void setLayerNameOutput(String layerName) throws IllegalArgumentException;

  /**
   * Sets the text for the saving file name label.
   * @param fileName the name of the saving file.
   * @throws IllegalArgumentException if an argument is null
   */
  void setNewFileName(String fileName) throws IllegalArgumentException;

  /**
   * Sets the image of the top most visible layer in GUI.
   * @throws IllegalArgumentException if an argument is null
   */
  void setLayerImage(BufferedImage image) throws IllegalArgumentException;

  /**
   * Sets the text for the saving files' name label.
   * @param fileNames the names of the files to be saved.
   * @throws IllegalArgumentException if an argument is null
   */
  void setMultipleFileNames(String fileNames) throws IllegalArgumentException;

  /**
   * Sets the list of created layer names.
   * @param layersNames the sequence of layer names.
   * @throws IllegalArgumentException if an argument is null
   */
  void setLayersNames(List<String> layersNames) throws IllegalArgumentException;

  /**
   * Sets the label of checker board inputs.
   * @param boardInputs the inputs to create checker board.
   * @throws IllegalArgumentException if an argument is null
   */
  void setBoardInputs(String boardInputs) throws IllegalArgumentException;

  /**
   * Sends the message of the status in view.
   * @param message the provided message to be sent to user.
   * @throws IllegalArgumentException if an argument is null
   */
  void sendMessage(String message) throws IllegalArgumentException;

  /**
   * Gets the ret value of the file path.
   * @param fchooser the file chooser.
   * @return the ret value of the file path.
   * @throws IllegalArgumentException if an argument is null
   */
  int getRetValue(JFileChooser fchooser) throws IllegalArgumentException;

  /**
   * Updates the a file name that user is trying to save as.
   * @param saveFileName the file name that user is trying to save as.
   * @throws IllegalArgumentException if an argument is null
   */
  void setSavingFileName(String saveFileName) throws IllegalArgumentException;

  /**
   * Asks the user to provide an input for a layer showing the pane with the instructions.
   * @param ins the instructions for a user
   * @return the output of the user
   * @throws IllegalArgumentException if the instruction is null
   */
  String askInfo(String ins) throws IllegalArgumentException;

  /**
   * Appears when the user chooses a file in the app.
   * @return the file chooser
   */
  JFileChooser fileChooser();

}
