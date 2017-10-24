package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINK;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Link;

/**
 * Changes the facebook link of an existing person in the address book.
 */
public class LinkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "link";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a facebook link to person identified "
            + "by the index number used in the last person listing. "
            + "Existing links will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_LINK + "[LINK]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_LINK + "https://www.facebook.com/profile.php?id=100021659181463";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Link: %2$s";

    private final Index index;
    private final Link link;

    /**
     * @param index of the person in the filtered person list to edit the link
     * @param link of the person
     */
    public LinkCommand(Index index, Link link) {
        requireNonNull(index);
        requireNonNull(link);

        this.index = index;
        this.link = link;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        throw new CommandException(String.format(MESSAGE_ARGUMENTS, index.getOneBased(), link));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LinkCommand)) {
            return false;
        }

        // state check
        LinkCommand e = (LinkCommand) other;
        return index.equals(e.index)
                && link.equals(e.link);
    }
}