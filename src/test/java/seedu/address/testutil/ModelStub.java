package seedu.address.testutil;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.model.task.exceptions.TaskNotFoundException;
import seedu.address.ui.MainWindow;

/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {
    @Override
    public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
        fail("This method should not be called.");
    }
    //@@author wangyiming1019
    @Override
    public void addTag(Tag toAdd, ArrayList<Index> targetIndexes)  {
        fail("This method should not be called.");
    }
    //@@author
    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        fail("This method should not be called.");
    }

    @Override
    public void resetPartialData(ReadOnlyAddressBook newData, Prefix prefix) {
        fail("This method should not be called.");
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        fail("This method should not be called.");
        return null;
    }

    //@@author charlesgoh
    public void increaseFontSize() {
        fail("This method should not be called.");
    }

    public void decreaseFontSize() {
        fail("This method should not be called.");
    }

    public void resetFontSize() {
        fail("This method should not be called.");
    }

    public void setMainWindow(MainWindow mainWindow) {
        fail("This method should not be called.");
    }
    //@@author

    @Override
    public void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
        fail("This method should not be called.");
    }
    //@@author wangyiming1019
    @Override
    public void deleteTag(Tag toDelete, ArrayList<Index> targetIndexes) {
        fail("This method should not be called.");
    }
    //@@author
    @Override
    public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException {
        fail("This method should not be called.");
    }

    @Override
    public void editTag(Tag toChange, Tag newTag, ArrayList<Index> affectedIndexes) throws PersonNotFoundException,
            DuplicatePersonException {
        fail("This method should not be called.");
    }

    @Override
    public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
        fail("This method should not be called.");
        return null;
    }

    @Override
    public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
        fail("This method should not be called.");
    }

    @Override
    public void updateFilteredTaskList(Predicate<ReadOnlyTask> predicate) {
        fail("This method should not be called.");
    }

    @Override
    public void sortPersons(String field, String order) {
        fail("This method should not be called.");
    }

    @Override
    public void sortTasks(String field, String order) {
        fail("This method should not be called.");
    }
    //@@author wangyiming1019
    @Override
    public void favouritePerson(ReadOnlyPerson target) throws PersonNotFoundException {
        fail("This method should not be called.");
    }
    //@@author
    @Override
    public void addTask(ReadOnlyTask toAdd) throws DuplicateTaskException {
        fail("This method should not be called.");
    }
    //@@author wangyiming1019
    @Override
    public void unfavouritePerson(ReadOnlyPerson target) throws PersonNotFoundException {
        fail("This method should not be called.");
    }
    //@@author
    @Override
    public void deleteTask(ReadOnlyTask toDelete) throws TaskNotFoundException {
        fail("This method should not be called.");
    }

    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        fail("This method should not be called.");
        return null;
    }

    @Override
    public void updateTask(ReadOnlyTask target, ReadOnlyTask editedTask) throws DuplicateTaskException {
        fail("This method should not be called.");
    }

    @Override
    public void assignToTask(ArrayList<ReadOnlyPerson> personsToAssign, ReadOnlyTask taskToAssign)
            throws TaskNotFoundException, DuplicateTaskException {
        fail("This method should not be called.");
    }

    @Override
    public void dismissFromTask(ArrayList<ReadOnlyPerson> personsToDismiss, ReadOnlyTask taskToDismiss)
            throws TaskNotFoundException, DuplicateTaskException {
        fail("This method should not be called.");
    }

    @Override
    public void setAsComplete(ReadOnlyTask toSet, boolean isComplete)
            throws TaskNotFoundException, DuplicateTaskException {
        fail("This method should not be called.");
    }

    //@@author jeffreygohkw
    @Override
    public void setPrivacyLevel(int level) {
        fail("This method should not be called.");
    }

    @Override
    public int getPrivacyLevel() {
        fail("This method should not be called.");
        return 0;
    }

    @Override
    public ReadOnlyPerson getPersonAtIndexFromAddressBook(int index) {
        fail("This method should not be called.");
        return null;
    }

    @Override
    public void setTheme(String theme) {
        fail("This method should not be called.");
    }

    @Override
    public String getTheme() {
        fail("This method should not be called.");
        return null;
    }

    @Override
    public HashMap<String, String> getStyleMap() {
        fail("This method should not be called.");
        return null;
    }
    //@@author

    @Override
    public boolean getLockState() {
        fail("This method should not be called.");
        return false;
    }

    @Override
    public void lockAddressBookFromModel() {
        fail("This method should not be called.");
    }

    @Override
    public void unlockAddressBookFromModel() {
        fail("This method should not be called.");
    }

    @Override
    public UserPrefs getUserPrefs() {
        fail("This method should not be called.");
        return null;
    }

    @Override
    public void viewAssignees(ReadOnlyTask task) {
        fail("This method should not be called");
    }
}
