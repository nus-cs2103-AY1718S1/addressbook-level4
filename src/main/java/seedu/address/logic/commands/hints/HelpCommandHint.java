package seedu.address.logic.commands.hints;

/**
 * Generates description for Help Command
 * Assumes that {@code userInput} are from Help Command.
 */
public class HelpCommandHint extends NoArgumentsHint {

    protected static final String HELP_COMMAND_DESC = "shows user guide";

    public HelpCommandHint(String userInput) {
        this.userInput = userInput;
        this.description = HELP_COMMAND_DESC;
        parse();
    }
}
