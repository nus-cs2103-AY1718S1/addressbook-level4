# April0616
###### \java\seedu\address\commons\util\FileUtil.java
``` java
    /**
     * Checks whether the name of the file has extension.
     * @param filePath of the file
     * @return true if it has specified extension
     */
    public static Boolean hasFileExtension(String filePath) {
        String[] parts = filePath.split(REGEX_DOT);
        return parts.length == 2;
    }

    /**
     * Gets the extension of the file path by split the path string by regex "."
     * @param filePath
     * @return extension string
     */
    public static String getFileExtension(String filePath) {
        return "." + filePath.split("\\.")[1];
    }

    /**
     * Checks whether the specified file is in the specified folder.
     * @param filePath of the file to be checked
     * @param folderPath of the folder
     * @return true if the file is in the folder
     */
    public static Boolean isInFolder(String filePath, String folderPath) {
        return filePath.startsWith(folderPath);
    }
    /**
     * Copies all the contents from the file in original path to the one in destination path.
     * @param oriPath of the file to be copied
     * @param destPath of the file to be pasted
     * @return true if the file is successfully copied to the specified place.
     */
    public static boolean copyFile(String oriPath, String destPath) throws IOException {

        //create a buffer to store content
        byte[] buffer = new byte[1024];

        //bufferedInputStream
        FileInputStream fis = new FileInputStream(oriPath);
        BufferedInputStream bis = new BufferedInputStream(fis);

        //bufferedOutputStream
        FileOutputStream fos = new FileOutputStream(destPath);
        BufferedOutputStream bos = new BufferedOutputStream(fos);

        int numBytes = bis.read(buffer);
        while (numBytes > 0) {
            bos.write(buffer, 0, numBytes);
            numBytes = bis.read(buffer);
        }

        //close input,output stream
        bis.close();
        bos.close();

        return true;
    }

    /**
     * Removes the file in the app if it exists.
     * @param path of the file to be deleted
     */
    public static void removeAppFile(String path) {
        File fileToDelete = new File(path);
        if (fileToDelete.exists()) {
            fileToDelete.delete();
        }
    }

    /**
     * Checks whether two files have the same content.
     * @param firstPath path of one file
     * @param secondPath path of another file
     * @return true if they have the same content, false otherwise
     * @throws IOException if an I/O error occurs reading from the stream
     */
    public static boolean haveSameContent(String firstPath, String secondPath) {
        Path p1 = Paths.get(firstPath);
        Path p2 = Paths.get(secondPath);

        byte[] firstFileBytes = new byte[0];
        try {
            firstFileBytes = Files.readAllBytes(p1);
        } catch (IOException e) {
            assert false : "An I/O error occurs reading from the stream.";
        }

        byte[] secondFileBytes = new byte[0];
        try {
            secondFileBytes = Files.readAllBytes(p2);
        } catch (IOException e) {
            assert false : "An I/O error occurs reading from the stream.";
        }
        return Arrays.equals(firstFileBytes, secondFileBytes);
    }
```
###### \java\seedu\address\logic\commands\DeleteCommand.java
``` java
    /**
     * Creates a delete command which aims to delete one person.
     * @param targetIndex of the specified person
     */
    public DeleteCommand(Index targetIndex) {
        this.targetIndexes = new ArrayList<>();
        targetIndexes.add(targetIndex);
        this.targetTags = null;
    }

    /**
     * Creates a delete command which aims to delete multiple persons.
     * @param targetIndexes is the list of all the indexes of the specified persons.
     */
    public DeleteCommand(ArrayList<Index> targetIndexes) {
        this.targetIndexes = targetIndexes;
        this.targetTags = null;
    }

    @Override
    /**
     * Executes the delete commands for persons or tags.
     * @return the CommandResult of the delete command
     * @throws CommandException if the invalid persons or tags are provided
     */
    public CommandResult executeUndoableCommand() throws CommandException {
        if (targetTags == null && targetIndexes != null) {
            return executeCommandForPersons();

        } else {
            return executeCommandForTag();
        }
    }

    /**
     * Executes Delete Command for persons.
     * @throws CommandException when the person index provided is invalid
     */
    private CommandResult executeCommandForPersons() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        ArrayList<ReadOnlyPerson> deletePersonList = new ArrayList<>();

        for (Index index : targetIndexes) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            ReadOnlyPerson personToDelete = lastShownList.get(index.getZeroBased());
            deletePersonList.add(personToDelete);
        }

        try {
            model.deletePersons(deletePersonList);
        } catch (PersonNotFoundException pnfe) {
            assert false : "One of the target persons is missing";
        }

        return new CommandResult(generateSuccessfulResultMsgForPerson(deletePersonList));
    }

```
###### \java\seedu\address\logic\commands\DeleteCommand.java
``` java
    /**
     * Generates the successful command result of the deletePersonList.
     * @param deletePersonList of the deleted persons
     * @return the string of the command result message
     */
    public static String generateSuccessfulResultMsgForPerson(ArrayList<ReadOnlyPerson> deletePersonList) {
        int numOfPersons = deletePersonList.size();

        StringBuilder formatBuilder = new StringBuilder();
        if (numOfPersons == 1) {
            formatBuilder.append("Deleted Person :\n");
        } else {
            formatBuilder.append("Deleted Persons :\n");
        }

        // List the names of the persons deleted
        formatBuilder.append("[ ");
        for (int i = 0; i < deletePersonList.size(); i++) {
            formatBuilder.append((i + 1) + ". " + deletePersonList.get(i).getName() + " ");
        }
        formatBuilder.append("]\n");

        // List the details of the persons deleted
        formatBuilder.append("Details: \n");
        for (ReadOnlyPerson p : deletePersonList) {
            formatBuilder.append("[");
            formatBuilder.append(p.getAsText());
            formatBuilder.append("]");
            formatBuilder.append("\n");
        }

        String resultMsg = formatBuilder.toString();
        return resultMsg;
    }

```
###### \java\seedu\address\logic\commands\DeleteCommand.java
``` java
    @Override
    public boolean equals(Object other) {
        if (targetIndexes != null) {
            return other == this // short circuit if same object
                    || (other instanceof DeleteCommand // instanceof handles nulls
                    && this.targetIndexes.equals(((DeleteCommand) other).targetIndexes)); // state check
        } else {
            return other == this // short circuit if same object
                    || (other instanceof DeleteCommand // instanceof handles nulls
                    && this.targetTags.equals(((DeleteCommand) other).targetTags)); // state check
        }
    }
}
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
    /**
     * Creates a new person object with the details of the person to be edited.
     * Be edited with {@param editPersonDescriptor}.
     * @param personToEdit the person to be edited
     * @return the created person object
     */
    private static Person createEditedPerson(ReadOnlyPerson personToEdit,
                                             EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Gender updatedGender = editPersonDescriptor.getGender().orElse(personToEdit.getGender());
        MatricNo updatedMatricNo = editPersonDescriptor.getMatricNo().orElse(personToEdit.getMatricNo());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Birthday updateBirthday = editPersonDescriptor.getBirthday().orElse(personToEdit.getBirthday());
        Timetable updatedTimetable = editPersonDescriptor.getTimetable().orElse(personToEdit.getTimetable());
        Remark updatedRemark = personToEdit.getRemark();
        PhotoPath updatedPhotoPath = personToEdit.getPhotoPath();
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());

        return new Person(updatedName, updatedGender, updatedMatricNo,
                updatedPhone, updatedEmail, updatedAddress,
                updatedTimetable, updatedRemark, updatedPhotoPath, updatedTags, updateBirthday);
    }

```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        /**
         * Sets the details of the gender to edit the person with.
         * @param gender
         */
        public void setGender(Gender gender) {
            this.gender = gender;
        }

        /**
         * Gets the details of the gender to edit the person with.
         * @return gender if the gender is specified
         */
        public Optional<Gender> getGender() {
            return Optional.ofNullable(gender);
        }

        /**
         * Sets the details of the matriculation number to edit the person with.
         * @param matricNo
         */
        public void setMatricNo(MatricNo matricNo) {
            this.matricNo = matricNo;
        }

        /**
         * Gets the details of the matriculation number to edit the person with.
         * @return matriculation number if it is specified
         */
        public Optional<MatricNo> getMatricNo() {
            return Optional.ofNullable(matricNo);
        }
