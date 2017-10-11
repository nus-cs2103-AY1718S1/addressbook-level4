package seedu.address.storage;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
//import java.util.HashSet;
import java.util.List;
//import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.Name;
import seedu.address.model.meeting.Place;
import seedu.address.model.meeting.ReadOnlyMeeting;

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
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedMeeting.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedMeeting() {}


    /**
     * Converts a given Meeting into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedMeeting
     */
    public XmlAdaptedMeeting(ReadOnlyMeeting source) {
        name = source.getName().fullName;
        place = source.getPlace().value;
        date = source.getDate().toString(); //.value
    }


    /**
     * Converts this jaxb-friendly adapted meeting object into the model's Meeting object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted meeting
     */
    public Meeting toModelType() throws IllegalValueException {

        final Name name = new Name(this.name);
        final Place place = new Place(this.place);
        //convert String date to Long Date
        DateFormat df = new SimpleDateFormat("ddMMyyyy HH:mm");
        Date newDate = new Date();
        try {
            newDate = df.parse(this.date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        final Date date = newDate;

        return new Meeting(name, date, place);
    }

}
