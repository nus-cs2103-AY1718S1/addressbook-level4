//@@author hthjthtrh
package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.group.Group;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * to create typical groups
 */
public class TypicalGroups {

    private Group testGroup3;

    private Group testGroup4;

    public TypicalGroups() {
        testGroup3 = new Group("TestGrp3");
        try {
            testGroup3.add(TypicalPersons.ALICE);
            testGroup3.add(TypicalPersons.DANIEL);
            testGroup3.add(TypicalPersons.CARL);
        } catch (DuplicatePersonException e) {
            assert false : "not possible";
        }

        try {
            testGroup4 = new Group(testGroup3);
            testGroup4.setGrpName("TestGrp4");
            testGroup4.add(TypicalPersons.ELLE);
            testGroup4.add(TypicalPersons.GEORGE);
        } catch (DuplicatePersonException e) {
            assert false : "not possible";
        }
    }

    public Group getTestGroup3() {
        return testGroup3;
    }

    public Group getTestGroup4() {
        return testGroup4;
    }

    public List<Group> getTypicalGroups() {
        return new ArrayList<>(Arrays.asList(testGroup3, testGroup4));
    }
}
//@@author
