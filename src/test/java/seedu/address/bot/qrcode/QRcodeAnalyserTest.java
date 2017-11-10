package seedu.address.bot.qrcode;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.bot.qrcode.exceptions.QRreadException;
import seedu.address.commons.core.LogsCenter;


/**
 * QRcodeAnalyser takes in a QR code image and unwraps the information encoded within it.
 */
public class QRcodeAnalyserTest {

    private static final Logger logger = LogsCenter.getLogger(QRcodeAnalyser.class);
    private static final String READABLE_FILE_PATH = "/data/BETSY_CROWE_READABLE.jpg";
    private static final String UNREADABLE_FILE_PATH = "/data/BETSY_CROWE_UNREADABLE.jpg";
    private static final String INVALID_FILE_PATH = "Jibberish.jpg";
    private static final String BETSY_DESIRED_DATA = "#/RR000000000SG n/Betsy Crowe t/frozen d/02-02-2002 "
            + "e/betsycrowe@example.com a/22 Crowe road S123123 p/1234567 t/fragile";

    private ClassLoader classLoader;
    private File readable;
    private File unreadable;
    private File jibberish;
    private QRcodeAnalyser QRcA;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        classLoader = getClass().getClassLoader();
        readable = new File(classLoader.getResource(READABLE_FILE_PATH).getFile());
        unreadable = new File(classLoader.getResource(UNREADABLE_FILE_PATH).getFile());
        jibberish = new File(classLoader.getResource(INVALID_FILE_PATH).getFile());
    }

    @Test
    public void acceptsReadable() {
        QRcA = new QRcodeAnalyser(readable);
        assertEquals(BETSY_DESIRED_DATA, QRcA.getDecodedText());
    }

    @Test
    public void rejectsUnreadable() throws QRreadException {
        QRcA = new QRcodeAnalyser(unreadable);
        thrown.expect(QRreadException.class);
    }

    @Test
    public void rejectsInvalidFilePath() throws IOException {
        QRcA = new QRcodeAnalyser(jibberish);
        thrown.expect(IOException.class);
    }

}
