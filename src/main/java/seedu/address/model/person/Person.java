package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.commands.WhyCommand.SHOWING_WHY_MESSAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELTAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.insurance.LifeInsurance;
import seedu.address.model.insurance.ReadOnlyInsurance;
import seedu.address.model.insurance.UniqueLifeInsuranceList;
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
    private ObjectProperty<DateOfBirth> dob;

    private String reason;

    private ObjectProperty<UniqueTagList> tags;
    private ObjectProperty<List<UUID>> lifeInsuranceIds;
    private ObjectProperty<UniqueLifeInsuranceList> lifeInsurances;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, DateOfBirth dob, Set<Tag> tags,
                  List<UUID> lifeInsuranceIds) {
        requireAllNonNull(name, phone, email, address, dob, tags);
        this.name = new SimpleObjectProperty<>(name);
        this.phone = new SimpleObjectProperty<>(phone);
        this.email = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);
        this.dob = new SimpleObjectProperty<>(dob);
        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
        this.lifeInsuranceIds = new SimpleObjectProperty<>(lifeInsuranceIds);
        this.lifeInsurances = new SimpleObjectProperty<>(new UniqueLifeInsuranceList());
    }

    /**
     * Only the name field is required
     */
    public Person(Name name, Phone phone, Email email, Address address, DateOfBirth dob, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, dob, tags);
        this.name = new SimpleObjectProperty<>(name);
        this.phone = new SimpleObjectProperty<>(phone);
        this.email = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);
        this.dob = new SimpleObjectProperty<>(dob);
        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
        this.lifeInsuranceIds = new SimpleObjectProperty<>(new ArrayList<UUID>());
        this.lifeInsurances = new SimpleObjectProperty<>(new UniqueLifeInsuranceList());
    }

    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Person(ReadOnlyPerson source) {
        this(source.getName(), source.getPhone(), source.getEmail(), source.getAddress(),
                source.getDateOfBirth(), source.getTags());
        if (source.getLifeInsuranceIds() != null) {
            this.lifeInsuranceIds = new SimpleObjectProperty<>(source.getLifeInsuranceIds());
        }
        if (source.getLifeInsurances() != null) {
            this.lifeInsurances = new SimpleObjectProperty<>(source.getLifeInsurances());
        }
    }

    public Person(ReadOnlyPerson source, LifeInsurance lifeInsurance) {
        this(source.getName(), source.getPhone(), source.getEmail(), source.getAddress(),
                source.getDateOfBirth(), source.getTags());
        if (source.getLifeInsuranceIds() != null) {
            this.lifeInsuranceIds = new SimpleObjectProperty<>(source.getLifeInsuranceIds());
        }
        addLifeInsurances(lifeInsurance);
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

    public void setDateOfBirth(DateOfBirth dob) {
        this.dob.set(requireNonNull(dob));
    }

    @Override
    public ObjectProperty<DateOfBirth> dobProperty() {
        return dob;
    }

    @Override
    public DateOfBirth getDateOfBirth() {
        return dob.get();
    }

    @Override
    public String getReason() {
        Address a = this.getAddress();
        Name n = this.getName();
        this.reason = String.format(SHOWING_WHY_MESSAGE, n, a);
        return reason;
    }

    @Override
    public ObjectProperty<List<UUID> > lifeInsuranceIdProperty() {
        return this.lifeInsuranceIds;
    }

    @Override
    public List<UUID> getLifeInsuranceIds() {
        return this.lifeInsuranceIds.get();
    }

    public void addLifeInsurances(ReadOnlyInsurance lifeInsurance) {
        this.lifeInsurances.get().add(lifeInsurance);
    }

    @Override
    public ObjectProperty<UniqueLifeInsuranceList> lifeInsuranceProperty() {
        return this.lifeInsurances;
    }

    @Override
    public UniqueLifeInsuranceList getLifeInsurances() {
        return this.lifeInsurances.get();
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    @Override
    public Set<Tag> getTags() {
        return tags.get().toSet();
    }

    @Override
    public ObjectProperty<UniqueTagList> tagProperty() {
        return tags;
    }

    /**
     * Replaces this person's tags with the tags in the argument tag set.
     */
    public void setTags(Set<Tag> replacement) {
        tags.set(new UniqueTagList(replacement));
    }

    public String getDetailByPrefix(Prefix prefix) {
        if (prefix.equals(PREFIX_NAME)) {
            return getName().toString();
        } else if (prefix.equals(PREFIX_ADDRESS)) {
            return getAddress().toString();
        } else if (prefix.equals(PREFIX_EMAIL)) {
            return getEmail().toString();
        } else if (prefix.equals(PREFIX_PHONE)) {
            return getPhone().toString();
        } else if (prefix.equals(PREFIX_DOB)) {
            return getDateOfBirth().toString();
        } else if (prefix.equals(PREFIX_TAG) || prefix.equals(PREFIX_DELTAG)) {
            Set<Tag> tags = getTags();
            String detail = "";
            for (Tag tag : tags) {
                detail += tag.tagName + " ";
            }
            return detail.trim();
        }
        return "";
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
        return Objects.hash(name, phone, email, address, dob, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
