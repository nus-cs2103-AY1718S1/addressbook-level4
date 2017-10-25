package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINK;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.PossibleLinks;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Link;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

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
            + PREFIX_LINK + "twitter.com/KingJames";

    public static final String MESSAGE_ADD_LINK_SUCCESS = "Added link to Person: %1$s";
    public static final String MESSAGE_DELETE_LINK_SUCCESS = "Removed link from Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

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
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (!(link.value.startsWith(PossibleLinks.POSSIBLE_LINK_1)
                || link.value.startsWith(PossibleLinks.POSSIBLE_LINK_2)) && !link.value.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LINK_FORMAT);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getRemark(), personToEdit.getFavouriteStatus(),
                personToEdit.getTags(), link);

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     *
     * @param personToEdit
     * @return String that shows whether add or delete was successfully done
     */
    private String generateSuccessMessage(ReadOnlyPerson personToEdit) {
        if (!link.value.isEmpty()) {
            return String.format(MESSAGE_ADD_LINK_SUCCESS, personToEdit);
        } else {
            return String.format(MESSAGE_DELETE_LINK_SUCCESS, personToEdit);
        }
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
