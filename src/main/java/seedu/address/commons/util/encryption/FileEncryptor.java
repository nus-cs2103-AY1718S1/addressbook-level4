package seedu.address.commons.util.encryption;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * This provides encryption and decryption utilities
 */
public class FileEncryptor {
    private static final byte[] salt = {
        (byte) 0x43, (byte) 0x76, (byte) 0x95, (byte) 0xc7,
        (byte) 0x5b, (byte) 0xd7, (byte) 0x45, (byte) 0x17
    };

    private static final String MESSAGE_PUBLIC_CONTACTS_ENCRYPTION_ERROR = "Cannot encrypt the public contacts";
    private static String addressBookFilePath = "data/addressbook.xml";

    /**
     * Create a cipher
     *
     * @param pass        passphrase
     * @param encryptMode true for encryption, false for decryption
     * @return a cipher object
     * @throws GeneralSecurityException
     */
    private static Cipher makeCipher(String pass, Boolean encryptMode) throws GeneralSecurityException {

        //Use a KeyFactory to derive the corresponding key from the passphrase:
        PBEKeySpec keySpec = new PBEKeySpec(pass.toCharArray());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        SecretKey key = keyFactory.generateSecret(keySpec);

        //Create parameters from the salt and an arbitrary number of iterations:
        PBEParameterSpec pbeParamSpec = new PBEParameterSpec(salt, 42);

        //Set up the cipher:
        Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");

        //Set the cipher mode to decryption or encryption:
        if (encryptMode) {
            cipher.init(Cipher.ENCRYPT_MODE, key, pbeParamSpec);
        } else {
            cipher.init(Cipher.DECRYPT_MODE, key, pbeParamSpec);
        }

        return cipher;
    }

    /**
     * Encrypts one file to a second file using a key derived from a passphrase:
     */
    public static void encryptFile(String userId, String pass, boolean emptyFile)
            throws IOException, GeneralSecurityException {
        byte[] decData;
        byte[] encData;
        File inFile = new File(addressBookFilePath);
        //Generate the cipher using pass:
        Cipher cipher = FileEncryptor.makeCipher(pass, true);

        //Read in the file:
        FileInputStream inStream = new FileInputStream(inFile);

        int blockSize = 8;
        //Figure out how many bytes are padded
        int paddedCount = blockSize - ((int) inFile.length() % blockSize);

        //Figure out full size including padding
        int padded = (int) inFile.length() + paddedCount;

        decData = new byte[padded];


        inStream.read(decData);

        inStream.close();

        //Write out padding bytes as per PKCS5 algorithm
        for (int i = (int) inFile.length(); i < padded; ++i) {
            decData[i] = (byte) paddedCount;
        }

        //Encrypt the file data:
        encData = cipher.doFinal(decData);


        //Write the encrypted data to a new file:
        FileOutputStream outStream = new FileOutputStream(new File("data/" + userId + ".encrypted"));
        outStream.write(encData);
        outStream.close();

        if (emptyFile) {
            outStream = new FileOutputStream(new File("data/addressbook.xml"));
            String emptyContent = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                    + "<addressbook/>\n";
            outStream.write(emptyContent.getBytes());
            outStream.close();
        }
    }

    /**
     * Decrypts one file to a second file using a key derived from a passphrase:
     */
    public static void decryptFile(String fileName, String pass)
            throws Exception {
        byte[] encData;
        byte[] decData;
        File inFile = new File("data/" + fileName + ".encrypted");

        //Generate the cipher using pass:
        Cipher cipher = FileEncryptor.makeCipher(pass, false);

        //Read in the file:
        FileInputStream inStream = new FileInputStream(inFile);
        encData = new byte[(int) inFile.length()];
        inStream.read(encData);
        inStream.close();
        //Decrypt the file data:
        decData = cipher.doFinal(encData);

        //Figure out how much padding to remove

        int padCount = (int) decData[decData.length - 1];

        //Naive check, will fail if plaintext file actually contained
        //this at the end
        //For robust check, check that padCount bytes at the end have same value
        if (padCount >= 1 && padCount <= 8) {
            decData = Arrays.copyOfRange(decData, 0, decData.length - padCount);
        }
        //Write the decrypted data to a new file:

        FileOutputStream target = new FileOutputStream(new File("data/addressbook.xml"));
        target.write(decData);
        target.close();
    }

    /**
     * Encrypt the public list of contacts with file name as PUBLIC and password as PUBLIC.
     *
     * @param isLockCommand
     * @throws CommandException
     */
    public static void encryptPublicFile(boolean isLockCommand) throws CommandException {
        try {
            if (isLockCommand) {
                addressBookFilePath = "data/addressbook_empty.xml";
            }
            FileEncryptor.encryptFile("PUBLIC", "PUBLIC", false);
            addressBookFilePath = "data/addressbook.xml";
        } catch (Exception e) {
            throw new CommandException(MESSAGE_PUBLIC_CONTACTS_ENCRYPTION_ERROR);
        }
    }
}
