package seedu.address.logic.commands.imports;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_YEAR_OFFSET_NUSMODS_URL;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_YEAR_START_NUSMODS_URL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NUSMODS_URL;
import static seedu.address.logic.commands.imports.ImportNusmodsCommand.INVALID_URL;
import static seedu.address.logic.commands.imports.ImportNusmodsCommand.MESSAGE_SUCCESS;
import static seedu.address.logic.commands.imports.ImportNusmodsCommand.YEAR_OFFSET_BY_ONE;

import java.net.URL;

import org.junit.BeforeClass;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.stub.ModelStub;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.person.exceptions.DuplicateEventException;
import seedu.address.model.property.PropertyManager;

public class ImportNusmodsCommandTest {
    private static ImportCommand validCommand;

    @BeforeClass
    public static void setUp() throws Exception {
        validCommand = new ImportNusmodsCommand(new URL(VALID_NUSMODS_URL));
    }

    @Test
    public void createCommand_succeed_checkCorrectness() throws Exception {
        String expected = "AY2017-2018 Semester 1";
        assertEquals(expected, validCommand.toString());
    }

    @Test
    public void createCommand_wrongSemesterInformation_expectException() throws Exception {
        assertConstructorFailure(INVALID_YEAR_START_NUSMODS_URL, String.format(INVALID_URL, ""));
        assertConstructorFailure(INVALID_YEAR_OFFSET_NUSMODS_URL, String.format(INVALID_URL, YEAR_OFFSET_BY_ONE));
    }

    @Test
    public void execute_succeed_checkCorrectness() throws Exception {
        Model modelStub = new ImportNusmodsCommandModelStub();
        validCommand.setData(modelStub, new CommandHistory(), new UndoRedoStack());
        CommandResult result = validCommand.execute();
        ImportNusmodsCommandModelStub stubCount = (ImportNusmodsCommandModelStub) modelStub;
        assertEquals(1, stubCount.getEventCount());
        assertEquals(String.format(MESSAGE_SUCCESS, 1), result.feedbackToUser);
    }

    /**
     * Constructs an {@link ImportNusmodsCommand} and checks its failure and corresponding error message.
     */
    private void assertConstructorFailure(String input, String expectedMessage) throws Exception {
        ImportCommand command = null;
        try {
            command = new ImportNusmodsCommand(new URL(input));
        } catch (ParseException pe) {
            assertEquals(expectedMessage, pe.getMessage());
        }
        assertNull(command);
    }

    private class ImportNusmodsCommandModelStub extends ModelStub {
        private int countEvent = 0;

        ImportNusmodsCommandModelStub() {
            PropertyManager.initializePropertyManager();
        }

        @Override
        public void addEvent(ReadOnlyEvent event) throws DuplicateEventException {
            countEvent++;
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }

        int getEventCount() {
            return countEvent;
        }
    }
}
