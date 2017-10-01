package seedu.address.logic.commands;

/**
 * Creates an alias for other commands.
 */
public class AliasCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "alias";
    public static final String MESSAGE_SUCCESS = "The alias \"%1$s\" now points to \"%2$s\".";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates an alias for other commands."
            + "Parameters: NEW_COMMAND OLD_COMMAND\n"
            + "Example: " + COMMAND_WORD + " create add";

    private final String alias;
    private String command;

    public AliasCommand(String alias, String command) {
        this.alias = alias;
        this.command = command;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        model.addAlias(alias, command);
        return new CommandResult(String.format(MESSAGE_SUCCESS, alias, command));
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
