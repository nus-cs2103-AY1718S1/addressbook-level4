package seedu.address.model.module;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.fxmisc.easybind.EasyBind;
import seedu.address.commons.util.CollectionUtil;

import java.util.Iterator;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * A list of modules that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Module #equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueModuleList implements Iterable<Module>{

    private final ObservableList<Module> internalList = FXCollections.observableArrayList();
    // used by asObservableList()
    private final ObservableList<ReadOnlyModule> mappedList = EasyBind.map(internalList, (Module) -> Module);

    /**
     * Returns true if the list contains an equivalent Lesson as the given argument.
     */
    public boolean contains(ReadOnlyModule toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Module to the list.
     *
     * @throws DuplicateModuleException if the module to add is a duplicate of an existing module in the list.
     */
    public void add(ReadOnlyModule toAdd) throws DuplicateModuleException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateModuleException();
        }
        internalList.add(new Module(toAdd));
    }

    /**
     * Replaces the module {@code target} in the list with {@code editedModule}.
     *
     * @throws DuplicateModuleException if the replacement is equivalent to another existing module in the list.
     * @throws ModuleNotFoundException if {@code target} could not be found in the list.
     */
    public void setModule(ReadOnlyModule target, ReadOnlyModule editedModule)
            throws DuplicateModuleException, ModuleNotFoundException {
        requireNonNull(editedModule);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ModuleNotFoundException();
        }

        if (!target.equals(editedModule) && internalList.contains(editedModule)) {
            throw new DuplicateModuleException();
        }

        internalList.set(index, new Module(editedModule));
    }

    /**
     * Removes the equivalent module from the list.
     *
     * @throws ModuleNotFoundException if no such module could be found in the list.
     */
    public boolean remove(ReadOnlyModule toRemove) throws ModuleNotFoundException {
        requireNonNull(toRemove);
        final boolean moduleFoundAndDeleted = internalList.remove(toRemove);
        if (!moduleFoundAndDeleted) {
            throw new ModuleNotFoundException();
        }
        return moduleFoundAndDeleted;
    }

    public void setModules(UniqueModuleList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setModules(List<? extends ReadOnlyModule> modules) throws DuplicateModuleException {
        final UniqueModuleList replacement = new UniqueModuleList();
        for (final ReadOnlyModule module : modules) {
            replacement.add(new Module(module));
        }
        setModules(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ReadOnlyModule> asObservableList() {
        return FXCollections.unmodifiableObservableList(mappedList);
    }

    @Override
    public Iterator<Module> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueModuleList// instanceof handles nulls
                && this.internalList.equals(((UniqueModuleList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
