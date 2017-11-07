package seedu.address.model.util;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.group.Group;
import seedu.address.model.person.*;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.group.DuplicateGroupException;
import seedu.address.model.tag.Tag;

import java.util.HashSet;
import java.util.Set;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        try {
            return new Person[] {
                    //@@author yanji1221
                new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Birthday("1993/11/12"), new Address("Blk 30 Geylang Street 29, #06-40"), new ProfilePage("www.facebook.com"),
                    getTagSet("friends"),getGroupSet("Soccer","Band")),
                new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Birthday("1988/12/22"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new ProfilePage("www.facebook.com"),
                    getTagSet("colleagues", "friends"),getGroupSet("Band")),
                new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Birthday("1987/12/12"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new ProfilePage("www.facebook.com"),
                    getTagSet("neighbours"),getGroupSet("Painting")),
                    //@@author
                new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Birthday("1999/01/01"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new ProfilePage("www.facebook.com"),
                    getTagSet("family"),getGroupSet("Soccer")),
                new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Birthday("1985/03/04"), new Address("Blk 47 Tampines Street 20, #17-35"), new ProfilePage("www.facebook.com"),
                    getTagSet("classmates"),getGroupSet("Soccer")),
                new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Birthday("1983/05/08"), new Address("Blk 45 Aljunied Street 85, #11-31"),new ProfilePage("www.facebook.com"),
                    getTagSet("colleagues"),getGroupSet("Band"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static Group[] getSampleGroups(){
        try
        {
            return new Group[]{
                    new Group(new Name("Soccer"),getPersonSet("Alex Yeoh","David Li","Irfan Ibrahim")),
                    new Group(new Name("Band"),getPersonSet("Alex Yeoh","Bernice Yu","Roy Balakrishnan")),
                    new Group(new Name("Painting"),getPersonSet("Charlotte Oliveiro"))
            };
        }
        catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAb = new AddressBook();
            for (Person samplePerson : getSamplePersons()) {
                sampleAb.addPerson(samplePerson);
            }
            for (Group sampleGroup : getSampleGroups()) {
                sampleAb.addGroup(sampleGroup);
            }
            return sampleAb;
        } catch (DuplicatePersonException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        }
        catch (DuplicateGroupException e) {
            throw new AssertionError("sample data cannot contain duplicate groups", e);
        }
        catch (IllegalValueException e) {
            throw new AssertionError("sample Data has invalid group names", e);
        }
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) throws IllegalValueException {
        HashSet<Tag> tags = new HashSet<>();
        for (String s : strings) {
            tags.add(new Tag(s));
        }

        return tags;
    }
    public static Set<Group> getGroupSet(String... strings) throws IllegalValueException {
        HashSet<Group> groups = new HashSet<>();
        for (String s : strings) {
            Name name=new Name(s);
            groups.add(new Group(name));
        }

        return groups;
    }
    public static Set<ReadOnlyPerson> getPersonSet(String... strings) throws IllegalValueException {
        HashSet<ReadOnlyPerson> persons = new HashSet<>();
        for (String s : strings) {
            Name name=new Name(s);
            persons.add(new Person(name));
        }

        return persons;
    }


}
