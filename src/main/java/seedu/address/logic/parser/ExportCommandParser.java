package seedu.address.logic.parser;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import java.io.File;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class ExportCommandParser implements Parser<ExportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ExportCommand
     * and returns an ExportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    @Override
    public ExportCommand parse(String args) throws ParseException {
        String[] arguments = args.split(" ");

        switch (arguments[1]) {
            case ".txt":
                //need to check whether windows or posix
                //for now just do windows

                char[] tmp = arguments[2].toCharArray();
                if (tmp[tmp.length - 1] == '/') {
                    arguments[2] = new String(tmp, 0, tmp.length - 1);
                }

                try {
                    Path exportPath = Paths.get(arguments[2]);
                    return new ExportCommand(".txt", arguments[2]);
                } catch (IllegalArgumentException e) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
                } catch (FileSystemNotFoundException e) {
                    throw new ParseException(
                            ExportCommand.MESSAGE_PATH_NOT_FOUND
                    );
                } catch (SecurityException e) {
                    throw new ParseException(
                            ExportCommand.MESSAGE_ACCESS_DENIED
                    );
                }


            default:
                throw new ParseException(
                        ExportCommand.MESSAGE_PATH_NOT_FOUND
                );
        }
    }
}
