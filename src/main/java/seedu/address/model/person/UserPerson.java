package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import seedu.address.model.person.weblink.UniqueWebLinkList;
import seedu.address.model.person.weblink.WebLink;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.util.SampleUserPersonUtil;

/**
 * Represents the user's Profile in the address book.
 *
 */
public class UserPerson implements ReadOnlyPerson {
    private ObjectProperty<Name> name;
    private ObjectProperty<Phone> phone;
    private ObjectProperty<ArrayList<Email>> emails;
    private ObjectProperty<Address> address;
    private ObjectProperty<Remark> remark;
    private ObjectProperty<UniqueTagList> tags;
    private ObjectProperty<UniqueWebLinkList> webLinks;

    public UserPerson() {
        this(SampleUserPersonUtil.getDefaultSamplePerson().getName(),
                SampleUserPersonUtil.getDefaultSamplePerson().getPhone(),
                SampleUserPersonUtil.getDefaultSamplePerson().getEmail(),
                SampleUserPersonUtil.getDefaultSamplePerson().getAddress());
    }


    /**
     * Every field must be present and not null.
     */
    public UserPerson(Name name, Phone phone, ArrayList<Email> email, Address address) {
        requireAllNonNull(name, phone, email, address);
        this.name = new SimpleObjectProperty<>(name);
        this.phone = new SimpleObjectProperty<>(phone);
        this.emails = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);
        this.remark = new SimpleObjectProperty<>(new Remark(""));
        this.tags = new SimpleObjectProperty<>(new UniqueTagList());
        this.webLinks = new SimpleObjectProperty<>(new UniqueWebLinkList());
    }

    /**
     * Every field must be present and not null.
     */
    public UserPerson(ReadOnlyPerson src) {
        this.name = new SimpleObjectProperty<>(src.getName());
        this.phone = new SimpleObjectProperty<>(src.getPhone());
        this.emails = new SimpleObjectProperty<>(src.getEmail());
        this.address = new SimpleObjectProperty<>(src.getAddress());
        this.remark = new SimpleObjectProperty<>(new Remark(""));
        this.tags = new SimpleObjectProperty<>(new UniqueTagList());
        this.webLinks = new SimpleObjectProperty<>(new UniqueWebLinkList());
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

    public void setEmail(ArrayList<Email> email) {
        this.emails.set(requireNonNull(email));
    }

    @Override
    public ObjectProperty<ArrayList<Email>> emailProperty() {
        return emails;
    }

    @Override
    public ArrayList<Email> getEmail() {
        return emails.get();
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

    public void setRemark(Remark remark) {
        this.remark.set(requireNonNull(remark));
    }

    @Override
    public ObjectProperty<Remark> remarkProperty() {
        return remark;
    }

    @Override
    public Remark getRemark() {
        return remark.get();
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    @Override
    public Set<Tag> getTags() {
        return tags.get().toSet();
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
     * Replaces this person's web links with the web links in the argument tag set.
     */
    public void setWebLinks(Set<WebLink> replacement) {
        webLinks.set(new UniqueWebLinkList(replacement));
    }

    /**
     * Returns an immutable webLink set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    @Override
    public Set<WebLink> getWebLinks() {
        return webLinks.get().toSet();
    }

    @Override
    public ObjectProperty<UniqueWebLinkList> webLinkProperty() {
        return webLinks;
    }


    /**
     * returns a ArrayList of string websites for UI usage.
     * @code WebLinkUtil for the list of webLinkTags can be used as category.
     */
    @Override
    public ArrayList<String> listOfWebLinkByCategory (String category) {
        ArrayList<String> outputWebLinkList = new ArrayList<String>();
        for (Iterator<WebLink> iterateWebLinkSet = getWebLinks().iterator(); iterateWebLinkSet.hasNext();) {
            WebLink checkWebLink = iterateWebLinkSet.next();
            String webLinkAddedToList = checkWebLink.toStringWebLink();
            String checkWebLinkTag = checkWebLink.toStringWebLinkTag();
            if (checkWebLinkTag.equals(category)) {
                outputWebLinkList.add(webLinkAddedToList);
            }
        }
        return outputWebLinkList;
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
        return Objects.hash(name, phone, emails, address, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

    /**
     * Update the UserPerson details with that of the ReadOnlyPerson target
     * @param target
     */
    public void update(ReadOnlyPerson target) {
        this.name = new SimpleObjectProperty<>(target.getName());
        this.emails = new SimpleObjectProperty<>(target.getEmail());
        this.phone = new SimpleObjectProperty<>(target.getPhone());
        this.address = new SimpleObjectProperty<>(target.getAddress());
    }
}
