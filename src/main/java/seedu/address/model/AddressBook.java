package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.model.module.Module;
import seedu.address.model.module.ReadOnlyModule;
import seedu.address.model.module.UniqueModuleList;
import seedu.address.model.module.exceptions.DuplicateModuleException;
import seedu.address.model.module.exceptions.ModuleNotFoundException;
import seedu.address.model.tag.Lecturer;
import seedu.address.model.tag.UniqueLecturerList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueModuleList modules;
    private final UniqueLecturerList lecturers;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        modules = new UniqueModuleList();
        lecturers = new UniqueLecturerList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons and Tags in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    public void setModules(List<? extends ReadOnlyModule> modules) throws DuplicateModuleException {
        this.modules.setModules(modules);
    }

    public void setLecturers(Set<Lecturer> lecturers) {
        this.lecturers.setLectuers(lecturers);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        try {
            setModules(newData.getModuleList());
        } catch (DuplicateModuleException e) {
            assert false : "AddressBooks should not have duplicate modules";
        }

        setLecturers(new HashSet<>(newData.getLecturerList()));
        syncMasterLecturerListWith(modules);
    }

    //// module-level operations

    /**
     * Adds a module to the address book.
     * Also checks the new module's lecturers and updates {@link #lecturers} with any new lecturers found,
     * and updates the Lecturer objects in the module to point to those in {@link #lecturers}.
     *
     * @throws DuplicateModuleException if an equivalent module already exists.
     */
    public void addModule(ReadOnlyModule m) throws DuplicateModuleException {
        Module newModule = new Module(m);
        try {
            modules.add(newModule);
        } catch (DuplicateModuleException e) {
            throw e;
        }

        syncMasterLecturerListWith(newModule);
    }

    /**
     * Replaces the given module {@code target} in the list with {@code editedReadOnlyModule}.
     * {@code AddressBook}'s lecturer list will be updated with the lecturers of {@code editedReadOnlyModule}.
     *
     * @throws DuplicateModuleException if updating the module's details causes the module to be equivalent to
     *      another existing module in the list.
     * @throws ModuleNotFoundException if {@code target} could not be found in the list.
     *
     * @see #syncMasterLecturerListWith(Module)
     */
    public void updateModule(ReadOnlyModule target, ReadOnlyModule editedReadOnlyModule)
            throws DuplicateModuleException, ModuleNotFoundException {
        requireNonNull(editedReadOnlyModule);

        Module editedModule = new Module(editedReadOnlyModule);

        try {
            modules.setModule(target, editedModule);
        } catch (DuplicateModuleException e) {
            throw e;
        } catch (ModuleNotFoundException e) {
            throw e;
        }

        syncMasterLecturerListWith(editedModule);
    }

    /**
     * Ensures that every lecturer in this module:
     *  - exists in the master list {@link #modules}
     *  - points to a lecturer object in the master list
     */
    private void syncMasterLecturerListWith(Module module) {
        final UniqueLecturerList moduleLecturers = new UniqueLecturerList(module.getLecturers());
        lecturers.mergeFrom(moduleLecturers);

        // Create map with values = tag object references in the master list
        // used for checking module lecturers references
        final Map<Lecturer, Lecturer> masterTagObjects = new HashMap<>();
        lecturers.forEach(lecturer -> masterTagObjects.put(lecturer, lecturer));

        // Rebuild the list of module lecturers to point to the relevant lecturers in the master lecturer list.
        final Set<Lecturer> correctTagReferences = new HashSet<>();
        moduleLecturers.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        moduleLecturers.setLectuers(correctTagReferences);
    }

    /**
     * Ensures that every lecturer in these modules:
     *  - exists in the master list {@link #modules}
     *  - points to a Lecturer object in the master list
     *  @see #syncMasterLecturerListWith(Module)
     */
    private void syncMasterLecturerListWith(UniqueModuleList modules) {
        modules.forEach(this::syncMasterLecturerListWith);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws ModuleNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removeModule(ReadOnlyModule key) throws ModuleNotFoundException {
        if (modules.remove(key)) {
            return true;
        } else {
            throw new ModuleNotFoundException();
        }
    }

    //// lecturer-level operations

    public void addLecturer(Lecturer t) throws UniqueLecturerList.DuplicateLecturerException {
        lecturers.add(t);
    }

    //// util methods

    @Override
    public String toString() {
        return modules.asObservableList().size() + " modules, " + modules.asObservableList().size() +  " lecturers";
        // TODO: refine later
    }

    @Override
    public ObservableList<ReadOnlyModule> getModuleList() {
        return modules.asObservableList();
    }

    @Override
    public ObservableList<Lecturer> getLecturerList() {
        return lecturers.asObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.modules.equals(((AddressBook) other).modules)
                && this.lecturers.equalsOrderInsensitive(((AddressBook) other).lecturers));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(modules, lecturers);
    }
}
