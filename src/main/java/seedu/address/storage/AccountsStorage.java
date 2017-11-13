package seedu.address.storage;
//@@author yangminxingnus
import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.JsonUtil;

/**
 * Represents a storage for account information.
 */

public class AccountsStorage {
    public static final String DEFAULT_ACCOUNTS_PATH = "accounts.json";

    private String accountsPath;
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

    public String getAccPrefsFilePath() {
        return accountsPath;
    }

    public void saveAccountsPrefs(AccountsStorage accStorage, String filePath) throws IOException {
        JsonUtil.saveJsonFile(accStorage, filePath);
    }

    public Optional<AccountsStorage> readAccountsPrefs(String prefsFilePath)
            throws DataConversionException, IOException {
        return JsonUtil.readJsonFile(prefsFilePath, AccountsStorage.class);
    }

    public HashMap<String, String> getHm() {
        return hm;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getHm().keySet());
        sb.append("/");
        sb.append(getHm().values());
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        return ((AccountsStorage) o).getHm().equals(this.getHm());
    }
}
//@@author
