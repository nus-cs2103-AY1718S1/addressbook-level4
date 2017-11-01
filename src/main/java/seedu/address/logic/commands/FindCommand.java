package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.model.person.NameContainsKeywordsPredicate;


/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    //@@author LeeYingZheng
    public static final String COMMAND_WORDVAR_1 = "find";
    public static final String COMMAND_WORDVAR_2 = "f";
    //@@author

    public static final String MESSAGE_USAGE = COMMAND_WORDVAR_1
            + " OR "
            + COMMAND_WORDVAR_2
            + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers."
            + " Command is case-insensitive. \n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example 1: " + COMMAND_WORDVAR_1 + " alice bob charlie \n"
            + "Example 2: " + COMMAND_WORDVAR_2.toUpperCase() + " alice bob charlie \n";

    private final NameContainsKeywordsPredicate predicate;


    public FindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    //@@author vivekscl
    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        int numberOfPersonsShown = model.getFilteredPersonList().size();
        if (numberOfPersonsShown == 0 && !predicate.getKeywords().isEmpty()) {
            model.updateFilteredPersonList(predicate);
            return new CommandResult(String.format(getMessageForPersonListShownSummary(numberOfPersonsShown)
                    + Messages.MESSAGE_NO_PERSON_FOUND, model.getClosestMatchingName(predicate)));
        }
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    //@@author
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.predicate.equals(((FindCommand) other).predicate)); // state check
    }

}
