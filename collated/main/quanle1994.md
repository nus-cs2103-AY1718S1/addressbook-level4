# quanle1994
###### /java/seedu/address/commons/util/digestutil/HashDigest.java
``` java

/**
 * Converts a string to a SHA-256 Hash Digest.
 */
public class HashDigest {
    /**
     * Return the hash digest of {@code text}. Used for creating accounts and validating log-ins.
     */
    public byte[] getHashDigest(String text) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(text.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
```
###### /java/seedu/address/logic/commands/LoginCommand.java
``` java

/**
 * Log the user in.
 */
public class LoginCommand extends Command {

    public static final String COMMAND_WORD = "login";
    public static final String COMMAND_ALIAS = "lgi";
    public static final Object MESSAGE_USAGE = COMMAND_WORD + ": User logs in using a pre-registered account. "
            + "Parameters: "
            + PREFIX_USERID + "USER ID "
            + PREFIX_PASSWORD + "PASSWORD";
    public static final String MESSAGE_SUCCESS = "Log In Successful";
    public static final String MESSAGE_ERROR_NO_USER = "User does not exist";
    public static final String MESSAGE_LOGIN_ERROR = "Log out first before logging in";
    private String userId;
    private String passwordText;

    public LoginCommand(String userId, String passwordText) {
        this.userId = userId;
        this.passwordText = passwordText;
    }

    public static String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        if (!(new CurrentUserDetails().getUserId().equals("PUBLIC"))) {
            throw new CommandException(MESSAGE_LOGIN_ERROR);
        }

        byte[] userNameHash = new HashDigest().getHashDigest(userId);
        String userNameHex = new HexCode().getHexFormat(new String(userNameHash));
        String saltText = "";
        try {

            String saltHex = model.retrieveSaltFromStorage(userNameHex);
            saltText = new HexCode().hexStringToByteArray(saltHex);
            byte[] saltedPassword = new HashDigest().getHashDigest(saltText + passwordText);
            String saltedPasswordHex = new HexCode().getHexFormat(new String(saltedPassword));

            model.getUserFromIdAndPassword(userNameHex, saltedPasswordHex);

            model.encryptPublic(false);

            ObservableList<ReadOnlyPerson> list = model.getListLength();
            model.emptyPersonList(list);

            model.decrypt(userNameHex.substring(0, 10), saltText + passwordText);
            model.refreshAddressBook();

            new CurrentUserDetails().setCurrentUser(userId, userNameHex, saltText, passwordText);
        } catch (UserNotFoundException e) {
            throw new CommandException(MESSAGE_ERROR_NO_USER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### /java/seedu/address/logic/commands/OrderCommand.java
``` java

/**
 * Order the list according to a parameter
 */
public class OrderCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "order";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Order the contacts of Address Book based on one/multiple parameter(s).\n"
            + "To order GROUP with other parameters, GROUP must be the first parameter. \n"
            + "Parameters:  NAME, ADDRESS, BIRTHDAY, TAG, GROUP\n"
            + "Example: " + COMMAND_WORD + " BIRTHDAY NAME";

    public static final String MESSAGE_ORDER_SUCCESS = "Address Book has been ordered by ";
    public static final String MESSAGE_ORDER_WRONG_PARAMETER = "The parameter can only contain Name, Address, Birthday,"
            + " Tag";

    private String orderParameter;

    public OrderCommand(String orderParameter) {
        this.orderParameter = orderParameter;
    }

    public static String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.orderList(orderParameter);
        } catch (UnrecognisedParameterException upe) {
            throw new CommandException(MESSAGE_ORDER_WRONG_PARAMETER);
        }
        return new CommandResult(MESSAGE_ORDER_SUCCESS + orderParameter);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OrderCommand // instanceof handles nulls
                && this.orderParameter.equals(((OrderCommand) other).orderParameter)); // state check
    }
}
```
###### /java/seedu/address/logic/commands/LockCommand.java
``` java

/**
 * Create an account and encrypt the addressbook.xml with that account
 */
public class LockCommand extends Command {
    public static final String COMMAND_WORD = "lock";
    public static final String COMMAND_ALIAS = "lo";
    public static final Object MESSAGE_USAGE = COMMAND_WORD + ": Locks the current address book with a user account. "
            + "Parameters: "
            + PREFIX_USERID + "USER ID "
            + PREFIX_PASSWORD + "PASSWORD";
    public static final String MESSAGE_SUCCESS = "Account is created and your Address Book is locked with your "
            + "password";
    public static final String MESSAGE_EXISTING_USER = "User already exists";
    private static final int SALT_MIN = 0;
    private static final int SALT_MAX = 1000000;
    private String userId;
    private String passwordText;