```
###### \java\seedu\address\logic\commands\PhotoCommand.java
``` java
/**
 * Edits the photo path of the specified person.
 */
public class PhotoCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "photo";
    public static final String COMMAND_ALIAS = "ph";

    public static final String MESSAGE_USAGE = "| " + COMMAND_WORD + " |"
            + ": Adds a photo to the person identified by the index number used in the last person listing"
            + "by specifying the path of the photo.\n"
            + "If the path field is empty, the old photo path is removed for the person.\n"
            + "Parameters: INDEX " + PREFIX_PHOTO + "[PHOTO PATH] \n"
            + FORMAT_ALIGNMENT_TO_PARAMETERS + "(INDEX must be a positive integer)\n"
            + "Example: (add photo)  " + COMMAND_WORD + " 1 " + PREFIX_PHOTO
            + "C:\\Users\\User\\Desktop\\photo.jpg\n"
            + FORMAT_ALIGNMENT_TO_EXAMPLE + "(delete photo) " + COMMAND_WORD + " 2 " + PREFIX_PHOTO + "\n";
    public static final String MESSAGE_ADD_PHOTO_SUCCESS =
            "Successfully saved photo and added the photo path to Person: %1$s";
    public static final String MESSAGE_DELETE_PHOTO_SUCCESS = "Removed photo path from Person: %1$s";
    public static final String MESSAGE_NO_PHOTO_TO_DELETE = "No photo path to remove from Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSONS = "This person already exists in the address book.";
    public static final String MESSAGE_LOCAL_PHOTOPATH_CONSTRAINTS =
            "Photo Path should be the absolute path of a valid file in your PC. It should be a string started "
                    + "with the name of your disk, "
                    + "followed by several groups of backslash and string, like \"c:\\desktop\\happy.jpg\","
                    + "and the file should exist.";

    public static final String REGEX_LOCAL_PHOTOPATH_VALIDATION = "([a-zA-Z]:)?(\\\\[a-zA-Z0-9_.-]+)+\\\\?";

    public static final String PATH_FILE_SAVED_PARENT_DIRECTORY = "src/main/resources/images/contactPhotos/";
    public static final String PATH_DEFAULT_PHOTO = "src/main/resources/images/defaultPhoto.jpg";

    private Index targetIndex;
    private String localPhotoPath;
    private PhotoPath photoPath;

    /**
     * Initializes the PhotoCommand and decides its behaviour.
     * @param targetIndex of the person in the list to edit the photo path
     * @param localPhotoPath path of the photo store in the computer
     */
    public PhotoCommand(Index targetIndex, String localPhotoPath) throws IllegalValueException, FileNotFoundException {
        requireNonNull(targetIndex);
        requireNonNull(localPhotoPath);

        String trimmedPhotoPath = localPhotoPath.trim();

        if (isEmptyPhotoPath(trimmedPhotoPath)) {
            setDeletePhotoCommandParameters(targetIndex);
        } else if (isValidLocalPhotoPath(trimmedPhotoPath)) {
            setEditPhotoCommandParametersAndFile(targetIndex, trimmedPhotoPath);
        } else {
            throw new IllegalValueException(MESSAGE_LOCAL_PHOTOPATH_CONSTRAINTS);
        }
    }
    private void setEditPhotoCommandParametersAndFile(Index targetIndex, String photoPath)
            throws FileNotFoundException, IllegalValueException {
        // copy the photo
        String savePath = copyLocalPhotoToApp(photoPath);

        // set up parameters
        this.targetIndex = targetIndex;
        localPhotoPath = photoPath;
        this.photoPath = new PhotoPath(savePath);
    }

    /**
     * Copies the photo specified in the local photo path to the app.
     * The copied photo is saved in the default folder in the app.
     * @param localPhotoPath of the photo
     * @return the photo path string of the copied photo
     * @throws FileNotFoundException if the photo is not found in the user's computer
     */
    private String copyLocalPhotoToApp(String localPhotoPath) throws FileNotFoundException {
        File targetFile = new File(localPhotoPath);
        String extension = getFileExtension(localPhotoPath);
        String savePath = PATH_FILE_SAVED_PARENT_DIRECTORY + getSavedFileName(extension);

        if (!isFileExists(targetFile)) {
            throw new FileNotFoundException(MESSAGE_LOCAL_PHOTOPATH_CONSTRAINTS);
        }

        createAppPhotoFile(savePath);

        try {
            copyFile(localPhotoPath, savePath);
        } catch (IOException e) {
            assert false : "Cannot copy the file!";
        }

        return savePath;
    }


    private void setDeletePhotoCommandParameters(Index targetIndex) throws IllegalValueException {
        this.targetIndex = targetIndex;
        localPhotoPath = "";
        photoPath = new PhotoPath("");
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToPhoto = lastShownList.get(targetIndex.getZeroBased());
        Person photoedPerson = createPhotoedPerson(personToPhoto, photoPath);

        try {
            model.addPhotoPath(photoPath);
        } catch (DuplicatePhotoPathException e) {
            assert false : "Duplicated photo path!";
        }

        try {
            model.updatePerson(personToPhoto, photoedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSONS);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        EventsCenter.getInstance().post(new PersonSelectedEvent(photoedPerson, targetIndex.getZeroBased()));
        return new CommandResult(generateSuccessMsg(photoedPerson));
    }

    /**
     * Returns true if the given string is an empty photo path.
     */
    public static boolean isEmptyPhotoPath(String photoPathString) {
        return photoPathString.equals("");
    }

    /**
     * Returns true if the given string is a valid local photo path.
     */
    public static boolean isValidLocalPhotoPath(String photoPathString) {
        return photoPathString.matches(REGEX_LOCAL_PHOTOPATH_VALIDATION);
    }

    /**
     * Generates the unique name of the copied photo.
     * The name is generated by combining the time {@code num} this method executes and
     * its original {@code fileExtension}.
     * @return the name of the copied photo.
     */
    private String getSavedFileName(String fileExtension) {
        Date date = new Date();
        Long num = date.getTime();
        return num.toString() + fileExtension;
    }


    /**
     * Creates and returns a {@code Person} with the details of {@code personToPhoto}.
     */
    private static Person createPhotoedPerson(ReadOnlyPerson personToPhoto,
                                              PhotoPath photoPath) {
        assert personToPhoto != null;

        Person photoPerson = new Person(personToPhoto.getName(), personToPhoto.getGender(),
                personToPhoto.getMatricNo(), personToPhoto.getPhone(),
                personToPhoto.getEmail(), personToPhoto.getAddress(), personToPhoto.getTimetable(),
                personToPhoto.getRemark(), photoPath, personToPhoto.getTags(), personToPhoto.getBirthday());

        return photoPerson;
    }

    /**
     * Creates an empty app photo file if it doesn't exist.
     * @param path of the app photo
     * @throws IOException if the file or directory cannot be created.
     */
    public void createAppPhotoFile(String path) {
        File photoFile = new File(path);
        try {
            createIfMissing(photoFile);
        } catch (IOException e) {
            assert false : "The file or directory cannot be created.";
        }
    }

    /**
     * Generates the successful message accordingly.
     * @param personToPhoto
     * @return the successful message for adding photo if the photo path string is not empty.
     */
    private String generateSuccessMsg(ReadOnlyPerson personToPhoto) {
        if (photoPath.toString().equals("")) {
            if (personToPhoto.getPhotoPath().value.equals("")) {
                return String.format(MESSAGE_NO_PHOTO_TO_DELETE, personToPhoto);
            } else {
                return String.format(MESSAGE_DELETE_PHOTO_SUCCESS, personToPhoto);
            }
        } else {
            return String.format(MESSAGE_ADD_PHOTO_SUCCESS, personToPhoto);
        }
    }

    /**
     * Gets the local photo path of the file.
     * @return the string of local photo path
     */
    public String getLocalPhotoPath() {
        return this.localPhotoPath;
    }

    /**
     * Gets the photo path of the file stored in app.
     * @return the string of app photo path
     */
    public String getAppPhotoPath() {
        return this.photoPath.value;
    }

    /**
     * Gets the index of the person.
     * @return the target index of the person
     */
    public Index getIndex() {
        return this.targetIndex;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PhotoCommand)) {
            return false;
        }

        // state check
        PhotoCommand ph = (PhotoCommand) other;
        return targetIndex.equals(ph.targetIndex)
                && haveSameContent(ph.photoPath.value, ((PhotoCommand) other).photoPath.value);
    }
}

