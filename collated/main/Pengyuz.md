# Pengyuz
###### /java/seedu/address/commons/events/model/RecyclebinChangeEvent.java
``` java
/** Indicates the Recyclebin in the model has changed*/
public class RecyclebinChangeEvent extends BaseEvent {

    public final ReadOnlyAddressBook data;

    public RecyclebinChangeEvent(ReadOnlyAddressBook data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getPersonList().size() + ", number of tags " + data.getTagList().size()
                + ", number of events " + data.getEventList().size();
    }
}
```
###### /java/seedu/address/logic/commands/BinclearCommand.java
``` java
/**
 * Clears the recyclebin.
 */
public class BinclearCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "bin-fresh";
    public static final String MESSAGE_SUCCESS = "Recyclebin has been cleared!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Clear all the person in the recyclebin.";


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);
        model.resetRecyclebin(new AddressBook());
        //EventsCenter.getInstance().post(new ClearPersonListEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### /java/seedu/address/logic/commands/BindeleteCommand.java
``` java
/**
 * Delete the person in bin forever
 */
public class BindeleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "bin-delete";

    public static final String MESSAGE_SUCCESS = "Forever deleted.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + "Delete the person in bin forever.";
    private ArrayList<Index> targets;
    private boolean allvalid = true;
    private boolean exist  = false;

    public BindeleteCommand(ArrayList<Index> targets) {
        this.targets = targets;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastshownlist = model.getRecycleBinPersonList();
        ArrayList<ReadOnlyPerson> personstodelete = new ArrayList<>();

        for (Index s: targets) {
            if (s.getZeroBased() >= lastshownlist.size()) {
                allvalid = false;
            } else {
                personstodelete.add(lastshownlist.get(s.getZeroBased()));
                exist = true;
            }
        }

        if (allvalid && exist) {
            try {
                model.deleteBinPerson(personstodelete);
            } catch (PersonNotFoundException pnfe) {
                assert false : "The target person cannot be missing";
            }
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BindeleteCommand
                && this.targets.equals(((BindeleteCommand) other).targets)); // state check
    }
}
```
###### /java/seedu/address/logic/commands/BinrestoreCommand.java
``` java
/**
 * Restore the person in bin to address book;
 */
public class BinrestoreCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "bin-restore";

    public static final String MESSAGE_SUCCESS = "Resotred";
    public static final String MESSAGE_USAGE = COMMAND_WORD + "Restore the person in bin to address book";
    private ArrayList<Index> targets;
    private boolean allvalid = true;
    private boolean exist  = false;

    public BinrestoreCommand(ArrayList<Index> targets) {
        this.targets = targets;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastshownlist = model.getRecycleBinPersonList();
        ArrayList<ReadOnlyPerson> personstodelete = new ArrayList<>();

        for (Index s: targets) {
            if (s.getZeroBased() >= lastshownlist.size()) {
                allvalid = false;
            } else {
                personstodelete.add(lastshownlist.get(s.getZeroBased()));
                exist = true;
            }
        }

        if (allvalid && exist) {
            try {
                model.restorePerson(personstodelete);
            } catch (PersonNotFoundException pnfe) {
                assert false : "The target person cannot be missing";
            } catch (DuplicatePersonException d) {
                assert false : "the duplicate person in bin should be handled";
            }
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BinrestoreCommand
                && this.targets.equals(((BinrestoreCommand) other).targets)); // state check
    }

}
```
###### /java/seedu/address/logic/commands/DeleteCommand.java
``` java
/**
 * Deletes a person identified using it's last displayed index or name from the address book.
 */

