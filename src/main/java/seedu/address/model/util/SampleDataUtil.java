package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Id;
import seedu.address.model.person.LastUpdated;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        try {
            Person[] person = new Person[] {
                new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"), new Note(""), new Id(""),
                    new LastUpdated("2012-03-08T16:59:00.121Z"), getTagSet("friends"),
                        getMeetingSet("Life insurance", "2017-11-27 10:00")),
                new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Note("Single"), new Id(""),
                    new LastUpdated("2016-07-08T14:30:00.121Z"), getTagSet("colleagues", "friends"),
                        getMeetingSet("Health insurance", "2017-11-28 10:00")),
                new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Note(""), new Id(""),
                    new LastUpdated("2014-02-10T20:00:00.121Z"), getTagSet("neighbours"),
                        getMeetingSet("Introduce policies", "2017-11-25 15:00")),
                new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Note("Divorced"), new Id(""),
                    new LastUpdated("2015-12-02T18:46:23.121Z"), getTagSet("family"),
                         getMeetingSet("Lunch", "2017-12-25 10:30")),
                new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"), new Note(""), new Id(""),
                    new LastUpdated("2017-08-09T12:30:55.121Z"), getTagSet("classmates"),
                        getMeetingSet("Investment policy", "2017-12-05 13:00")),
                new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), new Note("Wife recently passed away"), new Id(""),
                    new LastUpdated("2016-01-01T12:45:55.121212Z"), getTagSet("colleagues"),
                        getMeetingSet("Lunch", "2017-12-22 11:00"))
            };
            for (Person p : person) {
                for (Meeting meeting : p.getMeetings()) {
                    meeting.setPerson(p);
                }
            }
            return person;
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
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) throws IllegalValueException {
        HashSet<Tag> tags = new HashSet<>();
        for (String s : strings) {
            tags.add(new Tag(s));
        }

        return tags;
    }

    /**
     * Returns a meeting set containing the list of meetings given.
     */
    public static Set<Meeting> getMeetingSet(String meetingName, String meetingTime) throws IllegalValueException {
        HashSet<Meeting> meetings = new HashSet<>();
        meetings.add(new Meeting(meetingName, meetingTime));
        return meetings;
    }

}
