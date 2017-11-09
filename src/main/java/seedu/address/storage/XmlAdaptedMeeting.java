package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

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
 * JAXB-friendly version of the Meeting.
 */
public class XmlAdaptedMeeting {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String place;
    @XmlElement(required = true)
    private String date;
    @XmlElement
    private List<XmlAdaptedPerson> persons = new ArrayList<>();
    @XmlElement(required = true)
    private String meetTag;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedMeeting.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedMeeting() {}


    //@@author Melvin-leo
    /**
     * Converts a given Meeting into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedMeeting
     */
    public XmlAdaptedMeeting(ReadOnlyMeeting source) {
        name = source.getName().fullName;
        place = source.getPlace().value;
        date = source.getDate().toString();
        persons = new ArrayList<>();
        for (ReadOnlyPerson person: source.getPersonsMeet()) {
            persons.add(new XmlAdaptedPerson(person));
        }
        meetTag = source.getMeetTag().toString();
    }

    //@@author nelsonqyj
    /**
     * Converts this jaxb-friendly adapted meeting object into the model's Meeting object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted meeting
     */
    public Meeting toModelType() throws IllegalValueException {

        final NameMeeting name = new NameMeeting(this.name);
        final DateTime dateTime = new DateTime(this.date);
        final Place place = new Place(this.place);
        final List<ReadOnlyPerson> personsMeet = new ArrayList<>();
        for (XmlAdaptedPerson person: this.persons) {
            personsMeet.add(person.toModelType());
        }
        final MeetingTag meetTag = new MeetingTag(this.meetTag);

        return new Meeting(name, dateTime, place, personsMeet, meetTag);
    }

}
