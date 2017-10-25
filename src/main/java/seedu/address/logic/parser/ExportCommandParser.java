package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ExportCommand object
 */
public class ExportCommandParser implements Parser<ExportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ExportCommand
     * and returns an ExportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    @Override
    public ExportCommand parse(String args) throws ParseException {
        String[] arguments = args.split(" ");

        if (arguments.length < 3) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        }

        switch (arguments[1]) {
        case ".txt":
            //need to check whether windows or posix
            //for now just do windows

            char[] tmp = args.toCharArray();
            if (tmp[tmp.length - 1] == '/') {
                args = new String(tmp, 6, args.length() - 7);
            } else {
                args = new String(tmp, 6, args.length() - 6);
            }

            Path exportPath = Paths.get(args);
            return new ExportCommand(".txt", args);

        default:
            throw new ParseException(ExportCommand.MESSAGE_FILE_TYPE_NOT_SUPPORTED);
        }
    }
}
