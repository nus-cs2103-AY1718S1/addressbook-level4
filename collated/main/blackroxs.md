# blackroxs
###### /java/seedu/room/logic/commands/BackupCommand.java
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
###### /java/seedu/room/logic/commands/ImportCommand.java
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

    private String filePath;

    public ImportCommand(String filePath) {
        this.filePath = filePath;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            Optional<ReadOnlyResidentBook> newContacts = MainApp.getBackup().readResidentBook(filePath);
            ReadOnlyResidentBook newList = newContacts.orElse(null);

            ArrayList<String> namesAdded = new ArrayList<>();
            String namesFeedback = "";
            if (newList != null) {
                ObservableList<ReadOnlyPerson> personList = newList.getPersonList();

                for (ReadOnlyPerson p : personList) {
                    try {
                        model.addPerson(p);
                    } catch (DuplicatePersonException e) {
                        continue;
                    }
                    namesAdded.add(p.getName().fullName);
                }

                namesFeedback = getNamesFeedback(namesAdded, namesFeedback);

                return new CommandResult(String.format(MESSAGE_SUCCESS + " Added: " + namesFeedback));
            }
            return new CommandResult(String.format(MESSAGE_ERROR));
        } catch (DataConversionException e) {
            throw new CommandException(MESSAGE_ERROR);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_ERROR);
        }
    }

    private String getNamesFeedback(ArrayList<String> namesAdded, String namesFeedback) {
        for (int i = 0; i < namesAdded.size(); i++) {
            namesFeedback += namesAdded.get(i);

            if (i + 1 != namesAdded.size()) {
                namesFeedback += ", ";
            }
        }
        return namesFeedback;
    }
}
```
###### /java/seedu/room/logic/commands/RemoveTagCommand.java
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
     * Creates and returns a {@code Person} with the details of {@code personToEdit} without @tagName
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
###### /java/seedu/room/logic/parser/ImportCommandParser.java
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
###### /java/seedu/room/logic/parser/RemoveTagParser.java
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
###### /java/seedu/room/MainApp.java
``` java
    public static Storage getBackup() {
        return backup;
    }
```
###### /java/seedu/room/storage/StorageManager.java
``` java
    @Override
    public void backupResidentBook(ReadOnlyResidentBook residentBook) throws IOException {
        saveResidentBook(residentBook, residentBookStorage.getResidentBookFilePath() + "-backup.xml");
    }

    public Optional<ReadOnlyResidentBook> readBackupResidentBook() throws DataConversionException, IOException {
        return readResidentBook(residentBookStorage.getResidentBookFilePath() + "-backup.xml");
    }


    // ================ EventBook methods ==============================

    @Override
    public String getEventBookFilePath() {
        return eventBookStorage.getEventBookFilePath();
    }

    @Override
    public Optional<ReadOnlyEventBook> readEventBook() throws DataConversionException, IOException {
        return readEventBook(eventBookStorage.getEventBookFilePath());
    }

    @Override
    public Optional<ReadOnlyEventBook> readEventBook(String filePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return eventBookStorage.readEventBook(filePath);
    }

    @Override
    public void saveEventBook(ReadOnlyEventBook residentBook) throws IOException {
        saveEventBook(residentBook, eventBookStorage.getEventBookFilePath());
    }

    @Override
    public void saveEventBook(ReadOnlyEventBook residentBook, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        eventBookStorage.saveEventBook(residentBook, filePath);
    }

    @Override
    @Subscribe
    public void handleEventBookChangedEvent(EventBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveEventBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }
    @Override
    public void backupEventBook(ReadOnlyEventBook residentBook) throws IOException {
        saveEventBook(residentBook, eventBookStorage.getEventBookFilePath() + "-backup.xml");
    }

    public Optional<ReadOnlyEventBook> readBackupEventBook() throws DataConversionException, IOException {
        return readEventBook(eventBookStorage.getEventBookFilePath() + "-backup.xml");
    }
}
```
###### /java/seedu/room/storage/XmlResidentBookStorage.java
``` java
    @Override
    public void backupResidentBook(ReadOnlyResidentBook residentBook) throws IOException {
        saveResidentBook(residentBook, filePath + "-backup.xml");
    }

}
```
###### /java/seedu/room/ui/MainWindow.java
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
