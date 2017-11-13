package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEADER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IMAGE_URL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.event.HeaderContainsKeywordsPredicate;
import seedu.address.model.person.event.ReadOnlyEvent;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.EditEventDescriptorBuilder;
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
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_WEB_IMAGE_URL = "http://asianwiki.com/images/4/45/Sooyoung-p2.jpg";
    public static final String VALID_LOCAL_IMAGE_URL = "file://"
            + Paths.get("src/main/resources/images/help_icon.png").toAbsolutePath().toUri().getPath();

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String URL_DESC_WEB = " " + PREFIX_IMAGE_URL + VALID_WEB_IMAGE_URL;
    public static final String URL_DESC_LOCAL = " " + PREFIX_IMAGE_URL + VALID_LOCAL_IMAGE_URL;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_URL_DESC = " " + PREFIX_IMAGE_URL + "images/fail.png"; //Not a valid URL

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    public static final String VALID_HEADER_MEETING = "Meeting";
    public static final String VALID_HEADER_BIRTHDAY = "Birthday";
    public static final String VALID_DESC_MEETING = "Business";
    public static final String VALID_DESC_BIRTHDAY = "friend";
    public static final String VALID_EVENT_DATE_MEETING = "2017-10-25";
    public static final String VALID_EVENT_DATE_BIRTHDAY = "2017-10-10";

    public static final String HEADER_DESC_MEETING = " " + PREFIX_HEADER + VALID_HEADER_MEETING;
    public static final String HEADER_DESC_BIRTHDAY = " " + PREFIX_HEADER + VALID_HEADER_BIRTHDAY;
    public static final String DESC_DESC_MEETING = " " + PREFIX_DESC + VALID_DESC_MEETING;
    public static final String DESC_DESC_BIRTHDAY = " " + PREFIX_DESC + VALID_DESC_BIRTHDAY;
    public static final String EVENT_DATE_DESC_MEETING = " " + PREFIX_EVENT_DATE + VALID_EVENT_DATE_MEETING;
    public static final String EVENT_DATE_DESC_BIRTHDAY = " " + PREFIX_EVENT_DATE + VALID_EVENT_DATE_BIRTHDAY;

    public static final String INVALID_HEADER_DESC = " " + PREFIX_HEADER; // empty string not allowed for addresses
    public static final String INVALID_DESC_DESC = " " + PREFIX_DESC; // empty string not allowed for addresses
    public static final String INVALID_EVENT_DATE_DESC = " "
            + PREFIX_EVENT_DATE + "2017-02-29"; // date does not exist

    public static final EditEventCommand.EditEventDescriptor DESC_MEETING;
    public static final EditEventCommand.EditEventDescriptor DESC_BIRTHDAY;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    static {
        DESC_MEETING = new EditEventDescriptorBuilder().withHeader(VALID_HEADER_MEETING)
                .withDesc(VALID_DESC_MEETING).withEventDate(VALID_EVENT_DATE_MEETING).build();
        DESC_BIRTHDAY = new EditEventDescriptorBuilder().withHeader(VALID_HEADER_BIRTHDAY)
                .withDesc(VALID_DESC_BIRTHDAY).withEventDate(VALID_EVENT_DATE_BIRTHDAY).build();
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
     * - the address book, the filtered event list
     * - and the filtered person list in the {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<ReadOnlyPerson> expectedFilteredList1 = new ArrayList<>(actualModel.getFilteredPersonList());
        List<ReadOnlyEvent> expectedFilteredList2 = new ArrayList<>(actualModel.getFilteredEventList());

        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getAddressBook());
            assertEquals(expectedFilteredList1, actualModel.getFilteredPersonList());
            assertEquals(expectedFilteredList2, actualModel.getFilteredEventList());
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

    //@@author royceljh
    /**
     * Updates {@code model}'s filtered list to show only the first event in the {@code model}'s address book.
     */
    public static void showFirstEventOnly(Model model) {
        ReadOnlyEvent event = model.getAddressBook().getEventList().get(0);
        final String[] splitHeader = event.getHeader().value.split("\\s+");
        model.updateFilteredEventList(new HeaderContainsKeywordsPredicate(Arrays.asList(splitHeader[0])));

        assert model.getFilteredEventList().size() == 1;
    }
}
