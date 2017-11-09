package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_ACTIVITY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_BIKING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ACTIVITY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BIKING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PLACE_ACTIVITY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PLACE_BIKING;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.model.AddressBook;
import seedu.address.model.meeting.ReadOnlyMeeting;
import seedu.address.model.meeting.exceptions.DuplicateMeetingException;
import seedu.address.model.meeting.exceptions.MeetingBeforeCurrDateException;
import seedu.address.model.meeting.exceptions.MeetingClashException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
//import seedu.address.model.AddressBook;
//import seedu.address.model.meeting.exceptions.DuplicateMeetingException;
//import seedu.address.model.meeting.exceptions.MeetingBeforeCurrDateException;
//import seedu.address.model.meeting.exceptions.MeetingClashException;

//@@author kyngyi
/**
 * A utility class containing a list of {@code Meeting} objects to be used in tests.
 */
public class TypicalMeetings {
    public static final ReadOnlyMeeting AGEING = new MeetingBuilder().withNameMeeting("Ageing")
            .withDateTime("31-01-2019 00:00").withIndex(Index.fromOneBased(1)).withPlace("Vivocity").build();
    public static final ReadOnlyMeeting BREEDING = new MeetingBuilder().withNameMeeting("Breeding")
            .withDateTime("31-01-2019 00:01").withIndex(Index.fromOneBased(2)).withPlace("West Coast Park").build();
    public static final ReadOnlyMeeting CYCLING = new MeetingBuilder().withNameMeeting("Cycling")
            .withDateTime("01-01-2019 00:02").withIndex(Index.fromOneBased(3)).withPlace("East Coast Park").build();
    public static final ReadOnlyMeeting DIVING = new MeetingBuilder().withNameMeeting("Diving Lesson")
            .withDateTime("01-01-2019 00:03").withIndex(Index.fromOneBased(4))
            .withPlace("Jurong Swimming Complex").build();
    public static final ReadOnlyMeeting EATING = new MeetingBuilder().withNameMeeting("Eating")
            .withDateTime("01-01-2019 00:04").withIndex(Index.fromOneBased(5)).withPlace("TechnoEdge").build();
    public static final ReadOnlyMeeting FENCING = new MeetingBuilder().withNameMeeting("Fencing Class")
            .withDateTime("01-01-2019 00:05").withIndex(Index.fromOneBased(6)).withPlace("NUS").build();
    public static final ReadOnlyMeeting GARDENING = new MeetingBuilder().withNameMeeting("Gardening")
            .withDateTime("01-01-2019 00:06").withIndex(Index.fromOneBased(7)).withPlace("My Backyard").build();

    // Manually added
    //    public static final ReadOnlyMeeting HIKING = new MeetingBuilder().withNameMeeting("Hiking")
    //            .withDateTime("01-01-2019 00:07").withPersonToMeet("Hiker Lo")
    //            .withPhoneNum("97745668").withPlace("Macritchie Reservoir").build();
    //    public static final ReadOnlyMeeting ICESKATING = new MeetingBuilder().withNameMeeting("Ice Skating")
    //            .withDateTime("01-01-2019 00:08").withPersonToMeet("Elsa Koh")
    //            .withPhoneNum("81234567").withPlace("JEM").build();
    //
    // Manually added - Meeting's details found in {@code CommandTestUtil}
    public static final ReadOnlyMeeting ACTIVITY = new MeetingBuilder().withNameMeeting(VALID_NAME_ACTIVITY)
            .withPlace(VALID_PLACE_ACTIVITY).withDateTime(VALID_DATE_ACTIVITY)
            .build();
    public static final ReadOnlyMeeting BIKING = new MeetingBuilder().withNameMeeting(VALID_NAME_BIKING)
            .withPlace(VALID_PLACE_BIKING).withDateTime(VALID_DATE_BIKING).build();


    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalMeetings() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical meetings.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (ReadOnlyPerson person : getTypicalPersons()) {
            try {
                ab.addPerson(person);
            } catch (DuplicatePersonException e) {
                assert false : "not possible";
            }
        }
        for (ReadOnlyMeeting meeting : getTypicalMeetings()) {
            try {
                ab.addMeeting(meeting);
            } catch (DuplicateMeetingException e) {
                assert false : "duplicate not possible";
            } catch (MeetingBeforeCurrDateException mbcde) {
                assert false : "mbcde not possible";
            } catch (MeetingClashException mce) {
                assert false : "mce not possible";
            }
        }
        return ab;
    }

    public static List<ReadOnlyMeeting> getTypicalMeetings() {
        return new ArrayList<>(Arrays.asList(AGEING, BREEDING, CYCLING, DIVING, EATING, FENCING, GARDENING));
    }
}
