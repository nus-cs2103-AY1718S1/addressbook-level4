package seedu.address.model.util;

import static seedu.address.model.person.ProfilePicture.DEFAULT_PICTURE;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.group.Group;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ProfilePicture;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static final String SAMPLE_PICTURE = "pic1.jpeg";

    public static Person[] getSamplePersons() {
        try {
            return new Person[] {
                new Person(new Name("Adam Zablocki"), new Phone("92624417"), new Email("adamz@example.com"),
                        new Address("Blk 45 Strawberry Street 85, #11-31"), new Appointment(""),
                        new ProfilePicture(DEFAULT_PICTURE),
                        getGroupSet("CS2103"), getTagSet()),
                new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"), new Appointment("01/01/3000 00:00 60"),
                        new ProfilePicture(DEFAULT_PICTURE),
                        getGroupSet("University", "Family"), getTagSet("askForHackathon")),
                new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Appointment(""),
                        new ProfilePicture(DEFAULT_PICTURE),
                        getGroupSet(), getTagSet()),
                new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Appointment(""),
                        new ProfilePicture(DEFAULT_PICTURE),
                        getGroupSet("Neigbours"), getTagSet()),
                new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Appointment(""),
                        new ProfilePicture(DEFAULT_PICTURE),
                        getGroupSet(), getTagSet()),
                new Person(new Name("Erfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"), new Appointment(""),
                        new ProfilePicture(DEFAULT_PICTURE),
                        getGroupSet(), getTagSet("hasMyPencil")),
                new Person(new Name("Goy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), new Appointment(""),
                        new ProfilePicture(DEFAULT_PICTURE),
                        getGroupSet("CS2103"), getTagSet("owesMoney")),
                new Person(new Name("Hen Spore"), new Phone("23123532"), new Email("ben@example.com"),
                        new Address("Blk 2, Mango Street 85, #11-31"), new Appointment(""),
                        new ProfilePicture(DEFAULT_PICTURE),
                        getGroupSet("CS2108"), getTagSet()),
                new Person(new Name("Lo Cheng"), new Phone("92492021"), new Email("wo@example.com"),
                        new Address("Blueberry Street 20, #11-01"), new Appointment(""),
                        new ProfilePicture(DEFAULT_PICTURE),
                        getGroupSet(), getTagSet("hasMyPencil")),
                new Person(new Name("Patrick Smith"), new Phone("23123532"), new Email("patrick@example.com"),
                        new Address("Blk 2, Mango Street 85, #11-31"), new Appointment(""),
                        new ProfilePicture("pic1.jpeg"),
                        getGroupSet("CS2108"), getTagSet()),
                new Person(new Name("Patrick Tywin"), new Phone("23512352"), new Email("ala@example.com"),
                        new Address("Mickiewicz Street 29, #06-40"), new Appointment("01/01/3000 00:00 60"),
                        new ProfilePicture(DEFAULT_PICTURE),
                        getGroupSet("University", "Family"), getTagSet("askForHackathon")),
                new Person(new Name("Peter Brudnicki"), new Phone("95272758"), new Email("brudnicki@example.com"),
                        new Address("New Gardens, #07-18"), new Appointment(""),
                        new ProfilePicture(DEFAULT_PICTURE),
                        getGroupSet(), getTagSet()),
                new Person(new Name("Ramila Maslowska"), new Phone("34212342"), new Email("kamila@example.com"),
                        new Address("Bogata Street 65, #11-04"), new Appointment(""),
                        new ProfilePicture(DEFAULT_PICTURE),
                        getGroupSet("School"), getTagSet()),
                new Person(new Name("San Karajas"), new Phone("91031282"), new Email("patrik@example.com"),
                        new Address("Daleka Street 2, #16-43"), new Appointment(""),
                        new ProfilePicture(DEFAULT_PICTURE),
                        getGroupSet(), getTagSet()),
                new Person(new Name("Stephen Boganski"), new Phone("92492021"), new Email("mari@example.com"),
                        new Address("Blk 47 Tampines Street 20, #17-35"), new Appointment(""),
                        new ProfilePicture(DEFAULT_PICTURE),
                        getGroupSet(), getTagSet("hasMyPencil")),
                new Person(new Name("Swat Males"), new Phone("92624417"), new Email("adramb@example.com"),
                        new Address("Blk 45 Aljunied Street 85, #11-31"), new Appointment(""),
                        new ProfilePicture(DEFAULT_PICTURE),
                        getGroupSet("CS2103"), getTagSet("owesMoney")),
                new Person(new Name("Tata Szalata"), new Phone("12343532"), new Email("agata@example.com"),
                        new Address("Blk 2, Mango Street 85, #11-31"), new Appointment(""),
                        new ProfilePicture("pic1.jpeg"),
                        getGroupSet("CS2108"), getTagSet()),
                new Person(new Name("Wamian Franczk"), new Phone("92492021"), new Email("francik@example.com"),
                        new Address("Zachodnia Street 20, #11-01"), new Appointment(""),
                        new ProfilePicture("pic1.jpeg"),
                        getGroupSet(), getTagSet("hasMyPencil")),
                new Person(new Name("Weter Zuck"), new Phone("92214417"), new Email("peter@example.com"),
                        new Address("Blk 45 Strawberry Street 85, #11-31"), new Appointment(""),
                        new ProfilePicture(DEFAULT_PICTURE),
                        getGroupSet("CS2103"), getTagSet()),
                new Person(new Name("Zen Muller"), new Phone("23123122"), new Email("stephen@example.com"),
                        new Address("Blk 2, German Street 85, #11-31"), new Appointment(""),
                        new ProfilePicture(DEFAULT_PICTURE),
                        getGroupSet("CS2108"), getTagSet())
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

    /**
     * Returns a group set containing the list of strings given.
     */
    public static Set<Group> getGroupSet(String... strings) throws IllegalValueException {
        HashSet<Group> groups = new HashSet<>();
        for (String s : strings) {
            groups.add(new Group(s));
        }

        return groups;
    }
}
