package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.PersonPanelHandle;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;

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
        postNow(new PersonPanelSelectionChangedEvent(new PersonCard(BOB, 1)));
        assertPersonIsDisplayed(BOB, personPanelHandle);
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

        //update tag information displayed
        personPanelHandle.updateTags();
        assertTagsAreDisplayed(expectedPerson, personPanelHandle);
    }

    /*
     * Asserts that {@code personPanelHandle} displays the tags of {@code expectedPerson} correctly
     */

    private void assertTagsAreDisplayed(ReadOnlyPerson expectedPerson, PersonPanelHandle personPanelHandle) {
        assertEquals(expectedPerson.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                personPanelHandle.getTags());
    }
}
