# blackroxs
###### \java\seedu\room\logic\commands\BackupCommand.java
``` java
/**
 * Create backup copy of resident book.
 */
public class BackupCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "backup";
    public static final String COMMAND_ALIAS = "b";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates backup copy of resident book.";

    public static final String MESSAGE_SUCCESS = "New backup created";

    public static final String MESSAGE_ERROR = "Error creating backup";

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            MainApp.getBackup().backupResidentBook(model.getResidentBook());
            return new CommandResult(String.format(MESSAGE_SUCCESS));
        } catch (IOException e) {
            return new CommandResult(String.format(MESSAGE_ERROR) + e.getMessage());
        }

    }
}
```
###### \java\seedu\room\logic\commands\exceptions\NoUniqueImport.java
``` java
/**
 * Represents an error which occurs during execution of a Sort Command Execution.
 */
public class NoUniqueImport extends Exception {
    public NoUniqueImport(String message) {
        super(message);
    }
}
```
###### \java\seedu\room\logic\commands\ImportCommand.java
``` java

/**
 * Import contacts from xml file.
 */
public class ImportCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "import";
    public static final String COMMAND_ALIAS = "i";
    public static final String MESSAGE_SUCCESS = "Import successful.";
    public static final String MESSAGE_ERROR = "Import error. Please check your file path or XML file.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds all persons in the XML file onto "
            + "the current resident book.\n"
            + "Parameters: FILE_PATH \n"
            + "Example: " + COMMAND_WORD + " friendsContacts.xml";

    public static final String NAME_SEPERATOR = ", ";
    public static final String MESSAGE_FILE_NOT_UNIQUE = "No unique residents found.";
    private String filePath;

    public ImportCommand(String filePath) {
        this.filePath = filePath;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            ReadOnlyResidentBook newList = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

            ArrayList<String> namesAdded = new ArrayList<>();

            addUniquePerson(newList, namesAdded);
            String namesFeedback = getNamesFeedback(namesAdded);

            return new CommandResult(String.format(MESSAGE_SUCCESS + " Added: " + namesFeedback));
        } catch (NullPointerException e) {
            throw new CommandException(MESSAGE_ERROR);
        } catch (DataConversionException e) {
            throw new CommandException(MESSAGE_ERROR);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_ERROR);
        } catch (NoUniqueImport noUniqueImport) {
            throw new CommandException(MESSAGE_FILE_NOT_UNIQUE);
        }
    }

    /**
     * Add only persons not found in current ResidentBook
     *
     * @param newList    import file viewed as ReadOnlyResidentBook
     * @param namesAdded list of names that are added
     */
    private void addUniquePerson(ReadOnlyResidentBook newList, ArrayList<String> namesAdded) {
        ObservableList<ReadOnlyPerson> personList = newList.getPersonList();

        for (ReadOnlyPerson p : personList) {
            try {
                model.addPerson(p);
            } catch (DuplicatePersonException e) {
                continue;
            }
            namesAdded.add(p.getName().fullName);
        }
    }

    /**
     * Form the string to return as feedback to user
     * @param namesAdded list of names that are added
     * @return concatenated string of all names
     * @throws NoUniqueImport no resident has been added to list (invalid import)
     */
    private String getNamesFeedback(ArrayList<String> namesAdded) throws NoUniqueImport {
        String namesFeedback = "";
        if (namesAdded.size() == 0) {
            throw new NoUniqueImport(MESSAGE_FILE_NOT_UNIQUE);
        }

        for (int i = 0; i < namesAdded.size(); i++) {
            namesFeedback += namesAdded.get(i);

            if (i + 1 != namesAdded.size()) {
                namesFeedback += NAME_SEPERATOR;
            }
        }
        return namesFeedback;
    }
}
```
###### \java\seedu\room\logic\commands\RemoveTagCommand.java
``` java
/**
 * Removes a tag that is shared by a group of contacts.
 */
public class RemoveTagCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "removeTag";
    public static final String COMMAND_ALIAS = "rm";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes a tag that is shared by a group of contacts\n"
            + "Parameters: TAG_NAME \n"
            + "Example: " + COMMAND_WORD + " colleagues";

    public static final String MESSAGE_REMOVE_TAG_SUCCESS = "Removed Tag.";
    public static final String MESSAGE_REMOVE_TAG_NOT_EXIST = "Tag does not exist in this address book.";
    public static final String MESSAGE_REMOVE_TAG_ERROR = "Error removing tag.";

    private String tagName;

    public RemoveTagCommand(String tagName) {
        this.tagName = tagName;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        try {
            for (int i = 0; i < lastShownList.size(); i++) {
                if (lastShownList.get(i).getTags().contains(new Tag(tagName))) {

                    updateTagList(lastShownList, i);
                }
            }

            model.removeTag(new Tag(tagName));
        } catch (IllegalValueException e) {
            throw new CommandException(MESSAGE_REMOVE_TAG_ERROR);
        } catch (TagNotFoundException e) {
            throw new CommandException(MESSAGE_REMOVE_TAG_NOT_EXIST);
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_REMOVE_TAG_SUCCESS));
    }

    /**
     * Update the tag list of person and refresh on model.
     * @param lastShownList the last updated list
     * @param i index of the person to edit
     * @throws CommandException raise error when there is problem in removing tag from model
     */
    private void updateTagList(List<ReadOnlyPerson> lastShownList, int i) throws CommandException {
        ReadOnlyPerson personToEdit = lastShownList.get(i);
        Person editedPerson = removedTagFromPerson(personToEdit);

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (PersonNotFoundException e) {
            throw new CommandException(MESSAGE_REMOVE_TAG_ERROR);
        } catch (DuplicatePersonException e) {
            throw new CommandException(MESSAGE_REMOVE_TAG_ERROR);
        }
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * @param personToEdit
     * @return
     */
    private Person removedTagFromPerson(ReadOnlyPerson personToEdit) {
        assert personToEdit != null;

        Set<Tag> updatedTags = new HashSet<>();

        for (Tag t : personToEdit.getTags()) {
            if (t.tagName.equals(this.tagName)) {
                continue;
            } else {
                updatedTags.add(t);
                System.out.println(t.tagName);
            }
        }

        return new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getRoom(), personToEdit.getTimestamp(), updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemoveTagCommand)) {
            return false;
        }

        // state check
        RemoveTagCommand e = (RemoveTagCommand) other;
        return tagName.equals(e.tagName);
    }
}
```
###### \java\seedu\room\logic\parser\ImportCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ImportCommand object
 */
