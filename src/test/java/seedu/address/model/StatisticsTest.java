package seedu.address.model;

import static junit.framework.TestCase.assertEquals;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import javafx.collections.ObservableList;

import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.AddressBookBuilder;

//@@author 500poundbear
/**
 * Tests for Statistics model
 */
public class StatisticsTest {
    private AddressBook addressBook1 = new AddressBookBuilder()
            .withPerson(ALICE)
            .withPerson(BENSON)
            .withPerson(CARL)
            .withPerson(DANIEL)
            .withPerson(ELLE)
            .withPerson(FIONA)
            .withPerson(GEORGE)
            .build();

    private AddressBook addressBook2 = new AddressBookBuilder()
            .withPerson(ELLE)
            .withPerson(FIONA)
            .withPerson(GEORGE)
            .withPerson(ALICE)
            .build();

    private ObservableList<ReadOnlyPerson> allPersonsList1 = addressBook1.getPersonList();
    private ObservableList<ReadOnlyPerson> allPersonsList2 = addressBook2.getPersonList();

    private Statistics statistics;

    @Test
    public void getTotalNumberOfPeopleTest() {

        statistics = new Statistics(allPersonsList1, 12, 2017);
        assertEquals(statistics.getTotalNumberOfPeople().intValue(), 7);

        statistics = new Statistics(allPersonsList2, 12, 2017);
        assertEquals(statistics.getTotalNumberOfPeople().intValue(), 4);
    }

    @Test
    public void getTotalNumberOfNoFacebookRecordsTest() {

        statistics = new Statistics(allPersonsList1, 12, 2015);
        assertEquals(statistics.getHasNoFacebook().intValue(), 3);

        statistics = new Statistics(allPersonsList2, 12, 2015);
        assertEquals(statistics.getHasNoFacebook().intValue(), 2);
    }

    @Test
    public void getTotalNumberOfNoTwitterRecordsTest() {

        statistics = new Statistics(allPersonsList1, 12, 2015);
        assertEquals(statistics.getHasNoTwitter().intValue(), 1);

        statistics = new Statistics(allPersonsList2, 12, 2015);
        assertEquals(statistics.getHasNoTwitter().intValue(), 1);

    }

    @Test
    public void getTotalNumberOfNoInstagramRecordsTest() {

        statistics = new Statistics(allPersonsList1, 12, 2015);
        assertEquals(statistics.getHasNoInstagram().intValue(), 2);

        statistics = new Statistics(allPersonsList2, 12, 2015);
        assertEquals(statistics.getHasNoInstagram().intValue(), 1);

    }

    @Test
    public void getNewPersonsAddByMonthTest() {

        statistics = new Statistics(allPersonsList1, 12, 2017);
        assertEquals(statistics.getNewPersonsAddByMonth(2),
                new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0, 0, 5, 1, 0, 0, 0, 0, 1,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)));

        statistics = new Statistics(allPersonsList1, 6, 2017);
        assertEquals(statistics.getNewPersonsAddByMonth(2),
                new ArrayList<Integer>(Arrays.asList(5, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)));

        statistics = new Statistics(allPersonsList1, 6, 2017);
        assertEquals(statistics.getNewPersonsAddByMonth(0),
                new ArrayList<Integer>(Arrays.asList(5)));


        statistics = new Statistics(allPersonsList2, 12, 2017);
        assertEquals(statistics.getNewPersonsAddByMonth(1),
                new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 1)));

    }

}
