package seedu.address.logic.parser;
//@@author yangminxingnus
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_REMARK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Remark;
import seedu.address.testutil.RemarkBuilder;

public class RemarkCommandParserTest {

    private RemarkCommandParser parser = new RemarkCommandParser();

    @Test
    public void parse_validValue_success() throws ParseException, IllegalValueException {

        Remark expectedRemark = new RemarkBuilder().withDetails(VALID_REMARK_AMY);

        assertParseSuccess(parser, " 1 " + PREFIX_REMARK + VALID_REMARK_AMY,
                new RemarkCommand(1, expectedRemark));

    }

    @Test
    public void parse_voidValue_success() throws ParseException, IllegalValueException {

        Remark expectedRemark = new RemarkBuilder().withDetails("");

        assertParseSuccess(parser, " 1 " + PREFIX_REMARK + "",
                new RemarkCommand(1, expectedRemark));

    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid remark
        assertParseFailure(parser, " 1 " + PREFIX_REMARK + INVALID_REMARK_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));

    }
}
//@@author
