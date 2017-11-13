package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Occupation;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
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
                new Person(new Name("Alex Yeoh"), new Occupation("Google, Software Engineer"), new Phone("87438807"),
                        new Email("alexyeoh@example.com"), new Address("Blk 30 Geylang Street 29, #06-40"),
                        new Remark(""), new Website("https://twitter.com/AlexYeoh"), getTagSet("friends")),
                new Person(new Name("Bernice Yu"), new Occupation("Apple, Janitor"), new Phone("99272758"),
                        new Email("berniceyu@example.com"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                        new Remark("Likes to swim."), new Website("https://twitter.com/BerniceYu"),
                        getTagSet("colleagues", "friends")),
                new Person(new Name("Charlotte Oliveiro"), new Occupation("Tan Tock Seng Hospital, Doctor"),
                        new Phone("93210283"), new Email("charlotte@example.com"),
                        new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Remark(""),
                        new Website("https://twitter.com/CharlotteOliveiro"), getTagSet("neighbours")),
                new Person(new Name("David Li"), new Occupation("Prudential, Financial Assistant"),
                        new Phone("91031282"), new Email("lidavid@example.com"),
                        new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Remark("As quick a a leopard."),
                        new Website("https://twitter.com/DavidLi"), getTagSet("family")),
                new Person(new Name("Irfan Ibrahim"), new Occupation("NUS, Professor"), new Phone("92492021"),
                        new Email("irfan@example.com"), new Address("Blk 47 Tampines Street 20, #17-35"),
                        new Remark(""), new Website("https://twitter.com/IrfanIbrahim"),
                        getTagSet("classmates")),
                new Person(new Name("Ben Asmoth"), new Occupation("Fairprice, Cashier"), new Phone("98453628"),
                        new Email("Ben@example.com"), new Address("Blk 66 Simei Drive, #12-44"), new Remark(""),
                        new Website("https://twitter.com/Ben"), getTagSet("Cousin")),
                new Person(new Name("Peter Pan"), new Occupation("Converse, Shoe Designer"), new Phone("84746363"),
                        new Email("Peter@example.com"), new Address("Blk 30 Geylang Street 29, #06-40"),
                        new Remark(""), new Website("https://facebook.com/Peter"), getTagSet("enemy")),
                new Person(new Name("Claire"), new Occupation("Asus, Sales manager"), new Phone("94634356"),
                        new Email("claire@example.com"), new Address("77 Woodlands Road, #10-23"),
                        new Remark(""), new Website("https://twitter.com/Claire"), getTagSet("lawyer")),
                new Person(new Name("Albert Ballard"), new Occupation("AG, Social worker"), new Phone("81610619"),
                        new Email("albert@example.com"), new Address("88 Pasir Panjang Road, #44-56"),
                        new Remark(""), new Website("https://twitter.com/Albert"), getTagSet("friends")),
                new Person(new Name("Anne Deines"), new Occupation("Matrix, Research chef"),
                        new Phone("98293453"),
                        new Email("Anne@example.com"), new Address("1778 Eagle Drive, Plymouth, MI 48170"),
                        new Remark(""), new Website("https://twitter.com/Anne"), getTagSet("relative")),
                new Person(new Name("Florencio Pham"), new Occupation("Consumers, Technician"), new Phone("8746477"),
                        new Email("florencio@example.com"), new Address("3162 Fort Street, Rocky Mount, NC 27801"),
                        new Remark(""), new Website("https://twitter.com/Florencio"),
                        getTagSet("grandmother")),
                new Person(new Name("Dina Cyr"), new Occupation("Flagg Bros Shoes, Office administrator"),
                        new Phone("96545638"),
                        new Email("dina@example.com"), new Address("4960 Lawman Avenue, Chantilly, VA 22021"),
                        new Remark(""), new Website("https://twitter.com/Dina"), getTagSet("boss")),
                new Person(new Name("Brian McDougal"), new Occupation("Meiji, Milk tester"),
                        new Phone("97356574"),
                        new Email("brianmc@example.com"), new Address("33 pasir ris drive, #12-48"),
                        new Remark(""), new Website("https://facebook.com/BrianMc"), getTagSet("teacher")),
                new Person(new Name("Carlene Hood"), new Occupation("Listenin Booth, Human resources trainer"),
                        new Phone("897488374"),
                        new Email("carlene@example.com"), new Address("747 Deercove Drive, Philadelphia, PA 19108"),
                        new Remark(""), new Website("https://facebook.com/Carlene"), getTagSet("girlfriend")),
                new Person(new Name("Larry Bakker"), new Occupation("Family Toy, Financial analyst"),
                        new Phone("90887532"),
                        new Email("larry@example.com"), new Address("2607 Brown Bear Drive, Murrieta, CA 92562"),
                        new Remark(""), new Website("https://twitter.com/Larry"), getTagSet("boyfriend")),
                new Person(new Name("Milton Coleman"), new Occupation("Helios Air, Armed Forces"),
                        new Phone("92773625"),
                        new Email("miltonh@example.com"), new Address("4947 Joseph Street, West Allis, WI 53227"),
                        new Remark(""), new Website("https://twitter.com/milton"), getTagSet("brother")),
                new Person(new Name("Sydney Thomas"), new Occupation("Childs Restaurants, Allergist"),
                        new Phone("87436367"),
                        new Email("sydney@example.com"), new Address("3233 Virginia Street, Chicago, IL 60631"),
                        new Remark(""), new Website("https://facebook.com/Sydney"), getTagSet("butcher")),
                new Person(new Name("Michelle Martinez"), new Occupation("Piece Goods Fabric, Pesticide sprayer"),
                        new Phone("90726355"),
                        new Email("michelle@example.com"), new Address("2577 Jail Drive, Peoria, IL 61602"),
                        new Remark(""), new Website("https://github.com/Michellle"), getTagSet("manager")),
                new Person(new Name("Michael Gonzalez"),
                        new Occupation("Schucks Auto Supply, Instructional assistant"), new Phone("99885537"),
                        new Email("michael@example.com"), new Address("3024 Meadowbrook Mall Road, Gardena, CA 90247"),
                        new Remark(""), new Website("https://github.com/Michael"), getTagSet("classmate")),
                new Person(new Name("Jacqueline Keeler"),
                        new Occupation("Real Estate Service, Funeral attendant"), new Phone("90303016"),
                        new Email("Jacqy@example.com"), new Address("2547 Berry Street, Limon, CO 80828"),
                        new Remark(""), new Website("https://twitter.com/Jac"), getTagSet("owner"))
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
