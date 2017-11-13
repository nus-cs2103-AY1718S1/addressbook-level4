# soonwj
###### \java\seedu\address\logic\commands\DeleteProfilePicCommand.java
``` java
/**
 * Deletes the profile picture of a person identified using it's last displayed index from the address book.
 */
public class DeleteProfilePicCommand extends Command {
    public static final String COMMAND_WORD = "deleteProfilePic";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the profile picture of the person identified by the index number used in "
            + "the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    public static final String MESSAGE_DELETE_PROFILE_PIC_SUCCESS = "Deleted Profile Picture of Person: %1$s";

    private final Index targetIndex;

    public DeleteProfilePicCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson profilePicToDelete = lastShownList.get(targetIndex.getZeroBased());

        UpdateProfilePicCommand updateToDefault = new UpdateProfilePicCommand(targetIndex, new ProfilePic());
        updateToDefault.setData(model, history, undoRedoStack);
        updateToDefault.execute();

        return new CommandResult(String.format(MESSAGE_DELETE_PROFILE_PIC_SUCCESS, profilePicToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteProfilePicCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteProfilePicCommand) other).targetIndex)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\UpdateProfilePicCommand.java
``` java
/**
 * Updates the profile picture of an existing person in the address book.
 */

public class UpdateProfilePicCommand extends Command {
    public static final String COMMAND_WORD = "updateProfilePic";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the profile picture of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing profile picture will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[u/valid URL of image]\n"
            + "Example of image stored locally: " + COMMAND_WORD + " 1 "
            + PREFIX_IMAGE_URL + "file:///C:/Users/Bobby/Images/picture.jpg\n"
            + "Example of image stored on the internet: " + COMMAND_WORD + " 1 "
            + PREFIX_IMAGE_URL
            + "https://koreaboo.global.ssl.fastly.net/wp-content/uploads/2017/04/Girls-Generation-Sooyoung.jpg\n";

    public static final String MESSAGE_UPDATE_PROFILE_PIC_SUCCESS = "Update profile pic of Person: %1$s";
    public static final String MESSAGE_NOT_UPDATED = "Please enter a valid image URL.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final ProfilePic profilePic;

    private boolean isOldFileDeleted = true;

