package seedu.address.model.util;

import static seedu.address.ui.MainWindow.DEFAULT_DP;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.DisplayPic;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.UserName;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        try {
            return new Person[] {
                new Person(new Name("Donald Trump"), new Phone("12345678"), new Email("dtrump@example.com"),
                    new Address("1600 Pennsylvania Ave NW"), new Birthday("14/06/1946"),
                    new UserName("realDonaldTrump"), new UserName("realDonaldTrump"),
                    new DisplayPic(DEFAULT_DP), getTagSet("republican")),
                new Person(new Name("Ed Sheeran"), new Phone("12345678"), new Email("ed@edsheeran.com"),
                    new Address("Halifax, United Kingdom"), new Birthday("17/02/1991"),
                    new UserName("edsheeran"), new UserName("teddysphotos"),
                    new DisplayPic(DEFAULT_DP), getTagSet("singer")),
                new Person(new Name("Kendrick Lamar"), new Phone("12345678"), new Email("kdot@topdawgent.com"),
                    new Address("Compton, California, United States"), new Birthday("17/06/1987"),
                        new UserName("kendricklamar"), new UserName("kendricklamar"),
                    new DisplayPic(DEFAULT_DP), getTagSet("california", "hiphop")),
                new Person(new Name("Kim Kardashian"), new Phone("12345678"), new Email("kim@kimk.com"),
                        new Address("Los Angeles, California, United States"), new Birthday("21/10/1980"),
                        new UserName("kimkardashian"), new UserName("kimkardashian"),
                        new DisplayPic(DEFAULT_DP), getTagSet("california", "fashion")),
                new Person(new Name("Sandara Park"), new Phone("12345678"), new Email("dara@ygent.com"),
                        new Address("Busan, South Korea"), new Birthday("12/11/1984"),
                        new UserName("krungy21"), new UserName("daraxxi"),
                        new DisplayPic(DEFAULT_DP), getTagSet("kpop")),
                new Person(new Name("Snoop Dogg"), new Phone("12345678"), new Email("snoop@snoop.com"),
                    new Address("Long Beach, California"), new Birthday("20/10/1971"),
                        new UserName("snoopdogg"), new UserName("snoopdogg"),
                    new DisplayPic(DEFAULT_DP), getTagSet("hiphop", "california"))
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
