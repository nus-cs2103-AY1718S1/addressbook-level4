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
import seedu.address.model.person.Picture;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Website;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;


/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        try {
            return new Person[] {
                new Person(new Name("Alexej Kiro"), new Phone("29505368"), new Email("alexej@gmail.com"),
                    new Address("2 Sembawang Walk"), new Birthday("27/07/1997"),
                    new Remark("Badminton champion"), new Website(Website.WEBSITE_NULL),
                    new Picture(Picture.DEFAULT_BALAKRISHNAN), getTagSet("friends")),
                new Person(new Name("Alex Yeoh"), new Phone("47261490"), new Email("alexyeoh@gmail.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"), new Birthday("13/01/1970"),
                    new Remark("Taking CS2103T"), new Website(Website.WEBSITE_NULL),
                    new Picture(Picture.DEFAULT_ALEX), getTagSet("friends", "classmates")),
                new Person(new Name("Beatrix Lauryn"), new Phone("98415482"), new Email("beatrixl@u.nus.edu.sg"),
                    new Address("Blk 1090 Lower Delta Road 03-01"), new Birthday("21/10/1977"),
                    new Remark("Owes me 50 dollar"), new Website(Website.WEBSITE_NULL),
                    new Picture(Picture.DEFAULT_BALAKRISHNAN), getTagSet("classmates", "roommates")),
                new Person(new Name("Bernice Yu"), new Phone("96632044"), new Email("berniceyu@yahoo.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Birthday("06/12/1975"),
                    new Remark("Likes to swim."), new Website(Website.WEBSITE_NULL),
                    new Picture(Picture.DEFAULT_BERNICE), getTagSet("colleagues", "friends")),
                new Person(new Name("Charlotte Oliveiro"), new Phone("29085937"), new Email("charlotte@hotmail.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Birthday("17/04/1976"),
                    new Remark("CAP 5.0"), new Website(Website.WEBSITE_NULL),
                    new Picture(Picture.DEFAULT_CHARLOTTE), getTagSet("neighbours", "classmates")),
                new Person(new Name("David Li"), new Phone("43379440"), new Email("lidavid@yahoo.com.sg"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Birthday("02/12/1976"),
                    new Remark("As quick as a leopard."), new Website(Website.WEBSITE_NULL),
                    new Picture(Picture.DEFAULT_DAVID), getTagSet("family")),
                new Person(new Name("Dijana Domitianus"), new Phone("28278063"), new Email("dijana@hotmail.com"),
                    new Address("85 Science Park Drive #03-01/04"), new Birthday("15/05/1990"),
                    new Remark("Love Char Kuey Teow"), new Website(Website.WEBSITE_NULL),
                    new Picture(Picture.DEFAULT_BALAKRISHNAN), getTagSet("friends")),
                new Person(new Name("Hisham Vasileios"), new Phone("30408853"), new Email("hisham@yahoo.com"),
                    new Address("110A Killiney Road TAI WAH BUILDING"), new Birthday("02/02/1995"),
                    new Remark("Hiker"), new Website(Website.WEBSITE_NULL),
                    new Picture(Picture.DEFAULT_BALAKRISHNAN), getTagSet("friends")),
                new Person(new Name("Irfan Ibrahim"), new Phone("60248065"), new Email("irfan@gmail.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"), new Birthday("12/02/1977"),
                    new Remark("Plays the piano"), new Website(Website.WEBSITE_NULL),
                    new Picture(Picture.DEFAULT_IRFAN), getTagSet("classmates", "colleagues")),
                new Person(new Name("Keshia Deloris"), new Phone("21038401"), new Email("keshia@hotmail.com"),
                    new Address("7500A Beach Road #05-312 The Plaza"), new Birthday("08/06/1995"),
                    new Remark("Likes watermelon"), new Website(Website.WEBSITE_NULL),
                    new Picture(Picture.DEFAULT_BALAKRISHNAN), getTagSet("roommates", "classmates")),
                new Person(new Name("Kumar Louisa"), new Phone("63774380"), new Email("kumar@yahoo.com"),
                    new Address("71 Sultan Gate"), new Birthday("29/04/1989"),
                    new Remark("Got A in CS2103T last year"), new Website(Website.WEBSITE_NULL),
                    new Picture(Picture.DEFAULT_BALAKRISHNAN), getTagSet("friends", "classmates")),
                new Person(new Name("Leto Thulile"), new Phone("34853934"), new Email("letot@yahoo.com"),
                    new Address("100 Gul Circle, 629586"), new Birthday("07/05/1987"),
                    new Remark("Googler"), new Website(Website.WEBSITE_NULL),
                    new Picture(Picture.DEFAULT_BALAKRISHNAN), getTagSet("roommates")),
                new Person(new Name("Lise Puja"), new Phone("61653993"), new Email("lisa@google.com"),
                    new Address("77 High Street 179433"), new Birthday("08/12/1985"),
                    new Remark("NUS Wind Symphony"), new Website(Website.WEBSITE_NULL),
                    new Picture(Picture.DEFAULT_BALAKRISHNAN), getTagSet("neighbours", "colleagues")),
                new Person(new Name("Ljube Shun"), new Phone("16047567"), new Email("ljube@gmail.com"),
                    new Address("23 Defu Lane 4"), new Birthday("16/08/1989"),
                    new Remark("Prospective doctor"), new Website(Website.WEBSITE_NULL),
                    new Picture(Picture.DEFAULT_BALAKRISHNAN), getTagSet("colleagues", "roommates")),
                new Person(new Name("Mikulas Larisa"), new Phone("56737480"), new Email("mikulasl@facebook.com"),
                    new Address("62 Tannery Lane, 347804, Singapore"), new Birthday("08/10/1980"),
                    new Remark("Exchange student from Poland"), new Website(Website.WEBSITE_NULL),
                    new Picture(Picture.DEFAULT_BALAKRISHNAN), getTagSet("friends", "colleagues")),
                new Person(new Name("Roy Balakrishnan"), new Phone("98259530"), new Email("royb@hotmail.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), new Birthday("29/03/1977"),
                    new Remark("Speaks 6 languages"), new Website(Website.WEBSITE_NULL),
                    new Picture(Picture.DEFAULT_BALAKRISHNAN), getTagSet("colleagues")),
                new Person(new Name("Shila Siguro"), new Phone("85577250"), new Email("shila@hotmail.com"),
                    new Address("12 Prince Edward Rd #04-06"), new Birthday("20/09/1990"),
                    new Remark("Make delicious tacos"), new Website(Website.WEBSITE_NULL),
                    new Picture(Picture.DEFAULT_BALAKRISHNAN), getTagSet("roommates", "classmates")),
                new Person(new Name("Tim Bone"), new Phone("67894650"), new Email("tim@gmail.com"),
                    new Address("1003 Bukit Merah Central #04-16 Entrepreneur Centre"), new Birthday("25/09/1993"),
                    new Remark("Math nerd"), new Website(Website.WEBSITE_NULL),
                    new Picture(Picture.DEFAULT_BALAKRISHNAN), getTagSet("colleagues", "neighbours")),
                new Person(new Name("Vasu Drust"), new Phone("80280434"), new Email("me@vasu.com"),
                    new Address("177 River Valley Rd #03-40"), new Birthday("06/08/1992"),
                    new Remark("Stand up comedian"), new Website(Website.WEBSITE_NULL),
                    new Picture(Picture.DEFAULT_BALAKRISHNAN), getTagSet("friends")),
                new Person(new Name("Viktoras Theodore"), new Phone("55046009"), new Email("vik@vk.com"),
                    new Address("7 Orange Grove Road Singapore 258355"), new Birthday("24/07/1985"),
                    new Remark("Footballer"), new Website(Website.WEBSITE_NULL),
                    new Picture(Picture.DEFAULT_BALAKRISHNAN), getTagSet("friends", "neighbours"))
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
