package seedu.address.model.socialMedia;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.socialMedia.exceptions.DuplicateSocialMediaException;
import seedu.address.model.socialMedia.exceptions.SocialMediaNotFoundException;


/**
 * A list of socialMediaUrlss that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see SocialMedia#equals(Object)
 */


public class UniqueSocialMediaList implements Iterable<SocialMedia> {

    private final ObservableList<SocialMedia> internalList = FXCollections.observableArrayList();
    // used by asObservableList()
    private final ObservableList<ReadOnlySocialMedia> mappedList =
            EasyBind.map(internalList, (socialMediaUrls) -> socialMediaUrls);

    /**
     * Creates a UniqueSocialMediaList using given SocialMedias.
     * Enforces no nulls.
     */
    public UniqueSocialMediaList(Set<SocialMedia> socialMediaUrls) {
        requireAllNonNull(socialMediaUrls);
        internalList.addAll(socialMediaUrls);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Constructs empty SocialMediaList.
     */
    public UniqueSocialMediaList() {}

    /**
     * Returns all socialMediaUrlss in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */

    /**
     * Returns true if the list contains an equivalent SocialMedia as the given argument.
     */
    public boolean contains(ReadOnlySocialMedia toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Returns a set representation of the socialMediaUrls.
     */
    public Set<SocialMedia> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Ensures every socialMediaUrls in the argument list exists in this object.
     */
    public void mergeFrom(UniqueSocialMediaList from) {
        final Set<SocialMedia> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(socialMediaUrls -> !alreadyInside.contains(socialMediaUrls))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Adds a SocialMedia to the list.
     *
     * throws seedu.address.model.socialmedia.exceptions.DuplicateSocialMediaException
     * if the SocialMedia to add is a duplicate of an existing SocialMedia in the list.
     */
    public void add(ReadOnlySocialMedia toAdd) throws DuplicateSocialMediaException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateSocialMediaException();
        }
        internalList.add(new SocialMedia(toAdd));

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Removes the equivalent socialMediaUrls from the list.
     *
     * @throws SocialMediaNotFoundException if no such socialMediaUrls could be found in the list.
     */
    public boolean remove(ReadOnlySocialMedia toRemove) throws SocialMediaNotFoundException {
        requireNonNull(toRemove);
        final boolean socialMediaUrlsFoundAndDeleted = internalList.remove(toRemove);
        if (!socialMediaUrlsFoundAndDeleted) {
            throw new SocialMediaNotFoundException();
        }
        return socialMediaUrlsFoundAndDeleted;
    }

    @Override
    public Iterator<SocialMedia> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    public void setSocialMedias(UniqueSocialMediaList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setSocialMedias(List<? extends ReadOnlySocialMedia> socialMediaUrls)
            throws DuplicateSocialMediaException {
        final UniqueSocialMediaList replacement = new UniqueSocialMediaList();
        for (final ReadOnlySocialMedia socialMediaUrl: socialMediaUrls) {
            replacement.add(new SocialMedia(socialMediaUrl));
        }
        setSocialMedias(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ReadOnlySocialMedia> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(mappedList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.socialMedia.UniqueSocialMediaList // instanceof handles nulls
                && this.internalList.equals(((seedu.address.model.socialMedia.UniqueSocialMediaList) other).internalList
        ));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(seedu.address.model.socialMedia.UniqueSocialMediaList other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        assert CollectionUtil.elementsAreUnique(other.internalList);
        return this == other || new HashSet<>(this.internalList).equals(new HashSet<>(other.internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }

}

