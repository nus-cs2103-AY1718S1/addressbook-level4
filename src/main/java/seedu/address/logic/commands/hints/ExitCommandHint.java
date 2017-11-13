package seedu.address.logic.commands.hints;

/**
 * Generates description for Exit Command
 * Assumes that {@code userInput} are from an exit command.
 */
public class ExitCommandHint extends NoArgumentsHint {

    protected static final String EXIT_COMMAND_DESC = "exits the application";

    public ExitCommandHint(String userInput) {
        this.userInput = userInput;
        this.description = EXIT_COMMAND_DESC;
        parse();
    }
}
