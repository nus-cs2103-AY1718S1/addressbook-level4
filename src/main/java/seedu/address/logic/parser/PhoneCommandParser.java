package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.StringTokenizer;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.CustomCommand;
import seedu.address.logic.commands.PhoneCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.phone.Phone;

/**
 * Parses input arguments and creates a new object
 */
public class PhoneCommandParser implements Parser<PhoneCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PhoneCommand
     * and returns a PhoneCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PhoneCommand parse(String args) throws ParseException {
        try {
            StringTokenizer st = new StringTokenizer(args);
            Index index = ParserUtil.parseIndex(st.nextToken());
            String action = st.nextToken();
            String value = "00000";
            if (st.hasMoreTokens()) {
                value = st.nextToken();
            }

            Phone phone = new Phone(value);
            return new PhoneCommand(index, action, phone);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CustomCommand.MESSAGE_USAGE));
        }
    }

}
