package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASS_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FONT_SIZE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LECTURER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME_SLOT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.module.Code;
import seedu.address.model.module.ReadOnlyLesson;
import seedu.address.model.module.exceptions.LessonNotFoundException;
import seedu.address.model.module.predicates.FixedCodePredicate;
import seedu.address.model.module.predicates.ShowSpecifiedLessonPredicate;
import seedu.address.testutil.EditLessonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_CODE_MA1101R = "MA1101R";
    public static final String VALID_CODE_CS2101 = "CS2101";
    public static final String VALID_CODE_MA1102R = "MA1102R";
    public static final String VALID_CLASSTYPE_MA1101R = "Lec";
    public static final String VALID_CLASSTYPE_CS2101 = "Tut";
    public static final String VALID_VENUE_MA1101R = "LT30";
    public static final String VALID_VENUE_MA1102R = "LT29";
    public static final String VALID_VENUE_CS2101 = "COM02-04";
    public static final String VALID_GROUP_MA1101R = "3";
    public static final String VALID_GROUP_CS2101 = "2";
    public static final String VALID_TIMESLOT_MA1101R = "TUE[1300-1500]";
    public static final String VALID_TIMESLOT_CS2101 = "TUE[1600-1800]";
    public static final String VALID_LECTURER_MA1101R = "Ma Siu Lun";
    public static final String VALID_LECTURER_CS2101 = "Diana";
    public static final String VALID_FONT_SIZE_XSMALL = "xsmall";
    public static final String VALID_FONT_SIZE_SMALL = "small";
    public static final String VALID_THEME_LIGHT = "light";
    public static final String VALID_THEME_DARK = "dark";


    public static final String CODE_DESC_MA1101R = " " + PREFIX_MODULE_CODE + VALID_CODE_MA1101R;
    public static final String CODE_DESC_CS2101 = " " + PREFIX_MODULE_CODE + VALID_CODE_CS2101;
    public static final String CLASSTYPE_DESC_MA1101R = " " + PREFIX_CLASS_TYPE + VALID_CLASSTYPE_MA1101R;
    public static final String CLASSTYPE_DESC_CS2101 = " " + PREFIX_CLASS_TYPE + VALID_CLASSTYPE_CS2101;
    public static final String VENUE_DESC_MA1101R = " " + PREFIX_VENUE + VALID_VENUE_MA1101R;
    public static final String VENUE_DESC_CS2101 = " " + PREFIX_VENUE + VALID_VENUE_CS2101;
    public static final String GROUP_DESC_MA1101R = " " + PREFIX_GROUP + VALID_GROUP_MA1101R;
    public static final String GROUP_DESC_CS2101 = " " + PREFIX_GROUP + VALID_GROUP_CS2101;
    public static final String TIMESLOT_DESC_MA1101R = " " + PREFIX_TIME_SLOT + VALID_TIMESLOT_MA1101R;
    public static final String TIMESLOT_DESC_CS2101 = " " + PREFIX_TIME_SLOT + VALID_TIMESLOT_CS2101;
    public static final String LECTURER_DESC_MA1101R = " " + PREFIX_LECTURER + VALID_LECTURER_MA1101R;
    public static final String LECTURER_DESC_CS2101 = " " + PREFIX_LECTURER + VALID_LECTURER_CS2101;
    public static final String FONT_SIZE_DESC_XSMALL = " " + PREFIX_FONT_SIZE + VALID_FONT_SIZE_XSMALL;
    public static final String FONT_SIZE_DESC_SMALL = " " + PREFIX_FONT_SIZE + VALID_FONT_SIZE_SMALL;


    public static final String INVALID_CODE_DESC = " " + PREFIX_MODULE_CODE + "MA*"; //code format is not correct
    public static final String INVALID_CLASSTYPE_DESC = " " + PREFIX_CLASS_TYPE + "1a"; // 'a' not allowed in class type
    public static final String INVALID_VENUE_DESC = " " + PREFIX_VENUE; // empty string not allowed for venue
    public static final String INVALID_GROUP_DESC = " " + PREFIX_GROUP + "SL1"; // 'SL' not allowed for addresses
    public static final String INVALID_TIMESLOT_DESC = " " + PREFIX_TIME_SLOT + "FRIDAY[1200-1300]"; // Only 3 letters
    public static final String INVALID_LECTURER_DESC = " " + PREFIX_LECTURER + ""; // '*' not allowed in tags
    public static final String INVALID_FONT_SIZE_DESC = " " + PREFIX_FONT_SIZE
            + "small!"; // '!' not allowed in font size
    public static final String INVALID_THEME_DESC = "blue";

    public static final EditCommand.EditLessonDescriptor DESC_MA1101R;
    public static final EditCommand.EditLessonDescriptor DESC_CS2101;

    public static final FixedCodePredicate MA1101R_CODE_PREDICATE;

    static {
        DESC_MA1101R = new EditLessonDescriptorBuilder().withCode(VALID_CODE_MA1101R)
                .withClassType(VALID_CLASSTYPE_MA1101R).withLocation(VALID_VENUE_MA1101R).withGroup(VALID_GROUP_MA1101R)
                .withTimeSlot(VALID_TIMESLOT_MA1101R).withLecturers(VALID_LECTURER_MA1101R).build();
        DESC_CS2101 = new EditLessonDescriptorBuilder().withCode(VALID_CODE_CS2101)
                .withClassType(VALID_CLASSTYPE_CS2101).withLocation(VALID_VENUE_CS2101).withGroup(VALID_GROUP_CS2101)
                .withTimeSlot(VALID_TIMESLOT_CS2101).withLecturers(VALID_LECTURER_CS2101).build();
        try {
            MA1101R_CODE_PREDICATE = new FixedCodePredicate(new Code(VALID_CODE_MA1101R));
        } catch (IllegalValueException e) {
            throw new AssertionError("The code cannot be invalid");
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        try {
            CommandResult result = command.execute();
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book and the filtered person list in the {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<ReadOnlyLesson> expectedFilteredList = new ArrayList<>(actualModel.getFilteredLessonList());

        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getAddressBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredLessonList());
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the first lesson in the {@code model}'s address book.
     */
    public static void showFirstLessonOnly(Model model) {
        ReadOnlyLesson lesson = model.getAddressBook().getLessonList().get(0);
        model.updateFilteredLessonList(new ShowSpecifiedLessonPredicate(lesson.hashCode()));

        assert model.getFilteredLessonList().size() == 1;
    }

    /**
     * Deletes the first lesson in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstLesson(Model model) {
        ReadOnlyLesson firstLesson = model.getFilteredLessonList().get(0);
        try {
            model.deleteLesson(firstLesson);
        } catch (LessonNotFoundException pnfe) {
            throw new AssertionError("Person in filtered list must exist in model.", pnfe);
        }
    }
}
