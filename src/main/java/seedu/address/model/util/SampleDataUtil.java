package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Favourite;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.PostalCode;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        try {
            return new Person[] {
                new Person(new Name("Abie Tan"), new Phone("87430055"), new Birthday("13-mar-95"),
                        new Email("abietan@example.com"), new Address("Blk 302 Choa Chu Kang Street 4, #01-20"),
                        new PostalCode("290370"), new Favourite("yes"), getTagSet("pretty")),
                new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Birthday("03-dec-98"),
                        new Email("alexyeoh@example.com"), new Address("Blk 30 Geylang Street 29, #06-40"),
                        new PostalCode("224375"), new Favourite("yes"), getTagSet("friends")),
                new Person(new Name("Justice Pang"), new Phone("91250125"), new Birthday("10.sep.50"),
                        new Email("justicepang@example.com"), new Address("Blk 10 Clementi Sreet 20, #05-10"),
                        new PostalCode("495900"), new Favourite("yes"), getTagSet("oldman")),
                new Person(new Name("Mary Van"), new Phone("92625420"), new Birthday("4 mar 95"),
                        new Email("maryvan@example.com"), new Address("Blk 45 Bukit Gombak Avenue 3, #10-29"),
                        new PostalCode("550228"), new Favourite("yes"), getTagSet("needy")),
                new Person(new Name("Roy Lim"), new Phone("92624417"), new Birthday("28 apr 94"),
                        new Email("royb@example.com"), new Address("Blk 21 Aljunied Street 1, #31-30"),
                        new PostalCode("300295"), new Favourite("yes"), getTagSet("loanshark")),
                new Person(new Name("Abigail Siew"), new Phone("80125501"), new Birthday("04 dec 80"),
                        new Email("abigialsiew@example.com"), new Address("Blk 200 Sembawang Avenue 2, #02-34"),
                        new PostalCode("799201"), new Favourite("no"), getTagSet("auntie")),
                new Person(new Name("Ali Bin Laden"), new Phone("92224500"), new Birthday("19.apr.1990"),
                        new Email("binladen@example.com"), new Address("Sunshine Tampines Street 20, #01-35"),
                        new PostalCode("125320"), new Favourite("no"), getTagSet("classmates")),
                new Person(new Name("Ali Mahattan"), new Phone("82124550"), new Birthday("19.apr.1976"),
                        new Email("alimahattan@example.com"), new Address("Marsiling Street 25, #01-01"),
                        new PostalCode("203540"), new Favourite("no"), getTagSet("classmates")),
                new Person(new Name("Babie Fan"), new Phone("97430155"), new Birthday("13-mar-60"),
                        new Email("babiefan@example.com"), new Address("Blk 302 Yew Tee Street 40, #21-20"),
                        new PostalCode("790472"), new Favourite("no"), getTagSet("oldlady")),
                new Person(new Name("Bernice Yu"), new Phone("99272758"), new Birthday("04 jun 1974"),
                        new Email("berniceyu@example.com"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                        new PostalCode("540986"), new Favourite("no"), getTagSet("colleagues", "friends")),
                new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Birthday("18.sep.88"),
                        new Email("charlotte@example.com"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                        new PostalCode("192450"), new Favourite("no"), getTagSet("neighbours")),
                new Person(new Name("David Li"), new Phone("91031282"), new Birthday("08 OCT 93"),
                        new Email("lidavid@example.com"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                        new PostalCode("800000"), new Favourite("no"), getTagSet("family")),
                new Person(new Name("David Pan"), new Phone("90124509"), new Birthday("08 feb 99"),
                        new Email("davidpan@example.com"), new Address("Blk 209 Kent Ridge Street 26, #06-40"),
                        new PostalCode("800000"), new Favourite("no"), getTagSet("family")),
                new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Birthday("19.jan.1984"),
                        new Email("irfan@example.com"), new Address("Blk 47 Tampines Street 20, #17-35"),
                        new PostalCode("125320"), new Favourite("no"), getTagSet("classmates")),
                new Person(new Name("John Lee"), new Phone("96225233"), new Birthday("05 jan 1985"),
                        new Email("johnlee@example.com"), new Address("Blk 200 Bugis Street, #05-10"),
                        new PostalCode("040150"), new Favourite("no"), getTagSet("rich", "attached")),
                new Person(new Name("Ong Wei Kang"), new Phone("82625419"), new Birthday("1 mar 95"),
                        new Email("royb@example.com"), new Address("Blk 45 Woodlands Street 5, #11-31"),
                        new PostalCode("650298"), new Favourite("no"), getTagSet("colleagues")),
                new Person(new Name("Peter Bin Muhammad"), new Phone("82550922"), new Birthday("1.jan.1974"),
                        new Email("peter@example.com"), new Address("Blk 270 Jurong West Street 2, #17-35"),
                        new PostalCode("225330"), new Favourite("no"), getTagSet("strangers")),
                new Person(new Name("Poon Wenzhe"), new Phone("81250025"), new Birthday("15.oct.95"),
                        new Email("pwenzhe@example.com"), new Address("Blk 11 Bishan Sreet 23, #15-09"),
                        new PostalCode("192900"), new Favourite("no"), getTagSet("student")),
                new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Birthday("28 apr 94"),
                        new Email("royb@example.com"), new Address("Blk 45 Aljunied Street 85, #11-31"),
                        new PostalCode("100295"), new Favourite("no"), getTagSet("colleagues")),
                new Person(new Name("Valerie Yue"), new Phone("99271235"), new Birthday("05 jan 1990"),
                        new Email("valerieyue@example.com"), new Address("Blk 20 Lorong 3 Pasir Ris Gardens, #09-10"),
                        new PostalCode("540156"), new Favourite("no"), getTagSet("friendly"))
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
