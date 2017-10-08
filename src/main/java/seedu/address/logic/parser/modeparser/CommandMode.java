package seedu.address.logic.parser.modeparser;

import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a command mode of {@code T} command.
 */
public abstract class CommandMode<T extends Command> {

    String modeArgs;

    CommandMode(String modeArgs) {
        this.modeArgs = modeArgs;
    }

    /**
     * Parses {@code modeArgs} into a command and returns it.
     * @throws ParseException if {@code modeArgs} does not conform the expected format.
     */
    public abstract T parse() throws ParseException;

    /**
     * Checks whether {@code modeArgs} is valid.
     * @return true if {@code modeArgs} is not empty by default.
     */
    boolean isValidModeArgs() {
        return !modeArgs.isEmpty();
    }
}
