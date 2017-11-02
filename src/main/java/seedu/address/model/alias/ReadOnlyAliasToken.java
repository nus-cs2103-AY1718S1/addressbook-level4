package seedu.address.model.alias;

import javafx.beans.property.ObjectProperty;

//@@author deep4k
/**
 * A read-only immutable interface for a AliasToken in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyAliasToken {
    ObjectProperty<Keyword> keywordProperty();

    Keyword getKeyword();

    ObjectProperty<Representation> representationProperty();

    Representation getRepresentation();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyAliasToken other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid  below
                && other.getKeyword().equals(this.getKeyword()) // state checks here onwards
                && other.getRepresentation().equals(this.getRepresentation()));
    }

    /**
     * Formats the AliasToken as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" keyword: ")
                .append(getKeyword())
                .append(" representation: ")
                .append(getRepresentation());
        return builder.toString();
    }
}
