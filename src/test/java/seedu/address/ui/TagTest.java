package seedu.address.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import guitests.guihandles.TagHandle;

public class TagTest extends GuiUnitTest {

    public static final String TAG_STUB = "TAG";

    @Test
    public void display() {
        Tag tag = new Tag(TAG_STUB);
        TagHandle th = new TagHandle(tag.getRoot());

        assertEquals(tag.getText(), th.getText());

        //Label is used inside tag
        assertEquals(tag.getLabel(), tag.getRoot());

        assertEquals(tag.getLabel().getText(), th.getText());
    }
}
