package seedu.address.logic;

import java.util.*;

import seedu.address.model.person.ReadOnlyPerson;

public class RecentlyDeletedQueue {
    private Queue<ReadOnlyPerson> recentlyDeletedQueue;
    int count;

    public RecentlyDeletedQueue() {
        recentlyDeletedQueue = new LinkedList<>();
        count = 0;
    }

    public void offer(ReadOnlyPerson person) {
        if(count < 30) {
            recentlyDeletedQueue.offer(person);
            count++;
        }
        else {
            recentlyDeletedQueue.poll();
            recentlyDeletedQueue.offer(person);
        }
    }

    public LinkedList<ReadOnlyPerson> getQueue() {
        return new LinkedList<>(recentlyDeletedQueue);
    }


}
