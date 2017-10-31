package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindCommand.FindPersonDescriptor;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    //@@author chrisboo
    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", FindCommand.MESSAGE_NO_FIELD_PROVIDED);
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindPersonDescriptor person = new FindPersonDescriptor();

        try {
            person.setName(new Name("Alice"));
            person.setPhone(new Phone("123456"));
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }

        FindCommand expectedFindCommand = new FindCommand(person);
        assertParseSuccess(parser, " n/Alice p/123456", expectedFindCommand);
    }
    //@@author
}
