package seedu.address.model.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.Meeting;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyMeetingList;
import seedu.address.model.UniqueMeetingList;
import seedu.address.model.exceptions.DuplicateMeetingException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.InternalId;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.SearchData;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        try {
            return new Person[] {
                new Person(new InternalId(1), new Name("Alex Yeoh"), new Phone("87438807"),
                    new Email("alexyeoh@example.com"), new Address("Blk 30 Geylang Street 29, #06-40"),
                    getTagSet("friends"), new SearchData("0")),
                new Person(new InternalId(2), new Name("Bernice Yu"), new Phone("99272758"),
                    new Email("berniceyu@example.com"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    getTagSet("colleagues", "friends"), new SearchData("0")),
                new Person(new InternalId(3), new Name("Charlotte Oliveiro"), new Phone("93210283"),
                    new Email("charlotte@example.com"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    getTagSet("neighbours"), new SearchData("0")),
                new Person(new InternalId(4), new Name("David Li"), new Phone("91031282"),
                    new Email("lidavid@example.com"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    getTagSet("family"), new SearchData("0")),
                new Person(new InternalId(5), new Name("Irfan Ibrahim"), new Phone("92492021"),
                    new Email("irfan@example.com"), new Address("Blk 47 Tampines Street 20, #17-35"),
                    getTagSet("classmates"), new SearchData("0")),
                new Person(new InternalId(6), new Name("Roy Balakrishnan"), new Phone("92624417"),
                    new Email("royb@example.com"), new Address("Blk 45 Aljunied Street 85, #11-31"),
                    getTagSet("colleagues"), new SearchData("0")),
                new Person(new InternalId(7), new Name("Sri Pasta"), new Phone("92381234"),
                    new Email("sriatpasta@gmail.com"), new Address("Blk 44 holland drive, #07-11"),
                    getTagSet("colleagues"), new SearchData("0")),
                new Person(new InternalId(8), new Name("Martini"), new Phone("98124567"),
                    new Email("martiniwongti@example.com"), new Address("Blk 83 Commonwealth Close, #03-01"),
                    getTagSet("friends"), new SearchData("0")),
                new Person(new InternalId(9), new Name("Deng Xing Xing"), new Phone("87231123"),
                    new Email("denguemin@hotmail.com"), new Address("Blk 229 Lorong 1 Toa Payoh, #09-04"),
                    getTagSet("friends"), new SearchData("0")),
                new Person(new InternalId(10), new Name("Yong Kong Kang"), new Phone("98664345"),
                    new Email("kongkang@mail.com"), new Address("Blk 127 HDB Toa Payoh, #10-10"),
                    getTagSet("friends"), new SearchData("0")),
                new Person(new InternalId(11), new Name("Stanley Tay"), new Phone("93456723"),
                    new Email("stay95@yahoo.com"), new Address("Blk 443 Ang Mo Kio Avenue 10, #12-05"),
                    getTagSet("friends", "colleagues"), new SearchData("0")),
                new Person(new InternalId(12), new Name("Bobby Tay"), new Phone("89007654"),
                    new Email("btay95@yahoo.com"), new Address("Blk 443 Ang Mo Kio Avenue 10, #12-05"),
                    getTagSet("friends"), new SearchData("0")),
                new Person(new InternalId(13), new Name("Melissa Tay"), new Phone("88767754"),
                    new Email("mtay@yahoo.com"), new Address("Blk 443 Ang Mo Kio Avenue 10, #12-05"),
                    getTagSet("friends"), new SearchData("0")),
                new Person(new InternalId(14), new Name("Glen Goh"), new Phone("99098977"),
                    new Email("glenthegoh@gmail.com"), new Address("Blk 681 Hougang Avenue 8, #06-12"),
                    getTagSet("money"), new SearchData("0")),
                new Person(new InternalId(15), new Name("Albert Goh"), new Phone("91123367"),
                    new Email("indogod@gmail.com"), new Address("Blk 681 Hougang Avenue 8, #06-12"),
                    getTagSet("money", "friends", "gym"), new SearchData("0")),
                new Person(new InternalId(16), new Name("Michelle Lim"), new Phone("90087654"),
                    new Email("michelim@hotmail.com"), new Address("Blk 123 UTR Street 55, #12-15"),
                    getTagSet("classmate"), new SearchData("0")),
                new Person(new InternalId(17), new Name("Liu Hang"), new Phone("97765580"),
                    new Email("hangl@gmail.com"), new Address("Blk 14 Telok Blangah Crescent, #06-05"),
                    getTagSet("classmate"), new SearchData("0")),
                new Person(new InternalId(18), new Name("Bob Tan"), new Phone("99347654"),
                    new Email("bobtan999@hotmail.com"), new Address("Blk 837 Hougang Central, #12-14"),
                    getTagSet("classmate", "climber"), new SearchData("0")),
                new Person(new InternalId(19), new Name("Gary Ong"), new Phone("93312609"),
                    new Email("garythesnail@gmail.com"), new Address("Blk 123 Toa Payoh Lorong 1, #08-04"),
                    getTagSet("colleagues"), new SearchData("0")),
                new Person(new InternalId(20), new Name("Samuel Ong"), new Phone("94326172"),
                    new Email("xxxONSENxxx@hotmail.com"), new Address("820 Thompson Rd, #03-03"),
                    getTagSet("colleagues"), new SearchData("0"))
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
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) throws IllegalValueException {
        HashSet<Tag> tags = new HashSet<>();
        for (String s : strings) {
            tags.add(new Tag(s));
        }

        return tags;
    }

    public static ReadOnlyMeetingList getSampleMeetingList() {
        try {
            UniqueMeetingList meetings = new UniqueMeetingList();
            for (Meeting sampleMeeting : getSampleMeetings()) {
                meetings.add(sampleMeeting);
            }
            return meetings;
        } catch (DuplicateMeetingException e) {
            throw new AssertionError("sample data cannot contain duplicate meetings", e);
        }
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static ArrayList<InternalId> getParticipantList(Integer... ids) throws IllegalValueException {
        ArrayList<InternalId> sampleParticipantsList = new ArrayList<>();
        for (Integer id : ids) {
            sampleParticipantsList.add(new InternalId(id));
        }

        return sampleParticipantsList;
    }

    public static Meeting[] getSampleMeetings() {
        try {
            LocalDateTime now = LocalDateTime.now();
            return new Meeting[] {
                new Meeting(LocalDateTime.of(now.getYear() + 1, 1, 1, 0, 0),
                        "Home", "New Year Celebration", getParticipantList(1, 2, 3)),
                new Meeting(LocalDateTime.of(now.getYear() + 1, 1, 15, 14, 0),
                        "COM1-02-10", "Project Meeting", getParticipantList(4, 5)),
                new Meeting(LocalDateTime.of(now.getYear() + 1, 2, 1, 10, 0),
                        "Grandparent's place", "Chinese New Year Visitation", getParticipantList(1, 3, 5)),
                new Meeting(LocalDateTime.of(now.getYear() + 1, 2, 13, 19, 30),
                        "Home", "Birthday Celebration", getParticipantList(1, 2, 4)),
                new Meeting(LocalDateTime.of(now.getYear() + 1, 3, 1, 12, 0),
                        "City Hall", "Classmate Gathering", getParticipantList(1, 3, 4)),
                new Meeting(LocalDateTime.of(now.getYear() + 1, 3, 15, 14, 0),
                        "Home", "Project Meeting", getParticipantList(4, 5)),
                new Meeting(LocalDateTime.of(now.getYear() + 1, 3, 30, 15, 30),
                        "Clementi", "Dental Appointment", getParticipantList(7)),
                new Meeting(LocalDateTime.of(now.getYear() + 1, 4, 1, 13, 0),
                        "UTown Starbucks", "Project Meeting", getParticipantList(4, 5)),
                new Meeting(LocalDateTime.of(now.getYear() + 1, 4, 10, 14, 0),
                        "Clementi", "Dental Appointment", getParticipantList(7)),
                new Meeting(LocalDateTime.of(now.getYear() + 1, 4, 15, 14, 0),
                        "COM1-02-10", "Project Meeting", getParticipantList(4, 5)),
                new Meeting(LocalDateTime.of(now.getYear() + 1, 4, 16, 19, 30),
                        "COM1-02-01", "Project Presentation", getParticipantList(4, 5)),
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

}