```
###### \java\seedu\address\logic\commands\RemarkCommand.java
``` java
/**
 * Edits the remark of the specified person.
 */
public class RemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remark";
    public static final String COMMAND_ALIAS = "rm";

    public static final String MESSAGE_USAGE = "| " + COMMAND_WORD + " |"
            + ": Adds one or more remarks the person identified by the index number used in the last person listing.\n"
            + FORMAT_ALIGNMENT_TO_REMARK + "If the remark field is empty, the remark is removed for the person.\n"
            + "Parameters: INDEX " + PREFIX_REMARK + "[REMARK1] " + PREFIX_REMARK + "[REMARK2] ...\n"
            + FORMAT_ALIGNMENT_TO_PARAMETERS + "(INDEX must be a positive integer)\n"
            + "Example: (add a remark) " + COMMAND_WORD + " 1 " + PREFIX_REMARK + "Likes to drink coffee\n"
            + FORMAT_ALIGNMENT_TO_EXAMPLE
            + "(add multiple remarks) " + COMMAND_WORD + " 1 " + PREFIX_REMARK + "Likes to drink coffee "
            + PREFIX_REMARK + "CAP5.0\n"
            + FORMAT_ALIGNMENT_TO_EXAMPLE + "(delete remarks) " + COMMAND_WORD + " 2 " + PREFIX_REMARK + "\n";

    public static final String MESSAGE_ADD_REMARK_SUCCESS = "Added Remark(s) to Person: %1$s";
    public static final String MESSAGE_DELETE_REMARK_SUCCESS = "Removed Remark(s) from Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index targetIndex;
    private final Remark remark;

    /**
     * Initializes the remark command.
     * @param targetIndex of the person in the list to edit the remark
     * @param remark of the person
     */
    public RemarkCommand(Index targetIndex, Remark remark) {
        requireNonNull(targetIndex);
        requireNonNull(remark);

        this.targetIndex = targetIndex;
        this.remark = remark;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToRemark = lastShownList.get(targetIndex.getZeroBased());

        Person remarkedPerson = createRemarkedPerson(personToRemark, remark);

        try {
            model.updatePerson(personToRemark, remarkedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateSuccessMsg(remarkedPerson));
    }

    /**
     * Generates the successful message for adding remarks and deleting remarks.
     * @param personToRemark the person to be remarked
     * @return the successful message for adding remark if the remark string is not empty.
     */
    private String generateSuccessMsg(ReadOnlyPerson personToRemark) {
        if (remark.toString().isEmpty()) {
            return String.format(MESSAGE_DELETE_REMARK_SUCCESS, personToRemark);
        } else {
            return String.format(MESSAGE_ADD_REMARK_SUCCESS, personToRemark);
        }
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToRemark}
     * remarked with {@code remarkPersonDescriptor}.
     * @return the person object with the new remark
     */
    public static Person createRemarkedPerson(ReadOnlyPerson personToRemark, Remark remark) {
        assert personToRemark != null;

        Person remarkPerson = new Person(personToRemark.getName(), personToRemark.getGender(),
                personToRemark.getMatricNo(), personToRemark.getPhone(),
                personToRemark.getEmail(), personToRemark.getAddress(), personToRemark.getTimetable(),
                remark, personToRemark.getPhotoPath(), personToRemark.getTags(), personToRemark.getBirthday());

        return remarkPerson;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemarkCommand)) {
            return false;
        }

        // state check
        RemarkCommand r = (RemarkCommand) other;
        return targetIndex.equals(r.targetIndex)
                && remark.equals(r.remark);
    }
}
```
###### \java\seedu\address\logic\parser\DeleteCommandParser.java
``` java
    public static final String VALID_REGEX_DELETE_ONE_PERSON = "-?\\d+";

    public static final String DELETE_MULTIPLE_PERSON_COMMA_VALIDATION_REGEX =
            "(-?\\d\\s*?,\\s*?-?\\d?)+";

    public static final String DELETE_MULTIPLE_PERSON_WHITESPACE_VALIDATION_REGEX =
            "(-?\\d\\s*?-?\\d?)+";
