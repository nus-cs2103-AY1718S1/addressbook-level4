package seedu.address.ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.ui.testutil.GuiTestAssert.assertPersonInfoDisplaysPerson;

import org.junit.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

//@@author pohjie
public class PersonInfoTest extends GuiUnitTest {

    @Test
    public void display() {
        /**
         * Although PersonInfo does not take in tags, we want to ensure that
         * the presence (or lack thereof) of tags does not affect its performance.
         */
        // no tags
        Person personWithNoTags = new PersonBuilder().withTags().build();
        PersonInfo personInfo = new PersonInfo(personWithNoTags);
        uiPartRule.setUiPart(personInfo);
        assertPersonInfoDisplaysPerson(personWithNoTags, personInfo);

        // with tags
        Person personWithTags = new PersonBuilder().build();
        personInfo = new PersonInfo(personWithTags);
        uiPartRule.setUiPart(personInfo);
        assertPersonInfoDisplaysPerson(personWithTags, personInfo);

        // changes made to Person reflects on PersonInfo (aside from attendance)
        guiRobot.interact(() -> {
            personWithTags.setName(ALICE.getName());
            personWithTags.setAddress(ALICE.getAddress());
            personWithTags.setEmail(ALICE.getEmail());
            personWithTags.setPhone(ALICE.getPhone());
        });
        assertPersonInfoDisplaysPerson(personWithTags, personInfo);
    }

    @Test
    public void equals() {
        Person person = new PersonBuilder().build();
        PersonInfo personInfo = new PersonInfo(person);

        // same object -> returns true
        assertTrue(personInfo.equals(personInfo));

        // null -> returns false
        assertFalse(personInfo == null);

        // different types -> returns false
        assertFalse(personInfo.equals(0));
    }
}
