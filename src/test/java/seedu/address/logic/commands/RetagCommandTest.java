//@@author duyson98

package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.RetagCommand.MESSAGE_SUCCESS;
import static seedu.address.logic.commands.RetagCommand.MESSAGE_TAG_NOT_FOUND;
import static seedu.address.testutil.TypicalAccounts.getTypicalDatabase;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

public class RetagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalDatabase(), new UserPrefs());

    @Test
    public void execute_success() throws Exception {
        Tag targetTag = new Tag("friends");
        Tag newTag = new Tag("enemies");
        RetagCommand command = prepareCommand(targetTag, newTag);

        String expectedMessage = String.format(MESSAGE_SUCCESS, targetTag.toString(), newTag.toString());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                model.getDatabase(), new UserPrefs());
        for (ReadOnlyPerson person : expectedModel.getFilteredPersonList()) {
            Person retaggedPerson = new Person(person);
            UniqueTagList updatedTags = new UniqueTagList(retaggedPerson.getTags());
            if (updatedTags.contains(targetTag)) {
                updatedTags.remove(targetTag);
                updatedTags.add(newTag);
            }
            retaggedPerson.setTags(updatedTags.toSet());
            expectedModel.updatePerson(person, retaggedPerson);
        }
        expectedModel.deleteUnusedTag(targetTag);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_tagNotFound() throws Exception {
        Tag targetTag = new Tag("enemies");
        Tag newTag = new Tag("friends");
        RetagCommand command = prepareCommand(targetTag, newTag);

        String expectedMessage = String.format(MESSAGE_TAG_NOT_FOUND, targetTag.toString());

        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void equals() throws Exception {
        Tag targetTag = new Tag("enemies");
        Tag newTag = new Tag("friends");
        RetagCommand command = new RetagCommand(targetTag, newTag);

        // same value -> returns true
        assertTrue(command.equals(new RetagCommand(targetTag, newTag)));

        // same object -> returns true
        assertTrue(command.equals(command));

        // null -> returns false
        assertFalse(command.equals(null));

        // different type -> returns false
        assertFalse(command.equals(new ClearCommand()));

        // different tag name -> returns false
        Tag anotherTag = new Tag("partners");
        assertFalse(command.equals(new RetagCommand(targetTag, anotherTag)));
    }

    /**
     * Parses {@code userInput} into a {@code RetagCommand}.
     */
    private RetagCommand prepareCommand(Tag targetTag, Tag newTag) throws Exception {
        RetagCommand command = new RetagCommand(targetTag, newTag);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

}
