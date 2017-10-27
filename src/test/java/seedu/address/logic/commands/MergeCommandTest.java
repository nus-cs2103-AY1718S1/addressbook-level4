package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.email.Email;
import seedu.address.email.EmailManager;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Contains integration test (interaction with Model) for {@code MergeCommand}
 */
public class MergeCommandTest {
    private final String TEST_DATA_ERROR_FILE_PATH = "./src/test/data/XmlAddressBookStorageTest/DataConversionError.xml";
    private final String TEST_NEW_FILE_PATH = "./src/test/data/XmlAddressBookStorageTest/TestNewFile.xml";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager();
    private Email emailManager = new EmailManager();
    private Logic logic = new LogicManager(model, emailManager);

    @Test
    public void equals() {
        MergeCommand mergeCommandFirst = new MergeCommand("./dummy/path/file1.txt");
        MergeCommand mergeCommandSecond = new MergeCommand("./dummy/path/file2.txt");

        // same object -> returns true
        assertTrue(mergeCommandFirst.equals(mergeCommandFirst));

        // same values -> returns true
        MergeCommand mergeCommandFirstCopy = new MergeCommand("./dummy/path/file1.txt");
        assertTrue(mergeCommandFirst.equals(mergeCommandFirstCopy));

        // different types -> returns false
        assertFalse(mergeCommandFirst.equals(1));

        // null -> returns false
        assertFalse(mergeCommandFirst.equals(null));

        // different file path -> returns false
        assertFalse(mergeCommandFirst.equals(mergeCommandSecond));
    }

    @Test
    public void merge_success() {
        // uses model and logic stubs to ensure testing files do not merge into actual data
        ModelStubAcceptingMergePath modelStub = new ModelStubAcceptingMergePath();
        Logic logicStub = new LogicManager(modelStub, emailManager);

        String mergeCommand = MergeCommand.COMMAND_WORD + " " + TEST_NEW_FILE_PATH;
        assertCommandSuccess(mergeCommand, MergeCommand.MESSAGE_SUCCESS, logicStub);
    }

    @Test
    public void merge_fileNotFound_failure() {
        String mergeCommand = MergeCommand.COMMAND_WORD + " " + "./dummy/path/file.xml";
        assertCommandFailure(mergeCommand, CommandException.class, MergeCommand.MESSAGE_FILE_NOT_FOUND, logic);
    }

    @Test
    public void merge_dataConversionError_failure() {
        String mergeCommand = MergeCommand.COMMAND_WORD + " " + TEST_DATA_ERROR_FILE_PATH;
        assertCommandFailure(mergeCommand, CommandException.class, MergeCommand.MESSAGE_DATA_CONVERSION_ERROR, logic);
    }

    /**
     * Executes the command, confirms that no exceptions are thrown and that the result message is correct.
     * Also confirms that {@code expectedModel} is as specified.
     *
     * @see #assertCommandBehavior(Class, String, String, Logic)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage, Logic expectedLogic) {
        assertCommandBehavior(null, inputCommand, expectedMessage, expectedLogic);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     *
     * @see #assertCommandBehavior(Class, String, String, Logic)
     */
    private void assertCommandFailure(String inputCommand, Class<?> expectedException, String expectedMessage, Logic expectedLogic) {
        assertCommandBehavior(expectedException, inputCommand, expectedMessage, expectedLogic);
    }

    private void assertCommandBehavior(Class<?> expectedException, String inputCommand,
                                       String expectedMessage, Logic expectedLogic) {

        try {
            CommandResult result = expectedLogic.execute(inputCommand);
            assertEquals(expectedException, null);
            assertEquals(expectedMessage, result.feedbackToUser);
        } catch (CommandException | ParseException e) {
            assertEquals(expectedException, e.getClass());
            assertEquals(expectedMessage, e.getMessage());
        }

    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredListToShowAll() {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void deleteTag(Tag tag) {
            fail("This method should not be called.");
        }

        @Override
        public void mergeAddressBook(ObservableList<ReadOnlyPerson> newFilePersonList) {
            fail("This method should not be called.");
        }
    }

    /**
     * A Model stub that always accept the merge path given.
     */
    private class ModelStubAcceptingMergePath extends MergeCommandTest.ModelStub {
        ObservableList<ReadOnlyPerson> mergeFilePersonList;

        @Override
        public void mergeAddressBook(ObservableList<ReadOnlyPerson> newFilePersonList) {
            mergeFilePersonList = newFilePersonList;
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
