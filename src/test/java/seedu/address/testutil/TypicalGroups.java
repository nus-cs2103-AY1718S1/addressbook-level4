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
    private Group testGroup1;
    private Group testGroup2;
    private Group testGroup3;
    private Group testGroup4;

    public TypicalGroups() {
        testGroup1 = new Group("TestGrp1");
        try {
            testGroup1.add(TypicalPersons.HOON);
            testGroup1.add(TypicalPersons.IDA);
        } catch (DuplicatePersonException e) {
            assert false : "not possible";
        }

        testGroup2 = new Group("TestGrp2");
        try {
            testGroup2.add(TypicalPersons.HOON);
            testGroup2.add(TypicalPersons.ALICE);
            testGroup2.add(TypicalPersons.AMY);
        } catch (DuplicatePersonException e) {
            assert false : "not possible";
        }

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

    public List<Group> getTypicalGroups() {
        return new ArrayList<>(Arrays.asList(testGroup1, testGroup2, testGroup3, testGroup4));
    }
}
//@@author
