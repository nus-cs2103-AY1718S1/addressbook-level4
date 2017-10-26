package seedu.address.storage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Meeting;
import seedu.address.model.person.InternalId;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedMeeting {

    @XmlElement(required = true)
    private String dateTime;
    @XmlElement(required = true)
    private String location;
    @XmlElement(required = true)
    private String notes;

    @XmlElement
    private List<Integer> listOfPersonsId = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedMeeting() {}


    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedMeeting(Meeting source) {
        dateTime = source.getDateTime();
        location = source.getLocation();
        notes = source.getNotes();
        listOfPersonsId = new ArrayList<Integer>();
        for (InternalId id : source.getListOfPersonsId()) {
            listOfPersonsId.add(id.getId());
        }
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Meeting toModelType() throws IllegalValueException {
        final ArrayList<InternalId> personIds = new ArrayList<>();
        for (Integer id : listOfPersonsId) {
            personIds.add(new InternalId(id));
        }

        final LocalDateTime dateTime = LocalDateTime.parse(this.dateTime);
        return new Meeting(dateTime, location, notes, personIds);
    }
}
