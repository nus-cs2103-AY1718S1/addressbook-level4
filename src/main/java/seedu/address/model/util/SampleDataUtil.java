package seedu.address.model.util;

import static java.util.Arrays.asList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Country;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.email.Email;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.schedule.Activity;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.ScheduleDate;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        try {
            return new Person[] {
                new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Country(""), getEmailSet("alexyeoh@example.com"),
                    new Address("30, Geylang Street 29, #06-40, Singapore 760770"), getScheduleSet(asList("15-01-2017",
                    "03-01-2018"), asList("Team meeting", "Interview")), getTagSet("friends")),
                new Person(new Name("Bernice Yu"), new Phone("+65 99272758"), new Country("65"), getEmailSet("berniceyu@example.com"),
                    new Address("30, Lorong 3 Serangoon Gardens, #07-18, Singapore 807777"), getScheduleSet(asList(
                    "15-01-2017", "04-04-2017"), asList("Team meeting", "Team bonding")),
                    getTagSet("colleagues", "friends")),
                new Person(new Name("Charlotte Oliveiro"), new Phone("+84 93210283"), new Country("84"), getEmailSet("charlotte@example.com"),
                    new Address("11, Ang Mo Kio Street 74, #11-04, singapore 506666"),
                    getScheduleSet(asList("15-01-2017", "04-04-2017"), asList("Team meeting", "Team bonding")),
                    getTagSet("neighbours")),
                new Person(new Name("David Li"), new Phone("+65 91031282"), new Country("65"), getEmailSet("lidavid@example.com"),
                    new Address("436, Serangoon Gardens Street 26, #16-43, singapore 707777"), getScheduleSet(asList(
                    "15-01-2017", "03-01-2018"), asList("Team meeting", "Interview")), getTagSet("family")),
                new Person(new Name("Irfan Ibrahim"), new Phone("+58 92492021"), new Country("58"), getEmailSet("irfan@example.com"),
                    new Address("47, Tampines Street 20, #17-35, singapore 303333"),
                    getScheduleSet(asList("15-01-2017", "08-09-2018"), asList("Team meeting", "Study Tour")),
                    getTagSet("classmates")),
                new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Country(""), getEmailSet("royb@example.com"),
                    new Address("45, Aljunied Street 85, #11-31, singapore 304444"),
                    getScheduleSet(asList("15-01-2017", "25-12-2017"), asList("Team meeting", "Christmas dinner")),
                    getTagSet("colleagues"))
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

    /**
     * Returns a schedule set containing the list of schedule dates and activities given.
     * pre-condition: the number of elements in scheduleDates must be the same as that of activities.
     */
    public static Set<Schedule> getScheduleSet(List<String> scheduleDates, List<String> activities)
            throws IllegalValueException {
        HashSet<Schedule> schedules = new HashSet<>();
        for (int i = 0; i < scheduleDates.size(); i++) {
            schedules.add(new Schedule(new ScheduleDate(scheduleDates.get(i)), new Activity(activities.get(i))));
        }

        return schedules;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Email> getEmailSet(String... strings) throws IllegalValueException {
        HashSet<Email> emails = new HashSet<>();
        for (String s : strings) {
            emails.add(new Email(s));
        }

        return emails;
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
