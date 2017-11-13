package seedu.address.logic.commands;

import java.util.function.Predicate;

import seedu.address.model.person.PersonContainsBirthdayPredicate;
import seedu.address.model.person.PersonContainsKeywordsPredicate;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {
    //@@author tbhbhbh
    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": finds all persons whose information contain "
            + "any of the specified keywords and displays them as a list with index numbers.\n"
            + "Parameters: names (eg. Tan, Alex, Abdullah), tags (eg. colleagues, friends), initials (eg. A, b, x), "
            + "birthday month (eg. 05, 12)"
            + "Format: KEYWORD [MORE_KEYWORDS]...\n"
            + "(The format for entering this command differs depending on what you are searching for. Go into our "
            + "help window for more information)"
            + "Example: " + COMMAND_WORD + " alice colleagues";

    public static final String BIRTHDAY_USAGE = COMMAND_WORD + ": Search for persons whose birthday month is "
            + "represented by [01-12] for [January to December].\n"
            + "Parameters: MM (must be a non-zero integer 01 to 12)\n"
            + "Example: " + COMMAND_WORD + " 09";

    private final Predicate<ReadOnlyPerson> searchPredicate;

    public FindCommand(PersonContainsKeywordsPredicate searchPredicate) {
        this.searchPredicate = searchPredicate;
    }
    //@@author conantteo
    public FindCommand(PersonContainsBirthdayPredicate searchPredicate) {
        this.searchPredicate = searchPredicate;
    }
    //@@author tbhbhbh
    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(searchPredicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.searchPredicate.equals(((FindCommand) other).searchPredicate)); // state check
    }
}
