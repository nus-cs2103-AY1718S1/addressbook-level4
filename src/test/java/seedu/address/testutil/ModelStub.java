package seedu.address.testutil;

import static org.junit.Assert.fail;
import static seedu.address.logic.parser.ParserUtil.EMPTY_STRING;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

//@@author liliwei25
/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {

    public static final String MESSAGE_FAIL = "This method should not be called.";

    @Override
    public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
        fail(MESSAGE_FAIL);
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        fail(MESSAGE_FAIL);
    }

    @Override
    public void updateListToShowAll() {
        fail(MESSAGE_FAIL);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        fail(MESSAGE_FAIL);
        return null;
    }

    @Override
    public void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
        fail(MESSAGE_FAIL);
    }

    @Override
    public String sortPerson(String target) {
        fail(MESSAGE_FAIL);
        return EMPTY_STRING;
    }

    @Override
    public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException , PersonNotFoundException {
        fail(MESSAGE_FAIL);
    }

    @Override
    public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
        fail(MESSAGE_FAIL);
        return null;
    }

    @Override
    public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
        fail(MESSAGE_FAIL);
    }

    @Override
    public void removeTag(Tag tag) {
        fail(MESSAGE_FAIL);
    }

    @Override
    public void mapPerson(ReadOnlyPerson target) throws PersonNotFoundException {
        fail(MESSAGE_FAIL);
    }

    @Override
    public void changeImage(ReadOnlyPerson target) throws PersonNotFoundException {
        fail(MESSAGE_FAIL);
    }

    @Override
    public void removeImage(ReadOnlyPerson target) throws PersonNotFoundException {
        fail(MESSAGE_FAIL);
    }

    @Override
    public void clearInfoPanel() {
        fail(MESSAGE_FAIL);
    }
}
