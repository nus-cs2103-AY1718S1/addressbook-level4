//@@author duyson98

package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagContainsKeywordPredicate;

/**
 * Lists all contacts having a certain tag in the address book.
 */
public class RetrieveCommand extends Command {

    public static final String COMMAND_WORD = "retrieve";
    public static final String COMMAND_ALIAS = "re";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Retrieves all persons belonging to an existing tag "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: TAGNAME\n"
            + "Example: " + COMMAND_WORD + " friends";

    public static final String MESSAGE_EMPTY_ARGS = "Please provide a tag name! \n%1$s";

    public static final String MESSAGE_NOT_FOUND = "Tag not found in person list." + "\n"
            + "You may want to refer to the following existing tags inside the unfiltered person list: %s";

    private final TagContainsKeywordPredicate predicate;

    public RetrieveCommand(TagContainsKeywordPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        final int personListSize = model.getFilteredPersonList().size();
        if (personListSize == 0) {
            Set<Tag> uniqueTags = new HashSet<>();
            for (ReadOnlyPerson person : model.getAddressBook().getPersonList()) {
                uniqueTags.addAll(person.getTags());
            }
            StringJoiner joiner = new StringJoiner(", ");
            for (Tag tag: uniqueTags) {
                joiner.add(tag.toString());
            }
            return new CommandResult(String.format(MESSAGE_NOT_FOUND, joiner.toString()));
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
