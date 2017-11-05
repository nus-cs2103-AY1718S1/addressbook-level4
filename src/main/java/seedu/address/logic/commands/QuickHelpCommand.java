package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

//@@author kenpaxtonlim
/**
 * Show a list of valid command words.
 */
public class QuickHelpCommand extends Command {

    public static final String COMMAND_WORD = "quickhelp";
    public static final String COMMAND_ALIAS = "qh";

    public static final String MESSAGE = "Valid Command Words:\n"
            + SelectCommand.COMMAND_WORD + " "
            + ListCommand.COMMAND_WORD + " "
            + AddCommand.COMMAND_WORD + " "
            + DeleteCommand.COMMAND_WORD + " "
            + EditCommand.COMMAND_WORD + " "
            + ClearCommand.COMMAND_WORD + " "
            + RemarkCommand.COMMAND_WORD + " "
            + AddRemoveTagsCommand.COMMAND_WORD + " "
            + FindCommand.COMMAND_WORD + " "
            + FindRegexCommand.COMMAND_WORD + " "
            + FindTagCommand.COMMAND_WORD + " "
            + SocialMediaCommand.COMMAND_WORD + " "
            + StatisticsCommand.COMMAND_WORD + " "
            + SizeCommand.COMMAND_WORD + " "
            + ToggleAccessDisplayCommand.COMMAND_WORD + " "
            + UndoCommand.COMMAND_WORD + " "
            + RedoCommand.COMMAND_WORD + " "
            + HistoryCommand.COMMAND_WORD + " "
            + HelpCommand.COMMAND_WORD + " "
            + ExitCommand.COMMAND_WORD;

    @Override
    public CommandResult execute() throws CommandException {
        return new CommandResult(MESSAGE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof QuickHelpCommand); // instanceof handles nulls
    }
}