```
###### \java\seedu\address\logic\parser\DeleteCommandParser.java
``` java
    /**
     * Parses the indexes contained within {@code argMultimap} to return
     * a DeleteCommand object that executes a delete for Persons.
     * @throws ParseException if the values mapped as the persons do not conform as valid person indexes
     * @see #parse(String)
     */
    private DeleteCommand parseForPersonIndexes (String args, String preamble) throws ParseException {
        if (preamble.matches(VALID_REGEX_DELETE_ONE_PERSON)) {
            // code block for delete for a person
            try {
                Index index = ParserUtil.parseIndex(args);
                return new DeleteCommand(index);
            } catch (IllegalValueException ive) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }
        } else if (preamble.matches(DELETE_MULTIPLE_PERSON_COMMA_VALIDATION_REGEX)) {
            //code block for delete multiple persons, input string separated by comma
            try {
                ArrayList<Index> deletePersons = ParserUtil.parseIndexes(args, ",");
                return new DeleteCommand(deletePersons);
            } catch (IllegalValueException ive) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }
        } else if (preamble.matches(DELETE_MULTIPLE_PERSON_WHITESPACE_VALIDATION_REGEX)) {
            //code block for delete multiple persons, input indexes separated by whitespace
            try {
                ArrayList<Index> deletePersons = ParserUtil.parseIndexes(args, " ");
                return new DeleteCommand(deletePersons);
            } catch (IllegalValueException ive) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }
        }
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses {@code oneBasedIndexes} separated with commas into a {@Code ArrayList<Index>} and returns it.
     * Leading and trailing whitespaces are trimmed.
     * @param splitString is the sign used to separate indexes, can be either comma or whitespace(s)
     * @throws IllegalValueException if one of the specified indexes is invalid (not non-zero unsigned integer).
     *
     */
    public static ArrayList<Index> parseIndexes(String oneBasedIndexes, String splitString)
            throws IllegalValueException {
        String trimmedIndexes = oneBasedIndexes.trim();
        String[] indexes;
        if (splitString.equals(REGEX_COMMA)) {
            indexes = trimmedIndexes.split(splitString);
        } else {
            indexes = trimmedIndexes.split(REGEX_MULTIPLE_WHITESPACE);
        }

        ArrayList<Index> deletePersons = new ArrayList<>();

        for (String index : indexes) {
            index = index.trim();
            if (!StringUtil.isNonZeroUnsignedInteger(index)) {
                throw new IllegalValueException(MESSAGE_INVALID_INDEX);
            }

            Index thisIndex = Index.fromOneBased(Integer.valueOf(index));
            if (!deletePersons.contains(thisIndex)) {
                deletePersons.add(thisIndex);
            }
        }

        return deletePersons;
    }
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> gender} into an {@code Optional<Gender>} if {@code gender} is present.
     * @See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Gender> parseGender(Optional<String> gender) throws IllegalValueException {
        requireNonNull(gender);
        return gender.isPresent() ? Optional.of(new Gender(gender.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> matricNo} into an {@code Optional<MatricNo>} if {@code matricNo} is present.
     * @See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<MatricNo> parseMatricNo(Optional<String> matricNo) throws IllegalValueException {
        requireNonNull(matricNo);
        return matricNo.isPresent() ? Optional.of(new MatricNo(matricNo.get())) : Optional.empty();
    }
```
###### \java\seedu\address\logic\parser\PhotoCommandParser.java
``` java
/**
 * Parses input arguments and creates a new PhotoCommand object
 */
public class PhotoCommandParser implements Parser<PhotoCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the PhotoCommand
     * and returns an PhotoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PhotoCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PHOTO);

        Index index;
        String localPhotoPath;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            localPhotoPath = argMultimap.getValue(PREFIX_PHOTO).orElse("");


        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PhotoCommand.MESSAGE_USAGE));
        }

        try {
            return new PhotoCommand(index, localPhotoPath);
        } catch (FileNotFoundException fnfe) {
            throw new ParseException(
                    String.format(MESSAGE_FILE_NOT_FOUND, PhotoCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_FILE_PATH, PhotoCommand.MESSAGE_USAGE));
        }

    }
}
```
###### \java\seedu\address\logic\parser\RemarkCommandParser.java
``` java
/**
 * Parses input arguments and creates a new RemarkCommand object
 */
