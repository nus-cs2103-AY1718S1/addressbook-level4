package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSTAL_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEBSITE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.place.NameContainsKeywordsPredicate;
import seedu.address.model.place.ReadOnlyPlace;
import seedu.address.model.place.exceptions.PlaceNotFoundException;
import seedu.address.testutil.EditPlaceDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_WEBSITE_AMY = "http://www.exampleamy.com";
    public static final String VALID_WEBSITE_BOB = "http://www.examplebob.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_POSTAL_CODE_AMY = "111111";
    public static final String VALID_POSTAL_CODE_BOB = "222222";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String WEBSITE_UNKNOW = "http://www.-.com";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String WEBSITE_DESC_AMY = " " + PREFIX_WEBSITE + VALID_WEBSITE_AMY;
    public static final String WEBSITE_DESC_BOB = " " + PREFIX_WEBSITE + VALID_WEBSITE_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String POSTAL_CODE_DESC_AMY = " " + PREFIX_POSTAL_CODE + VALID_POSTAL_CODE_AMY;
    public static final String POSTAL_CODE_DESC_BOB = " " + PREFIX_POSTAL_CODE + VALID_POSTAL_CODE_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String WEBSITE_UNKNOWN = " " + PREFIX_WEBSITE + WEBSITE_UNKNOW;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_WEBSITE_DESC = " " + PREFIX_WEBSITE + "bobyahoo.com";
    // missing http://wwww symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_POSTALCODE_DESC = " " + PREFIX_POSTAL_CODE + "12345"; // less than 6 digits
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final EditCommand.EditPlaceDescriptor DESC_AMY;
    public static final EditCommand.EditPlaceDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPlaceDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withWebsite(VALID_WEBSITE_AMY).withAddress(VALID_ADDRESS_AMY)
                .withPostalCode(VALID_POSTAL_CODE_AMY).withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPlaceDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withWebsite(VALID_WEBSITE_BOB).withAddress(VALID_ADDRESS_BOB)
                .withPostalCode(VALID_POSTAL_CODE_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
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
     * - the address book and the filtered place list in the {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<ReadOnlyPlace> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPlaceList());

        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getAddressBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredPlaceList());
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the first place in the {@code model}'s address book.
     */
    public static void showFirstPlaceOnly(Model model) {
        ReadOnlyPlace place = model.getAddressBook().getPlaceList().get(0);
        final String[] splitName = place.getName().fullName.split("\\s+");
        model.updateFilteredPlaceList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assert model.getFilteredPlaceList().size() == 1;
    }

    /**
     * Deletes the first place in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstPlace(Model model) {
        ReadOnlyPlace firstPlace = model.getFilteredPlaceList().get(0);
        try {
            model.deletePlace(firstPlace);
        } catch (PlaceNotFoundException pnfe) {
            throw new AssertionError("Place in filtered list must exist in model.", pnfe);
        }
    }
}
