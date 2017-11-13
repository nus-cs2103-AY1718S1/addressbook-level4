package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthdate;
import seedu.address.model.person.EmailAddress;
import seedu.address.model.person.Name;
import seedu.address.model.person.NusModules;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Photo;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UserId;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedPerson {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String emailAddress;
    @XmlElement(required = true)
    private String address;
    @XmlElement(required = true)
    private String photo;
    @XmlElement(required = true)
    private String birthdate;
    @XmlElement(required = true)
    private String id;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    @XmlElement(name = "nusModule")
    private List<XmlAdaptedNusModule> nusModules = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPerson() {
    }


    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedPerson(ReadOnlyPerson source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        emailAddress = source.getEmailAddress().value;
        address = source.getAddress().value;
        photo = source.getPhoto().value;
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
        birthdate = source.getBirthdate().value;
        nusModules = getArrayListOfXmlAdaptedNusModule(source);
        id = source.getUserId().value;
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }
        final Name name = new Name(this.name);
        final Phone phone = new Phone(this.phone);
        final EmailAddress email = new EmailAddress(this.emailAddress);
        final Address address = new Address(this.address);
        final Photo photo = new Photo(this.photo, 0);
        final Set<Tag> tags = new HashSet<>(personTags);
        final Birthdate birthdate = new Birthdate(this.birthdate);
        final NusModules personNusModules = getPersonNusModules();
        final UserId id = new UserId(this.id);

        return new Person(name, phone, email, address, photo, tags, birthdate, personNusModules, id);

    }

    //@@author ritchielq
    /**
     * Processes ArrayList<XmlAdaptedNusModule/> nusModules and returns NusModule object
     *
     * @return personNusModules
     * @throws IllegalValueException
     */
    private NusModules getPersonNusModules() throws IllegalValueException {
        NusModules personNusModules = new NusModules();
        if (!nusModules.isEmpty()) {
            for (XmlAdaptedNusModule nusModule : nusModules) {
                for (Map.Entry<String, HashMap<String, String>> mod : nusModule.toNusModulesModelEntry().entrySet()) {
                    personNusModules = personNusModules.addModule(mod.getKey(), mod.getValue());
                }
            }
        }
        return personNusModules;
    }

    /**
     * Processes person's NusModule and return it in ArrayList<XmlAdaptedNusModule/> format
     *
     * @param source
     * @return xmlAdaptedNusModulesArrayList
     */
    private ArrayList<XmlAdaptedNusModule> getArrayListOfXmlAdaptedNusModule(ReadOnlyPerson source) {
        ArrayList<XmlAdaptedNusModule> xmlAdaptedNusModulesArrayList = new ArrayList<>();
        if (source.getNusModules() != null) {
            for (Map.Entry<String, HashMap<String, String>> module : source.getNusModules().value.entrySet()) {
                xmlAdaptedNusModulesArrayList.add(new XmlAdaptedNusModule(module));
            }
        }
        return xmlAdaptedNusModulesArrayList;
    }
}
