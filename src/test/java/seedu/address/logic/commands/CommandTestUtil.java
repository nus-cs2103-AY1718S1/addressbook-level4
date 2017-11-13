package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.DisplayPicture;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Nickname;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.EditReminderDescriptorBuilder;

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
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_NICKNAME_AMY = "Amy";
    public static final String VALID_NICKNAME_BOB = "Bob";
    public static final String VALID_DISPLAYPIC_AMY = "amy.jpg";
    public static final String VALID_DISPLAYPIC_ALICE = "amy.jpg";
    public static final String VALID_DISPLAYPIC_BOB = "bob.jpg";
    public static final String VALID_BIRTHDAY_AMY = "22/10/1995";
    public static final String VALID_BIRTHDAY_BOB = "21/10/1995";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_REMINDER_COFFEE = "Drink coffee";
    public static final String VALID_REMINDER_DATE_COFFEE = "01/11/2017";
    public static final String VALID_REMINDER_TIME_COFFEE = "08:00";
    public static final String VALID_REMINDER_ASSIGNMENT = "CS2105 Assignment";
    public static final String VALID_REMINDER_DATE_ASSIGNMENT = "26/10/2017";
    public static final String VALID_REMINDER_TIME_ASSIGNMENT = "23:59";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String NICKNAME_DESC_AMY = " " + VALID_NICKNAME_AMY;
    public static final String BIRTHDAY_DESC_AMY = " " + PREFIX_BIRTHDAY + VALID_BIRTHDAY_AMY;
    public static final String BIRTHDAY_DESC_BOB = " " + PREFIX_BIRTHDAY + VALID_BIRTHDAY_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String REMINDER_DESC_COFFEE = " " + PREFIX_REMINDER + VALID_REMINDER_COFFEE;
    public static final String REMINDER_DESC_DATE_COFFEE = " " + PREFIX_DATE + VALID_REMINDER_DATE_COFFEE;
    public static final String REMINDER_DESC_TIME_COFFEE = " " + PREFIX_TIME + VALID_REMINDER_TIME_COFFEE;
    public static final String REMINDER_DESC_ASSIGNMENT = " " + PREFIX_REMINDER + VALID_REMINDER_ASSIGNMENT;
    public static final String REMINDER_DESC_DATE_ASSIGNMENT = " " + PREFIX_DATE + VALID_REMINDER_DATE_ASSIGNMENT;
    public static final String REMINDER_DESC_TIME_ASSIGNMENT = " " + PREFIX_TIME + VALID_REMINDER_TIME_ASSIGNMENT;

    public static final String INVALID_REMINDER_DESC = " " + PREFIX_REMINDER + ""; // Empty reminder
    public static final String INVALID_REMINDER_DESC_DATE = " " + PREFIX_DATE + "12 Mar 2017"; // Format not allowed
    public static final String INVALID_REMINDER_DESC_TIME = " " + PREFIX_TIME + "24:00"; // Violate 24-hr time system

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_BIRTHDAY_DESC = " " + PREFIX_BIRTHDAY + "21 Mar 1995"; //format is wrong
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;
    public static final EditReminderCommand.EditReminderDescriptor REMINDER_COFFEE;
    public static final EditReminderCommand.EditReminderDescriptor REMINDER_ASSIGNMENT;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).withBirthday(VALID_BIRTHDAY_AMY).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).withBirthday(VALID_BIRTHDAY_BOB).build();
        REMINDER_COFFEE = new EditReminderDescriptorBuilder().withReminder(VALID_REMINDER_COFFEE)
                .withDate(VALID_REMINDER_DATE_COFFEE).withTime(VALID_REMINDER_TIME_COFFEE).build();
        REMINDER_ASSIGNMENT = new EditReminderDescriptorBuilder().withReminder(VALID_REMINDER_ASSIGNMENT)
                .withDate(VALID_REMINDER_DATE_ASSIGNMENT).withTime(VALID_REMINDER_TIME_ASSIGNMENT).build();
    }

    public static final Nickname NICKNAME_AMY = new Nickname(VALID_NICKNAME_AMY);
    public static final Nickname NICKNAME_BOB = new Nickname(VALID_NICKNAME_BOB);

    public static final DisplayPicture DISPLAY_PICTURE_AMY =
            new DisplayPicture(new File("./src/test/resources/pictures/" + VALID_DISPLAYPIC_AMY)
                    .getAbsolutePath());
    public static final DisplayPicture DISPLAY_PICTURE_BOB =
            new DisplayPicture(new File("./src/test/resources/pictures/" + VALID_DISPLAYPIC_BOB)
                    .getAbsolutePath());


    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) throws  Exception {
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
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) throws Exception {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<ReadOnlyPerson> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getAddressBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
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
