package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysTag;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.TagCardHandle;
import guitests.guihandles.TagListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToListAllTagsRequestEvent;
import seedu.address.model.tag.Tag;

//@@author pohjie
public class TagListPanelTest extends GuiUnitTest {
    private static final ObservableList<Tag> TYPICAL_TAGS =
            FXCollections.observableList(getTypicalAddressBook().getTagList());

    private static final JumpToListAllTagsRequestEvent JUMP_TO_LIST_ALL_TAGS_EVENT =
            new JumpToListAllTagsRequestEvent();

    private TagListPanelHandle tagListPanelHandle;

    @Before
    public void setUp() {
        TagListPanel tagListPanel = new TagListPanel(TYPICAL_TAGS);
        uiPartRule.setUiPart(tagListPanel);

        tagListPanelHandle = new TagListPanelHandle(getChildNode(tagListPanel.getRoot(),
                TagListPanelHandle.TAG_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TYPICAL_TAGS.size(); i++) {
            tagListPanelHandle.navigateToCard(TYPICAL_TAGS.get(i));
            Tag expectedTag = TYPICAL_TAGS.get(i);
            TagCardHandle actualCard = tagListPanelHandle.getTagCardHandle(i);

            assertCardDisplaysTag(expectedTag, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }


    @Test
    public void handleJumpToListAllTagsRequestEvent() {
        postNow(JUMP_TO_LIST_ALL_TAGS_EVENT);
        guiRobot.pauseForHuman();

        for (int i = 0; i < TYPICAL_TAGS.size(); i++) {
            tagListPanelHandle.navigateToCard(TYPICAL_TAGS.get(i));
            Tag expectedTag = TYPICAL_TAGS.get(i);
            TagCardHandle actualCard = tagListPanelHandle.getTagCardHandle(i);

            assertCardDisplaysTag(expectedTag, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

}
