package seedu.address.logic.commands.hints;

public class HelpCommandHint extends NoArgumentsHint {

    protected final static String HELP_COMMAND_DESC = "shows user guide";

    public HelpCommandHint(String userInput) {
        this.userInput = userInput;
        this.description = HELP_COMMAND_DESC;
    }
}
