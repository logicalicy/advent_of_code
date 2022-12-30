package app.utils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileReader {
  private String path;
  public FileReader(String path) {
      this.path = path;
  }
  public String getContent() throws IOException {
    return Files.readString(Path.of(this.path));
  }
}
