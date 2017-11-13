package seedu.address.testutil;

import java.util.Arrays;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.person.PersonContainsKeywordsPredicate.FindFields;

//@@author sofarsophie
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
     * Sets the {@code nameKeywords} list of the {@code FindFields} that we are building.
     */
    public FindFieldsBuilder withName(String... names) {
        try {
            fieldsToFind.setNameKeywords(ParserUtil.parseNames(Arrays.asList(names)));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("invalid name.");
        }
        return this;
    }

    /**
     * Sets the {@code phoneKeywords} list of the {@code FindFields} that we are building.
     */
    public FindFieldsBuilder withPhone(String... phones) {
        try {
            fieldsToFind.setPhoneKeywords(ParserUtil.parsePhones(Arrays.asList(phones)));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("invalid phone.");
        }
        return this;
    }

    /**
     * Sets the {@code emailKeywords} list of the {@code FindFields} that we are building.
     */
    public FindFieldsBuilder withEmail(String emails) {
        try {
            fieldsToFind.setEmailKeywords(ParserUtil.parseEmails(Arrays.asList(emails)));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("invalid email!");
        }
        return this;
    }

    /**
     * Sets the {@code addressKeywords} list of the {@code FindFields} that we are building.
     */
    public FindFieldsBuilder withAddress(String... addresses) {
        try {
            fieldsToFind.setAddressKeywords(ParserUtil.parseAddresses(Arrays.asList(addresses)));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("invalid address.");
        }
        return this;
    }

    /**
     * Sets the {@code groupKeywords} list of the {@code FindFields} that we are building.
     */
    public FindFieldsBuilder withGroups(String... groups) {
        try {
            fieldsToFind.setGroupKeywords(ParserUtil.parseGroup(Arrays.asList(groups)));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("invalid groups.");
        }
        return this;
    }

    /**
     * Sets the {@code tagKeywords} list of the {@code FindFields} that we are building.
     */
    public FindFieldsBuilder withTags(String... tags) {
        fieldsToFind.setTagKeywords(Arrays.asList(tags));
        return this;
    }

    public FindFields build() {
        return fieldsToFind;
    }
}
//@@author
