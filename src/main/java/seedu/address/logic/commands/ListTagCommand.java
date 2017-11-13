package seedu.address.logic.commands;

import java.util.List;

import seedu.address.model.tag.Tag;

//@@author kengying

/**
 * Lists all tags in the address book to the user.
 */
public class ListTagCommand extends ListCommand {

    public static final String SEARCH_TERM = "tags";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + SEARCH_TERM + " : List all tags\n"
            + "Example: " + COMMAND_WORD +  " " + SEARCH_TERM;

    public static final String MESSAGE_SUCCESS = "List all tags: ";

    @Override
    public CommandResult execute() {
        List<Tag> listTags = model.getTagList();
        return new CommandResult(String.format(MESSAGE_SUCCESS + " " + listTags.toString().replaceAll("[\\[\\]]", "")));
    }

}
