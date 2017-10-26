package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import javafx.util.Pair;

import seedu.address.logic.parser.SortArgument;

public class PersonCompareTest {

    private Set<ReadOnlyPerson> personSet = new HashSet<>();
    private ArrayList<Pair<ReadOnlyPerson, ReadOnlyPerson>> personPermutations = new ArrayList<>();

    @Before
    public void setUp() {
        personSet.addAll(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE, HOON, IDA));
        personPermutations = generatePersonPermutations(personSet);
    }

    @Test
    public void assertCompareToPersonInvalidSortArgument() {
        for (Pair<ReadOnlyPerson, ReadOnlyPerson> personPair : personPermutations) {
            assertEquals(personPair.getKey().compareTo(personPair.getValue(), new SortArgument("InvalidSorter")),
                    personPair.getKey().compareTo(personPair.getValue()));
            assertEquals(personPair.getValue().compareTo(personPair.getKey(), new SortArgument("InvalidSorter")),
                    personPair.getValue().compareTo(personPair.getKey()));
        }
    }

    @Test
    public void assertHashCode() {
        for (ReadOnlyPerson person : personSet) {
            assertEquals(person.hashCode(),
                    Objects.hash(person.nameProperty(),
                            person.phoneProperty(),
                            person.emailProperty(),
                            person.addressProperty(),
                            person.remarkProperty(),
                            person.tagProperty()));
        }
    }

    /**
     * Returns a list permutation of all pairs of persons in a set
     * @param set to be permuted
     */
    private static ArrayList<Pair<ReadOnlyPerson, ReadOnlyPerson>> generatePersonPermutations(Set<ReadOnlyPerson> set) {
        ArrayList<Pair<ReadOnlyPerson, ReadOnlyPerson>> personPairList = new ArrayList<>();
        ArrayList<ReadOnlyPerson> personList = new ArrayList<>(set);

        for (int i = 0; i < set.size(); i++) {
            for (int j = i + 1; j < set.size(); j++) {
                if (i != j) {
                    Pair<ReadOnlyPerson, ReadOnlyPerson> personPair = new Pair<>(personList.get(i), personList.get(j));
                    personPairList.add(personPair);
                }
            }
        }

        return personPairList;
    }
}