public class DeleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "delete";
    public static final String COMMAND_WORD_2 = "remove";
    public static final String COMMAND_WORD_3 = "-";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) or NAME(exactly same)\n"
            + "Example: " + COMMAND_WORD + " 1" + COMMAND_WORD + "Alex Yeoh";
    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the recycle bin";
    private boolean allvalid = true;
    private boolean exist = false;
    private boolean duplicate = false;

    private ArrayList<Index> targetIndexs = new ArrayList<>();
    private String target = "";

    public DeleteCommand(ArrayList<Index> targetIndex) {
        this.targetIndexs = targetIndex;
    }
    public DeleteCommand(String target) {
        this.target = target;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList =  model.getFilteredPersonList();
        ArrayList<ReadOnlyPerson> personstodelete =  new ArrayList<ReadOnlyPerson>();
        if (target != "") {
            for (ReadOnlyPerson p: lastShownList) {
                if (p.getName().fullName.equals(target) && exist == true) {
                    personstodelete.add(p);
                    duplicate = true;
                }
                if (p.getName().fullName.equals(target)) {
                    personstodelete.add(p);
                    exist = true;
                }

            }
        } else {
            for (Index s: targetIndexs) {
                if (s.getZeroBased() >= lastShownList.size()) {
                    allvalid = false;
                } else {
                    personstodelete.add(lastShownList.get(s.getZeroBased()));
                    exist = true;
                }
            }
        }

        if (exist && duplicate) {
            List<String> duplicatePerson = Arrays.asList(target);
            NameContainsKeywordsPredicate updatedpredicate = new NameContainsKeywordsPredicate(duplicatePerson);
            model.updateFilteredPersonList(updatedpredicate);
            return new CommandResult("Duplicate persons exist, please choose one to delete.");
        }

        if (allvalid && exist) {
            try {
                model.deletePerson(personstodelete);
            } catch (PersonNotFoundException pnfe) {
                assert false : "The target person cannot be missing";
            } catch (DuplicatePersonException d) {
                assert false : "the duplicate person in bin should be handled";
            }
            return new CommandResult(MESSAGE_DELETE_PERSON_SUCCESS);
        } else {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }


    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && this.targetIndexs.equals(((DeleteCommand) other).targetIndexs))
                && (other instanceof DeleteCommand
                && this.target.equals(((DeleteCommand) other).target)); // state check
    }
}

