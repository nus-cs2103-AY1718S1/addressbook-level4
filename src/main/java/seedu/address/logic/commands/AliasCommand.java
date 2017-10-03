package seedu.address.logic.commands;

/**
 * Creates an alias for other commands.
 */
public class AliasCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "alias";
    public static final String MESSAGE_SUCCESS = "The alias \"%1$s\" now points to \"%2$s\".";
    public static final String MESSAGE_DELETE_SUCCESS = "Deleted alias \"%1$s\".";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates or deletes an alias for other commands."
            + "Parameters: [--delete|-d] COMMAND [OLD_COMMAND]\n"
            + "Example: " + COMMAND_WORD + " create add\n"
            + "         " + COMMAND_WORD + " --delete create";

    private final String alias;
    private final String command;
    private final Boolean isDelete;

    public AliasCommand(String alias, String command) {
        this.alias = alias;
        this.command = command;
        this.isDelete = false;
    }

    public AliasCommand(String alias, Boolean isDelete) {
        this.alias = alias;
        this.command = null;
        this.isDelete = isDelete;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        if (isDelete) {
            model.deleteAlias(alias);
            return new CommandResult(String.format(MESSAGE_DELETE_SUCCESS, alias));
        } else {
            model.addAlias(alias, command);
            return new CommandResult(String.format(MESSAGE_SUCCESS, alias, command));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AliasCommand // instanceof handles nulls
                && this.alias.equals(((AliasCommand) other).alias) // state check
                && this.command.equals(((AliasCommand) other).command)); // state check
    }

    public String getCommandWord() {
        return COMMAND_WORD;
    }
}
