package source;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EditFile {
  public String read(String filename) {
    try {
      Path path = Paths.get(filename);
      byte[] bytes = Files.readAllBytes(path);
      return new String(bytes, "UTF-8");
    } catch (IOException e) {
      System.err.println("Error reading file: " + e.getMessage());
      return null;
    }
  }

  public void write(String filename, String content) {
    try {
      Path path = Paths.get(filename);
      Files.write(path, content.getBytes());
    } catch (IOException e) {
      System.err.println("Error writing file: " + e.getMessage());
    }
  }
}