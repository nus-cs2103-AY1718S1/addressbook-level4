package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.alias.AliasToken;
import seedu.address.model.alias.Keyword;
import seedu.address.model.alias.ReadOnlyAliasToken;
import seedu.address.model.alias.Representation;

//@@author deep4k
/**
 * A utility class to help with building AliasToken objects.
 */

public class AliasTokenBuilder {

    public static final String DEFAULT_KEYWORD = "ph";
    public static final String DEFAULT_REPRESENTATION = "Public Holiday";

    private AliasToken aliasToken;

    public AliasTokenBuilder() {
        try {
            Keyword defaultKeyword = new Keyword(DEFAULT_KEYWORD);
            Representation defaultRepresentation = new Representation((DEFAULT_REPRESENTATION));
            this.aliasToken = new AliasToken(defaultKeyword, defaultRepresentation);
        } catch (IllegalValueException ive) {
            throw new AssertionError("Default alias' values are invalid.");
        }
    }

    /**
     * Initializes the AliasTokenBuilder with the data of {@code aliasToCopy}.
     */
    public AliasTokenBuilder(ReadOnlyAliasToken aliasToCopy) {
        this.aliasToken = new AliasToken(aliasToCopy);
    }

    /**
     * Sets the {@code Keyword} of the {@code AliasToken} that we are building.
     */
    public AliasTokenBuilder withKeyword(String keyword) {
        try {
            this.aliasToken.setKeyword(new Keyword(keyword));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Keyword is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Representation} of the {@code AliasToken} that we are building.
     */
    public AliasTokenBuilder withRepresentation(String representation) {
        try {
            this.aliasToken.setRepresentation(new Representation(representation));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Representation is expected to be unique.");
        }
        return this;
    }

    public AliasToken build() {
        return this.aliasToken;
    }

}

