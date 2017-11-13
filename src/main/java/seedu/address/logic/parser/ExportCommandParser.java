//@@author conantteo
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.IndexArrayUtil;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parsers input arguments and creates a new ExportCommand object
 */
public class ExportCommandParser implements Parser<ExportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EmailCommand
     * and returns an EmailCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ExportCommand parse(String args) throws ParseException {

        String trimmedArgs = args.trim();
        if (trimmedArgs.matches("all")) {
            return new ExportCommand();
        } else {
            String[] indices = args.trim().split(" ");
            Index[] indexArray = new Index[indices.length];
            try {
                for (int i = 0; i < indices.length; i++) {
                    Index index = ParserUtil.parseIndex(indices[i]);
                    indexArray[i] = index;
                }
            } catch (IllegalValueException e) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
            }
            if (!IndexArrayUtil.indexAreUnique(indexArray)) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ParserUtil.MESSAGE_INDEX_DUPLICATES));
            }
            return new ExportCommand(indexArray);
        }
    }
}
