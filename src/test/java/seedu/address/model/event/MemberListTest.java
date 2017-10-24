package seedu.address.model.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Test;

import seedu.address.commons.util.EventOutputUtil;

import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.TypicalPersons;

public class MemberListTest {

    @Test
    public void testMemberListCreationNonEmpty() {

        ArrayList<ReadOnlyPerson> list1 = new ArrayList<>();
        list1.add(TypicalPersons.ALICE);
        list1.add(TypicalPersons.BOB);
        MemberList m1 = new MemberList(list1);

        ArrayList<ReadOnlyPerson> list2 = new ArrayList<>();
        list2.add(TypicalPersons.ALICE);
        list2.add(TypicalPersons.BOB);
        MemberList m2 = new MemberList(list2);

        ArrayList<ReadOnlyPerson> list3 = new ArrayList<>();
        list3.add(TypicalPersons.DANIEL);
        list3.add(TypicalPersons.CARL);
        MemberList m3 = new MemberList(list3);

        MemberList m4Empty = new MemberList();

        //Test contains
        assertTrue(m1.contains(TypicalPersons.ALICE));
        assertFalse(m1.contains(TypicalPersons.AMY));

        //Test isEmpty
        assertFalse(m1.isEmpty());

        //Test toString
        assertEquals(EventOutputUtil.toStringMembers(list1), m1.toString());

        //Test equals
        assertTrue(m1.equals(m2));
        assertFalse(m1.equals(m3));
        assertFalse(m1.equals(m4Empty));

        //Test readOnlyList
        assertEquals(list1, m1.asReadOnlyMemberList());

    }

    @Test
    public void testMemberListCreationEmpty() {

        MemberList m1 = new MemberList();
        MemberList m2 = new MemberList();

        //Non-empty list for testing equals function
        ArrayList<ReadOnlyPerson> list = new ArrayList<>();
        list.add(TypicalPersons.ALICE);
        MemberList m3 = new MemberList(list);

        assertTrue(m1.isEmpty());
        assertTrue(m1.equals(m2));

        assertFalse(m1.contains(TypicalPersons.ALICE));

        assertEquals("none", m1.toString());

        //Empty list for comparing hashcode
        ArrayList<ReadOnlyPerson> emptyList = new ArrayList<>();
        assertEquals(emptyList.hashCode(), m1.hashCode());

        //Test equals
        assertFalse(m1.equals(m3));
        assertTrue(m1.equals(m2));

    }

    @Test
    public void testReadOnlyList() {

        //Test read-only list
        ArrayList<ReadOnlyPerson> listEmpty = new ArrayList<>();
        MemberList m1Empty = new MemberList();

        ArrayList<ReadOnlyPerson> list = new ArrayList<>();
        list.add(TypicalPersons.ALICE);
        MemberList m2 = new MemberList(list);

        assertEquals(Collections.unmodifiableList(listEmpty), m1Empty.asReadOnlyMemberList());
        assertEquals(Collections.unmodifiableList(list), m2.asReadOnlyMemberList());
    }

}
