//@@author tshradheya
package seedu.address.logic.commands;

import seedu.address.model.person.PersonContainsTagPredicate;

/**
 * Finds and lists all persons in address book who are associated with the tag given in keywords
 * Keyword matching is case sensitive.
 */

public class ViewTagCommand extends Command {

    public static final String COMMAND_WORD = "viewtag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons who are associated with the tag in "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD\n"
            + "Example: " + COMMAND_WORD + " cs2103";


    private final PersonContainsTagPredicate predicate;

    public ViewTagCommand(PersonContainsTagPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonListForViewTag(predicate);
        model.showDefaultPanel();
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewTagCommand // instanceof handles nulls
                && this.predicate.equals(((ViewTagCommand) other).predicate)); // state check
    }

}
