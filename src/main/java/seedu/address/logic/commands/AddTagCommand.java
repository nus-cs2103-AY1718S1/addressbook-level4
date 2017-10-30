package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

//@@author vivekscl
/**
 * Adds a tag to the identified persons using the last displayed indexes from the address book.
 */
public class AddTagCommand extends UndoableCommand {

    public static final String COMMAND_WORDVAR_1 = "addtag";
    public static final String COMMAND_WORDVAR_2 = "at";

    public static final String MESSAGE_USAGE = COMMAND_WORDVAR_1
            + " OR "
            + COMMAND_WORDVAR_2
            + ": Adds the given tag to the persons identified by the list of index numbers used in the last person "
            + "listing."
            + " Command is case-sensitive. \n"
            + "Parameters: "
            + "[INDEX] [MORE INDEXES] (every index must be a positive integer) "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example 1: " + COMMAND_WORDVAR_1 + " 1 2 3 t/friends \n"
            + "Example 2: " + COMMAND_WORDVAR_2.toUpperCase() + " 2 5 t/classmate \n";

    public static final String MESSAGE_ADD_TAG_SUCCESS = "Added Tag: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_DUPLICATE_TAG = "This tag already exists in all of the given persons.";

    private final ArrayList<Index> targetIndexes;
    private final Tag toAdd;

    /**
     * @param targetIndexes of the persons in the filtered person list to edit
     * @param toAdd tag to add to given target indexes
     */
    public AddTagCommand(ArrayList<Index> targetIndexes, Tag toAdd) {

        requireNonNull(targetIndexes);
        requireNonNull(toAdd);

        this.targetIndexes = targetIndexes;
        this.toAdd = toAdd;
    }

    /**
     * First checks if all target indexes are not out of bounds and then checks if the tag exists in all of
     * the given target indexes of person. If not, add the tag to each target person that doesn't have the given tag.
     */
    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        boolean allPersonsContainsTagToAdd = true;

        for (Index targetIndex : targetIndexes) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }

        for (int i = 0; i < targetIndexes.size(); i++) {
            int targetIndex = targetIndexes.get(i).getZeroBased();
            ReadOnlyPerson readOnlyPerson = lastShownList.get(targetIndex);
            if (!readOnlyPerson.getTags().contains(toAdd)) {
                allPersonsContainsTagToAdd = false;
            }
        }

        if (allPersonsContainsTagToAdd) {
            throw  new CommandException(MESSAGE_DUPLICATE_TAG);
        }

        try {
            model.addTag(this.targetIndexes, this.toAdd);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_ADD_TAG_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddTagCommand)) {
            return false;
        }

        // state check
        AddTagCommand e = (AddTagCommand) other;
        return targetIndexes.equals(e.targetIndexes)
                && toAdd.equals(e.toAdd);
    }
}
