package seedu.address.model.alias;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Represents all aliases used in address book. Each alias is a token that contains a keyword and representation.
 * Instances of AliasToken are immutable.
 * Guarantees : details are present and not null
 */
public class AliasToken implements ReadOnlyAliasToken {

    private ObjectProperty<Keyword> keyword;
    private ObjectProperty<Representation> representation;

    /**
     * Every field must be present and not null.
     */
    public AliasToken(Keyword keyword, Representation representation) {
        requireAllNonNull(keyword, representation);

        this.keyword = new SimpleObjectProperty<>(keyword);
        this.representation = new SimpleObjectProperty<>(representation);
    }

    public AliasToken(ReadOnlyAliasToken source) {
        this(source.getKeyword(), source.getRepresentation());
    }

    public void setKeyword(Keyword keyword) {
        this.keyword.set(requireNonNull(keyword));
    }

    public void setRepresentation(Representation representation) {
        this.representation.set(requireNonNull(representation));
    }

    @Override
    public ObjectProperty<Keyword> keywordProperty() {
        return keyword;
    }

    public Keyword getKeyword() {
        return keyword.get();
    }

    @Override
    public ObjectProperty<Representation> representationProperty() {
        return representation;
    }

    public Representation getRepresentation() {
        return representation.get();
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyAliasToken // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyAliasToken) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(keyword, representation);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
