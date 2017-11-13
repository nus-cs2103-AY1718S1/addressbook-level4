package seedu.address.commons.events.model;

import static junit.framework.TestCase.assertEquals;

import org.junit.Test;

public class FileChooserEventTest {

    private static final String TEST_IMG_PATH = "test.png";

    private FileChooserEvent fileChooserEvent = new FileChooserEvent();

    @Test
    public void setImgPath() throws Exception {
        fileChooserEvent.setImgPath(TEST_IMG_PATH);
        assertEquals(fileChooserEvent.getImgPath(), TEST_IMG_PATH);
    }

}
