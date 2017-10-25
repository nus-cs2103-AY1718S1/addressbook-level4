package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;


/**
 * Removes a tag that is shared by a group of contacts.
 */
public class RemoveTagCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "removeTag";
    public static final String COMMAND_ALIAS = "rm";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes a tag that is shared by a group of contacts\n"
            + "Parameters: TAG_NAME \n"
            + "Example: " + COMMAND_WORD + " colleagues";

    public static final String MESSAGE_REMOVE_TAG_SUCCESS = "Removed Tag.";
    public static final String MESSAGE_REMOVE_TAG_ERROR = "Error removing Tag.";

    private String tagName;

    public RemoveTagCommand(String tagName) {
        this.tagName = tagName;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        try {
            for (int i = 0; i < lastShownList.size(); i++) {
                if (lastShownList.get(i).getTags().contains(new Tag(tagName))) {
                    updateTagList(lastShownList, i);
                }
            }

            model.removeTag(new Tag(tagName));
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_REMOVE_TAG_SUCCESS));
    }

    /**
     * Update the tag list of person and refresh on model.
     */
    private void updateTagList(List<ReadOnlyPerson> lastShownList, int i) throws CommandException {
        ReadOnlyPerson personToEdit = lastShownList.get(i);
        Person editedPerson = removedTagFromPerson(personToEdit);

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (PersonNotFoundException e) {
            throw new CommandException(MESSAGE_REMOVE_TAG_ERROR);
        } catch (DuplicatePersonException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit} without @tagName
     */
    private Person removedTagFromPerson(ReadOnlyPerson personToEdit) {
        assert personToEdit != null;

        Set<Tag> updatedTags = new HashSet<>();

        for (Tag t : personToEdit.getTags()) {
            if (t.tagName.equals(this.tagName)) {
                continue;
            } else {
                updatedTags.add(t);
                System.out.println(t.tagName);
            }
        }

        return new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getTimestamp(), updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemoveTagCommand)) {
            return false;
        }

        // state check
        RemoveTagCommand e = (RemoveTagCommand) other;
        return tagName.equals(e.tagName);
    }
}
