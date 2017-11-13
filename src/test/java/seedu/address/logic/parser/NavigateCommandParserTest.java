package seedu.address.logic.parser;
//@@autho jeffreygohkw

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAVIGATE_FROM_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAVIGATE_FROM_PERSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAVIGATE_FROM_TASK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAVIGATE_TO_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAVIGATE_TO_PERSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAVIGATE_TO_TASK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_TASK;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.NavigateCommand;
import seedu.address.model.Location;

public class NavigateCommandParserTest {
    private NavigateCommandParser parser = new NavigateCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws IllegalValueException {
        //From address to address
        NavigateCommand fata = new NavigateCommand(new Location("NUS"), new Location("Sentosa"),
                null, null, false, false);
        assertParseSuccess(parser, NavigateCommand.COMMAND_WORD + " " + PREFIX_NAVIGATE_FROM_ADDRESS + "NUS"
                + " " + PREFIX_NAVIGATE_TO_ADDRESS + "Sentosa", fata);

        //From address to person
        NavigateCommand fatp = new NavigateCommand(new Location("NUS"), null,
                null, INDEX_FIRST_PERSON, false, false);
        assertParseSuccess(parser, NavigateCommand.COMMAND_WORD + " "  + PREFIX_NAVIGATE_FROM_ADDRESS + "NUS"
                + " " + PREFIX_NAVIGATE_TO_PERSON + "1", fatp);

        //From address to task
        NavigateCommand fatt = new NavigateCommand(new Location("NUS"), null,
                null, INDEX_FIRST_TASK, false, true);
        assertParseSuccess(parser, NavigateCommand.COMMAND_WORD + " "  + PREFIX_NAVIGATE_FROM_ADDRESS + "NUS"
                + " " + PREFIX_NAVIGATE_TO_TASK + "1", fatt);

        //From person to address
        NavigateCommand fpta = new NavigateCommand(null, new Location("Sentosa"),
                INDEX_SECOND_PERSON, null, false, false);
        assertParseSuccess(parser, NavigateCommand.COMMAND_WORD + " "  + PREFIX_NAVIGATE_FROM_PERSON + "2"
                + " " + PREFIX_NAVIGATE_TO_ADDRESS + "Sentosa", fpta);

        //From person to person
        NavigateCommand fptp = new NavigateCommand(null, null,
                INDEX_SECOND_PERSON, INDEX_THIRD_PERSON, false, false);
        assertParseSuccess(parser, NavigateCommand.COMMAND_WORD + " "  + PREFIX_NAVIGATE_FROM_PERSON + "2"
                + " " + PREFIX_NAVIGATE_TO_PERSON + "3", fptp);

        //From person to task
        NavigateCommand fptt = new NavigateCommand(null, null,
                INDEX_SECOND_PERSON, INDEX_SECOND_TASK, false, true);
        assertParseSuccess(parser, NavigateCommand.COMMAND_WORD + " "  + PREFIX_NAVIGATE_FROM_PERSON + "2"
                + " " + PREFIX_NAVIGATE_TO_TASK + "2", fptt);

        //From task to address
        NavigateCommand ftta = new NavigateCommand(null, new Location("Sentosa"),
                INDEX_THIRD_TASK, null, true, false);
        assertParseSuccess(parser, NavigateCommand.COMMAND_WORD + " "  + PREFIX_NAVIGATE_FROM_TASK + "3"
                + " " + PREFIX_NAVIGATE_TO_ADDRESS + "Sentosa", ftta);

        //From task to person
        NavigateCommand fttp = new NavigateCommand(null, null,
                INDEX_THIRD_TASK, INDEX_THIRD_PERSON, true, false);
        assertParseSuccess(parser, NavigateCommand.COMMAND_WORD + " "  + PREFIX_NAVIGATE_FROM_TASK + "3"
                + " " + PREFIX_NAVIGATE_TO_PERSON + "3", fttp);

        //From task to task
        NavigateCommand fttt = new NavigateCommand(null, null,
                INDEX_THIRD_TASK, INDEX_FIRST_TASK, true, true);
        assertParseSuccess(parser, NavigateCommand.COMMAND_WORD + " "  + PREFIX_NAVIGATE_FROM_TASK + "3"
                + " " + PREFIX_NAVIGATE_TO_TASK + "1", fttt);
    }

