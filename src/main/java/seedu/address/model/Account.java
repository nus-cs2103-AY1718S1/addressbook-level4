package seedu.address.model;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.model.user.ReadOnlyUser;
import seedu.address.model.user.UniqueUserList;
import seedu.address.model.user.User;
import seedu.address.model.user.exceptions.DuplicateUserException;
import seedu.address.model.user.exceptions.UserNotFoundException;

//@@author quanle1994

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
