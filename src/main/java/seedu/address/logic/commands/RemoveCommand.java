package seedu.address.logic.commands;

import static seedu.address.logic.commands.AddCommand.MESSAGE_DUPLICATE_PERSON;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Remove the specified tag from address book
 */
public class RemoveCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remove";
    public static final String COMMAND_ALIAS = "rm";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the specified tag from everyone in the address book.\n"
            + "Parameters: [TAG]\n"
            + "Example: " + COMMAND_WORD + " friends";

    public static final String MESSAGE_REMOVE_SUCCESS = "Removed Tag: %1$s from address book.";
    public static final String MESSAGE_TAG_NOT_FOUND = " does not exist in address book.";

    private final Tag tag;

    /**
     *
     * @param toRemove tag to be removed from the address book
     */
    public RemoveCommand(Tag toRemove)  {
        this.tag = toRemove;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.removeTag(tag);

        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new CommandException("Tag: " + tag.toString() + MESSAGE_TAG_NOT_FOUND);
        }

        return new CommandResult(String.format(MESSAGE_REMOVE_SUCCESS, tag.toString()));
    }



}
