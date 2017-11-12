package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.customField.CustomField;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Photo;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.phone.Phone;
import seedu.address.model.person.phone.UniquePhoneList;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        try {
            return new Person[] {
                new Person(new Name("Alex Yeoh"), new Phone("87438807"),
                    new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"),
                    new Photo("../addressbook4/docs/images/wolf.jpg"),
                    new UniquePhoneList(new Phone("2333")),
                    getTagSet("friends"),
                    getCustomFieldSet("Nickname Ah_lex", "Birthday 11/02/1998")),
                new Person(new Name("Ameens"), new Phone("67740637"),
                            new Email("alamaan@ex.com"), new Address("12 Clementi Rd, 129742"),
                            getTagSet("restaurant")
                    ),
                new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    getTagSet("colleagues", "friends")),
                new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    getTagSet("neighbours")),
                new Person(new Name("Chuan Garden"), new Phone("65320356"), new Email("Chuan@this.that"),
                            new Address("42 Mosque St, Singapore 059520"),
                            getTagSet("restaurant")
                    ),
                new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    getTagSet("family")),
                new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"),
                    getTagSet("classmates")),
                new Person(
                            new Name("KFC"), new Phone("66591792"), new Email("kendeji@gmail.com"),
                            new Address("3155 COMMONWEALTH AVENUE WEST #B1-32/33 THE CLEMENTI MALL, 129588"),
                            getTagSet("restaurant")
                    ),
                new Person(
                            new Name("LiHo"), new Phone("62656903"), new Email("liHo@ex.com"),
                            new Address("Stall #08, National University of Singapore, "
                                    + "21 Lower Kent Ridge Road, Level 2 Yusof Ishak House, 119077"),
                            getTagSet("restaurant")
                    ),
                new Person(
                            new Name("Lu Lechuan"), new Phone("00000000"), new Email("ahchuang@nus.edu.sg"),
                            new Address("NUS"),
                            getTagSet("CS2103")
                    ),
                new Person(new Name("McDonalds"), new Phone("67771440"), new Email("mc@hotmail.com"),
                            new Address("10 Kent Ridge Crescent, Kent Ridge Campus,Faculty of Engineering Annexe,"
                                    + " National University of Singapore, 117587"), getTagSet("restaurant")
                    ),
                new Person(
                            new Name("Ng Zu Yao"), new Phone("22222222"), new Email("zuyao@nus.edu.sg"),
                            new Address("NUS"),
                            getTagSet("CS2103")
                    ),
                new Person(
                            new Name("Old Chang Kee"), new Phone("63032344"), new Email("ock@hotmail.com"),
                            new Address("10 Kent Ridge Cres, Singapore 119260"),
                            getTagSet("restaurant")
                    ),
                new Person(
                            new Name("Old Cheng Du"), new Phone("62226858"), new Email("Chengdudelicous@hotmail.com"),
                            new Address("80 Pagoda St, 059239"),
                            getTagSet("restaurant")
                    ),
                new Person(
                            new Name("Pastamania"), new Phone("67342329"), new Email("pasta@good.com"),
                            new Address("50 Jurong Gateway Rd, 04-26, Singapore 608549"),
                            getTagSet("restaurant")
                    ),
                new Person(
                            new Name("Pizza Hut"), new Phone("66843153"), new Email("ph@we.com"),
                            new Address("2 College Avenue West #01-04 University Town Edusports "
                                    + "National University of Si, 138607"), getTagSet("restaurant")
                    ),
                new Person(new Name("Roy Balakrishnannan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"),
                    getTagSet("colleagues")),
                new Person(
                            new Name("Sarpinos"), new Phone("66363636"), new Email("sarpino@gmail.com"),
                            new Address("31 Lower Kent Ridge Road, Yusof Ishak House, #01-04, 119078"),
                            getTagSet("restaurant")
                    ),
                new Person(
                            new Name("Xu Jun"), new Phone("33333333"), new Email("xujun@nus.edu.sg"),
                            new Address("NUS"),
                            getTagSet("CS2103")
                    ),
                new Person(
                            new Name("Xu Yiqing"), new Phone("11111111"), new Email("eeching@nus.edu.sg"),
                            new Address("NUS"),
                            getTagSet("CS2103")
                    ),
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
     * Returns a custom field set containing the list of strings given.
     */
    public static Set<CustomField> getCustomFieldSet(String... strings) throws IllegalValueException {
        HashSet<CustomField> customFields = new HashSet<>();
        for (String s : strings) {
            StringTokenizer st = new StringTokenizer(s);
            customFields.add(new CustomField(st.nextToken(), st.nextToken()));
        }

        return customFields;
    }

}
