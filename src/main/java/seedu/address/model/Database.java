//@@author cqhchan
package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.model.account.Account;
import seedu.address.model.account.ReadOnlyAccount;
import seedu.address.model.account.UniqueAccountList;
import seedu.address.model.account.exceptions.DuplicateAccountException;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 *
 */
public class Database implements ReadOnlyDatabase {

    private final UniqueAccountList accounts;

    /*
    * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
    * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
    *
    * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
    *   among constructors.
    */ {
        accounts = new UniqueAccountList();
    }

    public Database() {
    }

    public Database(ReadOnlyDatabase toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    public void setAccounts(List<? extends ReadOnlyAccount> accounts) throws DuplicateAccountException {
        this.accounts.setAccounts(accounts);
    }

    /**
     *
     * @param newData
     */
    public void resetData(ReadOnlyDatabase newData) {
        requireNonNull(newData);
        try {
            setAccounts(newData.getAccountList());

        } catch (DuplicateAccountException dpe) {
            assert false : "Database should not have duplicate persons";
        }

    }

    /**
     *
     * @param p
     * @throws DuplicateAccountException
     */
    public void addAccount(ReadOnlyAccount p) throws DuplicateAccountException {
        Account newAccount = new Account(p);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        accounts.add(newAccount);
    }

    /**
     *
     * @param target
     * @param editedReadOnlyAccount
     * @throws DuplicatePersonException
     * @throws PersonNotFoundException
     */
    public void updateAccount(ReadOnlyAccount target, ReadOnlyAccount editedReadOnlyAccount)
            throws DuplicatePersonException, PersonNotFoundException {
        requireNonNull(editedReadOnlyAccount);

        Account editedAccount = new Account(editedReadOnlyAccount);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        accounts.setAccount(target, editedAccount);
    }

    /**
     *
     * @param key
     * @return
     * @throws PersonNotFoundException
     */
    public boolean removeAccount(ReadOnlyAccount key) throws PersonNotFoundException {
        if (accounts.remove(key)) {
            return true;
        } else {
            throw new PersonNotFoundException();
        }
    }

    @Override
    public ObservableList<ReadOnlyAccount> getAccountList() {
        return accounts.asObservableList();
    }

    @Override
    public String toString() {
        return accounts.asObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Database // instanceof handles nulls
                && this.accounts.equals(((Database) other).accounts));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(accounts);

    }
}
