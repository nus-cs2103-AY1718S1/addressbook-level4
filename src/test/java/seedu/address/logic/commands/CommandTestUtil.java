package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEBT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HANDPHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOME_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTEREST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OFFICE_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSTAL_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TOTAL_DEBT;

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
    public static final String VALID_HANDPHONE_AMY = "91111111";
    public static final String VALID_HANDPHONE_BOB = "92222222";
    public static final String VALID_HOME_PHONE_AMY = "61111111";
    public static final String VALID_HOME_PHONE_BOB = "62222222";
    public static final String VALID_OFFICE_PHONE_AMY = "60000001";
    public static final String VALID_OFFICE_PHONE_BOB = "60000002";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_POSTAL_CODE_AMY = "101312";
    public static final String VALID_POSTAL_CODE_BOB = "102123";
    public static final String VALID_DEBT_AMY = "100002";
    public static final String VALID_DEBT_BOB = "100001";
    public static final String VALID_INTEREST_AMY = "2";
    public static final String VALID_INTEREST_BOB = "3";
    public static final String VALID_DEADLINE_AMY = "11-11-2020";
    public static final String VALID_DEADLINE_BOB = "11-12-2021";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String HANDPHONE_DESC_AMY = " " + PREFIX_HANDPHONE + VALID_HANDPHONE_AMY;
    public static final String HANDPHONE_DESC_BOB = " " + PREFIX_HANDPHONE + VALID_HANDPHONE_BOB;
    public static final String HOME_PHONE_DESC_AMY = " " + PREFIX_HOME_PHONE + VALID_HOME_PHONE_AMY;
    public static final String HOME_PHONE_DESC_BOB = " " + PREFIX_HOME_PHONE + VALID_HOME_PHONE_BOB;
    public static final String OFFICE_PHONE_DESC_AMY = " " + PREFIX_OFFICE_PHONE + VALID_OFFICE_PHONE_AMY;
    public static final String OFFICE_PHONE_DESC_BOB = " " + PREFIX_OFFICE_PHONE + VALID_OFFICE_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String POSTAL_CODE_DESC_AMY = " " + PREFIX_POSTAL_CODE + VALID_POSTAL_CODE_AMY;
    public static final String POSTAL_CODE_DESC_BOB = " " + PREFIX_POSTAL_CODE + VALID_POSTAL_CODE_BOB;
    public static final String DEBT_DESC_AMY = " " + PREFIX_DEBT + VALID_DEBT_AMY;
    public static final String DEBT_DESC_BOB = " " + PREFIX_DEBT + VALID_DEBT_BOB;
    public static final String TOTAL_DEBT_DESC_AMY = " " + PREFIX_TOTAL_DEBT + VALID_DEBT_AMY;
    public static final String TOTAL_DEBT_DESC_BOB = " " + PREFIX_TOTAL_DEBT + VALID_DEBT_BOB;
    public static final String INTEREST_DESC_AMY = " " + PREFIX_INTEREST + VALID_INTEREST_AMY;
    public static final String INTEREST_DESC_BOB = " " + PREFIX_INTEREST + VALID_INTEREST_BOB;
    public static final String DEADLINE_DESC_AMY = " " + PREFIX_DEADLINE + VALID_DEADLINE_AMY;
    public static final String DEADLINE_DESC_BOB = " " + PREFIX_DEADLINE + VALID_DEADLINE_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_HANDPHONE_DESC = " " + PREFIX_HANDPHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_HOME_PHONE_DESC = " " + PREFIX_HOME_PHONE + "9111"; // not 8 digits
    public static final String INVALID_OFFICE_PHONE_DESC = " " + PREFIX_OFFICE_PHONE + "nineoneone"; // not 8 digits
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_POSTAL_CODE_DESC = " " + PREFIX_POSTAL_CODE + "12345"; // not 6 digits
    public static final String INVALID_DEBT_DESC = " " + PREFIX_DEBT + "onehundred"; // String not allowed in debt
    public static final String INVALID_TOTAL_DEBT_DESC = " " + PREFIX_TOTAL_DEBT + "0"; // String not allowed in debt
    public static final String INVALID_INTEREST_DESC = " " + PREFIX_INTEREST + "two"; // String not allowed in interest
    public static final String INVALID_DEADLINE_DESC = " " + PREFIX_DEADLINE + "0-0-2017"; //Only accepts range
    // of [1-12]
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String ONE_OR_MORE_SPACES_REGEX = "\\s+";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).withHandphone(VALID_HANDPHONE_AMY)
                .withHomePhone(VALID_HOME_PHONE_AMY).withOfficePhone(VALID_OFFICE_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withPostalCode(VALID_POSTAL_CODE_AMY).withDebt(VALID_DEBT_AMY)
                .withInterest(VALID_INTEREST_AMY).withDeadline(VALID_DEADLINE_AMY).withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).withHandphone(VALID_HANDPHONE_BOB)
                .withHomePhone(VALID_HOME_PHONE_BOB).withOfficePhone(VALID_OFFICE_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withPostalCode(VALID_POSTAL_CODE_BOB).withDebt(VALID_DEBT_BOB)
                .withInterest(VALID_INTEREST_BOB).withDeadline(VALID_DEADLINE_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
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
        final String[] splitName = person.getName().fullName.split(ONE_OR_MORE_SPACES_REGEX);
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assert model.getFilteredPersonList().size() == 1;
    }

    /**
     * Updates {@code model}'s filtered list to show only the first person in the {@code model}'s address book.
     */
    public static void showFirstBlacklistedPersonOnly(Model model) {
        ReadOnlyPerson person = model.getAddressBook().getBlacklistedPersonList().get(0);
        final String[] splitName = person.getName().fullName.split(ONE_OR_MORE_SPACES_REGEX);
        model.updateFilteredBlacklistedPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assert model.getFilteredBlacklistedPersonList().size() == 1;
    }

    /**
     * Updates {@code model}'s filtered whitelist to show only the first person in the {@code model}'s address book.
     */
    public static void showFirstWhitelistedPersonOnly(Model model) {
        ReadOnlyPerson person = model.getAddressBook().getWhitelistedPersonList().get(0);
        final String[] splitName = person.getName().fullName.split(ONE_OR_MORE_SPACES_REGEX);
        model.updateFilteredWhitelistedPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assert model.getFilteredWhitelistedPersonList().size() == 1;
    }

    /**
     * Updates {@code model}'s filtered overdue list to show only the first person in the {@code model}'s address book.
     */
    public static void showFirstOverdueDebtPersonOnly(Model model) {
        ReadOnlyPerson person = model.getAddressBook().getOverduePersonList().get(0);
        final String[] splitName = person.getName().fullName.split(ONE_OR_MORE_SPACES_REGEX);
        model.updateFilteredOverduePersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assert model.getFilteredOverduePersonList().size() == 1;
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
