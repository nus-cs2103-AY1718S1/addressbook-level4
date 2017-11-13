package seedu.address.logic.parser;

//@@author jeffreygohkw
import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ChangePrivacyCommand;
import seedu.address.logic.commands.ChangePrivacyCommand.PersonPrivacySettings;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.testutil.PersonPrivacySettingsBuilder;

public class ChangePrivacyCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangePrivacyCommand.MESSAGE_USAGE);

    private ChangePrivacyCommandParser parser = new ChangePrivacyCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", ChangePrivacyCommand.MESSAGE_NO_FIELDS);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // Non boolean argument
        assertParseFailure(parser, "1" + " " + PREFIX_NAME + "notBoolean",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangePrivacyCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1" + " " + PREFIX_PHONE + "notBoolean",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangePrivacyCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1" + " " + PREFIX_EMAIL + "notBoolean",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangePrivacyCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1" + " " + PREFIX_ADDRESS + "notBoolean",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangePrivacyCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1" + " " + PREFIX_REMARK + "notBoolean",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangePrivacyCommand.MESSAGE_USAGE));

        // valid value followed by invalid value. The test case for invalid value  followed by valid value
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + " " + PREFIX_NAME + "true" + " " + PREFIX_NAME + "notBoolean",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangePrivacyCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1" + " " + PREFIX_PHONE + "true" + " " + PREFIX_PHONE + "notBoolean",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangePrivacyCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1" + " " + PREFIX_EMAIL + "true" + " " + PREFIX_EMAIL + "notBoolean",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangePrivacyCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1" + " " + PREFIX_ADDRESS + "true" + " " + PREFIX_ADDRESS + "notBoolean",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangePrivacyCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1" + " " + PREFIX_REMARK + "true" + " " + PREFIX_REMARK + "notBoolean",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangePrivacyCommand.MESSAGE_USAGE));
    }


    @Test
    public void parse_allFieldsSpecified_success() throws ParseException {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_NAME + "true" + " " + PREFIX_EMAIL + "false"
                + " " + PREFIX_ADDRESS + "true" + " " + PREFIX_PHONE + "false";

        PersonPrivacySettings pps = new PersonPrivacySettingsBuilder().setNamePrivate("true")
                .setEmailPrivate("false").setAddressPrivate("true").setPhonePrivate("false").build();
        ChangePrivacyCommand expectedCommand = new ChangePrivacyCommand(targetIndex, pps);

        ChangePrivacyCommand actualCommand = parser.parse(userInput);

        compareChangePrivacyCommand(expectedCommand, actualCommand);

    }

    @Test
    public void parse_someFieldsSpecified_success() throws ParseException {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_NAME + "true" + " " + PREFIX_EMAIL + "true";

        PersonPrivacySettings pps = new PersonPrivacySettingsBuilder().setNamePrivate("true")
                .setEmailPrivate("true").build();
        ChangePrivacyCommand expectedCommand = new ChangePrivacyCommand(targetIndex, pps);

        ChangePrivacyCommand actualCommand = parser.parse(userInput);

        compareChangePrivacyCommand(expectedCommand, actualCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() throws ParseException {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_NAME + "true";

        PersonPrivacySettings pps = new PersonPrivacySettingsBuilder().setNamePrivate("true").build();
        ChangePrivacyCommand expectedCommand = new ChangePrivacyCommand(targetIndex, pps);

        ChangePrivacyCommand actualCommand = parser.parse(userInput);

        compareChangePrivacyCommand(expectedCommand, actualCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() throws ParseException {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_NAME + "true" + " " + PREFIX_EMAIL + "false"
                + " " + PREFIX_ADDRESS + "true" + " " + PREFIX_PHONE + "false" + " " + PREFIX_NAME + "false" + " "
                + PREFIX_EMAIL + "true" + " " + PREFIX_ADDRESS + "false" + " " + PREFIX_PHONE + "true";

        PersonPrivacySettings pps = new PersonPrivacySettingsBuilder().setNamePrivate("false")
                .setEmailPrivate("true").setAddressPrivate("false").setPhonePrivate("true").build();
        ChangePrivacyCommand expectedCommand = new ChangePrivacyCommand(targetIndex, pps);

        ChangePrivacyCommand actualCommand = parser.parse(userInput);

        compareChangePrivacyCommand(expectedCommand, actualCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() throws ParseException {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_NAME + "notBoolean" + " " + PREFIX_NAME + "true";

        PersonPrivacySettings pps = new PersonPrivacySettingsBuilder().setNamePrivate("true").build();
        ChangePrivacyCommand expectedCommand = new ChangePrivacyCommand(targetIndex, pps);

        ChangePrivacyCommand actualCommand = parser.parse(userInput);

        compareChangePrivacyCommand(expectedCommand, actualCommand);
    }

    /**
     * Checks if two ChangePrivacyCommands are equal by comparing their contents
     * @param expectedCommand The expected ChangePrivacyCommand
     * @param actualCommand The actual ChangePrivacyCommand
     */
    private void compareChangePrivacyCommand(ChangePrivacyCommand expectedCommand, ChangePrivacyCommand actualCommand) {
        assertEquals(expectedCommand.getIndex(), actualCommand.getIndex());
        assertEquals(expectedCommand.getPps().getAddressIsPrivate(), actualCommand.getPps().getAddressIsPrivate());
        assertEquals(expectedCommand.getPps().getNameIsPrivate(), actualCommand.getPps().getNameIsPrivate());
        assertEquals(expectedCommand.getPps().getEmailIsPrivate(), actualCommand.getPps().getEmailIsPrivate());
        assertEquals(expectedCommand.getPps().getPhoneIsPrivate(), actualCommand.getPps().getPhoneIsPrivate());
        assertEquals(expectedCommand.getPps().getRemarkIsPrivate(), actualCommand.getPps().getRemarkIsPrivate());
    }
}
