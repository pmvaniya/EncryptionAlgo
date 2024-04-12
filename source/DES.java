package source;

import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class DES {

	/**
	 * Generate a random key and store it at the aes_key location
	 * 
	 * @param des_key
	 */
	public void generateKey(String des_key) {
		byte[] keyBytes = new byte[32];
		SecureRandom secureRandom = new SecureRandom();
		secureRandom.nextBytes(keyBytes);
		String encodedKey = Base64.getEncoder().encodeToString(keyBytes);
		EditFile editFile = new EditFile();
		editFile.write(des_key, encodedKey.substring(0, 8));
	}

	/**
	 * Read data from data_file location and store the encrypted data at
	 * encrypted_file location
	 * The key is being read from des_key location
	 * 
	 * @param data_file
	 * @param encrypted_file
	 * @param des_key
	 * @throws Exception
	 */
	public void encrypt(String data_file, String encrypted_file, String des_key)
			throws Exception {
		EditFile editFile = new EditFile();
		String key = editFile.read(des_key);
		if (key.length() != 8) {
			throw new IllegalArgumentException("DES key must be exactly 8 characters long.");
		}
		SecretKey secretKey = new SecretKeySpec(key.getBytes(), "DES");

		String plaintext = editFile.read(data_file);
		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());
		editFile.write(encrypted_file, Base64.getEncoder().encodeToString(encryptedBytes));
	}

	/**
	 * Read encrypted data from encrypted_file location and store the decrypted data
	 * at decrypted_file location
	 * The key is being read from des_key location
	 * 
	 * @param encrypted_file
	 * @param decrypted_file
	 * @param des_key
	 * @throws Exception
	 */
	public void decrypt(String encrypted_file, String decrypted_file, String des_key)
			throws Exception {
		EditFile editFile = new EditFile();
		String key = editFile.read(des_key);
		if (key.length() != 8) {
			throw new IllegalArgumentException("DES key must be exactly 8 characters long.");
		}
		SecretKey secretKey = new SecretKeySpec(key.getBytes(), "DES");

		String encryptedText = editFile.read(encrypted_file);
		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
		editFile.write(decrypted_file, new String(decryptedBytes));
	}
}