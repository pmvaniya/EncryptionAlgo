import java.io.*;
import java.nio.file.*;
import java.util.*;
import javax.crypto.*;
import javax.crypto.spec.*;

public class EncryptionDemo {

  public static void main(String[] args) {
    try {
      Scanner scanner = new Scanner(System.in);
      System.out.println("Choose encryption algorithm (DES/AES): ");
      String algorithmChoice = scanner.nextLine().toUpperCase();
      if (algorithmChoice.equals("DES")) {
        processDES();
      } else if (algorithmChoice.equals("AES")) {
        processAES();
      } else {
        System.err.println("Invalid choice! Please choose either DES or AES.");
      }
    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
    }
  }

  private static void processDES() throws Exception {
    String plaintext = readFile("plaintext.txt");
    String encryptedText = encryptDES(plaintext);
    writeFile("encrypt_des.txt", encryptedText);

    String decryptedText = decryptDES(encryptedText);
    writeFile("decrypt_des.txt", decryptedText);

    System.out.println("DES encryption and decryption completed successfully.");
  }

  private static void processAES() throws Exception {
    String plaintext = readFile("plaintext.txt");
    String encryptedText = encryptAES(plaintext);
    writeFile("encrypt_aes.txt", encryptedText);

    String decryptedText = decryptAES(encryptedText);
    writeFile("decrypt_aes.txt", decryptedText);

    System.out.println("AES encryption and decryption completed successfully.");
  }

  private static String readFile(String filePath) throws Exception {
    return new String(Files.readAllBytes(Paths.get(filePath)));
  }

  private static void writeFile(String filePath, String content) throws Exception {
    Files.write(Paths.get(filePath), content.getBytes(), StandardOpenOption.CREATE);
  }

  private static String encryptDES(String plaintext) throws Exception {
    String key = "secretKe"; // Your secret key (8 characters)
    if (key.length() != 8) {
      throw new IllegalArgumentException("DES key must be exactly 8 characters long.");
    }
    SecretKey secretKey = new SecretKeySpec(key.getBytes(), "DES");

    Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
    byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());

    return Base64.getEncoder().encodeToString(encryptedBytes);
  }

  private static String decryptDES(String encryptedText) throws Exception {
    String key = "secretKe"; // Your secret key (8 characters)
    if (key.length() != 8) {
      throw new IllegalArgumentException("DES key must be exactly 8 characters long.");
    }
    SecretKey secretKey = new SecretKeySpec(key.getBytes(), "DES");

    Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
    cipher.init(Cipher.DECRYPT_MODE, secretKey);
    byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));

    return new String(decryptedBytes);
  }

  private static String encryptAES(String plaintext) throws Exception {
    String key = "aesSecretKey1234"; // Your secret key for AES (16, 24, or 32 characters)
    if (key.length() != 16 && key.length() != 24 && key.length() != 32) {
      throw new IllegalArgumentException("AES key must be 16, 24, or 32 characters long.");
    }
    SecretKey secretKey = new SecretKeySpec(key.getBytes(), "AES");

    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
    byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());

    return Base64.getEncoder().encodeToString(encryptedBytes);
  }

  private static String decryptAES(String encryptedText) throws Exception {
    String key = "aesSecretKey1234"; // Your secret key for AES (16, 24, or 32 characters)
    if (key.length() != 16 && key.length() != 24 && key.length() != 32) {
      throw new IllegalArgumentException("AES key must be 16, 24, or 32 characters long.");
    }
    SecretKey secretKey = new SecretKeySpec(key.getBytes(), "AES");

    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
    cipher.init(Cipher.DECRYPT_MODE, secretKey);
    byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));

    return new String(decryptedBytes);
  }
}
