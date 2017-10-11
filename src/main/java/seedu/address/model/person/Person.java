package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.commons.util.StringUtil.levenshteinDistance;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Person implements ReadOnlyPerson {
    private static final int FIND_NAME_GLOBAL_TOLERANCE = 4;
    private static final int FIND_NAME_DISTANCE_TOLERANCE = 2;

    private ObjectProperty<Name> name;
    private ObjectProperty<Phone> phone;
    private ObjectProperty<Email> email;
    private ObjectProperty<Address> address;

    private ObjectProperty<UniqueTagList> tags;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = new SimpleObjectProperty<>(name);
        this.phone = new SimpleObjectProperty<>(phone);
        this.email = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);
        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
    }

    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Person(ReadOnlyPerson source) {
        this(source.getName(), source.getPhone(), source.getEmail(), source.getAddress(),
                source.getTags());
    }

    public void setName(Name name) {
        this.name.set(requireNonNull(name));
    }

    @Override
    public ObjectProperty<Name> nameProperty() {
        return name;
    }

    @Override
    public Name getName() {
        return name.get();
    }

    public void setPhone(Phone phone) {
        this.phone.set(requireNonNull(phone));
    }

    @Override
    public ObjectProperty<Phone> phoneProperty() {
        return phone;
    }

    @Override
    public Phone getPhone() {
        return phone.get();
    }

    public void setEmail(Email email) {
        this.email.set(requireNonNull(email));
    }

    @Override
    public ObjectProperty<Email> emailProperty() {
        return email;
    }

    @Override
    public Email getEmail() {
        return email.get();
    }

    public void setAddress(Address address) {
        this.address.set(requireNonNull(address));
    }

    @Override
    public ObjectProperty<Address> addressProperty() {
        return address;
    }

    @Override
    public Address getAddress() {
        return address.get();
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    @Override
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.get().toSet());
    }

    /**
     * Returns whether a person's name contains some of the specified keywords,
     * within a Levenshtein distance of {@link #FIND_NAME_DISTANCE_TOLERANCE},
     * ONLY WHEN the name word contains a number of characters
     * more than or equal to the global limit {@link #FIND_NAME_GLOBAL_TOLERANCE}.
     *
     * Otherwise, it returns the case-insensitive direct match for the particular
     * name word and continues on to the next name word for a check.
     *
     * If no conditions are triggered, the method returns false.
     *
     * @param keyWords for searching
     * @return {@code true} if the name is close to any keyWord,
     *         {@code false} otherwise.
     */
    @Override
    public boolean isNameCloseToAnyKeyword(List<String> keyWords) {
        return getName().getWordsInName().stream()
                .anyMatch(nameWord -> nameWord.length() >= FIND_NAME_GLOBAL_TOLERANCE && keyWords.stream()
                        .anyMatch(keyWord -> levenshteinDistance(nameWord, keyWord) <= FIND_NAME_DISTANCE_TOLERANCE));
    }

    /**
     * Returns whether a person's name matches any of the specified keywords
     * @param keyWords for searching
     * @return {@code true} if the name matches any keyWord,
     *         {@code false} otherwise.
     */
    @Override
    public boolean isNameMatchAnyKeyword(List<String> keyWords) {
        return keyWords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(getName().fullName, keyword));
    }

    /**
     * Returns whether any of a person's tags exist in the search parameters.
     * @param keyWords set of case-sensitive keywords to be matched against
     *                 search parameters
     * @return {@code false} if the sets are disjoint,
     * {@code true} otherwise.
     */
    @Override
    public boolean isTagSetJointKeywordSet(List<String> keyWords) {
        return !Collections.disjoint(keyWords,
                getTags().stream().map(tag -> tag.tagName).collect(Collectors.toSet()));
    }

    @Override
    public boolean isSearchKeyWordsMatchAnyData(List<String> keyWords) {
        return isNameCloseToAnyKeyword(keyWords)
                || isNameMatchAnyKeyword(keyWords)
                || isTagSetJointKeywordSet(keyWords);
    }

    public ObjectProperty<UniqueTagList> tagProperty() {
        return tags;
    }

    /**
     * Replaces this person's tags with the tags in the argument tag set.
     */
    public void setTags(Set<Tag> replacement) {
        tags.set(new UniqueTagList(replacement));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyPerson // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyPerson) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
