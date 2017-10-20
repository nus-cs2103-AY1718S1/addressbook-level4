package seedu.address.logic.commands;

import java.util.*;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.RecentlyDeletedQueue;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Shows a list of recently deleted contacts and their details, up to the last 30 contacts deleted.
 */
public class RecentlyDeletedCommand extends Command {
    public static final String COMMAND_WORD = "recentlyDel";
    public static final String COMMAND_ALIAS = "recentD";
    public static final String MESSAGE_SUCCESS = "Listed all recently deleted:\n%1$s";
    public static final String MESSAGE_NO_RECENTLY_DELETED = "You have not yet deleted any contacts.";

    @Override
    public CommandResult execute() {
        LinkedList<ReadOnlyPerson> previouslyDeleted;
        previouslyDeleted = queue.getQueue();

        if (previouslyDeleted.isEmpty()) {
            return new CommandResult(MESSAGE_NO_RECENTLY_DELETED);
        }

        Collections.reverse(previouslyDeleted);
        LinkedList<String> DeletedAsText = new LinkedList<>();
        Iterator list = previouslyDeleted.listIterator(0);
        //System.out.println(previouslyDeleted.size());
        while(list.hasNext()) {
            String personAsText = (list.next()).toString();
            DeletedAsText.add(personAsText);
        }
        //System.out.println(DeletedAsText.size());
        return new CommandResult(String.format(MESSAGE_SUCCESS, String.join("\n", DeletedAsText)));
    }

    public void setData(Model model, CommandHistory history, UndoRedoStack undoRedoStack, RecentlyDeletedQueue queue) {
        this.queue = queue;
    }
}
