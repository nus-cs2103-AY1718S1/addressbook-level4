package seedu.address.logic.parser;

import seedu.address.logic.commands.LogoutCommand;

/**
 * Parser for LogoutCommand
 */

public class LogoutCommandParser implements Parser<LogoutCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LogoutCommand
     * and returns an LogoutCommand object for execution.
     */
    public LogoutCommand parse(String args) {
        return new LogoutCommand();
    }
}
