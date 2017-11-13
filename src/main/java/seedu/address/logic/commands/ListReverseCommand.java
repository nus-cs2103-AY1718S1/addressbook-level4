package seedu.address.logic.commands;

//@@author Jeremy
/**
 * Reverses existing displayed list
 */
public class ListReverseCommand extends Command {

    public static final String COMMAND_WORD = "reverse";
    public static final String COMMAND_ALIAS = "rev"; // shorthand equivalent alias
    public static final String COMPILED_COMMAND = ListCommand.COMMAND_WORD + " " + COMMAND_WORD;
    public static final String COMPILED_SHORTHAND_COMMAND = ListCommand.COMMAND_WORD + " " + COMMAND_ALIAS;

    public static final String MESSAGE_USAGE = COMPILED_COMMAND
            + ": Reverses existing displayed list \n"
            + "Example: " + COMPILED_COMMAND + "\n"
            + "Shorthand Example: " + COMPILED_SHORTHAND_COMMAND;

    public static final String MESSAGE_SUCCESS = "Displayed list reversed";

    /**
     * Returns success message and reverses list.
     *
     * @return Success Message.
     */
    @Override
    public CommandResult execute() {
        model.listNameReversed();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
