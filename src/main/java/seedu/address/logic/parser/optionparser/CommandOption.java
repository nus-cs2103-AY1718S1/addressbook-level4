//@@author Hailinx
package seedu.address.logic.parser.optionparser;

import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a command mode of {@code T} command.
 */
public abstract class CommandOption<T extends Command> {

    protected String optionArgs;

    CommandOption(String optionArgs) {
        this.optionArgs = optionArgs.trim();
    }

    /**
     * Parses {@code optionArgs} into a command and returns it.
     * @throws ParseException if {@code optionArgs} does not conform the expected format.
     */
    public abstract T parse() throws ParseException;

    /**
     * Checks whether {@code optionArgs} is valid.
     * @return true if {@code optionArgs} is not empty by default.
     */
    public boolean isValidOptionArgs() {
        return !optionArgs.isEmpty();
    }
}
