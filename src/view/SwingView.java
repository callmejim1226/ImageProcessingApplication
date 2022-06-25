package view;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * This class represents a view for GUI and swing. It contains windows, buttons, panels, dialogs
 * needed for a graphical interface and user interaction.
 */
public class SwingView extends JFrame implements ISwingViewer {
  private JButton layerInputButton;
  private JButton applyButton;
  private JButton inputButton;
  private JButton loadFile;
  private JButton saveFile;
  private JButton loadCheckerBoard;
  private JButton boardInputButton;
  private JButton multipleFilesInputButton;
  private JButton saveAll;
  private JComboBox<String> operationBoxes;
  private JComboBox<String> layerNames;
  private JLabel newFileName;
  private JLabel multipleFileNames;
  private JLabel layerNameLabel;
  private JLabel imageLabel;
  private JLabel boardInput;
  private JLabel saveFileNameLabel;
  private JPanel mainPanel;

  /**
   * Constructs a swing view by initializing sizes and creating buttons, panels, and so on.
   * Sets the command to the buttons and options.
   */
  public SwingView() {
    super();
    settingsOfTheFrame();
    createMainPanel();
    createImagePanel();
    workWithFilePanel();
    JScrollPane mainScrollPane = new JScrollPane(mainPanel);
    add(mainScrollPane);
    setVisible(true);
  }

  /**
   * Arranges, assigns, adds buttons related to loading, saving files, entering inputs the file,
   * sets up the file panel for GUI.
   */
  private void workWithFilePanel() {
    JPanel fileButtonPanel = createFileButtonsPanel();
    mainPanel.add(fileButtonPanel);
    JPanel loadFilePanel = createButtonsLoadFileAndFilePanel();
    fileButtonPanel.add(loadFilePanel);
    JPanel loadCheckerBoardPanel = createCheckerBoardPanel();
    fileButtonPanel.add(loadCheckerBoardPanel);
    JPanel savingFile = createSavePanels();
    fileButtonPanel.add(savingFile);
    savingFile.add(saveFile);
    savingFile.add(inputButton);
    saveFileNameLabel = new JLabel("Default");
    savingFile.add(saveFileNameLabel);
    JPanel saveAllPanel = createSavePanels();
    fileButtonPanel.add(saveAllPanel);
    saveAllPanel.add(saveAll);
    saveAllPanel.add(multipleFilesInputButton);
    multipleFileNames = new JLabel("Default");
    saveAllPanel.add(multipleFileNames);
  }


  /**
   * Creates a panel needed for GUI. Such panel is used for save file and save all options.
   * @return a panel for graphical interface
   */
  private JPanel createSavePanels() {
    JPanel savePanel = new JPanel();
    savePanel.setLayout(new FlowLayout());
    return savePanel;
  }

  /**
   * Creates a scroll pane and creates an image label for GUI. Image scroll pane is set to be white
   * and with its standard size.
   * @return a scroll pane for graphical interface
   */
  private JScrollPane createFileImageLabelAndPane() {
    imageLabel = new JLabel();
    JScrollPane imageScrollPane = new JScrollPane(imageLabel);
    imageScrollPane.getViewport().setBackground(Color.WHITE);
    imageScrollPane.setPreferredSize(new Dimension(1000, 500));
    return imageScrollPane;
  }

  /**
   * Initializes the settings of the frame needed for GUI.
   */
  private void settingsOfTheFrame() {
    setTitle("Multilayer Image");
    setSize(500, 500);
    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setDefaultLookAndFeelDecorated(false);
  }

  /**
   * Creates buttons for loading files and file panel needed for GUI.
   * @return a panel for loading files and load buttons
   */
  private JPanel createButtonsLoadFileAndFilePanel() {
    // load file button and load file panel
    JPanel loadFilePanel = new JPanel();
    loadFilePanel.setLayout(new FlowLayout());
    newFileName = new JLabel("Default");
    loadFile = new JButton("Load File");
    loadFile.setActionCommand("Load File");
    loadFilePanel.add(loadFile);
    loadFilePanel.add(newFileName);
    return loadFilePanel;
  }

