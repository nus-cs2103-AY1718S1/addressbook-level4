# Vanessa
###### /java/seedu/address/logic/commands/DeleteCommand.java
``` java
    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToDelete = lastShownList.get(targetIndex.getZeroBased());


        try {
            model.deletePerson(personToDelete);
            queue.offer(personToDelete);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }

```
###### /java/seedu/address/logic/commands/DeleteCommand.java
``` java
    @Override
    public void setData(Model model, CommandHistory commandHistory,
                        UndoRedoStack undoRedoStack, RecentlyDeletedQueue queue) {
        this.model = model;
        this.queue = queue;
    }
}
```
###### /java/seedu/address/logic/commands/RecentlyDeletedCommand.java
``` java
package seedu.address.logic.commands;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.RecentlyDeletedQueue;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.person.ReadOnlyPerson;

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
        LinkedList<String> deletedAsText = new LinkedList<>();
        Iterator list = previouslyDeleted.listIterator(0);
        //System.out.println(previouslyDeleted.size());
        while (list.hasNext()) {
            String personAsText = (list.next()).toString();
            deletedAsText.add(personAsText);
        }
        //System.out.println(deletedAsText.size());
        return new CommandResult(String.format(MESSAGE_SUCCESS, String.join("\n", deletedAsText)));
    }

    public void setData(Model model, CommandHistory history, UndoRedoStack undoRedoStack, RecentlyDeletedQueue queue) {
        this.queue = queue;
    }
}
```
###### /java/seedu/address/logic/commands/UndoableCommand.java
``` java
    /**
     * Stores the current state of {@code model#addressBook}.
     */
    private void saveAddressBookSnapshot() {
        requireNonNull(model);
        this.previousAddressBook = new AddressBook(model.getAddressBook());
        this.previousQueue = new RecentlyDeletedQueue(queue.getQueue());
    }

```
###### /java/seedu/address/logic/commands/UndoableCommand.java
``` java
    /**
     * Reverts the AddressBook to the state before this command
     * was executed and updates the filtered person list to
     * show all persons.
     */
    protected final void undo() {
        requireAllNonNull(model, previousAddressBook);
        model.resetData(previousAddressBook);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        queue.setQueue(previousQueue.getQueue());
    }

    /**
     * Executes the command and updates the filtered person
     * list to show all persons.
     */
    protected final void redo() {
        requireNonNull(model);
        try {
            executeUndoableCommand();
        } catch (CommandException ce) {
            throw new AssertionError("The command has been successfully executed previously; "
                    + "it should not fail now");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public final CommandResult execute() throws CommandException {
        saveAddressBookSnapshot();
        return executeUndoableCommand();
    }
}
```
###### /java/seedu/address/logic/commands/UndoCommand.java
``` java
    @Override
    public void setData(Model model, CommandHistory commandHistory,
                        UndoRedoStack undoRedoStack, RecentlyDeletedQueue queue) {
        this.model = model;
        this.undoRedoStack = undoRedoStack;
        this.queue = queue;
    }
}
```
###### /java/seedu/address/logic/RecentlyDeletedQueue.java
``` java
package seedu.address.logic;

import java.util.LinkedList;
import java.util.Queue;

import seedu.address.model.person.ReadOnlyPerson;

/**
 * Maintains the Recently Deleted Queue (the list of contacts that were recently deleted,
 * for up to 30 contacts).
 */
public class RecentlyDeletedQueue {
    private Queue<ReadOnlyPerson> recentlyDeletedQueue;
    private int count;

    public RecentlyDeletedQueue() {
        recentlyDeletedQueue = new LinkedList<>();
        count = 0;
    }

    public RecentlyDeletedQueue(LinkedList<ReadOnlyPerson> newQueue) {
        recentlyDeletedQueue = newQueue;
        count = newQueue.size();
    }

    /**
     * Offers the @param person in the queue when the @param person is deleted by
     * {@code DeleteCommand} or {@code DeleterMultipleCommand}.
     */
    public void offer(ReadOnlyPerson person) {
        if (count < 30) {
            recentlyDeletedQueue.offer(person);
            count++;
        } else {
            recentlyDeletedQueue.poll();
            recentlyDeletedQueue.offer(person);
        }
    }

    /**
     * Returns the list of people in the queue.
     */
    public LinkedList<ReadOnlyPerson> getQueue() {
        return new LinkedList<>(recentlyDeletedQueue);
    }

    public void setQueue(LinkedList<ReadOnlyPerson> newQueue) {
        recentlyDeletedQueue = newQueue;
    }

}
```
