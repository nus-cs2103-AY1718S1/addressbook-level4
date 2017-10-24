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
            }

            // Iterate through our list of people, if we find a name in our group list then edit the person to be in the
            // group
            try {
                for (int i = 1; i < args.size() ; i++) {
                    for (Iterator<ReadOnlyPerson> it = model.getFilteredPersonList().iterator(); it.hasNext(); ) {

                        ReadOnlyPerson p = it.next();
                        if (p.getName().fullName.toLowerCase().contains(args.get(i).toLowerCase())) {
                            ReadOnlyPerson newPerson = new Person(p.getName(), p.getPhone(), p.getEmail(), p.getAddress(),
                                    p.getTags(), p.getRemark(), group);
                            model.updatePerson(p, newPerson);
                            break;
                        }
                    }
                }

                return new CommandResult( MESSAGE_SUCCESS);
            } catch (DuplicatePersonException e ) {
                throw new CommandException(e.getMessage());
            } catch (PersonNotFoundException e) {
                throw new CommandException(e.getMessage());
            }
        } catch (GroupNotFoundException e) {
            throw new CommandException(e.getMessage());
        } catch (DuplicateGroupException e ){
            throw new CommandException(e.getMessage());
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GroupCommand // instanceof handles nulls
                && args.equals(((GroupCommand) other).args));
    }
}
