package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.TagMatchingPredicate;

//@@author zameschua
/**
 * Contains integration tests (interaction with the Model) and unit tests for SmsCommand.
 */
public class SmsCommandTest {
    private static final String STUB_USER_INPUT_ALL = "all";
    private static final String STUB_USER_INPUT_VALID = "owesMoney";
    private static final String STUB_USER_INPUT_INVALID = "invalid";

    private Model model;
    private Model originalModel;

    @Test
    public void execute_allTag_success() throws Exception {
        setupModel();
        int expectedCount = originalModel.getAddressBook().getPersonList().size();
        SmsCommand command = prepareCommand(STUB_USER_INPUT_ALL);

        model.updateFilteredPersonList(command.getPredicate());
        int actualCount = model.getFilteredPersonList().size();

        System.out.println("expected persons: " + expectedCount + ", actual persons: " + actualCount);
        assertListSize(expectedCount, actualCount);
        assertEqualList(originalModel.getFilteredPersonList(), model.getFilteredPersonList());
    }

    @Test
    // one valid tag
    public void  execute_oneValidTag_success() throws Exception {
        setupModel();
        ArrayList<String> tagList = new ArrayList<String>();
        tagList.add(STUB_USER_INPUT_VALID);
        TagMatchingPredicate predicate = new TagMatchingPredicate(tagList);
        ObservableList expectedPersons =  originalModel.getAddressBook().getPersonList().filtered(predicate);
        int expectedCount = expectedPersons.size();

        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        SmsCommand command = prepareCommand(STUB_USER_INPUT_VALID);
        model.updateFilteredPersonList(command.getPredicate());
        int actualCount = model.getFilteredPersonList().size();

        System.out.println("expected persons: " + expectedCount + ", actual persons: " + actualCount);
        assertListSize(expectedCount, actualCount);
        assertEqualList(expectedPersons, model.getFilteredPersonList());
    }

    @Test
    // no vaild tag
    public void  execute_oneInvalidTag_error() throws Exception {
        setupModel();
        ArrayList<String> tagList = new ArrayList<String>();
        tagList.add(STUB_USER_INPUT_INVALID);
        TagMatchingPredicate predicate = new TagMatchingPredicate(tagList);
        ObservableList expectedPersons =  originalModel.getAddressBook().getPersonList().filtered(predicate);
        int expectedCount = expectedPersons.size();

        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        SmsCommand command = prepareCommand(STUB_USER_INPUT_INVALID);
        model.updateFilteredPersonList(command.getPredicate());
        int actualCount = model.getFilteredPersonList().size();

        System.out.println("expected persons: " + expectedCount + ", actual persons: " + actualCount);
        assertListSize(expectedCount, actualCount);
        assert(model.getFilteredPersonList().isEmpty());
    }

    @Test
    // 1 vaild tag and 1 invalid tag
    public void  execute_mixedValidInvalidTag_error() throws Exception {
        setupModel();
        ArrayList<String> tagList = new ArrayList<String>();
        tagList.add(STUB_USER_INPUT_VALID);
        tagList.add(STUB_USER_INPUT_INVALID);
        TagMatchingPredicate predicate = new TagMatchingPredicate(tagList);
        ObservableList expectedPersons =  originalModel.getAddressBook().getPersonList().filtered(predicate);
        int expectedCount = expectedPersons.size();

        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        SmsCommand command = prepareCommand(STUB_USER_INPUT_INVALID + " " + STUB_USER_INPUT_VALID);
        model.updateFilteredPersonList(command.getPredicate());
        int actualCount = model.getFilteredPersonList().size();

        System.out.println("expected persons: " + expectedCount + ", actual persons: " + actualCount);
        assertListSize(expectedCount, actualCount);
        assert(!model.getFilteredPersonList().isEmpty());
        assertEqualList(expectedPersons, model.getFilteredPersonList());
    }

    /**
     * Helper method to setup a model {@code AddressBook}
     */
    private void setupModel() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        originalModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
    }

    /**
     * Parses {@code userInput} into a {@code SmsCommand}.
     */
    private SmsCommand prepareCommand(String userInput) {
        SmsCommand command =
                new SmsCommand(new TagMatchingPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    private void assertListSize(int ex, int act) {
        assert (ex == act);
    }

    private void assertEqualList(ObservableList<ReadOnlyPerson> ex, ObservableList<ReadOnlyPerson> act) {
        assert (act.containsAll(ex));
    }
}
