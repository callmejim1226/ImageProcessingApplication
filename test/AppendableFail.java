import java.io.IOException;

/**
 * This class represents a class for testing purposes only in order to test IOException.
 * All methods of this class that implements Appendable throw new {@code IOException}.
 */
public class AppendableFail implements Appendable {

  @Override
  public Appendable append(CharSequence csq) throws IOException {
    throw new IOException("fail");
  }

  @Override
  public Appendable append(CharSequence csq, int start, int end) throws IOException {
    throw new IOException("fail");
  }

  @Override
  public Appendable append(char c) throws IOException {
    throw new IOException("fail");
  }
}

