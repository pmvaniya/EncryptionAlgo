
/**

Information Security Project:

Implement a standalone program in Java to demonstrate the working of the DES algorithm for Text/ASCII data. The program reads the plaintext (ASCII) data from a predefined file plaintext.txt. There are two sub-routines in the program: Encrypt and Decrypt. The Encrypt subroutine will read the plaintext data from the plaintext file, generate the ciphertext and store the ciphertext in a predefined file encrypt.txt. The Decrypt subroutine will decrypt the generated ciphertext and store the decrypted ciphertext into a predefined file decrypt.txt. Make use of in-built Java packages for DES algorithm/key generation, etc. At the end of the program, the user will open the decrypt.txt file and manually verify whether the contents of the two files plaintext.txt and decrypt.txt are matching or not.

Extend the above program for AES also. Now, the revised program will first ask the user about which algorithm is to be used between DES and AES. Depending upon the algorithm choice of the user, accordingly the program will proceed with the DES processing logic or the AES processing logic. Inbuilt Java packages for both AES an DES are to be used.

Extend the above program by incorporating an ATTACK GENERATOR which randomly corrupts the contents of the encrypt file. Demonstrate that the contents of the plaintext file and the decrypted ciphertext file DO NOT TALLY if the ATTACK GENERATOR is used.

ALSO demonstrate the AVALANCHE EFFECT for the above pogram

*/

/** Import Custom Modules */
import source.AES;
import source.DES;
import source.EditFile;
import source.Attack;

/** Import Java Built-in Modules */
import java.util.Scanner;

/** Start of Program */
public class Encryption {

	public static void main(String[] args) {

		System.out.println("This program demonstrates AES and DES algorithms.\n");

		try {

			/* Decalre various variables and initialize necessary objects */
			String data_file = "data/plaintext.txt", encrypted_file = "data/encrypt.txt",
					decrypted_file = "data/decrypt.txt", incorrect_file = "data/incorrect.txt",
					avalanche_data_file = "data/ava_plain.txt", avalanche_encrypted_file = "data/ava_encrypt.txt",
					fake_content = "data/fake_content.txt";
			String aes_key = "keys/aes_key", des_key = "keys/des_key";
			Attack attack = new Attack();
			Scanner scanner = new Scanner(System.in);

			/* Ask user to choose between AES or DES */
			System.out.print("Which algorithm would you like to use (AES/DES): ");
			String algorithmChoice = scanner.nextLine().toUpperCase();

			/* If user chooses DES */
			if (algorithmChoice.equals("DES")) {
				DES des = new DES();

				System.out.print("Do you want to generate a new key? (y/n): ");
				String userChoice = scanner.nextLine();
				if (userChoice.equalsIgnoreCase("y")) {
					des.generateKey(des_key);
				} else {
					System.out.println("\nNew key will not be generated.");
				}

				des.encrypt(data_file, encrypted_file, des_key);
				des.encrypt(avalanche_data_file, avalanche_encrypted_file, des_key);
				des.decrypt(encrypted_file, decrypted_file, des_key);
				System.out.println("\nData encrypted and decrypted successfully.");

				attack.launchAttack(encrypted_file, fake_content, "des", des_key);
				des.decrypt(encrypted_file, incorrect_file, des_key);

				showFileContents();
			}

			/* If user chooses AES */
			else if (algorithmChoice.equals("AES")) {
				AES aes = new AES();

				System.out.print("Do you want to generate a new key? (y/n): ");
				String userChoice = scanner.nextLine();
				if (userChoice.equalsIgnoreCase("y")) {
					aes.generateKey(aes_key);
				} else {
					System.out.println("\nNew key will not be generated.");
				}

				aes.encrypt(data_file, encrypted_file, aes_key);
				aes.encrypt(avalanche_data_file, avalanche_encrypted_file, aes_key);
				aes.decrypt(encrypted_file, decrypted_file, aes_key);
				System.out.println("\nData encrypted and decrypted successfully.");

				attack.launchAttack(encrypted_file, fake_content, "aes", aes_key);
				aes.decrypt(encrypted_file, incorrect_file, aes_key);

				showFileContents();
			}

			/* If user enters incorrect choice */
			else {
				System.err.println("Invalid choice. Please enter 'AES' or 'DES'.");
				System.exit(0);
			}

			/* Close the opened resources */
			scanner.close();
		}

		catch (Exception exception) {
			System.err.println("Error: " + exception.getMessage());
		}
	}

	public static void showFileContents() {
		EditFile editFile = new EditFile();

		String plaintext = editFile.read("data/plaintext.txt");
		String encrypted = editFile.read("data/encrypt.txt");
		String decrypted = editFile.read("data/decrypt.txt");
		String avaplain = editFile.read("data/ava_plain.txt");
		String avaencrypt = editFile.read("data/ava_encrypt.txt");
		String fakecontent = editFile.read("data/fake_content.txt");
		String incorrect = editFile.read("data/incorrect.txt");

		System.out.println("\n> Plain Content:\n" + plaintext);
		System.out.println("> Encrypted Content:\n" + encrypted);
		System.out.println("\n> Decrypted Content:\n" + decrypted);
		System.out.println("> Plain text with a slight change:\n" + avaplain);
		System.out.println("\n> Avalanche Effect:\n" + avaencrypt);
		System.out.println("\n> Fake Content:\n" + fakecontent);
		System.out.println("> Incorrect Content due to changed Encrypted File:\n" + incorrect);
	}
}