    public LockCommand(String userId, String passwordText) {
        this.userId = userId;
        this.passwordText = passwordText;
    }

    public static String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public CommandResult execute() throws CommandException, DuplicateUserException {
        requireNonNull(model);

        byte[] uIdDigest = new HashDigest().getHashDigest(userId);
        String saltText = "" + ThreadLocalRandom.current().nextInt(SALT_MIN, SALT_MAX + 1);
        byte[] pwDigest = new HashDigest().getHashDigest(saltText + passwordText);
        String hexUidDigest = new HexCode().getHexFormat(new String(uIdDigest));
        String hexSalt = new HexCode().getHexFormat(saltText);
        String hexPassword = new HexCode().getHexFormat(new String(pwDigest));
        try {
            model.persistUserAccount(new User(hexUidDigest, hexSalt, hexPassword));
        } catch (DuplicateUserException due) {
            throw new CommandException(MESSAGE_EXISTING_USER);
        }

        try {
            model.encrypt(hexUidDigest.substring(0, 10), saltText + passwordText, false);
            model.encryptPublic(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        new CurrentUserDetails().setCurrentUser(this.userId, hexUidDigest, saltText, this.passwordText);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    public String getUserId() {
        return userId;
    }

    public String getPasswordText() {
        return passwordText;
    }
}
```
###### /java/seedu/address/logic/commands/RemoveUserCommand.java
``` java

import seedu.address.commons.util.digestutil.HashDigest;
import seedu.address.commons.util.digestutil.HexCode;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.user.exceptions.DuplicateUserException;
import seedu.address.model.user.exceptions.UserNotFoundException;

/**
 * Remove user from Account file
 */
public class RemoveUserCommand extends Command {
    public static final String COMMAND_WORD = "remove";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Remove the user from the Account storage.\n"
            + "Parameters: u/USERNAME, p/PASSWORD, r/REMOVE_CONTACT\n"
            + "USERNAME is a string and is the user id of the account you want to remove\n"
            + "PASSWORD is a string associated with that user. It must match with the password stored locally\n"
            + "REMOVE_CONTACT is either a Y or N, Y means the contacts associated to that user will be deleted, and "
            + "N means the contacts associated to that user will be released to accessible by the public."
            + "Example: " + COMMAND_WORD + " u/lequangquan p/123123 r/Y";
    public static final String MESSAGE_REMOVE_USER_SUCCESS = "Removed user: %1$s";
    public static final String MESSAGE_USER_NOT_FOUND = "The user credentials provided do not match our "
            + "database.";
    public static final String MESSAGE_ENCRYPTION_ERROR = "Decryption Failed";
    private String userName;
    private String password;
    private boolean cascade;

    public RemoveUserCommand(String userName, String password, boolean cascade) {
        this.userName = userName;
        this.password = password;
        this.cascade = cascade;
    }

    public static String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public CommandResult execute() throws CommandException, DuplicateUserException {
        try {
            byte[] userNameHash = new HashDigest().getHashDigest(userName);
            String userNameHex = new HexCode().getHexFormat(new String(userNameHash));

            String saltHex = model.retrieveSaltFromStorage(userNameHex);
            String saltText = new HexCode().hexStringToByteArray(saltHex);
            byte[] saltedPassword = new HashDigest().getHashDigest(saltText + password);
            String saltedPasswordHex = new HexCode().getHexFormat(new String(saltedPassword));

            model.deleteUser(userNameHex, saltedPasswordHex);
            if (cascade) {
                model.deleteEncryptedContacts(userNameHex.substring(0, 10));
            } else {
                model.decrypt(userNameHex.substring(0, 10), saltText + password);
                model.releaseEncryptedContacts(userNameHex.substring(0, 10));
            }
            model.saveToEncryptedFile();
        } catch (UserNotFoundException unfe) {
            throw new CommandException(MESSAGE_USER_NOT_FOUND);
        } catch (Exception e) {
            throw new CommandException(MESSAGE_ENCRYPTION_ERROR);
        }
        return new CommandResult(String.format(MESSAGE_REMOVE_USER_SUCCESS, userName));
    }
}
```
###### /java/seedu/address/storage/XmlAdaptedUser.java
``` java

/**
 * JAXB-friendly version of the Event.
 */
public class XmlAdaptedUser {
    @XmlElement(required = true)
    private String userId;
    @XmlElement(required = true)
    private String salt;
    @XmlElement(required = true)
    private String password;

    /**
     * Constructs an XmlAdaptedUser.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedUser() {
    }

    /**
     * Converts a given User into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedUser(ReadOnlyUser source) {
        userId = source.getUserId();
        salt = source.getSalt();
        password = source.getPassword();
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     */
    public User toModelType() {
        return new User(this.userId, this.salt, this.password);
    }
}
```
###### /java/seedu/address/storage/AccountStorage.java
``` java

/**
 * Represents a storage for {@link seedu.address.model.Account}.
 */
public interface AccountStorage {

    /**
     * Returns the file path of the data file.
     */
    String getAccountFilePath();

    /**
     * Returns AddressBook data as a {@link seedu.address.model.ReadOnlyAccount}.
     * Returns {@code Optional.empty()} if storage file is not found.
     */
    Optional<ReadOnlyAccount> readAccount() throws FileNotFoundException, DataConversionException;

    /**
     * @see #getAccountFilePath()
     */
    Optional<ReadOnlyAccount> readAccount(String filePath) throws FileNotFoundException, DataConversionException;

    /**
     * Saves the given {@link ReadOnlyAccount} to the storage.
     *
     * @param addressBook cannot be null.
     */
    void saveAccount(ReadOnlyAccount addressBook) throws IOException;

    /**
     * @see #saveAccount(ReadOnlyAccount)
     */
    void saveAccount(ReadOnlyAccount addressBook, String filePath) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyAddressBook)
     */
}
```
###### /java/seedu/address/storage/XmlSerializableAccount.java
``` java

/**
 * An Immutable Account that is serializable to XML format
 */
@XmlRootElement(name = "account")
public class XmlSerializableAccount implements ReadOnlyAccount {

    @XmlElement
    private List<XmlAdaptedUser> users;

    public XmlSerializableAccount() {
        users = new ArrayList<>();
    }

    public XmlSerializableAccount(ReadOnlyAccount src) {
        this();
        users.addAll(src.getUserList().stream().map(XmlAdaptedUser::new).collect(Collectors.toList()));
    }

    public List<XmlAdaptedUser> getUsers() {
        return users;
    }

    @Override
    public ObservableList<ReadOnlyUser> getUserList() {
        final ObservableList<ReadOnlyUser> users = this.users.stream().map(u ->
                u.toModelType()).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(users);
    }
}
```
###### /java/seedu/address/storage/XmlAccountStorage.java
``` java

/**
 * A class to access TunedIn Account data stored as an xml file on the hard disk.
 */
public class XmlAccountStorage implements AccountStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlAddressBookStorage.class);

