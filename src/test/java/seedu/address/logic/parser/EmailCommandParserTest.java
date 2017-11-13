package seedu.address.logic.parser;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.lang.reflect.Method;

import org.junit.Test;

import seedu.address.logic.commands.EmailCommand;
import seedu.address.model.ModelManager;

public class EmailCommandParserTest {
    private EmailCommandParser parser = new EmailCommandParser();

    @Test
    public void parseSubjectAbsentSuccess() {
        assertParseSuccess(parser, "1", new EmailCommand(INDEX_FIRST_PERSON, ""));
    }

    @Test
    public void parseSubjectPresentSuccess() {
        assertParseSuccess(parser, "1 s/hello", new EmailCommand(INDEX_FIRST_PERSON, "hello"));
    }

    @Test
    public void parseSubjectPresentWithSpacingSuccess() {
        assertParseSuccess(parser, "1 s/hello world", new EmailCommand(INDEX_FIRST_PERSON, "hello%20world"));
    }

    @Test
    public void parseInvalidArgsThrowsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseInvalidPersonIndexThrowsParseException() {
        assertParseFailure(parser, "0", String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseArgumentsNoIndexFailueThrowsParseException() throws Exception {
        assertTrue(null == EmailCommandParser.parseArguments("mailto", "no index here!"));
        assertTrue(null == EmailCommandParser.parseArguments("send", "none here either!"));
    }

    @Test
    public void parseArgumentsIndexInArgumentsReturnsArguments() throws Exception {
        Method setLastRolodexSize = ModelManager.class.getDeclaredMethod("setLastRolodexSize", Integer.TYPE);
        setLastRolodexSize.setAccessible(true);
        setLastRolodexSize.invoke(null, 1);
        assertEquals(" 1 s/some String V4lue", EmailCommandParser.parseArguments("email", "1some String V4lue"));
        setLastRolodexSize.invoke(null, 8);
        assertEquals(" 8 s/someStringV4lue", EmailCommandParser.parseArguments("mail", "8someStringV4lue"));
    }

    @Test
    public void parseArgumentsIndexInCommandWordReturnsArguments() throws Exception {
        Method setLastRolodexSize = ModelManager.class.getDeclaredMethod("setLastRolodexSize", Integer.TYPE);
        setLastRolodexSize.setAccessible(true);
        setLastRolodexSize.invoke(null, 1);
        assertEquals(" 1 s/", EmailCommandParser.parseArguments("mail1", ""));
        setLastRolodexSize.invoke(null, 7);
        assertEquals(" 7 s/", EmailCommandParser.parseArguments("send7", ""));
    }
}
