package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.NoteCommand;
import seedu.address.model.person.Note;

//@@author derrickchua
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the NoteCommand code.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class NoteCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteCommand.MESSAGE_USAGE);

    private NoteCommandParser parser = new NoteCommandParser();

    @Test
    public void parse_validArgs_returnsNoteCommand() {

        String userInputNotes = INDEX_FIRST_PERSON.getOneBased() + " " + "remark";
        String userInputNoNotes = INDEX_FIRST_PERSON.getOneBased() + " ";
        String userInputNotesAmy = INDEX_FIRST_PERSON.getOneBased() + " " + VALID_NOTE_AMY;

        assertParseSuccess(parser, userInputNotes, new NoteCommand(INDEX_FIRST_PERSON, new Note("remark")));
        assertParseSuccess(parser, userInputNoNotes, new NoteCommand(INDEX_FIRST_PERSON, new Note("")));
        assertParseSuccess(parser, userInputNotesAmy, new NoteCommand(INDEX_FIRST_PERSON, new Note(VALID_NOTE_AMY)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", MESSAGE_INVALID_FORMAT);
        // no index and no tag specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + VALID_NOTE_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + VALID_NOTE_AMY, MESSAGE_INVALID_FORMAT);
    }
}
