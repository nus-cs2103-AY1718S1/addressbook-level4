
//@@author aali195
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parser for the export command
 */
public class ExportCommandParser implements Parser<ExportCommand> {


    @Override
    public ExportCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);

        String path = userInput;
        return new ExportCommand(path);
    }
}

//@@author