```
###### /java/seedu/address/logic/commands/ExportCommand.java
``` java
/**
 * export the person details in txt
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_SUCCESS = "new file created";
    public static final String MESSAGE_USAGE = COMMAND_WORD + "export the person details in txt file";
    private String filepath;
    public ExportCommand (String f) {
        this.filepath = f.trim();
    }
    @Override
    public CommandResult execute() throws CommandException {
        try {
            File file = new File(filepath);
            BufferedWriter output = new BufferedWriter(new FileWriter(file));
            output.write("New backup addressbook storage is created at " + filepath + " "
                    + LocalDateTime.now());
            output.newLine();
            output.write("==================================================================================");
            output.newLine();
            output.newLine();

            for (int i = 0; i < model.getAddressBook().getPersonList().size(); i++) {
                output.write("Person No." + (i + 1));
                output.newLine();
                output.write("name:" + model.getAddressBook().getPersonList().get(i).getName().fullName);
                output.newLine();
                if (!"01/01/1900".equals(model.getAddressBook().getPersonList().get(i).getBirthday().toString())) {
                    output.write("birthday:"
                            + model.getAddressBook().getPersonList().get(i).getBirthday().toString());
                    output.newLine();
                }
                output.write("phone:" + model.getAddressBook().getPersonList().get(i).getPhone().toString());
                output.newLine();
                output.write("email:" + model.getAddressBook().getPersonList().get(i).getEmail().toString());
                output.newLine();
                output.write("address:" + model.getAddressBook().getPersonList().get(i).getAddress().toString());
                output.newLine();
                output.write("tags:" + model.getAddressBook().getPersonList().get(i).getTags().toString());
                output.newLine();
                output.write("dateAdded:" + model.getAddressBook().getPersonList().get(i).getDateAdded().toString());
                output.newLine();
                output.write("eventAdded:");
                for (Event o: model.getAddressBook().getPersonList().get(i).getEvents()) {
                    output.write(o.getEventName().fullName + " || ");
                }
                output.newLine();
                output.write("==================================================================================");
                output.newLine();
                output.newLine();
            }
            for (int i = 0; i < model.getEventList().size(); i++) {
                output.write("Event No." + (i + 1));
                output.newLine();
                output.write("eventName:" + model.getEventList().get(i).getEventName().fullName);
                output.newLine();
                output.write("eventTime:" + model.getEventList().get(i).getEventTime().toString());
                output.newLine();
                output.write("eventDuration:" + model.getEventList().get(i).getEventTime().toString());
                output.newLine();
                output.write("==================================================================================");
                output.newLine();
                output.newLine();

            }
            output.write("End of file");
            output.close();
            return new CommandResult(MESSAGE_SUCCESS);

        } catch (Exception e) {
            throw new CommandException("can't create a file in the path" + filepath);
        }
    }
}
```
###### /java/seedu/address/logic/commands/HelpCommand.java
``` java
/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";
    public static final String COMMAND_WORD_2 = "h";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";
    private String commandword = "";
    public HelpCommand() {}
    public HelpCommand(String args) {
        commandword = args;
    }

    @Override
    public CommandResult execute() {
        if ("bin-clear".equals(commandword)) {
            return new CommandResult(BinclearCommand.MESSAGE_USAGE);
        } else if ("bin-delete".equals(commandword)) {
            return new CommandResult(BindeleteCommand.MESSAGE_USAGE);
        } else if ("bin-restore".equals(commandword)) {
            return new CommandResult(BinrestoreCommand.MESSAGE_USAGE);
        } else if ("export".equals(commandword)) {
            return new CommandResult(ExportCommand.MESSAGE_USAGE);
        } else if ("add".equals(commandword)) {
            return new CommandResult(AddCommand.MESSAGE_USAGE);
        } else if ("clear".equals(commandword)) {
            return new CommandResult(ClearCommand.MESSAGE_USAGE);
        } else if ("delete".equals(commandword)) {
            return new CommandResult(DeleteCommand.MESSAGE_USAGE);
        } else if ("edit".equals(commandword)) {
            return new CommandResult(EditCommand.MESSAGE_USAGE);
        } else if ("exit".equals(commandword)) {
            return new CommandResult(ExitCommand.MESSAGE_USAGE);
        } else if ("find".equals(commandword)) {
            return new CommandResult(FindCommand.MESSAGE_USAGE);
        } else if ("history".equals(commandword)) {
            return new CommandResult(HistoryCommand.MESSAGE_USAGE);
        } else if ("list".equals(commandword)) {
            return new CommandResult(ListCommand.MESSAGE_USAGE);
        } else if ("redo".equals(commandword)) {
            return new CommandResult(RedoCommand.MESSAGE_USAGE);
        } else if ("select".equals(commandword)) {
            return new CommandResult(SelectCommand.MESSAGE_USAGE);
        } else if ("sort".equals(commandword)) {
            return new CommandResult(SortCommand.MESSAGE_USAGE);
        } else if ("tagadd".equals(commandword)) {
            return new CommandResult(TagAddCommand.MESSAGE_USAGE);
        } else if ("tagremove".equals(commandword)) {
            return new CommandResult(TagRemoveCommand.MESSAGE_USAGE);
        } else if ("tagfind".equals(commandword)) {
            return new CommandResult(TagFindCommand.MESSAGE_USAGE);
        } else if ("birthdayadd".equals(commandword)) {
            return new CommandResult(BirthdayAddCommand.MESSAGE_USAGE);
        } else if ("birthdayremove".equals(commandword)) {
            return new CommandResult(BirthdayRemoveCommand.MESSAGE_USAGE);
        } else if ("mapshow".equals(commandword)) {
            return new CommandResult(MapShowCommand.MESSAGE_USAGE);
        } else if ("maproute".equals(commandword)) {
            return new CommandResult(MapRouteCommand.MESSAGE_USAGE);
        } else if ("scheduleadd".equals(commandword)) {
            return new CommandResult(ScheduleAddCommand.MESSAGE_USAGE);
        } else if ("scheduleremove".equals(commandword)) {
            return new CommandResult(ScheduleRemoveCommand.MESSAGE_USAGE);
        } else if ("undo".equals(commandword)) {
            return new CommandResult(UndoCommand.MESSAGE_USAGE);
        } else {
            EventsCenter.getInstance().post(new ShowHelpRequestEvent());
            return new CommandResult(SHOWING_HELP_MESSAGE);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof HelpCommand // instanceof handles nulls
                && this.commandword.equals(((HelpCommand) other).commandword)); // state check
    }
}
```
###### /java/seedu/address/logic/commands/UndoableCommand.java
``` java
    /**
     * Stores the current state of {@code model#recyclebin}.
     */
    private void saveRecycleBinSnapshot() {
        requireNonNull(model);
        this.previousRecycleBin = new AddressBook(model.getRecycleBin());
    }

    /**
     * Reverts the AddressBook and Recyclebin to the state before this command
     * was executed and updates the filtered person list to
     * show all persons.
     */
    protected final void undo() {
        requireAllNonNull(model, previousAddressBook, previousRecycleBin);
        model.resetData(previousAddressBook, previousRecycleBin);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    /**
     * Executes the command and updates the filtered person
     * list to show all persons.
     */
    protected final void redo() {
        requireNonNull(model);
        try {
            executeUndoableCommand();
        } catch (CommandException ce) {
            throw new AssertionError("The command has been successfully executed previously; "
                    + "it should not fail now");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public final CommandResult execute() throws CommandException {
        saveAddressBookSnapshot();
        saveRecycleBinSnapshot();
        return executeUndoableCommand();
    }
}
```
###### /java/seedu/address/logic/parser/BindeleteCommandParser.java
``` java
/**
 * Parses input arguments and creates a new BindeleteCommand object
 */
public class BindeleteCommandParser implements Parser<BindeleteCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the BindeleteCommand
     * and returns an BindeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public BindeleteCommand parse(String args) throws ParseException {
        ArrayList<Index> index;
        String arguments = args.trim();
        if (arguments.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, BindeleteCommand.MESSAGE_USAGE));
        }
        try {
            index = ParserUtil.parseIndexes(arguments);
            return new BindeleteCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BindeleteCommand.MESSAGE_USAGE));
        }
    }
}
```
###### /java/seedu/address/logic/parser/BinrestoreCommandParser.java
``` java
/**
 * Parses input arguments and creates a new BinrestoreCommand object
 */
