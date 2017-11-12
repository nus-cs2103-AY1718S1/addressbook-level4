package seedu.address.model.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
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
import seedu.address.model.person.Score;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    //@@author Linus
    private static String[] firstNames = {"Roy", "David", "Alex", "Linus", "Siri", "Hector", "Achilles", "Odysseus",
        "Jack", "Donald"};
    private static String[] lastNames = {"Herason", "Trump", "Thompson", "Curry", "Westbrook", "Harden", "O'niel",
        "Clinton", "Obama", "Washington"};
    private static String[] emails = {"gmail.com", "yahoo", "outlook", "hotmail", "qq.com", "sina.com"};
    private static String[] tags = {"colleagues", "friends", "president", "students", "customers", "lawyers",
        "farmers"};
    private static String[] phones = {"90909099", "87878888", "33222111", "84456723", "90111123", "12345678",
        "98363363"};
    private static String[] birthdays = {"01/09/1988", "03/08/1975", "02/02/1989", "31/12/1966", "28/05/1956",
        "03/02/1999", "13/12/2000"};

    private static int sampleDataSize = 8;

    public static Person[] getSamplePersons() {
        /*
         *  Sample data
         *  It generates 100 test data
         * */
        Random rand = new Random();
        String firstName = null;
        ArrayList<Person> persons = new ArrayList<Person>();

        for (int i = 0; i < sampleDataSize; i++) {

            firstName = firstNames[i % (sampleDataSize)];

            for (int j = 0; j < sampleDataSize - 1; j++) {

                try {

                    persons.add(generatePerson(firstName, j, rand));

                } catch (IllegalValueException e) {

                    throw new AssertionError("sample data cannot be invalid", e);

                }
            }
        }
        return persons.toArray(new Person[persons.size()]);
    }

    /**
     * Returns a tag set containing the list of strings given baesd on input.
     */
    public static Set<Tag> generateTags(int i) throws IllegalValueException {

        HashSet<Tag> addTags = new HashSet<>();
        for (int j = 0; j < i; j++) {
            addTags.add(new Tag(tags[j]));
        }
        return addTags;
    }

    /**
     * Returns a person object that generated randomly.
     */
    public static Person generatePerson(String firstName, int j, Random rand) throws IllegalValueException {

        /*
        *  Those numbers are totally arbitrary for generating sample data
        * */
        j = j % 7;
        String lastName = lastNames[j % (sampleDataSize - 2)];
        String phone = phones[j];
        String email = emails[j % (sampleDataSize - 2)];
        String units = Integer.toString(j);
        String score = Integer.toString(j);
        Set<Tag> tag = generateTags(j);

        if (j / 3 == 1) {
            return new Person(new Name(firstName + " " + lastName), new Phone(phone),
                    new Birthday("No Birthday Listed"),
                    new Email(firstName + "@" + email),
                    new Address("Blk 45 Aljunied Street 85, #" + units),
                    new Score(score), tag);
        }
        return new Person(new Name(firstName + " " + lastName), new Phone(phone),
                new Birthday(generateBirthday(j)),
                new Email(firstName + "@" + email),
                new Address("Victoria 34 Arran Street 45, #" + units),
                new Score(score), tag);

    }

    /**
     * Returns a birthday string based on input.
     */
    public static String generateBirthday(int i) {
        int n = i % 7;
        return birthdays[n];
    }

    //@@author Linus

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
