package seedu.address.ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.ui.testutil.GuiTestAssert.assertInfoPanelDisplaysPerson;

import org.junit.Test;

import guitests.guihandles.PersonInfoPanelHandle;

import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PersonBuilder;

//@@author liliwei25
public class PersonInfoPanelTest extends GuiUnitTest {

    private static final String DIFFERENT_NAME = "differentName";

    @Test
    public void display() {
        // no tags
        Person personWithNoTags = new PersonBuilder().withTags().build();
        PersonInfoPanel personInfoPanel = new PersonInfoPanel(personWithNoTags);
        uiPartRule.setUiPart(personInfoPanel);
        assertPersonInfoDisplay(personInfoPanel, personWithNoTags);

        // with tags
        Person personWithTags = new PersonBuilder().build();
        personInfoPanel = new PersonInfoPanel(personWithTags);
        uiPartRule.setUiPart(personInfoPanel);
        assertPersonInfoDisplay(personInfoPanel, personWithTags);

        // changes made to Person reflects on card
        guiRobot.interact(() -> {
            personWithTags.setName(ALICE.getName());
            personWithTags.setAddress(ALICE.getAddress());
            personWithTags.setEmail(ALICE.getEmail());
            personWithTags.setPhone(ALICE.getPhone());
            personWithTags.setBirthday(ALICE.getBirthday());
            personWithTags.setRemark(ALICE.getRemark());
            personWithTags.setTags(ALICE.getTags());
        });
        assertPersonInfoDisplay(personInfoPanel, personWithTags);
    }

    @Test
    public void equals() {
        Person person = new PersonBuilder().build();
        PersonInfoPanel personInfoPanel = new PersonInfoPanel(person);

        // same person -> returns true
        PersonInfoPanel copy = new PersonInfoPanel(person);
        assertTrue(personInfoPanel.equals(copy));

        // same object -> returns true
        assertTrue(personInfoPanel.equals(personInfoPanel));

        // null -> returns false
        assertFalse(personInfoPanel.equals(null));

        // different types -> returns false
        assertFalse(personInfoPanel.equals(0));

        // different person -> returns false
        Person differentPerson = new PersonBuilder().withName(DIFFERENT_NAME).build();
        assertFalse(personInfoPanel.equals(new PersonInfoPanel(differentPerson)));
    }

    /**
     * Asserts that {@code personInfoPanel} displays the details of {@code expectedPerson} correctly and matches
     * {@code expectedId}.
     */
    private void assertPersonInfoDisplay(PersonInfoPanel personInfoPanel, ReadOnlyPerson expectedPerson) {
        guiRobot.pauseForHuman();

        PersonInfoPanelHandle personInfoPanelHandle = new PersonInfoPanelHandle(personInfoPanel.getRoot());

        // verify person details are displayed correctly
        assertInfoPanelDisplaysPerson(expectedPerson, personInfoPanelHandle);
    }
}
