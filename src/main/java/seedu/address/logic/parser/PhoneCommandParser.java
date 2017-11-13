package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.PhoneCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.phone.Phone;

/**
 * Parses input arguments and creates a new object
 */
//@@author eeching
public class PhoneCommandParser implements Parser<PhoneCommand> {

    private static final String BY_NAME_IDENTIFIER = "byName";
    /**
     * Parses the given {@code String} of arguments in the context of the PhoneCommand
     * and returns a PhoneCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public PhoneCommand parse(String args) throws ParseException {

        try {
            StringTokenizer st = new StringTokenizer(args);
            String personIdentifier = st.nextToken();
            String action = st.nextToken();
            String value = st.nextToken();
            Phone phone = new Phone(value);


            //if the index of the target is passed to identify the person
            try {
                Integer.parseInt(personIdentifier);
                Index index = ParserUtil.parseIndex(personIdentifier);
                return new PhoneCommand(index, action, phone);
            } catch (Exception e) { //name is passed to identify the person
                if (personIdentifier.equals(BY_NAME_IDENTIFIER)) {
                    String rawName = "";
                    while (st.hasMoreTokens()) {
                        rawName = rawName + " " + st.nextToken();
                    }
                    try {
                        String name = ParserUtil.parseNameString(rawName.trim());
                        return new PhoneCommand(name, action, phone);
                    } catch (IllegalValueException ive) {
                        throw new ParseException(
                                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PhoneCommand.MESSAGE_USAGE));
                    }
                } else {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, PhoneCommand.MESSAGE_USAGE));
                }
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PhoneCommand.MESSAGE_USAGE));
        } catch (NoSuchElementException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PhoneCommand.MESSAGE_USAGE));
        }
    }

}
