package seedu.address.logic.commands;
//@@author pjunwei95
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.DeleteTagCommand.DeleteTagDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.DeleteTagDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for DeleteTagCommand.
 */
public class DeleteTagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Person firstPerson = new Person(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        DeleteTagDescriptor descriptor = new DeleteTagDescriptorBuilder(firstPerson).build();
        DeleteTagCommand deleteTagCommand = prepareCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(deleteTagCommand, model, DeleteTagCommand.MESSAGE_NOT_EXISTING_TAGS);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showFirstPersonOnly(model);

        // edit person in filtered list into a duplicate in address book
        ReadOnlyPerson personInList = model.getAddressBook().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        DeleteTagCommand deleteTagCommand = prepareCommand(INDEX_FIRST_PERSON,
                new DeleteTagDescriptorBuilder(personInList).build());

        assertCommandFailure(deleteTagCommand, model, DeleteTagCommand.MESSAGE_NOT_EXISTING_TAGS);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteTagDescriptor descriptor = new DeleteTagDescriptorBuilder().withTags(VALID_TAG_HUSBAND).build();
        DeleteTagCommand deleteTagCommand = prepareCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(deleteTagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Delete the tags of  filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteTagCommand deleteTagCommand = prepareCommand(outOfBoundIndex,
                new DeleteTagDescriptorBuilder().withTags(VALID_TAG_HUSBAND).build());

        assertCommandFailure(deleteTagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }


    @Test
    public void checkTagDeletedPerson() {

        ReadOnlyPerson personInList = model.getAddressBook().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        DeleteTagDescriptor descriptor = new DeleteTagDescriptorBuilder().withTags(VALID_TAG_HUSBAND)
                .build();
        DeleteTagCommand command = new DeleteTagCommand(INDEX_SECOND_PERSON, descriptor);
        ReadOnlyPerson changed = command.createTagDeletedPerson(personInList, descriptor);
        assertTrue(!changed.getTags().isEmpty());
    }



    @Test
    public void equals() {
        final DeleteTagCommand standardCommand = new DeleteTagCommand(INDEX_FIRST_PERSON, TAG_DESC_AMY);

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new DeleteTagCommand(INDEX_SECOND_PERSON, TAG_DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new DeleteTagCommand(INDEX_FIRST_PERSON, TAG_DESC_BOB)));
    }

    /**
     * Returns an {@code DeleteTagCommand} with parameters {@code index} and {@code descriptor}
     */
    private DeleteTagCommand prepareCommand(Index index, DeleteTagDescriptor descriptor) {
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(index, descriptor);
        deleteTagCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteTagCommand;
    }
}
