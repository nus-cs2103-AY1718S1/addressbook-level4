//@@author limyongsong
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LINK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LINK_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Link;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for LinkCommand.
 */
public class LinkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addLink_success() throws Exception {
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withLink("twitter.com/").build();

        LinkCommand linkCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getLink().value);

        String expectedMessage = String.format(LinkCommand.MESSAGE_ADD_LINK_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(linkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteLink_success() throws Exception {
        Person editedPerson = new Person(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        editedPerson.setLink(new Link(""));

        LinkCommand linkCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getLink().toString());

        String expectedMessage = String.format(LinkCommand.MESSAGE_DELETE_LINK_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(linkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        LinkCommand linkCommand = prepareCommand(outOfBoundIndex, VALID_LINK_BOB);

        assertCommandFailure(linkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidLink_failure() throws Exception {
        String invalidLink = "nolink.com";
        LinkCommand linkCommand = prepareCommand(INDEX_FIRST_PERSON, invalidLink);

        assertCommandFailure(linkCommand, model, Messages.MESSAGE_INVALID_LINK_FORMAT);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws Exception {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        LinkCommand linkCommand = prepareCommand(outOfBoundIndex, VALID_LINK_BOB);

        assertCommandFailure(linkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final LinkCommand standardCommand = new LinkCommand(INDEX_FIRST_PERSON, new Link(VALID_LINK_AMY));

        // same values -> returns true
        LinkCommand commandWithSameValues = new LinkCommand(INDEX_FIRST_PERSON, new Link(VALID_LINK_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new LinkCommand(INDEX_SECOND_PERSON, new Link(VALID_LINK_AMY))));

        // different links -> returns false
        assertFalse(standardCommand.equals(new LinkCommand(INDEX_FIRST_PERSON, new Link(VALID_LINK_BOB))));
    }

    /**
     * Returns an {@code LinkCommand} with parameters {@code index} and {@code link}
     */
    private LinkCommand prepareCommand(Index index, String link) {
        LinkCommand linkCommand = new LinkCommand(index, new Link(link));
        linkCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return linkCommand;
    }
}
