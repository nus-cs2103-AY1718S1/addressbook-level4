package seedu.address.model.util;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Avatar;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.NokName;
import seedu.address.model.person.NokPhone;
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
                new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                        new Address("Blk 30 Geylang Street 29, #06-40"), new Avatar(""), new NokName("Peter Yeoh"),
                        new NokPhone("91516545"), getTagSet("Year1")),
                new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                        new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Avatar(""),
                        new NokName("Denise Yu"), new NokPhone("94894182"), getTagSet("Year4", "Tutor")),
                new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                        new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Avatar(""),
                        new NokName("Charlie Oliveiro"), new NokPhone("81454165"), getTagSet("Professor")),
                new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                        new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Avatar(""),
                        new NokName("Elliot Li"), new NokPhone("94156418"), getTagSet("Employee")),
                new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                        new Address("Blk 47 Tampines Street 20, #17-35"), new Avatar(""),
                        new NokName("Juliet Ibrahim"), new NokPhone("91854185"), getTagSet("Year2")),
                new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                        new Address("Blk 45 Aljunied Street 85, #11-31"), new Avatar(""),
                        new NokName("Sakthiar Balakrishnan"), new NokPhone("85648965"), getTagSet("Professor")),
                new Person(new Name("Mary Lim"), new Phone("97222313"), new Email("maryyyy1998@gmail.com"),
                        new Address("Blk 868 Choa Chu Kang Crescent, #08-212"), new Avatar(""),
                        new NokName("John Lim"), new NokPhone("96335671"), getTagSet("Year2", "Tutor")),
                new Person(new Name("Jerene Soh"), new Phone("87169110"), new Email("jerjerjer123321@hotmail.com"),
                        new Address("31 Sixth Avenue"), new Avatar(""),
                        new NokName("Melissa Soh"), new NokPhone("81113121"), getTagSet("Employee")),
                new Person(new Name("Chris Ang"), new Phone("97145123"), new Email("chris@example.com"),
                        new Address("Blk 657 Red Hill Avenue 2, #09-12"), new Avatar(""),
                        new NokName("Cherie Ang"), new NokPhone("972878212"), getTagSet("Year2")),
                new Person(new Name("Romeo Ong"), new Phone("81123112"), new Email("romeo@example.com"),
                        new Address("5 Sentosa Cove"), new Avatar(""),
                        new NokName("Sam Ong"), new NokPhone("97123112"), getTagSet("Employee")),
                new Person(new Name("Sally Tan"), new Phone("97231123"), new Email("sally@example.com"),
                        new Address("31 West Coast Road, #12-01"), new Avatar(""),
                        new NokName("Alan Tan"), new NokPhone("98172312"), getTagSet("Year1")),
                new Person(new Name("Michael Low"), new Phone("81213712"), new Email("lowlowlow@example.com"),
                        new Address("Blk 751 Bukit Batok Avenue 3, #07-22"), new Avatar(""),
                        new NokName("Jason Low"), new NokPhone("81231221"), getTagSet("Employee")),
                new Person(new Name("Alexander Lee"), new Phone("91112301"), new Email("alexlee@example.com"),
                        new Address("Blk 681 Clementi Avenue 1, #11-08"), new Avatar(""),
                        new NokName("Lee Kok Leong"), new NokPhone("81717231"), getTagSet("Professor")),
                new Person(new Name("Leon Tan"), new Phone("98671230"), new Email("leon@example.com"),
                        new Address("Blk 889 Jurong East Street 32, #04-08"), new Avatar(""),
                        new NokName("Tan Ah Kow"), new NokPhone("87896711"), getTagSet("Year2")),
                new Person(new Name("Anna Ong"), new Phone("98889754"), new Email("anna@example.com"),
                        new Address("4 Pasir Panjang Road, #03-04"), new Avatar(""),
                        new NokName("Ong Jia Hui"), new NokPhone("98412561"), getTagSet("Year3")),
                new Person(new Name("Bruce Neo"), new Phone("97771234"), new Email("bruce@example.com"),
                        new Address("Blk 751 Alexandra Road, #02-16"), new Avatar(""),
                        new NokName("Brenda Neo"), new NokPhone("86151668"), getTagSet("Year1")),
                new Person(new Name("Renee Sim"), new Phone("98161231"), new Email("renee@example.com"),
                        new Address("Blk 124 Petir Road, #08-13"), new Avatar(""),
                        new NokName("Rena Sim"), new NokPhone("92828722"), getTagSet("Year3")),
                new Person(new Name("Mason Montana"), new Phone("97211312"), new Email("mmmmmm12131@example.com"),
                        new Address("3 Tanglin Road"), new Avatar(""),
                        new NokName("Marilyn Montana"), new NokPhone("91827341"), getTagSet("Year4", "Tutor")),
                new Person(new Name("Ken Yeo"), new Phone("97819231"), new Email("yeolo@example.com"),
                        new Address("Blk 415 Tampines street 31, #15-12"), new Avatar(""),
                        new NokName("Yeo Zong Han"), new NokPhone("81817171"), getTagSet("Year2")),
                new Person(new Name("Pauline Aoki"), new Phone("85127121"), new Email("pauline@example.com"),
                        new Address("111 Emerald Hill"), new Avatar(""),
                        new NokName("Peter Aoki"), new NokPhone("97615123"), getTagSet("Year4"))
            };
        } catch (IllegalValueException | IOException e) {
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
