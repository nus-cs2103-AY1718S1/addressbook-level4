package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.function.Predicate;

import seedu.address.model.meeting.MeetingContainPersonPredicate;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Finds and lists all persons in address book whose specified fields contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    //@@author newalter
    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_ALIAS = "f";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose specified fields "
            + "contain any of the specified keywords (case-insensitive) "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: "
            + "[" + PREFIX_NAME + "KEYWORD...] "
            + "[" + PREFIX_PHONE + "KEYWORD...] "
            + "[" + PREFIX_EMAIL + "KEYWORD...] "
            + "[" + PREFIX_ADDRESS + "KEYWORD...] "
            + "[" + PREFIX_TAG + "KEYWORD...]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "alice bob charlie "
            + PREFIX_PHONE + "98765432 93250124 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "Clementi Ave "
            + PREFIX_TAG + "friends owesMoney";

    public static final Predicate<ReadOnlyPerson> FALSE = (unused -> false);

    private final ArrayList<Predicate<ReadOnlyPerson>> predicates;

    public FindCommand(ArrayList<Predicate<ReadOnlyPerson>> predicates) {
        this.predicates = predicates;
    }

    @Override
    public CommandResult execute() {
        Predicate<ReadOnlyPerson> predicate = combinePredicates();

        Predicate<? super ReadOnlyPerson>  currentPredicate = model.getPersonListPredicate();
        if (currentPredicate == null) {
            model.updateFilteredPersonList(predicate);
        } else {
            model.updateFilteredPersonList(predicate.and(currentPredicate));
        }
        model.updateFilteredMeetingList(new MeetingContainPersonPredicate(model.getFilteredPersonList()));
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    /**
     * combines the list of predicates into a single predicate for execution
     * by taking OR operations
     */
    private Predicate<ReadOnlyPerson> combinePredicates() {
        Predicate<ReadOnlyPerson> combinedPredicate = FALSE;
        for (Predicate predicate : predicates) {
            combinedPredicate = combinedPredicate.or(predicate);
        }
        return combinedPredicate;
    }
    //@@author

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.predicates.equals(((FindCommand) other).predicates)); // state check
    }
}
