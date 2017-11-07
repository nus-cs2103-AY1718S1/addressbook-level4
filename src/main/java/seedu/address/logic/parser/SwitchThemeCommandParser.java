package seedu.address.logic.parser;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.SwitchThemeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author zhangshuoyang
/**
 * Parses the given {@code String} of arguments in the context of the SwitchThemeCommand
 * and returns an SwitchThemeCommand object for execution.
 */
public class SwitchThemeCommandParser implements Parser<SwitchThemeCommand> {

    @Override
    public SwitchThemeCommand parse(String userInput) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(userInput);
            return new SwitchThemeCommand(index);
        } catch (IllegalValueException e) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    SwitchThemeCommand.MESSAGE_USAGE));
        }
    }

}