    @Test
    public void parse_repeatedFromAndTos_failure() {
        //Multiple Froms
        assertParseFailure(parser, NavigateCommand.COMMAND_WORD + " "  + PREFIX_NAVIGATE_FROM_ADDRESS + "NUS"
                + " " + PREFIX_NAVIGATE_FROM_PERSON + "1" + " " + PREFIX_NAVIGATE_TO_ADDRESS + "Sentosa",
                NavigateCommand.MESSAGE_MULTIPLE_FROM_ERROR);

        assertParseFailure(parser, NavigateCommand.COMMAND_WORD + " "  + PREFIX_NAVIGATE_FROM_ADDRESS + "NUS"
                        + " " + PREFIX_NAVIGATE_FROM_TASK + "1" + " " + PREFIX_NAVIGATE_TO_ADDRESS + "Sentosa",
                NavigateCommand.MESSAGE_MULTIPLE_FROM_ERROR);

        assertParseFailure(parser, NavigateCommand.COMMAND_WORD + " "  + PREFIX_NAVIGATE_FROM_TASK + "1"
                + " " + PREFIX_NAVIGATE_FROM_PERSON + "1" + " " + PREFIX_NAVIGATE_TO_ADDRESS + "Sentosa",
                NavigateCommand.MESSAGE_MULTIPLE_FROM_ERROR);

        assertParseFailure(parser, NavigateCommand.COMMAND_WORD + " "  + PREFIX_NAVIGATE_FROM_TASK + "1"
                + " " + PREFIX_NAVIGATE_FROM_PERSON + "1" + " "  + PREFIX_NAVIGATE_FROM_ADDRESS + "NUS"
                + " " + PREFIX_NAVIGATE_TO_ADDRESS + "Sentosa", NavigateCommand.MESSAGE_MULTIPLE_FROM_ERROR);

        //Multiple Tos
        assertParseFailure(parser, NavigateCommand.COMMAND_WORD + " "  + PREFIX_NAVIGATE_FROM_ADDRESS + "NUS"
                + " " + PREFIX_NAVIGATE_TO_PERSON + "1" + " " + PREFIX_NAVIGATE_TO_ADDRESS + "Sentosa",
                NavigateCommand.MESSAGE_MULTIPLE_TO_ERROR);

        assertParseFailure(parser, NavigateCommand.COMMAND_WORD + " "  + PREFIX_NAVIGATE_FROM_ADDRESS + "NUS"
                + " " + PREFIX_NAVIGATE_TO_TASK + "1" + " " + PREFIX_NAVIGATE_TO_ADDRESS + "Sentosa",
                NavigateCommand.MESSAGE_MULTIPLE_TO_ERROR);

        assertParseFailure(parser, NavigateCommand.COMMAND_WORD + " "  + PREFIX_NAVIGATE_FROM_ADDRESS + "NUS"
                + " " + PREFIX_NAVIGATE_TO_PERSON + "1" + " " + PREFIX_NAVIGATE_TO_TASK + "2",
                NavigateCommand.MESSAGE_MULTIPLE_TO_ERROR);

        assertParseFailure(parser, NavigateCommand.COMMAND_WORD + " "  + PREFIX_NAVIGATE_FROM_ADDRESS + "NUS"
                + " "  + PREFIX_NAVIGATE_TO_TASK + "1" + " " + PREFIX_NAVIGATE_TO_PERSON + "1"
                + " " + PREFIX_NAVIGATE_TO_ADDRESS + "Sentosa", NavigateCommand.MESSAGE_MULTIPLE_TO_ERROR);
    }

    @Test
    public void parse_missingFromOrTo_failure() {
        //No To
        assertParseFailure(parser, NavigateCommand.COMMAND_WORD + " "  + PREFIX_NAVIGATE_FROM_ADDRESS + "NUS",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, NavigateCommand.MESSAGE_USAGE));
        //No From
        assertParseFailure(parser, NavigateCommand.COMMAND_WORD + " "  + PREFIX_NAVIGATE_TO_ADDRESS + "NUS",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, NavigateCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_failure() {
        //Non positive indices
        assertParseFailure(parser, NavigateCommand.COMMAND_WORD + " "  + PREFIX_NAVIGATE_FROM_ADDRESS + "NUS"
                + " " + PREFIX_NAVIGATE_TO_PERSON + "-1", ParserUtil.MESSAGE_INVALID_INDEX);
    }
}
