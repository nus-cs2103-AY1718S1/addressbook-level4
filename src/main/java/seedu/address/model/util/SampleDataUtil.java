package seedu.address.model.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.FavouriteStatus;
import seedu.address.model.person.Link;
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
                new Person(new Name("Ahmad Johnson"), new Phone("82623417"), new Email("ahmadj@example.com"),
                    new Address("Blk 555 Hougang Street 31, #04-48"), getRemarkArrayList(),
                        new FavouriteStatus(false), getTagSet("classmates"), new Link("")),
                new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"), getRemarkArrayList(),
                        new FavouriteStatus(false), getTagSet("friends"), new Link("")),
                new Person(new Name("Ashley Ashley"), new Phone("82623417"), new Email("ashleyashley@example.com"),
                    new Address("Blk 771 Kent Ridge Street 90, #03-21"), getRemarkArrayList(),
                        new FavouriteStatus(false), getTagSet("classmates"), new Link("")),
                new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                        getRemarkArrayList("Discuss about project direction"),
                        new FavouriteStatus(false), getTagSet("colleagues", "friends"),
                        new Link("")),
                new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), getRemarkArrayList(),
                        new FavouriteStatus(false), getTagSet("neighbours"), new Link("")),
                new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                        getRemarkArrayList("Finish up the introduction for presentation"),
                        new FavouriteStatus(false), getTagSet("family"),
                        new Link("twitter.com/_david_li_")),
                new Person(new Name("Erfan Ibrahim"), new Phone("92492021"), new Email("eirfan@nus.edu.sg"),
                    new Address("Blk 47 Tampines Street 20, #17-35"), getRemarkArrayList(),
                        new FavouriteStatus(false), getTagSet("classmates"), new Link("")),
                new Person(new Name("Foy Balakrishnan"), new Phone("92624417"), new Email("foyb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), getRemarkArrayList(),
                        new FavouriteStatus(false), getTagSet("colleagues"), new Link("")),
                new Person(new Name("Games Willian"), new Phone("82623417"), new Email("gamesW@example.com"),
                        new Address("Blk 38 Aljunied Street 84, #10-28"), getRemarkArrayList(),
                        new FavouriteStatus(false), getTagSet("classmates"), new Link("")),
                new Person(new Name("Hojack Wee"), new Phone("82623417"), new Email("Hojack@example.com"),
                        new Address("Blk 382 Hougang Street 84, #07-28"), getRemarkArrayList(),
                        new FavouriteStatus(false), getTagSet("classmates"), new Link("")),
                new Person(new Name("Jack Power"), new Phone("82623417"), new Email("jackPower@example.com"),
                        new Address("Blk 122 Kent Ridge Street 12, #02-28"), getRemarkArrayList(),
                        new FavouriteStatus(false), getTagSet("classmates"), new Link("")),
                new Person(new Name("Koung Ashley"), new Phone("82233417"), new Email("kashleyy@example.com"),
                        new Address("Blk 222 Kent Ridge Street 32, #03-19"), getRemarkArrayList(),
                        new FavouriteStatus(false), getTagSet("classmates"), new Link("")),
                new Person(new Name("Lacob Yasim"), new Phone("82664417"), new Email("LacobYas@nus.edu.sg"),
                        new Address("Blk 512 Sengkang Street 81, #03-19"), getRemarkArrayList(),
                        new FavouriteStatus(false), getTagSet("classmates"), new Link("")),
                new Person(new Name("Mua Wei Zong"), new Phone("87431107"), new Email("muaweizh@example.com"),
                        new Address("Blk 8 Geylang Street 99, #02-40"), getRemarkArrayList(),
                        new FavouriteStatus(false), getTagSet("friends"), new Link("")),
                new Person(new Name("Orence Yu"), new Phone("99271513"), new Email("orenceyu@example.com"),
                        new Address("Blk 18 Lorong 4 Serangoon Gardens, #03-18"), getRemarkArrayList(),
                        new FavouriteStatus(false), getTagSet("colleagues", "friends"),
                        new Link("")),
                new Person(new Name("Pharlotte Manny"), new Phone("88810283"), new Email("pharlotteman@example.com"),
                        new Address("Blk 78 Ang Mo Kio Street 21, #09-31"), getRemarkArrayList(),
                        new FavouriteStatus(false), getTagSet("neighbours"), new Link("")),
                new Person(new Name("Qavid Lee"), new Phone("81241423"), new Email("leeqavid@example.com"),
                        new Address("Blk 551 Serangoon Gardens Street 71, #14-12"), getRemarkArrayList(),
                        new FavouriteStatus(false), getTagSet("family"),
                        new Link("")),
                new Person(new Name("Qur Irfan"), new Phone("90002313"), new Email("qurirfan@example.com"),
                        new Address("Blk 37 Tampines Street 19, #16-32"), getRemarkArrayList(),
                        new FavouriteStatus(false), getTagSet("classmates"), new Link("")),
                new Person(new Name("Roystan tan"), new Phone("88231423"), new Email("royb@example.com"),
                        new Address("Blk 45 Aljunied Street 85, #11-31"), getRemarkArrayList(),
                        new FavouriteStatus(false), getTagSet("colleagues", "enemy"), new Link("")),
                new Person(new Name("Sheng Yi"), new Phone("82623007"), new Email("shengyi@example.com"),
                        new Address("Blk 18 Aljunied Street 84, #02-28"), getRemarkArrayList(),
                        new FavouriteStatus(false), getTagSet("classmates"), new Link("")),
                new Person(new Name("Tim Jan Lee"), new Phone("82623417"), new Email("timleejan@example.com"),
                        new Address("Blk 90 Kent Ridge Street 89, #07-28"), getRemarkArrayList(),
                        new FavouriteStatus(false), getTagSet("classmates"), new Link("")),
                new Person(new Name("Zachary Tan"), new Phone("82623417"), new Email("jacobYas@example.com"),
                        new Address("Blk 333 Sengkang Street 81, #02-22"), getRemarkArrayList(),
                        new FavouriteStatus(false), getTagSet("classmates"), new Link("")),
                new Person(new Name("Zack Jojo"), new Phone("82623417"), new Email("zackjojo@nus.edu.sg"),
                        new Address("Blk 213 Sengkang Street 42, #12-82"), getRemarkArrayList(),
                        new FavouriteStatus(false), getTagSet("classmates"), new Link(""))
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
     * Returns a remarks arraylist containing the list of strings given.
     */
    public static ArrayList<Remark> getRemarkArrayList(String... strings) throws IllegalValueException {
        ArrayList<Remark> remarks = new ArrayList<>();
        for (String s : strings) {
            remarks.add(new Remark(s));
        }

        return remarks;
    }
}
