package seedu.address.logic.commands;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_ALIAS = "l";

    public static final String MESSAGE_SUCCESS = "Listed all persons in alphabetical order";
    public static final String MESSAGE_TEMPLATE = COMMAND_WORD;


    @Override
    public CommandResult execute() {
        model.sortPersonListLexicographically();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}





