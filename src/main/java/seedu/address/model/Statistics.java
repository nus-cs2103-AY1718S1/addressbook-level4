package seedu.address.model;

import java.time.Month;
import java.time.Year;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.SocialMedia;

/**
 * A model for calculating the values for Statistics Panel
 */
public class Statistics {

    private static final Integer NUMBER_OF_PERSONS_IN_TOP_LIST = 10;
    private static final Integer NUMBER_OF_PERSONS_IN_BOTTOM_LIST = 10;

    private ObservableList<ReadOnlyPerson> personList;

    private Integer totalNumberOfPeople = 0;
    private Integer hasNoFacebook = 0;
    private Integer hasNoTwitter = 0;
    private Integer hasNoInstagram = 0;

    private Integer currentYear;
    private Integer currentMonth;

    //@@author 500poundbear
    public Statistics (ObservableList<ReadOnlyPerson> list, int currentMonth, int currentYear) {

        this.currentYear = currentYear;
        this.currentMonth = currentMonth;

        this.personList = list;

        tabulateTotalNumberOfPeople();
        tabulateSocialMediaUsage();
    }

    public ArrayList<Integer> getNewPersonsAddByMonth(int displayYears) {

        ArrayList<Integer> countByMonth = new ArrayList<>(Collections.nCopies(displayYears * 12 + 1, 0));

        personList.forEach((p) -> {
            Date givenDate = p.getCreatedAt();
            ZonedDateTime given = givenDate.toInstant().atZone(ZoneId.of("UTC"));

            int personAddedYear = Integer.parseInt(Year.from(given).toString());
            int personAddedMonth = Month.from(given).getValue();

            int indOffset = calculateCountByMonthOffset(personAddedMonth, personAddedYear);
            if (indOffset >= 0 && indOffset <= displayYears * 12) {
                countByMonth.set(indOffset, countByMonth.get(indOffset) + 1);
            }
        });

        return countByMonth;
    }

    /**
     * Count the offset when adding to the array list of sum by months
     */
    public int calculateCountByMonthOffset(int personAddedMonth, int personAddedYear) {
        return (this.currentYear - personAddedYear) * 12
                + (this.currentMonth - personAddedMonth);
    }

    /**
     * Tabulate the total number of people in the list
     */
    public void tabulateTotalNumberOfPeople() {
        this.totalNumberOfPeople = personList.size();
    }

    /**
     * Tabulates number of users of each social media platform
     */
    public void tabulateSocialMediaUsage() {
        for (ReadOnlyPerson aList : personList) {
            SocialMedia current = aList.getSocialMedia();
            if (current.facebook.isEmpty()) {
                this.hasNoFacebook++;
            }
            if (current.twitter.isEmpty()) {
                this.hasNoTwitter++;
            }
            if (current.instagram.isEmpty()) {
                this.hasNoInstagram++;
            }
        }
    }

    /**
     * Fetches n people with the highest access count
     */
    public List<ReadOnlyPerson> getAllTimeMostAccesses() {
        ArrayList<ReadOnlyPerson> sortedByMostAccesses = new ArrayList<>(personList);
        sortedByMostAccesses.sort(sortByGetAccessCount());

        int startingIndex = this.totalNumberOfPeople - NUMBER_OF_PERSONS_IN_TOP_LIST;
        int endingIndex = this.totalNumberOfPeople - 1;

        return sortedByMostAccesses.subList(startingIndex, endingIndex);
    }

    /**
     * Fetches n people with the lowest access count
     */
    public List<ReadOnlyPerson> getAllTimeLeastAccesses() {
        ArrayList<ReadOnlyPerson> sortedByMostAccesses = new ArrayList<>(personList);
        sortedByMostAccesses.sort(sortByGetAccessCount());

        int startingIndex = 0;
        int endingIndex = NUMBER_OF_PERSONS_IN_BOTTOM_LIST - 1;

        return sortedByMostAccesses.subList(startingIndex, endingIndex);
    }

    /**
     * Fetches number of persons with no facebook information added
     */
    public Integer getHasNoFacebook() {
        return this.hasNoFacebook;
    }

    /**
     * Fetches number of persons with no twitter information added
     */
    public Integer getHasNoTwitter() {
        return this.hasNoTwitter;
    }

    /**
     * Fetches number of persons with no instagram information added
     */
    public Integer getHasNoInstagram() {
        return this.hasNoInstagram;
    }

    /**
     * Fetches total number of persons
     */
    public Integer getTotalNumberOfPeople() {
        return this.totalNumberOfPeople;
    }

    /**
     * Sort by ReadOnlyPerson.getAccessCount()
     * @return
     */
    private static Comparator<ReadOnlyPerson> sortByGetAccessCount() {
        return new Comparator<ReadOnlyPerson>() {
            @Override
            public int compare(ReadOnlyPerson s1, ReadOnlyPerson s2) {
                return s1.getAccessCount().numAccess() - s2.getAccessCount().numAccess();
            }
        };
    }
}
