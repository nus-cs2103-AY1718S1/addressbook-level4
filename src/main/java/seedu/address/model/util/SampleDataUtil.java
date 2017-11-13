package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        try {
            return new Person[] {
                new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Birthday("20/10/1991"),
                        new Email("alexyeoh@example.com"), new Address("Blk 30 Geylang Street 29, #06-40"),
                    new Remark(""), getTagSet("friends")),
                new Person(new Name("Bernice Yu"), new Phone("99272758"), new Birthday("20/10/1991"),
                        new Email("berniceyu@example.com"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    new Remark(""), getTagSet("colleagues", "friends")),
                new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Birthday("20/10/1991"),
                        new Email("charlotte@example.com"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    new Remark(""), getTagSet("neighbours")),
                new Person(new Name("David Li"), new Phone("91031282"), new Birthday("20/10/1991"),
                        new Email("lidavid@example.com"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    new Remark(""), getTagSet("family")),
                new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Birthday("20/10/1991"),
                        new Email("irfan@example.com"), new Address("Blk 47 Tampines Street 20, #17-35"),
                    new Remark(""), getTagSet("classmates")),
                new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Birthday("20/10/1991"),
                        new Email("royb@example.com"), new Address("Blk 45 Aljunied Street 85, #11-31"),
                    new Remark(""), getTagSet("colleagues")), //@@author aver0214
                new Person(new Name("Zed Toh"), new Phone("82315756"), new Birthday("20/10/1991"),
                        new Email("zedtoh@example.com"), new Address("Blk 30 Geylang Street 29, #06-40"),
                    new Remark(""), getTagSet("friends")),
                new Person(new Name("Elaine Lim"), new Phone("99272328"), new Birthday("20/10/1991"),
                        new Email("elainelim@example.com"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    new Remark(""), getTagSet("colleagues", "friends")),
                new Person(new Name("Lebron James"), new Phone("93450283"), new Birthday("20/10/1991"),
                        new Email("lebronjames@example.com"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    new Remark(""), getTagSet("neighbours")),
                new Person(new Name("Stephen Curry"), new Phone("95871282"), new Birthday("20/10/1991"),
                        new Email("curry@example.com"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    new Remark(""), getTagSet("family")),
                new Person(new Name("Tony Parker"), new Phone("9237841"), new Birthday("20/10/1991"),
                        new Email("tonyparker@example.com"), new Address("Blk 47 Tampines Street 20, #17-35"),
                    new Remark(""), getTagSet("classmates")),
                new Person(new Name("Tim Duncan"), new Phone("92623437"), new Birthday("20/10/1991"),
                        new Email("timduncan@example.com"), new Address("Blk 45 Aljunied Street 85, #11-31"),
                    new Remark(""), getTagSet("colleagues")),
                new Person(new Name("James Harden"), new Phone("87132807"), new Birthday("20/10/1991"),
                        new Email("jamesharden@example.com"), new Address("Blk 30 Geylang Street 29, #06-40"),
                    new Remark(""), getTagSet("friends")),
                new Person(new Name("Dwayne Wade"), new Phone("98998858"), new Birthday("20/10/1991"),
                        new Email("dwaynewade@example.com"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    new Remark(""), getTagSet("colleagues", "friends")),
                new Person(new Name("Russell Westbrook"), new Phone("93210283"), new Birthday("20/10/1991"),
                        new Email("russell@example.com"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    new Remark(""), getTagSet("neighbours")),
                new Person(new Name("Klay Thompson"), new Phone("91031282"), new Birthday("20/10/1991"),
                        new Email("klaythom@example.com"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    new Remark(""), getTagSet("family")),
                new Person(new Name("Kyrie Irving"), new Phone("92492021"), new Birthday("20/10/1991"),
                        new Email("irving@example.com"), new Address("Blk 47 Tampines Street 20, #17-35"),
                    new Remark(""), getTagSet("classmates")),
                new Person(new Name("Manu Gino"), new Phone("92678417"), new Birthday("20/10/1991"),
                        new Email("manu@example.com"), new Address("Blk 45 Aljunied Street 85, #11-31"),
                    new Remark(""), getTagSet("colleagues")),
                new Person(new Name("Derrick Rose"), new Phone("93452021"), new Birthday("20/10/1991"),
                        new Email("rose@example.com"), new Address("Blk 47 Tampines Street 20, #17-35"),
                    new Remark(""), getTagSet("classmates")),
                new Person(new Name("Kawhi Leonard"), new Phone("92612317"), new Birthday("20/10/1991"),
                        new Email("leonard@example.com"), new Address("Blk 45 Aljunied Street 85, #11-31"),
                    new Remark(""), getTagSet("colleagues"))//@@author
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

}
