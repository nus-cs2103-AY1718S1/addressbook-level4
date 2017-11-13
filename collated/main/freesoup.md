# freesoup
###### \java\seedu\address\commons\events\ui\ImportFileChooseEvent.java
``` java
/**
 * Indicates a request to open a FileChooser Window to import a file.
 */
public class ImportFileChooseEvent extends BaseEvent {

    private final FileWrapper file;

    public ImportFileChooseEvent (FileWrapper file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public FileWrapper getFile() {
        return this.file;
    }
}
```
###### \java\seedu\address\logic\commands\ExportCommand.java
``` java
/**
 * Exports address book app contacts into an  contacts.vcf file.
 */
public class ExportCommand extends Command {
    public static final String COMMAND_WORD = "export";
    public static final String COMMAND_USAGE = COMMAND_WORD;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports contacts into a vCard or XML file. "
            + "Parameters: FileName.xml Or FileName.vcf\n"
            + "Example: export sample.xml OR export sample.vcf";
    public static final String MESSAGE_WRONG_FILE_TYPE = "Export only exports .vcf and .xml file.";
    public static final String MESSAGE_EMPTY_BOOK = "No contacts found in Rubrika to export.";

    public static final String MESSAGE_SUCCESS = "Successfully exported contacts.";
    public static final String XML_EXTENSION = ".xml";
    public static final String VCF_EXTENSION = ".vcf";

    public final String filePath;

    private final Logger logger = LogsCenter.getLogger(ExportCommand.class);

    public ExportCommand(String path) {
        this.filePath = path;
    }

    @Override
    public CommandResult execute() throws CommandException {
        File export = new File(filePath);
        ReadOnlyAddressBook addressBook = model.getAddressBook();

        if (addressBook.getPersonList().isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY_BOOK);
        }
        try {
            if (export.getName().endsWith(XML_EXTENSION)) {
                createXmlFile(export, addressBook);
            } else if (export.getName().endsWith(VCF_EXTENSION)) {
                createVcfFile(export, addressBook);
            }
        } catch (IOException ioe) {
            assert false : "The file should have been created and writable";
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Creates a .vcf File that contains contacts in addressBook.
     * @param export the file to be created and exported to.
     * @param addressBook containing list of contacts to be exported
     * @throws IOException if export was not found.
     */
    private void createVcfFile(File export, ReadOnlyAddressBook addressBook) throws IOException {
        logger.info("Attempting to write to data file: " + export.getPath());
        VcfExport.saveDataToFile(export, addressBook.getPersonList());
    }

    /**
     * Creates a .xml File that contains contacts in addressBook.
     * @param export the file to be created and exported to.
     * @param addressBook containing list of contacts to be exported.
     * @throws IOException if export was not found.
     */
    private void createXmlFile(File export, ReadOnlyAddressBook addressBook) throws IOException {
        logger.info("Attempting to write to data file: " + export.getPath());
        XmlSerializableAddressBook xmlAddressBook = new XmlSerializableAddressBook(addressBook);
        export.createNewFile();
        XmlFileStorage.saveDataToFile(export, xmlAddressBook);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExportCommand // instanceof handles nulls
                && this.filePath.equals(((ExportCommand) other).filePath)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\ImportCommand.java
``` java
/**
 * Imports contact from a .vcf file.
 * Adds the contacts into the address book.
 */
public class ImportCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "import";
    public static final String COMMAND_USAGE = COMMAND_WORD;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports contacts from a .vcf file.";

    public static final String MESSAGE_SUCCESS = "Successfully imported contacts. %1$s duplicates were found";
    public static final String MESSAGE_WRONG_FORMAT = "File chosen is not of .vcf or .xml type";
    public static final String MESSAGE_FILE_CORRUPT = "File is corrupted. Please check.";
    public static final String MESSAGE_FILE_NOT_FOUND = "File was not found in specified directory.";
    public static final String MESSAGE_IMPORT_CANCELLED = "Import cancelled";
    public static final String MESSAGE_FILE_INVALID = "File might not have been exported from Rubrika"
            + " and contains missing fields.";
    public static final String XML_EXTENSION = ".xml";
    public static final String VCF_EXTENSION = ".vcf";

    public final List<ReadOnlyPerson> toImport;

    public ImportCommand (List<ReadOnlyPerson> importList) {
        toImport = importList;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        int duplicate = 0;
        for (ReadOnlyPerson toAdd : toImport) {
            try {
                model.addPerson(toAdd);
            } catch (DuplicatePersonException dpe) {
                duplicate++;
            }
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, duplicate));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportCommand // instanceof handles nulls
                && this.toImport.equals(((ImportCommand) other).toImport)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\RemoveTagCommand.java