public class BinrestoreCommandParser implements Parser<BinrestoreCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the BinrestoreCommand
     * and returns an BinrestoreCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public BinrestoreCommand parse(String args) throws ParseException {
        ArrayList<Index> index;
        String arguments = args.trim();
        if (arguments.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, BinrestoreCommand.MESSAGE_USAGE));
        }
        try {
            index = ParserUtil.parseIndexes(arguments);
            return new BinrestoreCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BinrestoreCommand.MESSAGE_USAGE));
        }
    }

}
```
###### /java/seedu/address/logic/parser/DeleteCommandParser.java
``` java
/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        String argsWithoutpre;
        ArrayList<Index> index;
        String arguments = args.trim();
        if (arguments.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        int indexOfname = arguments.indexOf("n/");
        int indexOfnumbers = arguments.indexOf("I/");

        if ((indexOfname == -1) && (indexOfnumbers == -1)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        String atStart = arguments.substring(0, 2);
        if ((!atStart.equals("n/")) && (!atStart.equals("I/"))) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        if (atStart.equals("I/")) {
            argsWithoutpre = arguments.replace("I/", "").trim();
            try {
                index = ParserUtil.parseIndexes(argsWithoutpre);
                return new DeleteCommand(index);
            } catch (IllegalValueException ive) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }
        } else {
            argsWithoutpre = arguments.replace("n/", "").trim();
            if (argsWithoutpre.equals("")) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }
            return new DeleteCommand(argsWithoutpre);
        }
    }

}
```
###### /java/seedu/address/logic/parser/ParserUtil.java
``` java
    /**
     * Parses  {@code oneBasedIndex} into an {@code numbers} and return it.the commas will be deleted.
     *
     * @throws IllegalValueException if the specified index is invalid.
     */
    public static ArrayList<Index> parseIndexes(String oneBasedIndexes) throws IllegalValueException {
        String[] ns = oneBasedIndexes.trim().split(" ");
        ArrayList<Index> numbers = new ArrayList<>();
        boolean allvalid = true;
        for (String a : ns) {
            String s = a.trim();
            if (StringUtil.isNonZeroUnsignedInteger(s)) {
                numbers.add(Index.fromOneBased(Integer.parseInt(s)));
            } else {
                allvalid = false;

            }
        }
        if (!allvalid) {
            throw new IllegalValueException(MESSAGE_INVALID_INDEX);
        }
        return numbers;

    }
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public void resetRecyclebin(ReadOnlyAddressBook newData) {
        recycleBin.resetData(newData);
        indicateRecycleBinChanged();
    }
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public ReadOnlyAddressBook getRecycleBin() {
        return recycleBin;
    }
