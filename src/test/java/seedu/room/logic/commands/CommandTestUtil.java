package seedu.room.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.room.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.room.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.room.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.room.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.room.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.room.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.room.logic.parser.CliSyntax.PREFIX_ROOM;
import static seedu.room.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.room.logic.parser.CliSyntax.PREFIX_TEMPORARY;
import static seedu.room.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.room.logic.commands.exceptions.CommandException;
import seedu.room.model.Model;
import seedu.room.model.ResidentBook;
import seedu.room.model.event.ReadOnlyEvent;
import seedu.room.model.event.TitleContainsKeywordsPredicate;
import seedu.room.model.event.exceptions.EventNotFoundException;
import seedu.room.model.person.NameContainsKeywordsPredicate;
import seedu.room.model.person.ReadOnlyPerson;
import seedu.room.model.person.exceptions.PersonNotFoundException;
import seedu.room.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ROOM_AMY = "08-115";
    public static final String VALID_ROOM_BOB = "20-121F";
    public static final String VALID_TIMESTAMP_AMY = "0";
    public static final String VALID_TIMESTAMP_BOB = "0";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friends";
    public static final String DEFAULT_NOT_SET = "Not Set";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ROOM_DESC_AMY = " " + PREFIX_ROOM + VALID_ROOM_AMY;
    public static final String ROOM_DESC_BOB = " " + PREFIX_ROOM + VALID_ROOM_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String TIMESTAMP_DESC_AMY = " " + PREFIX_TEMPORARY + VALID_TIMESTAMP_AMY;
    public static final String TIMESTAMP_DESC_BOB = " " + PREFIX_TEMPORARY + VALID_TIMESTAMP_BOB;
    public static final String EMAIL_DEFAULT_UNSET = " " + PREFIX_EMAIL + DEFAULT_NOT_SET;
    public static final String PHONE_DEFAULT_UNSET = " " + PREFIX_PHONE + DEFAULT_NOT_SET;
    public static final String ROOM_DEFAULT_UNSET = " " + PREFIX_ROOM + DEFAULT_NOT_SET;


    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ROOM_DESC = " " + PREFIX_ROOM; // empty string not allowed for rooms
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    //@@author blackroxs
    public static final String INVALID_TAG = "testing"; // 'testing' does not exist inside the room book
    public static final String INVALID_FILE = "testing.xml"; // 'testing.xml' does not exist inside the temp folder

    //@@author
    public static final String VALID_TITLE_POLYMATH = "USPolymath";
    public static final String VALID_LOCATION_POLYMATH = "Chatterbox";
    public static final String VALID_DESCRIPTION_POLYMATH = "Intellectual Night";
    public static final String VALID_DATETIME_POLYMATH = "25/09/2017 2030 to 2100";

    public static final String VALID_TITLE_ORIENTATION = "USP Orientation";
    public static final String VALID_DESCRIPTION_ORIENTATION = "Conducted by UsAmbassadors";
    public static final String VALID_LOCATION_ORIENTATION = "Chua Thian Poh Hall";
    public static final String VALID_DATETIME_ORIENTATION = "20/12/2017 0700 to 2100";

    public static final String TITLE_DESC_POLYMATH = " " + PREFIX_TITLE + VALID_TITLE_POLYMATH;
    public static final String TITLE_DESC_ORIENTATION = " " + PREFIX_TITLE + VALID_TITLE_ORIENTATION;
    public static final String DESCRIPTION_DESC_POLYMATH = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_POLYMATH;
    public static final String DESCRIPTION_DESC_ORIENTATION = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_ORIENTATION;
    public static final String LOCATION_DESC_POLYMATH = " " + PREFIX_LOCATION + VALID_LOCATION_POLYMATH;
    public static final String LOCATION_DESC_ORIENTATION = " " + PREFIX_LOCATION + VALID_LOCATION_ORIENTATION;
    public static final String DATETIME_DESC_POLYMATH = " " + PREFIX_DATETIME + VALID_DATETIME_POLYMATH;
    public static final String DATETIME_DESC_ORIENTATION = " " + PREFIX_DATETIME + VALID_DATETIME_ORIENTATION;

    public static final String INVALID_TITLE_DESC = " " + PREFIX_TITLE + "James&"; // '&' not allowed in titles
    public static final String INVALID_DESCRIPTION_DESC = " " + PREFIX_DESCRIPTION; // empty string not allowed
    public static final String INVALID_LOCATION_DESC = " " + PREFIX_LOCATION; // empty string not allowed
    public static final String INVALID_DATETIME_DESC = " " + PREFIX_DATETIME + "50/23/2017 1200 to 5000";
    // date and endtime do not exist

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withRoom(VALID_ROOM_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withRoom(VALID_ROOM_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
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
     * - the resident book and the filtered person list in the {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        ResidentBook expectedResidentBook = new ResidentBook(actualModel.getResidentBook());
        List<ReadOnlyPerson> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedResidentBook, actualModel.getResidentBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the first person in the {@code model}'s resident book.
     */
    public static void showFirstPersonOnly(Model model) {
        ReadOnlyPerson person = model.getResidentBook().getPersonList().get(0);
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assert model.getFilteredPersonList().size() == 1;
    }

    /**
     * Deletes the first person in {@code model}'s filtered list from {@code model}'s resident book.
     */
    public static void deleteFirstPerson(Model model) {
        ReadOnlyPerson firstPerson = model.getFilteredPersonList().get(0);
        try {
            model.deletePerson(firstPerson);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("Person in filtered list must exist in model.", pnfe);
        }
    }

    //@@author sushinoya
    /**
     * Updates {@code model}'s filtered list to show only the first event in the {@code model}'s event book.
     */
    public static void showFirstEventOnly(Model model) {
        ReadOnlyEvent event = model.getEventBook().getEventList().get(0);
        final String[] splitTitle = event.getTitle().value.split("\\s+");
        model.updateFilteredEventList(new TitleContainsKeywordsPredicate(Arrays.asList(splitTitle[0])));

        assert model.getFilteredEventList().size() == 1;
    }

    /**
     * Deletes the first person in {@code model}'s filtered list from {@code model}'s event book.
     */
    public static void deleteFirstEvent(Model model) {
        ReadOnlyEvent firstEvent = model.getFilteredEventList().get(0);
        try {
            model.deleteEvent(firstEvent);
        } catch (EventNotFoundException enfe) {
            throw new AssertionError("Event in filtered list must exist in model.", enfe);
        }
    }
}
