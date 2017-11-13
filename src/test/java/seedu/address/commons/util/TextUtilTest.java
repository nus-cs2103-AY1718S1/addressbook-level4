package seedu.address.commons.util;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.util.TextUtil.computeTextWidth;

import org.junit.Before;
import org.junit.Test;

import guitests.AddressBookGuiTest;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

// we extend AddressBookGuitest to load (a stage) internal graphics for TextUtil to work
//@@author nicholaschuayunzhi-reused
public class TextUtilTest extends AddressBookGuiTest {

    private Text helper;
    @Before
    public void setup() {
        helper = new Text();
    }

    @Test
    public void compute_text_width() {
        Font stubFont = new Font(1);
        double width = computeTextWidth(stubFont, "stub", 0.0D);

        helper.setText("stub");
        helper.setFont(stubFont);

        helper.setWrappingWidth(0.0D);
        helper.setLineSpacing(0.0D);
        double expectedWidth = Math.min(helper.prefWidth(-1.0D), 0.0D);
        helper.setWrappingWidth((int) Math.ceil(expectedWidth));
        expectedWidth = Math.ceil(helper.getLayoutBounds().getWidth());


        assertEquals(expectedWidth, width, 0);

        Font stubFont2 = new Font(20);

        helper.setText("stub");
        helper.setFont(stubFont2);

        helper.setWrappingWidth(0.0D);
        helper.setLineSpacing(0.0D);
        double expectedWidth2 = Math.min(helper.prefWidth(-1.0D), 0.0D);
        helper.setWrappingWidth((int) Math.ceil(expectedWidth2));
        expectedWidth2 = Math.ceil(helper.getLayoutBounds().getWidth());

        double width2 = computeTextWidth(stubFont2, "stub", 0.0D);
        assertEquals(expectedWidth2, width2, 0);
    }
}
