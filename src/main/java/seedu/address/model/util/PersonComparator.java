package seedu.address.model.util;

import seedu.address.model.person.Person;

import java.util.Comparator;

public class PersonComparator implements Comparator<Person>{

    public int compare(Person o1, Person o2 )
    {
        if( o1 == o2 )
            return 0;
        if( o1 == null )
            return 1;
        if( o2 == null )
            return -1;
        return o1.getName().toString().compareTo( o2.getName().toString() );
    }
}