```
###### /java/seedu/address/model/ModelManager.java
``` java
    private void indicateRecycleBinChanged() {
        raise(new RecyclebinChangeEvent(recycleBin));
    }
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public synchronized void deletePerson(ArrayList<ReadOnlyPerson> targets) throws PersonNotFoundException,
            DuplicatePersonException {

        for (ReadOnlyPerson s : targets) {
            if (recycleBin.getPersonList().contains(s)) {
                addressBook.removePerson(s);
            } else {
                addressBook.removePerson(s);
                recycleBin.addPerson(s);
            }
        }
        indicateRecycleBinChanged();
        indicateAddressBookChanged();
    }
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public synchronized void deleteBinPerson(ArrayList<ReadOnlyPerson> targets) throws PersonNotFoundException {
        for (ReadOnlyPerson s: targets) {
            recycleBin.removePerson(s);
        }
        indicateRecycleBinChanged();
    }
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public synchronized void restorePerson(ReadOnlyPerson person) throws DuplicatePersonException,
            PersonNotFoundException {
        addressBook.addPerson(person);
        recycleBin.removePerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        updateFilteredBinList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
        indicateAddressBookChanged();
    }
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public synchronized void restorePerson(ArrayList<ReadOnlyPerson> targets) throws DuplicatePersonException,
            PersonNotFoundException {
        boolean flag = true;
        for (ReadOnlyPerson s : targets) {
            if (addressBook.getPersonList().contains(s)) {
                recycleBin.removePerson(s);
            } else {
                recycleBin.removePerson(s);
                addressBook.addPerson(s);
                flag = false;
            }
        }
        if (!flag) {
            indicateAddressBookChanged();
        }
        indicateRecycleBinChanged();
    }
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public ObservableList<ReadOnlyPerson> getRecycleBinPersonList() {
        return FXCollections.unmodifiableObservableList(filteredRecycle);
    }
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public void updateFilteredBinList(Predicate<ReadOnlyPerson> predicate) {
        requireNonNull(predicate);
        filteredRecycle.setPredicate(predicate);
    }
