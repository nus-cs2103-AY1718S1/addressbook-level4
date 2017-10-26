package seedu.address.logic.commands;

import java.util.Iterator;
import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.person.Group;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;

import seedu.address.model.person.exceptions.DuplicateGroupException;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.GroupNotFoundException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * This command is used to add people to an existing group
 * If the group doesn't already exist, the command will create it
 */

public class GroupCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "group";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " [group name] [names to add to group]. Example: "
            + COMMAND_WORD + " Vietnam Grant Ali Joey";
    public static final String MESSAGE_PARAMETERS = "[group name] [person names ...]";
    public static final String MESSAGE_SUCCESS = "Group command executed";
    public static final String MESSAGE_FAILURE = "Group command failed.";

    private final List<String> args;

    public GroupCommand (List<String> args) {
        this.args = args;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        String groupName = args.get(0);
        Group group = new Group (groupName);
        try {
            if (!model.groupExists (group)) {
                model.addGroup (group);
                editPersonGroups(args, group);
            } else {
                model.deleteGroup(group);
            }

            return new CommandResult (MESSAGE_SUCCESS);

        } catch (DuplicateGroupException | GroupNotFoundException e) {
            throw new CommandException(e.getMessage());
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GroupCommand // instanceof handles nulls
                && args.equals(((GroupCommand) other).args));
    }

    /**
     * Iterates through the person list from the model and updates them to add the group
     * @param args persons to add group to
     * @param group to add to person
     */
    private void editPersonGroups (List<String> args, Group group) throws CommandException {
        for (int i = 1; i < args.size(); i++) {
            for (Iterator<ReadOnlyPerson> it = model.getFilteredPersonList().iterator(); it.hasNext(); ) {
                ReadOnlyPerson p = it.next();
                if (p.getName().fullName.toLowerCase().contains(args.get(i).toLowerCase())) {
                    ReadOnlyPerson newPerson = new Person(p.getName(), p.getPhone(), p.getEmail(),
                            p.getAddress(), p.getTags(),p.getExpiryDate(), p.getRemark(), group, p.getImage());
                    try {
                        model.updatePerson(p, newPerson);
                    } catch (DuplicatePersonException | PersonNotFoundException e) {
                        throw new CommandException(MESSAGE_FAILURE);
                    }
                    break;
                }
            }
        }
    }
}
