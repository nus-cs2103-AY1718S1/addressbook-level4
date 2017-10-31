package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.FIONA;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.PersonPanelHandle;
import javafx.scene.image.Image;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.util.AppUtil;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.storage.ImageStorageTest;

//@@author nicholaschuayunzhi
public class PersonPanelTest extends GuiUnitTest {

    private PersonPanel personPanel;
    private PersonPanelHandle personPanelHandle;

    @Before
    public void setUp() {
        guiRobot.interact(() -> personPanel = new PersonPanel());
        uiPartRule.setUiPart(personPanel);
        personPanelHandle = new PersonPanelHandle(personPanel.getRoot());
    }
    @Test
    public void display() throws Exception {
        //select ALICE
        postNow(new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 0)));
        assertPersonIsDisplayed(ALICE, personPanelHandle);
        //select BOB
        postNow(new PersonPanelSelectionChangedEvent(new PersonCard(FIONA, 1)));
        assertPersonIsDisplayed(FIONA, personPanelHandle);
    }
    /**
     * Asserts that {@code personPanelHandle} displays the details of {@code expectedPerson} correctly
     */
    private void assertPersonIsDisplayed(ReadOnlyPerson expectedPerson, PersonPanelHandle personPanelHandle) {
        guiRobot.pauseForHuman();
        assertEquals(expectedPerson.getName().toString(), personPanelHandle.getName());
        assertEquals(expectedPerson.getPhone().toString(), personPanelHandle.getPhone());
        assertEquals(expectedPerson.getEmail().toString(), personPanelHandle.getEmail());
        assertEquals(expectedPerson.getAddress().toString(), personPanelHandle.getAddress());
        assertEquals(expectedPerson.getRemark().toString(), personPanelHandle.getRemark());
        assertImageDisplayed(expectedPerson.getAvatar().getOriginalFilePath(), personPanelHandle.getAvatar());

        //update tag information displayed
        personPanelHandle.updateTags();
        assertTagsAreDisplayed(expectedPerson, personPanelHandle);
    }

    /**
     * Asserts that {@code personPanelHandle} displays the tags of {@code expectedPerson} correctly
     */

    private void assertTagsAreDisplayed(ReadOnlyPerson expectedPerson, PersonPanelHandle personPanelHandle) {
        assertEquals(expectedPerson.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                personPanelHandle.getTags());
    }

    /**
     * Asserts that {@code personPanelHandle} displays the image of {@code expectedPerson} correctly
     */
    private void assertImageDisplayed(String originalFilePath, Image displayedImage) {

        Image image;
        try {
            if (originalFilePath == null || originalFilePath.isEmpty()) {
                image = AppUtil.getImage("/images/avatars/default.png");
            } else {
                image = new Image(new FileInputStream(new File(originalFilePath)));
            }

            ImageStorageTest.assertImageAreEqual(image, displayedImage);
        } catch (FileNotFoundException e) {
            assert false;
        }




    }
}
