package seedu.address.testutil;

import seedu.address.model.meeting.ReadOnlyMeeting;

public class TypicalMeetings {
    public static final ReadOnlyMeeting ACTIVITY = new MeetingBuilder().withNameMeeting("Activity")
            .withDateTime("01-01-2018 00:00").withPersonToMeet("Alice Tan")
            .withPhoneNum("85355255").withPlace("Vivocity").build();
    public static final ReadOnlyMeeting BIKING = new MeetingBuilder().withNameMeeting("Biking")
            .withDateTime("01-01-2018 00:01").withPersonToMeet("John Tan")
            .withPhoneNum("97788542").withPlace("West Coast Park").build();
    public static final ReadOnlyMeeting CYCLING = new MeetingBuilder().withNameMeeting("Cycling")
            .withDateTime("01-01-2018 00:02").withPersonToMeet("Bob Ng")
            .withPhoneNum("97452541").withPlace("East Coast Park").build();
    public static final ReadOnlyMeeting DIVING = new MeetingBuilder().withNameMeeting("Diving Lesson")
            .withDateTime("01-01-2018 00:03").withPersonToMeet("Charles Wong")
            .withPhoneNum("84562351").withPlace("Jurong Swimming Complex").build();
    public static final ReadOnlyMeeting EATING = new MeetingBuilder().withNameMeeting("Eating")
            .withDateTime("01-01-2018 00:04").withPersonToMeet("Eaton Lo")
            .withPhoneNum("97745253").withPlace("TechnoEdge").build();
    public static final ReadOnlyMeeting FENCING = new MeetingBuilder().withNameMeeting("Fencing Class")
            .withDateTime("01-01-2018 00:05").withPersonToMeet("Tristan Lim")
            .withPhoneNum("97745256").withPlace("NUS").build();
    public static final ReadOnlyMeeting GARDENING = new MeetingBuilder().withNameMeeting("Gardening")
            .withDateTime("01-01-2018 00:06").withPersonToMeet("Trist Gardener")
            .withPhoneNum("97745253").withPlace("My Backyard").build();

    // Manually added
    public static final ReadOnlyMeeting HIKING = new MeetingBuilder().withNameMeeting("Hiking")
            .withDateTime("01-01-2018 00:07").withPersonToMeet("Hiker Lo")
            .withPhoneNum("97745668").withPlace("Macritchie Reservoir").build();
    public static final ReadOnlyMeeting ICESKATING = new MeetingBuilder().withNameMeeting("Ice Skating")
            .withDateTime("01-01-2018 00:08").withPersonToMeet("Elsa Koh")
            .withPhoneNum("81234567").withPlace("JEM").build();

    // Manually added - Meeting's details found in {@code CommandTestUtil}
    public static final ReadOnlyMeeting AMY = new MeetingBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final ReadOnlyMeeting BOB = new MeetingBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalMeetings() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical meetings.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (ReadOnlyMeeting meeting : getTypicalMeetings()) {
            try {
                ab.addMeeting(meeting);
            } catch (DuplicateMeetingException e) {
                assert false : "not possible";
            }
        }
        return ab;
    }

    public static List<ReadOnlyMeeting> getTypicalMeetings() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}