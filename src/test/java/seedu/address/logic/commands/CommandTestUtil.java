package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOME_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCH_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEBSITE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.EditPersonDescriptorBuilder;

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
    public static final String VALID_EVENT_A_NAME = "Chinese New Year";
    public static final String VALID_EVENT_A_DATE = "2017-01-25";
    public static final String VALID_EVENT_A_ADDRESS = "ChinaTown";
    public static final String VALID_EVENT_B_NAME = "Christmas";
    public static final String VALID_EVENT_B_DATE = "25/12/2017";
    public static final String VALID_EVENT_B_ADDRESS = "Iceland";
    public static final String VALID_HOME_NUM_AMY = "65656511";
    public static final String VALID_HOME_NUM_BOB = "65656522";
    public static final String VALID_SCH_EMAIL_AMY = "amy@u.nus.edu";
    public static final String VALID_SCH_EMAIL_BOB = "bob@u.ntu.edu";
    public static final String VALID_WEBSITE_AMY = "https://www.facebook.com/Amy";
    public static final String VALID_WEBSITE_BOB = "https://www.facebook.com/Bob";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_BIRTHDAY_AMY = "12/11/1998";
    public static final String VALID_BIRTHDAY_BOB = "12/11/1998";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String HOME_NUM_DESC_AMY = " " + PREFIX_HOME_NUMBER + VALID_HOME_NUM_AMY;
    public static final String HOME_NUM_DESC_BOB = " " + PREFIX_HOME_NUMBER + VALID_HOME_NUM_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String EVENT_NAME_A_DESC = " " + PREFIX_NAME + VALID_EVENT_A_NAME;
    public static final String EVENT_NAME_B_DESC = " " + PREFIX_NAME + VALID_EVENT_B_NAME;
    public static final String EVENT_DATE_A_DESC = " " + PREFIX_DATE + VALID_EVENT_A_DATE;
    public static final String EVENT_DATE_B_DESC = " " + PREFIX_DATE + VALID_EVENT_B_DATE;
    public static final String EVENT_ADDRESS_A_DESC = " " + PREFIX_ADDRESS + VALID_EVENT_A_ADDRESS;
    public static final String EVENT_ADDRESS_B_DESC = " " + PREFIX_ADDRESS + VALID_EVENT_B_ADDRESS;
    public static final String SCH_EMAIL_DESC_AMY = " " + PREFIX_SCH_EMAIL + VALID_SCH_EMAIL_AMY;
    public static final String SCH_EMAIL_DESC_BOB = " " + PREFIX_SCH_EMAIL + VALID_SCH_EMAIL_BOB;
    public static final String WEBSITE_DESC_AMY = " " + PREFIX_WEBSITE + VALID_WEBSITE_AMY;
    public static final String WEBSITE_DESC_BOB = " " + PREFIX_WEBSITE + VALID_WEBSITE_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String BIRTHDAY_DESC_AMY = " " + PREFIX_BIRTHDAY + VALID_BIRTHDAY_AMY;
    public static final String BIRTHDAY_DESC_BOB = " " + PREFIX_BIRTHDAY + VALID_BIRTHDAY_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_HOME_NUM_DESC = " " + PREFIX_HOME_NUMBER + "6411a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_SCH_EMAIL_DESC = " " + PREFIX_SCH_EMAIL + "amy!nus"; // missing '@' symbol
    public static final String INVALID_WEBSITE_DESC = " " + PREFIX_WEBSITE; //empty link
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_BIRTHDAY_DESC = " " + PREFIX_BIRTHDAY + "29/january/2012"; //words not allowed
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_DATE_DESC = " " + PREFIX_DATE + "21/Decemeber/2017"; //words not allowed

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withHomeNumber(VALID_HOME_NUM_AMY).withEmail(VALID_EMAIL_AMY)
                .withSchEmail(VALID_SCH_EMAIL_AMY).withWebsite(VALID_WEBSITE_AMY)
                .withAddress(VALID_ADDRESS_AMY).withFavourite("false")
                .withBirthday(VALID_BIRTHDAY_AMY).withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withHomeNumber(VALID_HOME_NUM_BOB).withEmail(VALID_EMAIL_BOB)
                .withSchEmail(VALID_SCH_EMAIL_BOB).withWebsite(VALID_WEBSITE_BOB)
                .withAddress(VALID_ADDRESS_BOB).withFavourite("false")
                .withBirthday(VALID_BIRTHDAY_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
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
        List<ReadOnlyPerson> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());
        List<ReadOnlyEvent> expectedList = new ArrayList<>(actualModel.getFilteredEventList());

        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getAddressBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
            assertEquals(expectedList, actualModel.getFilteredEventList());
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

    /**
     * Favourites the first person in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void favouriteFirstPerson(Model model) {
        ReadOnlyPerson firstPerson = model.getAddressBook().getPersonList().get(0);
        try {
            model.favouritePerson(firstPerson);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("Person in filtered list must exist in model.", pnfe);
        }
    }
}
