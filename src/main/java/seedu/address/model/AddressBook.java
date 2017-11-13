package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.FileUtil.removeAppFile;
import static seedu.address.logic.commands.PhotoCommand.PATH_DEFAULT_PHOTO;
import static seedu.address.logic.commands.PhotoCommand.PATH_FILE_SAVED_PARENT_DIRECTORY;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonHasBeenDeletedEvent;
import seedu.address.commons.events.ui.PersonHasBeenModifiedEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.photo.PhotoPath;
import seedu.address.model.photo.UniquePhotoPathList;
import seedu.address.model.photo.exceptions.DuplicatePhotoPathException;
import seedu.address.model.photo.exceptions.PhotoPathNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.tag.exceptions.TagNotFoundException;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueTagList tags;
    private final UniquePhotoPathList photoPaths;
    private final HashMap<String, String> themes;

    private final Logger logger = LogsCenter.getLogger(this.getClass());


    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        tags = new UniqueTagList();
        photoPaths = new UniquePhotoPathList();
        themes = new HashMap<String, String>();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons, PhotoPaths and Tags in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        initialiseThemes();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    public void setPersons(List<? extends ReadOnlyPerson> persons) throws DuplicatePersonException {
        this.persons.setPersons(persons);
    }


    public void setTags(Set<Tag> tags) {
        this.tags.setTags(tags);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        try {
            setPersons(newData.getPersonList());
        } catch (DuplicatePersonException e) {
            assert false : "AddressBook should not have duplicate persons";
        }

        setTags(new HashSet<>(newData.getTagList()));

        try {
            setPhotoPaths(newData.getPhotoPathList());
        } catch (DuplicatePhotoPathException e) {
            assert false : "AddressBook should not have duplicate photo paths";
        }

        syncMasterTagListWith(persons);
    }

    //// person-level operations

    /**
     * Adds a person to the address book.
     * Also checks the new person's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the person to point to those in {@link #tags}.
     *
     * @throws DuplicatePersonException if an equivalent person already exists.
     */
    public void addPerson(ReadOnlyPerson p) throws DuplicatePersonException {
        Person newPerson = new Person(p);
        syncMasterTagListWith(newPerson);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        persons.add(newPerson);
    }


    /**
     * Replaces the given person {@code target} in the list with {@code editedReadOnlyPerson}.
     * {@code AddressBook}'s tag list will be updated with the tags of {@code editedReadOnlyPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     *
     * @see #syncMasterTagListWith(Person)
     */
    public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedReadOnlyPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireNonNull(editedReadOnlyPerson);

        Person editedPerson = new Person(editedReadOnlyPerson);
        syncMasterTagListWith(editedPerson);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        persons.setPerson(target, editedPerson);
        EventsCenter.getInstance().post(new PersonHasBeenModifiedEvent(target, editedPerson));
    }

    /**
     * Ensures that every tag in this person:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */
    private void syncMasterTagListWith(Person person) {
        final UniqueTagList personTags = new UniqueTagList(person.getTags());
        tags.mergeFrom(personTags);

        // Create map with values = tag object references in the master list
        // used for checking person tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of person tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        personTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        person.setTags(correctTagReferences);
    }

    /**
     * Ensures that every tag in these persons:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     *  @see #syncMasterTagListWith(Person)
     */
    private void syncMasterTagListWith(UniquePersonList persons) {
        persons.forEach(this::syncMasterTagListWith);
    }

    //author@@ nbriannl
    /**
     * Checks if the master list {@link #tags} has every tag being used.
     *  @return true if all tags in the master list is being used by a person
     */
    public boolean hasAllTagsInUse () {
        HashSet<Tag> masterSet = new HashSet<Tag>();
        for (ReadOnlyPerson person: persons) {
            masterSet.addAll(person.getTags());
        }
        return masterSet.containsAll(tags.toSet());
    }

    /**
     *  Gets the tags in the master list {@link #tags} that is not being used
     *  @return {@code Set<Tag>} of Tags not being used by any person
     *  @see #hasAllTagsInUse()
     */
    public Set<Tag> getUnusedTags () {
        HashSet<Tag> actualSet = new HashSet<Tag>();
        for (ReadOnlyPerson person: persons) {
            actualSet.addAll(person.getTags());
        }
        Set<Tag> masterSet = tags.toSet();

        masterSet.removeAll(actualSet);
        return masterSet;
    }


    //@@author April0616
    /**
     * Sets a list of photo paths to the address book.
     * @param photoPaths
     * @throws DuplicatePhotoPathException if an equivalent photo path already exists.
     */
    public void setPhotoPaths(List<PhotoPath> photoPaths) throws DuplicatePhotoPathException {
        this.photoPaths.setPhotoPaths(photoPaths);
    }

    /**
     * Adds a new photo path to the address book.
     * @throws DuplicatePhotoPathException if an equivalent photo path already exists.
     */
    public void addPhotoPath(PhotoPath newPhotoPath) throws DuplicatePhotoPathException {
        photoPaths.add(newPhotoPath);
    }

    /**
     * Checks if the master list {@link #photoPaths} has every photo path being used.
     * @return true if all photo paths in the master list are being used
     */
    public boolean hasAllPhotoPathsInUse () {
        List<PhotoPath> masterList = new ArrayList<>();
        for (ReadOnlyPerson person: persons) {
            masterList.add(person.getPhotoPath());
        }
        return masterList.containsAll(photoPaths.toList());
    }

    /**
     *  Gets the unused photo paths in the master list {@link #photoPaths}
     *  @return {@code List<PhotoPath>} of photo paths not being used by any person
     *  @see #hasAllPhotoPathsInUse()
     */
    public List<PhotoPath> getUnusedPhotoPaths () {
        List<PhotoPath> actualList = new ArrayList<>();
        for (ReadOnlyPerson person: persons) {
            PhotoPath thisPhotoPath = person.getPhotoPath();
            if (!thisPhotoPath.value.equals("")) {
                actualList.add(thisPhotoPath);
            }
        }
        List<PhotoPath> masterList = photoPaths.toList();

        masterList.removeAll(actualList);
        return masterList;
    }

    /**
     * Removes all the unused photos specified by the unused photo paths
     * @see #getUnusedPhotoPaths()
     */
    public void removeAllUnusedPhotosAndPaths() throws PhotoPathNotFoundException {
        List<PhotoPath> unusedPhotoPathList = getUnusedPhotoPaths();
        for (PhotoPath unusedPhotoPath : unusedPhotoPathList) {
            removeContactPhoto(unusedPhotoPath);
            this.photoPaths.remove(unusedPhotoPath);

            logger.info("Delete photo and its path: " + unusedPhotoPath);
        }
    }

    /**
     * Updates the master list of photo paths saved in the default folder of this
     * address book and delete the empty paths.
     */
    public void updatePhotoPathSavedInMasterList() {
        final File folder = new File(PATH_FILE_SAVED_PARENT_DIRECTORY);
        if (!folder.exists()) {
            return;
        }

        if (!folder.isDirectory() || folder.listFiles() == null) {
            assert false : "Does not exist a default folder to save photos or it has no files!";
            return;
        }

        for (File photo : folder.listFiles()) {
            try {
                // covert the photo path string to standard format in the app
                String photoPathString = photo.getPath().replace("\\", "/");
                PhotoPath thisPhotoPath = new PhotoPath(photoPathString);

                if (!this.photoPaths.contains(thisPhotoPath)) {
                    this.photoPaths.add(thisPhotoPath);
                }
            } catch (IllegalValueException e) {
                assert false : "The string of the photo path has wrong format!";
            }
        }

        // delete empty path in the master list
        for (PhotoPath photoPath : this.photoPaths) {
            if (photoPath.value.equals("")) {
                try {
                    this.photoPaths.remove(photoPath);
                } catch (PhotoPathNotFoundException e) {
                    assert false : "This photo path cannot be found: " + photoPath;
                }
            }
            if (this.photoPaths.size() == 0) {
                break;
            }
        }
    }

    /**
     * Removes the photo of the specified contact.
     * @param photoPath of the photo to be removed
     */
    public void removeContactPhoto(PhotoPath photoPath) {
        removeAppFile(photoPath.value);
    }

    /**
     * Checks whether the contact photo is the default photo
     * @param photoPath of the photo
     * @return true if the photo is the default photo
     */
    public static boolean isDefaultPhoto(PhotoPath photoPath) {
        String photoPathValue = photoPath.value;
        return photoPathValue.equals(PATH_DEFAULT_PHOTO);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}
     * @throws PersonNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removePerson(ReadOnlyPerson key) throws PersonNotFoundException {
        if (persons.remove(key)) {
            EventsCenter.getInstance().post(new PersonHasBeenDeletedEvent(key));
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes {@code keys} from this {@code AddressBook}
     * @throws PersonNotFoundException if one of the {@code keys} is not in this {@code AddressBook}.
     */
    public boolean removePersons(ArrayList<ReadOnlyPerson> keys) throws PersonNotFoundException {
        for (ReadOnlyPerson key : keys) {
            removePerson(key);
        }
        return true;
    }
    //@@author

    //// Tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    //@@author nbriannl
    /**
     * Removes {@code tag} from this {@code AddressBook}.
     * @throws TagNotFoundException if the {@code tag} is not in this {@code AddressBook}.
     */
    public boolean removeTag(Tag tag) throws TagNotFoundException {
        if (tags.remove(tag)) {
            return true;
        } else {
            throw new TagNotFoundException();
        }
    }

    //// Theme-level operations

    //@@author nbriannl
    /**
     * Initialises the themes in the address book.
     */
    private void initialiseThemes() {
        themes.put("light", "LightTheme.css");
        themes.put("dark", "DarkTheme.css");
    }

    HashMap<String, String> getThemeMap () {
        return themes;
    }

    //@@author
    //// util methods

    @Override
    public String toString() {
        return persons.asObservableList().size() + " persons, " + tags.asObservableList().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public ObservableList<ReadOnlyPerson> getPersonList() {
        return persons.asObservableList();
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asObservableList();
    }

    @Override
    public ObservableList<PhotoPath> getPhotoPathList() {
        return photoPaths.asObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.persons.equals(((AddressBook) other).persons)
                && this.tags.equalsOrderInsensitive(((AddressBook) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(persons, tags);
    }
}
