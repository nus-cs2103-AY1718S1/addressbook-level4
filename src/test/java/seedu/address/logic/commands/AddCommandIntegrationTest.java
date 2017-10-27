package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalParcels.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.parcel.Parcel;
import seedu.address.model.parcel.Status;
import seedu.address.testutil.ParcelBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newParcel_success() throws Exception {
        Parcel validParcel = new ParcelBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addParcel(validParcel);
        expectedModel.maintainSorted();
        assertCommandSuccess(prepareCommand(validParcel, model), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validParcel), expectedModel);
    }

    @Test
    public void execute_duplicateParcel_throwsCommandException() {
        Parcel parcelInList = new Parcel(model.getAddressBook().getParcelList().get(0));
        assertCommandFailure(prepareCommand(parcelInList, model), model, AddCommand.MESSAGE_DUPLICATE_PARCEL);

        // Adding a parcel from a undelivered list
        Parcel parcelInUndeliveredList = new Parcel(model.getFilteredUndeliveredParcelList().get(0));
        assertCommandFailure(prepareCommand(parcelInUndeliveredList, model), model,
                AddCommand.MESSAGE_DUPLICATE_PARCEL);

        // Adding a parcel from a delivered list
        Parcel parcelInDeliveredList = new Parcel(model.getFilteredDeliveredParcelList().get(0));
        assertCommandFailure(prepareCommand(parcelInDeliveredList, model), model,
                AddCommand.MESSAGE_DUPLICATE_PARCEL);

        // Adding the same parcel with
        Parcel parcelInListWithDifferentStatus = new Parcel(model.getAddressBook().getParcelList().get(0));
        parcelInListWithDifferentStatus.setStatus(Status.COMPLETED);
        assertCommandFailure(prepareCommand(parcelInDeliveredList, model), model,
                AddCommand.MESSAGE_DUPLICATE_PARCEL);
    }

    /**
     * Generates a new {@code AddCommand} which upon execution, adds {@code parcel} into the {@code model}.
     */
    private AddCommand prepareCommand(Parcel parcel, Model model) {
        AddCommand command = new AddCommand(parcel);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
