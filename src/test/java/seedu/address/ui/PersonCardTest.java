package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PersonBuilder;

public class PersonCardTest extends GuiUnitTest {

    /**
     * Tests if a person's attributes is binded in each individual person card
     */
    @Test
    public void display() {
        Person personWithRightAttributes = new PersonBuilder().withFormClass("6E1").withName("Alice Pauline")
                .withPhone("97979797").withTags().build();
        PersonCard personCard = new PersonCard(personWithRightAttributes, 1);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, personWithRightAttributes, 1);
    }

    /**
     * Tests if the person is initialised with tags on the individual person card
     */
    @Test
    public void displayPerson() {
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
            personWithTags.setAddress(ALICE.getAddress());
            personWithTags.setFormClass(ALICE.getFormClass());
            personWithTags.setGrades(ALICE.getGrades());
            personWithTags.setEmail(ALICE.getEmail());
            personWithTags.setPostalCode(ALICE.getPostalCode());
            personWithTags.setRemark(ALICE.getRemark());
            personWithTags.setTags(ALICE.getTags());
        });
        assertCardDisplay(personCard, personWithTags, 2);
    }
    //@@author Lenaldnwj
    @Test
    public void obtainTagColors() {

        //Check if same tags are assigned the same color.
        Person marcusPersonWithTags = new PersonBuilder().withName("Marcus").withTags("Basketballer").build();
        PersonCard marcusPersonCard = new PersonCard(marcusPersonWithTags, 3);

        Person jamiePersonWithTags = new PersonBuilder().withName("Jamie").withTags("Basketballer").build();
        PersonCard jamiePersonCard = new PersonCard(jamiePersonWithTags, 4);

        Person lennyPersonWithTags = new PersonBuilder().withName("Lenny").withTags("Basketballer").build();
        PersonCard lennyPersonCard = new PersonCard(lennyPersonWithTags, 5);

        assertEquals(marcusPersonCard.obtainTagColors(marcusPersonWithTags.getTags().toString()),
                jamiePersonCard.obtainTagColors(jamiePersonWithTags.getTags().toString()));

        assertEquals(marcusPersonCard.obtainTagColors(marcusPersonWithTags.getTags().toString()),
                lennyPersonCard.obtainTagColors(lennyPersonWithTags.getTags().toString()));

        assertEquals(lennyPersonCard.obtainTagColors(lennyPersonWithTags.getTags().toString()),
                jamiePersonCard.obtainTagColors(jamiePersonWithTags.getTags().toString()));


        //Check if different tags are assigned different color.
        Person lunaPersonWithTags = new PersonBuilder().withName("Luna").withTags("Quiet").build();
        PersonCard lunaPersonCard = new PersonCard(lunaPersonWithTags, 6);

        Person potterPersonWithTags = new PersonBuilder().withName("Potter").withTags("Noisy").build();
        PersonCard potterPersonCard = new PersonCard(potterPersonWithTags, 7);

        Person ronPersonWithTags = new PersonBuilder().withName("Ron").withTags("Happy").build();
        PersonCard ronPersonCard = new PersonCard(ronPersonWithTags, 8);

        assertNotEquals(lunaPersonCard.obtainTagColors(lunaPersonWithTags.getTags().toString()),
                potterPersonCard.obtainTagColors(potterPersonWithTags.getTags().toString()));

        assertNotEquals(ronPersonCard.obtainTagColors(ronPersonWithTags.getTags().toString()),
                lunaPersonCard.obtainTagColors(lunaPersonWithTags.getTags().toString()));

        assertNotEquals(ronPersonCard.obtainTagColors(ronPersonWithTags.getTags().toString()),
                potterPersonCard.obtainTagColors(potterPersonWithTags.getTags().toString()));

        //Check if assigned tag color is added into the Arraylist of usedColors.
        Person personWithTags = new PersonBuilder().build();
        PersonCard personCard = new PersonCard(personWithTags, 2);

        assertTrue(personCard.getUsedColor().contains(personCard.getAssignedTagColor()));
        assertTrue(ronPersonCard.getUsedColor().contains(ronPersonCard.getAssignedTagColor()));
        assertTrue(lunaPersonCard.getUsedColor().contains(lunaPersonCard.getAssignedTagColor()));
        assertTrue(potterPersonCard.getUsedColor().contains(potterPersonCard.getAssignedTagColor()));
        assertTrue(jamiePersonCard.getUsedColor().contains(jamiePersonCard.getAssignedTagColor()));
        assertTrue(marcusPersonCard.getUsedColor().contains(marcusPersonCard.getAssignedTagColor()));
        assertTrue(lennyPersonCard.getUsedColor().contains(lennyPersonCard.getAssignedTagColor()));

    }
    //@@author Lenaldnwj

    @Test
    public void equals() {
        Person person = new PersonBuilder().build();
        PersonCard personCard = new PersonCard(person, 0);

        // same person, same index -> returns true
        PersonCard copy = new PersonCard(person, 0);
        assertTrue(personCard.equals(copy));

        // same object -> returns true
        assertTrue(personCard.equals(personCard));

        // null -> returns false
        assertFalse(personCard.equals(null));

        // different types -> returns false
        assertFalse(personCard.equals(0));

        // different person, same index -> returns false
        Person differentPerson = new PersonBuilder().withName("differentName").build();
        assertFalse(personCard.equals(new PersonCard(differentPerson, 0)));

        // same person, different index -> returns false
        assertFalse(personCard.equals(new PersonCard(person, 1)));
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
