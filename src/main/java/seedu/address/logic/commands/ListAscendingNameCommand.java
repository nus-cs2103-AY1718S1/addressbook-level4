package seedu.address.logic.commands;

//@@author Jeremy
/**
 * Finds and lists all persons in address book in ascending order by name.
 */
public class ListAscendingNameCommand extends Command {

    public static final String COMMAND_WORD = "ascending";
    public static final String COMMAND_ALIAS = "asc"; // shorthand equivalent alias
    public static final String COMPILED_COMMAND = ListCommand.COMMAND_WORD + " " + COMMAND_WORD;
    public static final String COMPILED_SHORTHAND_COMMAND = ListCommand.COMMAND_WORD + " " + COMMAND_ALIAS;

    public static final String MESSAGE_USAGE = COMPILED_COMMAND
            + ": Finds and lists contacts by name in ascending order \n"
            + "Example: " + COMPILED_COMMAND + "\n"
            + "Shorthand Example: " + COMPILED_SHORTHAND_COMMAND;

    public static final String MESSAGE_SUCCESS = "Listed persons by name in ascending order";

    /**
     * Returns a success message and filters the list by name in ascending order.
     *
     * @return CommandResult(MESSAGE_SUCCESS).
     */
    @Override
    public CommandResult execute() {
        model.listNameAscending();
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
