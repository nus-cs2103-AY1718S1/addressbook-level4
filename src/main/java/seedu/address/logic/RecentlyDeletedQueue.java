package seedu.address.logic;

import java.util.Queue;
import java.util.LinkedList;
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

    /**
     * Offers the {@param person} in the queue when the {@param person} is deleted by
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


}
