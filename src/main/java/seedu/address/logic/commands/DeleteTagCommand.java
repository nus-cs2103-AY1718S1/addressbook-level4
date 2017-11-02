//@@author majunting
package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_EXECUTION_FAILURE;

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
 * deletes a tag from all persons in addressbook.
 */
public class DeleteTagCommand extends Command {

    public static final String COMMAND_WORD = "deleteTag";
    public static final String COMMAND_ALIAS = "dt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a specific tag from all persons in addressbook.\n"
            + "Parameters: KEYWORD\n"
            + "Example: " + COMMAND_WORD + " friends";

    private static final String MESSAGE_DELETE_TAG_SUCCESS = "Tag deleted.";
    private static final String MESSAGE_DELETE_TAG_FAILED = "Tag deletion unsuccessful.";
    private final String keyword;

    public DeleteTagCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public CommandResult execute() throws CommandException {
        boolean isTagDeleted;
        try {
            isTagDeleted = deleteTag(new Tag(keyword));
        } catch (IllegalValueException ive) {
            throw new CommandException(" ");
        }
        if (isTagDeleted) {
            return new CommandResult(MESSAGE_DELETE_TAG_SUCCESS);
        } else {
            return new CommandResult(MESSAGE_DELETE_TAG_FAILED);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if same object
                || (other instanceof DeleteTagCommand // instanceof handles nulls
                && this.keyword.equals(((DeleteTagCommand) other).keyword)); // state check
    }

    /**
     * executes delete tag action, returns a boolean value to indicate status of tag deletion
     * @param tag
     * @return whether a tag is deleted successfully
     * @throws CommandException
     */
    private boolean deleteTag(Tag tag) throws CommandException {
        boolean isTagDeleted = false;
        List<ReadOnlyPerson> personList = model.getFilteredPersonList();

        for (int i = 0; i < personList.size(); i++) {
            ReadOnlyPerson originalPerson = personList.get(i);
            Set<Tag> tagList = originalPerson.tagProperty().get().toSet();
            if (tagList.contains(tag)) {
                tagList.remove(tag);
                isTagDeleted = true;
            }
            Person newPerson = new Person(originalPerson.getName(), originalPerson.getPhone(),
                    originalPerson.getEmail(), originalPerson.getAddress(),
                    originalPerson.getBirthday(), originalPerson.getRemark(), tagList);
            try {
                model.updatePerson(originalPerson, newPerson);
                model.propagateToGroup(originalPerson, newPerson, this.getClass());
            } catch (DuplicatePersonException dpe) {
                throw new CommandException(MESSAGE_EXECUTION_FAILURE, "Duplicate person found in addressbook");
            } catch (PersonNotFoundException pnfe) {
                throw new CommandException(MESSAGE_EXECUTION_FAILURE, "Person not found exception");
            }
        }
        return isTagDeleted;
    }
}
//@@author
