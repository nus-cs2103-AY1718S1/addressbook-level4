package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.email.Email;
import seedu.address.email.EmailManager;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

/**
 * Contains integration test (interaction with Logic) for {@code MergeCommand}
 */
public class MergeCommandTest {
    private final String TEST_DEFAULT_FILE_PATH = "./src/test/data/XmlAddressBookStorageTest/TestAddressBook.xml";
    private final String TEST_NEW_FILE_PATH = "./src/test/data/XmlAddressBookStorageTest/TestNewFile.xml";
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager();
    private Email emailManager = new EmailManager();
    private Logic logic = new LogicManager(model, emailManager, TEST_DEFAULT_FILE_PATH);
    
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
        String mergeCommand = MergeCommand.COMMAND_WORD + " " + TEST_NEW_FILE_PATH;
        assertCommandSuccess(mergeCommand, MergeCommand.MESSAGE_SUCCESS, model, logic);
    }

    /**
     * Executes the command, confirms that no exceptions are thrown and that the result message is correct.
     * Also confirms that {@code expectedModel} is as specified.
     * @see #assertCommandBehavior(Class, String, String, Model, Logic)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage, Model expectedModel, Logic expectedLogic) {
        assertCommandBehavior(null, inputCommand, expectedMessage, expectedModel, expectedLogic);
    }
    
    private void assertCommandBehavior(Class<?> expectedException, String inputCommand,
                                       String expectedMessage, Model expectedModel, Logic expectedLogic) {

        try {
            CommandResult result = logic.execute(inputCommand);
            assertEquals(expectedException, null);
            assertEquals(expectedMessage, result.feedbackToUser);
        } catch (CommandException | ParseException e) {
            assertEquals(expectedException, e.getClass());
            assertEquals(expectedMessage, e.getMessage());
        }

        assertEquals(expectedModel, model);
        assertEquals(expectedLogic, logic);
    }
}
