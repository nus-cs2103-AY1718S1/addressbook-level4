//@@author PhuaJunJie
package seedu.address.testutil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.api.services.people.v1.model.Address;
import com.google.api.services.people.v1.model.EmailAddress;
import com.google.api.services.people.v1.model.Name;
import com.google.api.services.people.v1.model.Person;
import com.google.api.services.people.v1.model.PhoneNumber;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Email;
import seedu.address.model.person.FacebookAddress;
import seedu.address.model.person.GoogleId;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Builds Stub Google contact and addressbook contacts
 */
public class GoogleContactBuilder {
    private String nameDefault;
    private String phoneDefault;
    private String emailDefault;
    private String addressDefault;
    private String googleIdDefault;

    /**
     * Constructor
     */
    public GoogleContactBuilder(String name, String phone, String email, String address, String googleId) {
        this.nameDefault = name;
        this.phoneDefault = phone;
        this.emailDefault = email;
        this.addressDefault = address;
        this.googleIdDefault = googleId;
    }

    /**
     * Builds Stub Google contact
     */
    public Person buildGooglePerson() {
        Person contactToCreate = new Person();

        List names = new ArrayList();
        List email = new ArrayList();
        List phone = new ArrayList();
        List address = new ArrayList();

        names.add(new Name().setGivenName(nameDefault));
        email.add(new EmailAddress().setValue(emailDefault));
        phone.add(new PhoneNumber().setValue(phoneDefault));
        address.add(new Address().setStreetAddress(addressDefault));

        return contactToCreate.setNames(names).setEmailAddresses(email).setPhoneNumbers(phone)
                .setAddresses(address).setResourceName(googleIdDefault);
    }
    /**
     * Builds Stub Addressbook contact
     */
    public seedu.address.model.person.Person buildAddressBookPerson() {
        seedu.address.model.person.Person person;
        try {
            seedu.address.model.person.Name name = new seedu.address.model.person.Name(nameDefault);
            Phone phone = new Phone(phoneDefault);
            Email email = new Email(emailDefault);
            seedu.address.model.person.Address address = new seedu.address.model.person.Address(addressDefault);
            GoogleId id;
            if (googleIdDefault.length() != 0) {
                id = new GoogleId(googleIdDefault.substring(8));
            } else {
                id = new GoogleId("not GoogleContact");
            }

            Tag tag = new Tag("GoogleContact");
            Set<Tag> tags = new HashSet<>();
            tags.add(tag);
            FacebookAddress facebookAddress = new FacebookAddress("");
            seedu.address.model.person.Birthday birthday = new seedu.address.model.person.Birthday("");
            person = new seedu.address.model.person.Person(name, phone, email, address, birthday, facebookAddress,
                    tags, id);
        } catch (IllegalValueException e) {
            person = null;
        }
        return person;
    }
}
