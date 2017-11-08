# wshijing
###### \java\seedu\address\logic\commands\SortCommand.java
``` java
package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

/**
 * Sort all persons in address book.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_SUCCESS = "Sorted all persons";

    @Override
    public CommandResult execute() {
        model.sortPersonList();
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> photo} into an {@code Optional<Photo>} if {@code photo} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Photo> parsePhoto(Optional<String> photo) throws IllegalValueException {
        requireNonNull(photo);
        return photo.isPresent() ? Optional.of(new Photo(photo.get())) : Optional.empty();
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Sort person list in the address book
     */
    public void sortPersonList() {
        persons.sort();
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    public void sortPersonList() {
        addressBook.sortPersonList();
    }
```
###### \java\seedu\address\model\person\Photo.java
``` java
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents the directory to a person's photo in the address book.
 */
public class Photo {

    public static final String MESSAGE_PHOTO_CONSTRAINTS = "Person's photo should be in format: nameOfFile.png";

    public static final String PHOTO_VALIDATION_REGEX = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)";

    public static final int HEIGHT = 100;
    public static final int WIDTH = 75;

    public static final String BASE_DIR = System.getProperty("user.dir") + "/src/main/resources/person_photos/";

    private String photoDir;

    private String defaultPhoto = "template.png";

    /**
     * Validates given photo.
     *
     * @throws IllegalValueException if given photo address string is invalid.
     */
    public Photo(String photoDir) throws IllegalValueException {
        requireNonNull(photoDir);
        String trimmedPhoto = photoDir.trim();
        if (!isValidPhoto(trimmedPhoto)) {
            throw new IllegalValueException(MESSAGE_PHOTO_CONSTRAINTS);
        }
        this.photoDir = trimmedPhoto;
    }

    public String getPhotoDir() {
        return photoDir;
    }

    public String getFullPhotoDir() {
        return BASE_DIR + photoDir;
    }

    public String getTemplatePhotoDir() {
        return BASE_DIR + defaultPhoto;
    }

    /**
     * Returns if a given string is a valid person photo.
     */
    public static boolean isValidPhoto(String test) {
        return test.matches(PHOTO_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return photoDir;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Photo // instanceof handles nulls
                && this.photoDir.equals(((Photo) other).photoDir)); // state check
    }

    @Override
    public int hashCode() {
        return photoDir.hashCode();
    }

}
```
###### \java\seedu\address\model\person\UniquePersonList.java
``` java
    /**
     * Sort list by name
     */
    public void sort() {
        Collections.sort(internalList, Comparator.comparing(firstPerson -> firstPerson.getName().fullName.replaceFirst(
                "[a-z]{1}", firstPerson.getName().fullName.substring(0, 1).toUpperCase())));
    }

    /**
     * Removes the equivalent person from the list.
     *
     * @throws PersonNotFoundException if no such person could be found in the list.
     */
    public boolean remove(ReadOnlyPerson toRemove) throws PersonNotFoundException {
        requireNonNull(toRemove);
        final boolean personFoundAndDeleted = internalList.remove(toRemove);
        if (!personFoundAndDeleted) {
            throw new PersonNotFoundException();
        }
        return personFoundAndDeleted;
    }

    public void setPersons(UniquePersonList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setPersons(List<? extends ReadOnlyPerson> persons) throws DuplicatePersonException {
        final UniquePersonList replacement = new UniquePersonList();
        for (final ReadOnlyPerson person : persons) {
            replacement.add(new Person(person));
        }
        setPersons(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ReadOnlyPerson> asObservableList() {
        return FXCollections.unmodifiableObservableList(mappedList);
    }

    @Override
    public Iterator<Person> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePersonList // instanceof handles nulls
                        && this.internalList.equals(((UniquePersonList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    /**
     * Initialise each image and assign a person a photo
     */
    private void initPhoto(ReadOnlyPerson person) {
        try {
            File photoFile = new File(person.getPhoto().getFullPhotoDir());
            if (photoFile.exists()) {
                FileInputStream fileStream = new FileInputStream(photoFile);
                Image personPhoto = new Image(fileStream);
                photo = new ImageView(personPhoto);
                photo.setFitHeight(person.getPhoto().HEIGHT);
                photo.setFitWidth(person.getPhoto().WIDTH);
                cardPane.getChildren().add(photo);
            } else {
                File defaultPhotoFile = new File(person.getPhoto().getTemplatePhotoDir());
                FileInputStream defaultFileStream = new FileInputStream(defaultPhotoFile);
                Image defaultPersonPhoto = new Image(defaultFileStream);
                photo = new ImageView(defaultPersonPhoto);

                photo.setFitHeight(person.getPhoto().HEIGHT);
                photo.setFitWidth(person.getPhoto().WIDTH);
                cardPane.getChildren().add(photo);
            }
        } catch (Exception e) {
            System.out.println("Image not found in directory");
        }
    }

```
