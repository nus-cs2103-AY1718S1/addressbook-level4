package seedu.address.model.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.event.Event;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.property.Address;
import seedu.address.model.property.DateTime;
import seedu.address.model.property.Email;
import seedu.address.model.property.Name;
import seedu.address.model.property.Phone;
import seedu.address.model.property.PropertyManager;
import seedu.address.model.property.exceptions.PropertyNotFoundException;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        try {
            return new Person[] {
                new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"),
                    getTagSet("friends")),
                new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    getTagSet("colleagues", "friends")),
                new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    getTagSet("neighbours")),
                new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    getTagSet("family")),
                new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"),
                    getTagSet("classmates")),
                new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"),
                    getTagSet("colleagues"))
            };
        } catch (IllegalValueException | PropertyNotFoundException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    //@@author yunpengn

    public static ArrayList<Event> getSampleEvents() {
        try {
            ArrayList<Event> events = new ArrayList<>();
            String reminderMsg = "You have an event!";

            Event event1 = new Event(new Name("Volleyball Practice"), new DateTime("19102017 08:30"),
                        new Address("OCBC ARENA Hall 3, #01-111"), new ArrayList<>());
            event1.addReminder(new Reminder(event1, reminderMsg));
            Event event2 = new Event(new Name("CS2103T Lecture"), new DateTime("20102017 14:00"),
                        new Address("iCube Auditorium, NUS"), new ArrayList<>());
            event2.addReminder(new Reminder(event2, reminderMsg));
            Event event3 = new Event(new Name("Project Meeting"), new DateTime("20102017 14:00"),
                        new Address("iCube Auditorium, NUS"), new ArrayList<>());
            event3.addReminder(new Reminder(event3, reminderMsg));
            Event event4 = new Event(new Name("Family Lunch"), new DateTime("20112017 13:00"),
                        new Address("Sakae Sushi, Causeway Point"), new ArrayList<>());
            event4.addReminder(new Reminder(event4, reminderMsg));
            Event event5 = new Event(new Name("Movie date"), new DateTime("22112017 22:00"),
                        new Address("Golden Village Yishun"), new ArrayList<>());
            event5.addReminder(new Reminder(event5, reminderMsg));
            Event event6 = new Event(new Name("Consultation for EE2020"), new DateTime("23112017 16:00"),
                        new Address("E3-06-14, Faculty of Engineering, NUS "), new ArrayList<>());
            event6.addReminder(new Reminder(event6, reminderMsg));
            Event event7 = new Event(new Name("Project Meeting for CS2101"), new DateTime("31112017 09:00"),
                        new Address("SR09, School of Computing"), new ArrayList<>());
            event7.addReminder(new Reminder(event7, reminderMsg));
            Event event8 = new Event(new Name("Dental Appointment"), new DateTime("02122017 14:00"),
                        new Address("National Dental Centre"), new ArrayList<>());
            event8.addReminder(new Reminder(event8, reminderMsg));
            Event event9 = new Event(new Name("Volleyball Practice"), new DateTime("08122017 18:00"),
                        new Address("OCBC ARENA Hall 3, #01-111"), new ArrayList<>());
            event9.addReminder(new Reminder(event9, reminderMsg));
            Event event10 = new Event(new Name("Lunch with OG mates"), new DateTime("09122017 14:00"),
                        new Address("The Deck, FASS, NUS"), new ArrayList<>());
            event10.addReminder(new Reminder(event10, reminderMsg));
            Event event11 = new Event(new Name("Family Dinner"), new DateTime("11122017 19:00"),
                        new Address("Home Sweet Home"), new ArrayList<>());
            event11.addReminder(new Reminder(event11, reminderMsg));

            events.add(event1);
            events.add(event2);
            events.add(event3);
            events.add(event4);
            events.add(event5);
            events.add(event6);
            events.add(event7);
            events.add(event8);
            events.add(event9);
            events.add(event10);
            events.add(event11);
            return events;


        } catch (IllegalValueException | PropertyNotFoundException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }
    //@@author

    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAb = new AddressBook();

            // Initialize the PropertyManager by adding all the preLoaded properties.
            PropertyManager.initializePropertyManager();

            for (Person samplePerson : getSamplePersons()) {
                sampleAb.addPerson(samplePerson);
            }

            for (Event sampleEvent : getSampleEvents()) {
                sampleAb.addEvent(sampleEvent);
            }

            return sampleAb;
        } catch (DuplicatePersonException | DuplicateEventException e) {
            throw new AssertionError("sample data cannot contain duplicate persons/events", e);
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
