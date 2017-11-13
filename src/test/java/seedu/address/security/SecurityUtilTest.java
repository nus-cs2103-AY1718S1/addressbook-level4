//@@author Hailinx
package seedu.address.security;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.EncryptOrDecryptException;

public class SecurityUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private File file;

    @Before
    public void init() throws IOException {
        file = new File(testFolder.getRoot().getPath() + "TempAddressBook.xml");
    }

    @Test
    public void test_isPasswordValid() {
        Assert.assertTrue(SecurityUtil.isValidPassword("1234"));

        Assert.assertFalse(SecurityUtil.isValidPassword(null));
        Assert.assertFalse(SecurityUtil.isValidPassword(""));
        Assert.assertFalse(SecurityUtil.isValidPassword("12"));
        Assert.assertFalse(SecurityUtil.isValidPassword("dd"));
    }

    @Test
    public void test_encrypt_noException() {
        try {
            String password = "tempPassword";

            // encrypt normal file
            write_tempXmlFile("a normal file");
            SecurityUtil.encrypt(file, password);

            // encrypt a xml file
            write_tempXmlFile(SecurityUtil.XML_STARTER + "<addressbook></addressbook>");
            SecurityUtil.encrypt(file, password);

            // encrypt file without content
            write_tempXmlFile("");
            SecurityUtil.encrypt(file, password);
        } catch (IOException | EncryptOrDecryptException e) {
            e.printStackTrace();
            Assert.fail("Should not throw exception.");
        }
    }

    @Test
    public void test_encrypt_throwIoexception() throws Exception {
        thrown.expect(IOException.class);

        String password = "tempPassword";

        SecurityUtil.encrypt(new File(""), password);

        SecurityUtil.encrypt(new File("abcdefg"), password);
    }

    @Test
    public void test_decrypt_noException() throws Exception {
        String password = "tempPassword";

        // encrypt a normal xml file and then decrypt it
        String fileStr = SecurityUtil.XML_STARTER + "a normal file";
        write_tempXmlFile(fileStr);
        SecurityUtil.encrypt(file, password);
        SecurityUtil.decrypt(file, password);
        Assert.assertEquals(fileStr, read_tempXmlFile());

        // encrypt a empty xml file and then decrypt it
        fileStr = SecurityUtil.XML_STARTER + "";
        write_tempXmlFile(fileStr);
        SecurityUtil.encrypt(file, password);
        SecurityUtil.decrypt(file, password);
        Assert.assertEquals(fileStr, read_tempXmlFile());
    }

    @Test
    public void test_decryptWrongPassword_throwsEncryptOrDecryptException() throws Exception {
        thrown.expect(EncryptOrDecryptException.class);

        // encrypt a normal xml file and then decrypt it
        String fileStr = SecurityUtil.XML_STARTER + "a normal file";
        write_tempXmlFile(fileStr);
        SecurityUtil.encrypt(file, "the first");
        SecurityUtil.decrypt(file, "the second");
    }

    @Test
    public void test_decryptNotEncryptedFile_throwsEncryptOrDecryptException() throws Exception {
        thrown.expect(EncryptOrDecryptException.class);

        // encrypt a normal xml file and then decrypt it
        String fileStr = "a normal file";
        write_tempXmlFile(fileStr);
        SecurityUtil.decrypt(file, "the second");
    }

    /**
     * Writes {@code inputString} into the file.
     */
    public void write_tempXmlFile(String inputString) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(inputString);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail("Write to temp file failed.");
        }
    }

    /**
     * Reads from the file.
     */
    public String read_tempXmlFile() {
        try {
            Reader reader = new FileReader(file);
            char[] buffer = new char[(int) file.length()];
            reader.read(buffer);
            return new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail("Read temp file failed.");
        }
        return null;
    }

}
