package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.event.EditEventCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.person.EditCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.EventNotFoundException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.property.EventNameContainsKeywordsPredicate;
import seedu.address.model.property.NameContainsKeywordsPredicate;
import seedu.address.model.property.PropertyManager;
import seedu.address.testutil.EditEventDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {
    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Chou";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String VALID_NAME_EVENT1 = "Mel Birthday";
    public static final String VALID_NAME_EVENT2 = "Bobs Birthday";
    public static final String VALID_DATE_EVENT1 = "25122017 08:30";
    public static final String VALID_DATE_EVENT2 = "25022018 08:45";
    public static final String VALID_VENUE_EVENT1 = "Mels crib";
    public static final String VALID_VENUE_EVENT2 = "Bobs crib";

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
    public static final String NAME_DESC_EVENT1 = " " + PREFIX_NAME + VALID_NAME_EVENT1;
    public static final String DATE_DESC_EVENT1 = " " + PREFIX_DATE_TIME + VALID_DATE_EVENT1;
    public static final String VENUE_DESC_EVENT1 = " " + PREFIX_ADDRESS + VALID_VENUE_EVENT1;
    public static final String NAME_DESC_EVENT2 = " " + PREFIX_NAME + VALID_NAME_EVENT2;
    public static final String DATE_DESC_EVENT2 = " " + PREFIX_DATE_TIME + VALID_DATE_EVENT2;
    public static final String VENUE_DESC_EVENT2 = " " + PREFIX_ADDRESS + VALID_VENUE_EVENT2;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG = "hubby*"; // '*' not allowed in tags
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + INVALID_TAG; // '*' not allowed in tags
    public static final String INVALID_DATE_DESC = " " + PREFIX_DATE_TIME + "20102004 03:30pm"; // "pm/am not allowed"

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;
    public static final EditEventCommand.EditEventDescriptor DESC_EVENT1;
    public static final EditEventCommand.EditEventDescriptor DESC_EVENT2;

    public static final String VALID_CONFIG_TAG_COLOR = " --set-tag-color ";
    public static final String VALID_TAG_COLOR = " #7db9a1";
    public static final String VALID_PREDEFINED_COLOR = " red";
    public static final String VALID_CONFIG_ADD_PROPERTY = " --add-property ";
    public static final String VALID_NEW_PROPERTY = " s/b f/birthday m/something r/[^\\s].*";
    public static final String VALID_NEW_PROPERTY_NO_REGEX = " s/m f/major";


    public static final String INVALID_CONFIG_TYPE = " --some-config-type-unknown ";
    public static final String INVALID_CONFIG_VALUE = " unknown value(s)";
    public static final String INVALID_TAG_COLOR = " bee";
    public static final String INVALID_NEW_PROPERTY = " s/b r/[^\\s].*";


    public static final String INVALID_IMPORT_TYPE = " --some-import-type-unknown ";
    public static final String INVALID_IMPORT_PATH = " unknown path";

    public static final String VALID_URL = "https://www.google.com.sg/contacts?day=monday";
    public static final String INVALID_URL_COMMA = " https://123,tg/";

    static {
        PropertyManager.initializePropertyManager();

        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        DESC_EVENT1 = new EditEventDescriptorBuilder().withName(VALID_NAME_EVENT1).withTime(VALID_DATE_EVENT1)
               .withVenue(VALID_VENUE_EVENT1).build();
        DESC_EVENT2 = new EditEventDescriptorBuilder().withName(VALID_NAME_EVENT2).withTime(VALID_DATE_EVENT2)
                .withVenue(VALID_VENUE_EVENT2).build();
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
        List<ReadOnlyEvent> expectedFilteredEventList = new ArrayList<>(actualModel.getFilteredEventList());

        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getAddressBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
            assertEquals(expectedFilteredEventList, actualModel.getFilteredEventList());
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the first person in the {@code model}'s address book.
     */
    public static void showFirstPersonOnly(Model model) {
        ReadOnlyPerson person = model.getAddressBook().getPersonList().get(0);
        final String[] splitName = person.getName().getValue().split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assert model.getFilteredPersonList().size() == 1;
    }
    /**
     * Updates {@code model}'s filtered list to show only the first person in the {@code model}'s address book.
     */
    public static void showFirstEventOnly(Model model) {
        ReadOnlyEvent event = model.getAddressBook().getEventList().get(0);
        final String[] splitName = event.getName().getValue().split("\\s+");
        model.updateFilteredEventsList(new EventNameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assert model.getFilteredEventList().size() == 1;
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
     * Deletes the first event in {@code model}'s filtered list from {@code model}'s address book.
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