    private String filePath;

    public XmlAccountStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getAccountFilePath() {
        return filePath;
    }

    /**
     * Similar to {@link #readAccount()}
     *
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyAccount> readAccount(String filePath)
            throws FileNotFoundException, DataConversionException {
        requireNonNull(filePath);

        File accountFile = new File(filePath);
        if (!accountFile.exists()) {
            logger.info("Account file " + accountFile + " not found");
            return Optional.empty();
        }

        ReadOnlyAccount accountOptional = XmlFileStorage.loadAccountFromSaveFile(new File(filePath));

        return Optional.of(accountOptional);
    }

    @Override
    public Optional<ReadOnlyAccount> readAccount() throws FileNotFoundException, DataConversionException {
        return readAccount(filePath);
    }

    @Override
    public void saveAccount(ReadOnlyAccount account) throws IOException {
        saveAccount(account, filePath);
    }

    /**
     * Similar to {@link #saveAccount(ReadOnlyAccount)}
     *
     * @param filePath location of the data. Cannot be null
     */
    public void saveAccount(ReadOnlyAccount account, String filePath) throws IOException {
        requireNonNull(account);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveAccountToFile(file, new XmlSerializableAccount(account));
    }
}
```
###### /java/seedu/address/model/user/UniqueUserList.java
``` java

import static java.util.Objects.requireNonNull;

import java.util.List;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.user.exceptions.DuplicateUserException;
import seedu.address.model.user.exceptions.UserNotFoundException;

```
###### /java/seedu/address/model/user/UniqueUserList.java
``` java

/**
 * A list of users that enforces uniqueness between its elements and does not allow nulls.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see User#equals(Object)
 */
