package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.StorageUtil.getDummyStorage;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.TagsContainKeywordsPredicate;

//@@author marvinchin
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteByTagCommand}.
 */
public class DeleteByTagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_unfilteredList_success() throws Exception {
        List<String> tagsToDelete = Arrays.asList("friends");
        TagsContainKeywordsPredicate predicate = new TagsContainKeywordsPredicate(tagsToDelete);
        List<ReadOnlyPerson> personsToDelete = model.getFilteredPersonList().stream()
                .filter(predicate).collect(Collectors.toList());
        StringBuilder deletedPersons = new StringBuilder();
        for (ReadOnlyPerson person : personsToDelete) {
            deletedPersons.append("\n" + person.toString());
        }

        DeleteByTagCommand deleteCommand = prepareCommand(tagsToDelete);

        String expectedMessage = String.format(DeleteByTagCommand.MESSAGE_DELETE_PERSON_SUCCESS, deletedPersons);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        for (ReadOnlyPerson person : personsToDelete) {
            expectedModel.deletePerson(person);
        }

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        NameContainsKeywordsPredicate filterPredicate = new NameContainsKeywordsPredicate(Arrays.asList("Alice"));
        model.updateFilteredPersonList(filterPredicate);
        List<ReadOnlyPerson> filteredPersons = model.getFilteredPersonList();

        List<String> tagsToDelete = Arrays.asList("friends");
        TagsContainKeywordsPredicate deletePredicate = new TagsContainKeywordsPredicate(tagsToDelete);

        List<ReadOnlyPerson> personsToDelete = filteredPersons.stream()
                .filter(deletePredicate).collect(Collectors.toList());
        StringBuilder deletedPersons = new StringBuilder();
        for (ReadOnlyPerson person : personsToDelete) {
            deletedPersons.append("\n" + person.toString());
        }

        DeleteByTagCommand deleteCommand = prepareCommand(tagsToDelete);

        String expectedMessage = String.format(DeleteByTagCommand.MESSAGE_DELETE_PERSON_SUCCESS, deletedPersons);
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        for (ReadOnlyPerson person : personsToDelete) {
            expectedModel.deletePerson(person);
        }
        expectedModel.updateFilteredPersonList(filterPredicate);
        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        Set<String> firstDeleteTagSet = new HashSet<>(Arrays.asList("friends"));
        Set<String> secondDeleteTagSet = new HashSet<>(Arrays.asList("family"));
        DeleteByTagCommand deleteFirstCommand = new DeleteByTagCommand(firstDeleteTagSet);
        DeleteByTagCommand deleteSecondCommand = new DeleteByTagCommand(secondDeleteTagSet);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteByTagCommand deleteFirstCommandCopy = new DeleteByTagCommand(firstDeleteTagSet);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Returns a {@code DeleteByTagCommand} with the parameter {@code index}.
     */
    private DeleteByTagCommand prepareCommand(List<String> tags) {
        HashSet<String> tagSet = new HashSet<>(tags);
        DeleteByTagCommand deleteCommand = new DeleteByTagCommand(tagSet);
        deleteCommand.setData(model, getDummyStorage(), new CommandHistory(), new UndoRedoStack());
        return deleteCommand;
    }
}
