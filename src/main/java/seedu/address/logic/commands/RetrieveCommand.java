//@@author duyson98

package seedu.address.logic.commands;

import java.util.StringJoiner;

import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagContainsKeywordPredicate;

/**
 * Lists all contacts having a certain tag in the address book.
 */
public class RetrieveCommand extends Command {

    public static final String COMMAND_WORD = "retrieve";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Retrieves all persons belonging to an existing tag "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: TAGNAME\n"
            + "Example: " + COMMAND_WORD + " friends";

    public static final String MESSAGE_EMPTY_ARGS = "Please provide a tag name! \n%1$s";

    public static final String MESSAGE_NOT_FOUND = "Tag not found.";

    private final TagContainsKeywordPredicate predicate;

    public RetrieveCommand(TagContainsKeywordPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        final int personListSize = model.getFilteredPersonList().size();
        if (personListSize == 0) {
            StringJoiner joiner = new StringJoiner(", ");
            for (Tag tag: model.getAddressBook().getTagList()) {
                joiner.add(tag.toString());
            }
            return new CommandResult(MESSAGE_NOT_FOUND + "\n"
                    + "You may want to refer to the following existing tags: "
                    + joiner.toString());
        }
        return new CommandResult(getMessageForPersonListShownSummary(personListSize));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RetrieveCommand // instanceof handles nulls
                && this.predicate.equals(((RetrieveCommand) other).predicate)); // state check
    }

}
