package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TYPE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.LESSON_DESC_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.MODULE_DESC_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.TYPE_DESC_ADD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LESSONSLOT_T5;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_CS2103T;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_ADD;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.NusmodsCommandParser.PREFIX_TYPE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.NusmodsCommand;
import seedu.address.logic.commands.NusmodsCommand.NusmodsDescriptor;
import seedu.address.testutil.NusmodsDescriptorBuilder;

//@@author ritchielq-reuse
public class NusmodsCommandParserTest {

    private static final String TYPE_EMPTY = " " + PREFIX_TYPE;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, NusmodsCommand.MESSAGE_USAGE);

    private NusmodsCommandParser parser = new NusmodsCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, TYPE_DESC_ADD + MODULE_DESC_CS2103T, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", NusmodsCommand.MESSAGE_INVALID_TYPE);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + TYPE_DESC_ADD + MODULE_DESC_CS2103T, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + TYPE_DESC_ADD + MODULE_DESC_CS2103T, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_TYPE_DESC + MODULE_DESC_CS2103T,
                NusmodsCommand.MESSAGE_INVALID_TYPE); // Invalid type
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + TYPE_DESC_ADD + MODULE_DESC_CS2103T + LESSON_DESC_CS2103T;

        NusmodsDescriptor descriptor = new NusmodsDescriptorBuilder().withType(VALID_TYPE_ADD)
                .withModuleCode(VALID_MODULE_CS2103T).withTutorial(VALID_LESSONSLOT_T5).build();
        NusmodsCommand expectedCommand = new NusmodsCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + TYPE_DESC_ADD + MODULE_DESC_CS2103T;

        NusmodsDescriptor descriptor = new NusmodsDescriptorBuilder().withType(VALID_TYPE_ADD)
                .withModuleCode(VALID_MODULE_CS2103T).build();
        NusmodsCommand expectedCommand = new NusmodsCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
