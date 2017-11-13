package seedu.address.testutil;

import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.meeting.DateTime;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.MeetingTag;
import seedu.address.model.meeting.NameMeeting;
import seedu.address.model.meeting.Place;
import seedu.address.model.meeting.ReadOnlyMeeting;
import seedu.address.model.person.ReadOnlyPerson;


//@@author nelsonqyj
/**
 * A utility class to help with building Meeting objects.
 */
public class MeetingBuilder {

    public static final String DEFAULT_NAMEMEETING = "Project Meeting";
    public static final String DEFAULT_DATETIME = "27-01-2018 21:30";
    public static final String DEFAULT_PLACE = "School of Computing";
    public static final String DEFAULT_TAG = "1";

    private Meeting meeting;

    public MeetingBuilder() {
        try {
            List<Index> indexes = new ArrayList<>();
            indexes.add(Index.fromOneBased(1));
            NameMeeting defaultNameMeeting = new NameMeeting(DEFAULT_NAMEMEETING);
            DateTime defaultDateTime = new DateTime(DEFAULT_DATETIME);
            Place defaultPlace = new Place(DEFAULT_PLACE);
            List<ReadOnlyPerson> defaultPersonsMeet = new ArrayList<>();
            defaultPersonsMeet.add(getTypicalPersons().get(indexes.get(0).getZeroBased()));
            MeetingTag defaultMeetingTag = new MeetingTag(DEFAULT_TAG);

            this.meeting = new Meeting(defaultNameMeeting, defaultDateTime, defaultPlace,
                    defaultPersonsMeet, defaultMeetingTag);
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
    //@@author kyngyi
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
     * Sets the {@code Place} of the {@code Meeting} that we are building.
     */
    public MeetingBuilder withIndex(Index index) {
        try {
            List<ReadOnlyPerson> persons = new ArrayList<>();
            ReadOnlyPerson person = getTypicalPersons().get(0);
            persons.add(person);
            this.meeting.setPersonsMeet(persons);
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Index must be smaller than the size of person list");
        }
        return this;
    }

    public Meeting build() {
        return this.meeting;
    }

}
