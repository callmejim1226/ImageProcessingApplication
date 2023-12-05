# ImageProcessingApplication
Group project for Object-Oriented Design course

# Screenshots & Demo video
![picphotoshop](https://github.com/callmejim1226/ImageProcessingApplication/assets/91857946/9ac96f92-9c26-46b2-aa00-030599a78477)

https://github.com/callmejim1226/ImageProcessingApplication/assets/91857946/655b6c6c-aac4-43ae-b85a-7a09d12ec198

# Assignment 5: Image Processing (Part 1)

In this assignment, we implement an application that works with colorPixel 24-bit images.

## Interfaces and their classes
All interfaces and classes implemented in this assignment are located in the model package.
### PositionPixel class
Class PositionPixel represents a position of a pixel in an image and has non-negative integer fields: row and column. The class is final, so that it cannot be mutated. If any of the parameters is invalid, an Illegal Argument Exception will be thrown.

### ColorPixel class
Class ColorPixel represents a colorPixel of a pixel in an image and has 3 fields that are 3 colorPixel channel values in a range 0-255. Each of the colorPixel channel is an integer and has a clamping, meaning all negative values become 0 and values that are greater than 255 become 255. We created this class because we needed immutable colorPixel. 
**Change**: This class was previously named Color, however, since java has its built in class Color, we had to change to use java Color to import images and use related to RGB methods freely.

### Pixel class
Class pixel represents a pixel of an image (image is sequence of pixels which is a list of lists of pixels in our case). Each pixel has non-null position in an image and non-null colorPixel. 

### Filter abstract class
Filter abstract class represents a filter which is a category of image operation and stores all helpers needed to implement all image operations that are connected to this category, which are Blur and Sharpen operations. It has a valid image for filtering. If the image is not valid, an Illegal Argument Exception will be thrown.

### ColorTransformation abstract class 
ColorTransformation abstract class represents a colorPixel transformation which is a category of image operation and stores all helpers needed to implement all image operations that are connected to this category, which are Grayscale and Sepia operations. It has a valid image for colorPixel transformation but if the image is not valid, an Illegal Argument Exception will be thrown.

### ImageOperation Interface
Interface ImageOperation represents an image operation that can be applied on an image which is represented as a list of list of pixels. It has one apply() method which is used to apply an effect on the image. 

#### Blur class
Blur class implements ImageOperation Interface and extends Filter abstract class. Blur class has a image (super field) for blurring and a specific kernel that is only for blurring. Blur class contains apply() method that applies blur effect on the image and uses helpers from Filter to do that.

### Sharpen class
Sharpen class implements ImageOperation Interface and extends Filter abstract class. Sharpen class has a image (super field) to sharpen and a specific kernel for sharpening only. Sharpen class contains apply() method that applies sharpen effect on the image and uses helpers from Filter to do that.

### Grayscale class
Grayscale class implements ImageOperation Interface and extends ColorTransformation abstract class. Grayscale class has a image (super field)  and a specific kernel for grayscale. Grayscale class contains apply() method that applies grayscale on the image and uses helpers from ColorTransformation to do that.

### Sepia class
Sepia class implements ImageOperation Interface and extends ColorTransformation abstract class. Sepia class has a image (super field) and a specific kernel for sepia tone. Sepia class contains apply() method that applies sepia tone on the image and uses helpers from ColorTransformation to do that.

### Interface ImageModel
Interface ImageModel represents an image model that contains a method createImageProgram that creates different images programmatically as a list of list of pixels. For now, there is only one class that implements this interface.

### CheckerBoardImage class
CheckerBoardImage class represents an image model of a checkerboard pattern and has fields for the image parameters: a positive size of a single square tile, a positive width and a lenth of the image, and a list of colors of size 2, where all colors are non-null. All parameters should be valid, otherwise an Illegal Argument Exception will be thrown.

### ImageUtil class
ImageUtil class contains some utility methods: 
creating an image as a list of list of pixels from a ppm file, 
writing a new ppm file given a list of list of pixels.
**Change**: Added exporting as a JPEG or PNG file from an image (a sequence of pixels),
importing from a file with JPEG or PNG format. Moved main method to its own class.
These methods while staying remained in Util class are called in our Controller.

### Citations of images used
Sea.ppm image is taken by Jimin Kim.
Boston.ppm is taken by Vita Khan.

# Assignment 6 (Part 2)
All changes in the Assignment 5 are described in the appropriate classes and marked bold.
## New Interfaces and classes
### Layer class
Layer class is located in model package and represents a layer that has a non-null given name, an image that is on default set to an
empty image, and a visibility state which is set to visible in the constructor. So the new object Layer
is created visible with its name and an image that could be set using the methods from the class. 
### Interface ImageController
ImageController interface is located in controller package and represents a controller for our MultiLayer model (a list of Layers).
The interface uses the model (Multilayer) and controls it, controls the view (Interface ImageView) and what it should
show when the user's input is processed. The method processes the actions the user gave to the application
and signals what to do to view and model.
### SimpleImageController class
SimpleImageController class is located in controller package and implements the ImageController interface and contains one public method
that is responsible for commands. It also has private helpers that are needed for implementing the
modelMultiLayers(). It accepts non-null Readable and Appendable for processing inputs and outputs.
If one of the arguments is null, the IllegalArgumentException will be thrown.
### Interface ImageViewer 
Interface ImageViewer is located in view package and responsible for the view in the application. It contains one method that renders
the messages when it receives the signal from the controller (ImageController). The messages usually contain
the information about the errors, the processes that were successfully completed, which indicates
that the model has received the call and finished its job.
### ImageTextViewer class
ImageTextViewer class implements ImageViewer interface and contains the implementation of the method
renderMessage(String message). The constructor of the class cannot accept null appendable since the error
will be thrown. 
### Main class
Main class contains one method main and is used for user interaction and creating jar file.
In that method user can input the desired filename or start typing commands. There are 2 scripts that 
could be used to interact with main method:
res/script.txt or res/script2.txt
### AppendableFail and ReadableFail classes
These classes are created for testing purposes only and located in test folder.
They contain methods that always fail and throw IOException when the instances of classes and read/append methods are called together.

# Assignment 7 (Part 3)
In this assignment we worked on GUI and fixed design from assignment 6. 

## Changes made for the implementation of this part
Main method from Main class was fixed as we had bugs last assignment. Our previous implementation did not have a 
separate Multilayer class with its own methods, so we have added this class and appropriate tests.
Since we added a new model, we had to change the constructor of our SimpleImageController class
which made it easy to test and get information about the state of the model (which was problematic before).
We also added tests for controller constructor as well. The convenience constructor was added, so that the implementation of a new controller for this hw was easier. 
For this assignment we needed a method in Layer class that we did not have previously - getName() which gives us a name of the Layer. This method was used in our
assignment 7 implementation (+ tests). Additionally, a new method was added to ImageUtil class - produceImage.
Appropriate tests were added to its test class. This method was added because we needed an image represented
as a BufferedImage for our GUI. Finally, we added a new method to our interface ImageViewer because we needed it in the implementation of our new view and controller.

### Interface ISwingViewer
This interface is created for GUI implementation and uses a lot of swing imports needed to produce a view.
It is responsible for graphical view and produces swing buttons, panes, dialogs, and so on.
This view is called by the controller, so that the interactive window appears for a viewer to use.

### SwingView class
This class implements the ISwingViewer interface and contains all methods needed to implement view.
The constructor contains all buttons, menus (they are represented as JCombo box - clarified with TA), 
windows, panels info and sizes. This class extends JFrame.

### MultiLayer class
This class represents a multilayer model that is used in the controller for GUI and just Simple controller.
This class contains methods needed to determine the state of the model: visibility, top most layer,
names, size, and other. 

### SwingController class
This class represents a controller for GUI which works with ISwingViewer as a view and MultiLayer as a model.
This class extends SimpleImageController which means that it automatically becomes an object of ImageController interface and it inherits the constructor and a protected method
that we used in assignment 6 (it was private before). Also, this controller implements ActionListener and contains some of its methods.

### MockView class
This class was created for testing purposes only since it is a view object (it implements ISwingViewer) 
and can imitate its behaviour. To specify, the mock works with user inputs and invokes the correct commands.

## Submission notice
We had to remove some test cases in order to submit because our submission zip folder was more than 9 MB.
We removed test classes for classes that had testing purposes only, ColorPixel test,
PixelTest, PositionPixelTest, CheckerBoardTest, all hw 5 related tests except ImageUtil.
We zipped some photos and folders several times due to large size, so, please, unzip them if you
want to run our tests and put those files to the folder where zip folders are (for example, 
all images zip folder has Boston related zip folder which contains pics of Boston needed for either script or testing, 
you could unzip all of them and place them into res folder, so that tests work). 
Thank you for your understanding!!! :)
