import java.io.IOException;
import java.nio.CharBuffer;

/**
 * This class represents a class for testing only that implements Readable.
 * It contains one method that only throws IOException.
 */
public class ReadableFail implements Readable {

  @Override
  public int read(CharBuffer cb) throws IOException {
    throw new IOException("fail");
  }
}
