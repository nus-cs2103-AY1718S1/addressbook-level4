//@@author tshradheya
package seedu.address.ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPersonDetailsPanel;

import org.junit.Test;

import guitests.guihandles.DetailsPanelHandle;
import seedu.address.model.person.DisplayPicture;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PersonBuilder;

public class DetailsPanelTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Person personWithNoTags = new PersonBuilder().withTags(new String[0]).build();
        DetailsPanel detailsPanel = new DetailsPanel(personWithNoTags);
        uiPartRule.setUiPart(detailsPanel);
        assertCardDisplay(detailsPanel, personWithNoTags, 1);

        // with tags
        Person personWithTags = new PersonBuilder().build();
        detailsPanel = new DetailsPanel(personWithTags);
        uiPartRule.setUiPart(detailsPanel);
        assertCardDisplay(detailsPanel, personWithTags, 2);

        // changes made to Person reflects on card
        guiRobot.interact(() -> {
            personWithTags.setName(ALICE.getName());
            personWithTags.setAddress(ALICE.getAddress());
            personWithTags.setEmail(ALICE.getEmail());
            personWithTags.setPhone(ALICE.getPhone());
            personWithTags.setNickname(ALICE.getNickname());
            personWithNoTags.setDisplayPicture(ALICE.getDisplayPicture());
            personWithTags.setTags(ALICE.getTags());
        });
        assertCardDisplay(detailsPanel, personWithTags, 2);
    }

    @Test
    public void displayImage() {
        Person personWithDisplayPicture = new PersonBuilder().withDisplayPicture("1137944384").build();
        DetailsPanel detailsPanel = new DetailsPanel(personWithDisplayPicture);
        uiPartRule.setUiPart(detailsPanel);
        assertCardDisplay(detailsPanel, personWithDisplayPicture, 1);

        // changes made to Person reflects on card
        guiRobot.interact(() -> {
            personWithDisplayPicture.setName(ALICE.getName());
            personWithDisplayPicture.setAddress(ALICE.getAddress());
            personWithDisplayPicture.setEmail(ALICE.getEmail());
            personWithDisplayPicture.setPhone(ALICE.getPhone());
            personWithDisplayPicture.setNickname(ALICE.getNickname());
            personWithDisplayPicture.setDisplayPicture(new DisplayPicture("1137944384"));
            personWithDisplayPicture.setTags(ALICE.getTags());
        });
    }

    @Test
    public void equals() {
        Person person = new PersonBuilder().build();
        DetailsPanel detailsPanel = new DetailsPanel(person);

        // same person, same index -> returns true
        DetailsPanel copy = new DetailsPanel(person);
        assertTrue(detailsPanel.equals(copy));

        // same object -> returns true
        assertTrue(detailsPanel.equals(detailsPanel));

        // null -> returns false
        assertFalse(detailsPanel.equals(null));

        // different types -> returns false
        assertFalse(detailsPanel.equals(0));

        // different person, same index -> returns false
        Person differentPerson = new PersonBuilder().withName("differentName").build();
        assertFalse(detailsPanel.equals(new DetailsPanel(differentPerson)));
    }

    /**
     * Asserts that {@code personCard} displays the details of {@code expectedPerson} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(DetailsPanel detailsPanel, ReadOnlyPerson expectedPerson, int expectedId) {
        guiRobot.pauseForHuman();

        DetailsPanelHandle detailsPanelHandle = new DetailsPanelHandle(detailsPanel.getRoot());

        // verify person details are displayed correctly
        assertCardDisplaysPersonDetailsPanel(expectedPerson, detailsPanelHandle);
    }
}
