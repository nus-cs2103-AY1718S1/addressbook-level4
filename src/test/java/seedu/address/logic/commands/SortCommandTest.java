package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalLessons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
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

public class SortCommandTest {

    private Model model;
    private Model expectedModel;
    private List<ReadOnlyLesson> expectedList;
    private String expectedMessage;
    private final ListingUnit beginningListingUnit = ListingUnit.getCurrentListingUnit();


    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedList = new ArrayList<>();
        expectedMessage = new String();
    }

    @Test
    public void execute_sortInModuleList_sortListByModule() {

        ListingUnit.setCurrentListingUnit(ListingUnit.MODULE);
        SortCommand sortByModule = new SortCommand();
        sortByModule.setData(model, new CommandHistory(), new UndoRedoStack());
        expectedMessage = SortCommand.MESSAGE_SORT_LESSON_SUCCESS;
        model.sortLessons();
        expectedList = model.getFilteredLessonList();
        assertCommandSuccess(sortByModule, expectedMessage, expectedList);

    }

    @Test
    public void execute_sortInLocationList_sortListByLocation() {

        ListingUnit.setCurrentListingUnit(ListingUnit.LOCATION);
        SortCommand sortByModule = new SortCommand();
        sortByModule.setData(model, new CommandHistory(), new UndoRedoStack());
        expectedMessage = SortCommand.MESSAGE_SORT_LESSON_SUCCESS;
        model.sortLessons();
        expectedList = model.getFilteredLessonList();
        assertCommandSuccess(sortByModule, expectedMessage, expectedList);

    }

    @Test
    public void execute_sortInLessonList_sortListByLesson() {

        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);
        SortCommand sortByModule = new SortCommand();
        sortByModule.setData(model, new CommandHistory(), new UndoRedoStack());
        expectedMessage = SortCommand.MESSAGE_SORT_LESSON_SUCCESS;
        model.sortLessons();
        expectedList = model.getFilteredLessonList();
        assertCommandSuccess(sortByModule, expectedMessage, expectedList);

    }


    /***
     * Asserts that {@code command} is successfully executed, and<br>
     * - the command feedback is equal to {@code expectedMessage}<br>
     * - the {@code FilteredList<ReadOnlyLesson>} is equal to {@code expectedList}<br>
     * - the {@code AddressBook} in model remains the same after executing the {@code command}
     ***/
    private void assertCommandSuccess(SortCommand command, String expectedMessage, List<ReadOnlyLesson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = null;
        try {
            commandResult = command.execute();
        } catch (CommandException e) {
            e.printStackTrace();
        }

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredLessonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }

    @After
    public void wrapUp() {
        ListingUnit.setCurrentListingUnit(beginningListingUnit);
    }
}
