package seedu.address.logic.commands;

import jdk.nashorn.internal.runtime.ParserException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

//@@author AngularJiaSheng
/**
 * Deletes a tag from all contacts in the address book.
 */
public class DeleteTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "tagDelete";
    public static final String COMMAND_ALIAS = "td";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes all the tag specified in all person in the list.\n"
            + "Parameters: tag (must be one of the existing tag on any one person\n"
            + "Example: " + COMMAND_WORD + " classmate";

    //@@author hansiang93
    public static final String MESSAGE_USAGE_EXAMPLE = COMMAND_WORD + " {tag}";
    //@@author

    public static final String MESSAGE_DELETE_ALL_TAG_SUCCESS = "Deleted tag: %1$s";
    public static final String MESSAGE_TAG_NOT_FOUND = "The tag is not found";

    private final Tag targetTag;

    public DeleteTagCommand(Tag tag) {
        this.targetTag = tag;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        try {
            model.deleteTag(targetTag);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        } catch (DuplicatePersonException dpe) {
            assert false : "There should not be any duplicate person";
        } catch (ParserException pe) {
            throw new CommandException(MESSAGE_TAG_NOT_FOUND);
        }

        return new CommandResult(String.format(MESSAGE_DELETE_ALL_TAG_SUCCESS, targetTag));
    }
}
