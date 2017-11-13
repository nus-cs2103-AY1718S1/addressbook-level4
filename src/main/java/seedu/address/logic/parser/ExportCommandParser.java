package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author aali195
/**
 * Parser for the export command
 */
public class ExportCommandParser implements Parser<ExportCommand> {

    @Override
    public ExportCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        String regex = "[\\s]+";
        String[] splitArgs = userInput.trim().split(regex, 2);

        String path;
        if (splitArgs.length > 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        } else {
            path = splitArgs[0];
        }
        return new ExportCommand(path);
    }
}
//@@author
