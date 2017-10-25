package seedu.address.storage;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

//@@author jelneo-reused

/*
 * This class is written with reference to an article by HowToDoInJava:
 * Generate Secure Password Hash : MD5, SHA, PBKDF2, BCrypt Examples
 *
 * Link: https://howtodoinjava.com/security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/
 */

/**
 * Deals with password security
 */
public class PasswordSecurity {

    /**
     * Generates a SHA-512 secure password using a given salt and password
     * @param salt randomly generated text
     * @return a password that is hashed
     */
    public static String getSha512SecurePassword(String passwordToHash, byte[] salt) {
        String generatedPassword = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.update(salt);
            byte[] bytes = messageDigest.digest(passwordToHash.getBytes());
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                // Converting each
                stringBuilder.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = stringBuilder.toString();
        } catch (NoSuchAlgorithmException nsae) {
            assert false : "Cryptographic algorithm should be available.";
        }
        return generatedPassword;
    }

    /**
     * Generates a random salt
     */
    public static byte[] getSalt() {
        SecureRandom secureRandom = null;
        try {
            secureRandom = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            assert false : "Cryptographic algorithm should be available.";
        }
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return salt;
    }
}
