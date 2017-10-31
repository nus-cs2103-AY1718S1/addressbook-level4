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

    public static final String MESSAGE_SUCCESS = "Successfully exported contacts.";

    @Override
    public CommandResult execute() throws CommandException {
        File export = new File(System.getProperty("user.home"), "Desktop/export.vcf");
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(export));
            ObservableList<ReadOnlyPerson> list = model.getFilteredPersonList();

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
        } catch (IOException ioe) {
            throw new CommandException("Problem writing to file");
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException ioe) {
                throw new CommandException("Problem closing file");
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
    public static final String MESSAGE_FILEERROR = "Please ensure that VCard file contents are correct.";
    public static final String MESSAGE_NOFILECHOSEN = "No files were selected";

    private final BufferedReader br;

    public ImportCommand (FileInputStream fis) {
        br = new BufferedReader(new InputStreamReader(fis));
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        int duplicate = 0;

        try {
            String newLine = br.readLine();

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
                        default :
                        }
                        newLine = br.readLine();
                    }
                    newLine = br.readLine();
                    ReadOnlyPerson toAdd = new Person(name, phone, email, address, remark, tagList);
                    try {
                        model.addPerson(toAdd);
                    } catch (DuplicatePersonException dpe) {
                        duplicate++;
                    }
                } else {
                    newLine = br.readLine();
                }
            }
        } catch (IOException ioe) {
            throw new CommandException(MESSAGE_FILEERROR);
        } catch (IllegalValueException ive) {
            throw new CommandException("Data problem");
        } finally {
            try {
                br.close();
            } catch (IOException ioe) {
                throw new CommandException("Problem Closing File");
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
###### \java\seedu\address\logic\parser\ImportCommandParser.java
``` java
/**
 * Retrieves the location of the import file and passes the FileInputStream into
 * a new ImportCommand Object.
 */
public class ImportCommandParser implements Parser<ImportCommand> {

    @Override
    public ImportCommand parse(String userInput) throws ParseException {
        JButton open = new JButton();
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new java.io.File("C:/"));
        fc.setDialogTitle("Select your vCard file");
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (fc.showOpenDialog(open) == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            try {
                FileInputStream fis = new FileInputStream(file);
                return new ImportCommand(fis);
            } catch (IOException ioe) {
                throw new ParseException(ImportCommand.MESSAGE_FILEERROR);
            }
        } else {
            throw new ParseException(ImportCommand.MESSAGE_NOFILECHOSEN);
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
