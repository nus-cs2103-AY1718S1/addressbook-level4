import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * method for encrypt / decrypt the files
 */
public class CipherUnit {
    private static String key = "squirrel123"; // needs to be at least 8 characters for DES
    private static String dest;
    /**
    * class for encrypting files
    */
    public static void encrypt(String path) {
        try {
            dest = path;
            FileInputStream fis = new FileInputStream(path);
            FileOutputStream fos = new FileOutputStream("data/temp.xml");
            encryptOrDecrypt(key, Cipher.ENCRYPT_MODE, fis, fos);
            swapName("data/temp.xml");
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    /**
    * class for decrypting files
    */
    public static void decrypt(String path) {
        try {
            dest = path;
            FileInputStream fis = new FileInputStream(path);
            FileOutputStream fos = new FileOutputStream("data/temp.xml");
            encryptOrDecrypt(key, Cipher.DECRYPT_MODE, fis, fos);
            swapName("data/temp.xml");
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    /**
    * translating method
    */
    public static void encryptOrDecrypt(String key, int mode, InputStream is, OutputStream os) throws Throwable {
        DESKeySpec dks = new DESKeySpec(key.getBytes());
        SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
        SecretKey desKey = skf.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES"); // DES/ECB/PKCS5Padding for SunJCE
        if (mode == Cipher.ENCRYPT_MODE) {
            cipher.init(Cipher.ENCRYPT_MODE, desKey);
            CipherInputStream cis = new CipherInputStream(is, cipher);
            doCopy(cis, os);
        } else if (mode == Cipher.DECRYPT_MODE) {
            cipher.init(Cipher.DECRYPT_MODE, desKey);
            CipherOutputStream cos = new CipherOutputStream(os, cipher);
            doCopy(is, cos);
        }
    }
    /**
    * do a copy
    */
    public static void doCopy(InputStream is, OutputStream os) throws IOException {
        byte[] bytes = new byte[64];
        int numBytes;
        while ((numBytes = is.read(bytes)) != -1) {
            os.write(bytes, 0, numBytes);
        }
        os.flush();
        os.close();
        is.close();
    }
    /**
    * used to replace files encypted/decrypted with the dectypted/encrypted one.
    */
    public static boolean swapName(String source) {
        File tmp = new File(source);
        File swapFile1 = new File(dest);
        return tmp.renameTo(swapFile1);
    }
}
