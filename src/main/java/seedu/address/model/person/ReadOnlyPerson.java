package seedu.address.model.person;

import java.util.Date;
import java.util.Set;

import javafx.beans.property.ObjectProperty;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Person in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyPerson {

    ObjectProperty<Name> nameProperty();
    Name getName();
    ObjectProperty<Handphone> handphoneProperty();
    Handphone getHandphone();
    ObjectProperty<HomePhone> homePhoneProperty();
    HomePhone getHomePhone();
    ObjectProperty<OfficePhone> officePhoneProperty();
    OfficePhone getOfficePhone();
    ObjectProperty<Email> emailProperty();
    Email getEmail();
    ObjectProperty<Address> addressProperty();
    Address getAddress();
    ObjectProperty<PostalCode> postalCodeProperty();
    PostalCode getPostalCode();
    ObjectProperty<Cluster> clusterProperty();
    Cluster getCluster();
    ObjectProperty<Debt> debtProperty();
    Debt getDebt();
    ObjectProperty<Debt> totalDebtProperty();
    Debt getTotalDebt();
    ObjectProperty<Interest> interestProperty();
    Interest getInterest();
    ObjectProperty<DateBorrow> dateBorrowProperty();
    DateBorrow getDateBorrow();
    ObjectProperty<Deadline> deadlineProperty();
    Deadline getDeadline();

    ObjectProperty<DateRepaid> dateRepaidProperty();
    DateRepaid getDateRepaid();
    Date getLastAccruedDate();
    ObjectProperty<UniqueTagList> tagProperty();
    Set<Tag> getTags();

    //@@author jaivigneshvenugopal
    /**
     * Returns true if person is blacklisted.
     */
    boolean isBlacklisted();

    /**
     * {@param} is {@code boolean} value.
     * Sets {@code boolean} variable as the value of {@param isBlacklisted}
     */
    void setIsBlacklisted(boolean isBlacklisted);

    /**
     * Returns true if person is whitelisted.
     */
    boolean isWhitelisted();

    /**
     * {@param} is {@code boolean} value.
     * Sets {@code boolean} variable as the value of {@param isWhitelisted}
     */
    void setIsWhitelisted(boolean isWhitelisted);

    /**
     * Returns true if person has display picture.
     */
    boolean hasDisplayPicture();

    /**
     * {@param} is {@code boolean} value.
     * Sets {@code boolean} variable as the value of {@param hasDisplayPicture}
     */
    void setHasDisplayPicture(boolean hasDisplayPicture);
    //@@author

    /**
     * Returns true if person has overdue debt.
     */
    boolean hasOverdueDebt();

    /**
     * {@param} is {@code boolean} value.
     * Sets {@code boolean} variable as the value of {@param hasOverdueDebt}
     */
    void setHasOverdueDebt(boolean hasOverdueDebt);

    /**
     * Returns true if both are in same cluster.
     */
    boolean isSameCluster(ReadOnlyPerson other);

    /**
     * Calculates new debt of debtor based on current interest rate.
     */
    String calcAccruedAmount(int differenceInMonths);

    /**
     * Checks if person is due for an update on his/her debt.
     */
    int checkLastAccruedDate(Date currentDate);

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyPerson other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getHandphone().equals(this.getHandphone())
                && other.getHomePhone().equals(this.getHomePhone())
                && other.getOfficePhone().equals(this.getOfficePhone())
                && other.getEmail().equals(this.getEmail())
                && other.getAddress().equals(this.getAddress()))
                && other.getPostalCode().equals(this.getPostalCode())
                && other.getCluster().equals(this.getCluster())
                && (other.isBlacklisted() == (this.isBlacklisted()))
                && (other.isWhitelisted() == (this.isWhitelisted()))
                && (other.hasOverdueDebt() == (this.hasOverdueDebt()))
                && other.getDebt().equals(this.getDebt())
                && other.getTotalDebt().equals(this.getTotalDebt())
                && other.getInterest().equals(this.getInterest())
                && other.getDateBorrow().equals(this.getDateBorrow())
                && other.getDeadline().equals(this.getDeadline())
                && other.getDateRepaid().equals(this.getDateRepaid());
    }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" HP: ")
                .append(getHandphone())
                .append(" Home: ")
                .append(getHomePhone())
                .append(" Office: ")
                .append(getOfficePhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Postal Code: ")
                .append(getPostalCode())
                .append(" Cluster: ")
                .append(getCluster())
                .append(" Current Debt: ")
                .append(getDebt())
                .append(" Total Debt: ")
                .append(getTotalDebt())
                .append(" Interest: ")
                .append(getInterest())
                .append(" Date borrowed: ")
                .append(getDateBorrow())
                .append(" Deadline: ")
                .append(getDeadline())
                .append(" Date Repaid: ")
                .append(getDateRepaid())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
