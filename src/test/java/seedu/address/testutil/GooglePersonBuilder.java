//@@author danielbrzn
package seedu.address.testutil;

import java.util.ArrayList;
import java.util.List;

import com.google.api.services.people.v1.model.Address;
import com.google.api.services.people.v1.model.Birthday;
import com.google.api.services.people.v1.model.Date;
import com.google.api.services.people.v1.model.EmailAddress;
import com.google.api.services.people.v1.model.Name;
import com.google.api.services.people.v1.model.PhoneNumber;


/**
 * A utility class to help with building Google Person objects.
 */
public class GooglePersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_BIRTHDAY_DAY = "3";
    public static final String DEFAULT_BIRTHDAY_MONTH = "7";
    public static final String DEFAULT_BIRTHDAY_YEAR = "1990";
    public static final String DEFAULT_TAGS = "Google";

    private com.google.api.services.people.v1.model.Person person;


    public GooglePersonBuilder() {

        ArrayList<com.google.api.services.people.v1.model.Name> names = new ArrayList<>();
        ArrayList<PhoneNumber> phone = new ArrayList<>();
        ArrayList<EmailAddress> emails = new ArrayList<>();
        ArrayList<com.google.api.services.people.v1.model.Address> addresses = new ArrayList<>();
        ArrayList<Birthday> birthday = new ArrayList<>();

        names.add(new com.google.api.services.people.v1.model.Name().setDisplayName(DEFAULT_NAME));
        phone.add(new PhoneNumber().setCanonicalForm(DEFAULT_PHONE));
        emails.add(new EmailAddress().setValue(DEFAULT_EMAIL));
        addresses.add(new com.google.api.services.people.v1.model.Address().setFormattedValue(DEFAULT_ADDRESS));
        Birthday convertedBirthday = new Birthday();
        convertedBirthday.setDate(new Date().setDay(Integer.parseInt(DEFAULT_BIRTHDAY_DAY))
                .setMonth(Integer.parseInt(DEFAULT_BIRTHDAY_MONTH))
                .setYear(Integer.parseInt(DEFAULT_BIRTHDAY_YEAR)));
        birthday.add(convertedBirthday);

        this.person = new com.google.api.services.people.v1.model.Person().setNames(names).setAddresses(addresses)
                .setEmailAddresses(emails).setPhoneNumbers(phone).setBirthdays(birthday);

    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public GooglePersonBuilder withName(String name) {
        List<Name> names = new ArrayList<>();
        names.add(new com.google.api.services.people.v1.model.Name().setDisplayName(name));
        this.person.setNames(names);

        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public GooglePersonBuilder withAddress(String address) {
        List<Address> addresses = new ArrayList<>();
        addresses.add(new com.google.api.services.people.v1.model.Address().setFormattedValue(address));
        this.person.setAddresses(addresses);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public GooglePersonBuilder withPhone(String phone) {
        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        phoneNumbers.add(new PhoneNumber().setValue(phone));
        this.person.setPhoneNumbers(phoneNumbers);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public GooglePersonBuilder withEmail(String email) {
        List<EmailAddress> emails = new ArrayList<>();
        emails.add(new EmailAddress().setValue(email));
        this.person.setEmailAddresses(emails);
        return this;
    }
    /**
     * Sets the {@code Birthday} of the {@code Person} that we are building.
     */
    public GooglePersonBuilder withBirthday(String birthday) {
        List<Birthday> birthdays = new ArrayList<>();
        birthdays.add(new com.google.api.services.people.v1.model.Birthday().setText(birthday));
        this.person.setBirthdays(birthdays);

        return this;
    }

    /**
     * Empties the list of {@code Email} of the {@code Person} that we are building.
     */
    public GooglePersonBuilder noEmail() {
        this.person.setEmailAddresses(new ArrayList<>());
        return this;
    }

    /**
     * Empties the list of {@code PhoneNumber} of the {@code Person} that we are building.
     */
    public GooglePersonBuilder noPhone() {
        this.person.setPhoneNumbers(new ArrayList<>());
        return this;
    }

    /**
     * Empties the list of {@code Name} of the {@code Person} that we are building.
     */
    public GooglePersonBuilder noName() {
        this.person.setNames(new ArrayList<>());
        return this;
    }

    /**
     * Empties the list of {@code Address} of the {@code Person} that we are building.
     */
    public GooglePersonBuilder noAddress() {
        this.person.setAddresses(new ArrayList<>());
        return this;
    }

    /**
     * Empties the list of {@code Birthday} of the {@code Person} that we are building.
     */
    public GooglePersonBuilder noBirthday() {
        this.person.setBirthdays(new ArrayList<>());
        return this;
    }
    public com.google.api.services.people.v1.model.Person build() {
        return this.person;
    }

}
