package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalParcels.ALICE;
import static seedu.address.testutil.TypicalParcels.BOB;
import static seedu.address.testutil.TypicalParcels.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.parcel.ReadOnlyParcel;

//@author kennard123661
/**
 * Contains integration tests (interaction with the Model) for {@code ImportCommand}.
 */
public class ImportCommandIntegrationTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newParcel_success() throws Exception {
        List<ReadOnlyParcel> parcels = new ArrayList<>();
        List<ReadOnlyParcel> addedParcels = new ArrayList<>();
        List<ReadOnlyParcel> duplicateParcels = new ArrayList<>();

        parcels.add(ALICE);
        parcels.add(BOB);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addAllParcels(parcels, addedParcels, duplicateParcels);

        assertCommandSuccess(prepareCommand(parcels, model), model,
                String.format(ImportCommand.MESSAGE_SUCCESS, 1, 1, "\n  " + BOB.toString(), "\n  " + ALICE.toString()),
                expectedModel);
    }

    @Test
    public void execute_allParcelsAreDuplicates_throwsCommandException() {
        List<ReadOnlyParcel> parcels = model.getAddressBook().getParcelList();
        assertCommandFailure(prepareCommand(parcels, model), model, ImportCommand.MESSAGE_INVALID_DUPLICATE_PARCELS);
    }

    @Test
    public void execute_emptyParcelList_throwsCommandException() {
        List<ReadOnlyParcel> emptyParcelList = new ArrayList<>();
        List<ReadOnlyParcel> addedParcels = new ArrayList<>();
        List<ReadOnlyParcel> duplicateParcels = new ArrayList<>();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addAllParcels(emptyParcelList, addedParcels, duplicateParcels);

        assertCommandFailure(prepareCommand(emptyParcelList, model), model, ImportCommand.MESSAGE_INVALID_FILE_EMPTY);
    }

    /**
     * Generates a new {@code ImportCommand} which upon execution, adds all {@link ReadOnlyParcel}(s) in {@code parcels}
     *  into the {@link Model}.
     */
    private ImportCommand prepareCommand(List<ReadOnlyParcel> parcels, Model model) {
        ImportCommand command = new ImportCommand(parcels);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
