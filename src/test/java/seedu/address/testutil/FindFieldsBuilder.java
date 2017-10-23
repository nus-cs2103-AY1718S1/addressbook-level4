package seedu.address.testutil;

import java.util.Arrays;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.person.PersonContainsKeywordsPredicate.FindFields;

/**
 * A utility class to help with building FindFields objects.
 */
public class FindFieldsBuilder {

    private FindFields fieldsToFind;

    public FindFieldsBuilder() {
        fieldsToFind = new FindFields();
    }

    public FindFieldsBuilder(FindFields fieldsToFind) {
        this.fieldsToFind = fieldsToFind;
    }

    /**
     * Sets the {@code Name} of the {@code FindFields} that we are building.
     */
    public FindFieldsBuilder withName(String... names) {
        try {
            fieldsToFind.setNameKeywords(ParserUtil.parseNames(Arrays.asList(names)));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("names are expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code FindFields} that we are building.
     */
    public FindFieldsBuilder withPhone(String... phones) {
        try {
            fieldsToFind.setPhoneKeywords(ParserUtil.parsePhones(Arrays.asList(phones)));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("phone is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code FindFields} that we are building.
     */
    public FindFieldsBuilder withEmail(String... emails) {
        try {
            fieldsToFind.setEmailKeywords(ParserUtil.parseEmails(Arrays.asList(emails)));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("email is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code FindFields} that we are building.
     */
    public FindFieldsBuilder withAddress(String... addresses) {
        try {
            fieldsToFind.setAddressKeywords(ParserUtil.parseAddresses(Arrays.asList(addresses)));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("address is expected to be unique.");
        }
        return this;
    }

    public FindFields build() {
        return fieldsToFind;
    }
}
