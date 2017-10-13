package seedu.address.logic.commands;

import seedu.address.model.Aliases;
import seedu.address.model.UserPrefs;

/**
 * Creates an alias for other commands.
 */
public class AliasCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "alias";
    public static final String MESSAGE_ADD_SUCCESS = "The alias \"%1$s\" now points to \"%2$s\".";
    public static final String MESSAGE_DELETE_SUCCESS = "Deleted alias \"%1$s\".";
    public static final String MESSAGE_LIST_SUCCESS = "Aliases:\n%1$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates or deletes an alias for other commands."
            + "Parameters: [--delete|-d] COMMAND [OLD_COMMAND]\n"
            + "Example: " + COMMAND_WORD + " create add\n"
            + "         " + COMMAND_WORD + " --delete create";

    private final String alias;
    private final String command;
    private final Boolean isDelete;

    public AliasCommand() {
        this.alias = null;
        this.command = null;
        this.isDelete = false;
    }

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
        Aliases aliases = UserPrefs.getAliases();

        if (isDelete) {
            aliases.removeAlias(alias);
            return new CommandResult(String.format(MESSAGE_DELETE_SUCCESS, alias));
        } else if (command != null) {
            aliases.addAlias(alias, command);
            return new CommandResult(String.format(MESSAGE_ADD_SUCCESS, alias, command));
        } else {
            StringBuilder output = new StringBuilder();

            for (String alias : aliases.getAllAliases()) {
                String command = aliases.getCommand(alias);
                output.append(String.format("%1$s=%2$s\n", alias, command));
            }
            return new CommandResult(String.format(MESSAGE_LIST_SUCCESS, output));
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
