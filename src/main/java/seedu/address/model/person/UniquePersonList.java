package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Person#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniquePersonList implements Iterable<Person> {

    private static final Stack<String> photoStack = new Stack<String>();
    private static final int ONLY_PHOTO_CHANGED = 1;
    private static final int ONLY_EMAIL_CHANGED = 2;
    private static final int BOTH_PHOTO_AND_EMAIL_CHANGED = 3;
    private static final int NEITHER_PHOTO_OR_EMAIL_CHANGED = 4;
    private static final String editedFolder = "data/edited/";
    private static final String photoFolder = "data/images/";
    private static final String photoFileType = ".jpg";
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    private final ObservableList<Person> internalList = FXCollections.observableArrayList();
    // used by asObservableList()
    private final SortedList<Person> sortedInternalList = new SortedList<Person>(internalList);
    private final ObservableList<ReadOnlyPerson> mappedList = EasyBind.map(sortedInternalList, (person) -> person);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(ReadOnlyPerson toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a person to the list.
     *
     * @throws DuplicatePersonException if the person to add is a duplicate of an existing person in the list.
     */
    public void add(ReadOnlyPerson toAdd) throws DuplicatePersonException, IOException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }

        Person person = new Person(toAdd);
        String intendedPhotoPath = photoFolder + toAdd.getEmailAddress().toString() + photoFileType;
        createCurrentPhoto(toAdd.getPhoto().toString(), toAdd.getEmailAddress().toString());
        person = updatePhoto(person, intendedPhotoPath);
        internalList.add(new Person(person));
        sortInternalList();
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     *
     * @throws DuplicatePersonException if the replacement is equivalent to another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    public void setPerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException, IOException {
        requireNonNull(editedPerson);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.equals(editedPerson) && internalList.contains(editedPerson)) {
            throw new DuplicatePersonException();
        }
        Person person = new Person(editedPerson);
        String intendedPhotoPath = photoFolder + editedPerson.getEmailAddress().toString() + photoFileType;
        boolean deleteFile = false;

        int option = updateCasesForPhoto(target, editedPerson);

        switch(option) {

        case ONLY_PHOTO_CHANGED:
            createBackUpPhoto(intendedPhotoPath, editedPerson.getEmailAddress().toString());
            createCurrentPhoto(editedPerson.getPhoto().toString(), editedPerson.getEmailAddress().toString());

            person = updatePhoto(person, intendedPhotoPath);
            logger.info("Photo edited");
            break;

        case ONLY_EMAIL_CHANGED:
            createBackUpPhoto(target.getPhoto().toString(), target.getEmailAddress().toString());
            createCurrentPhoto(editedPerson.getPhoto().toString(), editedPerson.getEmailAddress().toString());

            person = updatePhoto(person, intendedPhotoPath);
            deleteFile = true;
            logger.info("Email edited");
            break;

        case BOTH_PHOTO_AND_EMAIL_CHANGED:
            createBackUpPhoto(target.getPhoto().toString(), target.getEmailAddress().toString());
            createCurrentPhoto(editedPerson.getPhoto().toString(), editedPerson.getEmailAddress().toString());

            person = updatePhoto(person, intendedPhotoPath);
            deleteFile = true;
            logger.info("Both Photo and Email edited");
            break;

        case NEITHER_PHOTO_OR_EMAIL_CHANGED:
            logger.info("Neither Photo and Email edited");
            break;

        default:
            throw new AssertionError("Shouldn't be here");
        }

        internalList.set(index, new Person(person));
        sortInternalList();

        if (deleteFile) {
            deleteExistingPhoto(target.getPhoto().toString());
        }
    }

    /**
     * Removes the equivalent person from the list.
     *
     * @throws PersonNotFoundException if no such person could be found in the list.
     */
    public boolean remove(ReadOnlyPerson toRemove) throws PersonNotFoundException, IOException {
        requireNonNull(toRemove);
        sortInternalList();
        final boolean personFoundAndDeleted = internalList.remove(toRemove);
        if (!personFoundAndDeleted) {
            throw new PersonNotFoundException();
        }

        createBackUpPhoto(toRemove.getPhoto().toString(), toRemove.getEmailAddress().toString());
        deleteExistingPhoto(toRemove.getPhoto().toString());
        return personFoundAndDeleted;
    }

    public void setPersons(UniquePersonList replacement) {
        this.internalList.setAll(replacement.internalList);
        sortInternalList();
    }


    public void setPersons(List<? extends ReadOnlyPerson> persons) throws DuplicatePersonException, IOException {
        final UniquePersonList replacement = new UniquePersonList();
        for (final ReadOnlyPerson person : persons) {
            File image = new File(person.getPhoto().toString());

            File toBeCopied = new File(editedFolder + person.getEmailAddress().toString() + photoFileType);
            if (!FileUtil.isFileExists(image)) {
                toBeCopied = new File(photoStack.pop());
                copyBackupPhoto(person, toBeCopied);
            } else {
                comparePhotoHash(person, toBeCopied);
            }
            replacement.add(new Person(person));
        }
        setPersons(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ReadOnlyPerson> asObservableList() {
        sortInternalList();
        return FXCollections.unmodifiableObservableList(mappedList);
    }

    //@@author wenzongteo
    /**
     * Accessor to allow other classes to push into the edited photo stack when they delete photos from Augustine.
     * @param photoPath photoPath in data/edited/ folder.
     */
    public static void addToPhotoStack(String photoPath) {
        photoStack.push(photoPath);
    }

    /**
     * Calculates the MD5 hash value of the person's display picture in {@code srcPath}.
     * returns the calculated hash in {@code hashValue}.
     *
     * @throws IOException if the srcPath cannot be found in the system.
     * @throws NoSuchAlgorithmException if the algorithm used for hashing is invalid.
     */
    public String calculateHash(String srcPath) throws IOException, NoSuchAlgorithmException {
        MessageDigest hashing;
        hashing = MessageDigest.getInstance("MD5");

        File existingImage = new File(srcPath);
        String hashValue = new String(hashing.digest(Files.readAllBytes(existingImage.toPath())));

        return hashValue;
    }

    /**
     * Creates a backup copy of the person's display picture in {@code srcPath} to {@code destPath}.
     *
     * @throws IOException if the srcPath cannot be found in the system.
     */
    public void createBackUpPhoto(String srcPath, String emailAddr) throws IOException {
        String destPath = editedFolder + emailAddr + photoFileType;
        int counter = 0;

        while (FileUtil.isFileExists(new File(destPath))) {
            counter++;
            destPath = editedFolder + emailAddr + counter + photoFileType;
        }

        Files.copy(Paths.get(srcPath), Paths.get(destPath), StandardCopyOption.REPLACE_EXISTING);
        photoStack.push(destPath);
        logger.info("Image for " + emailAddr + photoFileType + " copied to " + editedFolder);
    }

    /**
     * Creates a local copy of the person's display picture in {@code srcPath} to {@code destPath}.
     *
     * @throws IOException if the srcPath cannot be found in the system.
     */
    public void createCurrentPhoto(String srcPath, String emailAddr) throws IOException {
        String destPath = photoFolder + emailAddr + photoFileType;
        if (!srcPath.equals(destPath)) {
            Files.copy(Paths.get(srcPath), Paths.get(destPath), StandardCopyOption.REPLACE_EXISTING);
        }
        logger.info("Image for " + emailAddr + photoFileType + " copied to " + photoFolder);
    }

    /**
     * Deletes the existing copy of the person's display picture in {@code srcPath}.
     *
     * @throws IOException if the srcPath cannot be found in the system.
     */
    public void deleteExistingPhoto(String srcPath) throws IOException {
        Files.delete(Paths.get(srcPath));
        logger.info("Image " + srcPath + " deleted");
    }

    /**
     * Update the photo of the person in (@code person) to reflect the new address of local file.
     *
     * @return the updated person
     */
    public Person updatePhoto(Person person, String srcPath) {
        person.setPhoto(new Photo(srcPath, 0));
        return person;
    }

    /**
     * Check if the photo or email of the person is changed during the edit command.
     *
     * @param target Original Person in Augustine
     * @param editedPerson Edited Person by the user
     * @return which case of change occurred.
     */
    public int updateCasesForPhoto(ReadOnlyPerson target, ReadOnlyPerson editedPerson) {
        if (target.getEmailAddress().equals(editedPerson.getEmailAddress())
                && !target.getPhoto().equals(editedPerson.getPhoto())) { //Only Photo changed.
            return ONLY_PHOTO_CHANGED;
        } else if (!target.getEmailAddress().equals(editedPerson.getEmailAddress())
                && target.getPhoto().equals(editedPerson.getPhoto())) { //only email changed.
            return ONLY_EMAIL_CHANGED;
        } else if (!target.getEmailAddress().equals(editedPerson.getEmailAddress())
                && !target.getPhoto().equals(editedPerson.getPhoto())) { //Both changed.
            return BOTH_PHOTO_AND_EMAIL_CHANGED;
        } else if (target.getEmailAddress().equals(editedPerson.getEmailAddress())
                && target.getPhoto().equals(editedPerson.getPhoto())) { //No special update
            return NEITHER_PHOTO_OR_EMAIL_CHANGED;
        } else {
            throw new AssertionError("Shouldn't be here");
        }
    }

    /**
     * Copy the contact's photo from data/edited folder to data/images folder when an undo command is executed.
     *
     * @param person the edited person.
     * @param toBeCopied Previous photo of the person.
     */
    private void copyBackupPhoto (ReadOnlyPerson person, File toBeCopied) {
        try {
            if (FileUtil.isFileExists(toBeCopied)) {
                createCurrentPhoto(toBeCopied.toString(), person.getEmailAddress().toString());
            }
        } catch (IOException ioe) {
            throw new AssertionError("Photo does not exist");
        }
    }

    /**
     * Compare the hash of the contact's current photo and the stored photo in the Person's Photo Object to ensure that
     * the photo is correct.
     *
     * @param person person who the photos belong to.
     * @param toBeCopied previous photo of the person.
     */
    private void comparePhotoHash (ReadOnlyPerson person, File toBeCopied) {
        try {
            String hashValue = calculateHash(person.getPhoto().toString());
            if (!hashValue.equals(person.getPhoto().getHash())) { //Not equal, go take the old image
                createCurrentPhoto(photoStack.pop(), person.getEmailAddress().toString());
                //createCurrentPhoto(toBeCopied.toString(), person.getEmailAddress().toString());
            }
        } catch (IOException ioe) {
            throw new AssertionError("Photo does not exist!");
        } catch (NoSuchAlgorithmException nsa) {
            throw new AssertionError("Impossible");
        }
    }
    //@@author

    //@@author awarenessxz
    /**
     * Sort the list in lexicographically name order.
     */
    private void sortInternalList() {
        Comparator<Person> sort = new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.getName().fullName.toUpperCase().compareTo(o2.getName().fullName.toUpperCase());
            }
        };
        sortedInternalList.setComparator(sort);
    }
    //@@author

    @Override
    public Iterator<Person> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePersonList // instanceof handles nulls
                && this.sortedInternalList.equals(((UniquePersonList) other).sortedInternalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

}
