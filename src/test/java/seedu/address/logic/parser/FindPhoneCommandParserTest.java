//@@author quangtdn
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindPhoneCommand;
import seedu.address.model.person.NameContainsPhonePredicate;

public class FindPhoneCommandParserTest {

    private FindPhoneCommandParser parser = new FindPhoneCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPhoneCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        String firstPersonPhone = ALICE.getPhone().toString();
        String secondPersonPhone = BENSON.getPhone().toString();
        FindPhoneCommand expectedFindPhoneCommand =
                new FindPhoneCommand(new NameContainsPhonePredicate(Arrays.asList(firstPersonPhone,
                        secondPersonPhone)));
        assertParseSuccess(parser, firstPersonPhone + " " + secondPersonPhone, expectedFindPhoneCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n " + firstPersonPhone + " \n \t " + secondPersonPhone + "  \t",
                expectedFindPhoneCommand);
    }
}
//@@author
