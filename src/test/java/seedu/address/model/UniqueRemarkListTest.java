package seedu.address.model;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CODE_MA1101R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.module.Code;
import seedu.address.model.module.Remark;
import seedu.address.model.module.UniqueRemarkList;
import seedu.address.model.module.exceptions.RemarkNotFoundException;

//@@author junming403
public class UniqueRemarkListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueRemarkList uniqueLessonList = new UniqueRemarkList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueLessonList.asObservableList().remove(0);
    }

    @Test
    public void equals() throws IllegalValueException, RemarkNotFoundException {
        UniqueRemarkList firstList = new UniqueRemarkList();
        Remark firstRemark = new Remark("Sample remark", new Code(VALID_CODE_MA1101R));
        Remark secondRemark = new Remark("another remark", new Code(VALID_CODE_MA1101R));
        Remark updatedRemark = new Remark("updated", new Code(VALID_CODE_MA1101R));

        firstList.add(firstRemark);
        firstList.add(secondRemark);
        UniqueRemarkList secondList = new UniqueRemarkList(firstList.toSet());

        assertTrue(secondList.equals(firstList));
        firstList.setRemark(firstRemark, updatedRemark);
        secondList.setRemark(firstRemark, updatedRemark);
        assertTrue(secondList.equals(firstList));
        assertTrue(secondList.equalsOrderInsensitive(firstList));
    }
}
