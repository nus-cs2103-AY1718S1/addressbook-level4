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

//@@author sushinoya
/**
 * Contains utility methods for populating {@code ResidentBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        try {
            return new Person[] {
                new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Room("09-119"), new Timestamp(0),
                    getTagSet("professor")),

                new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Room("10-100A"), new Timestamp(0),
                    getTagSet("level10", "RA")),

                new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Room("21-118"), new Timestamp(0),
                    getTagSet("level21")),

                new Person(new Name("David Li"), new Phone("91023282"), new Email("lidavid@example.com"),
                    new Room("26-105"), new Timestamp(0),
                    getTagSet("level26")),

                new Person(new Name("Irfan Ibrahim"), new Phone("92492321"), new Email("irfan@example.com"),
                    new Room("17-135F"), new Timestamp(0),
                    getTagSet("level17")),

                new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Room("12-120E"), new Timestamp(0),
                    getTagSet("staff")),

                new Person(new Name("Jeremy Arnold"), new Phone("87432307"), new Email("jeremy@example.com"),
                    new Room("10-152"), new Timestamp(0),
                    getTagSet("professor", "fulltime")),

                new Person(new Name("Peter Vail"), new Phone("99098758"), new Email("peter@example.com"),
                    new Room("05-150A"), new Timestamp(0),
                    getTagSet("level5", "RA")),

                new Person(new Name("Shawna Metzger"), new Phone("98383283"), new Email("shwana@example.com"),
                    new Room("20-119"), new Timestamp(0),
                    getTagSet("level20")),

                new Person(new Name("Varun Gupta"), new Phone("91030982"), new Email("varun@example.com"),
                    new Room("30-115"), new Timestamp(0),
                    getTagSet("level30")),

                new Person(new Name("Govind Koyal"), new Phone("92412321"), new Email("govind@example.com"),
                    new Room("16-134F"), new Timestamp(0),
                    getTagSet("level16", "NUSSU")),

                new Person(new Name("James Hobbit"), new Phone("92620917"), new Email("jamesh@example.com"),
                    new Room("04-120"), new Timestamp(0),
                    getTagSet("staff", "cleaner")),

                new Person(new Name("Leia Krotes"), new Phone("87488807"), new Email("leia@example.com"),
                    new Room("07-119B"), new Timestamp(0),
                    getTagSet("management")),

                new Person(new Name("Sherlock Holmes"), new Phone("90982758"), new Email("sherlock@example.com"),
                    new Room("16-100A"), new Timestamp(0),
                    getTagSet("level16", "RA")),

                new Person(new Name("Harry Jones"), new Phone("93211233"), new Email("harry@example.com"),
                    new Room("07-118"), new Timestamp(0),
                    getTagSet("level7", "staff", "helper")),

                new Person(new Name("Katniss Mallark"), new Phone("92981282"), new Email("kat92@example.com"),
                    new Room("03-115"), new Timestamp(0),
                    getTagSet("level3")),

                new Person(new Name("James T Kirk"), new Phone("98765654"), new Email("kirk@example.com"),
                    new Room("04-135F"), new Timestamp(0),
                    getTagSet("level4")),

                new Person(new Name("Luna Lovegood"), new Phone("92620977"), new Email("luna@example.com"),
                    new Room("21-130"), new Timestamp(0),
                    getTagSet("staff")),

                new Person(new Name("Dolores Umbridge"), new Phone("91928282"), new Email("dolores@example.com"),
                    new Room("16-115"), new Timestamp(0),
                    getTagSet("level16")),

                new Person(new Name("Abeforth"), new Phone("92491321"), new Email("abeforth@example.com"),
                    new Room("15-132"), new Timestamp(0),
                    getTagSet("level15"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static Event[] getSampleEvents() {
        try {
            return new Event[]{
                new Event(new Title("End of Semester Dinner"), new Description("Organised by USC"),
                        new Location("Cinnamon College"), new Datetime("01/11/2017 2030 2")),

                new Event(new Title("USPolymath Talk"), new Description("Talk by Students"),
                        new Location("Chatterbox"), new Datetime("02/11/2017 0800 1")),

                new Event(new Title("USProductions"), new Description("Drama performance"),
                            new Location("Blackbox"), new Datetime("03/10/2017 1200 3")),

                new Event(new Title("Neighbourhood Party"), new Description("Organised by USC"),
                        new Location("YaleNUS"), new Datetime("07/11/2017 2030 2")),

                new Event(new Title("Frisbee Finals"), new Description("Talk by Students"),
                        new Location("MPSH"), new Datetime("08/11/2017 0800 1")),

                new Event(new Title("Chess Training"), new Description("Drama performance"),
                            new Location("Level 4 Lounge"), new Datetime("11/10/2017 1200 3")),

                new Event(new Title("End of Semester Dinner"), new Description("Organised by USC"),
                        new Location("Cinnamon College"), new Datetime("15/11/2017 2030 2")),

                new Event(new Title("Meet Minister"), new Description("Talk by Students"),
                        new Location("Chua Thian Poh Hall"), new Datetime("17/11/2017 0800 1")),

                new Event(new Title("USAmbassadors Meeting"), new Description("Drama performance"),
                            new Location("Dining Hall"), new Datetime("17/10/2017 1200 3")),

                new Event(new Title("Orientation Day 1"), new Description("Organised by House Comm"),
                        new Location("Cinnamon College"), new Datetime("12/12/2017 2030 2")),

                new Event(new Title("Orientation Day 2"), new Description("Talk by Students"),
                        new Location("Cinnamon College"), new Datetime("13/12/2017 2030 2")),

                new Event(new Title("Deans Address"), new Description("College Wide Event"),
                            new Location("UTSH"), new Datetime("19/12/2017 1200 3")),

                new Event(new Title("Diwali Celebrations"), new Description("Festival"),
                        new Location("Cinnamon College"), new Datetime("01/12/2017 2030 2")),

                new Event(new Title("Movie Screening"), new Description("Free Admission"),
                        new Location("Chatterbox"), new Datetime("03/12/2017 0800 3")),

                new Event(new Title("Volleyball Training"), new Description("Sports"),
                            new Location("CTPH"), new Datetime("29/11/2017 1200 3")),

                new Event(new Title("End of Semester Dinner"), new Description("Organised by USC"),
                        new Location("Cinnamon College"), new Datetime("28/10/2017 2030 2")),

                new Event(new Title("USPolymath Talk"), new Description("Talk by Students"),
                        new Location("Chatterbox"), new Datetime("04/10/2017 0800 1")),

                new Event(new Title("USProductions"), new Description("Drama performance"),
                            new Location("Blackbox"), new Datetime("17/09/2017 1200 3")),

                new Event(new Title("Gala Dinner"), new Description("Freshmen Event"),
                        new Location("CHIJMES"), new Datetime("25/09/2017 2030 2")),

                new Event(new Title("Piano Concert"), new Description("By USClassical"),
                        new Location("Chatterbox"), new Datetime("05/09/2017 0800 1")),

                new Event(new Title("New Year Celebrations"), new Description("Festival"),
                            new Location("Blackbox"), new Datetime("01/01/2018 1200 3"))
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
