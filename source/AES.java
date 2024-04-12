package source;

import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AES {

	/**
	 * Generate a random key and store it at the aes_key location
	 * 
	 * @param aes_key aes_key is the file name describing it's location
	 */
	public void generateKey(String aes_key) {
		byte[] keyBytes = new byte[32];
		SecureRandom secureRandom = new SecureRandom();
		secureRandom.nextBytes(keyBytes);
		String encodedKey = Base64.getEncoder().encodeToString(keyBytes);
		EditFile editFile = new EditFile();
		editFile.write(aes_key, encodedKey.substring(0, 32));
	}

	/**
	 * Read data from data_file location and store the encrypted data at
	 * encrypted_file location
	 * The key is being read from aes_key location
	 * 
	 * @param data_file
	 * @param encrypted_file
	 * @param aes_key
	 * @throws Exception
	 */
	public void encrypt(String data_file, String encrypted_file, String aes_key)
			throws Exception {
		EditFile editFile = new EditFile();
		String key = editFile.read(aes_key);
		if (key.length() != 16 && key.length() != 24 && key.length() != 32) {
			throw new IllegalArgumentException("AES key must be 16, 24, or 32 characters long.");
		}
		SecretKey secretKey = new SecretKeySpec(key.getBytes(), "AES");

		String plaintext = editFile.read(data_file);
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());
		editFile.write(encrypted_file, Base64.getEncoder().encodeToString(encryptedBytes));
	}

	/**
	 * Read encrypted data from encrypted_file location and store the decrypted data
	 * at decrypted_file location
	 * The key is being read from aes_key location
	 * 
	 * @param encrypted_file
	 * @param decrypted_file
	 * @param aes_key
	 * @throws Exception
	 */
	public void decrypt(String encrypted_file, String decrypted_file, String aes_key)
			throws Exception {
		EditFile editFile = new EditFile();
		String key = editFile.read(aes_key);
		if (key.length() != 16 && key.length() != 24 && key.length() != 32) {
			throw new IllegalArgumentException("AES key must be 16, 24, or 32 characters long.");
		}
		SecretKey secretKey = new SecretKeySpec(key.getBytes(), "AES");

		String encryptedText = editFile.read(encrypted_file);
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
		editFile.write(decrypted_file, new String(decryptedBytes));
	}
}