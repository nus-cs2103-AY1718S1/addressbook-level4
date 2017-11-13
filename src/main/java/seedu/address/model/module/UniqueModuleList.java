package seedu.address.model.module;

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
 * A list of Modules that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Module#equals(Object)
 */
public class UniqueModuleList implements Iterable<Module> {

    private final ObservableList<Module> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty ModuleList.
     */
    public UniqueModuleList() {}

    /**
     * Creates a UniqueModuleList using given Modules.
     * Enforces no nulls.
     */
    public UniqueModuleList(Set<Module> modules) {
        requireAllNonNull(modules);
        internalList.addAll(modules);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all Modules in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Module> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the Modules in this list with those in the argument Module list.
     */
    public void setModules(Set<Module> modules) {
        requireAllNonNull(modules);
        internalList.setAll(modules);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Ensures every Module in the argument list exists in this object.
     */
    public void mergeFrom(UniqueModuleList from) {
        final Set<Module> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(Module -> !alreadyInside.contains(Module))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent Module as the given argument.
     */
    public boolean contains(Module toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Module to the list.
     *
     * @throws DuplicateModuleException if the Module to add is a duplicate of an existing Module in the list.
     */
    public void add(Module toAdd) throws DuplicateModuleException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateModuleException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    @Override
    public Iterator<Module> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Module> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniqueModuleList // instanceof handles nulls
                        && this.internalList.equals(((UniqueModuleList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueModuleList other) {
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
    public static class DuplicateModuleException extends DuplicateDataException {
        protected DuplicateModuleException() {
            super("Operation would result in duplicate Modules");
        }
    }

}
