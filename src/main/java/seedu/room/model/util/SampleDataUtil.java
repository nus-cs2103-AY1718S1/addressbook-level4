package seedu.room.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.room.commons.exceptions.IllegalValueException;
import seedu.room.model.EventBook;
import seedu.room.model.ReadOnlyEventBook;
import seedu.room.model.ReadOnlyResidentBook;
import seedu.room.model.ResidentBook;
import seedu.room.model.event.Datetime;
import seedu.room.model.event.Description;
import seedu.room.model.event.Event;
import seedu.room.model.event.Location;
import seedu.room.model.event.Title;
import seedu.room.model.event.exceptions.DuplicateEventException;
import seedu.room.model.person.Email;
import seedu.room.model.person.Name;
import seedu.room.model.person.Person;
import seedu.room.model.person.Phone;
import seedu.room.model.person.Room;
import seedu.room.model.person.Timestamp;
import seedu.room.model.person.exceptions.DuplicatePersonException;
import seedu.room.model.tag.Tag;

/**
 * Contains utility methods for populating {@code ResidentBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        try {
            return new Person[] {
                new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Room("09-119"), new Timestamp(0),
                    getTagSet("master")),
                new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Room("09-100A"), new Timestamp(0),
                    getTagSet("level5", "RA")),
                new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Room("21-118"), new Timestamp(0),
                    getTagSet("level6")),
                new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Room("26-105"), new Timestamp(0),
                    getTagSet("level8")),
                new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Room("17-135F"), new Timestamp(0),
                    getTagSet("level20")),
                new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Room("03-130"), new Timestamp(0),
                    getTagSet("staff"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static Event[] getSampleEvents() {
        try {
            return new Event[]{
                new Event(new Title("End of Semester Dinner"), new Description("Organised by USC"),
                        new Location("Cinnamon College"), new Datetime("25/11/2017 2030 2")),
                new Event(new Title("USPolymath Talk"), new Description("Talk by Students"),
                        new Location("Chatterbox"), new Datetime("1/11/2017 0800 1")),
                new Event(new Title("USProductions"), new Description("Drama performance"),
                            new Location("Blackbox"), new Datetime("17/10/2017 1200 3"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyResidentBook getSampleResidentBook() {
        try {
            ResidentBook sampleAb = new ResidentBook();
            for (Person samplePerson : getSamplePersons()) {
                sampleAb.addPerson(samplePerson);
            }
            return sampleAb;
        } catch (DuplicatePersonException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        }
    }

    public static ReadOnlyEventBook getSampleEventBook() {
        try {
            EventBook sampleEb = new EventBook();
            for (Event sampleEvent : getSampleEvents()) {
                sampleEb.addEvent(sampleEvent);
            }
            return sampleEb;
        } catch (DuplicateEventException e) {
            throw new AssertionError("sample data cannot contain duplicate events", e);
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
