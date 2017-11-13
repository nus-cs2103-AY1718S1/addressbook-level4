package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPlaces.getTypicalAddressBook;

import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.place.ReadOnlyPlace;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteByNameCommand}.
 */

public class ExportCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addressBookExport_success() {
        ExportCommand exportCommand = prepareCommand();
        assertCommandSuccess(exportCommand, model, ExportCommand.MESSAGE_EXPORT_SUCCESS, model);
    }

    @Test
    public void execute_emptyAddressBookExport_throwsCommandException() throws Exception {
        List<ReadOnlyPlace> persons = model.getAddressBook().getPlaceList();
        while (!persons.isEmpty()) {
            ReadOnlyPlace personToRemove = persons.get(0);
            model.deletePlace(personToRemove);
        }
        ExportCommand exportCommand = prepareCommand();
        assertCommandFailure(exportCommand, model, ExportCommand.MESSAGE_EMPTY_ADDRESS_BOOK);
    }

    /**
     * Returns a {@code ExportCommand}.
     */
    private ExportCommand prepareCommand() {
        ExportCommand exportCommand = new ExportCommand();
        exportCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return exportCommand;
    }
}
