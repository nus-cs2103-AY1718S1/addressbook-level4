package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Person implements ReadOnlyPerson {

    private ObjectProperty<Name> name;
    private ObjectProperty<Phone> phone;
    private ObjectProperty<Email> email;
    private ObjectProperty<Address> address;
    private ObjectProperty<PostalCode> postalCode;
    private ObjectProperty<Cluster> cluster;
    private ObjectProperty<Debt> debt;
    private ObjectProperty<DateBorrow> dateBorrow;
    private ObjectProperty<DateRepaid> dateRepaid;

    private ObjectProperty<UniqueTagList> tags;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, PostalCode postalCode,
                  Debt debt, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, postalCode, debt, tags);
        this.name = new SimpleObjectProperty<>(name);
        this.phone = new SimpleObjectProperty<>(phone);
        this.email = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);
        this.postalCode = new SimpleObjectProperty<>(postalCode);
        this.cluster = new SimpleObjectProperty<>(new Cluster(postalCode));
        this.debt = new SimpleObjectProperty<>(debt);
        this.dateBorrow = new SimpleObjectProperty<>(new DateBorrow());
        this.dateRepaid = new SimpleObjectProperty<>(new DateRepaid());
        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
    }

    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Person(ReadOnlyPerson source) {
        this(source.getName(), source.getPhone(), source.getEmail(), source.getAddress(), source.getPostalCode(),
                source.getDebt(), source.getTags());
        this.dateBorrow = new SimpleObjectProperty<>(source.getDateBorrow());
        this.dateRepaid = new SimpleObjectProperty<>(source.getDateRepaid());
        this.cluster = new SimpleObjectProperty<>(new Cluster(postalCode.get()));
    }

    /**
     * Sets name of a person to the given Name.
     * @param name must not be null.
     */
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

    /**
     * Sets phone number of a person to the given Phone.
     * @param phone must not be null.
     */
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

    /**
     * Sets email of a person to the given Email.
     * @param email must not be null.
     */
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

    /**
     * Sets address of a person to the given Address.
     * @param address must not be null.
     */
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

    //@@author khooroko
    /**
     * Sets postal code of a person to the given PostalCode.
     * @param postalCode must not be null.
     */
    public void setPostalCode(PostalCode postalCode) {
        this.postalCode.set(requireNonNull(postalCode));
    }

    @Override
    public ObjectProperty<PostalCode> postalCodeProperty() {
        return postalCode;
    }

    @Override
    public PostalCode getPostalCode() {
        return postalCode.get();
    }

    public void setCluster(Cluster cluster) {
        this.cluster.set(requireNonNull(cluster));
    }

    @Override
    public ObjectProperty<Cluster> clusterProperty() {
        return cluster;
    }

    @Override
    public Cluster getCluster() {
        return cluster.get();
    }

    //@@author lawwman
    /**
     * Sets current debt of a person to the given Debt.
     * @param debt must not be null.
     */
    public void setDebt(Debt debt) {
        this.debt.set(requireNonNull(debt));
    }

    @Override
    public ObjectProperty<Debt> debtProperty() {
        return debt;
    }

    @Override
    public Debt getDebt() {
        return debt.get();
    }

    /**
     * Sets date borrowed of a person the the given DateBorrow.
     * @param dateBorrow must not be null.
     */
    public void setDateBorrow(DateBorrow dateBorrow) {
        this.dateBorrow.set(requireNonNull(dateBorrow));
    }

    @Override
    public ObjectProperty<DateBorrow> dateBorrowProperty() {
        return dateBorrow;
    }

    @Override
    public DateBorrow getDateBorrow() {
        return dateBorrow.get();
    }

    //@@author
    /**
     * Sets date borrowed of a person the the given DateBorrow.
     * @param dateRepaid must not be null.
     */
    public void setDateRepaid(DateRepaid dateRepaid) {
        this.dateRepaid.set(requireNonNull(dateRepaid));
    }

    @Override
    public ObjectProperty<DateRepaid> dateRepaidProperty() {
        return dateRepaid;
    }

    @Override
    public DateRepaid getDateRepaid() {
        return dateRepaid.get();
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    @Override
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.get().toSet());
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
        return Objects.hash(name, phone, email, address, postalCode, debt, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
