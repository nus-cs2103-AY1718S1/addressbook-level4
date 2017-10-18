package seedu.address.commons.util;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.util.TextUtil.computeTextWidth;

import org.junit.Test;

import guitests.AddressBookGuiTest;
import javafx.scene.text.Font;

// we extend AddressBookGuitest to load (a stage) internal graphics for TextUtil to work
public class TextUtilTest extends AddressBookGuiTest {

    @Test
    public void compute_text_width() {
        Font stubFont = new Font(1);
        double width = computeTextWidth(stubFont, "stub", 0.0D);
        assertEquals(width, 2.0, 0);

        Font stubFont2 = new Font(20);
        double width2 = computeTextWidth(stubFont2, "stub", 0.0D);
        assertEquals(width2, 39.0, 0);
    }
}
