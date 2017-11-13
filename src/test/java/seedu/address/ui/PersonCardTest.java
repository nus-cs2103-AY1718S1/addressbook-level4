package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BILL;
import static seedu.address.testutil.TypicalPersons.JOHN;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysFavouriteStar;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysTodoCount;

import org.junit.Test;

import guitests.guihandles.FavouriteStarHandle;
import guitests.guihandles.PersonCardHandle;
import guitests.guihandles.TodoCountHandle;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
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
            personWithTags.setAddress(ALICE.getAddress());
            personWithTags.setEmail(ALICE.getEmail());
            personWithTags.setPhone(ALICE.getPhone());
            personWithTags.setTags(ALICE.getTags());
        });
        assertCardDisplay(personCard, personWithTags, 2);

        //@@author qihao27
        // is not favurited
        assertEquals(false, personWithNoTags.getFavourite());

        // is favourited
        Person personIsFavourited = new PersonBuilder().build();
        personIsFavourited.setFavourite(JOHN.getFavourite());
        assertEquals(true, personIsFavourited.getFavourite());

        personCard = new PersonCard(personIsFavourited, 3);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, personIsFavourited, 3);

        // no todolists
        assertEquals(0, personWithNoTags.getTodoItems().size());

        // with todolists
        Person personWithTodo = new PersonBuilder().build();
        personWithTodo.setTodoItems(BILL.getTodoItems());
        assertEquals(1, personWithTodo.getTodoItems().size());

        personCard = new PersonCard(personWithTodo, 4);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, personWithTodo, 4);
        //@@author
    }

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
        //@@author qihao27
        TodoCountHandle todoCountHandle = new TodoCountHandle(personCard.getRoot());
        FavouriteStarHandle favouriteStarHandle = new FavouriteStarHandle(personCard.getRoot());
        //@@author

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", personCardHandle.getId());

        // verify person details are displayed correctly
        assertCardDisplaysPerson(expectedPerson, personCardHandle);

        //@@author qihao27
        // verify todolist count is displayed correctly
        assertCardDisplaysTodoCount(expectedPerson, todoCountHandle);

        // verify favourite star is displayed correctly
        assertCardDisplaysFavouriteStar(expectedPerson, favouriteStarHandle);
        //@@author
    }
}
