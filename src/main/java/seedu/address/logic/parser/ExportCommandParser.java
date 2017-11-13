package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author freesoup
/**
 * Parses path of file given by user and ensures that it is of .vcf or .xml file type
 */
public class ExportCommandParser implements Parser<ExportCommand> {

    @Override
    public ExportCommand parse(String userInput) throws ParseException {
        String path = userInput.trim();
        if (path.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        }
        if (!path.endsWith(".xml") && !path.endsWith(".vcf")) {
            throw new ParseException(ExportCommand.MESSAGE_WRONG_FILE_TYPE);
        }
        return new ExportCommand(path);
    }
}