``` java
/**
 * Removes a tag from the address book.
 */
public class RemoveTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "removetag";
    public static final String COMMAND_USAGE = COMMAND_WORD + " ";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes specified tag\n"
            + "Parameters: RANGE (all OR INDEX(Index must be positive)) followed by TAG (must be a string)\n"
            + "Example: removetag 30 prospective OR removetag all colleagues";

    public static final String MESSAGE_SUCCESS = "Tag removed";
    public static final String MESSAGE_EXCEEDTAGNUM = "Please type one TAG to be removed";
    public static final String MESSAGE_TAG_NOT_FOUND = "Tag given does not exist in address book";
    public static final String MESSAGE_TAG_NOT_FOUND_IN = "Index %s does not have the given tag.";

    private final Tag toRemove;
    private final Optional<Index> index;

    /**
     * Creates an RemoveTagCommand to remove the specified {@code Tag} from a (@code Index) if given.
     */
    public RemoveTagCommand (Tag tag) {
        this.toRemove = tag;
        this.index = Optional.ofNullable(null);
    }

    public RemoveTagCommand (Index index, Tag tag) {
        this.toRemove = tag;
        this.index = Optional.of(index);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        if (index.orElse(null) == null) {
            removeAllTag(toRemove);
        } else {
            removeOneTag(index.get(), toRemove);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    /**
     * Ensures that the index given can be found within the given list.
     * @param index of the person in the filtered person list to remove tag from.
     * @throws CommandException if the index given is out of the bounds of the filtered list.
     */
    private void requireIndexValid(Index index) throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
    }

    /**
     * Removes a tag from a specified person.
     * @param index of the person in the filtered person list to remove tag from.
     * @param toRemove is the tag to be removed from the person
     * @throws CommandException if the tag to be removed does not exist in the person.
     */
    private void removeOneTag(Index index, Tag toRemove) throws CommandException {
        requireIndexValid(index);
        try {
            model.removeTag(index, toRemove);
        } catch (NoSuchTagException nste) {
            throw new CommandException(String.format(MESSAGE_TAG_NOT_FOUND_IN, index.getOneBased()));
        }
    }

    /**
     * Removes a tag from the whole addressbook.
     * @param toRemove is the tag to be removed from the addressbook.
     * @throws CommandException if tag does not exist in the addressbook.
     */
    private void removeAllTag(Tag toRemove) throws CommandException {
        try {
            model.removeTag(toRemove);
        } catch (NoSuchTagException nste) {
            throw new CommandException(MESSAGE_TAG_NOT_FOUND);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveTagCommand // instanceof handles nulls
                && this.toRemove.equals(((RemoveTagCommand) other).toRemove) // state check
                && this.index.equals(((RemoveTagCommand) other).index)); // state check
    }

}
```
###### \java\seedu\address\logic\commands\SortCommand.java
``` java
/**
 * Sorts all persons in the address book to the user.
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_USAGE = COMMAND_WORD + " ";

    public static final String MESSAGE_SUCCESS = "List has been sorted.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the list by FIELD\n"
            + "Parameters: FIELD (name/phone/email) ORDER (asc/dsc)";

    private final Comparator<ReadOnlyPerson> comparator;

    public SortCommand(Comparator<ReadOnlyPerson> comparator) {
        this.comparator = comparator;
    }

    @Override
    public CommandResult execute() {
        model.sortFilteredPersonList(comparator);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && this.comparator.equals(((SortCommand) other).comparator)); // state check
    }
}
```
###### \java\seedu\address\logic\parser\ExportCommandParser.java
``` java
/**
 * Parses path of file given by user and ensures that it is of .vcf or .xml file type
 */
public class ExportCommandParser implements Parser<ExportCommand> {

    @Override
    public ExportCommand parse(String userInput) throws ParseException {
        String path = userInput.trim();
        if (path.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        }
        if (!path.endsWith(".xml") && !path.endsWith(".vcf")) {
            throw new ParseException(ExportCommand.MESSAGE_WRONG_FILE_TYPE);
        }
        return new ExportCommand(path);
    }
}
```
###### \java\seedu\address\logic\parser\ImportCommandParser.java
``` java
/**
 * Retrieves the location of the import file and passes the FileInputStream into
 * a new ImportCommand Object.
 */
public class ImportCommandParser implements Parser<ImportCommand> {

    private final Logger logger = LogsCenter.getLogger(ImportCommandParser.class);

    @Override
    public ImportCommand parse(String userInput) throws ParseException {
        File file;

        if (userInput.trim().isEmpty()) {
            file = getFileFromImportWindow();
        } else {
            file = new File(userInput.trim());
        }

        if (file.getName().endsWith(ImportCommand.XML_EXTENSION)) {
            return importByXml(file);
        } else if (file.getName().endsWith(ImportCommand.VCF_EXTENSION)) {
            return importByVcf(file);
        } else {
            throw new ParseException(ImportCommand.MESSAGE_WRONG_FORMAT);
        }
    }

    /**
     * Creates an ImportCommand with the list of contacts given in the vcf file.
     * @param file of .vcf file format containing list of contacts.
     * @return ImportCommand that contains the list of contact given in the vcf file
     * @throws ParseException if filepath given is not found or if file is corrupted.
     */
    private ImportCommand importByVcf(File file) throws ParseException {
        try {

            logger.info("Attempting to read data from file: " + file.getPath());
            List<ReadOnlyPerson> importList = VcfImport.getPersonList(file);

            return new ImportCommand(importList);
        } catch (IllegalValueException ive) {
            throw new ParseException(ImportCommand.MESSAGE_FILE_CORRUPT);
        } catch (IOException ioe) {
            throw new ParseException(ImportCommand.MESSAGE_FILE_NOT_FOUND);
        } catch (NullPointerException npe) {
            throw new ParseException(ImportCommand.MESSAGE_FILE_INVALID);
        }
    }

    /**
     * Creates an ImportCommand with the list of contacts given in the xml file.
     * @param file of .xml file format containing list of contacts.
     * @return ImportCommand that contains the list of contact given in the xml file
     * @throws ParseException if filepath given is not found or if file is corrupted.
     */
    private ImportCommand importByXml(File file) throws ParseException {
        try {

            logger.info("Attempting to read data from file: " + file.getPath());
            ReadOnlyAddressBook importingBook = XmlFileStorage.loadDataFromSaveFile(file);
            List<ReadOnlyPerson> importList = importingBook.getPersonList();

            return new ImportCommand(importList);
        } catch (DataConversionException dce) {
            throw new ParseException(ImportCommand.MESSAGE_FILE_CORRUPT);
        } catch (FileNotFoundException fnfe) {
            throw new ParseException(ImportCommand.MESSAGE_FILE_NOT_FOUND);
        }
    }

    /**
     * Raises an event to open a File Explorer and returns the file to be imported
     * @return File containing contacts to be created
     * @throws ParseException if File Explorer was closed before a file has been selected
     */
    private File getFileFromImportWindow() throws ParseException {
        File file;
        FileWrapper fw = new FileWrapper();

        EventsCenter.getInstance().post(new ImportFileChooseEvent(fw));
        file = fw.getFile();
        if (file == null) {
            throw new ParseException(ImportCommand.MESSAGE_IMPORT_CANCELLED);
        }
        return file;
    }
}
```
###### \java\seedu\address\logic\parser\RemoveTagCommandParser.java
``` java
/**
 * Parses input arguments and creates a new RemoveTagCommand object
 */
public class RemoveTagCommandParser implements Parser<RemoveTagCommand> {

    public static final int RANGE = 0;
    public static final int TAG = 1;
    public static final String WHOLEAB = "all";

    /**
     * Parses the given {@code String} of arguments in the context of the RemoveTagCommand
     * and returns an RemoveTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveTagCommand parse(String args) throws ParseException {
        requireNonNull(args);

        String trimmedArgs = args.trim();
        String[] fields = trimmedArgs.split("\\s+");

        Index index;
        Tag toRemove;

        if (trimmedArgs.isEmpty() || fields.length < 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
        }

        if (fields.length > 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_EXCEEDTAGNUM));
        }

        try {
            toRemove =  new Tag(fields[TAG]);
            if (fields[RANGE].equals(WHOLEAB)) {
                return new RemoveTagCommand(toRemove);
            } else if (StringUtil.isNonZeroUnsignedInteger(fields[RANGE])) {
                index = ParserUtil.parseIndex(fields[RANGE]);
                return new RemoveTagCommand(index, toRemove);
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        RemoveTagCommand.MESSAGE_USAGE));
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
        }
    }

}
```
###### \java\seedu\address\logic\parser\SortCommandParser.java
``` java

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    public static final String SORTBYNAMEASCENDING = "name asc";
    public static final String SORTBYEMAILASCENDING = "email asc";
    public static final String SORTBYPHONEASCENDING = "phone asc";
    public static final String SORTBYNAMEDESCENDING = "name dsc";
    public static final String SORTBYEMAILDESCENDING = "email dsc";
    public static final String SORTBYPHONEDESCENDING = "phone dsc";


    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns an SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();

        switch (trimmedArgs) {
        case SORTBYNAMEASCENDING:
            return new SortCommand(ReadOnlyPerson.NAMESORTASC);
        case SORTBYEMAILASCENDING:
            return new SortCommand(ReadOnlyPerson.EMAILSORTASC);
        case SORTBYPHONEASCENDING:
            return new SortCommand(ReadOnlyPerson.PHONESORTASC);
        case SORTBYNAMEDESCENDING:
            return new SortCommand(ReadOnlyPerson.NAMESORTDSC);
        case SORTBYEMAILDESCENDING:
            return new SortCommand(ReadOnlyPerson.EMAILSORTDSC);
        case SORTBYPHONEDESCENDING:
            return new SortCommand(ReadOnlyPerson.PHONESORTDSC);
        default:
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
    }

}
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void removeTag(Tag tag) throws NoSuchTagException {
        if (!addressBook.getTagList().contains(tag)) {
            throw new NoSuchTagException();
        }
        ObservableList<ReadOnlyPerson> list = addressBook.getPersonList();
        for (int i = 0; i < list.size(); i++) {
            ReadOnlyPerson person = list.get(i);
            removeTagFromPerson(tag, person);
        }
        addressBook.removeTagFromUniqueList(tag);
        indicateAddressBookChanged();
    }

    @Override
    public void removeTag(Index index, Tag tag) throws NoSuchTagException {
        List<ReadOnlyPerson> list = getFilteredPersonList();
        ReadOnlyPerson person = list.get(index.getZeroBased());
        if (!person.getTags().contains(tag)) {
            throw new NoSuchTagException();
        }
        removeTagFromPerson(tag, person);
        addressBook.removeTagFromUniqueList(tag);
        indicateAddressBookChanged();
    }

    @Override
    public void removeTagFromPerson(Tag tag, ReadOnlyPerson person) {
        Person newPerson = new Person(person);
        Set<Tag> tagList = newPerson.getTags();
        tagList = new HashSet<>(tagList);
        tagList.remove(tag);

        newPerson.setTags(tagList);

        try {
            addressBook.updatePerson(person, newPerson);
        } catch (PersonNotFoundException pnfe) {
            assert false : "Person will always be found";
        } catch (DuplicatePersonException dpe) {
            assert false : "There will never be duplicates";
        }
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void sortFilteredPersonList(Comparator<ReadOnlyPerson> comparator) {
        sortedPersons.setComparator(comparator);
    }
```
###### \java\seedu\address\model\person\ReadOnlyPerson.java
``` java
    Comparator<ReadOnlyPerson> NAMESORTASC = (ReadOnlyPerson o1, ReadOnlyPerson o2)
        -> o1.getName().compareTo(o2.getName());
    Comparator<ReadOnlyPerson> PHONESORTASC = (ReadOnlyPerson o1, ReadOnlyPerson o2)
        -> o1.getPhone().compareTo(o2.getPhone());
    Comparator<ReadOnlyPerson> EMAILSORTASC = (ReadOnlyPerson o1, ReadOnlyPerson o2)
        -> o1.getEmail().compareTo(o2.getEmail());
    Comparator<ReadOnlyPerson> NAMESORTDSC = (ReadOnlyPerson o1, ReadOnlyPerson o2)
        -> o2.getName().compareTo(o1.getName());
    Comparator<ReadOnlyPerson> PHONESORTDSC = (ReadOnlyPerson o1, ReadOnlyPerson o2)
        -> o2.getPhone().compareTo(o1.getPhone());
    Comparator<ReadOnlyPerson> EMAILSORTDSC = (ReadOnlyPerson o1, ReadOnlyPerson o2)
        -> o2.getEmail().compareTo(o1.getEmail());
```
###### \java\seedu\address\storage\FileWrapper.java
``` java
/**
 * A File Wrapper class to allow modification of File Object after it has been created.
 */
public class FileWrapper {
    private File file;

    public FileWrapper() {
        this.file = null;
    }

    public FileWrapper(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
```
###### \java\seedu\address\storage\VcfExport.java
``` java
/**
 * Parses a list of {@code ReadOnlyPerson} into a .vcf file.
 */
public class VcfExport {
    /**
     * Parses a .vcf file into a list of {@code ReadOnlyPerson}.
     * Throws a IOException if file is not found.
     */
    public static void saveDataToFile(File file, List<ReadOnlyPerson> list) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));

        for (ReadOnlyPerson person : list) {
            Set<Tag> tagList = person.getTags();
            bw.write("BEGIN:VCARD\n");
            bw.write("VERSON:2.1\n");
            bw.write("FN:" + person.getName() + "\n");
            bw.write("EMAIL:" + person.getEmail() + "\n");
            bw.write("TEL:" + person.getPhone() + "\n");
            bw.write("ADR:" + person.getAddress() + "\n");

            if (!person.getRemark().isEmpty()) {
                bw.write("NOTE:" + person.getRemark() + "\n");
            }

            if (!tagList.isEmpty()) {
                bw.write("CATEGORIES:");
                for (Tag tag : tagList) {
                    bw.write(tag.getTagName());
                }
                bw.write("\n");
            }

            bw.write("END:VCARD\n");
        }
        bw.close();
    }
}
```
###### \java\seedu\address\storage\VcfImport.java
``` java
/**
 * Parses a .vcf file into a list of {@code ReadOnlyPerson}.
 */
public class VcfImport {

    public static List<ReadOnlyPerson> getPersonList(File file) throws IOException, IllegalValueException {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(file)));
        String newLine = br.readLine();
        List<ReadOnlyPerson> importList = new ArrayList<>();
        while (newLine != null) {
            if (newLine.equals("BEGIN:VCARD")) {
                Name name = null;
                Email email = null;
                Phone phone = null;
                Address address = null;
                Remark remark = new Remark("");
                Set<Tag> tagList = new HashSet<>();

                newLine = br.readLine();
                while (!newLine.equals("END:VCARD")) {
                    String type = newLine.split(":")[0];
                    String parameter = newLine.split(":")[1];

                    switch (type) {
                    case "FN":
                        name = new Name(parameter);
                        break;

                    case "EMAIL":
                        email = new Email(parameter);
                        break;

                    case "TEL":
                        phone = new Phone(parameter);
                        break;

                    case "ADR":
                        address = new Address(parameter);
                        break;

                    case "NOTE":
                        remark = new Remark(parameter);
                        break;

                    case "CATEGORIES":
                        tagList = parseTag(parameter);
                        break;
                    default:
                    }
                    newLine = br.readLine();
                }
                newLine = br.readLine();
                ReadOnlyPerson toAdd = new Person(name, phone, email, address, remark, tagList);
                importList.add(toAdd);
            } else {
                newLine = br.readLine();
            }
        }
        br.close();
        return importList;
    }

    /**
     * Parses the parameters CATEGORIES field of the vCard into a Set of Tag Objects
     * @param list String of tags to be imported.
     * @throws IllegalValueException if tag does not conform to the requirements.
     */
    private static Set<Tag> parseTag(String list) throws IllegalValueException {
        Set<Tag> tagSet = new HashSet<>();
        String[] tagList = list.split(",");

        for (String tag : tagList) {
            tagSet.add(new Tag(tag));
        }

        return tagSet;
    }
}
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Opens the FileChooser
     */
    @FXML
    private void handleImport(FileWrapper file) {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All", "*.*"),
                new FileChooser.ExtensionFilter("vCard (.vcf)", "*.vcf"),
                new FileChooser.ExtensionFilter("XML (.xml)", "*.xml")
        );
        chooser.setTitle("Select import file.");
        File selectedFile = chooser.showOpenDialog(primaryStage);
        file.setFile(selectedFile);
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Opens a file explorer to select a file to import.
     */
    @Subscribe
    private void handleImportFileChooseEvent(ImportFileChooseEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleImport(event.getFile());
    }
```
