package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PortraitPathTest {

    @Test
    public void isValidPath() {

        //invalid path
        assertFalse(PortraitPath.isValidPortraitPath(""));
        assertFalse(PortraitPath.isValidPortraitPath(" "));
        assertFalse(PortraitPath.isValidPortraitPath("abcdjpg")); //no suffix
        assertFalse(PortraitPath.isValidPortraitPath(".png")); //only suffix
        assertFalse(PortraitPath.isValidPortraitPath("abcd.jpeg")); //invalid suffix
        assertFalse(PortraitPath.isValidPortraitPath("ab*cd.png")); //illegal char in path name
        assertFalse(PortraitPath.isValidPortraitPath("abcd..jpg")); //redundant '.'
        assertFalse(PortraitPath.isValidPortraitPath("ab.jpgcd.png")); //multiple suffix
        assertFalse(PortraitPath.isValidPortraitPath("  .jpg")); //space name

        //valid path
        assertTrue(PortraitPath.isValidPortraitPath("Name.jpg"));
        assertTrue(PortraitPath.isValidPortraitPath("name12WithNumber34.png"));
        assertTrue(PortraitPath.isValidPortraitPath("name_with_underscore.jpg"));
        assertTrue(PortraitPath.isValidPortraitPath("name with space.png"));
        assertTrue(PortraitPath.isValidPortraitPath("Name_mixed with everything 1234.png"));
    }
}
