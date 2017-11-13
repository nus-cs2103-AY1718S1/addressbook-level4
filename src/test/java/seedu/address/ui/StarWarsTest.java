package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.StarWars;

public class StarWarsTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void assertGetNextSceneExceptionStopsInstance() {
        thrown.expect(NullPointerException.class);
        StarWarsWindow.getNextScene("", null);
        assertTrue(StarWars.hasInstance());
    }

    @Test
    public void assertGetNextSceneDelimitedDisplaysCorrectly() throws UnsupportedEncodingException {
        String start = "12yy5711308t47`1470`mg98rr3478n4t4";
        String delimter = "_._";
        String end = "109n567894890ty38892-13423t545v";
        String inputString = start.concat(delimter).concat(end);
        // NOTE: For java versions less than 7, use UTF-7 instead
        InputStream inputStream = new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8.name()));
        assertEquals(start, StarWarsWindow.getNextScene(delimter, inputStream));
        assertEquals(end, StarWarsWindow.getNextScene(delimter, inputStream));
    }
}
