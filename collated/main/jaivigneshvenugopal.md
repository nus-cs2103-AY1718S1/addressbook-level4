# jaivigneshvenugopal
###### \java\seedu\address\logic\commands\BanCommand.java
``` java
/**
 * Adds a person identified using it's last displayed index into the blacklist.
 */
public class BanCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "ban";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Ban a person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_BAN_PERSON_SUCCESS = "%1$s has been added to BLACKLIST";
    public static final String MESSAGE_BAN_PERSON_FAILURE = "%1$s is already in BLACKLIST!";

    private final Index targetIndex;

    public BanCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        String messagetoDisplay = MESSAGE_BAN_PERSON_SUCCESS;
        List<ReadOnlyPerson> lastShownList = listObserver.getCurrentFilteredList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToBan = lastShownList.get(targetIndex.getZeroBased());

        if (personToBan.isBlacklisted()) {
            messagetoDisplay = MESSAGE_BAN_PERSON_FAILURE;
        } else {
            model.addBlacklistedPerson(personToBan);
        }

        listObserver.updateCurrentFilteredList(PREDICATE_SHOW_ALL_PERSONS);

        String currentList = listObserver.getCurrentListName();

        return new CommandResult(currentList + String.format(messagetoDisplay, personToBan.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BanCommand // instanceof handles nulls
                && this.targetIndex.equals(((BanCommand) other).targetIndex)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\BlacklistCommand.java
``` java
/**
 * Lists all blacklisted persons in the address book to the user.
 */
public class BlacklistCommand extends Command {

    public static final String COMMAND_WORD = "blacklist";
    public static final String COMMAND_WORD_ALIAS = "bl";

    public static final String MESSAGE_SUCCESS = "Listed all debtors "
            + "who are prohibited from borrowing money";