  /**
   * Creates a main panel for GUI. Includes layer menu panels, labels, operations menu, layer
   * name menu, buttons.
   */
  private void createMainPanel() {
    // image with menu panel
    mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    JPanel layerMenuPanel = createLayerMenuPanel();
    mainPanel.add(layerMenuPanel);
    layerMenuPanel.setLayout(new FlowLayout());
    layerNameLabel = new JLabel("Top Most Visible Layer Name: blank");
    layerMenuPanel.add(layerNameLabel);
    createOperationsMenu();
    layerMenuPanel.add(operationBoxes);
    createLayerNameMenu();
    layerMenuPanel.add(layerNames);
    createButtons();
    layerMenuPanel.add(layerInputButton);
    layerMenuPanel.add(applyButton);
  }

  /**
   * Creates a layer menu panel for GUI with the title.
   * @return a layer menu panel with title.
   */
  private JPanel createLayerMenuPanel() {
    // layer menu panel
    JPanel layerMenuPanel = new JPanel();
    layerMenuPanel.setBorder(BorderFactory.createTitledBorder("Layer operations and model"));
    return layerMenuPanel;
  }

  /**
   * Creates a menu of operations related to layers and changing their states. Include layer
   * manipulation (remove and set to current, visibility) and image operations (blur, sepia,
   * grayscale, and sharpen).
   */
  private void createOperationsMenu() {
    // layer operations combo boxes
    String[] options = {"remove layer", "current", "visible", "invisible", "blur", "sharpen",
                        "grayscale", "sepia"};
    operationBoxes = new JComboBox<String>();
    operationBoxes.setActionCommand("Operation options");
    for (int i = 0; i < options.length; i++) {
      operationBoxes.addItem(options[i]);
    }
  }

  /**
   * Creates a menu of existing layers. If there is no layer, then shows None. After creating layers
   * the names are displayed in each option.
   */
  private void createLayerNameMenu() {
    // layer names combo boxes
    String[] emptyLayerNames = {"None"};
    layerNames = new JComboBox<String>(emptyLayerNames);
    layerNames.setActionCommand("Names of layers");
  }

  /**
   * Creates buttons needed for GUI implementation. Each buttons has its name and action command.
   */
  private void createButtons() {
    layerInputButton = new JButton("Click to create layer");
    layerInputButton.setActionCommand("create layer"); // layer name button
    applyButton = new JButton("Apply");
    applyButton.setActionCommand("apply"); // apply button
    saveFile = new JButton("Save File");
    saveFile.setActionCommand("Save File"); // saving button
    inputButton = new JButton("Click to enter FileName");
    inputButton.setActionCommand("fileName input"); // input file name button
    saveAll = new JButton("Save All");
    saveAll.setActionCommand("Save All"); // load save all files button
    multipleFilesInputButton = new JButton("Click to enter FileNames");
    multipleFilesInputButton.setActionCommand("fileNames inputs"); // multiple files input button
  }

  /**
   * Creates an image panel with the title for GUI that is located in the main panel.
   */
  private void createImagePanel() {
    // image panel
    JPanel imagePanel = new JPanel();
    imagePanel.setBorder(BorderFactory.createTitledBorder("Showing an top most layer image"));
    imagePanel.setLayout(new FlowLayout());
    JScrollPane imageScrollPane = createFileImageLabelAndPane();
    imagePanel.add(imageScrollPane);
    mainPanel.add(imagePanel);
  }

  /**
   * Creates file panel which contains all buttons related to file manipulation, with title.
   * @return a panel for file buttons
   */
  private JPanel createFileButtonsPanel() {
    // file button panel
    JPanel fileButtonPanel = new JPanel();
    fileButtonPanel.setBorder(BorderFactory.createTitledBorder("File Buttons"));
    fileButtonPanel.setLayout(new GridLayout(4, 1, 0, 0));
    return fileButtonPanel;
  }

