package seedu.address.logic.parser.task;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.task.RenameTaskCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author deep4k
/**
 * Parses input arguments and creates a new RenameTaskCommand object
 */
public class RenameTaskCommandParser implements Parser<RenameTaskCommand> {

    private static final Pattern RENAME_ARGS_FORMAT = Pattern.compile("(?<targetIndex>\\d+)\\s+(?<name>.+)");

    /**
     * Parses the given {@code String} of arguments in the context of the RenameTaskCommand
     * and returns an RenameTaskCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public RenameTaskCommand parse(String args) throws ParseException {
        final Matcher matcher = RENAME_ARGS_FORMAT.matcher(args.trim());

        if (!matcher.matches()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RenameTaskCommand.MESSAGE_USAGE));
        }

        final String inputName = matcher.group("name").trim();
        final String inputIndex = matcher.group("targetIndex");

        Index index;
        try {
            index = ParserUtil.parseIndex(inputIndex);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RenameTaskCommand.MESSAGE_USAGE));
        }

        try {
            return new RenameTaskCommand(index, inputName);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    @Override
    public String getCommandWord() {
        return RenameTaskCommand.COMMAND_WORD;
    }
}
