package seedu.address.logic.commands;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.SearchContainsKeywordsPredicate;

//@@author RonakLakhotia
/**
 * Searches and lists all persons in address book whose name and DateOfBirth matches the argument keywords, that
 * is persons with same name and DateOfBirth. This is to make the find command more powerful.
 * Keyword matching is case insensitive.
 */

public class SearchCommand extends Command {

    public static final String COMMAND_WORD = "search";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose name and Date Of Birth "
            + "contain any of the specified keyowrds and displays them as a list with index number. \n"
            + "Parameters: Name and Date Of Birth\n"
            + "Example: " + COMMAND_WORD + " search n/ronak b/13.10.1997";

    private static final Logger logger = LogsCenter.getLogger(SearchCommand.class);
    private final SearchContainsKeywordsPredicate predicate;


    public SearchCommand(SearchContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }
    @Override
    public CommandResult execute() {
        logger.info("Executing seacrh command!");
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }
    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof SearchCommand
                && this.predicate.equals(((SearchCommand) other).predicate));

    }

}
