package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandTest;
import seedu.address.logic.commands.BorrowCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Debt;

//@@author jelneo
public class BorrowCommandParserTest extends CommandTest {

    private static final String MESSAGE_INVALID_FORMAT =  String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            BorrowCommand.MESSAGE_USAGE);
    private static final String VALID_DEBT_FIGURE = "500.20";
    private static final String INVALID_DEBT_FIGURE = "-500";
    private static final String VALID_INDEX = "1";
    private static final String INVALID_INDEX = "-1";

    private BorrowCommandParser parser  = new BorrowCommandParser();

    @Test
    public void parse_missingParts_failure() throws Exception {
        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidArguments() throws Exception {
        // invalid index and amount
        assertParseFailure(parser, INVALID_INDEX + " " + INVALID_DEBT_FIGURE, MESSAGE_INVALID_FORMAT);

        // valid index but invalid amount
        assertParseFailure(parser, VALID_INDEX + " " + INVALID_DEBT_FIGURE, MESSAGE_INVALID_FORMAT);

        // invalid index but valid amount
        assertParseFailure(parser, INVALID_INDEX + " " + VALID_DEBT_FIGURE, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validArguments() {
        try {
            Index index = Index.fromOneBased(Integer.valueOf(VALID_INDEX));
            Debt amount = new Debt(VALID_DEBT_FIGURE);
            BorrowCommand expectedBorrowCommand = new BorrowCommand(index, amount);
            assertParseSuccess(parser, VALID_INDEX + " " + VALID_DEBT_FIGURE, expectedBorrowCommand);

            amount = new Debt(VALID_DEBT_FIGURE);
            expectedBorrowCommand = new BorrowCommand(amount);
            assertParseSuccess(parser, VALID_DEBT_FIGURE, expectedBorrowCommand);
        } catch (IllegalValueException | CommandException e) {
            e.printStackTrace();
        }
    }
}
