package seedu.address.storage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.DateBorrow;
import seedu.address.model.person.DateRepaid;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.Debt;
import seedu.address.model.person.Email;
import seedu.address.model.person.Handphone;
import seedu.address.model.person.HomePhone;
import seedu.address.model.person.Interest;
import seedu.address.model.person.Name;
import seedu.address.model.person.OfficePhone;
import seedu.address.model.person.Person;
import seedu.address.model.person.PostalCode;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.DateUtil;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedPerson {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String handphone;
    @XmlElement(required = true)
    private String homePhone;
    @XmlElement(required = true)
    private String officePhone;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private String address;
    @XmlElement(required = true)
    private String postalCode;
    @XmlElement (required = true)
    private String debt;
    @XmlElement (required = true)
    private String totalDebt;
    @XmlElement (required = true)
    private String interest;
    @XmlElement (required = true)
    private String dateBorrow;
    @XmlElement (required = true)
    private String deadline;
    @XmlElement (required = true)
    private String dateRepaid;
    @XmlElement (required = true)
    private String lastAccruedDate;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

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
        handphone = source.getHandphone().value;
        homePhone = source.getHomePhone().value;
        officePhone = source.getOfficePhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        postalCode = source.getPostalCode().value;
        interest = source.getInterest().value;
        debt = source.getDebt().toString();
        totalDebt = source.getTotalDebt().toString();
        dateBorrow = source.getDateBorrow().value;
        deadline = source.getDeadline().value;
        dateRepaid = source.getDateRepaid().value;
        lastAccruedDate = DateUtil.formatDate(source.getLastAccruedDate());
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
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
        final Handphone handphone = new Handphone(this.handphone);
        final HomePhone homePhone = new HomePhone(this.homePhone);
        final OfficePhone officePhone = new OfficePhone(this.officePhone);
        final Email email = new Email(this.email);
        final Address address = new Address(this.address);
        final PostalCode postalCode = new PostalCode(this.postalCode);
        final Debt debt = new Debt(this.debt);
        final Debt totalDebt = new Debt(this.totalDebt);
        final Interest interest = new Interest(this.interest);
        final DateBorrow dateBorrow = new DateBorrow(this.dateBorrow);
        final Deadline deadline = new Deadline(this.deadline);
        final DateRepaid dateRepaid = new DateRepaid(this.dateRepaid);
        final Date lastAccruedDate = DateUtil.convertStringToDate(this.lastAccruedDate);
        final Set<Tag> tags = new HashSet<>(personTags);
      
        Person adaptedPerson = new Person(name, handphone, homePhone, officePhone, email, address, postalCode, debt,
                interest, deadline, tags);
        adaptedPerson.setTotalDebt(totalDebt);
        adaptedPerson.setDateBorrow(dateBorrow);
        adaptedPerson.setDateRepaid(dateRepaid);
        adaptedPerson.setLastAccruedDate(lastAccruedDate);
        return adaptedPerson;
    }
}
