package systemtests;

import static seedu.room.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.room.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.room.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.room.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.room.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.room.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.room.logic.commands.CommandTestUtil.INVALID_ROOM_DESC;
import static seedu.room.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.room.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.room.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.room.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.room.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.room.logic.commands.CommandTestUtil.ROOM_DESC_AMY;
import static seedu.room.logic.commands.CommandTestUtil.ROOM_DESC_BOB;
import static seedu.room.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.room.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.room.logic.commands.CommandTestUtil.TIMESTAMP_DESC_AMY;
import static seedu.room.logic.commands.CommandTestUtil.TIMESTAMP_DESC_BOB;
import static seedu.room.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.room.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.room.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.room.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.room.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.room.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.room.logic.commands.CommandTestUtil.VALID_ROOM_AMY;
import static seedu.room.logic.commands.CommandTestUtil.VALID_ROOM_BOB;
import static seedu.room.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.room.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.room.testutil.TypicalPersons.ALICE;
import static seedu.room.testutil.TypicalPersons.AMY;
import static seedu.room.testutil.TypicalPersons.BOB;
import static seedu.room.testutil.TypicalPersons.CARL;
import static seedu.room.testutil.TypicalPersons.HOON;
import static seedu.room.testutil.TypicalPersons.IDA;
import static seedu.room.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.room.commons.core.Messages;
import seedu.room.logic.commands.AddCommand;
import seedu.room.logic.commands.ClearCommand;
import seedu.room.logic.commands.FindCommand;
import seedu.room.logic.commands.RedoCommand;
import seedu.room.logic.commands.SelectCommand;
import seedu.room.logic.commands.UndoCommand;
import seedu.room.model.Model;
import seedu.room.model.person.Email;
import seedu.room.model.person.Name;
import seedu.room.model.person.Phone;
import seedu.room.model.person.ReadOnlyPerson;
import seedu.room.model.person.Room;
import seedu.room.model.person.exceptions.DuplicatePersonException;
import seedu.room.model.tag.Tag;
import seedu.room.testutil.PersonBuilder;
import seedu.room.testutil.PersonUtil;

public class AddCommandSystemTest extends ResidentBookSystemTest {

