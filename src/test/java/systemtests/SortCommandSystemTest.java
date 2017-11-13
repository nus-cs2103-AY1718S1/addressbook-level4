package systemtests;

import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.BIRTHDAY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.REMARK_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.parser.ParserUtil.EMPTY_STRING;
import static seedu.address.logic.parser.ParserUtil.SPACE_STRING;
import static seedu.address.testutil.TypicalPersons.AMY;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.model.Model;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;

//@@author justintkj
public class SortCommandSystemTest extends AddressBookSystemTest {
    public static final String PARAM_NAME_CAMELCASE = " NaMe";
    public static final String PARAM_NAME = " name";
    public static final String PARAM_ADDRESS = " address";
    public static final String PARAM_REMARK = " remark";
    public static final String PARAM_BIRTHDAY = " birthday";
    public static final String PARAM_INVALID = " xxx";
    public static final String PARAM_NUMBER = " number";
    public static final String PARAM_FAVOURITE = " favourite";
    public static final String NEW_LINE = " \n";
    public static final String DUPLICATE_EXCEPTION_MESSAGE = "toAdd already exists in the model.";

    @Test
    public void sort() throws Exception {
        Model model = getModel();
        String command;
        String expectedResultMessage;

        /* Case: Sort all persons by name, CamelCase */
        sortExecuteSuccess(model, PARAM_NAME_CAMELCASE);

        /* Case: Sort all persons by null*/
        sortExecuteFail(SPACE_STRING + SPACE_STRING);

        /* Case: Sort all persons by name */
        sortExecuteSuccess(model, PARAM_NAME);

        /* Case: Sort all persons by address */
        sortExecuteSuccess(model, PARAM_ADDRESS);

        /* Case: Sort all persons by number */
        sortExecuteSuccess(model, PARAM_NUMBER);

        /* Case: Sort all persons by remark */
        sortExecuteSuccess(model, PARAM_REMARK);

        /* Case: Sort all persons by birthday */
        sortExecuteSuccess(model, PARAM_BIRTHDAY);

        /* Case: Sort all persons by invalid argument */
        sortExecuteFail(PARAM_INVALID);

        /* Adds a person AMY to the addressBook */
        addAmyToModel(model);

        /* Case: Sort all persons by phone number */
        sortExecuteSuccess(model, PARAM_NUMBER);

        /* Case: Sort all persons by favourite */
        sortExecuteSuccess(model, PARAM_FAVOURITE);
    }

    /**
     * Adds amy into the addressbook model
     *
     * @param model the addressbook mdodel currently in use
     * @throws DuplicatePersonException Amy already exist in model
     */
    private void addAmyToModel(Model model) throws DuplicatePersonException {
        String command;
        ReadOnlyPerson toAdd = AMY;
        command = "   " + AddCommand.COMMAND_WORD + "  " + NAME_DESC_AMY + "  " + PHONE_DESC_AMY + " "
                + EMAIL_DESC_AMY + "   " + ADDRESS_DESC_AMY + "   " + REMARK_DESC_AMY + " "
                + BIRTHDAY_DESC_AMY + " " + TAG_DESC_FRIEND + " ";
        assertCommandSuccess(command, toAdd);
        model.addPerson(toAdd);
    }

    /**
     * Fails to sort given invalid argument
     *
     * @param message invalid argument
     */
    private void sortExecuteFail(String message) {
        String command;
        String expectedResultMessage;
        command = SortCommand.COMMAND_WORD + message;
        expectedResultMessage = SortCommand.MESSAGE_SORT_FAILURE + NEW_LINE + SortCommand.MESSAGE_USAGE;
        assertCommandFailure(command, expectedResultMessage);
    }

    /**
     * Succeeds in sorting given valid argument
     *
     * @param model Addressbook currently being used
     * @param message Valid argument used
     */
    private void sortExecuteSuccess(Model model, String message) {
        String command;
        String expectedResultMessage;
        command = SortCommand.COMMAND_WORD + message;
        expectedResultMessage = SortCommand.MESSAGE_SORT_SUCCESS + message.toLowerCase().trim();
        model.sortPerson(message.toLowerCase().trim());
        assertCommandSuccess(command, model, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, ReadOnlyPerson)} except that the result
     * display box displays {@code expectedResultMessage} and the model related components equal to
     * {@code expectedModel}.
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected(EMPTY_STRING, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
    }
    /**
     * Performs the same verification as {@code assertCommandSuccess(ReadOnlyPerson)}. Executes {@code command}
     * instead.
     *
     * @see AddCommandSystemTest#assertCommandSuccess(ReadOnlyPerson)
     */
    private void assertCommandSuccess(String command, ReadOnlyPerson toAdd) {
        Model expectedModel = getModel();
        try {
            expectedModel.addPerson(toAdd);
        } catch (DuplicatePersonException dpe) {
            throw new IllegalArgumentException(DUPLICATE_EXCEPTION_MESSAGE);
        }
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }
    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();
        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }

}
