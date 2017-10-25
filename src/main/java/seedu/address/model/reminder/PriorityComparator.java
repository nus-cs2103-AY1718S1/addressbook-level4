package seedu.address.model.reminder;

import java.util.Comparator;

/**
 * Compares Age of ReadOnlyPerson
 */
public class PriorityComparator implements Comparator<ReadOnlyReminder> {

    @Override
    public int compare(ReadOnlyReminder firstPrior, ReadOnlyReminder secondPrior) {
        String newFirstPrior = firstPrior.getPriority().toString();
        String newSecondPrior = secondPrior.getPriority().toString();
        if (newFirstPrior.equals("High") || newSecondPrior.equals("High")) {
            return newFirstPrior.compareTo(newSecondPrior);
        } else {
            return newSecondPrior.compareTo(newFirstPrior);
        }
    }
}
