package source;

public class Attack {

	/**
	 * Read encrypted data from encrypted_file location and feed it with incorrect
	 * data
	 * 
	 * @param encrypted_file
	 */
	public void launchAttack(String encrypted_file) {
		EditFile editFile = new EditFile();
		String encryptedText = editFile.read(encrypted_file);

		int length = encryptedText.length();
		char[] charArray = encryptedText.toCharArray();
		charArray[length - 1] = charArray[length - 1];

		editFile.write(encrypted_file, new String(charArray));
	}
}