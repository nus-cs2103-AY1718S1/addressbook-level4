package seedu.address.model.photo;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.photo.exceptions.DuplicatePhotoPathException;
import seedu.address.model.photo.exceptions.PhotoPathNotFoundException;



/**
 * A list of PhotoPaths that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see PhotoPath#equals(Object)
 */
public class UniquePhotoPathList implements Iterable<PhotoPath> {

    private final ObservableList<PhotoPath> internalList = FXCollections.observableArrayList();
    // used by asObservableList()
    private final ObservableList<PhotoPath> mappedList = EasyBind.map(internalList, (photoPath) -> photoPath);

    /**
     * Returns true if the list contains an equivalent PhotoPath as the given argument.
     */
    public boolean contains(PhotoPath toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a PhotoPath to the list.
     *
     * @throws DuplicatePhotoPathException if the PhotoPath to add is a duplicate of an existing PhotoPath in the list.
     */
    public void add(PhotoPath toAdd) throws DuplicatePhotoPathException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePhotoPathException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the photoPath {@code target} in the list with {@code editedPhotoPath}.
     *
     * @throws DuplicatePhotoPathException if the replacement is equivalent to another existing PhotoPath in the list.
     * @throws PhotoPathNotFoundException if {@code target} could not be found in the list.
     */
    public void setPhotoPath(PhotoPath target, PhotoPath editedPhotoPath)
            throws DuplicatePhotoPathException, PhotoPathNotFoundException {
        requireNonNull(editedPhotoPath);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PhotoPathNotFoundException();
        }

        if (!target.equals(editedPhotoPath) && internalList.contains(editedPhotoPath)) {
            throw new DuplicatePhotoPathException();
        }

        internalList.set(index, editedPhotoPath);
    }

    /**
     * Removes the equivalent PhotoPath from the list.
     *
     * @throws PhotoPathNotFoundException if no such PhotoPath could be found in the list.
     */
    public boolean remove(PhotoPath toRemove) throws PhotoPathNotFoundException {
        requireNonNull(toRemove);
        final boolean photoPathFoundAndDeleted = internalList.remove(toRemove);
        if (!photoPathFoundAndDeleted) {
            throw new PhotoPathNotFoundException();
        }
        return photoPathFoundAndDeleted;
    }

    public void setPhotoPaths(UniquePhotoPathList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setPhotoPaths(List<PhotoPath> photoPaths) throws DuplicatePhotoPathException {
        final UniquePhotoPathList replacement = new UniquePhotoPathList();
        for (final PhotoPath photoPath : photoPaths) {
            replacement.add(photoPath);
        }
        setPhotoPaths(replacement);
    }

    /**
     * Returns all photo paths in this list as a list.
     * This list is mutable and change-insulated against the internal list.
     */
    public List<PhotoPath> toList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new ArrayList<>(internalList);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<PhotoPath> asObservableList() {
        return FXCollections.unmodifiableObservableList(mappedList);
    }

    /**
     * Get the size of the internal list.
     * @return how many photo paths in the unique photo list.
     */
    public int size() {
        return internalList.size();
    }

    @Override
    public Iterator<PhotoPath> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePhotoPathList // instanceof handles nulls
                        && this.internalList.equals(((UniquePhotoPathList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
