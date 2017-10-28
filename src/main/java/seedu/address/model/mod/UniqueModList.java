package seedu.address.model.mod;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.commons.util.CollectionUtil;

/**
 * A list of mods that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Mod#equals(Object)
 */
public class UniqueModList implements Iterable<Mod> {

    private final ObservableList<Mod> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty ModList.
     */
    public UniqueModList() {}

    /**
     * Creates a UniqueModList using given mods.
     * Enforces no nulls.
     */
    public UniqueModList(Set<Mod> mods) {
        requireAllNonNull(mods);
        internalList.addAll(mods);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all mods in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Mod> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the Mods in this list with those in the argument mod list.
     */
    public void setMods(Set<Mod> mods) {
        requireAllNonNull(mods);
        internalList.setAll(mods);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Ensures every mod in the argument list exists in this object.
     */
    public void mergeFrom(UniqueModList from) {
        final Set<Mod> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(mod -> !alreadyInside.contains(mod))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent Mod as the given argument.
     */
    public boolean contains(Mod toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Mod to the list.
     *
     * @throws DuplicateModException if the Mod to add is a duplicate of an existing Mod in the list.
     */
    public void add(Mod toAdd) throws DuplicateModException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateModException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    @Override
    public Iterator<Mod> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Mod> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniqueModList // instanceof handles nulls
                        && this.internalList.equals(((UniqueModList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueModList other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        assert CollectionUtil.elementsAreUnique(other.internalList);
        return this == other || new HashSet<>(this.internalList).equals(new HashSet<>(other.internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateModException extends DuplicateDataException {
        protected DuplicateModException() {
            super("Operation would result in duplicate mods");
        }
    }

}
