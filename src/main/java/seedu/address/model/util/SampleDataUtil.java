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
import seedu.address.model.person.avatar.Avatar;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        try {
            return new Person[] {
                new Person(new Name("Alex Zheng"), new Phone("87438807"), new Email("alexzheng@example.com"),
                        new Address("Blk 30 Geylang Street 29, #06-40"), new Birthday("01/01/1991"),
                        new Avatar(), getTagSet("NewClient", "Wedding")),
                new Person(new Name("Boyce Yang"), new Phone("96648282"), new Email("boycey@example.com"),
                        new Address("13 Computing Drive"), new Birthday("02/05/1995"),
                        new Avatar(), getTagSet("NUSComputingClub")),
                new Person(new Name("Charlie Lim"), new Phone("98761234"), new Email("clim@example.com"),
                        new Address("462 Crawford Lane"), new Birthday("01/02/1990"),
                        new Avatar(), getTagSet("Musician", "LoyalClient")),
                new Person(new Name("Darshini R"), new Phone("91231231"), new Email("darshr@example.com"),
                        new Address("7 Kim Chuan Terrace"), new Birthday("06/06/1966"),
                        new Avatar(), getTagSet("NewClient", "Wedding")),
                new Person(new Name("Dean James"), new Phone("92838182"), new Email("dj@example.com"),
                        new Address("43 Sixth Ave"), new Birthday("01/02/1990"),
                        new Avatar(), getTagSet("Photographer", "Work")),
                new Person(new Name("Jacky Mao"), new Phone("91231337"), new Email("jlmao@example.com"),
                        new Address("1469 Beaver Creek"), new Birthday("05/05/1995"),
                        new Avatar(), getTagSet("WebsiteDeveloper", "Work", "Overseas")),
                new Person(new Name("Kally Loh"), new Phone("91234567"), new Email("kloh93@example.com"),
                        new Address("95 Desker Road"), new Birthday("12/12/1993"),
                        new Avatar(), getTagSet("NUSStudentUnion", "LoyalClient")),
                new Person(new Name("Abcde Qhi"), new Phone("92831111"), new Email("qigang@example.com"),
                        new Address("50 Chin Swee Road #09-04"), new Birthday("25/12/1967"),
                        new Avatar(), getTagSet("SegarCommunityClub", "NewClient")),
                new Person(new Name("Ang Jing Zhe"), new Phone("91231231"), new Email("ajc123@example.com"),
                        new Address("101 Thomson Road"), new Birthday("14/10/1987"),
                        new Avatar(), getTagSet("NewClient")),
                new Person(new Name("Hu Buay Song"), new Phone("91231231"), new Email("ajc123@example.com"),
                        new Address("123 Kappa Drive #06-66"), new Birthday("06/06/1966"),
                        new Avatar(), getTagSet("NewClient", "Wedding")),
                new Person(new Name("Irsan Khuang"), new Phone("88822773"), new Email("ikendo@example.com"),
                        new Address("2 Lady Musgrave Ave"), new Birthday("02/04/1993"),
                        new Avatar(), getTagSet("LoyalClient")),
                new Person(new Name("Albert Ng"), new Phone("89012345"), new Email("indogod@example.com"),
                        new Address("7 Warrenton Road"), new Birthday("05/05/1997"),
                        new Avatar(), getTagSet("LoyalClient")),
                new Person(new Name("Fong Wei Zheng"), new Phone("89991234"), new Email("schifix@example.com"),
                        new Address("20 Cecil Street #16-04"), new Birthday("15/08/1995"),
                        new Avatar(), getTagSet("NUSComputingClub")),
                new Person(new Name("Umairah S"), new Phone("82737123"), new Email("archiumi@example.com"),
                        new Address("10 Toh Guan Road #04-09"), new Birthday("19/02/1996"),
                        new Avatar(), getTagSet("LoyalClient", "WorkComplete")),
                new Person(new Name("Victoria Lim"), new Phone("82557190"), new Email("vickyly@example.com"),
                        new Address("42 Pasir Panjang"), new Birthday("28/07/1997"),
                        new Avatar(), getTagSet("WorkComplete")),
                new Person(new Name("Tracy Shen"), new Phone("92736123"), new Email("tshenk@example.com"),
                        new Address("6 Boon Lay Drive, Summerdale, #02-09"), new Birthday("28/01/1997"),
                        new Avatar(), getTagSet("NewClient", "WorkPending")),
                new Person(new Name("Deng Yi Min"), new Phone("92221423"), new Email("dxingxing@example.com"),
                        new Address("6 Kent Ridge Road, #06-21"), new Birthday("20/09/1997"),
                        new Avatar(), getTagSet("NewClient", "NUS", "WorkPending")),
                new Person(new Name("Kong Ying Xuan"), new Phone("81212355"), new Email("kangkong@example.com"),
                        new Address("88 Jurong Road, #14-14"), new Birthday("01/04/1997"),
                        new Avatar(), getTagSet("WorkPending")),
                new Person(new Name("Kevin Leonardo"), new Phone("95558666"), new Email("kla@example.com"),
                        new Address("36 College Ave E, North Tower, #22-22"), new Birthday("22/03/1997"),
                        new Avatar(), getTagSet("NUS", "WorkComplete")),
                new Person(new Name("Nadhira Salsabila"), new Phone("92228321"), new Email("nadhira@example.com"),
                        new Address("37 College Ave E, South Tower, #11-11"), new Birthday("11/05/1999"),
                        new Avatar(), getTagSet("NUS", "WorkComplete"))
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