    @Override
    public CommandResult execute() {
        model.changeListTo(COMMAND_WORD);
        model.updateFilteredBlacklistedPersonList(PREDICATE_SHOW_ALL_BLACKLISTED_PERSONS);
        String currentList = listObserver.getCurrentListName();
        return new CommandResult(currentList + MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\logic\commands\RepaidCommand.java
``` java
/**
 * Adds a person identified using it's last displayed index into the whitelist.
 * Resets person's debt to zero and sets the date repaid field.
 */
public class RepaidCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "repaid";
    public static final String COMMAND_WORD_ALIAS = "rp";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds person identified by the index number into the whitelist and concurrently clear his debt.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_REPAID_PERSON_SUCCESS = "%1$s has now repaid his/her debt";
    public static final String MESSAGE_REPAID_PERSON_FAILURE = "%1$s has already repaid debt!";

    private final Index targetIndex;

    public RepaidCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        String messagetoDisplay = MESSAGE_REPAID_PERSON_SUCCESS;
        List<ReadOnlyPerson> lastShownList = listObserver.getCurrentFilteredList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToWhitelist = lastShownList.get(targetIndex.getZeroBased());

        if (personToWhitelist.getDebt().toNumber() == 0) {
            messagetoDisplay = MESSAGE_REPAID_PERSON_FAILURE;
        } else {
            model.addWhitelistedPerson(personToWhitelist);
        }

        listObserver.updateCurrentFilteredList(PREDICATE_SHOW_ALL_PERSONS);

        String currentList = listObserver.getCurrentListName();

        return new CommandResult(currentList + String.format(messagetoDisplay, personToWhitelist.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RepaidCommand // instanceof handles nulls
                && this.targetIndex.equals(((RepaidCommand) other).targetIndex)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\UnbanCommand.java
``` java
/**
 * Removes a person identified using it's last displayed index from the blacklist.
 */
public class UnbanCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "unban";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unban a person identified by the index number used in the last person listing from blacklist.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_UNBAN_PERSON_SUCCESS = "Removed %1$s from BLACKLIST";
    public static final String MESSAGE_UNBAN_PERSON_FAILURE = "%1$s is not BLACKLISTED!";

    private final Index targetIndex;

    public UnbanCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        String messagetoDisplay = MESSAGE_UNBAN_PERSON_SUCCESS;
        List<ReadOnlyPerson> lastShownList = listObserver.getCurrentFilteredList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToUnban = lastShownList.get(targetIndex.getZeroBased());

        try {
            if (personToUnban.isBlacklisted()) {
                model.removeBlacklistedPerson(personToUnban);
            } else {
                messagetoDisplay = MESSAGE_UNBAN_PERSON_FAILURE;
            }
        } catch (PersonNotFoundException e) {
            assert false : "The target person is not in blacklist";
        }

        listObserver.updateCurrentFilteredList(PREDICATE_SHOW_ALL_PERSONS);

        String currentList = listObserver.getCurrentListName();

        return new CommandResult(currentList + String.format(messagetoDisplay, personToUnban.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnbanCommand // instanceof handles nulls
                && this.targetIndex.equals(((UnbanCommand) other).targetIndex)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\WhitelistCommand.java
``` java
/**
 * Lists all persons who have cleared their debts.
 */
public class WhitelistCommand extends Command {

    public static final String COMMAND_WORD = "whitelist";
    public static final String COMMAND_WORD_ALIAS = "wl";

    public static final String MESSAGE_SUCCESS = "Listed all debtors "
            + "who have cleared their debts";


    @Override
    public CommandResult execute() {
        model.changeListTo(COMMAND_WORD);
        model.updateFilteredWhitelistedPersonList(PREDICATE_SHOW_ALL_WHITELISTED_PERSONS);
        String currentList = listObserver.getCurrentListName();
        return new CommandResult(currentList + MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\logic\ListObserver.java
``` java
/**
 * Monitors current displayed list on person list panel.
 * Obtains or updates the various lists according to the changes made to the current displayed list.
 */
public class ListObserver {

    public static final String MASTERLIST_NAME_DISPLAY_FORMAT = "MASTERLIST:\n";
    public static final String BLACKLIST_NAME_DISPLAY_FORMAT = "BLACKLIST:\n";
    public static final String WHITELIST_NAME_DISPLAY_FORMAT = "WHITELIST:\n";
    public static final String OVERDUELIST_NAME_DISPLAY_FORMAT = "OVERDUELIST:\n";

    private Model model;

    public ListObserver(Model model) {
        this.model = model;
    }

    /**
     * Monitors current displayed list on person list panel.
     * @return updated version of the current displayed list.
     */
    public List<ReadOnlyPerson> getCurrentFilteredList() {
        String currentList = model.getCurrentListName();

        switch (currentList) {

        case BlacklistCommand.COMMAND_WORD:
            return model.getFilteredBlacklistedPersonList();

        case WhitelistCommand.COMMAND_WORD:
            return model.getFilteredWhitelistedPersonList();

        case OverdueListCommand.COMMAND_WORD:
            return model.getFilteredOverduePersonList();

        default:
            return model.getFilteredPersonList();
        }
    }

    /**
     * Monitors current displayed list on person list panel.
     * Updates the current displayed list with {@param predicate}
     * @return updated version of the current displayed list.
     */
    public int updateCurrentFilteredList(Predicate<ReadOnlyPerson> predicate) {
        String currentList = model.getCurrentListName();

        switch (currentList) {

        case BlacklistCommand.COMMAND_WORD:
            model.changeListTo(BlacklistCommand.COMMAND_WORD);
            return model.updateFilteredBlacklistedPersonList(predicate);

        case WhitelistCommand.COMMAND_WORD:
            model.changeListTo(WhitelistCommand.COMMAND_WORD);
            return model.updateFilteredWhitelistedPersonList(predicate);

        case OverdueListCommand.COMMAND_WORD:
            model.changeListTo(OverdueListCommand.COMMAND_WORD);
            return model.updateFilteredOverduePersonList(predicate);

        default:
            return model.updateFilteredPersonList(predicate);
        }
    }

    /**
     * Monitors current displayed list on person list panel.
     * @return name of current displayed list.
     */
    public String getCurrentListName() {
        String currentList = model.getCurrentListName();

        switch (currentList) {

        case BlacklistCommand.COMMAND_WORD:
            return BLACKLIST_NAME_DISPLAY_FORMAT;

        case WhitelistCommand.COMMAND_WORD:
            return WHITELIST_NAME_DISPLAY_FORMAT;

        case OverdueListCommand.COMMAND_WORD:
            return OVERDUELIST_NAME_DISPLAY_FORMAT;

        default:
            return MASTERLIST_NAME_DISPLAY_FORMAT;
        }
    }

    /**
     * Monitors current displayed list on person list panel.
     * @return {@code Index} of current selected person.
     */
    public Index getIndexofSelectedPersonInCurrentList() {
        Index index;
        switch (model.getCurrentListName()) {

        case "blacklist":
            index = Index.fromZeroBased(model.getFilteredBlacklistedPersonList().indexOf(model.getSelectedPerson()));
            break;
        case "whitelist":
            index = Index.fromZeroBased(model.getFilteredWhitelistedPersonList().indexOf(model.getSelectedPerson()));
            break;
        case "overduelist":
            index = Index.fromZeroBased(model.getFilteredOverduePersonList().indexOf(model.getSelectedPerson()));
            break;
        default:
            index = Index.fromZeroBased(model.getFilteredPersonList().indexOf(model.getSelectedPerson()));
        }

        return index;
    }

    /**
     * @return {@code Index} of person in current displayed list.
     */
    public Index getIndexofPersonInCurrentList(ReadOnlyPerson person) {
        Index index;

        switch (model.getCurrentListName()) {
        case "blacklist":
            if (model.getFilteredBlacklistedPersonList().contains(person)) {
                index = Index.fromZeroBased(model.getFilteredBlacklistedPersonList()
                        .indexOf(person));
            } else {
                index = null;
            }
            break;

        case "whitelist":
            if (model.getFilteredWhitelistedPersonList().contains(person)) {
                index = Index.fromZeroBased(model.getFilteredWhitelistedPersonList()
                        .indexOf(person));
            } else {
                index = null;
            }
            break;

        case "overduelist":
            if (model.getFilteredOverduePersonList().contains(person)) {
                index = Index.fromZeroBased(model.getFilteredOverduePersonList()
                        .indexOf(person));
            } else {
                index = null;
            }
            break;

        default:
            if (model.getFilteredPersonList().contains(person)) {
                index = Index.fromZeroBased(model.getFilteredPersonList().indexOf(person));
            } else {
                index = null;
            }
        }

        return index;
    }
}
```
###### \java\seedu\address\logic\Logic.java
``` java
    /** Returns an unmodifiable view of the filtered list of blacklisted persons */
    ObservableList<ReadOnlyPerson> getFilteredBlacklistedPersonList();

    /** Returns an unmodifiable view of the filtered list of whitelisted persons */
    ObservableList<ReadOnlyPerson> getFilteredWhitelistedPersonList();
```
###### \java\seedu\address\logic\LogicManager.java
``` java
    @Override
    public ObservableList<ReadOnlyPerson> getFilteredBlacklistedPersonList() {
        return model.getFilteredBlacklistedPersonList();
    }

    @Override
    public ObservableList<ReadOnlyPerson> getFilteredWhitelistedPersonList() {
        return model.getFilteredWhitelistedPersonList();
    }
```
###### \java\seedu\address\logic\parser\BanCommandParser.java
``` java
/**
 * Parses input arguments and creates a new BanCommand object
 */
public class BanCommandParser implements Parser<BanCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the BanCommand
     * and returns an BanCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public BanCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new BanCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, BanCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\RepaidCommandParser.java
``` java
/**
 * Parses input arguments and creates a new RepaidCommand object
 */
public class RepaidCommandParser implements Parser<RepaidCommand> {
    /**
    * Parses the given {@code String} of arguments in the context of the RepaidCommand
    * and returns an RepaidCommand object for execution.
    * @throws ParseException if the user input does not conform the expected format
    */
    public RepaidCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new RepaidCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RepaidCommand.MESSAGE_USAGE));
        }
    }

}
```
###### \java\seedu\address\logic\parser\UnbanCommandParser.java
``` java
/**
 * Parses input arguments and creates a new UnbanCommand object
 */
public class UnbanCommandParser implements Parser<UnbanCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the UnbanCommand
     * and returns an UnbanCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public UnbanCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new UnbanCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnbanCommand.MESSAGE_USAGE));
        }
    }
}

```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Adds a person to the blacklist in the address book.
     * @return ReadOnly newBlacklistedPerson
     */
    public ReadOnlyPerson addBlacklistedPerson(ReadOnlyPerson p) {
        int index;
        index = persons.getIndexOf(p);

        Person newBlacklistedPerson = new Person(p);
        newBlacklistedPerson.setIsBlacklisted(true);
        try {
            updatePerson(p, newBlacklistedPerson);
        } catch (DuplicatePersonException e) {
            throw new AssertionError("The target person cannot be a duplicate");
        } catch (PersonNotFoundException e) {
            throw new AssertionError("This is not possible as prior checks have been done");
        }

        return persons.getReadOnlyPerson(index);
    }

    /**
     * Adds a person to the whitelist in the address book.
     * @return ReadOnly newWhitelistedPerson
     */
    public ReadOnlyPerson addWhitelistedPerson(ReadOnlyPerson p) {
        int index;
        index = persons.getIndexOf(p);

        Person newWhitelistedPerson = new Person(p);
        newWhitelistedPerson.setIsWhitelisted(true);
        try {
            updatePerson(p, newWhitelistedPerson);
        } catch (DuplicatePersonException e) {
            throw new AssertionError("The target person cannot be a duplicate");
        } catch (PersonNotFoundException e) {
            throw new AssertionError("This is not possible as prior checks have been done");
        }
        return persons.getReadOnlyPerson(index);
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Updates {@code key} to exclude {@code key} from the blacklist in this {@code AddressBook}.
     * @return ReadOnly newUnBlacklistedPerson
     * @throws PersonNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public ReadOnlyPerson removeBlacklistedPerson(ReadOnlyPerson key) throws PersonNotFoundException {
        int index;
        index = persons.getIndexOf(key);

        Person newUnBlacklistedPerson = new Person(key);
        newUnBlacklistedPerson.setIsBlacklisted(false);

        if (newUnBlacklistedPerson.getDebt().toNumber() == 0) {
            newUnBlacklistedPerson.setIsWhitelisted(true);
        }

        persons.remove(key);

        try {
            persons.add(index, newUnBlacklistedPerson);
        } catch (DuplicatePersonException e) {
            assert false : "This is not possible as prior checks have"
                    + " been done to ensure AddressBook does not have duplicate persons";
        }

        return persons.getReadOnlyPerson(index);
    }

    /**
     * Updates {@code key} to exclude {@code key} from the whitelist in this {@code AddressBook}.
     * @return ReadOnly newWhitelistedPerson
     * @throws PersonNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public ReadOnlyPerson removeWhitelistedPerson(ReadOnlyPerson key) throws PersonNotFoundException {
        int index;
        index = persons.getIndexOf(key);

        Person newWhitelistedPerson = new Person(key);
        newWhitelistedPerson.setIsWhitelisted(false);
        persons.remove(key);
        try {
            persons.add(index, newWhitelistedPerson);
        } catch (DuplicatePersonException e) {
            assert false : "This is not possible as prior checks have"
                    + " been done to ensure AddressBook does not have duplicate persons";
        }
        return persons.getReadOnlyPerson(index);
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    @Override
    public ObservableList<ReadOnlyPerson> getBlacklistedPersonList() {
        return persons.asObservableBlacklist();
    }

    @Override
    public ObservableList<ReadOnlyPerson> getWhitelistedPersonList() {
        return persons.asObservableWhitelist();
    }
```
###### \java\seedu\address\model\Model.java
``` java
    /** {@code Predicate} that always evaluate to true */
    Predicate<ReadOnlyPerson> PREDICATE_SHOW_ALL_BLACKLISTED_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<ReadOnlyPerson> PREDICATE_SHOW_ALL_WHITELISTED_PERSONS = unused -> true;
```
###### \java\seedu\address\model\Model.java
``` java
    /** Returns the name of current displayed list */
    String getCurrentListName();

    /** Sets the name of current displayed list */
    void setCurrentListName(String currentList);
```
###### \java\seedu\address\model\Model.java
``` java
    /** Deletes the given person from blacklist and returns the deleted person */
    ReadOnlyPerson removeBlacklistedPerson(ReadOnlyPerson target) throws PersonNotFoundException;

    /** Removes the given person from whitelist and returns the updated person */
    ReadOnlyPerson removeWhitelistedPerson(ReadOnlyPerson target) throws PersonNotFoundException;
```
###### \java\seedu\address\model\Model.java
``` java
    /** Adds the given person into blacklist and returns the added person*/
    ReadOnlyPerson addBlacklistedPerson(ReadOnlyPerson person);

    /** Adds the given person into whitelist and returns the added person */
    ReadOnlyPerson addWhitelistedPerson(ReadOnlyPerson person);
```
###### \java\seedu\address\model\Model.java
``` java
    /** Returns an unmodifiable view of the blacklisted filtered person list */
    ObservableList<ReadOnlyPerson> getFilteredBlacklistedPersonList();

    /** Returns an unmodifiable view of the whitelisted filtered person list */
    ObservableList<ReadOnlyPerson> getFilteredWhitelistedPersonList();
```
###### \java\seedu\address\model\Model.java
``` java
    /**
     * Updates the filter of the filtered blacklisted person list to filter by the given {@code predicate}.
     * @return size of current displayed filtered list.
     * @throws NullPointerException if {@code predicate} is null.
     */
    int updateFilteredBlacklistedPersonList(Predicate<ReadOnlyPerson> predicate);

    /**
     * Updates the filter of the filtered whitelisted person list to filter by the given {@code predicate}.
     * @return size of current displayed filtered list.
     * @throws NullPointerException if {@code predicate} is null.
     */
    int updateFilteredWhitelistedPersonList(Predicate<ReadOnlyPerson> predicate);
```
###### \java\seedu\address\model\Model.java
``` java
    /**
     * Updates the list shown in Person List Panel to the requested list.
     */
    void changeListTo(String listName);
```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * @return String value of the current displayed list
     */
    @Override
    public String getCurrentListName() {
        return currentList;
    }

    /**
     * Sets String value of the current displayed list using value of {@param currentList}
     */
    @Override
    public void setCurrentListName(String currentList) {
        this.currentList = currentList;
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * Removes a specific person from blacklist in the AddressBook.
     * @param target to be removed from blacklist.
     * @return removedBlacklistedPerson
     * @throws PersonNotFoundException if no person is found.
     */
    @Override
    public synchronized ReadOnlyPerson removeBlacklistedPerson(ReadOnlyPerson target) throws PersonNotFoundException {
        ReadOnlyPerson removedBlacklistedPerson = addressBook.removeBlacklistedPerson(target);
        updateFilteredBlacklistedPersonList(PREDICATE_SHOW_ALL_BLACKLISTED_PERSONS);
        indicateAddressBookChanged();
        return removedBlacklistedPerson;
    }

    /**
     * Deletes a specific person from whitelist in the AddressBook.
     * @param target to be removed from whitelist.
     * @return removedBlacklistedPerson
     * @throws PersonNotFoundException if no person is found.
     */
    @Override
    public synchronized ReadOnlyPerson removeWhitelistedPerson(ReadOnlyPerson target) throws PersonNotFoundException {
        ReadOnlyPerson whitelistedPerson = addressBook.removeWhitelistedPerson(target);
        updateFilteredWhitelistedPersonList(PREDICATE_SHOW_ALL_WHITELISTED_PERSONS);
        indicateAddressBookChanged();
        return whitelistedPerson;
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * Adds a specific person to blacklist in the AddressBook.
     * @param person to be updated.
     * @return newBlacklistedPerson
     * @throws DuplicatePersonException if this operation causes a contact to be a duplicate of another.
     */
    @Override
    public synchronized ReadOnlyPerson addBlacklistedPerson(ReadOnlyPerson person) {
        ReadOnlyPerson newBlacklistPerson = person;

        if (person.isWhitelisted()) {
            try {
                newBlacklistPerson = addressBook.removeWhitelistedPerson(newBlacklistPerson);
            } catch (PersonNotFoundException e) {
                assert false : "This person cannot be missing from addressbook";
            }
        }
        addressBook.addBlacklistedPerson(newBlacklistPerson);
        updateFilteredBlacklistedPersonList(PREDICATE_SHOW_ALL_BLACKLISTED_PERSONS);
        indicateAddressBookChanged();
        return newBlacklistPerson;
    }

    /**
     * Adds a specific person to whitelist in the AddressBook.
     * @param person to be updated.
     * @return whitelistedPerson
     * @throws DuplicatePersonException if this operation causes a contact to be a duplicate of another.
     */
    @Override
    public synchronized ReadOnlyPerson addWhitelistedPerson(ReadOnlyPerson person) {
        ReadOnlyPerson whitelistedPerson = person;

        try {
            whitelistedPerson = addressBook.resetPersonDebt(person);
            whitelistedPerson = addressBook.setDateRepaid(whitelistedPerson);
        } catch (PersonNotFoundException e) {
            assert false : "This person cannot be missing from addressbook";
        }

        if (!whitelistedPerson.isBlacklisted()) {
            whitelistedPerson = addressBook.addWhitelistedPerson(whitelistedPerson);
        }
        updateFilteredWhitelistedPersonList(PREDICATE_SHOW_ALL_WHITELISTED_PERSONS);
        indicateAddressBookChanged();
        return whitelistedPerson;
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * Reads the masterlist and updates the blacklist accordingly.
     */
    public void syncBlacklist() {
        filteredBlacklistedPersons = new FilteredList<>(this.addressBook.getBlacklistedPersonList());
    }

    /**
     * Reads the masterlist and updates the whitelist accordingly.
     */
    public void syncWhitelist() {
        filteredWhitelistedPersons = new FilteredList<>(this.addressBook.getWhitelistedPersonList());
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void changeListTo(String listName) {
        raise(new ChangeInternalListEvent(listName));
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * Returns an unmodifiable view of the blacklist of {@code ReadOnlyPerson} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<ReadOnlyPerson> getFilteredBlacklistedPersonList() {
        setCurrentListName("blacklist");
        syncBlacklist();
        filteredBlacklistedPersons.setPredicate(currentPredicate);
        return FXCollections.unmodifiableObservableList(filteredBlacklistedPersons);
    }

    /**
     * Returns an unmodifiable view of the whitelist of {@code ReadOnlyPerson} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<ReadOnlyPerson> getFilteredWhitelistedPersonList() {
        setCurrentListName("whitelist");
        syncWhitelist();
        filteredWhitelistedPersons.setPredicate(currentPredicate);
        return FXCollections.unmodifiableObservableList(filteredWhitelistedPersons);
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * Filters {@code filteredBlacklistedPersons} according to given {@param predicate}
     * @return size of current displayed filtered list.
     */
    @Override
    public int updateFilteredBlacklistedPersonList(Predicate<ReadOnlyPerson> predicate) {
        requireNonNull(predicate);
        currentPredicate = predicate;
        filteredBlacklistedPersons.setPredicate(predicate);
        return filteredBlacklistedPersons.size();
    }

    /**
     * Filters {@code filteredWhitelistedPersons} according to given {@param predicate}
     * @return size of current displayed filtered list.
     */
    @Override
    public int updateFilteredWhitelistedPersonList(Predicate<ReadOnlyPerson> predicate) {
        requireNonNull(predicate);
        currentPredicate = predicate;
        filteredWhitelistedPersons.setPredicate(predicate);
        return filteredWhitelistedPersons.size();
    }
```
###### \java\seedu\address\model\person\DateRepaid.java
``` java
/**
 * Represents the date of when the Person was sent to the whitelist in the address book, i.e. the date
 * the Person repaid his debt completely.
 * Guarantees: immutable;
 */
public class DateRepaid {

    public static final String DATE_REPAID_PLACEHOLDER = "NOT REPAID";

    public final String value;

    public DateRepaid() {
        value = DATE_REPAID_PLACEHOLDER;
    }

    /**
     * Creates a copy of the DateRepaid object with a set date.
     * @param date must be a valid date
     */
    public DateRepaid(String date) {
        value = date;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateRepaid // instanceof handles nulls
                && this.value.equals(((DateRepaid) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### \java\seedu\address\model\person\Person.java
``` java
    /**
     * Returns boolean status of a person's blacklist-status.
     */
    @Override
    public boolean isBlacklisted() {
        return isBlacklisted;
    }

    /**
     * Sets boolean status of a person's blacklist-status using the value of {@param isBlacklisted}.
     */
    @Override
    public void setIsBlacklisted(boolean isBlacklisted) {
        this.isBlacklisted = isBlacklisted;
    }

    /**
     * Returns boolean status of a person's whitelist-status.
     */
    @Override
    public boolean isWhitelisted() {
        return isWhitelisted;
    }

    /**
     * Sets boolean status of a person's whitelist-status using the value of {@param isWhitelisted}.
     */
    @Override
    public void setIsWhitelisted(boolean isWhitelisted) {
        this.isWhitelisted = isWhitelisted;
    }
```
###### \java\seedu\address\model\person\Person.java
``` java
    /**
     * Sets date repaid of a person in the given {@code dateRepaid}.
     * @param dateRepaid must not be null.
     */
    public void setDateRepaid(DateRepaid dateRepaid) {
        this.dateRepaid.set(requireNonNull(dateRepaid));
    }

    @Override
    public ObjectProperty<DateRepaid> dateRepaidProperty() {
        return dateRepaid;
    }

    @Override
    public DateRepaid getDateRepaid() {
        return dateRepaid.get();
    }
```
###### \java\seedu\address\model\person\ReadOnlyPerson.java
``` java
    /**
     * Returns true if person is blacklisted.
     */
    boolean isBlacklisted();

    /**
     * Accepts {@code boolean} as parameter.
     *
     * Sets {@code boolean} variable as the value of {@param isBlacklisted}
     */
    void setIsBlacklisted(boolean isBlacklisted);

    /**
     * Returns true if person is whitelisted.
     */
    boolean isWhitelisted();

    /**
     * {@param} is {@code boolean} value.
     * Sets {@code boolean} variable as the value of {@param isWhitelisted}
     */
    void setIsWhitelisted(boolean isWhitelisted);
```
###### \java\seedu\address\model\person\UniquePersonList.java
``` java
    /**
     * Returns index of {@code ReadOnlyPerson} in list.
     */
    public int getIndexOf(ReadOnlyPerson key) {
        return internalList.indexOf(key);
    }

    /**
     * Returns {@code ReadOnlyPerson} in list from given {@param index}.
     */
    public ReadOnlyPerson getReadOnlyPerson(int index) {
        return mappedList.get(index);
    }
```
###### \java\seedu\address\model\person\UniquePersonList.java
``` java
    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ReadOnlyPerson> asObservableBlacklist() {
        return FXCollections.unmodifiableObservableList(mappedList.stream()
                .filter(person -> person.isBlacklisted()).collect(toCollection(FXCollections::observableArrayList)));
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ReadOnlyPerson> asObservableWhitelist() {
        return FXCollections.unmodifiableObservableList(mappedList.stream()
                .filter(person -> person.isWhitelisted()).collect(toCollection(FXCollections::observableArrayList)));
    }
```
###### \java\seedu\address\model\ReadOnlyAddressBook.java
``` java
    /**
     * Returns an unmodifiable view of the blacklisted persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<ReadOnlyPerson> getBlacklistedPersonList();

    /**
     * Returns an unmodifiable view of the whitelisted persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<ReadOnlyPerson> getWhitelistedPersonList();
```
###### \java\seedu\address\storage\XmlSerializableAddressBook.java
``` java
    /**
     * @return {@code ObservableList} of blacklisted persons.
     */
    @Override
    public ObservableList<ReadOnlyPerson> getBlacklistedPersonList() {
        ObservableList<ReadOnlyPerson> persons = getPersonList();
        ObservableList<ReadOnlyPerson> blacklistedPersons = persons.stream()
                .filter(person -> person.isBlacklisted())
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(blacklistedPersons);
    }

    /**
     * @return {@code ObservableList} of whitelisted persons.
     */
    @Override
    public ObservableList<ReadOnlyPerson> getWhitelistedPersonList() {
        ObservableList<ReadOnlyPerson> persons = getPersonList();
        ObservableList<ReadOnlyPerson> whitelistedPersons = persons.stream()
                .filter(person -> person.isWhitelisted())
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(whitelistedPersons);
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Fills up the placeholders of PersonListPanel with the given list name.
     * Should only display welcome page without contacts.
     */
    void fillInnerPartsWithIndicatedList(String listName) {

        switch(listName) {

        case "blacklist":
            personListPanel = new PersonListPanel(logic.getFilteredBlacklistedPersonList());
            break;
        case "whitelist":
            personListPanel = new PersonListPanel(logic.getFilteredWhitelistedPersonList());
            break;
        case "overduelist":
            personListPanel = new PersonListPanel(logic.getFilteredOverduePersonList());
            break;
        default:
            personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        }

        personListPanelPlaceholder.getChildren().clear();
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());
        infoPanel = new InfoPanel(logic);
        infoPanelPlaceholder.getChildren().clear();
        infoPanelPlaceholder.getChildren().add(infoPanel.getRoot());
    }
```
###### \java\seedu\address\ui\UiManager.java
``` java
    /**
     * Handles change internal list event.
     * Displays the list that user requested(e.g masterlist, blacklist etc)
     */
    @Subscribe
    private void handleChangeInternalListEvent(ChangeInternalListEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.fillInnerPartsWithIndicatedList(event.getListName());
    }
```
