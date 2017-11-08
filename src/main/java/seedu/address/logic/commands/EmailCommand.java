package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

//@@author jin-ting
/**
 * Locates a person's address by showing its location on Google Maps.
 */
public class EmailCommand extends Command {

    public static final String COMMAND_WORD = "email";
    public static final String COMMAND_ALIAS = "m";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " (alias: " + COMMAND_ALIAS + ")"
            + ":Opening up email webpage)\n"
            + MESSAGE_GET_MORE_HELP;

    public static final String MESSAGE_DISPLAY_EMAIL_SUCCESS = "Opening up email webpage";


    @Override
    public CommandResult execute() throws CommandException {
        return new CommandResult(MESSAGE_DISPLAY_EMAIL_SUCCESS);

    }

}
