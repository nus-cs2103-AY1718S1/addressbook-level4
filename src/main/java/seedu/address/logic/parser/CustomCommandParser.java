package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.StringTokenizer;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.CustomCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.customField.CustomField;

/**
 * Parses input arguments and creates a new CustomCommand object
 */
public class CustomCommandParser implements Parser<CustomCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CustomCommand
     * and returns a CustomCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CustomCommand parse(String args) throws ParseException {
        try {
            StringTokenizer st = new StringTokenizer(args);
            Index index = ParserUtil.parseIndex(st.nextToken());
            String customFieldName = st.nextToken();
            String customFieldValue;

            if (st.hasMoreTokens()) {
                customFieldValue = st.nextToken();
            } else {
                customFieldValue = "";
            }

            CustomField customField = new CustomField(customFieldName, customFieldValue);
            return new CustomCommand(index, customField);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CustomCommand.MESSAGE_USAGE));
        }
    }

}
