package seedu.address.logic.commands.imports;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_YEAR_OFFSET_NUSMODS_URL;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_YEAR_START_NUSMODS_URL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NUSMODS_URL;
import static seedu.address.logic.commands.imports.ImportNusmodsCommand.INVALID_URL;
import static seedu.address.logic.commands.imports.ImportNusmodsCommand.YEAR_OFFSET_BY_ONE;

import java.net.URL;

import org.junit.BeforeClass;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.stub.ModelStub;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.person.exceptions.DuplicateEventException;

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
        validCommand.setData(new ImportNusmodsCommandModelStub(), new CommandHistory(), new UndoRedoStack());
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
        @Override
        public void addEvent(ReadOnlyEvent event) throws DuplicateEventException {
            fail("This method should not be called.");
        }
    }
}
