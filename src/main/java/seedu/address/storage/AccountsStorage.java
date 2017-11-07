package seedu.address.storage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.UserPrefs;

/**
 * Represents a storage for account information.
 */

public class AccountsStorage implements UserPrefsStorage {
    public static final String DEFAULT_ACCOUNTS_PATH = "accounts.json";

    private String accountsPath;
    private String usernames;
    private String passwords;
    private HashMap<String, String> hm;

    public AccountsStorage() {
        this.accountsPath = DEFAULT_ACCOUNTS_PATH;
        this.hm = new HashMap<String, String> ();
        hm.put("admin", "admin");
        hm.put("user", "user");
    }

    public AccountsStorage(String accountsPath) {
        this.accountsPath = accountsPath;
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return readUserPrefs(accountsPath);
    }

    /**
     * Returns UserPrefs data from storage.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    public Optional<UserPrefs> readUserPrefs(String prefsFilePath) throws DataConversionException, IOException {
        return JsonUtil.readJsonFile(prefsFilePath, UserPrefs.class);
    }

    public void saveAccountsPrefs(AccountsStorage accStorage, String filePath) throws IOException {
        JsonUtil.saveJsonFile(accStorage, filePath);
    }

    public AccountsStorage setAccountMsg() throws DataConversionException, IOException {
        String accountMsg = this.readAccountsPrefs(this.accountsPath).toString();
        this.hm = makeMap(accountMsg);
        return this;
    }

    /**
     * Returns the file path of the UserPrefs data file.
     */
    public String getUserPrefsFilePath() {
        return accountsPath;
    }

    public Optional<AccountsStorage> readAccountsPrefs(String prefsFilePath)
            throws DataConversionException, IOException {
        return JsonUtil.readJsonFile(prefsFilePath, AccountsStorage.class);
    }

    /**
     * Saves the given {@link seedu.address.model.UserPrefs} to the storage.
     * @param userPrefs cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        JsonUtil.saveJsonFile(userPrefs, accountsPath);
    }

    /**
     * Returns the map of username and password.
     */
    public HashMap<String, String> makeMap(String rawValue) {
        HashMap<String, String> hm = new HashMap<String, String>();
        rawValue = rawValue.substring(9, rawValue.length() - 1);
        String[] value = rawValue.split("/");
        String[] username = value[0].split(",");
        String[] password = value[1].split(",");
        for (int i = 0; i < username.length; i++) {
            hm.put(username[i].trim(), password[i].trim());
        }
        return hm;
    }

    public HashMap<String, String> getHm() {
        return hm;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(usernames);
        sb.append("/");
        sb.append(passwords);
        return sb.toString();
    }
}
