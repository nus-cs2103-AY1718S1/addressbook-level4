package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Avatar;
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
                new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"), new Remark(""), new Avatar(null),
                    getTagSet("friends")),
                new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Remark("Likes to swim"),
                    new Avatar(null), getTagSet("colleagues", "friends")),
                new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Remark(""), new Avatar(null),
                    getTagSet("neighbours")),
                new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Remark("As quick as a leopard."),
                    new Avatar(null), getTagSet("family")),
                new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"), new Remark(""), new Avatar(null),
                    getTagSet("classmates")),
                new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), new Remark(""), new Avatar(null),
                    getTagSet("colleagues")),
                new Person(new Name("Ahmad Mohamad"), new Phone("84516541"), new Email("ahmd@example.com"),
                    new Address("Blk 293 Ang Mo Kio Street 76, #05-24"), new Remark(""), new Avatar(null),
                    getTagSet("friends")),
                new Person(new Name("Lindsey To"), new Phone("95591920"), new Email("lindsey@example.com"),
                    new Address("Blk 10 Boon Lay Street 24, #15-32"), new Remark(""), new Avatar(null),
                    getTagSet("friends")),
                new Person(new Name("Gabriella Woo"), new Phone("62421515"), new Email("gab@example.com"),
                    new Address("Blk 23 Hougang Street 83, #09-40"), new Remark(""), new Avatar(null),
                    getTagSet("classmates")),
                new Person(new Name("Burairah Ismail"), new Phone("82882212"), new Email("burairah@example.com"),
                    new Address("Blk 43 Bukit Panjang Street 79, #05-31"), new Remark(""), new Avatar(null),
                    getTagSet("friends")),
                new Person(new Name("Sonny Chew"), new Phone("94718214"), new Email("sonny@example.com"),
                    new Address("9 Tai Seng Court, #14-02"), new Remark("With chance of meatballs."), new Avatar(null),
                    getTagSet("friends")),
                new Person(new Name("Hansel Ng"), new Phone("97845124"), new Email("hanselblack@hotmail.com"),
                    new Address("Blk 354 Kent Ridge View 74, #09-14"), new Remark(""), new Avatar(null),
                    getTagSet("friends")),
                new Person(new Name("Rodger Teoh"), new Phone("63422455"), new Email("rodgerthat@example.com"),
                    new Address("72 Cairnhill Park"), new Remark(""), new Avatar(null),
                    getTagSet("friends")),
                new Person(new Name("Ava Kok"), new Phone("98744789"), new Email("ava@example.com"),
                    new Address("55 Serangoon North Park"), new Remark(""), new Avatar(null),
                    getTagSet("friends")),
                new Person(new Name("Cedrick Chew"), new Phone("87785522"), new Email("chewbacca@example.com"),
                    new Address("Blk 434 Geylang Street 87, #12-08"), new Remark(""), new Avatar(null),
                    getTagSet("classmates")),
                new Person(new Name("Zack Yeo"), new Phone("95591920"), new Email("zack@example.com"),
                    new Address("9 Bartley Walk, #18-39"), new Remark(""), new Avatar(null),
                    getTagSet("friends")),
                new Person(new Name("Low Siew Li Althea"), new Phone("87655396"), new Email("Althea@example.com"),
                    new Address("67 Jalan Asas"), new Remark(""), new Avatar(null),
                    getTagSet("friends")),
                new Person(new Name("Teoh King Guat"), new Phone("84564521"), new Email("tkg@example.com"),
                    new Address("Blk 22 Tampines Street 73, #05-25"), new Remark(""), new Avatar(null),
                    getTagSet("friends")),
                new Person(new Name("Tiffany Guo"), new Phone("87456321"), new Email("tiffany@example.com"),
                    new Address("52 Innova Heights"), new Remark(""), new Avatar(null),
                    getTagSet("friends")),
                new Person(new Name("Umar Hazirah"), new Phone("91249845"), new Email("umar@example.com"),
                    new Address("Blk 15 Hougang Street 39, #13-15"), new Remark(""), new Avatar(null),
                    getTagSet("friends"))
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
