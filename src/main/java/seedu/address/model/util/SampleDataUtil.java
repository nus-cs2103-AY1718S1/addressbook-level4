package seedu.address.model.util;

import static seedu.address.model.util.DateUtil.formatDate;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.DateRepaid;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.Debt;
import seedu.address.model.person.Email;
import seedu.address.model.person.Interest;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.PostalCode;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        try {
            return new Person[] {
                new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"), new PostalCode("418362"),
                    new Debt("10000"), new Interest(Interest.NO_INTEREST_SET),
                    new Deadline(Deadline.NO_DEADLINE_SET), getTagSet("violent")),
                new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new PostalCode("554403"),
                    new Debt("100"), new Interest("1"),
                    new Deadline("02-02-2020"), getTagSet("unfriendly", "tricky")),
                new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new PostalCode("560011"),
                    new Debt("300"), new Interest(Interest.NO_INTEREST_SET),
                    new Deadline(Deadline.NO_DEADLINE_SET), getTagSet("tricky")),
                new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new PostalCode("554436"),
                    new Debt("50"), new Interest(Interest.NO_INTEREST_SET),
                    new Deadline(Deadline.NO_DEADLINE_SET), getTagSet("unfriendly")),
                new Person(new Name("Eileen Choo"), new Phone("91373723"), new Email("elchoo@example.com"),
                    new Address("Blk 432 Tiong Bahru Avenue 2, #16-43"), new PostalCode("144432"),
                    new Debt("2000"), new Interest("1"),
                    new Deadline("25-12-2050"), getTagSet("cooperative")),
                new Person(new Name("Farhan Mohammed"), new Phone("82837273"), new Email("fhmm@example.com"),
                    new Address("Blk 22 Queenstown Street 25, #16-43"), new PostalCode("164422"),
                    new Debt("2000"), new Interest("4"),
                    new Deadline("01-01-2040"), getTagSet("violent")),
                new Person(new Name("Gisela Tan"), new Phone("87727737"), new Email("ggwantan@example.com"),
                    new Address("Blk 23 Queenstown Street 25, #12-37"), new PostalCode("164423"),
                    new Debt("0"), new Interest(Interest.NO_INTEREST_SET),
                    new Deadline(Deadline.NO_DEADLINE_SET), getTagSet("cooperative", "friendly")),
                new Person(new Name("Herbert He"), new Phone("90093007"), new Email("hehehe@example.com"),
                    new Address("Blk 3 HillView Avenue, #16-43"), new PostalCode("674433"),
                    new Debt("2000"), new Interest("4"),
                    new Deadline("31-01-2030"), getTagSet("violent")),
                new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"), new PostalCode("515047"),
                    new Debt("90000"), new Interest("3"),
                    new Deadline("15-03-2050"), getTagSet("unfriendly")),
                new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), new PostalCode("389045"),
                    new Debt("15630"), new Interest(Interest.NO_INTEREST_SET),
                    new Deadline(Deadline.NO_DEADLINE_SET), getTagSet("friendly"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAb = new AddressBook();
            Person[] samplePersons = getSamplePersons();
            samplePersons[5].setIsBlacklisted(true);
            samplePersons[7].setIsBlacklisted(true);
            samplePersons[6].setIsWhitelisted(true);
            samplePersons[6].setDateRepaid(new DateRepaid(formatDate(new Date())));

            for (Person samplePerson : samplePersons) {
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
