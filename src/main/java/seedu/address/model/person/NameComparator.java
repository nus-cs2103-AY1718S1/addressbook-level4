package seedu.address.model.person;

import java.util.Comparator;

public class NameComparator implements Comparator <ReadOnlyPerson> {

    @Override
    public int compare(ReadOnlyPerson p1, ReadOnlyPerson p2){
        return p1.getName().fullName.compareToIgnoreCase(p2.getName().fullName);
    }
}
