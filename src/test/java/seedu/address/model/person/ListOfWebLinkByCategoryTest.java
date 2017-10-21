package seedu.address.model.person;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEB_LINK_DEFAULT;
import static seedu.address.model.person.weblink.WebLinkUtil.FACEBOOK_TAG;
import static seedu.address.testutil.TypicalPersons.AMY;

import java.util.ArrayList;

import org.junit.Test;

/**
 * junit test for listOfWebLinkByCategoryTest. Will increase coverage in the future.
 */
public class ListOfWebLinkByCategoryTest {

    @Test
    public void isValidOutputOfWebLinks() {
        ReadOnlyPerson toAdd = AMY;
        ArrayList<String> expectedOutput = new ArrayList<String>();
        expectedOutput.add(VALID_WEB_LINK_DEFAULT);
        assertTrue(isTwoArrayListsWithSameValues(expectedOutput, AMY.listOfWebLinkByCategory(FACEBOOK_TAG)));
    }

    /**
     * compares 2 ArrayLists, irregardless of order of objects in the 2 lists,
     * return boolean whether 2 lists are equals.
     */
    public boolean isTwoArrayListsWithSameValues(ArrayList<String> list1, ArrayList<String> list2) {
        //null checking
        if (list1 == null && list2 == null) {
            return true;
        }
        if ((list1 == null && list2 != null) || (list1 != null && list2 == null)) {
            return false;
        }
        if (list1.size() != list2.size()) {
            return false;
        }
        for (Object itemList1: list1) {
            if (!list2.contains(itemList1)) {
                return false;
            }
        }
        return true;
    }
}