```
###### /java/seedu/address/model/util/SampleRecycleUtil.java
``` java
/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleRecycleUtil {
    public static Person[] getSamplePersons() {
        try {
            return new Person[] {
                new Person(new Name("Alex Yeohre"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"), getTagSet("friends"),
                    new HashSet<>(), new DateAdded("01/01/2016 11:11:53")),
                new Person(new Name("Bernice Yure"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    getTagSet("colleagues", "friends"), new HashSet<>(),
                    new DateAdded("07/02/2016 12:00:01")),
                new Person(new Name("Charlotte Oliveirore"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), getTagSet("neighbours"),
                    new HashSet<>(), new DateAdded("01/05/2016 12:00:01")),
                new Person(new Name("David Lire"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), getTagSet("family"),
                    new HashSet<>(), new DateAdded("15/09/2017 12:00:01")),
                new Person(new Name("Irfan Ibrahimre"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"), getTagSet("classmates"),
                    new HashSet<>(), new DateAdded("15/09/2017 12:01:01")),
                new Person(new Name("Roy Balakrishnanre"), new Phone("92624417"), new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), getTagSet("colleagues"),
                    new HashSet<>(), new DateAdded("20/09/2017 12:00:01"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyAddressBook getSampleRecycleBin() {
        try {
            AddressBook sampleAb = new AddressBook();
            for (Person samplePerson : getSamplePersons()) {
                sampleAb.addPerson(samplePerson);
            }
            return sampleAb;
        } catch (DuplicatePersonException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        }
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) throws IllegalValueException {
        HashSet<Tag> tags = new HashSet<>();
        for (String s : strings) {
            tags.add(new Tag(s));
        }

        return tags;
    }


}
```
###### /java/seedu/address/storage/RecycleBinStorage.java
``` java
/**
 * Represents a storage for {@link seedu.address.model.AddressBook}.
 */
public interface RecycleBinStorage {

    /**
     * Returns the file path of the data file.
     */
    String getRecycleBinFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyAddressBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyAddressBook> readRecycleBin() throws DataConversionException, IOException;

    /**
     * @see #getRecycleBinFilePath() ()
     */
    Optional<ReadOnlyAddressBook> readRecycleBin(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyAddressBook} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveRecycleBin(ReadOnlyAddressBook addressBook) throws IOException;

    /**
     * @see #saveRecycleBin(ReadOnlyAddressBook) (ReadOnlyAddressBook)
     */
    void saveRecycleBin(ReadOnlyAddressBook addressBook, String filePath) throws IOException;

}
```
###### /java/seedu/address/storage/StorageManager.java
``` java
    // ================ RecycleBinStorage methods ==============================

    @Override
    public String getRecycleBinFilePath() {
        return recycleBinStorage.getRecycleBinFilePath();
    }

    @Override
    public Optional<ReadOnlyAddressBook> readRecycleBin() throws DataConversionException, IOException {
        return readRecycleBin(recycleBinStorage.getRecycleBinFilePath());
    }

    @Override
    public Optional<ReadOnlyAddressBook> readRecycleBin(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return recycleBinStorage.readRecycleBin(filePath);
    }

    @Override
    public void saveRecycleBin(ReadOnlyAddressBook recycleBin) throws IOException {
        saveRecycleBin(recycleBin, recycleBinStorage.getRecycleBinFilePath());
    }

    @Override
    public void saveRecycleBin(ReadOnlyAddressBook recycleBin, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        recycleBinStorage.saveRecycleBin(recycleBin, filePath);
    }


    @Override
    @Subscribe
    public void handleRecycleBinChangeEvent(RecyclebinChangeEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveRecycleBin(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }
```
###### /java/seedu/address/storage/XmlRecycleBinStorage.java
``` java
/**
 * A class to access AddressBook data stored as an xml file on the hard disk.
 */
public class XmlRecycleBinStorage implements RecycleBinStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlRecycleBinStorage.class);

    private String filePath;

    public XmlRecycleBinStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getRecycleBinFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyAddressBook> readRecycleBin() throws DataConversionException, IOException {
        return readRecycleBin(filePath);
    }

    /**
     * Similar to {@link #readRecycleBin()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyAddressBook> readRecycleBin(String filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        File addressBookFile = new File(filePath);

        if (!addressBookFile.exists()) {
            logger.info("Recyclebin file "  + addressBookFile + " not found");
            return Optional.empty();
        }

        ReadOnlyAddressBook addressBookOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(addressBookOptional);
    }

    @Override
    public void saveRecycleBin(ReadOnlyAddressBook addressBook) throws IOException {
        saveRecycleBin(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveRecycleBin(ReadOnlyAddressBook)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveRecycleBin(ReadOnlyAddressBook addressBook, String filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableAddressBook(addressBook));
    }

}
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public synchronized void deletePerson(ArrayList<ReadOnlyPerson> targets) throws PersonNotFoundException,
            DuplicatePersonException {

        for (ReadOnlyPerson s : targets) {
            if (recycleBin.getPersonList().contains(s)) {
                addressBook.removePerson(s);
            } else {
                addressBook.removePerson(s);
                recycleBin.addPerson(s);
            }
        }
        indicateRecycleBinChanged();
        indicateAddressBookChanged();
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public synchronized void deleteBinPerson(ArrayList<ReadOnlyPerson> targets) throws PersonNotFoundException {
        for (ReadOnlyPerson s: targets) {
            recycleBin.removePerson(s);
        }
        indicateRecycleBinChanged();
    }

    @Override
    public synchronized void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public synchronized void restorePerson(ReadOnlyPerson person) throws DuplicatePersonException,
            PersonNotFoundException {
        addressBook.addPerson(person);
        recycleBin.removePerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        updateFilteredBinList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
        indicateAddressBookChanged();
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public synchronized void restorePerson(ArrayList<ReadOnlyPerson> targets) throws DuplicatePersonException,
            PersonNotFoundException {
        boolean flag = true;
        for (ReadOnlyPerson s : targets) {
            if (addressBook.getPersonList().contains(s)) {
                recycleBin.removePerson(s);
            } else {
                recycleBin.removePerson(s);
                addressBook.addPerson(s);
                flag = false;
            }
        }
        if (!flag) {
            indicateAddressBookChanged();
        }
        indicateRecycleBinChanged();
    }

    @Override
    public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireAllNonNull(target, editedPerson);

        addressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
    }

```
###### \java\seedu\address\model\util\SampleRecycleUtil.java
``` java
/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleRecycleUtil {
    public static Person[] getSamplePersons() {
        try {
            return new Person[] {
                new Person(new Name("Alex Yeohre"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"), getTagSet("friends"),
                    new HashSet<>(), new DateAdded("01/01/2016 11:11:53")),
                new Person(new Name("Bernice Yure"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    getTagSet("colleagues", "friends"), new HashSet<>(),
                    new DateAdded("07/02/2016 12:00:01")),
                new Person(new Name("Charlotte Oliveirore"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), getTagSet("neighbours"),
                    new HashSet<>(), new DateAdded("01/05/2016 12:00:01")),
                new Person(new Name("David Lire"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), getTagSet("family"),
                    new HashSet<>(), new DateAdded("15/09/2017 12:00:01")),
                new Person(new Name("Irfan Ibrahimre"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"), getTagSet("classmates"),
                    new HashSet<>(), new DateAdded("15/09/2017 12:01:01")),
                new Person(new Name("Roy Balakrishnanre"), new Phone("92624417"), new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), getTagSet("colleagues"),
                    new HashSet<>(), new DateAdded("20/09/2017 12:00:01"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyAddressBook getSampleRecycleBin() {
        try {
            AddressBook sampleAb = new AddressBook();
            for (Person samplePerson : getSamplePersons()) {
                sampleAb.addPerson(samplePerson);
            }
            return sampleAb;
        } catch (DuplicatePersonException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        }
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) throws IllegalValueException {
        HashSet<Tag> tags = new HashSet<>();
        for (String s : strings) {
            tags.add(new Tag(s));
        }

        return tags;
    }


}
```
###### \java\seedu\address\storage\RecycleBinStorage.java
``` java
/**
 * Represents a storage for {@link seedu.address.model.AddressBook}.
 */
public interface RecycleBinStorage {

    /**
     * Returns the file path of the data file.
     */
    String getRecycleBinFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyAddressBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyAddressBook> readRecycleBin() throws DataConversionException, IOException;

    /**
     * @see #getRecycleBinFilePath() ()
     */
    Optional<ReadOnlyAddressBook> readRecycleBin(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyAddressBook} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveRecycleBin(ReadOnlyAddressBook addressBook) throws IOException;

    /**
     * @see #saveRecycleBin(ReadOnlyAddressBook) (ReadOnlyAddressBook)
     */
    void saveRecycleBin(ReadOnlyAddressBook addressBook, String filePath) throws IOException;

}
```
###### \java\seedu\address\storage\XmlRecycleBinStorage.java
``` java
/**
 * A class to access AddressBook data stored as an xml file on the hard disk.
 */
public class XmlRecycleBinStorage implements RecycleBinStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlRecycleBinStorage.class);

    private String filePath;

    public XmlRecycleBinStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getRecycleBinFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyAddressBook> readRecycleBin() throws DataConversionException, IOException {
        return readRecycleBin(filePath);
    }

    /**
     * Similar to {@link #readRecycleBin()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyAddressBook> readRecycleBin(String filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        File addressBookFile = new File(filePath);

        if (!addressBookFile.exists()) {
            logger.info("Recyclebin file "  + addressBookFile + " not found");
            return Optional.empty();
        }

        ReadOnlyAddressBook addressBookOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(addressBookOptional);
    }

    @Override
    public void saveRecycleBin(ReadOnlyAddressBook addressBook) throws IOException {
        saveRecycleBin(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveRecycleBin(ReadOnlyAddressBook)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveRecycleBin(ReadOnlyAddressBook addressBook, String filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableAddressBook(addressBook));
    }

}
```
