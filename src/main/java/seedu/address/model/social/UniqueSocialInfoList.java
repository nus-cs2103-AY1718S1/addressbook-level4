package seedu.address.model.social;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DuplicateDataException;

//@@author marvinchin
/**
 * A list of {@code SocialInfo}s that enforces no nulls and no duplicate social platforms between its elements.
 */
public class UniqueSocialInfoList implements Iterable<SocialInfo> {

    private final ObservableList<SocialInfo> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty SocialInfoList.
     */
    public UniqueSocialInfoList() {}

    /**
     * Creates a UniqueSocialInfoList using given {@code socialInfos}.
     * Enforces no nulls.
     */
    public UniqueSocialInfoList(Set<SocialInfo> socialInfos) {
        requireAllNonNull(socialInfos);
        internalList.addAll(socialInfos);

        assert areInternalListSocialTypesUnique();
    }


    /**
     * Returns all the social media information in this list as a {@code Set}.
     */
    public Set<SocialInfo> toSet() {
        assert areInternalListSocialTypesUnique();
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the {@code SocialInfo} in the internal list with those in the argument {@code SocialInfo} set.
     */
    public void setSocialInfos(Set<SocialInfo> socialInfos) {
        requireAllNonNull(socialInfos);
        internalList.setAll(socialInfos);

        assert areInternalListSocialTypesUnique();
    }

    /**
     * Returns true if the internal list contains a {@code SocialInfo} with the same social platform.
     */
    public boolean containsSameType(SocialInfo toCheck) {
        requireNonNull(toCheck);

        String toCheckType = toCheck.getSocialType();
        for (SocialInfo socialInfo : internalList) {
            String thisSocialType = socialInfo.getSocialType();
            if (toCheckType.equals(thisSocialType)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Adds a {@code SocialInfo} to the internal list.
     * @throws DuplicateSocialTypeException if the platform represented by the {@code SocialInfo} to add is a duplicate
     * of another element in the list.
     */
    public void add(SocialInfo toAdd) throws DuplicateSocialTypeException {
        requireNonNull(toAdd);

        if (containsSameType(toAdd)) {
            throw new DuplicateSocialTypeException();
        }
        internalList.add(toAdd);

        assert areInternalListSocialTypesUnique();
    }

    /**
     * Checks that there are no {@code SocialInfo} of the same platform in the internal list.
     */
    private boolean areInternalListSocialTypesUnique() {
        HashSet<String> socialTypes = new HashSet<>();

        for (SocialInfo socialInfo : internalList) {
            String socialType = socialInfo.getSocialType();
            if (socialTypes.contains(socialType)) {
                return false;
            } else {
                socialTypes.add(socialType);
            }
        }

        return true;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<SocialInfo> asObservableList() {
        assert areInternalListSocialTypesUnique();

        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<SocialInfo> iterator() {
        assert areInternalListSocialTypesUnique();
        return internalList.iterator();

    }

    @Override
    public boolean equals(Object other) {
        assert areInternalListSocialTypesUnique();

        return other == this
                || (other instanceof UniqueSocialInfoList
                && this.internalList.equals(((UniqueSocialInfoList) other).internalList));
    }

    /**
     * Returns true if the elements in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueSocialInfoList other) {
        assert areInternalListSocialTypesUnique();
        assert other.areInternalListSocialTypesUnique();
        return other == this
                || new HashSet<>(internalList).equals(new HashSet<>(other.internalList));
    }

    /**
     * Signals that an operation would have violated the 'no duplicate social platform' property of the list.
     */
    public static class DuplicateSocialTypeException extends DuplicateDataException {
        protected DuplicateSocialTypeException() {
            super("Operation would result in more than one SocialInfo of the same type");
        }
    }
}
