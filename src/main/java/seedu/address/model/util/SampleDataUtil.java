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
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        try {
            return new Person[] {
                new Person(new Name("LOreal"), new Phone("87438807"), new Email("sales@loreal.com"),
                        new Address("You're worth it Street 29, #06-40"), new Favourite(), new Birthday("19/10/10"),
                        getTagSet("friends")),
                new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                        new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Favourite(true),
                        new Birthday(), getTagSet("colleagues", "friends")),
                new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charl@example.com"),
                        new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Favourite(),
                        new Birthday("19/10/10"), getTagSet("neighbours")),
                new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                        new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Favourite(true),
                        new Birthday(), getTagSet("family")),
                new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                        new Address("Blk 47 Tampines Street 20, #17-35"), new Favourite(true),
                        new Birthday("17/03/98"), getTagSet("classmates")),
                new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                        new Address("Blk 45 Aljunied Street 85, #11-31"), new Favourite(), new Birthday("11/06/96"),
                        getTagSet("colleagues")),
                new Person(new Name("Graham Bill"), new Phone("84358807"), new Email("grahambill@example.com"),
                        new Address("Blk 30 Geylang Street 29, #06-40"), new Favourite(), new Birthday("19/10/10"),
                        getTagSet("friends")),
                new Person(new Name("Kathy Forest"), new Phone("98438201"), new Email("kathy@example.com"),
                        new Address("Blk 30 Geylang Street 29, #06-40"), new Favourite(), new Birthday("19/10/10"),
                        getTagSet("friends")),
                new Person(new Name("Bern Lee"), new Phone("99272356"), new Email("bern@example.com"),
                        new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Favourite(true),
                        new Birthday(), getTagSet("colleagues", "friends")),
                new Person(new Name("Lorette Baker"), new Phone("93212231"), new Email("lorette@example.com"),
                        new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Favourite(),
                        new Birthday("19/10/10"), getTagSet("neighbours")),
                new Person(new Name("Bart Simpson"), new Phone("98031423"), new Email("barter@example.com"),
                        new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Favourite(true),
                        new Birthday(), getTagSet("family")),
                new Person(new Name("Theresa Parker"), new Phone("93422011"), new Email("theresa@example.com"),
                        new Address("Blk 47 Tampines Street 20, #17-35"), new Favourite(true),
                        new Birthday("17/03/98"), getTagSet("classmates")),
                new Person(new Name("Blake Powers"), new Phone("98726457"), new Email("blakepowers@example.com"),
                        new Address("Blk 45 Aljunied Street 85, #11-31"), new Favourite(), new Birthday("11/06/96"),
                        getTagSet("colleagues")),
                new Person(new Name("Tommy Wiseau"), new Phone("81435867"), new Email("tomm@example.com"),
                        new Address("Blk 30 Geylang Street 29, #06-40"), new Favourite(), new Birthday("19/10/10"),
                        getTagSet("friends")),
                new Person(new Name("Greg Sestero"), new Phone("87132897"), new Email("gregs@example.com"),
                        new Address("Blk 30 Geylang Street 29, #06-40"), new Favourite(), new Birthday("19/10/10"),
                        getTagSet("friends")),
                new Person(new Name("Lisa Holland"), new Phone("93312458"), new Email("lishol@example.com"),
                        new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Favourite(true),
                        new Birthday(), getTagSet("colleagues", "friends")),
                new Person(new Name("Courtney Prince"), new Phone("93415783"), new Email("courtprince@example.com"),
                        new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Favourite(),
                        new Birthday("19/10/10"), getTagSet("neighbours")),
                new Person(new Name("Jake Berret"), new Phone("85432281"), new Email("jake@example.com"),
                        new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Favourite(true),
                        new Birthday(), getTagSet("family")),
                new Person(new Name("Wallace"), new Phone("82193021"), new Email("wallace@example.com"),
                        new Address("Blk 47 Tampines Street 20, #17-35"), new Favourite(true),
                        new Birthday("17/03/98"), getTagSet("classmates")),
                new Person(new Name("Emanuel Samantha"), new Phone("82728217"), new Email("emsam@example.com"),
                        new Address("Blk 45 Aljunied Street 85, #11-31"), new Favourite(), new Birthday("11/06/96"),
                        getTagSet("colleagues"))


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
