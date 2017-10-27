package seedu.address.logic.commands;

//@@author Jeremy
/**
 * Finds and lists all persons in address book in descending order by name
 */
public class ListDescendingNameCommand extends Command {

    public static final String COMMAND_WORD = "descending";
    public static final String COMMAND_ALIAS = "dsc"; // shorthand equivalent alias
    public static final String COMPILED_COMMAND = ListCommand.COMMAND_WORD + " " + COMMAND_WORD;
    public static final String COMPILED_SHORTHAND_COMMAND = ListCommand.COMMAND_WORD + " " + COMMAND_ALIAS;

    public static final String MESSAGE_USAGE = COMPILED_COMMAND
            + ": Finds and lists contacts by name in descending order \n"
            + "Example: " + COMPILED_COMMAND + "\n"
            + "Shorthand Example: " + COMPILED_SHORTHAND_COMMAND;

    public static final String MESSAGE_SUCCESS = "Listed persons by name in descending order";

    @Override
    public CommandResult execute() {
        model.listNameDescending();
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