public class UniqueUserList {
    private final ObservableList<User> internalList = FXCollections.observableArrayList();
    // used by asObservableList()
    private final ObservableList<ReadOnlyUser> mappedList = EasyBind.map(internalList, (user) -> user);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(ReadOnlyUser toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a person to the list.
     *
     * @throws DuplicateUserException if the person to add is a duplicate of an existing person in the list.
     */
    public void add(ReadOnlyUser toAdd) throws DuplicateUserException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateUserException();
        }
        internalList.add(new User(toAdd));
    }

    /**
     * Removes the equivalent event from the list.
     *
     * @throws UserNotFoundException if no such event could be found in the list.
     */
    public boolean remove(ReadOnlyUser toRemove) throws UserNotFoundException {
        requireNonNull(toRemove);
        final boolean userFoundAndDeleted = internalList.remove(toRemove);
        if (!userFoundAndDeleted) {
            throw new UserNotFoundException();
        }
        return userFoundAndDeleted;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ReadOnlyUser> asObservableList() {
        return FXCollections.unmodifiableObservableList(mappedList);
    }

    public void setUsers(List<? extends ReadOnlyUser> users) throws DuplicateUserException {
        final UniqueUserList replacement = new UniqueUserList();
        for (final ReadOnlyUser user : users) {
            replacement.add(new User(user));
        }
        setUsers(replacement);
    }

    public void setUsers(UniqueUserList replacement) throws DuplicateUserException {
        this.internalList.setAll(replacement.internalList);
    }


    public User getUser(String userName) throws UserNotFoundException {
        int targetIndex = this.internalList.indexOf(new User(userName));
        if (targetIndex < 0) {
            throw new UserNotFoundException();
        }
        return this.internalList.get(targetIndex);
    }

    public String getSalt(String userId) throws UserNotFoundException {
        return getUser(userId).getSalt();
    }
}
```
###### /java/seedu/address/model/user/User.java
``` java

/**
 * Represents a User in the account.
 * Guarantees: details are present and not null, field values are validated.
 */
public class User implements ReadOnlyUser {
    private String userId;
    private String salt = "";
    private String password = "";

    public User() {
    }

    public User(String userId, String salt, String password) {
        this.userId = userId;
        this.salt = salt;
        this.password = password;
    }

    public User(String userId) {
        this.userId = userId;
    }

    /**
     * Creates a copy of the given ReadOnlyUser.
     */
    public User(ReadOnlyUser source) {
        this(source.getUserId(), source.getSalt(), source.getPassword());
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String getSalt() {
        return salt;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyUser // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyUser) other));
    }

    /**
     * Check if the users have the same userName and password
     */
    public boolean sameAs(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyUser // instanceof handles nulls
                && this.isSameUserAs((ReadOnlyUser) other));
    }
}
```
###### /java/seedu/address/model/user/exceptions/UserNotFoundException.java
``` java

/**
 * Signals that the operation is unable to find the specified user.
 */
public class UserNotFoundException extends Exception {
}
```
###### /java/seedu/address/model/user/exceptions/DuplicateUserException.java
``` java

/**
 * Signals that the operation will result in duplicate User objects.
 */
public class DuplicateUserException extends DuplicateDataException {
    public DuplicateUserException() {
        super("User exists");
    }
}
```
###### /java/seedu/address/model/user/ReadOnlyUser.java
``` java

/**
 * A read-only immutable interface for a user in the Account.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyUser {
    String getUserId();

    void setUserId(String userId);

    String getSalt();

    String getPassword();

    void setPassword(String password);

    /**
     * Returns true if both have the same username. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyUser other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getUserId().equals(this.getUserId())); // state checks here onwards
    }

    /**
     * Returns true if both have the same username and password
     */
    default boolean isSameUserAs(ReadOnlyUser other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getUserId().equals(this.getUserId())
                && other.getPassword().equals(this.getPassword())); // state checks here onwards
    }
}
```
###### /java/seedu/address/model/Account.java
``` java

/**
 * Wrap all data at account level
 */
public class Account implements ReadOnlyAccount {

    private final UniqueUserList users;

    {
        users = new UniqueUserList();
    }

    public Account() {
    }

