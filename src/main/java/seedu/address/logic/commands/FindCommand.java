package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.function.Predicate;

import seedu.address.model.person.ReadOnlyPerson;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_ALIAS = "f";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";
    public static final Predicate<ReadOnlyPerson> FALSE = (unused -> false);

    private final ArrayList<Predicate<ReadOnlyPerson>> predicates;

    public FindCommand(ArrayList<Predicate<ReadOnlyPerson>> predicates) {
        this.predicates = predicates;
    }

    @Override
    public CommandResult execute() {
        Predicate<ReadOnlyPerson> finalPredicate = FALSE;
        for (Predicate predicate : predicates) {
            finalPredicate = finalPredicate.or(predicate);
        }

        Predicate<? super ReadOnlyPerson>  currentPredicate = model.getPersonListPredicate();
        if (currentPredicate == null) {
            model.updateFilteredPersonList(finalPredicate);
        } else {
            model.updateFilteredPersonList(finalPredicate.and(currentPredicate));
        }

        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.predicates.equals(((FindCommand) other).predicates)); // state check
    }
}
