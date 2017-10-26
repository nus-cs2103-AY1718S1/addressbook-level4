package seedu.address.logic.commands;

import static junit.framework.TestCase.assertEquals;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LESSON;
import static seedu.address.testutil.TypicalLessons.MA1101R_L1;
import static seedu.address.testutil.TypicalLessons.MA1101R_L2;
import static seedu.address.testutil.TypicalLessons.MA1101R_T1;
import static seedu.address.testutil.TypicalLessons.MA1101R_T2;
import static seedu.address.testutil.TypicalLessons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.ListingUnit;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.ReadOnlyLesson;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ViewCommandTest {

    private Model model;
    private ViewCommand viewCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        viewCommand = new ViewCommand(INDEX_FIRST_LESSON);
        viewCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_viewSpecifiedLesson() {

        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);

        assertCommandSuccess(viewCommand, String.format(viewCommand.MESSAGE_VIEW_LESSON_SUCCESS,
                model.getFilteredLessonList().get(0)), Arrays.asList(MA1101R_L1));
    }

    @Test
    public void execute_viewAllLessons_withSpecifiedModule() {

        ListingUnit.setCurrentListingUnit(ListingUnit.MODULE);

        assertCommandSuccess(viewCommand, String.format(viewCommand.MESSAGE_VIEW_MODULE_SUCCESS,
                model.getFilteredLessonList().get(0).getCode()), Arrays.asList(MA1101R_L1,
                MA1101R_L2, MA1101R_T1, MA1101R_T2));
    }

    @Test
    public void execute_viewAllLesson_withSpecifiedLocation() {

        ListingUnit.setCurrentListingUnit(ListingUnit.LOCATION);

        assertCommandSuccess(viewCommand, String.format(viewCommand.MESSAGE_VIEW_LOCATION_SUCCESS,
                model.getFilteredLessonList().get(0).getLocation()), Arrays.asList(MA1101R_L1, MA1101R_L2));
    }


    /**
     * Asserts that {@code command} is successfully executed, and<br>
     * - the command feedback is equal to {@code expectedMessage}<br>
     * - the {@code FilteredList<ReadOnlyLesson>} is equal to {@code expectedList}<br>
     * - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(ViewCommand command, String expectedMessage, List<ReadOnlyLesson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = null;
        try {
            commandResult = command.execute();
        } catch (CommandException e) {
            assert false : "The exception is unexpected";
        }

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredLessonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }

}
