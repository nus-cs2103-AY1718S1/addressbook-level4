package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

//@@author tingtx

/**
 * Group person(s) into user defined group.
 */
public class GroupCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "group";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Group the person(s) identified "
            + "by the index number used in the last person listing.\n "
            + "Person(s) will be grouped to the group name specified.\n"
            + "Parameters: INDEX_1 [INDEX_2...] (must be a positive integer) "
            + PREFIX_GROUP + "GROUPNAME "
            + "Example: " + COMMAND_WORD + " 1 3 4 "
            + PREFIX_GROUP + "FAMILY \n"
            + "To view the existing groups."
            + "Parameters : SHOWALL (case insensitive)\n"
            + "Example: " + COMMAND_WORD + " SHOWALL";

    public static final String MESSAGE_GROUP_PERSON_SUCCESS = "Grouped Person(s) to ";
    public static final String MESSAGE_UNGROUP_PERSON_SUCCESS = "Person(s) removed from group.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String SHOW_ALL_GROUP = "showall";
    public static final String MESSAGE_WRONG_PARAMETER = "To view existing groups, "
            + "the parameter must be SHOWALL\n" + "To group person(s) : " + COMMAND_WORD + " 1 3 4 "
            + PREFIX_GROUP + "FAMILY";

    private final List<Index> indexes;
    private final String group;

    /**
     * @param indexes of the person in the filtered person list to edit
     * @param group   group name to group the person with
     */
    public GroupCommand(List<Index> indexes, String group) {
        requireNonNull(indexes);
        requireNonNull(group);

        this.indexes = indexes;
        this.group = group;
    }

    public static String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        CommandResult commandResult;
        if (group.equalsIgnoreCase(SHOW_ALL_GROUP)) {
            commandResult = showAllGroupName();
        } else if (indexes.size() > 0) {
            commandResult = setGroupToPerson();
        } else {
            throw new CommandException(MESSAGE_WRONG_PARAMETER);
        }

        return commandResult;

    }

    private CommandResult showAllGroupName() {

        return new CommandResult("Groups:  " + model.getGroupList().toString()
                .replaceAll("\\[", "").replaceAll("\\]", ""));
    }

    private CommandResult setGroupToPerson() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        for (Index index : indexes) {

            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException("Index " + index.toString() + " is invalid!");
            }

            ReadOnlyPerson personToGroup = lastShownList.get(index.getZeroBased());

            try {
                Person editedPerson = new Person(personToGroup.getName(), personToGroup.getPhone(),
                        personToGroup.getEmail(), personToGroup.getAddress(), personToGroup.getBirthday(),
                        new Group(group), personToGroup.getRemark(), personToGroup.getTags());
                model.updatePerson(personToGroup, editedPerson);
                model.saveToEncryptedFile();
            } catch (DuplicatePersonException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            } catch (IllegalValueException ive) {
                throw new CommandException(ive.getMessage());
            } catch (PersonNotFoundException pnfe) {
                throw new CommandException("The target person cannot be missing");
            }

        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(generateSuccessMessage());
    }

    /**
     * @return the generated success message
     */
    private String generateSuccessMessage() {
        if (!group.isEmpty()) {
            return MESSAGE_GROUP_PERSON_SUCCESS + group;
        } else {
            return MESSAGE_UNGROUP_PERSON_SUCCESS;
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GroupCommand)) {
            return false;
        }

        // state check
        GroupCommand groupCommand = (GroupCommand) other;
        return indexes.equals(groupCommand.indexes)
                && group.equals(groupCommand.group);
    }
}
