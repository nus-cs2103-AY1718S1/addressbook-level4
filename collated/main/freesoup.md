# freesoup
###### \java\seedu\address\logic\commands\ExportCommand.java
``` java
/**
 * Exports address book app contacts into an  contacts.vcf file.
 */
public class ExportCommand extends Command {
    public static final String COMMAND_WORD = "export";
    public static final String COMMAND_USAGE = COMMAND_WORD;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports contacts into a .vcf file.";
    public static final String MESSAGE_WRONG_FILE_TYPE = "Export only exports .vcf and .xml file.";
    public static final String MESSAGE_FILE_NOT_FOUND = "File was not found in specified directory.";

    public static final String MESSAGE_SUCCESS = "Successfully exported contacts.";
    public final String filePath;

    public ExportCommand(String path) {
        this.filePath = path;
    }

    @Override
    public CommandResult execute() throws CommandException {
        File export = new File(filePath);
        ReadOnlyAddressBook addressBook = model.getAddressBook();
        if (export.getName().endsWith(".xml")) {
            XmlSerializableAddressBook xmlAddressBook = new XmlSerializableAddressBook(addressBook);
            try {
                export.createNewFile();
                XmlFileStorage.saveDataToFile(export, xmlAddressBook);
            } catch (IOException ioe) {
                throw new CommandException(MESSAGE_FILE_NOT_FOUND);
            }
        } else if (export.getName().endsWith(".vcf")) {
            try {
                VcfExport.saveDataToFile(export, addressBook.getPersonList());
            } catch (IOException ioe) {
                throw new CommandException(MESSAGE_FILE_NOT_FOUND);
            }
        }
        return new CommandResult(MESSAGE_SUCCESS);
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

    private final List<ReadOnlyPerson> toImport;

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
            + "Parameters: TAG (must be a string)\n"
            + "Paramaters: INDEX (must be a positive integer) TAG (must be a string)";

    public static final String MESSAGE_SUCCESS = "Tag removed";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";
    public static final String MESSAGE_NOT_DELETED = "Tag not deleted";
    public static final String MESSAGE_EXCEEDTAGNUM = "Please only type one TAG to be removed";

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
        try {
            if (index.orElse(null) == null) {
                model.removeTag(toRemove);
                return new CommandResult(String.format(MESSAGE_SUCCESS));
            } else {
                model.removeTag(index.get(), toRemove);
                return new CommandResult(String.format(MESSAGE_SUCCESS));
            }
        } catch (DuplicatePersonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException e) {
            throw new CommandException(MESSAGE_NOT_DELETED);
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
    public static final String COMMAND_USAGE = COMMAND_WORD;

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

    @Override
    public ImportCommand parse(String userInput) throws ParseException {
        File file;
        if (userInput.trim().isEmpty()) {
            FileChooser chooser = new FileChooser();
            chooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All", "*.*"),
                    new FileChooser.ExtensionFilter("vCard (.vcf)", "*.vcf"),
                    new FileChooser.ExtensionFilter("XML (.xml)", "*.xml")
            );
            chooser.setTitle("Select import file.");
            file = chooser.showOpenDialog(new Stage());
            if (file == null) {
                throw new ParseException("Import cancelled");
            }
        } else {
            file = new File(userInput.trim());
        }
        if (file.getName().endsWith(".xml")) {
            try {
                ReadOnlyAddressBook importingBook = XmlFileStorage.loadDataFromSaveFile(file);
                List<ReadOnlyPerson> importList = importingBook.getPersonList();

                return new ImportCommand(importList);
            } catch (DataConversionException dce) {
                throw new ParseException(ImportCommand.MESSAGE_FILE_CORRUPT);
            } catch (FileNotFoundException fnfe) {
                throw new ParseException(ImportCommand.MESSAGE_FILE_NOT_FOUND);
            }

        } else if (file.getName().endsWith(".vcf")) {
            try {
                List<ReadOnlyPerson> importList = VcfImport.getPersonList(file);
                return new ImportCommand(importList);
            } catch (IllegalValueException ive) {
                throw new ParseException(ImportCommand.MESSAGE_FILE_CORRUPT);
            } catch (IOException ioe) {
                throw new ParseException(ImportCommand.MESSAGE_FILE_NOT_FOUND);
            }

        } else {
            throw new ParseException(ImportCommand.MESSAGE_WRONG_FORMAT);
        }
    }

}
```
###### \java\seedu\address\logic\parser\RemoveTagCommandParser.java
``` java
/**
 * Parses input arguments and creates a new RemoveTagCommand object
 */
public class RemoveTagCommandParser implements Parser<RemoveTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemoveTagCommand
     * and returns an RemoveTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveTagCommand parse(String args) throws ParseException {
        Index index = null;
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
        }

        if (trimmedArgs.split(" ").length >= 3) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_EXCEEDTAGNUM));
        }

        try {
            if (trimmedArgs.contains(" ")) {
                index = ParserUtil.parseIndex(trimmedArgs.split(" ")[0]);
                trimmedArgs = trimmedArgs.split(" ")[1];
            }
            Tag toRemoved = new Tag(trimmedArgs);
            if (index == null) {
                return new RemoveTagCommand(toRemoved);
            } else {
                return new RemoveTagCommand(index, toRemoved);
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
    public void removeTag(Tag tag) throws PersonNotFoundException, DuplicatePersonException {
        ObservableList<ReadOnlyPerson> list = addressBook.getPersonList();

        for (int i = 0; i < list.size(); i++) {
            ReadOnlyPerson person = list.get(i);
            Person newPerson = new Person(person);
            Set<Tag> tagList = newPerson.getTags();
            tagList = new HashSet<Tag>(tagList);
            tagList.remove(tag);

            newPerson.setTags(tagList);
            addressBook.updatePerson(person, newPerson);
        }
        indicateAddressBookChanged();
    }

    @Override
    public void removeTag(Index index, Tag tag) throws PersonNotFoundException, DuplicatePersonException {
        List<ReadOnlyPerson> list = getFilteredPersonList();
        ReadOnlyPerson person = list.get(index.getZeroBased());
        Person newPerson = new Person(person);
        Set<Tag> tagList = newPerson.getTags();
        tagList = new HashSet<>(tagList);
        tagList.remove(tag);

        newPerson.setTags(tagList);
        addressBook.updatePerson(person, newPerson);
        indicateAddressBookChanged();
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void sortFilteredPersonList(Comparator<ReadOnlyPerson> comparator) {
        sortedPersons.setComparator(comparator);
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

        for (int i = 0; i < list.size(); i++) {
            ReadOnlyPerson person = list.get(i);
            Set<Tag> tagList = person.getTags();
            bw.write("BEGIN:VCARD\n");
            bw.write("VERSON:2.1\n");
            bw.write("FN:" + person.getName() + "\n");
            bw.write("EMAIL:" + person.getEmail() + "\n");
            bw.write("TEL:" + person.getPhone() + "\n");
            bw.write("ADR:" + person.getAddress() + "\n");
            if (!person.getRemark().isEmpty()) {
                bw.write("RM:" + person.getRemark() + "\n");
            }
            for (Tag tag : tagList) {
                bw.write("TAG:" + tag.getTagName() + "\n");
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
                    case "RM":
                        remark = new Remark(parameter);
                        break;
                    case "TAG":
                        Tag tag = new Tag(parameter);
                        tagList.add(tag);
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
}
```
