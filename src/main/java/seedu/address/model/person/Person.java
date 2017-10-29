package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.util.DateUtil;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Person implements ReadOnlyPerson {

    private ObjectProperty<Name> name;
    private ObjectProperty<Handphone> handphone;
    private ObjectProperty<HomePhone> homePhone;
    private ObjectProperty<OfficePhone> officePhone;
    private ObjectProperty<Email> email;
    private ObjectProperty<Address> address;
    private ObjectProperty<PostalCode> postalCode;
    private ObjectProperty<Cluster> cluster;
    private ObjectProperty<Debt> debt;
    private ObjectProperty<Interest> interest;
    private ObjectProperty<DateBorrow> dateBorrow;
    private ObjectProperty<Deadline> deadline;
    private ObjectProperty<DateRepaid> dateRepaid;

    private ObjectProperty<UniqueTagList> tags;

    private boolean isBlacklisted = false;
    private boolean isWhitelisted = false;
    private Date lastAccruedDate; // the last time debt was updated by interest

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone handphone, Phone homePhone, Phone officePhone, Email email, Address address,
                  PostalCode postalCode, Debt debt, Interest interest, Deadline deadline, Set<Tag> tags) {
        requireAllNonNull(name, handphone, homePhone, officePhone, email, address, postalCode, debt, interest, deadline,
                tags);
        this.name = new SimpleObjectProperty<>(name);
        this.handphone = new SimpleObjectProperty<>((Handphone) handphone);
        this.homePhone = new SimpleObjectProperty<>((HomePhone) homePhone);
        this.officePhone = new SimpleObjectProperty<>((OfficePhone) officePhone);
        this.email = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);
        this.postalCode = new SimpleObjectProperty<>(postalCode);
        this.cluster = new SimpleObjectProperty<>(new Cluster(postalCode));
        this.debt = new SimpleObjectProperty<>(debt);
        this.interest = new SimpleObjectProperty<>(interest);
        this.dateBorrow = new SimpleObjectProperty<>(new DateBorrow());
        this.deadline = new SimpleObjectProperty<>(deadline);
        this.dateRepaid = new SimpleObjectProperty<>(new DateRepaid());
        this.lastAccruedDate = new Date();
        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
    }

    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Person(ReadOnlyPerson source) {
        this(source.getName(), source.getHandphone(), source.getHomePhone(), source.getOfficePhone(), source.getEmail(),
                source.getAddress(), source.getPostalCode(), source.getDebt(), source.getInterest(),
                source.getDeadline(), source.getTags());
        this.dateBorrow = new SimpleObjectProperty<>(source.getDateBorrow());
        this.dateRepaid = new SimpleObjectProperty<>(source.getDateRepaid());
        this.cluster = new SimpleObjectProperty<>(new Cluster(postalCode.get()));
        this.isBlacklisted = source.isBlacklisted();
        this.isWhitelisted = source.isWhitelisted();
        this.lastAccruedDate = source.getLastAccruedDate();
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
     * Sets handphone number of a person to the given Phone.
     * @param phone must not be null.
     */
    public void setHandphone(Phone phone) {
        this.handphone.set(requireNonNull((Handphone) phone));
    }

    @Override
    public ObjectProperty<Handphone> handphoneProperty() {
        return handphone;
    }

    @Override
    public Handphone getHandphone() {
        return handphone.get();
    }

    /**
     * Sets home phone number of a person to the given Phone.
     * @param phone must not be null.
     */
    public void setHomePhone(Phone phone) {
        this.homePhone.set(requireNonNull((HomePhone) phone));
    }

    @Override
    public ObjectProperty<HomePhone> homePhoneProperty() {
        return homePhone;
    }

    @Override
    public HomePhone getHomePhone() {
        return homePhone.get();
    }

    /**
     * Sets office phone number of a person to the given Phone.
     * @param phone must not be null.
     */
    public void setOfficePhone(Phone phone) {
        this.officePhone.set(requireNonNull((OfficePhone) phone));
    }

    @Override
    public ObjectProperty<OfficePhone> officePhoneProperty() {
        return officePhone;
    }

    @Override
    public OfficePhone getOfficePhone() {
        return officePhone.get();
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

    /**
     * Sets cluster of a person to the given Cluster.
     * @param cluster must not be null.
     */
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
     * Sets current interest of a person to the given Interest.
     * @param interest must not be null.
     */
    public void setInterest(Interest interest) {
        this.interest.set(requireNonNull(interest));
    }

    @Override
    public ObjectProperty<Interest> interestProperty() {
        return interest;
    }

    @Override
    public Interest getInterest() {
        return interest.get();
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
     * Sets date borrowed of a person in the given {@code dateBorrow}.
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

    //@@author lawwman

    /**
     * Sets associated deadline of a person to the given Deadline.
     * @param deadline must not be null.
     */
    public void setDeadline(Deadline deadline) {
        this.deadline.set(requireNonNull(deadline));
    }

    @Override
    public ObjectProperty<Deadline> deadlineProperty() {
        return deadline;
    }

    @Override
    public Deadline getDeadline() {
        return deadline.get();
    }

    /**
     * Returns boolean status of a person's blacklist-status.
     */
    @Override
    public boolean isBlacklisted() {
        return isBlacklisted;
    }

    /**
     * Sets boolean status of a person's blacklist-status using the value of {@param isBlacklisted}.
     */
    @Override
    public void setIsBlacklisted(boolean isBlacklisted) {
        this.isBlacklisted = isBlacklisted;
    }

    /**
     * Returns boolean status of a person's whitelist-status.
     */
    @Override
    public boolean isWhitelisted() {
        return isWhitelisted;
    }

    /**
     * Sets boolean status of a person's whitelist-status using the value of {@param isWhitelisted}.
     */
    @Override
    public void setIsWhitelisted(boolean isWhitelisted) {
        this.isWhitelisted = isWhitelisted;
    }
    //@@author
    /**
     * Sets date repaid of a person in the given {@code dateRepaid}.
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
     * Sets date of last accrued date.
     * @param lastAccruedDate must not be null.
     */
    public void setLastAccruedDate(Date lastAccruedDate) {
        requireNonNull(lastAccruedDate);
        this.lastAccruedDate = lastAccruedDate;
    }

    @Override
    public Date getLastAccruedDate() {
        return lastAccruedDate;
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

    /**
     * Returns true if both are in same cluster.
     */
    @Override
    public boolean isSameCluster(ReadOnlyPerson other) {
        return other.getCluster().equals(this.getCluster());
    }

    /**
     * Calculates increase in debt based on interest rate and amount of months
     */
    @Override
    public String calcAccruedAmount(int differenceInMonths) {
        this.lastAccruedDate = new Date(); // update last accrued date
        double principal = this.getDebt().toNumber();
        double interestRate = (double) Integer.parseInt(this.getInterest().toString()) / 100;
        double accruedInterest = principal * Math.pow((1 + interestRate), differenceInMonths) - principal;
        return String.format("%.2f", accruedInterest);
    }

    /**
     * Compares date of last accrued against current date.
     * @return number of months the current date is ahead of last accrued date. Returns 0 if
     * there is no need to increment debt.
     */
    @Override
    public int checkLastAccruedDate(Date currentDate) {
        if (lastAccruedDate.before(currentDate)) {
            return DateUtil.getNumberOfMonthBetweenDates(currentDate, lastAccruedDate);
        } else {
            return 0;
        }
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
        return Objects.hash(name, handphone, homePhone, officePhone, email, address, postalCode, debt, interest,
                deadline, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
