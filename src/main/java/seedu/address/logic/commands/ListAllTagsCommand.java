package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TAGS;

import javafx.collections.ObservableList;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.JumpToListAllTagsRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.Tag;

//@@author pohjie
/**
 * List all tags that exist in software to user
 */
public class ListAllTagsCommand extends Command {

    public static final String COMMAND_WORD = "listalltags";

    public static final String MESSAGE_SUCCESS = "All tags listed!";

    @Override
    public CommandResult execute() throws CommandException {
        model.updateFilteredTagList(PREDICATE_SHOW_ALL_TAGS);
        ObservableList<Tag> allTags = model.getFilteredTagList();

        EventsCenter.getInstance().post(new JumpToListAllTagsRequestEvent());
        return new CommandResult(getMessageForTagListShownSummary(allTags));
    }
}