    /**
     * Creates an Account list using the Events in the {@code toBeCopied}
     */
    public Account(ReadOnlyAccount toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Resets the existing data of this {@code Account} with {@code newData}.
     */
    private void resetData(ReadOnlyAccount newData) {
        requireNonNull(newData);
        try {
            setUsers(newData.getUserList());
        } catch (DuplicateUserException e) {
            assert false : "Account should not have duplicate users";
        }
    }

    @Override
    public ObservableList<ReadOnlyUser> getUserList() {
        return users.asObservableList();
    }

    //=================== user-level operations ========================

    /**
     * Adds a person to the address book.
     *
     * @throws DuplicateUserException if an equivalent person already exists.
     */
    public void addUser(ReadOnlyUser p) throws DuplicateUserException {
        User newPerson = new User(p);
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        users.add(newPerson);
    }

    /**
     * Removes {@code u} from this {@code Account}.
     *
     * @throws UserNotFoundException if the {@code u} is not in this {@code Account}.
     */
    public boolean removeUser(ReadOnlyUser u) throws UserNotFoundException {
        if (users.remove(u)) {
            return true;
        } else {
            throw new UserNotFoundException();
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Account // instanceof handles nulls
                && this.users.equals(((Account) other).users));
    }

    public void setUsers(ObservableList<ReadOnlyUser> users) throws DuplicateUserException {
        this.users.setUsers(users);
    }

    public User getUserFromIdAndPassword(String userName, String password) throws UserNotFoundException {
        User target = users.getUser(userName);
        if (!target.getPassword().equals(password)) {
            throw new UserNotFoundException();
        }
        return target;
    }

    public String getSalt(String userId) throws UserNotFoundException {
        return users.getSalt(userId);
    }
}
```
###### /java/seedu/address/model/ModelManager.java
``` java

    @Override
    public ReadOnlyAccount getAccount() {
        return this.account;
    }

    @Override
    public void deleteEncryptedContacts(String fileName) {
        File file = new File("data/" + fileName + ".encrypted");
        file.delete();
    }

    @Override
    public void releaseEncryptedContacts(String fileName) throws DataConversionException, IOException {
        File file = new File("data/" + fileName + ".encrypted");
        file.delete();
        refreshAddressBook();
    }

    @Override
    public UserPrefs getUserPrefs() {
        return userPref;
    }

    @Override
    public void refreshAddressBook() throws IOException, DataConversionException {
        AddressBook temp = new AddressBook(userStorage.readAddressBook().orElseGet
                (SampleDataUtil::getSampleAddressBook));
        for (ReadOnlyPerson p : temp.getPersonList()) {
            Person newP = new Person(p);
            try {
                addressBook.addPerson(newP);
            } catch (DuplicatePersonException dpe) {
                dpe.getStackTrace();
            }
        }
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public void emptyPersonList(ObservableList<ReadOnlyPerson> list) throws PersonNotFoundException {
        for (ReadOnlyPerson p : list) {
            Person newP = new Person(p);
            addressBook.removePerson(newP);
        }
        indicateAddressBookChanged();
    }

    @Override
    public ObservableList<ReadOnlyPerson> getListLength() throws IOException, DataConversionException {
        AddressBook temp = new AddressBook(userStorage.readAddressBook().orElseGet
                (SampleDataUtil::getSampleAddressBook));
        return temp.getPersonList();
    }

    @Override
    public void encrypt(String userId, String pass, boolean emptyFile) throws Exception {
        FileEncryptor.encryptFile(userId, pass, emptyFile);
    }

    @Override
    public void decrypt(String fileName, String pass) throws Exception {
        FileEncryptor.decryptFile(fileName, pass);
    }

    @Override
    public void encryptPublic(boolean isLockCommand) throws CommandException {
        FileEncryptor.encryptPublicFile(isLockCommand);
    }

    @Override
    public void saveToEncryptedFile() {
        SaveToEncryptedFile.save();
    }
}
```
###### /java/seedu/address/model/ReadOnlyAccount.java
``` java

/**
 * Unmodifiable view of the accounts
 */

public interface ReadOnlyAccount {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<ReadOnlyUser> getUserList();
}
```
###### /java/seedu/address/model/Model.java
``` java

    void deleteEncryptedContacts(String substring);

    void releaseEncryptedContacts(String fileName) throws DataConversionException, IOException;

    UserPrefs getUserPrefs();

    void refreshAddressBook() throws IOException, DataConversionException;

    void emptyPersonList(ObservableList<ReadOnlyPerson> list) throws PersonNotFoundException, IOException,
            DataConversionException;

    ObservableList<ReadOnlyPerson> getListLength() throws IOException, DataConversionException;

    void encrypt(String userId, String pass, boolean emptyFile) throws Exception;

    void decrypt(String fileName, String pass) throws Exception;

    void encryptPublic(boolean isLockCommand) throws CommandException;

    void saveToEncryptedFile();
}
```
