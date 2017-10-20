package seedu.address.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import seedu.address.commons.exceptions.EncryptOrDecryptException;

/**
 * Helper functions for AES encryption and decryption.
 */
public class SecurityUtil {

    public static final int MIN_KEY_LENGTH = 4;

    private static final String TEMP_FILE_PATH = "temp.xml";

    private static final String xmlStarter = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";

    public static boolean isValidPassword(String password) {
        return !(password == null || password.length() < MIN_KEY_LENGTH);
    }

    /**
     * Returns SecretKeySpec by given key string
     */
    private static SecretKeySpec getKeySpec(String inputKey) {
        byte[] keyByte;
        MessageDigest sha;
        try {
            keyByte = inputKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            keyByte = sha.digest(keyByte);
            keyByte = Arrays.copyOf(keyByte, 16);
            return new SecretKeySpec(keyByte, "AES");
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Encrypts the file
     * @throws IOException if the file cannot be found.
     * @throws EncryptOrDecryptException if the file cannot be encrypted.
     */
    public static void encrypt(File file, String inputKey)
            throws IOException, EncryptOrDecryptException {
        try {
            SecretKeySpec keySpec = getKeySpec(inputKey);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);

            FileInputStream inputStream = new FileInputStream(file);
            byte[] inputByte = new byte[(int) file.length()];
            inputStream.read(inputByte);
            inputStream.close();

            byte[] outputByte = cipher.doFinal(inputByte);

            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(outputByte);
            outputStream.close();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException
                | NoSuchPaddingException
                | BadPaddingException
                | InvalidKeyException e) {
            throw new EncryptOrDecryptException();
        }
    }

    /**
     * Decrypts the file
     * @throws IOException if the file cannot be found.
     * @throws EncryptOrDecryptException if the file cannot be decrypted.
     */
    public static void decrypt(File file, String inputKey)
            throws IOException, EncryptOrDecryptException {
        File tempFile = new File(file.getParent(), TEMP_FILE_PATH);

        try {
            SecretKeySpec keySpec = getKeySpec(inputKey);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);

            FileInputStream inputStream = new FileInputStream(file);
            byte[] inputByte = new byte[(int) file.length()];
            inputStream.read(inputByte);
            inputStream.close();

            byte[] outputByte = cipher.doFinal(inputByte);

            // write to a temp file to attempt decryption
            FileOutputStream outputStream = new FileOutputStream(tempFile);
            outputStream.write(outputByte);
            outputStream.close();

            if (isEncrypted(tempFile)) {
                throw new EncryptOrDecryptException();
            } else {
                outputStream = new FileOutputStream(file);
                outputStream.write(outputByte);
                outputStream.close();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException
                | NoSuchPaddingException
                | BadPaddingException
                | InvalidKeyException e) {
            throw new EncryptOrDecryptException();
        } finally {
            // delete the temp file
            tempFile.delete();
        }
    }

    /**
     * @return true if the file is encrypted.
     */
    public static boolean isEncrypted(File file) throws IOException {
        char[] charset = new char[xmlStarter.length()];

        FileReader reader = new FileReader(file);
        reader.read(charset, 0, xmlStarter.length());
        reader.close();

        return !new String(charset).equals(xmlStarter);
    }

}
