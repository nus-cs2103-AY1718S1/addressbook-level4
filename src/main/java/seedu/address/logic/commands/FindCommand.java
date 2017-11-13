package seedu.address.logic.commands;

import java.util.function.Predicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_ALIAS = "f";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names, addresses, phone "
            + "contain any of "
            + "the specified keywords (case-sensitive), or all favorite / unfavorite persons"
            + "and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie friends cs2103"
            + "The search can be specific with regards to a certain attribute\n"
            + "Parameters: KEYWORD [n/NAME] [t/TAG]...\n"
            + "Only 1 prefix will be processed if multiple prefixes are provided."
            + "The prefix with highest priority in the order of name > phone > address > email > tag"
            + "Example: " + COMMAND_WORD + " t/friends t/cs2103";
    private final Predicate predicate;

    public FindCommand(Predicate predicate) {
        this.predicate = predicate;
    }



    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);

        // displays a popup window to show number of persons found
        //      JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
        //      getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));

        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
