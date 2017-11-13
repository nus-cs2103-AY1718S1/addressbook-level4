package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHOTO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTTIME;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.task.AppointmentContainsKeywordsPredicate;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.EditTaskDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_NAME_CARRIE = "Carrie Chung";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_PHONE_CARRIE = "33333333";
    //@@author viviantan95
    public static final String VALID_BIRTHDAY_AMY = "11/11/1991";
    public static final String VALID_BIRTHDAY_BOB = "22/22/1992";
    public static final String VALID_BIRTHDAY_CARRIE = "33/33/1993";
    //@@author
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_EMAIL_CARRIE = "carrie@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_ADDRESS_CARRIE = "Block 231, Carrie Street 3";
    public static final String VALID_MODULE_CS2103 = "CS2103";
    public static final String VALID_MODULE_CS2101 = "CS2101";
    public static final String VALID_MODULE_GER1000 = "GER1000";
    //@@author viviantan95
    public static final String VALID_WEB_PHOTO_URL = "https://cdn.images.express.co.uk/img/dynamic/36/590x"
            + "/secondary/CHRIS-EVANS-865133.jpg";
    public static final String VALID_LOCAL_PHOTO_URL = "images/defaultPhoto.png";
    // @@author tanchc
    public static final String VALID_APPOINTMENT_MOVIE = "Movie";
    public static final String VALID_APPOINTMENT_EVENT = "Event";
    public static final String VALID_APPOINTMENT_BADMINTON = "Badminton";
    public static final String VALID_DATE_MOVIE = "29/11/2017";
    public static final String VALID_DATE_EVENT = "02/02/2018";
    public static final String VALID_DATE_BADMINTON = "06/01/2018";
    public static final String VALID_START_TIME_MOVIE = "22:00";
    public static final String VALID_START_TIME_EVENT = "10:00";
    public static final String VALID_START_TIME_EVENT_EDIT = "15:00";
    public static final String VALID_START_TIME_BADMINTON = "20:00";
    public static final String VALID_APPOINTMENT_MEETING = "Meeting";
    public static final String VALID_APPOINTMENT_INTERVIEW = "Interview";
    public static final String VALID_DATE_MEETING = "25/11/2017";
    public static final String VALID_DATE_INTERVIEW = "28/10/2017";
    public static final String VALID_START_TIME_MEETING = "12:00";
    public static final String VALID_START_TIME_INTERVIEW = "10:00";
    // @@author
    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String NAME_DESC_CARRIE = " " + PREFIX_NAME + VALID_NAME_CARRIE;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String PHONE_DESC_CARRIE = " " + PREFIX_PHONE + VALID_PHONE_CARRIE;
    public static final String BIRTHDAY_DESC_AMY = " " + PREFIX_BIRTHDAY + VALID_BIRTHDAY_AMY;
    public static final String BIRTHDAY_DESC_BOB = " " + PREFIX_BIRTHDAY + VALID_BIRTHDAY_BOB;
    public static final String BIRTHDAY_DESC_CARRIE = " " + PREFIX_BIRTHDAY + VALID_BIRTHDAY_CARRIE;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String EMAIL_DESC_CARRIE = " " + PREFIX_EMAIL + VALID_EMAIL_CARRIE;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String ADDRESS_DESC_CARRIE = " " + PREFIX_ADDRESS + VALID_ADDRESS_CARRIE;
    public static final String MODULE_DESC_CS2103 = " " + PREFIX_MODULE + VALID_MODULE_CS2103;
    public static final String MODULE_DESC_CS2101 = " " + PREFIX_MODULE + VALID_MODULE_CS2101;
    public static final String MODULE_DESC_GER1000 = " " + PREFIX_MODULE + VALID_MODULE_GER1000;
    public static final String URL_DESC_WEB = " " + PREFIX_PHOTO + VALID_WEB_PHOTO_URL;
    public static final String URL_DESC_LOCAL = " " + PREFIX_PHOTO + VALID_LOCAL_PHOTO_URL;
    // @@author tanchc
    public static final String APPOINTMENT_DESC_MEETING = " " + PREFIX_APPOINTMENT + VALID_APPOINTMENT_MEETING;
    public static final String APPOINTMENT_DESC_INTERVIEW = " " + PREFIX_APPOINTMENT + VALID_APPOINTMENT_INTERVIEW;
    public static final String DATE_DESC_MEETING = " " + PREFIX_DATE + VALID_DATE_MEETING;
    public static final String DATE_DESC_INTERVIEW = " " + PREFIX_DATE + VALID_DATE_INTERVIEW;
    public static final String START_TIME_DESC_MEETING = " " + PREFIX_STARTTIME + VALID_START_TIME_MEETING;
    public static final String START_TIME_DESC_INTERVIEW = " " + PREFIX_STARTTIME + VALID_START_TIME_INTERVIEW;
    // @@author
    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    //@@author viviantan95
    public static final String INVALID_BIRTHDAY_DESC = " " + PREFIX_BIRTHDAY + "11/01/19955";
    // more than 8 numbers
    //@@author
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_MODULE_DESC = " " + PREFIX_MODULE + "hubby*"; // '*' not allowed in modules
    //@@author viviantan95
    public static final String INVALID_URL_DESC = " " + PREFIX_PHOTO + "images/defaultPhoto1.png"; //Not a valid URL
    // @@author tanchc
    public static final String APPOINTMENT_DESC_MOVIE = " " + PREFIX_APPOINTMENT + VALID_APPOINTMENT_MOVIE;
    public static final String APPOINTMENT_DESC_EVENT = " " + PREFIX_APPOINTMENT + VALID_APPOINTMENT_EVENT;
    public static final String APPOINTMENT_DESC_BADMINTON = " " + PREFIX_APPOINTMENT + VALID_APPOINTMENT_BADMINTON;
    public static final String DATE_DESC_MOVIE = " " + PREFIX_DATE + VALID_DATE_MOVIE;
    public static final String DATE_DESC_EVENT = " " + PREFIX_DATE + VALID_DATE_EVENT;
    public static final String DATE_DESC_BADMINTON = " " + PREFIX_DATE + VALID_DATE_BADMINTON;
    public static final String START_TIME_DESC_MOVIE = " " + PREFIX_STARTTIME + VALID_START_TIME_MOVIE;
    public static final String START_TIME_DESC_EVENT = " " + PREFIX_STARTTIME + VALID_START_TIME_EVENT;
    public static final String START_TIME_DESC_BADMINTON = " " + PREFIX_STARTTIME + VALID_START_TIME_BADMINTON;

    public static final String INVALID_APPOINTMENT_DESC = " " + PREFIX_APPOINTMENT + "Meetings&"; // '&' not allowed
    public static final String INVALID_DATE_DESC = " " + PREFIX_DATE + "01.01.2020"; // '.' not allowed
    public static final String INVALID_START_TIME_DESC = " " + PREFIX_STARTTIME + "1300"; // missing ':' symbol
    // @@author
    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;
    public static final EditCommand.EditPersonDescriptor DESC_CARRIE;
    public static final EditTaskCommand.EditTaskDescriptor DESC_MEETING;
    public static final EditTaskCommand.EditTaskDescriptor DESC_INTERVIEW;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhones(VALID_PHONE_AMY).withBirthday(VALID_BIRTHDAY_AMY).withEmails(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withMods(VALID_MODULE_CS2101).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhones(VALID_PHONE_BOB).withBirthday(VALID_BIRTHDAY_BOB).withEmails(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withMods(VALID_MODULE_GER1000, VALID_MODULE_CS2101).build();
        DESC_CARRIE = new EditPersonDescriptorBuilder().withName(VALID_NAME_CARRIE)
                .withPhones(VALID_PHONE_CARRIE).withBirthday(VALID_BIRTHDAY_CARRIE).withEmails(VALID_EMAIL_CARRIE)
                .withAddress(VALID_ADDRESS_CARRIE).withMods(VALID_MODULE_CS2101).build();
        DESC_MEETING = new EditTaskDescriptorBuilder().withAppointment(VALID_APPOINTMENT_MEETING)
                .withDate(VALID_DATE_MEETING).withStartTime(VALID_START_TIME_MEETING).build();
        DESC_INTERVIEW = new EditTaskDescriptorBuilder().withAppointment(VALID_APPOINTMENT_INTERVIEW)
                .withDate(VALID_DATE_INTERVIEW).withStartTime(VALID_START_TIME_INTERVIEW).build();
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
        List<ReadOnlyTask> expectedFilteredTaskList = new ArrayList<>(actualModel.getFilteredTaskList());

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
     * Updates {@code model}'s filtered list to show only the first task in the {@code model}'s address book.
     */
    public static void showFirstTaskOnly(Model model) {
        ReadOnlyTask task = model.getAddressBook().getTaskList().get(0);
        final String[] splitAppointment = task.getAppointment().appointment.split("\\s+");
        model.updateFilteredTaskList(new AppointmentContainsKeywordsPredicate(Arrays.asList(splitAppointment[0])));

        assert model.getFilteredTaskList().size() == 1;
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
