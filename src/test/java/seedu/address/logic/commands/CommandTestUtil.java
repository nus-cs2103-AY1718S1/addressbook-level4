package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL_LOGIN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL_MESSAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHOTO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERID;
import static seedu.address.logic.parser.NusmodsCommandParser.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.NusmodsCommandParser.PREFIX_TUTORIAL;
import static seedu.address.logic.parser.NusmodsCommandParser.PREFIX_TYPE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
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
    public static final String VALID_PHONE_AMY = "99999999";
    public static final String VALID_PHONE_BOB = "88888888";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_PHOTO_AMY = "default.jpeg";
    public static final String VALID_PHOTO_BOB = "default.jpeg";
    public static final String VALID_BIRTHDATE_AMY = "01/02/1995";
    public static final String VALID_BIRTHDATE_BOB = "03/04/1994";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIENDS = "friends";
    public static final String VALID_TAG_OWESMONEY = "owesMoney";
    public static final String VALID_EMAIL_MESSAGE = "hello";
    public static final String VALID_EMAIL_SUBJECT = "subject";
    public static final String VALID_EMAIL_LOGIN = "bernicefortesting@gmail.com:password1234H";
    public static final String VALID_NAME_JOHN_EMAILTESTER = "JohnEmailTester";
    public static final String VALID_EMAIL_JOHN_EMAILTESTER = "johnfortesting@hotmail.com";
    public static final String VALID_TYPE_ADD = "add";
    public static final String VALID_TYPE_DELETE = "delete";
    public static final String VALID_TYPE_URL = "url";
    public static final String VALID_LESSONSLOT_T5 = "T5";
    public static final String VALID_MODULE_CS2103T = "CS2103T";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL_ADDRESS + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL_ADDRESS + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String PHOTO_DESC_AMY = " " + PREFIX_PHOTO + VALID_PHOTO_AMY;
    public static final String PHOTO_DESC_BOB = " " + PREFIX_PHOTO + VALID_PHOTO_BOB;
    public static final String BIRTHDATE_DESC_AMY = " " + PREFIX_BIRTHDATE + VALID_BIRTHDATE_AMY;
    public static final String BIRTHDATE_DESC_BOB = " " + PREFIX_BIRTHDATE + VALID_BIRTHDATE_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIENDS;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String NAME_DESC_JOHN_EMAILTESTER = " " + PREFIX_NAME + VALID_NAME_JOHN_EMAILTESTER;
    public static final String EMAIL_DESC_JOHN_EMAILTESTER = " " + PREFIX_EMAIL_ADDRESS + VALID_EMAIL_JOHN_EMAILTESTER;
    public static final String TYPE_DESC_ADD = " " + PREFIX_TYPE + VALID_TYPE_ADD;
    public static final String TYPE_DESC_DELETE = " " + PREFIX_TYPE + VALID_TYPE_DELETE;
    public static final String TYPE_DESC_URL = " " + PREFIX_TYPE + VALID_TYPE_URL;
    public static final String MODULE_DESC_CS2103T = " " + PREFIX_MODULE_CODE + VALID_MODULE_CS2103T;
    public static final String LESSON_DESC_CS2103T = " " + PREFIX_TUTORIAL + "T5";

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL_ADDRESS + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_BIRTHDATE_DESC = " " + PREFIX_BIRTHDATE + "35/12/1995"; // no date contains 35
    public static final String INVALID_EMAIL_LOGIN_INPUT = "bernicefortesting@gmail.com:key:key";
    public static final String INVALID_EMAIL_LOGIN_USERNAME = "bernicefortesting@yahoo.com:password";
    public static final String INVALID_TYPE_DESC = " " + PREFIX_TYPE + "asdf"; // Only allowed add, delete and url
    public static final String INVALID_MODULE_DESC_CS2103T = " " + PREFIX_MODULE_CODE + "CSS123456"; // Too Maybe digit
    public static final String INVALID_USERID_DESC = " " + PREFIX_USERID + "johncena@"; // contains '@' symbol


    public static final String NOT_FILLED = "-";
    public static final String IMAGE_STORAGE_BOB = "data/images/bob@example.com.jpg";
    public static final String IMAGE_STORAGE_AMY = "data/images/amy@example.com.jpg";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmailAddress(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withPhoto(VALID_PHOTO_AMY).withTags(VALID_TAG_FRIENDS).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmailAddress(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withPhoto(VALID_PHOTO_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIENDS).build();
    }

    public static final String EMAIL_MESSAGE = " " + PREFIX_EMAIL_MESSAGE + VALID_EMAIL_MESSAGE;
    public static final String EMAIL_SUBJECT = " " + PREFIX_EMAIL_SUBJECT + VALID_EMAIL_SUBJECT;
    public static final String EMAIL_LOGIN = " " + PREFIX_EMAIL_LOGIN + VALID_EMAIL_LOGIN;
    public static final String INVALID_EMAIL_MESSAGE = " " + PREFIX_EMAIL_MESSAGE;
    public static final String INVALID_EMAIL_SUBJECT = " " + PREFIX_EMAIL_SUBJECT;
    public static final String INVALID_EMAIL_LOGIN_LENGTH = " " + PREFIX_EMAIL_LOGIN + INVALID_EMAIL_LOGIN_INPUT;
    public static final String INVALID_EMAIL_LOGIN = " " + PREFIX_EMAIL_LOGIN + INVALID_EMAIL_LOGIN_USERNAME;

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) throws IOException {
        try {
            CommandResult result = command.execute();
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException | IOException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book and the filtered person list in the {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage)
            throws IOException {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<ReadOnlyPerson> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException | IOException e) {
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
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0]),
                Collections.emptyList()));

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
        } catch (IOException ioe) {
            throw new AssertionError("Image must exist");
        }
    }
}
