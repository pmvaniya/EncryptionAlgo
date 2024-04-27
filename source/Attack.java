package source;

public class Attack {

	/**
	 * Read encrypted data from encrypted_file location and feed it with incorrect
	 * data
	 * 
	 * @param encrypted_file
	 */
	public void launchAttack(String encrypted_file, String fake_content, String algo, String key) {
		try {
			if (algo.equals("aes")) {
				AES aes = new AES();
				aes.encrypt(fake_content, encrypted_file, key);
			} else {
				DES des = new DES();
				des.encrypt(fake_content, encrypted_file, key);
			}
		} catch (Exception exception) {
			System.err.println("Error: " + exception.getMessage());
		}
	}
}
