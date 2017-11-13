package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.ChannelId;
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
                new Person(new Name("Felix Arvid Ulf Kjellberg"),
                        new Phone("87438807"),
                        new Email("pewdiepie@example.com"),
                        new Address("5 Bedford Pl, Brighton BN1 2PT, UK"),
                        new ChannelId("UC-lHJZR3Gqxm24_Vd_AJ5Yw"),
                        getTagSet("Gaming", "Comedy"),
                        new Favourite("false")),
                new Person(new Name("Anthony Padilla"),
                        new Phone("93210283"),
                        new Email("anthonypadilla@example.com"),
                        new Address("4528 Olympiad Way, Sacramento, California"),
                        new ChannelId("UCPJHQ5_DLtxZ1gzBvZE99_g"),
                        getTagSet("Comedy"),
                        new Favourite("false")),
                new Person(new Name("Naomi Neo"),
                        new Phone("84196278"),
                        new Email("naomineo@example.com"),
                        new Address("Block 24 Redhill street 19, #09-572, Singapore"),
                        new ChannelId("UCPg43ka6hrodDjMtySF3FnQ"),
                        getTagSet("People"),
                        new Favourite("false")),
                new Person(new Name("Kurt Hugo Schneider"),
                        new Phone("95486217"),
                        new Email("kurtschneider@example.com"),
                        new Address("4525 Rosewood Ave, Los Angeles, USA"),
                        new ChannelId("UCplkk3J5wrEl0TNrthHjq4Q"),
                        getTagSet("Music"),
                        new Favourite("false")),
                new Person(new Name("Tan Jian Hao"),
                        new Phone("95138557"),
                        new Email("jianhaotan@example.com"),
                        new Address("Block 639 Yishun Street 61, #10-225, Singapore"),
                        new ChannelId("UCGwcH4qnJ2qM_ZJUSFcAMAA"),
                        getTagSet("People"),
                        new Favourite("false")),
                new Person(new Name("Soren Wood"),
                        new Phone("99272758"),
                        new Email("zfsovietwomble@example.com"),
                        new Address("36 Selborne Rd, Hove BN3 3AH, UK"),
                        new ChannelId("UCQD3awTLw9i8Xzh85FKsuJA"),
                        getTagSet("Gaming", "Comedy"),
                        new Favourite("false")),
                new Person(new Name("Aamir"),
                        new Phone("91031282"),
                        new Email("zfcyanide@example.com"),
                        new Address("Blk 436 Serangoon Gardens Street 26, #16-43, Singapore"),
                        new ChannelId("UCuHQqPko8f0lZtc8D2Z7BoA"),
                        getTagSet("Gaming", "Comedy"),
                        new Favourite("false")),
                new Person(new Name("Lisa Gade"),
                        new Phone("92492021"),
                        new Email("mobiletechreview@example.com"),
                        new Address("440 Old Iron Works Rd Spartanburg, SC 29302 USA"),
                        new ChannelId("UCW6J17hZ_Vgr6cQgd_kHt5A"),
                        getTagSet("Technology"),
                        new Favourite("false")),
                new Person(new Name("Gary Vaynerchuk"),
                        new Phone("92624417"),
                        new Email("garyvee@example.com"),
                        new Address("1170 Angelo Dr, Beverly Hills, CA 90210, USA"),
                        new ChannelId("UCctXZhXmG-kf3tlIXgVZUlw"),
                        getTagSet("People"),
                        new Favourite("false")),
                new Person(new Name("Edberg Oliveiro"),
                        new Phone("92624417"),
                        new Email("zfedberg@example.com"),
                        new Address("Domaregatan 21, 824 43 Hudiksvall, Sweden"),
                        new ChannelId("UCvlvI5s742g4cJiXS_ZYdfQ"),
                        getTagSet("Gaming", "Comedy"),
                        new Favourite("false")),
                new Person(new Name("Jamie Oliver"),
                        new Phone("34859674"),
                        new Email("ftbusiness@example.com"),
                        new Address("4 Blake Gardens, Great Harwood, Blackburn BB6 7JX, UK"),
                        new ChannelId("UCpSgg_ECBj25s9moCDfSTsA"),
                        getTagSet("HowTo", "Food"),
                        new Favourite("false")),
                new Person(new Name("Ellen Degeneres"),
                        new Phone("24574839"),
                        new Email("Ellen@example.com"),
                        new Address("7246 W. Windsor Dr.Carmichael, CA 95608"),
                        new ChannelId("UCp0hYYBW6IMayGgR-WeoCvQ"),
                        getTagSet("Comedy"),
                        new Favourite("false")),
                new Person(new Name("Rob Chapman"),
                        new Phone("38495768"),
                        new Email("robchapman@example.com"),
                        new Address("24-28 Eden St, Kingston upon Thames KT1 1EP, UK"),
                        new ChannelId("UCCOIcdii1bQmfSPHeNNw4Qw"),
                        getTagSet("Music"),
                        new Favourite("false")),
                new Person(new Name("Emzotic"),
                        new Phone("93736283"),
                        new Email("emzotic@example.com"),
                        new Address("294 Burnley Rd, Cliviger, Burnley BB10 4SP, UK"),
                        new ChannelId("UCE37328iVnqSj1OCrgrYd8w"),
                        getTagSet("Pets", "Animals"),
                        new Favourite("false")),
                new Person(new Name("Coyote Peterson"),
                        new Phone("95768172"),
                        new Email("coyotepeterson@example.com"),
                        new Address("20 Maple Avenue San Pedro, CA 90731"),
                        new ChannelId("UC6E2mP01ZLH_kbAyeazCNdg"),
                        getTagSet("Animals"),
                        new Favourite("false")),
                new Person(new Name("Conan O Brien"),
                        new Phone("12346578"),
                        new Email("conan@example.com"),
                        new Address("7 W. Adams Lane San Jose, CA 95116"),
                        new ChannelId("UCi7GJNg51C3jgmYTUwqoUXA"),
                        getTagSet("Comedy"),
                        new Favourite("false")),
                new Person(new Name("Paul Davids"),
                        new Phone("19385637"),
                        new Email("pauldavids@example.com"),
                        new Address("10 Westminster Cl, Accrington BB5 2TR, UK"),
                        new ChannelId("UC_Oa7Ph3v94om5OyxY1nPKg"),
                        getTagSet("Music"),
                        new Favourite("false")),
                new Person(new Name("Namewee"),
                        new Phone("58496057"),
                        new Email("namewee@example.com"),
                        new Address("Kuala Lumpur"),
                        new ChannelId("UCFUtqTcgJgRnmZ3tMU6P74Q"),
                        getTagSet("Music", "Comedy", "Politics"),
                        new Favourite("false")),
                new Person(new Name("Billy Wingrove"),
                        new Phone("87431589"),
                        new Email("billywingrove@example.com"),
                        new Address("315 Eaton Rd Liverpool L12 2AQ, UK"),
                        new ChannelId("UCKvn9VBLAiLiYL4FFJHri6g"),
                        getTagSet("Sports"),
                        new Favourite("false")),
                new Person(new Name("Jeremy Lynch"),
                        new Phone("94782165"),
                        new Email("jeremylynch@example.com"),
                        new Address("316 Eaton Rd Liverpool L12 2AQ, UK"),
                        new ChannelId("UCKvn9VBLAiLiYL4FFJHri6g"),
                        getTagSet("Sports"),
                        new Favourite("false"))

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
