package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalLessons.MA1101R_L1;
import static seedu.address.testutil.TypicalLessons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.ListingUnit;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.ReadOnlyLesson;
import seedu.address.model.module.predicates.LessonContainsKeywordsPredicate;
import seedu.address.model.module.predicates.LocationContainsKeywordsPredicate;
import seedu.address.model.module.predicates.MarkedLessonContainsKeywordsPredicate;
import seedu.address.model.module.predicates.ModuleContainsKeywordsPredicate;

//@@author angtianlannus
/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model;
    private Model expectedModel;
    private List<String> keywords;
    private List<ReadOnlyLesson> expectedList;
    private String expectedMessage;
    private final ListingUnit beginningListingUnit = ListingUnit.getCurrentListingUnit();


    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        keywords = new ArrayList<>();
        expectedList = new ArrayList<>();
        expectedMessage = new String();
    }


    @Test
    public void equals() {

        List<String> keywordOne = new ArrayList<String>() {
        };
        List<String> keywordTwo = new ArrayList<String>() {
        };

        keywordOne.add("111");
        keywordOne.add("222");
        keywordOne.add("333");

        keywordTwo.add("aaa");
        keywordTwo.add("bbb");
        keywordTwo.add("bbb");

        FindCommand firstFindCommand = new FindCommand(keywordOne);
        FindCommand secondFindCommand = new FindCommand(keywordTwo);

        // same object -> returns true
        assertTrue(firstFindCommand.equals(firstFindCommand));

        // same values -> returns true
        FindCommand firstFindCommandCopy = new FindCommand(keywordOne);
        assertTrue(firstFindCommand.equals(firstFindCommandCopy));

        // different types -> returns false
        assertFalse(firstFindCommand.equals(1));

        // null -> returns false
        assertFalse(firstFindCommand.equals(null));

        // different find command -> returns false
        assertFalse(firstFindCommand.equals(secondFindCommand));
    }

    @Test
    public void execute_zeroKeywords_noLessonFound() {
        String expectedMessage = String.format(FindCommand.MESSAGE_SUCCESS);
        FindCommand command = new FindCommand(keywords);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_findByValidModuleCode_moduleFound() {
        ListingUnit.setCurrentListingUnit(ListingUnit.MODULE);
        keywords.add("MA1101");
        FindCommand findByModule = new FindCommand(keywords);
        findByModule.setData(model, new CommandHistory(), new UndoRedoStack());
        expectedMessage = FindCommand.MESSAGE_SUCCESS;
        model.updateFilteredLessonList(new ModuleContainsKeywordsPredicate(keywords));
        expectedList = model.getFilteredLessonList();

        assertCommandSuccess(findByModule, expectedMessage, expectedList);
    }

    @Test
    public void execute_findByValidLocation_locationFound() {
        ListingUnit.setCurrentListingUnit(ListingUnit.LOCATION);
        keywords.add("LT27");
        FindCommand findByLocation = new FindCommand(keywords);
        findByLocation.setData(model, new CommandHistory(), new UndoRedoStack());
        expectedMessage = FindCommand.MESSAGE_SUCCESS;
        model.updateFilteredLessonList(new LocationContainsKeywordsPredicate(keywords));
        expectedList = model.getFilteredLessonList();

        assertCommandSuccess(findByLocation, expectedMessage, expectedList);
    }

    @Test
    public void execute_findByValidLessonDetails_lessonsFound() {
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);
        model.setViewingPanelAttribute("module");
        model.setCurrentViewingLesson(MA1101R_L1);
        keywords.add("FRI");
        FindCommand findByLesson = new FindCommand(keywords);
        findByLesson.setData(model, new CommandHistory(), new UndoRedoStack());
        expectedMessage = FindCommand.MESSAGE_SUCCESS;
        model.updateFilteredLessonList(new LessonContainsKeywordsPredicate(keywords, MA1101R_L1, "module"));
        expectedList = model.getFilteredLessonList();

        assertCommandSuccess(findByLesson, expectedMessage, expectedList);
    }

    @Test
    public void execute_findByValidLessonDetailsInMarkedList_noLessonsFound() {
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);
        model.setViewingPanelAttribute("marked");
        model.setCurrentViewingLesson(MA1101R_L1);
        keywords.add("FRI");
        FindCommand findByMarkedLesson = new FindCommand(keywords);
        findByMarkedLesson.setData(model, new CommandHistory(), new UndoRedoStack());
        expectedMessage = FindCommand.MESSAGE_SUCCESS;
        model.updateFilteredLessonList(new MarkedLessonContainsKeywordsPredicate(keywords));
        expectedList = model.getFilteredLessonList();

        assertCommandSuccess(findByMarkedLesson, expectedMessage, expectedList);
    }
    //@@author

    /***
     * Asserts that {@code command} is successfully executed, and<br>
     * - the command feedback is equal to {@code expectedMessage}<br>
     * - the {@code FilteredList<ReadOnlyLesson>} is equal to {@code expectedList}<br>
     * - the {@code AddressBook} in model remains the same after executing the {@code command}
     ***/
    private void assertCommandSuccess(FindCommand command, String expectedMessage, List<ReadOnlyLesson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredLessonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }

    //@@author angtianlannus
    @After
    public void wrapUp() {
        ListingUnit.setCurrentListingUnit(beginningListingUnit);
        model.setViewingPanelAttribute("default");
    }
}
