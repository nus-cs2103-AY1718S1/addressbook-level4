//@@author arturs68
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Removes the specified tag from all the persons and from the address book tag list
 */
public class RemoveTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "removetag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the tag identified by TAG name.\n"
            + "Parameters: " + PREFIX_TAG + "TAG\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_TAG + "OwesMoney";

    public static final String MESSAGE_SELECT_PERSON_SUCCESS = "Removed tag: %1$s";

    private final Tag tagToBeRemoved;

    public RemoveTagCommand(Tag tagToBeRemoved) {
        this.tagToBeRemoved = tagToBeRemoved;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            boolean wasRemoved = model.removeTag(tagToBeRemoved);
            if (!wasRemoved) {
                throw new CommandException("Such a tag does not exit");
            }
        } catch (DuplicatePersonException dpe) {
            throw new CommandException("The person cannot be duplicated when adding to a group");
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        return new CommandResult(String.format(MESSAGE_SELECT_PERSON_SUCCESS, tagToBeRemoved.toString()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveTagCommand // instanceof handles nulls
                && this.tagToBeRemoved.equals(((RemoveTagCommand) other).tagToBeRemoved)); // state check
    }
}
