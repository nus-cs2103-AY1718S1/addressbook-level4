package seedu.address.logic.commands;

import java.util.function.Predicate;

import seedu.address.model.person.ReadOnlyPerson;

/**
 * Finds and lists all persons in address book whose details contain any of the argument keywords.
 * Keyword matching is case sensitive.
 *
 * If a prefix is specified, finds and lists all persons in address book whose detail corresponding to the prefix
 * matches the argument keywords.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_ALIAS = "f";

    public static final String COMMAND_PARAMETERS = "KEYWORD [MORE_KEYWORDS]... or KEYWORD [PREFIX]/[MORE_KEYWORDS]...";

    public static final String SHORT_MESSAGE_USAGE = "global find: find\n"
            + "specific find: find n/NAME or find e/EMAIL or find p/PHONE or find t/TAG";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose details contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "A prefix can also be specified to narrow the search to persons whose particular detail contains any of "
            + "the keywords (case-sensitive).\n"
            + "Parameters: " + COMMAND_PARAMETERS + "\n"
            + "Examples:\n"
            + COMMAND_WORD + " alice Serangoon\n"
            + COMMAND_WORD + " n/alice bob\n"
            + COMMAND_WORD + " a/Serangoon\n"
            + COMMAND_WORD + " e/bob@example.com\n"
            + COMMAND_WORD + " p/96779802\n"
            + COMMAND_WORD + " t/friends";

    private final Predicate<ReadOnlyPerson> predicate;

    public FindCommand(Predicate<ReadOnlyPerson> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
