package seedu.address.model.util;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDuration;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventTime;
import seedu.address.model.event.MemberList;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.DateAdded;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {

        //@@author dalessr
        try {
            Person[] persons =  new Person[] {
                new Person(new Name("Alex Yeoh"),
                        new Birthday("30/09/2000"),
                        new Phone("87438807"),
                        new Email("alexyeoh@example.com"),
                        new Address("Blk 30 Geylang Street 29, #06-40"),
                        getTagSet("friends", "classmates"),
                        new HashSet<>(),
                        new DateAdded("01/01/2016 11:11:53")),

                new Person(new Name("Bernice Yu"),
                        new Birthday("27/02/1983"),
                        new Phone("99272758"),
                        new Email("berniceyu@example.com"),
                        new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                        getTagSet("colleagues", "friends"),
                        new HashSet<>(),
                        new DateAdded("07/02/2016 12:00:01")),

                new Person(new Name("Charlotte Oliveiro"),
                        new Birthday("03/12/1992"),
                        new Phone("93210283"),
                        new Email("charlotte@example.com"),
                        new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                        getTagSet("neighbours"),
                        new HashSet<>(),
                        new DateAdded("01/05/2016 12:00:01")),

                new Person(new Name("David Li"),
                        new Birthday("30/05/1976"),
                        new Phone("91031282"),
                        new Email("lidavid@example.com"),
                        new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                        getTagSet("family"),
                        new HashSet<>(),
                        new DateAdded("15/09/2016 12:00:01")),

                new Person(new Name("Irfan Ibrahim"),
                        new Birthday("18/11/1960"),
                        new Phone("92492021"),
                        new Email("irfan@example.com"),
                        new Address("Blk 47 Tampines Street 20, #17-35"),
                        getTagSet("profs"),
                        new HashSet<>(),
                        new DateAdded("15/09/2016 12:01:01")),

                new Person(new Name("Roy Balakrishnan"),
                        new Birthday("25/08/1996"),
                        new Phone("92624417"),
                        new Email("royb@example.com"),
                        new Address("Blk 45 Aljunied Street 85, #11-31"),
                        getTagSet("colleagues"),
                        new HashSet<>(),
                        new DateAdded("20/09/2016 12:00:01")),

                new Person(new Name("Quintin Levell"),
                        new Birthday("04/09/1997"),
                        new Phone("66012135"),
                        new Email("levellq@example.com"),
                        new Address("Blk 39 Aljunied Street 18, #03-44"),
                        getTagSet("friends", "cs2103"),
                        new HashSet<>(),
                        new DateAdded("01/10/2016 11:11:53")),

                new Person(new Name("Gino Trost"),
                        new Birthday("27/05/1987"),
                        new Phone("85154314"),
                        new Email("trostg@example.com"),
                        new Address("Blk 40 Boon Lay Street 87, #03-12"),
                        getTagSet("colleagues", "friends"),
                        new HashSet<>(),
                        new DateAdded("07/10/2016 12:00:01")),

                new Person(new Name("Honey Digiacomo"),
                        new Birthday("14/12/1994"),
                        new Phone("98500414"),
                        new Email("digiahoney@example.com"),
                        new Address("Blk 166 Ang Mo Kio Vista, #03-28"),
                        getTagSet("neighbours"),
                        new HashSet<>(),
                        new DateAdded("01/12/2016 12:00:01")),

                new Person(new Name("Fred Greenland"),
                        new Birthday("27/05/1982"),
                        new Phone("62667227"),
                        new Email("greenfd@example.com"),
                        new Address("Blk 395 Woodlands Street 10, #15-18"),
                        getTagSet("family"),
                        new HashSet<>(),
                        new DateAdded("15/12/2016 12:00:01")),

                new Person(new Name("Francina Schepers"),
                        new Birthday("07/08/1997"),
                        new Phone("62667887"),
                        new Email("francina@example.com"),
                        new Address("Blk 477 Jurong East Street 28, #01-33"),
                        getTagSet("classmates", "cs2103"),
                        new HashSet<>(),
                        new DateAdded("01/02/2017 12:01:01")),

                new Person(new Name("Ima Mauffray"),
                        new Birthday("17/10/1969"),
                        new Phone("86159036"),
                        new Email("imamau@example.com"),
                        new Address("Blk 395 Lorong 6 Orchard Road, #04-36"),
                        getTagSet("profs"),
                        new HashSet<>(),
                        new DateAdded("20/02/2017 12:00:01")),

                new Person(new Name("Han Slankard"),
                        new Birthday("18/10/1970"),
                        new Phone("82034763"),
                        new Email("hans@example.com"),
                        new Address("Blk 322 Lorong 2 Paya Lebar, #15-21"),
                        getTagSet("family"),
                        new HashSet<>(),
                        new DateAdded("01/05/2017 11:11:53")),

                new Person(new Name("Audry Sustaita"),
                        new Birthday("25/10/1983"),
                        new Phone("72394273"),
                        new Email("audrys@example.com"),
                        new Address("Blk 20 Jurong West Street 82, #10-02"),
                        getTagSet("colleagues", "friends"),
                        new HashSet<>(),
                        new DateAdded("07/05/2017 12:00:01")),

                new Person(new Name("Jade Dimas"),
                        new Birthday("28/01/1976"),
                        new Phone("99282463"),
                        new Email("dimasj@example.com"),
                        new Address("Blk 142 Geylang East Street 86, #18-33"),
                        getTagSet("tutors"),
                        new HashSet<>(),
                        new DateAdded("01/08/2017 12:00:01")),

                new Person(new Name("Lory Prosper"),
                        new Birthday("05/05/1978"),
                        new Phone("65029401"),
                        new Email("lory123@example.com"),
                        new Address("Blk 145 Shunfu Point, #11-05"),
                        getTagSet("family"),
                        new HashSet<>(),
                        new DateAdded("15/09/2017 12:00:01")),

                new Person(new Name("Leone Tipps"),
                        new Birthday("08/12/1989"),
                        new Phone("82058265"),
                        new Email("tippsl@example.com"),
                        new Address("Blk 39 Ang Mo Kio Street 86, #07-22"),
                        getTagSet("classmates"),
                        new HashSet<>(),
                        new DateAdded("28/09/2017 12:01:01")),

                new Person(new Name("Rusty Lucena"),
                        new Birthday("12/06/1973"),
                        new Phone("93376847"),
                        new Email("lucenar@example.com"),
                        new Address("Blk 345 Geylang East Street 27, #15-03"),
                        getTagSet("tutors"),
                        new HashSet<>(),
                        new DateAdded("01/10/2017 12:00:01")),

                new Person(new Name("Rhea Vallo"),
                        new Birthday("18/09/1999"),
                        new Phone("65028849"),
                        new Email("vallor@example.com"),
                        new Address("Blk 12 Lorong 14 Marine Parade, #05-25"),
                        getTagSet("family"),
                        new HashSet<>(),
                        new DateAdded("01/10/2017 11:11:53")),

                new Person(new Name("Stanford Blakemore"),
                        new Birthday("15/01/1983"),
                        new Phone("52170156"),
                        new Email("stanford@example.com"),
                        new Address("Blk 397 Serangoon Gardens Street 24, #03-26"),
                        getTagSet("colleagues", "friends"),
                        new HashSet<>(),
                        new DateAdded("10/10/2017 12:00:01")),

                new Person(new Name("Lori Bancroft"),
                        new Birthday("21/03/1992"),
                        new Phone("88305967"),
                        new Email("lorib@example.com"),
                        new Address("Blk 453 Jurong East Street 25, #10-13"),
                        getTagSet("neighbours"),
                        new HashSet<>(),
                        new DateAdded("15/10/2017 12:00:01")),

                new Person(new Name("Hollis Biles"),
                        new Birthday("29/07/1988"),
                        new Phone("92264773"),
                        new Email("bilesh@example.com"),
                        new Address("Blk 48 Lorong 10 Caldecott, #07-02"),
                        getTagSet("family"),
                        new HashSet<>(),
                        new DateAdded("29/10/2017 12:00:01")),

                new Person(new Name("Linn Mcewen"),
                        new Birthday("10/10/1966"),
                        new Phone("93045568"),
                        new Email("mcewenlinn@example.com"),
                        new Address("Blk 17 Boon Lay Street 29, #13-32"),
                        getTagSet("profs"),
                        new HashSet<>(),
                        new DateAdded("01/11/2017 12:01:01")),

                new Person(new Name("Melvin Sigmund"),
                        new Birthday("02/09/1995"),
                        new Phone("66083995"),
                        new Email("melvins@example.com"),
                        new Address("Blk 29 Aljunied Street 76, #09-25"),
                        getTagSet("colleagues"),
                        new HashSet<>(),
                        new DateAdded("05/11/2017 12:00:01"))
            };

            ArrayList<ReadOnlyPerson> eventPersonOne = new ArrayList<>();
            eventPersonOne.add(persons[0]);
            eventPersonOne.add(persons[10]);
            eventPersonOne.add(persons[16]);
            EventName eventNameOne = new EventName("CS2103T Exam");
            EventTime eventTimeOne = new EventTime(LocalDateTime.of(LocalDate.of(2017, 12, 04),
                    LocalTime.of(10, 00, 00, 00)), Duration.ofMinutes(120));
            EventDuration eventDurationOne = new EventDuration(Duration.ofMinutes(120));
            Event eventOne = new Event(new MemberList(eventPersonOne), eventNameOne, eventTimeOne, eventDurationOne);

            ArrayList<ReadOnlyPerson> eventPersonTwo = new ArrayList<>();
            eventPersonTwo.add(persons[1]);
            eventPersonTwo.add(persons[6]);
            eventPersonTwo.add(persons[7]);
            eventPersonTwo.add(persons[17]);
            EventName eventNameTwo = new EventName("Meeting for Project Demo");
            EventTime eventTimeTwo = new EventTime(LocalDateTime.of(LocalDate.of(2017, 10, 24),
                    LocalTime.of(9, 00, 00, 00)), Duration.ofMinutes(60));
            EventDuration eventDurationTwo = new EventDuration(Duration.ofMinutes(60));
            Event eventTwo = new Event(new MemberList(eventPersonTwo), eventNameTwo, eventTimeTwo, eventDurationTwo);

            ArrayList<ReadOnlyPerson> eventPersonThree = new ArrayList<>();
            eventPersonThree.add(persons[3]);
            eventPersonThree.add(persons[9]);
            eventPersonThree.add(persons[12]);
            eventPersonThree.add(persons[15]);
            eventPersonThree.add(persons[18]);
            eventPersonThree.add(persons[21]);
            EventName eventNameThree = new EventName("Family Gathering and Outing");
            EventTime eventTimeThree = new EventTime(LocalDateTime.of(LocalDate.of(2017, 12, 16),
                    LocalTime.of(17, 00, 00, 00)), Duration.ofMinutes(180));
            EventDuration eventDurationThree = new EventDuration(Duration.ofMinutes(180));
            Event eventThree = new Event(new MemberList(eventPersonThree), eventNameThree, eventTimeThree,
                    eventDurationThree);

            ArrayList<ReadOnlyPerson> eventPersonFour = new ArrayList<>();
            eventPersonFour.add(persons[0]);
            eventPersonFour.add(persons[1]);
            eventPersonFour.add(persons[10]);
            eventPersonFour.add(persons[16]);
            eventPersonFour.add(persons[19]);
            eventPersonFour.add(persons[23]);
            EventName eventNameFour = new EventName("Matchmaking Event");
            EventTime eventTimeFour = new EventTime(LocalDateTime.of(LocalDate.of(2017, 9, 23),
                    LocalTime.of(18, 30, 00, 00)), Duration.ofMinutes(90));
            EventDuration eventDurationFour = new EventDuration(Duration.ofMinutes(90));
            Event eventFour = new Event(new MemberList(eventPersonFour), eventNameFour, eventTimeFour,
                    eventDurationFour);

            HashSet<Event> eventsOne = new HashSet<>();
            eventsOne.add(eventOne);
            eventsOne.add(eventFour);
            persons[0].setEvents(eventsOne);

            HashSet<Event> eventsTwo = new HashSet<>();
            eventsTwo.add(eventTwo);
            eventsTwo.add(eventFour);
            persons[1].setEvents(eventsTwo);

            HashSet<Event> eventsThree = new HashSet<>();
            //eventsThree.add();
            persons[2].setEvents(eventsThree);

            HashSet<Event> eventsFour = new HashSet<>();
            eventsFour.add(eventThree);
            persons[3].setEvents(eventsFour);

            HashSet<Event> eventsFive = new HashSet<>();
            //eventsFive.add();
            persons[4].setEvents(eventsFive);

            HashSet<Event> eventsSix = new HashSet<>();
            //eventsSix.add();
            persons[5].setEvents(eventsSix);

            HashSet<Event> eventsSeven = new HashSet<>();
            eventsSeven.add(eventTwo);
            persons[6].setEvents(eventsSeven);

            HashSet<Event> eventsEight = new HashSet<>();
            eventsEight.add(eventTwo);
            persons[7].setEvents(eventsEight);

            HashSet<Event> eventsNine = new HashSet<>();
            //eventsNine.add();
            persons[8].setEvents(eventsNine);

            HashSet<Event> eventsTen = new HashSet<>();
            eventsTen.add(eventThree);
            persons[9].setEvents(eventsTen);

            HashSet<Event> eventsEleven = new HashSet<>();
            eventsEleven.add(eventOne);
            eventsEleven.add(eventFour);
            persons[10].setEvents(eventsEleven);

            HashSet<Event> eventsTwelve = new HashSet<>();
            //eventsTwelve.add();
            persons[11].setEvents(eventsTwelve);

            HashSet<Event> eventsThirteen = new HashSet<>();
            eventsThirteen.add(eventThree);
            persons[12].setEvents(eventsThirteen);

            HashSet<Event> eventsFourteen = new HashSet<>();
            //eventsFourteen.add();
            persons[13].setEvents(eventsFourteen);

            HashSet<Event> eventsFifteen = new HashSet<>();
            //eventsFifteen.add();
            persons[14].setEvents(eventsFifteen);

            HashSet<Event> eventsSixteen = new HashSet<>();
            eventsSixteen.add(eventThree);
            persons[15].setEvents(eventsSixteen);

            HashSet<Event> eventsSeventeen = new HashSet<>();
            eventsSeventeen.add(eventOne);
            eventsSeventeen.add(eventFour);
            persons[16].setEvents(eventsSeventeen);

            HashSet<Event> eventsEighteen = new HashSet<>();
            eventsEighteen.add(eventTwo);
            persons[17].setEvents(eventsEighteen);

            HashSet<Event> eventsNineteen = new HashSet<>();
            eventsNineteen.add(eventThree);
            persons[18].setEvents(eventsNineteen);

            HashSet<Event> eventsTwenty = new HashSet<>();
            eventsTwenty.add(eventFour);
            persons[19].setEvents(eventsTwenty);

            HashSet<Event> eventsTwoOne = new HashSet<>();
            //eventsTwoOne.add();
            persons[20].setEvents(eventsTwoOne);

            HashSet<Event> eventsTwoTwo = new HashSet<>();
            eventsTwoTwo.add(eventThree);
            persons[21].setEvents(eventsTwoTwo);

            HashSet<Event> eventsTwoThree = new HashSet<>();
            //eventsTwoThree.add();
            persons[22].setEvents(eventsTwoThree);

            HashSet<Event> eventsTwoFour = new HashSet<>();
            eventsTwoFour.add(eventFour);
            persons[23].setEvents(eventsTwoFour);

            return persons;
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
