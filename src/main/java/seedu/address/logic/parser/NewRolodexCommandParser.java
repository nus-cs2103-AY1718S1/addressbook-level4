package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_EXTENSION_FORMAT;
import static seedu.address.storage.util.RolodexStorageUtil.ROLODEX_FILE_EXTENSION;
import static seedu.address.storage.util.RolodexStorageUtil.isValidRolodexStorageExtension;
import static seedu.address.storage.util.RolodexStorageUtil.isValidRolodexStorageFilepath;

import seedu.address.logic.commands.NewRolodexCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new NewRolodexCommand object
 */
public class NewRolodexCommandParser implements Parser<NewRolodexCommand> {
    @Override
    public NewRolodexCommand parse(String args) throws ParseException {
        String trimmedAndFormattedArgs = args.trim().replace("\\", "/");
        if (trimmedAndFormattedArgs.isEmpty()
                || !isValidRolodexStorageFilepath(trimmedAndFormattedArgs)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NewRolodexCommand.MESSAGE_USAGE));
        }
        if (!isValidRolodexStorageExtension(trimmedAndFormattedArgs)) {
            throw new ParseException(String.format(MESSAGE_INVALID_EXTENSION_FORMAT, ROLODEX_FILE_EXTENSION));
        }

        return new NewRolodexCommand(trimmedAndFormattedArgs);
    }
}
