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

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.DeleteOnCascadeException;
import seedu.address.model.AddressBook;
import seedu.address.model.EventList;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventNameContainsKeywordsPredicate;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.model.event.exceptions.PersonHaveParticipateException;
import seedu.address.model.event.exceptions.PersonNotParticipateException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.HaveParticipateEventException;
import seedu.address.model.person.exceptions.NotParticipateEventException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.EventDescriptorBuilder;

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
    public static final String VALID_PORTRAIT_PATH_FIRST = "C:/sample folder/sample1.png";
    public static final String VALID_PORTRAIT_PATH_SECOND = "D:/my pictures/sample2.jpg";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_EVENT_NAME_FIRST = "First";
    public static final String VALID_EVENT_NAME_SECOND = "Second Meeting";
    public static final String VALID_EVENT_DESC_FIRST = "Discuss A & B 12354 ?";
    public static final String VALID_EVENT_DESC_SECOND = "??2Discuss A & B 12**354 ?";
    public static final String VALID_EVENT_TIME_FIRST = "31/12/2018";
    public static final String VALID_EVENT_TIME_SECOND = "29/02/2020"; //leap year
    public static final String VALID_EVENT_TIME_THIRD = "29/02/2000";

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
    public static final String INVALID_EVENT_TIME_SECOND = " " + PREFIX_EVENT_TIME + "29/02/2100"; //Not a leap year

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;
    public static final EditEventCommand.EditEventDescriptor DESC_EVENT_FIRST;
    public static final EditEventCommand.EditEventDescriptor DESC_EVENT_SECOND;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withBirthday(VALID_BIRTHDAY_AMY).withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withBirthday(VALID_BIRTHDAY_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        DESC_EVENT_FIRST = new EventDescriptorBuilder().withName(VALID_EVENT_NAME_FIRST)
            .withDescription(VALID_EVENT_DESC_FIRST).withTime(VALID_EVENT_TIME_FIRST).build();
        DESC_EVENT_SECOND = new EventDescriptorBuilder().withName(VALID_EVENT_NAME_SECOND)
            .withDescription(VALID_EVENT_DESC_SECOND).withTime(VALID_EVENT_TIME_SECOND).build();
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
        EventList expectedEventList = new EventList(actualModel.getEventList());
        List<ReadOnlyPerson> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());
        List<ReadOnlyEvent> expectedFilteredEventList = new ArrayList<>(actualModel.getFilteredEventList());

        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getAddressBook());
            assertEquals(expectedEventList, actualModel.getEventList());
            assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
            assertEquals(expectedFilteredEventList, actualModel.getFilteredEventList());
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
    public static void modifyPerson(Model model, Index targetIndex, ReadOnlyPerson editedPerson) {
        try {
            ReadOnlyPerson targetPerson = model.getFilteredPersonList().get(targetIndex.getZeroBased());
            model.updatePerson(targetPerson, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("Impossible.", dpe);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("Person in filtered list must exist in model.", pnfe);
        } catch (DeleteOnCascadeException doce) {
            throw new AssertionError("Impossible", doce);
        }
    }

    /**
     * Modify target person's details to be editedEvent's
     */
    public static void modifyEvent(Model model, Index targetIndex, ReadOnlyEvent editedEvent) {
        try {
            ReadOnlyEvent targetEvent = model.getFilteredEventList().get(targetIndex.getZeroBased());
            model.updateEvent(targetEvent, editedEvent);
        } catch (DuplicateEventException e) {
            throw new AssertionError("Impossible.", e);
        } catch (EventNotFoundException e) {
            throw new AssertionError("Event in filtered list must exist in model.", e);
        } catch (DeleteOnCascadeException doce) {
            throw new AssertionError("Impossible", doce);
        }
    }

    /**
     * Let a specific person quit a specific event
     */
    public static void quitEvent(Model model, Index personIndex, Index eventIndex) {
        Person person = (Person) model.getFilteredPersonList().get(personIndex.getZeroBased());
        Event event = (Event) model.getFilteredEventList().get(eventIndex.getZeroBased());
        try {
            model.quitEvent(person, event);
        } catch (PersonNotParticipateException e) {
            throw new AssertionError("Impossible", e);
        } catch (NotParticipateEventException e) {
            throw new AssertionError("Impossible", e);
        }
    }

    /**
     *  Let a specific person join a specific event
     */
    public static void joinEvent(Model model, Index personIndex, Index eventIndex) {
        Person person = (Person) model.getFilteredPersonList().get(personIndex.getZeroBased());
        Event event = (Event) model.getFilteredEventList().get(eventIndex.getZeroBased());
        try {
            model.joinEvent(person, event);
        } catch (PersonHaveParticipateException e) {
            throw new AssertionError("Impossible", e);
        } catch (HaveParticipateEventException e) {
            throw new AssertionError("Impossible", e);
        }
    }

    /**
     * Let some persons join certain events
     */
    public static void joinEvents(Model model) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                Person person = (Person) model.getFilteredPersonList().get(i);
                Event event = (Event) model.getFilteredEventList().get(j);
                try {
                    model.joinEvent(person, event);
                } catch (PersonHaveParticipateException e) {
                    throw new AssertionError("Impossible", e);
                } catch (HaveParticipateEventException e) {
                    throw new AssertionError("Impossible", e);
                }
            }
        }

    }
}
