package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;

import seedu.address.model.person.Address;
import seedu.address.model.person.Age;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Photo;
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
                new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"), new Remark(""), new Birthday("09-10-1988"),
                new Age("09-10-1988"), new Photo("src/main/resources/images/alexyeoh.jpeg"),
                getTagSet("friends")),
                new Person(new Name("Alice Childs"), new Phone("98167306"), new Email("alicechilds@hotmail.com"),
                new Address("Blk 99 Ang Mo Kio #13-14"), new Remark(""), new Birthday("01-12-1976"),
                new Age("01-12-1976"), new Photo("src/main/resources/images/alicechilds.jpg"),
                getTagSet("friends")),
                new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Remark("Likes to swim."),
                    new Birthday("17-11-1993"), new Age("17-11-1993"),
                    new Photo("src/main/resources/images/berniceyu.jpg"),
                    getTagSet("colleagues", "friends")),
                new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Remark(""), new Birthday("12-04-1969"),
                    new Age("12-04-1969"), new Photo("src/main/resources/images/charlotteoliver.jpg"),
                getTagSet("neighbours")),
                new Person(new Name("Cheryl Brand"), new Phone("85042184"), new Email("cherylbrand@yahoo.com"),
                    new Address("Blk 1 Toa Payoh #31-58"), new Remark(""),
                    new Birthday("12-09-1977"), new Age("12-09-1977"),
                    new Photo("src/main/resources/images/cherylbrand.jpg"), getTagSet("family")),
                new Person(new Name("Claire Williams"), new Phone("83093908"), new Email("clairewilliams@sia.com"),
                    new Address("22 Changi Road"), new Remark(""),
                    new Birthday("22-12-1972"), new Age("22-12-1972"),
                    new Photo("src/main/resources/images/clairewilliams.jpg"),
                    getTagSet("friends")),
                new Person(new Name("Dale Brown"), new Phone("82073764"), new Email("dalebrown@m1.biz"),
                    new Address("12 Geyland Lorong 18"), new Remark(""),
                    new Birthday("03-02-1966"), new Age("03-02-1966"),
                    new Photo("src/main/resources/images/dalebrown.jpg"),
                    getTagSet("friends")),
                new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Remark("As quick as a leopard."),
                    new Birthday("22-07-1960"), new Age("22-07-1960"),
                    new Photo("src/main/resources/images/davidli.jpg"), getTagSet("family")),
                new Person(new Name("Edna Jewell"), new Phone("96018291"), new Email("ednajewell@xiaomi.net"),
                    new Address("11 Jurong Point"), new Remark(""),
                    new Birthday("09-07-1931"), new Age("09-07-1931"),
                    new Photo("src/main/resources/images/ednajewell.jpg"), getTagSet("family")),
                new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"), new Remark(""), new Birthday("19-11-1972"),
                    new Age("19-11-1972"), new Photo("src/main/resources/images/irfanibrahim.jpg"),
                getTagSet("classmates")),
                new Person(new Name("Karen Murphy"), new Phone("94147773"), new Email("karenmurph@interstellar.com"),
                    new Address("33 Woodlawn Drive"), new Remark(""), new Birthday("18-02-1965"),
                    new Age("18-02-1965"), new Photo("src/main/resources/images/karenmurphy.jpg"),
                    getTagSet("classmates")),
                new Person(new Name("Kathy Hagan"), new Phone("98306318"), new Email("kathyhagan@realemail.com"),
                    new Address("11 Crestview Terrace"), new Remark(""), new Birthday("15-03-1965"),
                    new Age("15-03-1965"), new Photo("src/main/resources/images/kathyhagan.jpg"),
                    getTagSet("classmates")),
                new Person(new Name("Leo Fraizer"), new Phone("88164476"), new Email("leofraizer@singtel.com"),
                    new Address("8 Traders Alley"), new Remark(""), new Birthday("04-10-1960"),
                    new Age("04-10-1960"), new Photo("src/main/resources/images/leofrazier.jpg"),
                    getTagSet("classmates")),
                new Person(new Name("Linda Briley"), new Phone("98107605"), new Email("lindabriley@hotmail.com"),
                    new Address("99 East Coast Road"), new Remark(""), new Birthday("09-09-1968"),
                    new Age("09-09-1968"), new Photo("src/main/resources/images/lindabriley.jpg"),
                    getTagSet("classmates")),
                new Person(new Name("Mae Beckner"), new Phone("94082380"), new Email("mae@gmail.com"),
                    new Address("Blk 309 Toa Payoh #03-22"), new Remark(""), new Birthday("27-02-1986"),
                    new Age("27-02-1986"), new Photo("src/main/resources/images/maebeckner.jpg"),
                    getTagSet("classmates")),
                new Person(new Name("Miguel Gardener"), new Phone("82032473"), new Email("migueltgardener@hotmail.com"),
                    new Address("2103 Colony Street"), new Remark(""), new Birthday("10-08-1966"),
                    new Age("10-08-1966"), new Photo("src/main/resources/images/miguel.jpg"),
                    getTagSet("Mexican")),
                new Person(new Name("Noreen Perry"), new Phone("99288102"), new Email("noreenperry@starhub.com"),
                    new Address("3284 Farm Meadow Drive"), new Remark(""), new Birthday("10-01-1955"),
                    new Age("10-01-1955"), new Photo("src/main/resources/images/noreenperry.jpg"),
                    getTagSet("FunnyGuy")),
                new Person(new Name("Robert Gast"), new Phone("94198034"), new Email("robertgast@gmail.com"),
                    new Address("33 Hill Street"), new Remark(""), new Birthday("04-04-1989"),
                    new Age("04-04-1989"), new Photo("src/main/resources/images/robertgast.jpg"),
                    getTagSet("FromItaly")),
                new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"), new Remark(""), new Birthday("29-01-1979"),
                    new Age("29-01-1979"), new Photo("src/main/resources/images/roy.jpg"),
                getTagSet("colleagues")),
                new Person(new Name("Tony Valdez"), new Phone("95076366"), new Email("tonyvaldez@myrepublic.com"),
                    new Address("17 Tekong Drive"), new Remark(""), new Birthday("21-08-1944"),
                    new Age("21-08-1944"), new Photo("src/main/resources/images/tonyvaldez.jpg"),
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
