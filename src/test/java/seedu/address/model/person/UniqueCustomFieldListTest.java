package seedu.address.model.person;

import static org.junit.Assert.assertTrue;

import java.util.logging.Logger;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.customField.CustomField;
import seedu.address.model.customField.UniqueCustomFieldList;

//@@author LuLechuan
public class UniqueCustomFieldListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final Logger logger = LogsCenter.getLogger(UniqueCustomFieldListTest.class);

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueCustomFieldList uniqueCustomFieldList = new UniqueCustomFieldList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueCustomFieldList.asObservableList().remove(0);
    }

    @Test
    public void getCorrectSizeAfterAddition() {
        UniqueCustomFieldList list = new UniqueCustomFieldList();
        boolean correctSize = false;
        try {
            list.add(new CustomField("NickName", "Ah"));
            list.add(new CustomField("Age", "21"));
            list.add(new CustomField("Birthday", "29/02/1996"));
            list.add(new CustomField("Age", "22"));
        } catch (IllegalValueException e) {
            logger.warning("Input value is invalid");
        }
        if (list.getSize() == 3) {
            correctSize = true;
        }
        assertTrue("The size of the phoneList after adding numbers is correct", correctSize);
    }

    @Test
    public void getCorrectSizeAfterRemoval() {
        UniqueCustomFieldList list = new UniqueCustomFieldList();
        boolean correctSize = false;
        try {
            list.add(new CustomField("NichName", "Ah"));
            list.add(new CustomField("Age", "21"));
            list.add(new CustomField("Age", ""));  // Removes custom field "Age"
        } catch (IllegalValueException e) {
            logger.warning("Input value are invalid");
        }
        if (list.getSize() == 1) {
            correctSize = true;
        }
        assertTrue("The size of the custom field list after removing values is correct", correctSize);
    }

    @Test
    public void isEqual() { //to test content in the list are equal even if the elements are added in different order
        UniqueCustomFieldList list1 = new UniqueCustomFieldList();
        UniqueCustomFieldList list2 = new UniqueCustomFieldList();
        boolean isEqual;
        try {
            list1.add(new CustomField("NickName", "Ah"));
            list1.add(new CustomField("Age", "21"));
            list1.add(new CustomField("Birthday", "29/02/1996"));
            list1.add(new CustomField("Age", ""));  // Removes custom field "Age"
            list2.add(new CustomField("Birthday", "29/02/1996"));
            list2.add(new CustomField("Age", "21"));
            list2.add(new CustomField("NickName", "Ah"));
            list2.add(new CustomField("Age", ""));  // Removes custom field "Age"
        } catch (IllegalValueException e) {
            logger.warning("Input value is invalid");
        }
        isEqual = list1.equalsOrderInsensitive(list2);
        assertTrue("The two lists are equal", isEqual);
    }

}
