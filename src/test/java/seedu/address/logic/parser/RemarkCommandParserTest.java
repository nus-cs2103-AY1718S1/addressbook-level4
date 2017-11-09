package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.lang.reflect.Method;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Remark;

public class RemarkCommandParserTest {

    private RemarkCommandParser parser = new RemarkCommandParser();

    @Test
    public void parseIndexSpecifiedFailure() throws Exception {
        final Remark remark = new Remark("Some remark.");

        // have remarks
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_REMARK.toString() + " " + remark;
        RemarkCommand expectedCommand = new RemarkCommand(INDEX_FIRST_PERSON, remark);
        assertParseSuccess(parser, userInput, expectedCommand);

        // no remarks
        userInput = targetIndex.getOneBased() + " " + PREFIX_REMARK.toString();
        expectedCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parseNoFieldSpecifiedFailure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE);

        // nothing at all
        assertParseFailure(parser, RemarkCommand.COMMAND_WORD, expectedMessage);
    }

    @Test
    public void parseArgumentsNoIndexFailueThrowsParseException() throws Exception {
        assertTrue(null == RemarkCommandParser.parseArguments("rmk", "no index here!"));
        assertTrue(null == RemarkCommandParser.parseArguments("remark", "none here either!"));
    }

    @Test
    public void parseArgumentsIndexInArgumentsReturnsArguments() throws Exception {
        Method setLastRolodexSize = ModelManager.class.getDeclaredMethod("setLastRolodexSize", Integer.TYPE);
        setLastRolodexSize.setAccessible(true);
        setLastRolodexSize.invoke(null, 1);
        assertEquals(" 1 r/someStringV4lue", RemarkCommandParser.parseArguments("rmk", "1someStringV4lue"));
        setLastRolodexSize.invoke(null, 8);
        assertEquals(" 8 r/someStringV4lue", RemarkCommandParser.parseArguments("rmk", "8someStringV4lue"));
    }

    @Test
    public void parseArgumentsIndexInCommandWordReturnsArguments() throws Exception {
        Method setLastRolodexSize = ModelManager.class.getDeclaredMethod("setLastRolodexSize", Integer.TYPE);
        setLastRolodexSize.setAccessible(true);
        setLastRolodexSize.invoke(null, 1);
        assertEquals(" 1 r/", RemarkCommandParser.parseArguments("rmk1", ""));
        setLastRolodexSize.invoke(null, 7);
        assertEquals(" 7 r/", RemarkCommandParser.parseArguments("rmk7", ""));
    }

}
