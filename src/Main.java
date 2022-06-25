import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

import controller.ImageController;
import controller.SimpleImageController;
import controller.SwingController;
import model.MultiLayer;
import view.ISwingViewer;
import view.SwingView;

/**
 * The class contains a main method that is needed for interaction with the code and creating jar.
 * It contains one main method that allows user to interact with the code.
 */
public class Main {
  /**
   * Allows to interact with the controller. The user can input the desired filename of script or
   * type a command.
   *
   * @param args arguments needed to start
   */
  public static void main(String[] args) {
    if (args[0].equals("-script")) {
      try {
        FileInputStream file = new FileInputStream(String.valueOf(args[1]));
        ImageController c = new SimpleImageController(new InputStreamReader(file), System.out,
                new MultiLayer());
        c.modelMultiLayers();
      } catch (FileNotFoundException e) {
        System.out.println("Invalid file.");
      }
    } else if (args[0].equals("-text")) {
      ImageController c = new SimpleImageController(new InputStreamReader(System.in), System.out,
              new MultiLayer());
      c.modelMultiLayers();
    } else if (args[0].equals("-interactive")) {
      ISwingViewer view = new SwingView();
      ImageController controller = new SwingController(new StringBuilder(), view, new MultiLayer());
    } else {
      System.out.println("Invalid command line.");
    }
  }
}
