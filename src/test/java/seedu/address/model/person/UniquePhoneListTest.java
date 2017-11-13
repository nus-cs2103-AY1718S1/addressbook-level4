package seedu.address.model.person;

import static org.junit.Assert.assertTrue;

import java.util.logging.Logger;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.exceptions.PhoneNotFoundException;
import seedu.address.model.person.phone.Phone;
import seedu.address.model.person.phone.UniquePhoneList;

//@@author eeching
public class UniquePhoneListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final Logger logger = LogsCenter.getLogger(UniquePhoneListTest.class);

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniquePersonList uniquePersonList = new UniquePersonList();
        thrown.expect(UnsupportedOperationException.class);
        uniquePersonList.asObservableList().remove(0);
    }

    @Test
    public void isUniqueAfterAdd() {
        UniquePhoneList list = new UniquePhoneList();
        boolean isUnique = false;
        try {
            list.add(new Phone("23333"));
            list.add(new Phone("23333")); //this is an illegal value due to duplication
        } catch (IllegalValueException e) {
            isUnique = true;
        }

        assertTrue("No duplicate Phone number", isUnique);
    }

    @Test
    public void detectUnfoundPhoneForRemove() {
        UniquePhoneList list = new UniquePhoneList();
        boolean detected = false;
        try {
            list.add(new Phone("23333"));
            list.add(new Phone("23334"));
            list.remove(new Phone("2333")); //this is an exception since it does not exist in the list
        } catch (IllegalValueException e) {
            throw new AssertionError("Illegal phone number");
        } catch (PhoneNotFoundException e) {
            detected = true;
        }

        assertTrue("Detect unfound phone number", detected);
    }

    @Test
    public void getCorrectSizeAfterAddition() {
        UniquePhoneList list = new UniquePhoneList();
        boolean correctSize = false;
        try {
            list.add(new Phone("23333"));
            list.add(new Phone("23334"));
            list.add(new Phone("23335"));
            list.add(new Phone("23333")); //duplicate should not be added, hence the size is still 3
        } catch (IllegalValueException e) {
            logger.warning("duplicate number added");
        }
        if (list.getSize() == 3) {
            correctSize = true;
        }
        assertTrue("The size of the phoneList after adding numbers is correct", correctSize);
    }

    @Test
    public void getCorrectSizeAfterRemoval() {
        UniquePhoneList list = new UniquePhoneList();
        boolean correctSize = false;
        try {
            list.add(new Phone("23333"));
            list.add(new Phone("23334"));
            list.remove(new Phone("23333")); //now the size reduce to 1
            list.remove(new Phone("2333")); // size is still 1 since this phone is not in the list
        } catch (IllegalValueException e) {
            logger.warning("duplicate number added");
        } catch (PhoneNotFoundException e) {
            logger.warning("phone not found");
        }
        if (list.getSize() == 1) {
            correctSize = true;
        }
        assertTrue("The size of the phoneList after removing numbers is correct", correctSize);
    }

    @Test
    public void isEqual() { //to test the sequence of addition/ removal does not matter
        UniquePhoneList list1 = new UniquePhoneList();
        UniquePhoneList list2 = new UniquePhoneList();
        boolean isEqual;
        try {
            list1.add(new Phone("23333"));
            list1.add(new Phone("23334"));
            list1.add(new Phone("23335"));
            list1.remove(new Phone("23333"));
            list2.add(new Phone("23334"));
            list2.add(new Phone("23335"));
            list2.add(new Phone("23333"));
            list2.remove(new Phone("23333"));
        } catch (IllegalValueException e) {
            logger.warning("duplicate number added");
        } catch (PhoneNotFoundException e) {
            logger.warning("phone number not found");
        }
        isEqual = list1.equals(list2);
        assertTrue("The two lists are equal", isEqual);
    }

}
//@@author
