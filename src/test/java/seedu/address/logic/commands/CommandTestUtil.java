package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PORTRAIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.DeleteOnCascadeException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.event.EventNameContainsKeywordsPredicate;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
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
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_BIRTHDAY_AMY = "01/01/1990";
    public static final String VALID_BIRTHDAY_BOB = "10-10-1991";
    public static final String VALID_PORTRAIT_PATH_FIRST = "C:/sample1.png";
    public static final String VALID_PORTRAIT_PATH_SECOND = "D:/sample2.jpg";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_EVENT_NAME_FIRST = "First";
    public static final String VALID_EVENT_NAME_SECOND = "Second Meeting";
    public static final String VALID_EVENT_DESC_FIRST = "Discuss A & B 12354 ?";
    public static final String VALID_EVENT_DESC_SECOND = "??2Discuss A & B 12**354 ?";
    public static final String VALID_EVENT_TIME_FIRST = "03/11/2017";
    public static final String VALID_EVENT_TIME_SECOND = "29/02/2016";

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
    public static final String PORTRAIT_DESC_FIRST = " " + PREFIX_PORTRAIT + VALID_PORTRAIT_PATH_FIRST;
    public static final String PORTRAIT_DESC_SECOND = " " + PREFIX_PORTRAIT + VALID_PORTRAIT_PATH_SECOND;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String EVENT_NAME_FIRST = " " + PREFIX_EVENT_NAME + VALID_EVENT_NAME_FIRST;
    public static final String EVENT_NAME_SECOND = " " + PREFIX_EVENT_NAME + VALID_EVENT_NAME_SECOND;
    public static final String EVENT_DESC_FIRST = " " + PREFIX_EVENT_DESCRIPTION + VALID_EVENT_DESC_FIRST;
    public static final String EVENT_DESC_SECOND = " " + PREFIX_EVENT_DESCRIPTION + VALID_EVENT_DESC_SECOND;
    public static final String EVENT_TIME_FIRST = " " + PREFIX_EVENT_TIME + VALID_EVENT_TIME_FIRST;
    public static final String EVENT_TIME_SECOND = " " + PREFIX_EVENT_TIME + VALID_EVENT_TIME_SECOND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_BIRTHDAY_DESC = " " + PREFIX_BIRTHDAY + "32-7-1993";
    // there is no 32th day for any month
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_EVENT_NAME = " " + PREFIX_EVENT_NAME + "Meeting & "; // '&' not allowed in names
    public static final String INVALID_EVENT_DESC = " " + PREFIX_EVENT_DESCRIPTION + " "; //Empty description
    public static final String INVALID_EVENT_TIME_FIRST = " " + PREFIX_EVENT_TIME + "03/15/2017";
    public static final String INVALID_EVENT_TIME_SECOND = " " + PREFIX_EVENT_TIME + "31/11/2017";
    public static final String INVALID_EVENT_TIME_THIRD = " " + PREFIX_EVENT_TIME + "29/02/2017";
    public static final String INVALID_EVENT_TIME_FORTH = " " + PREFIX_EVENT_TIME + "29/02/2100";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withBirthday(VALID_BIRTHDAY_AMY).withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
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
     * Updates {@code model}'s filtered list to show only the first event in the {@code model}'s event list.
     */
    public static void showFirstEventOnly(Model model) {
        ReadOnlyEvent event = model.getEventList().getEventList().get(0);
        final String[] splitName = event.getEventName().fullEventName.split("\\s+");
        model.updateFilteredEventList(new EventNameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

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
        } catch (DeleteOnCascadeException doce) {
            throw new AssertionError("Person in filtered list must exist in model.", doce);
        }
    }

    /**
     * Deletes the first event in {@code model}'s filtered list from {@code model}'s event list.
     */
    public static void deleteFirstEvent(Model model) {
        ReadOnlyEvent firstEvent = model.getFilteredEventList().get(0);
        try {
            model.deleteEvent(firstEvent);
        } catch (EventNotFoundException pnfe) {
            throw new AssertionError("Event in filtered list must exist in model.", pnfe);
        } catch (DeleteOnCascadeException doce) {
            throw new AssertionError("Person in filtered list must exist in model.", doce);
        }
    }

    /**
     * Adds the person at the back in the address book.
     */
    public static void addPerson(Model model, ReadOnlyPerson person) {
        try {
            model.addPerson(person);
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("Impossible.", dpe);
        }
    }

    /**
     * Adds the event at the back in the event list.
     */
    public static void addEvent(Model model, ReadOnlyEvent event) {
        try {
            model.addEvent(event);
        } catch (DuplicateEventException dpe) {
            throw new AssertionError("Impossible.", dpe);
        }
    }

    /**
     * Modify target person's details to be editedPerson's
     */
    public static void modifyPerson(Model model, ReadOnlyPerson targetPerson, ReadOnlyPerson editedPerson) {
        try {
            model.updatePerson(targetPerson, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("Impossible.", dpe);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("Person in filtered list must exist in model.", pnfe);
        }
    }
}
