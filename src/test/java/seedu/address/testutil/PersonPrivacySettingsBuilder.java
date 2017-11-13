package seedu.address.testutil;

//@@author jeffreygohkw
import seedu.address.logic.commands.ChangePrivacyCommand.PersonPrivacySettings;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * A utility class to help with building PersonPrivacySettings objects.
 */
public class PersonPrivacySettingsBuilder {

    private PersonPrivacySettings pps;
    public PersonPrivacySettingsBuilder() {
        pps = new PersonPrivacySettings();
    }

    public PersonPrivacySettingsBuilder(PersonPrivacySettings pps) {
        this.pps = new PersonPrivacySettings(pps);
    }

    /**
     * Returns an {@code PersonPrivacySettings} with fields containing {@code person}'s privacy details
     */
    public PersonPrivacySettingsBuilder(ReadOnlyPerson person) {
        pps = new PersonPrivacySettings();
        pps.setNameIsPrivate(person.getName().getIsPrivate());
        pps.setPhoneIsPrivate(person.getPhone().getIsPrivate());
        pps.setEmailIsPrivate(person.getEmail().getIsPrivate());
        pps.setAddressIsPrivate(person.getAddress().getIsPrivate());
        pps.setRemarkIsPrivate(person.getRemark().getIsPrivate());
    }

    /**
     * Sets the {@code nameIsPrivate} of the {@code PersonPrivacySettings} that we are building.
     */
    public PersonPrivacySettingsBuilder setNamePrivate(String name) {
        if (name.equals("Optional[true]") || name.equals("true")) {
            pps.setNameIsPrivate(true);
        } else if (name.equals("Optional[false]") || name.equals("false")) {
            pps.setNameIsPrivate(false);
        } else {
            throw new IllegalArgumentException("Privacy of name should be true or false");
        }
        return this;
    }

    /**
     * Sets the {@code phoneIsPrivate} of the {@code PersonPrivacySettings} that we are building.
     */
    public PersonPrivacySettingsBuilder setPhonePrivate(String phone) {
        if (phone.equals("Optional[true]") || phone.equals("true")) {
            pps.setPhoneIsPrivate(true);
        } else if (phone.equals("Optional[false]") || phone.equals("false")) {
            pps.setPhoneIsPrivate(false);
        } else {
            throw new IllegalArgumentException("Privacy of phone should be true or false");
        }
        return this;
    }

    /**
     * Sets the {@code emailIsPrivate} of the {@code PersonPrivacySettings} that we are building.
     */
    public PersonPrivacySettingsBuilder setEmailPrivate(String email) {
        if (email.equals("Optional[true]") || email.equals("true")) {
            pps.setEmailIsPrivate(true);
        } else if (email.equals("Optional[false]") || email.equals("false")) {
            pps.setEmailIsPrivate(false);
        } else {
            throw new IllegalArgumentException("Privacy of email should be true or false");
        }
        return this;
    }

    /**
     * Sets the {@code addressIsPrivate} of the {@code PersonPrivacySettings} that we are building.
     */
    public PersonPrivacySettingsBuilder setAddressPrivate(String address) {
        if (address.equals("Optional[true]") || address.equals("true")) {
            pps.setAddressIsPrivate(true);
        } else if (address.equals("Optional[false]") || address.equals("false")) {
            pps.setAddressIsPrivate(false);
        } else {
            throw new IllegalArgumentException("Privacy of address should be true or false");
        }
        return this;
    }

    /**
     * Sets the {@code remarkIsPrivate} of the {@code PersonPrivacySettings} that we are building.
     */
    public PersonPrivacySettingsBuilder setRemarkPrivate(String remark) {
        if (remark.equals("Optional[true]") || remark.equals("true")) {
            pps.setRemarkIsPrivate(true);
        } else if (remark.equals("Optional[false]") || remark.equals("false")) {
            pps.setRemarkIsPrivate(false);
        } else {
            throw new IllegalArgumentException("Privacy of remark should be true or false");
        }
        return this;
    }

    public PersonPrivacySettings build() {
        return pps;
    }
}
