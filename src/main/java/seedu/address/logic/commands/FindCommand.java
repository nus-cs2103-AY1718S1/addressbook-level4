package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

import java.util.List;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final NameContainsKeywordsPredicate predicate;

    public FindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() throws CommandException{
        model.updateFilteredPersonList(predicate);
        List<String> tags=predicate.getSelectedTags();
        if (tags!=null){
            if(tags.size()!=0) {
                String selectedTags=" Selected tags:";
                for(String eachTag: tags){
                    selectedTags+=" " + eachTag;
                }
                return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size())
                        + selectedTags);
            }
        }
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