public class RemarkCommandParser implements Parser<RemarkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemarkCommand
     * and returns an RemarkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemarkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_REMARK);

        Index index;
        Remark remark;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());

        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
        }

        remark = getAllRemarks(argMultimap);

        return new RemarkCommand(index, remark);
    }

    /**
     * Concatenates all the remarks into one string.
     * @param argumentMultimap
     * @return the remark contains all the remark string.
     */
    private Remark getAllRemarks(ArgumentMultimap argumentMultimap) {

        List<String> allRemarks = argumentMultimap.getAllValues(PREFIX_REMARK);
        String allRemarkString = "";

        if (allRemarks.size() > 1 || (allRemarks.size() == 1 && (!allRemarks.get(0).equals("")))) {
            allRemarkString = allRemarks.toString();
        }

        return new Remark(allRemarkString);
    }

}
```
###### \java\seedu\address\model\AddressBook.java
``` java
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
```
###### \java\seedu\address\model\Model.java
``` java
    /** Deletes the given person. */
    void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException;

    /** Deletes the given list of persons. */
    void deletePersons(ArrayList<ReadOnlyPerson> targets) throws PersonNotFoundException;

    /** Adds the given photo path */
    void addPhotoPath(PhotoPath photoPath) throws DuplicatePhotoPathException;

```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);

        logger.fine("Updating all photopaths...");
        this.addressBook.updatePhotoPathSavedInMasterList();

        logger.fine("Deleting all unused photos...");
        try {
            this.addressBook.removeAllUnusedPhotosAndPaths();
        } catch (PhotoPathNotFoundException e) {
            assert false : "Some of the photopaths cannot be found!";
        }

        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        currentTheme = userPrefs.getCurrentTheme();
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public synchronized void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
        addressBook.removePerson(target);
        indicateAddressBookChanged();
        checkMasterTagListHasAllTagsUsed();
    }

    @Override
    public synchronized void deletePersons(ArrayList<ReadOnlyPerson> targets) throws PersonNotFoundException {
        addressBook.removePersons(targets);
        indicateAddressBookChanged();
        checkMasterTagListHasAllTagsUsed();
    }

    @Override
    public void addPhotoPath(PhotoPath photoPath) throws DuplicatePhotoPathException {
        addressBook.addPhotoPath(photoPath);
        indicateAddressBookChanged();
    }
```
###### \java\seedu\address\model\person\Gender.java
``` java
/**
 * Represents a Person's gender in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidInput(String)}
 */
public class Gender {

    public static final String MESSAGE_GENDER_CONSTRAINTS =
            "Person gender should be a case-insensitive string of either 'male', 'female', or 'm', 'f'";

    public static final String VALID_MALE_FIRST_WORD = "male";
    public static final String VALID_MALE_SECOND_WORD = "m";
    public static final String VALID_FEMALE_FIRST_WORD = "female";
    public static final String VALID_FEMALE_SECOND_WORD = "f";
    public static final String VALID_GENDER_UNSPECIFIED = "";

    public final String value;

    /**
     * Validates given gender and sets person's gender accordingly.
     * Accepts input (case-insensitive): male, female, m, f
     * @throws IllegalValueException if the given gender string is invalid.
     */
    public Gender(String gender) throws IllegalValueException {
        requireNonNull(gender);
        String trimmedGender = gender.trim();

        if (!isValidInput(trimmedGender)) {
            throw new IllegalValueException(MESSAGE_GENDER_CONSTRAINTS);
        }

        String ignoredCaseGender = trimmedGender.toLowerCase();
        value = setGenderByInput(ignoredCaseGender);
    }

    private String setGenderByInput(String ignoredCaseGender) {
        String genderValue = "";
        if (ignoredCaseGender.equals(VALID_MALE_FIRST_WORD)
                || ignoredCaseGender.equals(VALID_MALE_SECOND_WORD)) {
            genderValue = "Male";
        } else if (ignoredCaseGender.equals(VALID_FEMALE_FIRST_WORD)
                || ignoredCaseGender.equals(VALID_FEMALE_SECOND_WORD)) {
            genderValue = "Female";
        } else {
            genderValue = "";
        }
        return genderValue;
    }

    /**
     * Checks whether the input gender is valid.
     * @return true if a given input string is a valid person gender, false otherwise.
     */
    public static boolean isValidInput(String inputGender) {
        String ignoredCaseInput = inputGender.toLowerCase();
        return ignoredCaseInput.equals(VALID_MALE_FIRST_WORD)
                || ignoredCaseInput.equals(VALID_MALE_SECOND_WORD)
                || ignoredCaseInput.equals(VALID_FEMALE_FIRST_WORD)
                || ignoredCaseInput.equals(VALID_FEMALE_SECOND_WORD)
                || ignoredCaseInput.equals(VALID_GENDER_UNSPECIFIED);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Gender // instanceof handles nulls
                && this.value.equals(((Gender) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\person\MatricNo.java
``` java
/**
 * Represents a Person's Matriculation Number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidMatricNo(String)}
 */
public class MatricNo {

    public static final String MESSAGE_MATRIC_NO_CONSTRAINTS =
            "Person's matriculation number should be a 9-character string starting with 'A' or 'a', "
                    + "followed by 7 digits, and ending with a letter.";
    public static final String MATRIC_NO_VALIDATION_REGEX = "([Aa])(\\d{7})([a-zA-Z])";

