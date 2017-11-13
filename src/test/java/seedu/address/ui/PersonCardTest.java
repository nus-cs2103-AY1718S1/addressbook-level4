package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AVATAR_IMAGE_URL_ONE;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.storage.util.ProcessImageUtil;
import seedu.address.testutil.PersonBuilder;

public class PersonCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Person personWithNoTags = new PersonBuilder().withTags(new String[0]).build();
        PersonCard personCard = new PersonCard(personWithNoTags, 1);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, personWithNoTags, 1);

        // with tags
        Person personWithTags = new PersonBuilder().build();
        personCard = new PersonCard(personWithTags, 2);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, personWithTags, 2);

        // changes made to Person reflects on card
        guiRobot.interact(() -> {
            personWithTags.setName(ALICE.getName());
            personWithTags.setPhone(ALICE.getPhone());
            personWithTags.setTags(ALICE.getTags());
        });
        assertCardDisplay(personCard, personWithTags, 2);
    }

    //@@author karrui
    @Test
    public void avatarCheck() {
        Person personWithNoAvatar = new PersonBuilder().withAvatar("").build();
        PersonCard personCard = new PersonCard(personWithNoAvatar, 1);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, personWithNoAvatar, 1);

        Person personWithAvatar = null;
        try {
            personWithAvatar = new PersonBuilder().withAvatar(VALID_AVATAR_IMAGE_URL_ONE).build();
            personCard = new PersonCard(personWithAvatar, 1);
            uiPartRule.setUiPart(personCard);
            assertCardDisplay(personCard, personWithAvatar, 1);
        } finally {
            if (personWithAvatar != null) {
                ProcessImageUtil.removeImageFromStorage(personWithAvatar.getAvatar().path); // cleanup
            }
        }
    }

    /**
     * Asserts that {@code personCard} displays the details of {@code expectedPerson} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(PersonCard personCard, ReadOnlyPerson expectedPerson, int expectedId) {
        guiRobot.pauseForHuman();

        PersonCardHandle personCardHandle = new PersonCardHandle(personCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", personCardHandle.getId());

        // verify person details are displayed correctly
        assertCardDisplaysPerson(expectedPerson, personCardHandle);
    }
}
