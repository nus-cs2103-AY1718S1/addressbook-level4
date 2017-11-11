package seedu.address.logic.parser;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.BackupCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author icehawker
/**
 * Parses input arguments to create directory String used in BackupCommand
 */
public class BackupCommandParser implements Parser<BackupCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the BackupCommand
     * and returns a BackupCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public BackupCommand parse(String args) throws ParseException {
        try {
            String targetAddress = ParserUtil.parseBackup(args);
            return new BackupCommand(targetAddress);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }
}