    /**
     *
     * @param index of the person in the filtered person list to update profile picture
     * @param profilePic of the image to be used as the profile picture
     */
    public UpdateProfilePicCommand(Index index, ProfilePic profilePic) {
        requireNonNull(index);
        requireNonNull(profilePic);

        this.index = index;
        this.profilePic = profilePic;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToUpdateProfilePic = lastShownList.get(index.getZeroBased());
        Person updatedProfilePicPerson = new Person(personToUpdateProfilePic);
        ProfilePic newProfilePic;

        if (profilePic.toString().compareTo(ProfilePic.DEFAULT_URL) == 0) {
            String oldFile = personToUpdateProfilePic.getProfilePic().toString();
            if (oldFile.compareTo(ProfilePic.DEFAULT_URL) != 0) {
                oldFile = urlToPath(oldFile);
                try {
                    Files.delete(Paths.get(oldFile));
                } catch (IOException ioe) {
                    isOldFileDeleted = false;
                }
            }
            newProfilePic = profilePic;
        } else {
            String newFile;
            if (!Files.isDirectory(Paths.get("ProfilePics"))) {
                try {
                    Files.createDirectory(Paths.get("ProfilePics"));
                } catch (IOException ioe) {
                    throw new CommandException("ProfilePics directory failed to be created");
                }
            }
            if (personToUpdateProfilePic.getProfilePic().toString().compareTo(ProfilePic.DEFAULT_URL) == 0) {
                newFile = "ProfilePics/" + new Date().getTime() + ".png";
            } else {
                newFile = personToUpdateProfilePic.getProfilePic().toString();
                newFile = urlToPath(newFile);
            }
            if (!Files.exists(Paths.get(newFile))) {
                try {
                    Files.createFile(Paths.get(newFile));
                } catch (IOException ioe) {
                    throw new CommandException("New file failed to be created");
                }
            }
            try {
                URL url = new URL(profilePic.toString());
                InputStream in = url.openStream();
                Files.copy(in, Paths.get(newFile), StandardCopyOption.REPLACE_EXISTING);
                in.close();
                newProfilePic = new ProfilePic("file://" + Paths.get(newFile).toAbsolutePath().toUri().getPath());
            } catch (IOException ioe) {
                throw new CommandException("Image failed to download");
            } catch (IllegalValueException ive) {
                throw new CommandException(ive.getMessage());
            }
        }
        updatedProfilePicPerson.setProfilePic(newProfilePic);

        try {
            model.updatePerson(personToUpdateProfilePic, updatedProfilePicPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        if (profilePic.toString().compareTo(ProfilePic.DEFAULT_URL) != 0) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        }
        String resultMessage = String.format(MESSAGE_UPDATE_PROFILE_PIC_SUCCESS, personToUpdateProfilePic);
        if (isOldFileDeleted) {
            return new CommandResult(resultMessage);
        } else {
            return new CommandResult(String.join("\n", resultMessage, "Old image not deleted"));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UpdateProfilePicCommand // instanceof handles nulls
                && this.index.equals(((UpdateProfilePicCommand) other).index)
                && this.profilePic.equals(((UpdateProfilePicCommand) other).profilePic)); // state check
    }

    private String urlToPath(String url) {
        return url.substring(url.indexOf("ProfilePics"));
    }
}
```
###### \java\seedu\address\logic\parser\DeleteProfilePicCommandParser.java
``` java
/**
 * Parses input arguments and creates a new DeleteProfilePicCommand object
 */
public class DeleteProfilePicCommandParser implements Parser<DeleteProfilePicCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteProfilePicCommand
     * and returns an DeleteProfilePicCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteProfilePicCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteProfilePicCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteProfilePicCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> imageURL} into an {@code Optional<ProfilePic>} if {@code imageURL} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<ProfilePic> parseImageUrl(Optional<String> imageUrl) throws IllegalValueException {
        requireNonNull(imageUrl);
        return imageUrl.isPresent() ? Optional.of(new ProfilePic(imageUrl.get())) : Optional.empty();
    }
```
###### \java\seedu\address\logic\parser\UpdateProfilePicCommandParser.java
``` java
/**
 * Parses input arguments and creates a new UpdateProfilePicCommand object
 */
public class UpdateProfilePicCommandParser implements Parser<UpdateProfilePicCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the UpdateProfilePicCommand
     * and returns an UpdateProfilePicCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    @Override
    public UpdateProfilePicCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_IMAGE_URL);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    UpdateProfilePicCommand.MESSAGE_USAGE));
        }

        Person updatedPerson = new Person(SampleDataUtil.getSamplePersons()[0]);

        try {
            ParserUtil.parseImageUrl(argMultimap.getValue(PREFIX_IMAGE_URL)).ifPresent(updatedPerson::setProfilePic);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (updatedPerson.getProfilePic().toString().compareTo(ProfilePic.DEFAULT_URL) == 0) {
            throw new ParseException(UpdateProfilePicCommand.MESSAGE_NOT_UPDATED);
        }

        return new UpdateProfilePicCommand(index, updatedPerson.getProfilePic());
    }
}
```
###### \java\seedu\address\MainApp.java
``` java
    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (event.getNewSelection().person != null) {
            Person personSelected = new Person(event.getNewSelection().person);
            personSelected.setViewCount(personSelected.getViewCount() + 1);
            try {
                model.updatePerson(event.getNewSelection().person, personSelected);
            } catch (DuplicatePersonException dpe) {
                assert false : "Impossible to be duplicate";
            } catch (PersonNotFoundException pnfe) {
                assert false : "Impossible not to be found";
            }
        }
    }