public class ImportCommandParser implements Parser<ImportCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ImportCommand
     * and returns an ImportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public ImportCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
        }
        return new ImportCommand(trimmedArgs);
    }
}
```
###### \java\seedu\room\logic\parser\RemoveTagParser.java
``` java
/**
 * Parses input arguments and creates a new RemoveTagCommand object
 */
public class RemoveTagParser implements Parser<RemoveTagCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the RemoveTagCommand
     * and returns an RemoveTagCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveTagCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
        }
        return new RemoveTagCommand(trimmedArgs);
    }
}
```
###### \java\seedu\room\MainApp.java
``` java
    public static Storage getBackup() {
        return backup;
    }
```
###### \java\seedu\room\storage\StorageManager.java
``` java
    @Override
    public void backupResidentBook(ReadOnlyResidentBook residentBook) throws IOException {
        saveResidentBook(residentBook, getDirAbsolutePath() + "/backup.xml");
        backupImages();
    }

    public Optional<ReadOnlyResidentBook> readBackupResidentBook() throws DataConversionException, IOException {
        return readResidentBook(getDirAbsolutePath() + "/backup.xml");
    }

    /**
     * Get the absolute parent path of residentbook.xml
     *
     * @return absolute path of the residentbook.xml directory
     */
    public String getDirAbsolutePath() {
        File file = new File(residentBookStorage.getResidentBookFilePath());
        String absPath = file.getParent();

        return absPath;
    }

    /**
     * Stores the contact images into a backup folder
     *
     * @throws IOException if unable to read or write in the folder
     */
    public void backupImages() throws IOException {
        String backupFolder = getDirAbsolutePath() + File.separator + Picture.FOLDER_NAME + "_backup";
        String originalFolder = getDirAbsolutePath() + File.separator + Picture.FOLDER_NAME;

        handleImageBackupFolder(backupFolder, originalFolder);
        handleImagesBackupFiles(backupFolder, originalFolder);
    }

    /**
     * Copies each file from source to destination backup folder
     *
     * @param backupFolder   cannot be null
     * @param originalFolder cannot be null
     * @throws IOException if there is any problem writing to the file
     */
    private void handleImagesBackupFiles(String backupFolder, String originalFolder) throws IOException {
        File source = new File(originalFolder);

        if (source.exists()) {
            File[] listOfImages = source.listFiles();

            for (int i = 0; i < listOfImages.length; i++) {
                File dest = new File(backupFolder + File.separator + listOfImages[i].getName());
                copy(listOfImages[i], dest);
            }
        }

    }

    /**
     * Creates the image backup folder if it does not exist
     *
     * @param backupFolder cannot be empty or null
     * @throws IOException if folder cannot be created due to read or write access
     */
    private void handleImageBackupFolder(String backupFolder, String originalFolder) throws IOException {
        File source = new File(originalFolder);
        if (source.exists()) {
            boolean backupExist = new File(backupFolder).exists();

            if (!backupExist) {
                boolean isSuccess = (new File(backupFolder)).mkdirs();
                if (!isSuccess) {
                    throw new IOException();
                }
            }
        }
    }

    /**
     * Copies the file from source to destination
     *
     * @param source cannot be null
     * @param dest   cannot be null
     * @throws IOException if there is any problem writing to the file
     */
    public static void copy(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(buf)) > 0) {
                os.write(buf, 0, bytesRead);
            }
        } finally {
            is.close();
            os.close();
        }
    }

```
###### \java\seedu\room\storage\XmlResidentBookStorage.java
``` java
    @Override
    public void backupResidentBook(ReadOnlyResidentBook residentBook) throws IOException {
        saveResidentBook(residentBook, filePath + "-backup.xml");
    }

}
```
###### \java\seedu\room\ui\MainWindow.java
``` java
    /**
     * Handles import and allows user to choose file
     */
    @FXML
    public void handleImport() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        String filePath = file.getAbsolutePath();
        System.out.println(filePath);

        if (file != null) {
            try {
                CommandResult commandResult = logic.execute(ImportCommand.COMMAND_WORD + " " + filePath);
                logger.info("Result: " + commandResult.feedbackToUser);
                raise(new NewResultAvailableEvent(commandResult.feedbackToUser));
            } catch (CommandException e) {
                logger.info("Invalid command: " + ImportCommand.MESSAGE_ERROR);
                raise(new NewResultAvailableEvent(e.getMessage()));
            } catch (ParseException e) {
                logger.info("Invalid command: " + ImportCommand.MESSAGE_ERROR);
                raise(new NewResultAvailableEvent(e.getMessage()));
            }
        }
    }

```
