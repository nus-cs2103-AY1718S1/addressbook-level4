package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHOTO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RELATIONSHIP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMESLOT;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.event.EditEventCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.TitleContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.EditEventDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    //====== Person =========================================================================================

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_COMPANY_AMY = "Microsoft";
    public static final String VALID_COMPANY_BOB = "Google";
    public static final String VALID_POSITION_AMY = "Manager";
    public static final String VALID_POSITION_BOB = "Director";
    public static final String VALID_STATUS_AMY = "Requires follow up";
    public static final String VALID_STATUS_BOB = "Waiting for reply";
    public static final String VALID_PRIORITY_AMY = "H";
    public static final String VALID_PRIORITY_BOB = "M";
    public static final String VALID_NOTE_AMY = "Met this person last week";
    public static final String VALID_NOTE_BOB = "Likes coffee";
    public static final String FILE_SEPARATOR = File.separator;
    public static final String VALID_PHOTO_AMY = "src" + FILE_SEPARATOR + "main" + FILE_SEPARATOR + "resources"
            + FILE_SEPARATOR + "images" + FILE_SEPARATOR + "summer.jpg";
    public static final String VALID_PHOTO_BOB = "src" + FILE_SEPARATOR + "main" + FILE_SEPARATOR + "resources"
            + FILE_SEPARATOR + "images" + FILE_SEPARATOR + "mad-men.jpg";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_REL_SIBLINGS = "siblings";
    public static final String VALID_REL_COLLEAGUE = "colleague";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String COMPANY_DESC_AMY = " " + PREFIX_COMPANY + VALID_COMPANY_AMY;
    public static final String COMPANY_DESC_BOB = " " + PREFIX_COMPANY + VALID_COMPANY_BOB;
    public static final String POSITION_DESC_AMY = " " + PREFIX_POSITION + VALID_POSITION_AMY;
    public static final String POSITION_DESC_BOB = " " + PREFIX_POSITION + VALID_POSITION_BOB;
    public static final String STATUS_DESC_AMY = " " + PREFIX_STATUS + VALID_STATUS_AMY;
    public static final String STATUS_DESC_BOB = " " + PREFIX_STATUS + VALID_STATUS_BOB;
    public static final String PRIORITY_DESC_AMY = " " + PREFIX_PRIORITY + VALID_PRIORITY_AMY;
    public static final String PRIORITY_DESC_BOB = " " + PREFIX_PRIORITY + VALID_PRIORITY_BOB;
    public static final String NOTE_DESC_AMY = " " + PREFIX_NOTE + VALID_NOTE_AMY;
    public static final String NOTE_DESC_BOB = " " + PREFIX_NOTE + VALID_NOTE_BOB;
    public static final String PHOTO_DESC_AMY = " " + PREFIX_PHOTO + VALID_PHOTO_AMY;
    public static final String PHOTO_DESC_BOB = " " + PREFIX_PHOTO + VALID_PHOTO_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String REL_DESC_SIBLINGS = " " + PREFIX_RELATIONSHIP + VALID_REL_SIBLINGS;
    public static final String REL_DESC_COLLEAGUE = " " + PREFIX_RELATIONSHIP + VALID_REL_COLLEAGUE;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_COMPANY_DESC = " " + PREFIX_COMPANY; // empty string not allowed for company
    public static final String INVALID_POSITION_DESC = " " + PREFIX_POSITION; //empty string not allowed for position
    public static final String INVALID_STATUS_DESC = " " + PREFIX_STATUS; //empty string not allowed for status
    public static final String INVALID_PRIORITY_DESC = " " + PREFIX_PRIORITY + "A"; //only H, M, L allowed for priority
    public static final String INVALID_NOTE_DESC = " " + PREFIX_NOTE; //empty string not allowed for status
    public static final String INVALID_PHOTO_DESC = " " + PREFIX_PHOTO; //empty
    // string not allowed for status
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_REL_DESC = " " + PREFIX_RELATIONSHIP + "sibling*"; // '*' not allowed in
    // relationships

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    //====== Events =========================================================================================

    public static final String VALID_TITLE_MIDTERM = "CS2106 Mid-term Examination";
    public static final String VALID_TITLE_SOCCER = "Soccer Game with the boys";

    public static final String VALID_TIMESLOT_MIDTERM = "23/09/2017 1830-1930";
    public static final String VALID_TIMESLOT_SOCCER = "07/07/2016 1930-2100";

    public static final String VALID_DESCRIPTION_MIDTERM = "MPSH-2A, Seat 727";
    public static final String VALID_DESCRIPTION_SOCCER = "Bring the ball pump and drinks";


    public static final String TITLE_MIDTERM = " " + PREFIX_NAME + VALID_TITLE_MIDTERM;
    public static final String TITLE_SOCCER = " " + PREFIX_NAME + VALID_TITLE_SOCCER;
    public static final String TIMESLOT_MIDTERM = " " + PREFIX_TIMESLOT + VALID_TIMESLOT_MIDTERM;
    public static final String TIMESLOT_SOCCER = " " + PREFIX_TIMESLOT + VALID_TIMESLOT_SOCCER;
    public static final String DESCRIPTION_MIDTERM = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_MIDTERM;
    public static final String DESCRIPTION_SOCCER = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_SOCCER;

    public static final String INVALID_TITLE = " " + PREFIX_NAME + "";
    public static final String INVALID_TIMESLOT = " " + PREFIX_TIMESLOT + "00/2/1999 700-900";

    public static final EditEventCommand.EditEventDescriptor DESC_MIDTERM;
    public static final EditEventCommand.EditEventDescriptor DESC_SOCCER;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withCompany(VALID_COMPANY_AMY).withPosition(VALID_POSITION_AMY)
                .withStatus(VALID_STATUS_AMY).withPriority(VALID_PRIORITY_AMY)
                .withNote(VALID_NOTE_AMY).withPhoto(VALID_PHOTO_AMY).withTags
                (VALID_TAG_FRIEND).withRelation(VALID_REL_SIBLINGS).build();

        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withCompany(VALID_COMPANY_BOB).withPosition(VALID_POSITION_BOB)
                .withStatus(VALID_STATUS_BOB).withPriority(VALID_PRIORITY_BOB)
                .withNote(VALID_NOTE_BOB).withPhoto(VALID_PHOTO_BOB).withTags
                (VALID_TAG_FRIEND).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
                .withRelation(VALID_REL_SIBLINGS).build();

        DESC_MIDTERM = new EditEventDescriptorBuilder().withTitle(VALID_TITLE_MIDTERM)
                .withTimeslot(VALID_TIMESLOT_MIDTERM)
                .withDescription(VALID_DESCRIPTION_MIDTERM).build();
        DESC_SOCCER = new EditEventDescriptorBuilder().withTitle(VALID_TITLE_SOCCER)
                .withTimeslot(VALID_TIMESLOT_SOCCER)
                .withDescription(VALID_DESCRIPTION_SOCCER).build();
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
     * Updates {@code model}'s filtered list to show only the first event in the {@code model}'s address book.
     */
    public static void showFirstEventOnly(Model model) {
        ReadOnlyEvent event = model.getAddressBook().getEventList().get(0);
        final String[] splitEvent = event.getTitle().toString().split("\\s+");
        model.updateFilteredEventList(new TitleContainsKeywordsPredicate(Arrays.asList(splitEvent[0])));

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
}