    public final String value;

    /**
     * Initializes matricNo objeccts and validates given matricNo.
     * @throws IllegalValueException if the given matricNo string is invalid.
     */
    public MatricNo(String matricNo) throws IllegalValueException {
        requireNonNull(matricNo);
        String trimmedMatricNo = matricNo.trim();
        if (!isValidMatricNo(trimmedMatricNo)) {
            throw new IllegalValueException(MESSAGE_MATRIC_NO_CONSTRAINTS);
        }
        this.value = trimmedMatricNo;
    }

    /**
     * Returns if a given string is a valid person matricNo.
     */
    public static boolean isValidMatricNo(String test) {
        return test.matches(MATRIC_NO_VALIDATION_REGEX) || test.equals("");
    }

    @Override
    public String toString() {
        return value;
    }

    /**
     * Returns true if two matric numbers are the same.
     * 'A0162533K' equals 'a0162533k'
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MatricNo // instanceof handles nulls
                && this.value.toUpperCase().equals(((MatricNo) other).value.toUpperCase())); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\person\Remark.java
``` java
/**
 * Represents the remark of a person in the address book.
 */
public class Remark {

    public final String value;

    public Remark(String remark) {
        requireNonNull(remark);
        this.value = remark;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && this.value.equals(((Remark) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### \java\seedu\address\model\photo\PhotoPath.java
``` java
/**
 * Represents the path of a person's photo in the address book.
 */
public class PhotoPath {

    public static final String FILE_SAVED_PARENT_PATH = "src/main/resources/images/contactPhotos/";
    public static final String MESSAGE_APP_PHOTOPATH_CONSTRAINTS =
            "The app photo path should be a string starting with '"
                    + FILE_SAVED_PARENT_PATH
                    + "', following by the file name, like'photo.jpg'.";


    public final String value;

    /**
     * Initializes the photo path object and validates the given photo path string.
     * @param photoPath string of the specified photo path
     * @throws IllegalValueException if the given string is invalid
     */
    public PhotoPath(String photoPath) throws IllegalValueException {
        requireNonNull(photoPath);

        if (!isValidPhotoPath(photoPath)) {
            throw new IllegalValueException(MESSAGE_APP_PHOTOPATH_CONSTRAINTS);
        }
        this.value = photoPath;
    }

    /**
     * Returns if a given string is a valid photo path.
     */
    public static boolean isValidPhotoPath(String photoPath) {
        if (photoPath.equals("")) {
            //empty photo path
            return true;
        }
        Boolean hasFileExtension = hasFileExtension(photoPath);
        Boolean isInDefaultFolder = isInFolder(photoPath, FILE_SAVED_PARENT_PATH);

        return  isInDefaultFolder && hasFileExtension;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PhotoPath // instanceof handles nulls
                && this.value.equals(((PhotoPath) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### \java\seedu\address\storage\XmlAdaptedPhotoPath.java
``` java
/**
 * It's the JAXB-friendly adapted version of the photo path.
 */
public class XmlAdaptedPhotoPath {

    @XmlValue
    private String photoPathName;

    /**
     * Constructs an XmlAdaptedTag.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPhotoPath() {}

    /**
     * Converts a given PhotoPath into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedPhotoPath(PhotoPath source) {
        photoPathName = source.value;
    }

    /**
     * Converts this jaxb-friendly adapted PhotoPath object into the model's PhotoPath object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public PhotoPath toModelType() throws IllegalValueException {
        return new PhotoPath(photoPathName);
    }

}
```
###### \java\seedu\address\storage\XmlSerializableAddressBook.java
``` java
/**
 * An Immutable AddressBook that is serializable to XML format
 */
@XmlRootElement(name = "addressbook")
public class XmlSerializableAddressBook implements ReadOnlyAddressBook {

    @XmlElement
    private List<XmlAdaptedPerson> persons;
    @XmlElement
    private List<XmlAdaptedTag> tags;
    @XmlElement
    private List<XmlAdaptedPhotoPath> photoPaths;
    /**
     * Creates an empty XmlSerializableAddressBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableAddressBook() {
        persons = new ArrayList<>();
        tags = new ArrayList<>();
        photoPaths = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableAddressBook(ReadOnlyAddressBook src) {
        this();
        persons.addAll(src.getPersonList().stream().map(XmlAdaptedPerson::new).collect(Collectors.toList()));
        tags.addAll(src.getTagList().stream().map(XmlAdaptedTag::new).collect(Collectors.toList()));
        photoPaths.addAll(src.getPhotoPathList().stream().map(XmlAdaptedPhotoPath::new).collect(Collectors.toList()));
    }

    @Override
    public ObservableList<PhotoPath> getPhotoPathList() {
        final ObservableList<PhotoPath> photoPaths = this.photoPaths.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(photoPaths);
    }

```
###### \java\seedu\address\ui\MainWindow.java
``` java
/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Region> {

    private static final String ICON = "/images/icon.png";
    private static final String FXML = "MainWindow.fxml";
    private static final int MIN_HEIGHT = 600;
    private static final int MIN_WIDTH = 450;
    private static final String VIEW_PATH = "/view/";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private TagColorMap tagColorMap;
    private PersonInfoPanel personInfoPanel;
    private InfoPanel infoPanel;
    private PersonListPanel personListPanel;
    private TagListPanel tagListPanel;
    private Config config;
    private UserPrefs prefs;

    @FXML
    private StackPane infoPlaceholder;

    @FXML
    private StackPane personInfoPlaceholder;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane tagListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    /**
     * Initializes the main window by the parameters provided.
     */
    public MainWindow(Stage primaryStage, Config config, UserPrefs prefs, Logic logic) {
        super(FXML);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        this.config = config;
        this.prefs = prefs;

        // Configure the UI
        setTitle(config.getAppTitle());
        setIcon(ICON);
        setWindowMinSize();
        setWindowDefaultSize(prefs);
        setWindowDefaultTheme(prefs);
        Scene scene = new Scene(getRoot());
        primaryStage.setScene(scene);

        setAccelerators();
        registerAsAnEventHandler(this);
    }

    /**
     * Gets the primary stage of the main window.
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }
```
###### \java\seedu\address\ui\PersonInfoPanel.java
``` java

/**
 * A UI component that displays a person's data on the main panel
 */
public class PersonInfoPanel extends UiPart<Region> {

    private static final String FXML = "PersonInfoPanel.fxml";
    private static String DEFAULT_PHOTO_PATH = "/images/defaultPhoto.jpg";

    private ReadOnlyPerson person;
    private ReadOnlyPerson currentlyViewedPerson;

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private Circle photoCircle;
    @FXML
    private Label name;
    @FXML
    private Label gender;
    @FXML
    private Label matricNo;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label birthday;
    @FXML
    private Label remark;
    @FXML
    private FlowPane tags;

    /**
     * Initializes the person information panel.
     */
    public PersonInfoPanel() {
        super(FXML);
        this.person = null;
        loadDefaultPerson();

        registerAsAnEventHandler(this);
    }

    /**
     * Loads the default person when the app is first started
     */
    private void loadDefaultPerson() {
        name.setText("Person X");
        gender.setText("");
        matricNo.setText("");
        phone.setText("");
        address.setText("");
        email.setText("");
        birthday.setText("");
        remark.setText("");
        tags.getChildren().clear();

        setDefaultContactPhoto();
        currentlyViewedPerson = null;
        logger.info("Currently Viewing: Default Person");
    }

    /**
     * Updates info with person selected
     */
    private void loadPerson(ReadOnlyPerson person) {
        this.person = person;
        tags.getChildren().clear();
        initTags(person);

        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        gender.textProperty().bind(Bindings.convert(person.genderProperty()));
        matricNo.textProperty().bind(Bindings.convert(person.matricNoProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        address.textProperty().bind(Bindings.convert(person.addressProperty()));
        email.textProperty().bind(Bindings.convert(person.emailProperty()));
        birthday.textProperty().bind(Bindings.convert(person.birthdayProperty()));
        remark.textProperty().bind(Bindings.convert(person.remarkProperty()));
        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            initTags(person);
        });

        loadPhoto(person);

        currentlyViewedPerson = person;
        logger.info("Currently Viewing: " + currentlyViewedPerson.getName());
    }

```
###### \java\seedu\address\ui\PersonInfoPanel.java
``` java
    /**
     * Sets the default contact photo.
     */
    public void setDefaultContactPhoto() {
        Image defaultImage = new Image(MainApp.class.getResourceAsStream(DEFAULT_PHOTO_PATH));
        photoCircle.setFill(new ImagePattern(defaultImage));
    }

    /**
     * Loads the photo of the specified person.
     * @param person
     */
    public void loadPhoto(ReadOnlyPerson person) {
        String prefix = "src/main/resources";
        //String photoPath = person.getPhotoPath().value.substring(prefix.length());
        String photoPathString = person.getPhotoPath().value;
        Image image;

        if (photoPathString.equals("")) {  //default male and female photos
            image = getDefaultPhotoByGender();
        } else {
            File contactImg = new File(photoPathString);
            if (contactImg.exists() && !contactImg.isDirectory()) {
                String url = contactImg.toURI().toString();
                image = new Image(url);
            } else {
                image = getDefaultPhotoByGender();
            }
        }

        photoCircle.setFill(new ImagePattern(image));
    }

    /**
     * Gets the default photo by gender. If the gender is not specifed, then return the default photo without gender.
     * @return Image of the according default photo
     */
    private Image getDefaultPhotoByGender() {
        String photoPathString = "";

        if (person.getGender().toString().equals("Male")) {
            photoPathString = "/images/default_male.jpg";
        } else if (person.getGender().toString().equals("Female")) {
            photoPathString = "/images/default_female.jpg";
        } else {
            photoPathString = "/images/defaultPhoto.jpg";
        }
        return new Image(MainApp.class.getResourceAsStream(photoPathString));
    }

```
###### \java\seedu\address\ui\TagColorMap.java
``` java
    /**
     * Updates the color index to pick a new color for the new tag.
     */
    private static void updateColorIndex() {
        if (colorIndex == NUM_COLORS - 1) {
            colorIndex = 0;
        } else {
            colorIndex++;
        }
    }
}
```
###### \resources\view\MainWindow.fxml
``` fxml

<?import java.net.URL?>
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Menu?>
<?import javafx.scene.control.MenuBar?>
<?import javafx.scene.control.MenuItem?>
<?import javafx.scene.control.SplitPane?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.StackPane?>
<?import javafx.scene.layout.VBox?>
<VBox xmlns="http://javafx.com/javafx/8.0.141" xmlns:fx="http://javafx.com/fxml/1">
    <stylesheets>
        <URL value="@Extensions.css" />
    </stylesheets>
    <MenuBar fx:id="menuBar" maxHeight="22.0" prefHeight="22.0" prefWidth="2000.0" VBox.vgrow="NEVER">
        <Menu mnemonicParsing="false" text="File">
            <MenuItem mnemonicParsing="false" onAction="#handleExit" text="Exit" />
        </Menu>
        <Menu mnemonicParsing="false" text="Help">
            <MenuItem fx:id="helpMenuItem" mnemonicParsing="false" onAction="#handleHelp" text="Help" />
        </Menu>
    </MenuBar>
    <HBox>
        <VBox fx:id="personList">
            <StackPane fx:id="personListPanelPlaceholder" minWidth="255.0" VBox.vgrow="ALWAYS" />
        </VBox>
        <AnchorPane>

            <SplitPane dividerPositions="0.26582278481012656" orientation="VERTICAL" prefWidth="2000.0" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0">

                <SplitPane id="splitPane" fx:id="splitPane" dividerPositions="0.4269269269269269" maxHeight="628.0" minHeight="230.0" prefHeight="600.0" prefWidth="1990.0">
                    <VBox maxWidth="1000.0" minHeight="81.0" minWidth="430.0" prefHeight="81.0" prefWidth="843.0" SplitPane.resizableWithParent="true">
                        <padding>
                            <Insets bottom="10" left="10" right="10" top="10" />
                        </padding>

```
###### \resources\view\MainWindow.fxml
``` fxml
                    <VBox minWidth="200" prefWidth="323.0">

                        <StackPane fx:id="commandBoxPlaceholder" minWidth="100" prefWidth="305.0" styleClass="pane-with-border">
                            <padding>
                                <Insets bottom="5" left="10" right="10" top="5" />
                            </padding>
                        </StackPane>
                        <StackPane fx:id="resultDisplayPlaceholder" minWidth="100" prefWidth="320.0" styleClass="pane-with-border" VBox.vgrow="ALWAYS">
                            <padding>
                                <Insets bottom="5" left="10" right="10" top="5" />
                            </padding>
                        </StackPane>
                    </VBox>
                </SplitPane>
```
###### \resources\view\PersonInfoPanel.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.image.Image?>
<?import javafx.scene.image.ImageView?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.layout.FlowPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.StackPane?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.shape.Circle?>
<?import javafx.scene.text.Font?>
<AnchorPane styleClass="person-info-panel" xmlns="http://javafx.com/javafx/8.0.141" xmlns:fx="http://javafx.com/fxml/1">

    <HBox prefHeight="250.0" prefWidth="448.0" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0">
        <VBox prefHeight="250.0" prefWidth="198.0">
            <AnchorPane prefHeight="216.0" prefWidth="210.0" VBox.vgrow="ALWAYS">
                <ImageView fitHeight="210.0" fitWidth="221.0" pickOnBounds="true" preserveRatio="true">
                    <image>
                        <Image url="@../images/personInfoBg.jpg" />
                    </image>
                </ImageView>
                <Circle fx:id="photoCircle" fill="WHITE" layoutX="111.0" layoutY="100.0" radius="86.0" stroke="floralwhite" strokeType="INSIDE" strokeWidth="10" />
                <VBox.margin>
                    <Insets />
                </VBox.margin>
            </AnchorPane>
        </VBox>
        <VBox alignment="TOP_LEFT" prefHeight="250.0" prefWidth="574.0" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="20.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0" HBox.hgrow="ALWAYS">
            <StackPane alignment="TOP_LEFT" prefHeight="20.0" prefWidth="304.0" VBox.vgrow="NEVER">
                <Label fx:id="name" minHeight="-Infinity" minWidth="-Infinity" prefHeight="35.0" prefWidth="391.0" styleClass="display_big_label" text="\$name">
                    <font>
                        <Font size="24.0" />
                    </font>
                </Label>
                <VBox.margin>
                    <Insets left="15.0" top="20.0" />
                </VBox.margin>
            </StackPane>
            <FlowPane fx:id="tags" columnHalignment="CENTER">
                <VBox.margin>
                    <Insets bottom="5.0" left="15.0" right="10.0" />
                </VBox.margin>
            </FlowPane>
            <HBox prefHeight="100.0" prefWidth="200.0" VBox.vgrow="ALWAYS">
                <VBox prefHeight="160.0" prefWidth="9.0" HBox.hgrow="NEVER">
                    <Label minHeight="-Infinity" minWidth="-Infinity" prefHeight="20.0" prefWidth="80.0" styleClass="display_small_label" text="Gender" />
                    <Label minHeight="-Infinity" minWidth="-Infinity" prefHeight="20.0" prefWidth="80.0" styleClass="display_small_label" text="Matric No" />
                    <Label minHeight="-Infinity" minWidth="-Infinity" prefHeight="20.0" prefWidth="80.0" styleClass="display_small_label" text="Phone" />
                    <Label minHeight="-Infinity" minWidth="-Infinity" prefHeight="20.0" prefWidth="80.0" styleClass="display_small_label" text="Email" />
                    <Label minHeight="-Infinity" minWidth="-Infinity" prefHeight="20.0" prefWidth="80.0" styleClass="display_small_label" text="Birthday" />
                    <Label minHeight="-Infinity" minWidth="-Infinity" prefHeight="54.0" prefWidth="80.0" styleClass="display_small_label" text="Address" />
                    <Label minHeight="-Infinity" minWidth="-Infinity" prefHeight="20.0" prefWidth="80.0" styleClass="display_small_label" text="Remark" />
                </VBox>
                <VBox prefHeight="200.0" prefWidth="100.0" HBox.hgrow="ALWAYS">
                    <Label fx:id="gender" minHeight="-Infinity" minWidth="-Infinity" prefHeight="20.0" prefWidth="326.0" styleClass="display_small_value" text="\$gender" VBox.vgrow="ALWAYS" />
                    <Label fx:id="matricNo" minHeight="-Infinity" minWidth="-Infinity" prefHeight="20.0" prefWidth="326.0" styleClass="display_small_value" text="\$matricNo" />
                    <Label fx:id="phone" minHeight="-Infinity" minWidth="-Infinity" prefHeight="20.0" prefWidth="326.0" styleClass="display_small_value" text="\$phone" />
                    <Label fx:id="email" minHeight="-Infinity" minWidth="-Infinity" prefHeight="20.0" prefWidth="326.0" styleClass="display_small_value" text="\$email" />
                    <Label fx:id="birthday" minHeight="-Infinity" minWidth="-Infinity" prefHeight="20.0" prefWidth="326.0" styleClass="display_small_value" text="\$birthday" />
                    <Label fx:id="address" minHeight="-Infinity" minWidth="-Infinity" prefHeight="54.0" prefWidth="326.0" styleClass="display_small_value" text="\$address" wrapText="true">
                    <VBox.margin>
                        <Insets />
                    </VBox.margin></Label>
                    <Label fx:id="remark" styleClass="display_small_value" text="\$remark" wrapText="true">
                    <VBox.margin>
                        <Insets />
                    </VBox.margin></Label>
                </VBox>
                <VBox.margin>
                    <Insets bottom="10.0" left="15.0" />
                </VBox.margin>
            </HBox>
            <HBox.margin>
                <Insets />
            </HBox.margin>
        </VBox>
    </HBox>
</AnchorPane>
```
