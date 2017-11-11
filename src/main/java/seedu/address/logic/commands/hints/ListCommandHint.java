package seedu.address.logic.commands.hints;

public class ListCommandHint extends NoArgumentsHint {

    protected final static String LIST_COMMAND_DESC = "lists all users";

    public ListCommandHint(String userInput) {
        this.userInput = userInput;
        this.description = LIST_COMMAND_DESC;
    }
}
