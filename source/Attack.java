package source;

import source.EditFile;

public class Attack {
  public static void launchAttack(String encrypted_file) {
    EditFile editFile = new EditFile();
    String encryptedText = editFile.read(encrypted_file);

    int length = encryptedText.length();
    char[] charArray = encryptedText.toCharArray();
    charArray[length - 1] = charArray[length - 1];
    String attacked_content = new String(charArray);

    editFile.write(encrypted_file, attacked_content);
  }
}