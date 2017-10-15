package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalRolodex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;

public class RolodexTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final Rolodex rolodex = new Rolodex();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), rolodex.getPersonList());
        assertEquals(Collections.emptyList(), rolodex.getTagList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        rolodex.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyRolodex_replacesData() {
        Rolodex newData = getTypicalRolodex();
        rolodex.resetData(newData);
        assertEquals(newData, rolodex);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsAssertionError() {
        // Repeat ALICE twice
        List<Person> newPersons = Arrays.asList(new Person(ALICE), new Person(ALICE));
        List<Tag> newTags = new ArrayList<>(ALICE.getTags());
        RolodexStub newData = new RolodexStub(newPersons, newTags);

        thrown.expect(AssertionError.class);
        rolodex.resetData(newData);
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        rolodex.getPersonList().remove(0);
    }

    @Test
    public void getTagList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        rolodex.getTagList().remove(0);
    }

    /**
     * A stub ReadOnlyRolodex whose persons and tags lists can violate interface constraints.
     */
    private static class RolodexStub implements ReadOnlyRolodex {
        private final ObservableList<ReadOnlyPerson> persons = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();

        RolodexStub(Collection<? extends ReadOnlyPerson> persons, Collection<? extends Tag> tags) {
            this.persons.setAll(persons);
            this.tags.setAll(tags);
        }

        @Override
        public ObservableList<ReadOnlyPerson> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Tag> getTagList() {
            return tags;
        }
    }

}