```
###### \java\seedu\address\model\Model.java
``` java
    /**
     * Sorts all the persons in the address book from most selected to least selected.
     */
    void sortByViewCount();
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void sortByViewCount() {
        AddressBook addressBookToSort = new AddressBook(addressBook);
        ObservableList<ReadOnlyPerson> listToSort = addressBookToSort.getPersonList();
        ArrayList<ReadOnlyPerson> listToSortedCopy = new ArrayList<>();
        for (ReadOnlyPerson r : listToSort) {
            listToSortedCopy.add(r);
        }
        Collections.sort(listToSortedCopy, new ViewCountComparator());

        try {
            addressBookToSort.setPersons(listToSortedCopy);
        } catch (DuplicatePersonException dpe) {
            assert false : "Impossible to be duplicate";
        }

        resetData(addressBookToSort);
    }
```
###### \java\seedu\address\model\person\Person.java
``` java
    @Override
    public ObjectProperty<ProfilePic> profilePicProperty() {
        return profilePic;
    }

    @Override
    public ProfilePic getProfilePic() {
        return profilePic.get();
    }

    @Override
    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int newViewCount) {
        viewCount = newViewCount;
    }
```
###### \java\seedu\address\model\person\ProfilePic.java
``` java
/**
 * Represents a Person's profile picture in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidUrl(String)}
 */
public class ProfilePic {
    public static final String MESSAGE_PROFILE_PIC_CONSTRAINTS =
            "Person profile pictures must be a valid image URL, and it should not be blank";
    public static final String DEFAULT_URL = "images/default.png";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String PROFILE_PIC_VALIDATION_REGEX = "[^\\s].*";

    public final String source;

    public ProfilePic() {
        source = DEFAULT_URL;
    }

    /**
     * Validates given address.
     *
     * @throws IllegalValueException if given profilePic string is invalid.
     */
    public ProfilePic(String url) throws IllegalValueException {
        requireNonNull(url);
        if (!isValidUrl(url)) {
            throw new IllegalValueException(MESSAGE_PROFILE_PIC_CONSTRAINTS);
        }

        source = url;
    }

    /**
     * Returns true if a given string is a valid image URL.
     */
    public static boolean isValidUrl(String test) {
        if (test.matches(PROFILE_PIC_VALIDATION_REGEX)) {
            try {
                Image img = ImageIO.read(new URL(test));
                if (img == null) {
                    return false;
                }
            } catch (IOException e) {
                if (test.compareTo(DEFAULT_URL) == 0) {
                    return true;
                } else {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return source;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProfilePic // instanceof handles nulls
                && this.source.equals(((ProfilePic) other).source)); // state check
    }

    @Override
    public int hashCode() {
        return source.hashCode();
    }
}
```
###### \java\seedu\address\model\person\ReadOnlyPerson.java
``` java
    ObjectProperty<ProfilePic> profilePicProperty();
    ProfilePic getProfilePic();
    int getViewCount();
```
###### \java\seedu\address\model\util\ViewCountComparator.java
``` java
/**
 * Compares the view counts of 2 ReadOnlyPersons
 */
public class ViewCountComparator implements Comparator<ReadOnlyPerson> {
    @Override
    public int compare(ReadOnlyPerson r1, ReadOnlyPerson r2) {
        int r1ViewCount = r1.getViewCount();
        int r2ViewCount = r2.getViewCount();

        if (r1ViewCount < r2ViewCount) {
            return 1;
        } else if (r1ViewCount > r2ViewCount) {
            return -1;
        } else {
            return 0;
        }
    }
}
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    private int[] getTagColour(String tag) {
        int[] rgb = {random.nextInt(256), random.nextInt(256), random.nextInt(256)};

        if (!tagColour.containsKey(tag)) {
            tagColour.put(tag, rgb);
        }

        return tagColour.get(tag);
    }
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    /**
     * Initializes the profile picture to be displayed by the PersonCard
     * @param person The person whose information is to be displayed in the PersonCard
     */
    private void initProfilePic(ReadOnlyPerson person) {
        String url = person.getProfilePic().toString();
        if (!ProfilePic.isValidUrl(url)) {
            url = ProfilePic.DEFAULT_URL;
        }
        imageView.setImage(new Image(url, 128, 128, true, false));
    }
```
###### \java\seedu\address\ui\StatusBarFooter.java
``` java
    private void setTotalPersons(String totalPersons) {
        Platform.runLater(() -> this.totalPersons.setText(totalPersons + " person(s) total"));
    }
```
