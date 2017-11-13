package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ClearPersonListEvent;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.tag.TagMatchingKeywordPredicate;

//@@author ZhangH795

/**
 * Finds and lists all persons whose tag list contains the given tag.
 * Keyword matching is case insensitive, substring matching is also allowed.
 */
public class TagFindCommand extends Command {

    public static final String COMMAND_WORD = "t-find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose tags contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: [TAG] \n"
            + "Example: " + COMMAND_WORD + " " + "friends";

    private final TagMatchingKeywordPredicate predicate;
    private final int firstIndex = 0;

    public TagFindCommand(TagMatchingKeywordPredicate keywordPredicate) {
        this.predicate = keywordPredicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        if (model.getFilteredPersonList().size() > 0) {
            Index defaultIndex = new Index(firstIndex);
            EventsCenter.getInstance().post(new JumpToListRequestEvent(defaultIndex));
        } else {
            EventsCenter.getInstance().post(new ClearPersonListEvent());
        }
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagFindCommand // instanceof handles nulls
                && this.predicate.equals(((TagFindCommand) other).predicate)); // state check
    }
}
