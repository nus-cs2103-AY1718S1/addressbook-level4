package seedu.address.model.util;

import java.util.Comparator;

import seedu.address.model.person.Person;

/**
 * Comparator for FXCollections.sort() usage
 */
public class PersonComparator implements Comparator<Person> {

    @Override
    public int compare(Person o1, Person o2) {
        if (o1.equals(o2)) {
            return 0;
        }
        if (o1 == null) {
            return 1;
        }
        if (o2 == null) {
            return -1;
        }
        return o1.getName().compareTo(o2.getName());
    }
}