  /**
   * Creates a checkerboard panel with its buttons, titles, and instructions.
   * @return a panel for checkerboard loading
   */
  private JPanel createCheckerBoardPanel() {
    // load checkerboard image file button
    JPanel loadCheckerBoardPanel = new JPanel();
    loadCheckerBoardPanel.setLayout(new FlowLayout());
    loadCheckerBoard = new JButton("Load CheckerBoard");
    loadCheckerBoard.setActionCommand("Load CheckerBoard");
    boardInputButton = new JButton("Click to enter inputs to create board");
    boardInputButton.setActionCommand("board inputs");
    boardInput = new JLabel("Default");
    loadCheckerBoardPanel.add(loadCheckerBoard);
    loadCheckerBoardPanel.add(boardInputButton);
    loadCheckerBoardPanel.add(boardInput);
    return loadCheckerBoardPanel;
  }

  @Override
  public void setListeners(ActionListener listener) throws IllegalArgumentException {
    if (listener == null) {
      throw new IllegalArgumentException("Null action listener");
    }
    operationBoxes.addActionListener(listener);
    layerNames.addActionListener(listener);
    layerInputButton.addActionListener(listener);
    applyButton.addActionListener(listener);
    inputButton.addActionListener(listener);
    loadFile.addActionListener(listener);
    saveFile.addActionListener(listener);
    loadCheckerBoard.addActionListener(listener);
    boardInputButton.addActionListener(listener);
    multipleFilesInputButton.addActionListener(listener);
    saveAll.addActionListener(listener);
  }

  @Override
  public void setLayerNameOutput(String layerName) throws IllegalArgumentException {
    if (layerName == null) {
      throw new IllegalArgumentException("Null name of a layer");
    }
    layerNameLabel.setText("Top Most Visible Layer Name: " + layerName);
  }

  @Override
  public void setNewFileName(String fileName) throws IllegalArgumentException {
    if (fileName == null) {
      throw new IllegalArgumentException("Null name of a file");
    }
    newFileName.setText(fileName);
  }

  @Override
  public void setLayerImage(BufferedImage image) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("Null image");
    }
    imageLabel.setIcon(new ImageIcon(image));
  }

  @Override
  public void setMultipleFileNames(String fileNames) throws IllegalArgumentException {
    if (fileNames == null) {
      throw new IllegalArgumentException("Null names of files");
    }
    multipleFileNames.setText(fileNames);
  }

  @Override
  public void setLayersNames(List<String> layersNames) throws IllegalArgumentException {
    if (layersNames == null) {
      throw new IllegalArgumentException("Null list");
    }
    layerNames.removeAllItems();
    for (int i = 0; i < layersNames.size(); i++) {
      layerNames.addItem(layersNames.get(i));
    }
  }

  @Override
  public void setBoardInputs(String boardInputs) throws IllegalArgumentException {
    if (boardInputs == null) {
      throw new IllegalArgumentException("Null inputs");
    }
    boardInput.setText(boardInputs);
  }

  @Override
  public void sendMessage(String msg) throws IllegalArgumentException {
    if (msg == null) {
      throw new IllegalArgumentException("Null message");
    }
    JOptionPane.showMessageDialog(SwingView.this, msg,
            "Message", JOptionPane.PLAIN_MESSAGE);
  }

  @Override
  public int getRetValue(JFileChooser fchooser) throws IllegalArgumentException {
    if (fchooser == null) {
      throw new IllegalArgumentException("Null argument");
    }
    return fchooser.showOpenDialog(SwingView.this);
  }

  @Override
  public void setSavingFileName(String saveFileName) throws IllegalArgumentException {
    if (saveFileName == null) {
      throw new IllegalArgumentException("Null file name");
    }
    saveFileNameLabel.setText(saveFileName);
  }

  @Override
  public String askInfo(String ins) throws IllegalArgumentException {
    if (ins == null) {
      throw new IllegalArgumentException("Null instruction");
    }
    return JOptionPane.showInputDialog(ins);
  }

  @Override
  public JFileChooser fileChooser() {
    final JFileChooser fchooser = new JFileChooser(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Images", "jpg", "png", "ppm");
    fchooser.setFileFilter(filter);
    return fchooser;
  }
}
