package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IMAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_DUE_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.reminder.DetailsContainsKeywordsPredicate;
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.ChangeReminderDescriptorBuilder;
import seedu.address.testutil.DeleteTagDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_TAG_EMAIL = "friends";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_DOB_AMY = "13.10.1997";
    public static final String VALID_DOB_BOB = "13.10.1997";
    public static final String VALID_IMAGE_AMY = "";
    public static final String VALID_IMAGE_BOB = "";
    public static final String VALID_REMARK_AMY = "CS2101/SEC/1";
    public static final String VALID_REMARK_BOB = "CS2101/SEC/1";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friends";
    public static final String VALID_EMAIL_SUBJECT = "party";
    public static final String VALID_TAG_FAMILY = "family";
    public static final String VALID_TAG_COLOR_NAME_RED = "red";
    public static final String VALID_TAG_COLOR_NAME_YELLOW = "yellow";

    public static final String VALID_USERNAME_AMY = "amy";
    public static final String VALID_USERNAME_BOB = "bob";
    public static final String VALID_DETAILS_ASSIGNMENT = "Assignment";
    public static final String VALID_PRIORITY_ASSIGNMENT = "Low";
    public static final String VALID_DUE_DATE_ASSIGNMENT = "12.11.2017";
    public static final String VALID_DETAILS_MEETING = " Group meeting";
    public static final String VALID_PRIORITY_MEETING = "High";
    public static final String VALID_DUE_DATE_MEETING = "12.11.2017";
    public static final String VALID_DETAILS_TUTORIAL = "Tutorial meeting";
    public static final String VALID_DUE_DATE_TUTORIAL = "12.10.2017";
    public static final String VALID_PRIORITY_TUTORIAL = "Medium";


    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String DOB_DESC_AMY = " " + PREFIX_DOB + VALID_DOB_AMY;
    public static final String DOB_DESC_BOB = " " + PREFIX_DOB + VALID_DOB_BOB;
    public static final String IMAGE_AMY = " " + PREFIX_IMAGE + VALID_IMAGE_AMY;
    public static final String IMAGE_BOB = " " + PREFIX_IMAGE + VALID_IMAGE_BOB;
    public static final String EMAIL_TAG = " " + PREFIX_TAG + VALID_TAG_EMAIL;
    public static final String EMAIL_SUBJECT = " " + PREFIX_SUBJECT + VALID_EMAIL_SUBJECT;

    public static final String REMARK_DESC_AMY = " " + PREFIX_REMARK + VALID_REMARK_AMY;
    public static final String REMARK_DESC_BOB = " " + PREFIX_REMARK + VALID_REMARK_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String USERNAME_AMY = " " + PREFIX_USERNAME + VALID_USERNAME_AMY;
    public static final String USERNAME_BOB = " " + PREFIX_USERNAME + VALID_USERNAME_BOB;
    public static final String DETAILS_DESC_ASSIGNMENT = " " + PREFIX_REMINDER_DETAILS + VALID_DETAILS_ASSIGNMENT;
    public static final String PRIORITY_DESC_ASSIGNMENT = " " + PREFIX_REMINDER_PRIORITY + VALID_PRIORITY_ASSIGNMENT;
    public static final String DUE_DATE_DESC_ASSIGNMENT = " " + PREFIX_REMINDER_DUE_DATE + VALID_DUE_DATE_ASSIGNMENT;
    public static final String DETAILS_DESC_MEETING = " " + PREFIX_REMINDER_DETAILS + VALID_DETAILS_MEETING;
    public static final String PRIORITY_DESC_MEETING = " " + PREFIX_REMINDER_PRIORITY + VALID_PRIORITY_MEETING;
    public static final String DUE_DATE_DESC_MEETING = " " + PREFIX_REMINDER_DUE_DATE + VALID_DUE_DATE_MEETING;
    public static final String DETAILS_DESC_TUTORIAL = " " + PREFIX_REMINDER_DETAILS + VALID_DETAILS_TUTORIAL;
    public static final String PRIORITY_DESC_TUTORIAL = " " + PREFIX_REMINDER_PRIORITY + VALID_PRIORITY_TUTORIAL;
    public static final String DUE_DATE_DESC_TUTORIAL = " " + PREFIX_REMINDER_DUE_DATE + VALID_DUE_DATE_TUTORIAL;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses

    public static final String INVALID_DATE_OF_BIRTH_DESC = " " + PREFIX_DOB + "33.10.1997"; // out of bounds days
    // not allowed for dateOfBirth
    public static final String INVALID_IMAGE_PATH_DESC = " " + PREFIX_IMAGE
            + "src/images/clock.png"; //incorrect file path

    public static final String INVALID_DATE_OF_BIRTH = " " + PREFIX_DOB; // empty string not allowed for dateOfBirth
    public static final String INVALID_REMARK_AMY = "Dummy";
    public static final String INVALID_REMARK_BOB = "Dummy";
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_TAG = "notExistingTag";
    public static final String INVALID_TAG_2 = "notExistingTag2";
    public static final String INVALID_FONT_SIZE = "invalid font size";
    public static final String INVALID_THEME_NAME = "invalid theme name";
    public static final String INVALID_DETAILS_DESC = " " + PREFIX_REMINDER_DETAILS + "@Meeting";
    public static final String INVALID_PRIORITY_DESC = " " + PREFIX_REMINDER_PRIORITY + "!High";
    public static final String INVALID_DUE_DATE_DESC = " " + PREFIX_REMINDER_DUE_DATE + "@13.10.1997";
    public static final String INVALID_TAG_COLOR_NAME = "invalid tag color name";
    public static final String INVALID_DATE_OF_BIRTH_DESC_BOUNDS = " " + PREFIX_DOB + "30.02.2017";
    public static final String INVALID_EMAIL_TAG = " " + PREFIX_TAG + "FRIEN";
    public static final String INVALID_EMAIL_SUBJECT = " " + PREFIX_SUBJECT + "s.s";

    public static final DeleteTagCommand.DeleteTagDescriptor TAG_DESC_AMY;
    public static final DeleteTagCommand.DeleteTagDescriptor TAG_DESC_BOB;
    public static final ChangeReminderCommand.ChangeReminderDescriptor DESC_ASSIGNMENT;
    public static final ChangeReminderCommand.ChangeReminderDescriptor DESC_MEETING;
    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;
    public static final Set<Tag> VALID_TAGLIST_1;
    public static final Set<Tag> VALID_TAGLIST_2;
    public static final Set<Tag> INVALID_TAGLIST;
    public static final Set<Tag> INVALID_TAGLIST_2;


    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withDateOfBirth(VALID_DOB_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withDateOfBirth(VALID_DOB_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        System.out.println();
        //@@author pjunwei95
        TAG_DESC_AMY = new DeleteTagDescriptorBuilder()
                .withTags(VALID_TAG_FRIEND).build();
        TAG_DESC_BOB = new DeleteTagDescriptorBuilder()
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        //@@author pjunwei95

        VALID_TAGLIST_1 = new HashSet<>();
        try {
            VALID_TAGLIST_1.add(new Tag(VALID_TAG_FRIEND));
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }

        VALID_TAGLIST_2 = new HashSet<>();
        try {
            VALID_TAGLIST_2.add(new Tag(VALID_TAG_FRIEND));
            VALID_TAGLIST_2.add(new Tag(VALID_TAG_FAMILY));
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }

        INVALID_TAGLIST = new HashSet<>();
        try {
            INVALID_TAGLIST.add(new Tag(INVALID_TAG));
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }

        INVALID_TAGLIST_2 = new HashSet<>();
        try {
            INVALID_TAGLIST_2.add(new Tag(INVALID_TAG));
            INVALID_TAGLIST_2.add(new Tag(INVALID_TAG_2));
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
    }
    static {

        DESC_ASSIGNMENT = new ChangeReminderDescriptorBuilder().withDetails(VALID_DETAILS_ASSIGNMENT)
                .withPriority(VALID_PRIORITY_ASSIGNMENT).withDueDate(VALID_DUE_DATE_ASSIGNMENT).build();

        DESC_MEETING = new ChangeReminderDescriptorBuilder().withDetails(VALID_DETAILS_MEETING)
                .withPriority(VALID_PRIORITY_MEETING).withDueDate(VALID_DUE_DATE_MEETING).build();


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
            assertEquals(expectedMessage, result.feedbackToUser);;
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
        List<ReadOnlyPerson> expectedFilteredListPerson = new ArrayList<>(actualModel.getFilteredPersonList());
        List<ReadOnlyReminder> expectedFilteredListReminder = new ArrayList<>(actualModel.getFilteredReminderList());
        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getAddressBook());
            assertEquals(expectedFilteredListPerson, actualModel.getFilteredPersonList());
            assertEquals(expectedFilteredListReminder, actualModel.getFilteredReminderList());
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the first person in the {@code model}'s address book.
     */
    public static void showFirstPersonOnly(Model model) {
        ReadOnlyPerson person = model.getAddressBook().getPersonList().get(0);
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assert model.getFilteredPersonList().size() == 1;
    }
    //@@author RonakLakhotia
    /**
     * Updates {@code model}'s filtered list to show only the first reminder in the {@code model}'s address book.
     */
    public static void showFirstReminderOnly(Model model) {
        ReadOnlyReminder reminder = model.getAddressBook().getReminderList().get(0);
        final String[] splitDetails = reminder.getDetails().details.split("\\s+");
        model.updateFilteredReminderList(new DetailsContainsKeywordsPredicate(Arrays.asList(splitDetails[0])));

        assert model.getFilteredReminderList().size() == 1;
    }
    //@@author
    /**
     * Deletes the first person in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstPerson(Model model) {
        ReadOnlyPerson firstPerson = model.getFilteredPersonList().get(0);
        try {
            model.deletePerson(firstPerson);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("Person in filtered list must exist in model.", pnfe);
        }
    }
}
