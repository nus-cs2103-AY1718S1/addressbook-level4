package seedu.address.model;

import java.time.Month;
import java.time.Year;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import javafx.collections.ObservableList;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.SocialMedia;

//@@author 500poundbear
/**
 * A model for calculating the values for Statistics Panel
 */
public class Statistics {

    private static final String ZONE_ID = "UTC";

    private ObservableList<ReadOnlyPerson> personList;

    private Integer totalNumberOfPeople = 0;
    private Integer hasNoFacebook = 0;
    private Integer hasNoTwitter = 0;
    private Integer hasNoInstagram = 0;

    private Integer currentYear;
    private Integer currentMonth;

    public Statistics (ObservableList<ReadOnlyPerson> list, int currentMonth, int currentYear) {
        setStatisticAttributes(list, currentMonth, currentYear);
        tabulateSocialMediaUsage();
    }

    /**
     * Returns Array for each month in past displayYears with a count of new persons added in that month
     * Current month is also included
     *
     * @param displayYears The number of years to be displayed
     * @return ArrayList of integers
     */
    public ArrayList<Integer> getNewPersonsAddByMonth(int displayYears) {
        int totalMonthsDisplayed = yearsToMonth(displayYears) + 1;

        ArrayList<Integer> countByMonth = new ArrayList<>(
                Collections.nCopies(totalMonthsDisplayed, 0));

        personList.forEach(p -> {
            Date givenDate = p.getCreatedAt();
            ZonedDateTime given = givenDate.toInstant().atZone(ZoneId.of(ZONE_ID));

            int personAddedYear = Integer.parseInt(Year.from(given).toString());
            int personAddedMonth = Month.from(given).getValue();

            int indOffset = calculateCountByMonthOffset(personAddedMonth, personAddedYear);

            if (isOffsetWithinDisplayRange(indOffset, totalMonthsDisplayed)) {
                countByMonth.set(indOffset, countByMonth.get(indOffset) + 1);
            }
        });

        return countByMonth;
    }

    /**
     * Fetches number of persons with no facebook information added
     *
     * @return Number of Persons with no facebook
     */
    public Integer getHasNoFacebook() {
        return this.hasNoFacebook;
    }

    /**
     * Fetches number of persons with no twitter information added
     *
     * @return Number of Persons with no twitter
     */
    public Integer getHasNoTwitter() {
        return this.hasNoTwitter;
    }

    /**
     * Fetches number of persons with no instagram information added
     *
     * @return Number of Persons with no twitter
     */
    public Integer getHasNoInstagram() {
        return this.hasNoInstagram;
    }

    /**
     * Fetches total number of persons
     *
     * @return Number of Persons added
     */
    public Integer getTotalNumberOfPeople() {
        return this.totalNumberOfPeople;
    }

    /**
     * Set the initialisation parameters into the Statistics instance attributes
     */
    private void setStatisticAttributes(ObservableList<ReadOnlyPerson> list, int currentMonth, int currentYear) {
        this.personList = list;
        this.currentMonth = currentMonth;
        this.currentYear = currentYear;

        this.totalNumberOfPeople = personList.size();
    }

    /**
     * Count the offset when adding to the array list of sum by months
     */
    private int calculateCountByMonthOffset(int personAddedMonth, int personAddedYear) {
        return yearsToMonth(this.currentYear - personAddedYear) + (this.currentMonth - personAddedMonth);
    }

    /**
     * Tabulates number of users of each social media platform
     */
    private void tabulateSocialMediaUsage() {
        for (ReadOnlyPerson person : personList) {
            SocialMedia personSocialMedia = person.getSocialMedia();
            if (personSocialMedia.facebook.isEmpty()) {
                this.hasNoFacebook++;
            }
            if (personSocialMedia.twitter.isEmpty()) {
                this.hasNoTwitter++;
            }
            if (personSocialMedia.instagram.isEmpty()) {
                this.hasNoInstagram++;
            }
        }
    }

    /**
     * Sort by ReadOnlyPerson.getAccessCount()
     *
     * @return new comparator
     */
    private static Comparator<ReadOnlyPerson> sortByGetAccessCount() {
        return new Comparator<ReadOnlyPerson>() {
            @Override
            public int compare(ReadOnlyPerson s1, ReadOnlyPerson s2) {
                return s1.getAccessCount().numAccess() - s2.getAccessCount().numAccess();
            }
        };
    }

    /**
     * Converts the number of years into number of months
     *
     * @param years
     * @return Number of months
     */
    private int yearsToMonth(int years) {
        return years * 12;
    }


    /**
     * Returns whether month offset provided is within range of [0, displayYear * 12]
     *
     * @param indOffset Value to be checked
     * @param totalMonthsDisplayed To determine maximum bound of offset accepted
     * @return Boolean whether offset is accepted or not
     */
    private boolean isOffsetWithinDisplayRange(int indOffset, int totalMonthsDisplayed) {
        return (indOffset >= 0) && (indOffset < totalMonthsDisplayed);
    }
}
