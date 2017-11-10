package seedu.address.logic.commands.persons;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandUtil;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

//@@author raisa2010
/**
 * Tags multiple people in the address book.
 */
//@@author raisa2010
public class TagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Tags multiple people using the same tag(s) "
            + "by the index number used in the last person listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDICES (must be positive integers and may be one or more) "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1, 2, 3 "
            + PREFIX_TAG + "friend";

    public static final String MESSAGE_TAG_PERSONS_SUCCESS = "New tag added.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index[] indices;
    private final Set<Tag> newTags;

    /**
     * @param indices of the people in the filtered person list to tag
     * @param tagList list of tags to tag the people with
     */
    public TagCommand(Index[] indices, Set<Tag> tagList) {
        requireNonNull(indices);
        requireNonNull(tagList);

        this.indices = indices;
        newTags = tagList;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        Index[] validIndices = CommandUtil.filterValidIndices(lastShownList.size(), indices);

        if (validIndices.length == 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        for (Index currentIndex : validIndices) {
            ReadOnlyPerson personToEdit = lastShownList.get(currentIndex.getZeroBased());

            try {
                model.updatePersonTags(personToEdit, newTags);
            } catch (DuplicatePersonException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            } catch (PersonNotFoundException pnfe) {
                throw new AssertionError("The target person cannot be missing");
            }
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_TAG_PERSONS_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagCommand)) {
            return false;
        }

        //state check
        for (int i = 0; i < indices.length; i++) {
            assert(indices.length == ((TagCommand) other).indices.length);
            if (!indices[i].equals(((TagCommand) other).indices[i])) {
                return false;
            }
        }
        return newTags.toString().equals(((TagCommand) other).newTags.toString());
    }
}
