package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.module.Module;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Photo;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedPerson {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private List<XmlAdaptedPhone> phones = new ArrayList<>();
    //@@author viviantan95
    @XmlElement(required = true)
    private String birthday;
    //@@author
    @XmlElement(required = true)
    private List<XmlAdaptedEmail> emails = new ArrayList<>();
    @XmlElement(required = true)
    private String address;
    //@@author viviantan95
    @XmlElement(required = true)
    private String photo;
    //@@author

    @XmlElement
    private List<XmlAdaptedModule> modules = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPerson() {}


    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedPerson(ReadOnlyPerson source) {
        name = source.getName().fullName;
        // @@author ahmadalkaff
        phones = new ArrayList<>();
        for (Phone phone : source.getPhones()) {
            phones.add(new XmlAdaptedPhone(phone));
        }
        //@@author viviantan95
        birthday = source.getBirthday().value;
        //@@author ahmadalkaff
        emails = new ArrayList<>();
        for (Email email : source.getEmails()) {
            emails.add(new XmlAdaptedEmail(email));
        }
        // @@author
        address = source.getAddress().value;
        //@@author viviantan95
        photo = source.getPhoto().value;
        //@@author
        modules = new ArrayList<>();
        for (Module mod : source.getModules()) {
            modules.add(new XmlAdaptedModule(mod));
        }
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Person toModelType() throws IllegalValueException {
        // @@author ahmadalkaff
        final List<Phone> personPhones = new ArrayList<>();
        for (XmlAdaptedPhone phone : phones) {
            personPhones.add(phone.toModelType());
        }

        final List<Email> personEmails = new ArrayList<>();
        for (XmlAdaptedEmail email : emails) {
            personEmails.add(email.toModelType());
        }
        // @@author

        final List<Module> personModules = new ArrayList<>();
        for (XmlAdaptedModule mod : modules) {
            personModules.add(mod.toModelType());
        }

        final Name name = new Name(this.name);
        final Set<Phone> phones = new HashSet<>(personPhones);
        //@@author viviantan95
        final Birthday birthday = new Birthday(this.birthday);
        //@@author
        final Set<Email> emails = new HashSet<>(personEmails);
        final Address address = new Address(this.address);
        //@@author viviantan95
        final Photo photo = new Photo(this.photo);
        //@@author
        final Set<Module> modules = new HashSet<>(personModules);
        return new Person(name, phones, birthday, emails, address, photo, modules);
    }
}
