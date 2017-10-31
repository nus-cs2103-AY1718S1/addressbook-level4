package seedu.address.model.person;

import java.util.Comparator;

/**
 * A Person comparator that compares the {@code Name} alphabetically
 */
public class PersonComparator implements Comparator{
    @Override
    public int compare(Object o1, Object o2) {
        Person p1 = (Person) o1;
        Person p2 = (Person) o2;

        String name1 = p1.getName().fullName;
        String name2 = p2.getName().fullName;

        return name1.compareToIgnoreCase(name2);
    }
}