    @Test
    public void add() throws Exception {
        Model model = getModel();
        /* Case: add a person without tags to a non-empty resident book, command with leading spaces and trailing spaces
         * -> added
         */
        ReadOnlyPerson toAdd = AMY;
        String command = "   " + AddCommand.COMMAND_WORD + "  " + NAME_DESC_AMY + "  " + PHONE_DESC_AMY + " "
                + EMAIL_DESC_AMY + "   " + ROOM_DESC_AMY + "   " + TIMESTAMP_DESC_AMY + "   " + TAG_DESC_FRIEND
                + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Amy to the list -> Amy deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Amy to the list -> Amy added again */
        command = RedoCommand.COMMAND_WORD;
        model.addPerson(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a duplicate person -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ROOM_DESC_AMY
                + TAG_DESC_FRIEND;
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: add a duplicate person except with different tags -> rejected */
        // "friends" is an existing tag used in the default model, see TypicalPersons#ALICE
        // This test will fail is a new tag that is not in the model is used, see the bug documented in
        // ResidentBook#addPerson(ReadOnlyPerson)
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ROOM_DESC_AMY
                + " " + PREFIX_TAG.getPrefix() + "friends";
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_PERSON);

        /* Case: add a person with all fields same as another person in the resident book except name -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withRoom(VALID_ROOM_AMY).withTemporary(0).withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_AMY + EMAIL_DESC_AMY + ROOM_DESC_AMY
                + TIMESTAMP_DESC_BOB + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person with all fields same as another person in the resident book except phone -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY)
                .withRoom(VALID_ROOM_AMY).withTemporary(0).withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_AMY + ROOM_DESC_AMY
                + TIMESTAMP_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person with all fields same as another person in the resident book except email -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_BOB)
                .withRoom(VALID_ROOM_AMY).withTemporary(0).withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_BOB + ROOM_DESC_AMY
                + TIMESTAMP_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: add a person with all fields same as another person in the resident book except room -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withRoom(VALID_ROOM_BOB).withTemporary(0).withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ROOM_DESC_BOB
                + TIMESTAMP_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: filters the person list before adding -> added */
        executeCommand(FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_MEIER);
        assert getModel().getFilteredPersonList().size()
                < getModel().getResidentBook().getPersonList().size();
        assertCommandSuccess(IDA);

        /* Case: add to empty resident book -> added */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getResidentBook().getPersonList().size() == 0;
        assertCommandSuccess(ALICE);

        /* Case: add a person with tags, command with parameters in random order -> added */
        toAdd = BOB;
        command = AddCommand.COMMAND_WORD + TAG_DESC_FRIEND + PHONE_DESC_BOB + ROOM_DESC_BOB + NAME_DESC_BOB
                + TAG_DESC_HUSBAND + EMAIL_DESC_BOB;
        assertCommandSuccess(command, toAdd);

        /* Case: selects first card in the person list, add a person -> added, card selection remains unchanged */
        executeCommand(SelectCommand.COMMAND_WORD + " 1");
        assert getPersonListPanel().isAnyCardSelected();
        assertCommandSuccess(CARL);

        /* Case: add a person, missing tags -> added */
        assertCommandSuccess(HOON);

        /* Case: missing name -> rejected */
        command = AddCommand.COMMAND_WORD + PHONE_DESC_AMY + EMAIL_DESC_AMY + ROOM_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + PersonUtil.getPersonDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = AddCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_AMY + EMAIL_DESC_AMY + ROOM_DESC_AMY;
        assertCommandFailure(command, Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid phone -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + INVALID_PHONE_DESC + EMAIL_DESC_AMY + ROOM_DESC_AMY;
        assertCommandFailure(command, Phone.MESSAGE_PHONE_CONSTRAINTS);

        /* Case: invalid email -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + INVALID_EMAIL_DESC + ROOM_DESC_AMY;
        assertCommandFailure(command, Email.MESSAGE_EMAIL_CONSTRAINTS);

        /* Case: invalid room -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + INVALID_ROOM_DESC;
        assertCommandFailure(command, Room.MESSAGE_ROOM_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ROOM_DESC_AMY
                + INVALID_TAG_DESC;
        assertCommandFailure(command, Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and verifies that the command box displays
     * an empty string, the result display box displays the success message of executing {@code AddCommand} with the
     * details of {@code toAdd}, and the model related components equal to the current model added with {@code toAdd}.
     * These verifications are done by
     * {@code ResidentBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class, the status bar's sync status changes,
     * the browser url and selected card remains unchanged.
     * @see ResidentBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(ReadOnlyPerson toAdd) {
        assertCommandSuccess(PersonUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(ReadOnlyPerson)}. Executes {@code command}
     * instead.
     * @see AddCommandSystemTest#assertCommandSuccess(ReadOnlyPerson)
     */
    private void assertCommandSuccess(String command, ReadOnlyPerson toAdd) {
        Model expectedModel = getModel();
        try {
            expectedModel.addPerson(toAdd);
        } catch (DuplicatePersonException dpe) {
            throw new IllegalArgumentException("toAdd already exists in the model.");
        }
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);
        if (toAdd.getTimestamp().toString() == "null") {
            assertCommandSuccess(command, expectedModel, expectedResultMessage);
        } else {
            assertCommandSuccess(command, expectedModel, expectedResultMessage
                    + "\n" + AddCommand.MESSAGE_TEMPORARY_PERSON);
        }

    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, ReadOnlyPerson)} except that the result
     * display box displays {@code expectedResultMessage} and the model related components equal to
     * {@code expectedModel}.
     * @see AddCommandSystemTest#assertCommandSuccess(String, ReadOnlyPerson)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code ResidentBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see ResidentBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
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
