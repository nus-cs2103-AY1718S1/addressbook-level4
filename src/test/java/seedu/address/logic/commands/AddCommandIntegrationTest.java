package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPlaces.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.place.Place;
import seedu.address.testutil.PlaceBuilder;

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
    public void execute_newPlace_success() throws Exception {
        Place validPlace = new PlaceBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPlace(validPlace);

        assertCommandSuccess(prepareCommand(validPlace, model), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validPlace), expectedModel);
    }

    @Test
    public void execute_duplicatePlace_throwsCommandException() {
        Place placeInList = new Place(model.getAddressBook().getPlaceList().get(0));
        assertCommandFailure(prepareCommand(placeInList, model), model, AddCommand.MESSAGE_DUPLICATE_PLACE);
    }

    /**
     * Generates a new {@code AddCommand} which upon execution, adds {@code place} into the {@code model}.
     */
    private AddCommand prepareCommand(Place place, Model model) {
        AddCommand command = new AddCommand(place);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
