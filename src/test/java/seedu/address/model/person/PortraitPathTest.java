package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PortraitPathTest {

    @Test
    public void isValidPath() {

        //invalid path

        assertFalse(PortraitPath.isValidPortraitPath(" "));
        assertFalse(PortraitPath.isValidPortraitPath("abcdjpg")); //no suffix
        assertFalse(PortraitPath.isValidPortraitPath(".png")); //only suffix
        assertFalse(PortraitPath.isValidPortraitPath("abcd.jpeg")); //invalid suffix
        assertFalse(PortraitPath.isValidPortraitPath("ab*cd.png")); //illegal char in path name
        assertFalse(PortraitPath.isValidPortraitPath("abcd..jpg")); //redundant '.'
        assertFalse(PortraitPath.isValidPortraitPath("ab.jpgcd.png")); //multiple suffix
        assertFalse(PortraitPath.isValidPortraitPath("valid.j/pg")); //suffix has '/'
        assertFalse(PortraitPath.isValidPortraitPath("C/valid.jpg")); //miss ':'

        //valid path
        assertTrue(PortraitPath.isValidPortraitPath("")); // empty path is allowed
        assertTrue(PortraitPath.isValidPortraitPath("C:/src/Name.jpg"));
        assertTrue(PortraitPath.isValidPortraitPath("D:/name12WithNumber34.png"));
        assertTrue(PortraitPath.isValidPortraitPath("E:/very/very/deep/path/name_with_underscore.jpg"));
        assertTrue(PortraitPath.isValidPortraitPath("F:/name with space.png"));
        assertTrue(PortraitPath.isValidPortraitPath("G:/Name_mixed/ with every\thing 1234.png"));
    }
}
