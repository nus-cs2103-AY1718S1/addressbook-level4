package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MOD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHOTO;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
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
    public static final String VALID_NAME_CARRIE = "Carrie Chung";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_PHONE_CARRIE = "33333333";
    public static final String VALID_BIRTHDAY_AMY = "11/11/1991";
    public static final String VALID_BIRTHDAY_BOB = "22/22/1992";
    public static final String VALID_BIRTHDAY_CARRIE = "33/33/1993";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_EMAIL_CARRIE = "carrie@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_ADDRESS_CARRIE = "Block 231, Carrie Street 3";
    public static final String VALID_MOD_CS2103 = "CS2103";
    public static final String VALID_MOD_CS2101 = "CS2101";
    public static final String VALID_MOD_GER1000 = "GER1000";
    public static final String VALID_WEB_PHOTO_URL = "https://cdn.images.express.co.uk/img/dynamic/36/590x"
            + "/secondary/CHRIS-EVANS-865133.jpg";
    public static final String VALID_LOCAL_PHOTO_URL = "file://"
            + Paths.get("src/main/resources/images/defaultPhoto.png").toAbsolutePath().toUri().getPath();
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
    public static final String MOD_DESC_CS2103 = " " + PREFIX_MOD + VALID_MOD_CS2103;
    public static final String MOD_DESC_CS2101 = " " + PREFIX_MOD + VALID_MOD_CS2101;
    public static final String MOD_DESC_GER1000 = " " + PREFIX_MOD + VALID_MOD_GER1000;
    public static final String URL_DESC_WEB = " " + PREFIX_PHOTO + VALID_WEB_PHOTO_URL;
    public static final String URL_DESC_LOCAL = " " + PREFIX_PHOTO + VALID_LOCAL_PHOTO_URL;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_BIRTHDAY_DESC = " " + PREFIX_BIRTHDAY + "11/01/19955";
    // more than 8 numbers
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_MOD_DESC = " " + PREFIX_MOD + "hubby*"; // '*' not allowed in mods
    public static final String INVALID_URL_DESC = " " + PREFIX_PHOTO + "images/defaultPhoto.png"; //Not a valid URL

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;
    public static final EditCommand.EditPersonDescriptor DESC_CARRIE;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhones(VALID_PHONE_AMY).withBirthday(VALID_BIRTHDAY_AMY).withEmails(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withMods(VALID_MOD_CS2101).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhones(VALID_PHONE_BOB).withBirthday(VALID_BIRTHDAY_BOB).withEmails(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withMods(VALID_MOD_GER1000, VALID_MOD_CS2101).build();
        DESC_CARRIE = new EditPersonDescriptorBuilder().withName(VALID_NAME_CARRIE)
                .withPhones(VALID_PHONE_CARRIE).withBirthday(VALID_BIRTHDAY_CARRIE).withEmails(VALID_EMAIL_CARRIE)
                .withAddress(VALID_ADDRESS_CARRIE).withMods(VALID_MOD_CS2101).build();
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
