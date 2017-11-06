package systemtests;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.model.ListingUnit.LESSON;
import static seedu.address.model.ListingUnit.LOCATION;
import static seedu.address.model.ListingUnit.MODULE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LESSON;
import static seedu.address.ui.testutil.GuiTestAssert.assertListMatching;

import org.junit.After;
import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.model.ListingUnit;
import seedu.address.model.Model;
import seedu.address.model.module.ReadOnlyLesson;
import seedu.address.model.module.predicates.FixedCodePredicate;
import seedu.address.model.module.predicates.MarkedListPredicate;
import seedu.address.model.module.predicates.UniqueLocationPredicate;
import seedu.address.model.module.predicates.UniqueModuleCodePredicate;

//@@author angtianlannus
public class SortCommandSystemTest extends AddressBookSystemTest {

    private final ListingUnit beginningListingUnit = ListingUnit.getCurrentListingUnit();

    @Test
    public void sortAnyAttribute() {
        Model model = getModel();
        String command;
        String expectedResultMessage;

        command = SortCommand.COMMAND_WORD;
        expectedResultMessage = SortCommand.MESSAGE_SORT_LESSON_SUCCESS;
        model.sortLessons();
        assertCommandSuccess(command, expectedResultMessage, model);

        /* Case : capped sort command word -> rejected */
        assertCommandFailure("SORT", MESSAGE_UNKNOWN_COMMAND);

        /* Case : mixed cap command word -> rejected */
        assertCommandFailure("SoRt", MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void sortByModule() {
        Model model = getModel();
        String command;
        String expectedResultMessage;

        command = SortCommand.COMMAND_WORD;
        expectedResultMessage = SortCommand.MESSAGE_SORT_LESSON_SUCCESS;
        ListingUnit.setCurrentListingUnit(MODULE);
        model.updateFilteredLessonList(new UniqueModuleCodePredicate(model.getUniqueCodeSet()));
        model.sortLessons();
        assertCommandSuccessSortByModule(command, expectedResultMessage, model);
    }

    @Test
    public void sortByLocation() {
        Model model = getModel();
        String command;
        String expectedResultMessage;

        command = SortCommand.COMMAND_WORD;
        expectedResultMessage = SortCommand.MESSAGE_SORT_LESSON_SUCCESS;
        ListingUnit.setCurrentListingUnit(LOCATION);
        model.updateFilteredLessonList(new UniqueLocationPredicate(model.getUniqueLocationSet()));
        model.sortLessons();
        assertCommandSuccessSortByLocation(command, expectedResultMessage, model);
    }

    @Test
    public void sortByLesson() {
        Model model = getModel();
        String command;
        String expectedResultMessage;

        command = SortCommand.COMMAND_WORD;
        expectedResultMessage = SortCommand.MESSAGE_SORT_LESSON_SUCCESS;
        Index index = INDEX_FIRST_LESSON;
        ReadOnlyLesson toView = model.getFilteredLessonList().get(index.getZeroBased());
        model.updateFilteredLessonList(new FixedCodePredicate(toView.getCode()));
        ListingUnit.setCurrentListingUnit(LESSON);
        model.sortLessons();
        ListingUnit.setCurrentListingUnit(MODULE);
        assertCommandSuccessSortByLessons(command, expectedResultMessage, model);
    }

    @Test
    public void sortByMarkedLesson() {
        Model model = getModel();
        String command;
        String expectedResultMessage;

        command = SortCommand.COMMAND_WORD;
        expectedResultMessage = SortCommand.MESSAGE_SORT_LESSON_SUCCESS;
        ListingUnit.setCurrentListingUnit(LESSON);
        model.updateFilteredLessonList(new MarkedListPredicate());
        model.sortLessons();
        assertCommandSuccessSortByMarkedLessons(command, expectedResultMessage, model);
    }

    /**
     * Executes {@code SortCommand} in lesson list and
     * verifies that the result equals to {@code expectedResultMessage},{@code expectedModel}.
     */
    public void assertCommandSuccessSortByLessons(String command, String expectedResultMessage,
                                                  Model expectedModel) {
        executeCommand(ViewCommand.COMMAND_WORD + " 1");
        assertCommandSuccess(command, expectedResultMessage, expectedModel);
    }

    /**
     * Executes {@code SortCommand} in marked lesson list and
     * verifies that the result equals to {@code expectedResultMessage},{@code expectedModel}.
     */
    public void assertCommandSuccessSortByMarkedLessons(String command, String expectedResultMessage,
                                                        Model expectedModel) {
        executeCommand(ListCommand.COMMAND_WORD + " " + ListCommand.MARKED_LIST_KEYWORD);
        assertCommandSuccess(command, expectedResultMessage, expectedModel);
    }

    /**
     * Executes {@code SortCommand} in location list and
     * verifies that the result equals to {@code expectedResultMessage},{@code expectedModel}.
     */
    public void assertCommandSuccessSortByLocation(String command, String expectedResultMessage, Model expectedModel) {
        executeCommand(ListCommand.COMMAND_WORD + " " + ListCommand.LOCATION_KEYWORD);
        assertCommandSuccess(command, expectedResultMessage, expectedModel);
    }

    /**
     * Executes {@code SortCommand} in module list and
     * verifies that the result equals to {@code expectedResultMessage},{@code expectedModel}.
     */
    public void assertCommandSuccessSortByModule(String command, String expectedResultMessage, Model expectedModel) {
        executeCommand(ListCommand.COMMAND_WORD + " " + ListCommand.MODULE_KEYWORD);
        assertCommandSuccess(command, expectedResultMessage, expectedModel);
    }

    /**
     * Executes {@code SortCommand} and verifies that the result equals to {@code expectedResultMessage}.
     */
    private void assertCommandSuccess(String command, String expectedResultMessage, Model expectedModel) {
        executeCommand(command);
        assertEquals(expectedResultMessage, getResultDisplay().getText());
        Model model = getModel();
        assertEquals("", getCommandBox().getInput());
        assertEquals(expectedModel, model);
        assertListMatching(getLessonListPanel(), expectedModel.getFilteredLessonList());
        //assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertEquals(expectedResultMessage, getResultDisplay().getText());
        Model model = getModel();
        assertEquals(expectedModel, model);
        assertEquals(command, getCommandBox().getInput());
        assertListMatching(getLessonListPanel(), expectedModel.getFilteredLessonList());
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }

    @After
    public void wrapUp() {
        ListingUnit.setCurrentListingUnit(beginningListingUnit);
    }

}
