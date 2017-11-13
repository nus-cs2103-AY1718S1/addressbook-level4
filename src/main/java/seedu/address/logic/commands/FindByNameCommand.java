package seedu.address.logic.commands;

import java.util.function.Predicate;

import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.ReadOnlyPerson;

//@@author marvinchin
/**
 * Finds and lists all {@code Person} in address book whose {@code Name} contains any of the input keywords.
 * Keyword matching is case sensitive.
 */
public class FindByNameCommand extends FindCommand {
    private NameContainsKeywordsPredicate predicate;

    public FindByNameCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    protected Predicate<ReadOnlyPerson> getPredicate() {
        return predicate;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindByNameCommand // instanceof handles nulls
                && this.predicate.equals(((FindByNameCommand) other).predicate)); // state check
    }
}
