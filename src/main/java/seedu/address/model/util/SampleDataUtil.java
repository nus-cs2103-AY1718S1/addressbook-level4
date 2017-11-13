package seedu.address.model.util;

import static seedu.address.model.util.DateUtil.formatDate;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.DateBorrow;
import seedu.address.model.person.DateRepaid;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.Debt;
import seedu.address.model.person.Email;
import seedu.address.model.person.Handphone;
import seedu.address.model.person.HomePhone;
import seedu.address.model.person.Interest;
import seedu.address.model.person.Name;
import seedu.address.model.person.OfficePhone;
import seedu.address.model.person.Person;
import seedu.address.model.person.PostalCode;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        try {
            return new Person[] {
                new Person(new Name("Bernice Yu"), new Handphone("99272758"), new HomePhone("62772222"),
                    new OfficePhone("60006002"), new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new PostalCode("554403"),
                    new Debt("500"), new Interest("1"),
                    new Deadline("02-02-2020"), getTagSet("unfriendly", "tricky")),
                new Person(new Name("Alex Yeoh"), new Handphone("87438807"), new HomePhone("61313131"),
                    new OfficePhone (OfficePhone.NO_OFFICE_PHONE_SET), new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"), new PostalCode("418362"),
                    new Debt("10000"), new Interest(Interest.NO_INTEREST_SET),
                    new Deadline(Deadline.NO_DEADLINE_SET), getTagSet("violent")),
                new Person(new Name("David Li"), new Handphone("91031282"), new HomePhone("64744774"),
                    new OfficePhone("64446004"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new PostalCode("554436"),
                    new Debt("50"), new Interest(Interest.NO_INTEREST_SET),
                    new Deadline("11-11-2016"), getTagSet("unfriendly")),
                new Person(new Name("Charlotte Oliveiro"), new Handphone("93210283"), new HomePhone("63033333"),
                    new OfficePhone(OfficePhone.NO_OFFICE_PHONE_SET), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new PostalCode("560011"),
                    new Debt("300"), new Interest(Interest.NO_INTEREST_SET),
                    new Deadline(Deadline.NO_DEADLINE_SET), getTagSet("tricky")),
                new Person(new Name("Eileen Choo"), new Handphone("91373723"), new HomePhone("65126505"),
                    new OfficePhone(OfficePhone.NO_OFFICE_PHONE_SET), new Email("elchoo@example.com"),
                    new Address("Blk 432 Tiong Bahru Avenue 2, #16-43"), new PostalCode("144432"),
                    new Debt("2000"), new Interest("1"),
                    new Deadline("25-12-2017"), getTagSet("cooperative")),
                new Person(new Name("Irfan Ibrahim"), new Handphone("92492021"), new HomePhone("67099399"),
                    new OfficePhone(OfficePhone.NO_OFFICE_PHONE_SET), new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"), new PostalCode("515047"),
                    new Debt("90000"), new Interest("4"),
                    new Deadline("15-03-2017"), getTagSet("unfriendly")),
                new Person(new Name("Farhan Mohammed"), new Handphone("82837273"), new HomePhone("60666776"),
                    new OfficePhone("60606006"), new Email("fhmm@example.com"),
                    new Address("Blk 22 Queenstown Street 25, #16-43"), new PostalCode("164422"),
                    new Debt("2000"), new Interest("4"),
                    new Deadline("01-01-2040"), getTagSet("violent")),
                new Person(new Name("Herbert He"), new Handphone("90093007"), new HomePhone("60800028"),
                    new OfficePhone("66888008"), new Email("hehehe@example.com"),
                    new Address("Blk 3 Hillview Avenue, #16-43"), new PostalCode("674433"),
                    new Debt("2000"), new Interest("4"),
                    new Deadline("31-01-2030"), getTagSet("violent")),
                new Person(new Name("Gisela Tan"), new Handphone("87727737"), new HomePhone("67773777"),
                    new OfficePhone(OfficePhone.NO_OFFICE_PHONE_SET), new Email("ggwantan@example.com"),
                    new Address("Blk 23 Queenstown Street 25, #12-37"), new PostalCode("164423"),
                    new Debt("0"), new Interest(Interest.NO_INTEREST_SET),
                    new Deadline(Deadline.NO_DEADLINE_SET), getTagSet("cooperative", "friendly")),
                new Person(new Name("Jacob Peters"), new Handphone("82888088"), new HomePhone("68828882"),
                    new OfficePhone(OfficePhone.NO_OFFICE_PHONE_SET), new Email("jp@example.com"),
                    new Address("82 Chwee Chian Road"), new PostalCode("119782"),
                    new Debt("10000"), new Interest("1"),
                    new Deadline(Deadline.NO_DEADLINE_SET), getTagSet("cooperative")),
                new Person(new Name("Kanyee North"), new Handphone("90797099"), new HomePhone("61999192"),
                    new OfficePhone(OfficePhone.NO_OFFICE_PHONE_SET), new Email("yeeeeee@example.com"),
                    new Address("Blk 100 Admiralty Road, #07-09"), new PostalCode("739980"),
                    new Debt("99999"), new Interest("2"),
                    new Deadline("07-01-2025"), getTagSet("violent", "unfriendly", "tricky")),
                new Person(new Name("Liang Ah Hock"), new Handphone("91012102"), new HomePhone("61007212"),
                    new OfficePhone("60110010"), new Email("lyhkorkor@example.com"),
                    new Address("Blk 3 Sengkang East Ave, #10-100"), new PostalCode("544813"),
                    new Debt("20"), new Interest(Interest.NO_INTEREST_SET),
                    new Deadline(Deadline.NO_DEADLINE_SET), getTagSet("unpredictable")),
                new Person(new Name("Monica Liza"), new Handphone("91102013"), new HomePhone("63110213"),
                    new OfficePhone(OfficePhone.NO_OFFICE_PHONE_SET), new Email("monaliza@example.com"),
                    new Address("Blk 241 Jurong East St 24 #03-493"), new PostalCode("600241"),
                    new Debt("70123"), new Interest("1"),
                    new Deadline("01-06-2016"), getTagSet("unfriendly")),
                new Person(new Name("Nigel Tan"), new Handphone("91102033"), new HomePhone("67773777"),
                    new OfficePhone(OfficePhone.NO_OFFICE_PHONE_SET), new Email("nigetan@example.com"),
                    new Address("Blk 23 Queenstown Street 25, #12-37"), new PostalCode("164423"),
                    new Debt("40000"), new Interest("2"),
                    new Deadline("31-12-2018"), getTagSet("unpredictable")),
                new Person(new Name("Ong Ah Seng"), new Handphone("82441015"), new HomePhone("64341015"),
                    new OfficePhone("60001515"), new Email("oas@example.com"),
                    new Address("Blk 3 Loyang Ave 1, #01-02"), new PostalCode("810010"),
                    new Debt("20000"), new Interest("4"),
                    new Deadline("31-12-2016"), getTagSet()),
                new Person(new Name("Pasito Arioto"), new Handphone("92159316"), new HomePhone("65051016"),
                    new OfficePhone("60000015"), new Email("despasito@example.com"),
                    new Address("Blk 454 Clementi Ave 4, #12-201"), new PostalCode("123454"),
                    new Debt("1000"), new Interest(Interest.NO_INTEREST_SET),
                    new Deadline(Deadline.NO_DEADLINE_SET), getTagSet("cooperative", "tricky")),
                new Person(new Name("Queenie Lim"), new Handphone("90162217"), new HomePhone("61618817"),
                    new OfficePhone(OfficePhone.NO_OFFICE_PHONE_SET), new Email("limthequeen@example.com"),
                    new Address("Blk 455 Clementi Ave 4, #09-221"), new PostalCode("123455"),
                    new Debt("0"), new Interest(Interest.NO_INTEREST_SET),
                    new Deadline(Deadline.NO_DEADLINE_SET), getTagSet("cooperative", "friendly")),
                new Person(new Name("Roy Balakrishnan"), new Handphone("92624417"), new HomePhone("61001010"),
                    new OfficePhone("60006110"), new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), new PostalCode("389045"),
                    new Debt("15630"), new Interest(Interest.NO_INTEREST_SET),
                    new Deadline(Deadline.NO_DEADLINE_SET), getTagSet("friendly")),
                new Person(new Name("Sam Bahdy"), new Handphone("92624418"), new HomePhone("61008888"),
                    new OfficePhone("60008008"), new Email("oncetoldme@example.com"),
                    new Address("Blk 4 Hillview Avenue, #08-51"), new PostalCode("674434"),
                    new Debt("0"), new Interest(Interest.NO_INTEREST_SET),
                    new Deadline(Deadline.NO_DEADLINE_SET), getTagSet("friendly")),
                new Person(new Name("Timmy Zhu"), new Handphone("82207720"), new HomePhone("62116520"),
                    new OfficePhone("62000020"), new Email("z3@example.com"),
                    new Address("Blk 20 Punggol Street 15, #09-211"), new PostalCode("544020"),
                    new Debt("0"), new Interest(Interest.NO_INTEREST_SET),
                    new Deadline(Deadline.NO_DEADLINE_SET), getTagSet("friendly", "cooperative"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAb = new AddressBook();
            Person[] samplePersons = getSamplePersons();
            samplePersons[0].setDebt(new Debt("20"));
            samplePersons[2].setDateBorrow(new DateBorrow("Wed, 11 May, Year 2016"));
            samplePersons[2].setHasOverdueDebt(true);
            samplePersons[3].setDebt(new Debt("150"));
            samplePersons[5].setDateBorrow(new DateBorrow("Wed, 18 May, Year 2016"));
            samplePersons[5].setHasOverdueDebt(true);
            samplePersons[5].setDebt(new Debt("80000"));
            samplePersons[6].setIsBlacklisted(true);
            samplePersons[7].setIsBlacklisted(true);
            samplePersons[8].setTotalDebt(new Debt("5000"));
            samplePersons[8].setIsWhitelisted(true);
            samplePersons[8].setDateRepaid(new DateRepaid(formatDate(new Date())));
            samplePersons[10].setIsBlacklisted(true);
            samplePersons[10].setDebt(new Debt("99000"));
            samplePersons[11].setDebt(new Debt("10"));
            samplePersons[12].setDateBorrow(new DateBorrow("Sat, 31 Oct, Year 2015"));
            samplePersons[12].setHasOverdueDebt(true);
            samplePersons[13].setIsBlacklisted(true);
            samplePersons[14].setDateBorrow(new DateBorrow("Mon, 29 Feb, Year 2016"));
            samplePersons[14].setHasOverdueDebt(true);
            samplePersons[14].setDebt(new Debt("5000"));
            samplePersons[15].setDebt(new Debt("999"));
            samplePersons[16].setTotalDebt(new Debt("10000"));
            samplePersons[16].setIsWhitelisted(true);
            samplePersons[16].setDateRepaid(new DateRepaid(formatDate(new Date())));
            samplePersons[17].setDebt(new Debt("10000"));
            samplePersons[18].setTotalDebt(new Debt("20000"));
            samplePersons[18].setIsWhitelisted(true);
            samplePersons[18].setDateRepaid(new DateRepaid(formatDate(new Date())));
            samplePersons[19].setTotalDebt(new Debt("30000"));
            samplePersons[19].setIsWhitelisted(true);
            samplePersons[19].setDateRepaid(new DateRepaid(formatDate(new Date())));

            for (Person samplePerson : samplePersons) {
                sampleAb.addPerson(samplePerson);
            }

            return sampleAb;
        } catch (DuplicatePersonException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        } catch (IllegalValueException ive) {
            throw new AssertionError("Error creating sample data");
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
