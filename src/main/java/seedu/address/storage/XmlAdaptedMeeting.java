package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.meeting.Meeting;

//@@author alexanderleegs
/**
 * JAXB-friendly adapted version of the Meeting.
 */
public class XmlAdaptedMeeting implements XmlAdaptedClass<Meeting> {

    @XmlElement
    private String meetingName;
    @XmlElement
    private String meetingTime;

    /**
     * Constructs an XmlAdaptedMeeting.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedMeeting() {}

    /**
     * Converts a given Meeting into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedMeeting(Meeting source) {
        meetingTime = source.value;
        meetingName = source.meetingName;
    }

    /**
     * Converts this jaxb-friendly adapted meeting object into the model's Meeting object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Meeting toModelType() throws IllegalValueException {
        return new Meeting(meetingName, meetingTime);
    }

}
