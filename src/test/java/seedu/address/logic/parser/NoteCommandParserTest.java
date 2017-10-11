package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.NoteCommand;
import seedu.address.model.person.Note;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the NoteCommand code.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class NoteCommandParserTest {

    private NoteCommandParser parser = new NoteCommandParser();

    @Test
    public void parse_validArgs_returnsNoteCommand() {

        String userInputNotes = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_NOTE + "remark";
        String userInputNoNotes = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_NOTE + "";

        assertParseSuccess(parser, userInputNotes, new NoteCommand(INDEX_FIRST_PERSON, new Note("emark")));
        assertParseSuccess(parser, userInputNoNotes, new NoteCommand(INDEX_FIRST_PERSON, new Note("")));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 t/", String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteCommand.MESSAGE_USAGE));
    }
}
