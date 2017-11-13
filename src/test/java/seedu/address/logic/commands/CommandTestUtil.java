package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALIAS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.Config;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.EventBook;
import seedu.address.model.Model;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.TitleContainsKeywordsPredicate;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.model.person.ContainsKeywordsPredicate;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.user.exceptions.DuplicateUserException;
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
    public static final String VALID_BIRTHDAY_AMY = "09-09-1970";
    public static final String VALID_BIRTHDAY_BOB = "10-09-1970";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_GROUP_AMY = "Test";
    public static final String VALID_GROUP_BOB = "NUS";
    public static final String VALID_REMARK_AMY = "Like skiing.";
    public static final String VALID_REMARK_BOB = "Favourite pastime: Food";
    public static final String VALID_COMMAND = "add";
    public static final String VALID_ALIAS = "random1";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String BIRTHDAY_DESC_AMY = " " + PREFIX_BIRTHDAY + VALID_BIRTHDAY_AMY;
    public static final String BIRTHDAY_DESC_BOB = " " + PREFIX_BIRTHDAY + VALID_BIRTHDAY_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String GROUP_DESC_AMY = " " + PREFIX_GROUP + VALID_GROUP_AMY;
    public static final String GROUP_DESC_BOB = " " + PREFIX_GROUP + VALID_GROUP_BOB;

    public static final String ALIAS_COMMAND = " " + PREFIX_COMMAND + VALID_COMMAND;
    public static final String ALIAS_ALIAS = " " + PREFIX_ALIAS + VALID_ALIAS;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; //empty string not allowed for addresses
    // '/' not allowed as separator
    public static final String INVALID_BIRTHDAY_DESC = " " + PREFIX_BIRTHDAY + "09/09/1990";
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_COMMAND = " " + "unknown";
    public static final String INVALID_ALIAS = " " + "add";
    public static final String INVALID_ALIAS_VALUE = " " + PREFIX_ALIAS + "add";
    public static final String INVALID_COMMAND_VALUE = " " + PREFIX_COMMAND + "addx";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    public static final String VALID_TITLE_SPECTRA = "Spectra Show";
    public static final String VALID_TITLE_DEEPAVALI = "Deepavali Celebration";
    public static final String VALID_DESCRIPTION_SPECTRA = "Water Show";
    public static final String VALID_DESCRIPTION_DEEPAVALI = "Celebrating Deepavali 2017";
    public static final String VALID_LOCATION_SPECTRA = "Marina square";
    public static final String VALID_LOCATION_DEEPAVALI = "India";
    public static final String VALID_DATETIME_SPECTRA = "20-09-2017 1913";
    public static final String VALID_DATETIME_DEEPAVALI = "12-12-2017 1920";

    public static final String TITLE_DESC_SPECTRA = " " + PREFIX_TITLE + VALID_TITLE_SPECTRA;
    public static final String TITLE_DESC_DEEPAVALI = " " + PREFIX_TITLE + VALID_TITLE_DEEPAVALI;
    public static final String DESCRIPTION_DESC_SPECTRA = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_SPECTRA;
    public static final String DESCRIPTION_DESC_DEEPAVALI = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_DEEPAVALI;
    public static final String LOCATION_DESC_SPECTRA = " " + PREFIX_LOCATION + VALID_LOCATION_SPECTRA;
    public static final String LOCATION_DESC_DEEPAVALI = " " + PREFIX_LOCATION + VALID_LOCATION_DEEPAVALI;
    public static final String DATETIME_DESC_SPECTRA = " " + PREFIX_DATETIME + VALID_DATETIME_SPECTRA;
    public static final String DATETIME_DESC_DEEPAVALI = " " + PREFIX_DATETIME + VALID_DATETIME_DEEPAVALI;

    // '~' not allowed at the start of title
    public static final String INVALID_TITLE_DESC = " " + PREFIX_TITLE + "~Sentosa";
    public static final String INVALID_DESCRIPTION_DESC = " "
            + PREFIX_DESCRIPTION + "&New Basketball"; // '&' not allowed at the start of description
    public static final String INVALID_LOCATION_DESC = " "
            + PREFIX_LOCATION + "*Tampines"; // '*' not allowed at the start of location
    public static final String INVALID_DATETIME_DESC = " "
            + PREFIX_DATETIME + "32-11-2017 2359"; //There is no 32 in the date

    public static final EditEventCommand.EditEventDescriptor DESC_SPECTRA;
    public static final EditEventCommand.EditEventDescriptor DESC_DEEPAVALI;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withBirthday(VALID_BIRTHDAY_AMY).withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withBirthday(VALID_BIRTHDAY_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

        DESC_SPECTRA = new EditEventDescriptorBuilder().withTitle(VALID_TITLE_SPECTRA)
                .withDescription(VALID_DESCRIPTION_SPECTRA).withLocation(VALID_LOCATION_SPECTRA)
                .withDatetime(VALID_DATETIME_SPECTRA).build();
        DESC_DEEPAVALI = new EditEventDescriptorBuilder().withTitle(VALID_TITLE_DEEPAVALI)
                .withDescription(VALID_DESCRIPTION_DEEPAVALI).withLocation(VALID_LOCATION_DEEPAVALI)
                .withDatetime(VALID_DATETIME_DEEPAVALI).build();
    }

    //@@author keloysiusmak

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualConfig} matches {@code expectedConfig}
     */
    public static void assertConfigCommandSuccess(Command command, Config actualConfig, String expectedMessage,
                                                  Config expectedConfig) {
        try {
            CommandResult result = command.execute();
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedConfig, actualConfig);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        } catch (DuplicateUserException due) {
            throw new AssertionError("Execution of command should not fail.", due);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualConfig} does not match {@code expectedConfig}
     */
    public static void assertConfigDiffCommandSuccess(Command command, Config actualConfig, String expectedMessage,
                                                      Config expectedConfig) {
        try {
            CommandResult result = command.execute();
            assertEquals(expectedMessage, result.feedbackToUser);
            assertNotEquals(expectedConfig, actualConfig);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        } catch (DuplicateUserException due) {
            throw new AssertionError("Execution of command should not fail.", due);
        }
    }
    //@@author

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
        } catch (DuplicateUserException due) {
            throw new AssertionError("Execution of command should not fail.", due);
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

        EventBook expectedEventBook = new EventBook(actualModel.getEventBook());
        List<ReadOnlyEvent> expectedEventFilteredList = new ArrayList<>(actualModel.getFilteredEventList());

        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getAddressBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
            assertEquals(expectedEventBook, actualModel.getEventBook());
            assertEquals(expectedEventFilteredList, actualModel.getFilteredEventList());
        } catch (DuplicateUserException due) {
            throw new AssertionError("DuplicateUserException should not be thrown: ", due);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the first person in the {@code model}'s address book.
     */
    public static void showFirstPersonOnly(Model model) {
        ReadOnlyPerson person = model.getAddressBook().getPersonList().get(0);
        final String[] splitName = person.getName().fullName.split("\\s+");
        ContainsKeywordsPredicate.setPredicateType('n');
        model.updateFilteredPersonList(new ContainsKeywordsPredicate(Arrays.asList(splitName[0])));

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
     * Updates {@code model}'s filtered list to show only the first event in the {@code model}'s event book.
     */
    public static void showFirstEventOnly(Model model) {
        ReadOnlyEvent event = model.getEventBook().getEventList().get(0);
        model.updateFilteredEventList(new TitleContainsKeywordsPredicate(Arrays.asList(event.getTitle().value)));

        assert model.getFilteredEventList().size() == 1;
    }

    /**
     * Deletes the first person in {@code model}'s filtered list from {@code model}'s address book.
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
