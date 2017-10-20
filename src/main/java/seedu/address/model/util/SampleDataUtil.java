package seedu.address.model.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.weblink.WebLink;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        try {
            return new Person[] {
                new Person(new Name("Alex Yeoh"), new Phone("87438807"), getEmailSet("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"), new Remark(""),
                    getTagSet("friends"), getWebLinkSet("https://www.facebook.com/Phua.Han.Siang")),
                new Person(new Name("Bernice Yu"), new Phone("99272758"), getEmailSet("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Remark("Likes to swim."),
                    getTagSet("colleagues", "friends"),
                    getWebLinkSet("https://www.facebook.com/Phua.Han.Siang")),
                new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), getEmailSet("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Remark(""),
                    getTagSet("neighbours"),
                    getWebLinkSet("https://www.facebook.com/Phua.Han.Siang")),
                new Person(new Name("David Li"), new Phone("91031282"), getEmailSet("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Remark("As quick as a leapord."),
                    getTagSet("family"), getWebLinkSet("https://www.facebook.com/Phua.Han.Siang")),
                new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), getEmailSet("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"), new Remark(""),
                    getTagSet("classmates"),
                    getWebLinkSet("https://www.facebook.com/Phua.Han.Siang")),
                new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), getEmailSet("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), new Remark(""),
                    getTagSet("colleagues"), getWebLinkSet("https://www.facebook.com/Phua.Han.Siang"))
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
     * @return a email set containing the list of strings given
     * @throws IllegalValueException
     * */
    public static ArrayList<Email> getEmailSet(String... emails) throws IllegalValueException {
        ArrayList<Email> emailList = new ArrayList();
        for (String e : emails) {
            emailList.add(new Email(e));
        }
        return emailList;
    }

    public static Set<WebLink> getWebLinkSet(String... strings) throws IllegalValueException {
        HashSet<WebLink> webLinks = new HashSet<>();
        for (String s : strings) {
            webLinks.add(new WebLink(s));
        }
        return webLinks;
    }
}
