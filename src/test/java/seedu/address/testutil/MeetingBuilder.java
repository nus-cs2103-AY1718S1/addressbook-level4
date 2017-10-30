package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.meeting.DateTime;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.NameMeeting;
import seedu.address.model.meeting.PersonToMeet;
import seedu.address.model.meeting.PhoneNum;
import seedu.address.model.meeting.Place;
import seedu.address.model.meeting.ReadOnlyMeeting;

/**
 * A utility class to help with building Meeting objects.
 */
public class MeetingBuilder {

    public static final String DEFAULT_NAMEMEETING = "Project Meeting";
    public static final String DEFAULT_DATETIME = "31-10-2017 21:30";
    public static final String DEFAULT_PLACE = "School of Computing";
    public static final String DEFAULT_PERSONTOMEET = "Alex Yeoh";
    public static final String DEFAULT_PHONENUM = "87438807";

    private Meeting meeting;

    public MeetingBuilder() {
        try {
            NameMeeting defaultNameMeeting = new NameMeeting(DEFAULT_NAMEMEETING);
            DateTime defaultDateTime = new DateTime(DEFAULT_DATETIME);
            Place defaultPlace = new Place(DEFAULT_PLACE);
            PersonToMeet defaultPersonToMeet = new PersonToMeet(DEFAULT_PERSONTOMEET);
            PhoneNum defaultPhoneNum = new PhoneNum(DEFAULT_PHONENUM);

            this.meeting = new Meeting(defaultNameMeeting, defaultDateTime, defaultPlace, defaultPersonToMeet, defaultPhoneNum);
        } catch (IllegalValueException ive) {
            throw new AssertionError("Default meeting's values are invalid.");
        }
    }

    /**
     * Initializes the MeetingBuilder with the data of {@code meetingToCopy}.
     */
    public MeetingBuilder(ReadOnlyMeeting meetingToCopy) {
        this.meeting = new Meeting(meetingToCopy);
    }

    /**
     * Sets the {@code NameMeeting} of the {@code Meeting} that we are building.
     */
    public MeetingBuilder withNameMeeting(String nameMeeting) {
        try {
            this.meeting.setName(new NameMeeting(nameMeeting));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("name of meeting is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code DateTime} of the {@code Meeting} that we are building.
     */
    public MeetingBuilder withDateTime(String dateTime) {
        try {
            this.meeting.setDateTime(new DateTime(dateTime));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("address is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Place} of the {@code Meeting} that we are building.
     */
    public MeetingBuilder withPlace(String location) {
        try {
            this.meeting.setPlace(new Place(location));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("location is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code PersonName} of the {@code Meeting} that we are building.
     */
    public MeetingBuilder withPersonToMeet(String personToMeet) {
        this.meeting.setPersonName(new PersonToMeet(personToMeet));
        return this;
    }

    /**
     * Sets the {@code PhoneNum} of the {@code Meeting} that we are building.
     */
    public MeetingBuilder withPhoneNum(String phoneNum) {
        this.meeting.setPhoneNum(new PhoneNum(phoneNum));
        return this;
    }


    public Meeting build() {
        return this.meeting;
    }

}
