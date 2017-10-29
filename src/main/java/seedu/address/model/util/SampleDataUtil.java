package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.EventList;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyEventList;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDescription;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventTime;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.PortraitPath;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        try {
            return new Person[] {
                new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"), new Birthday("01/01/1993"),
                    new PortraitPath(""), getTagSet("friends"), new HashSet<>()),
                new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Birthday("30/12/1994"),
                    new PortraitPath(""), getTagSet("colleagues", "friends"), new HashSet<>()),
                new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Birthday("15/03/1990"),
                    new PortraitPath(""), getTagSet("neighbours"), new HashSet<>()),
                new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Birthday("03/11/1984"),
                    new PortraitPath(""), getTagSet("family"), new HashSet<>()),
                new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"), new Birthday("22/05/1994"),
                    new PortraitPath(""), getTagSet("classmates"), new HashSet<>()),
                new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), new Birthday("08/06/1996"),
                    new PortraitPath(""), getTagSet("colleagues"), new HashSet<>())
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static Event[] getSampleEvents() {
        try {
            return new Event[] {
                new Event(new EventName("First meeting"), new EventDescription("Discuss coding"),
                    new EventTime("10/10/2017")),
                new Event(new EventName("Second meeting"), new EventDescription("Discuss PPT"),
                    new EventTime("13/10/2017")),
                new Event(new EventName("Third meeting"), new EventDescription("Discuss presentation"),
                    new EventTime("17/10/2017")),
                new Event(new EventName("Forth meeting"), new EventDescription("Discuss demo"),
                    new EventTime("03/11/2017")),
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAb = new AddressBook();
            for (Person samplePerson : getSamplePersons()) {
                sampleAb.addPerson(samplePerson);
            }
            return sampleAb;
        } catch (DuplicatePersonException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        }
    }

    public static ReadOnlyEventList getSampleEventList() {
        try {
            EventList sampleEl = new EventList();
            for (Event sampleEvent : getSampleEvents()) {
                sampleEl.addEvent(sampleEvent);
            }
            return sampleEl;
        } catch (DuplicateEventException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
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

}
