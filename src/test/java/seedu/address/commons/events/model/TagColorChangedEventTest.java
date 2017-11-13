package seedu.address.commons.events.model;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.Test;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.tag.Tag;

//@@author yunpengn
public class TagColorChangedEventTest {
    @Test
    public void createEvent_success() throws Exception {
        BaseEvent event = new TagColorChangedEvent(new Tag(VALID_TAG_HUSBAND), "7db9a1");
        assertEquals("The color of tag [husband] has been changed to 7db9a1.", event.toString());
    }
}
