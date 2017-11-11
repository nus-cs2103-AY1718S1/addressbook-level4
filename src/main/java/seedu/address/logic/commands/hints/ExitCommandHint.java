package seedu.address.logic.commands.hints;

public class ExitCommandHint extends NoArgumentsHint {

    protected final static String EXIT_COMMAND_DESC = "exits the application";

    public ExitCommandHint(String userInput) {
        this.userInput = userInput;
        this.description = EXIT_COMMAND_DESC;
    }
}
