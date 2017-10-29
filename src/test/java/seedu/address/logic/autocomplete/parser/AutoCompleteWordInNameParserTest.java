package seedu.address.logic.autocomplete.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;

public class AutoCompleteWordInNameParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private AutoCompleteWordInNameParser parser;
    private ModelStubWithRequiredMethods mockModel;
    private final List<ReadOnlyPerson> allPersonsAdded = Arrays.asList(
            new ReadOnlyPerson[]{ALICE, AMY, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE});

    @Before
    public void fillMockModel() {
        mockModel = new ModelStubWithRequiredMethods();
        try {
            mockModel.addAllPersons(allPersonsAdded);
        } catch (DuplicatePersonException ex) {
            fail("This exception should not be thrown.");
        }
        parser = new AutoCompleteWordInNameParser(mockModel);
    }

    //@@author john19950730
    @Test
    public void testParsePossibilities() {
        // multiple matches
        String preamble = "find A";
        assertEquals(parser.parseForPossibilities(preamble),
                Arrays.asList(preamble + "lice",
                        preamble + "my",
                        preamble));

        // single match
        preamble = "find P";
        assertEquals(parser.parseForPossibilities(preamble),
                Arrays.asList(preamble + "auline",
                        preamble));

        // single duplicate match
        preamble = "find Mey";
        assertEquals(parser.parseForPossibilities(preamble),
                Arrays.asList(preamble + "er",
                        preamble));

        // no match
        preamble = "find Z";
        assertEquals(parser.parseForPossibilities(preamble),
                Arrays.asList(preamble));
    }

    //@@author
    @After
    public void cleanUpMockModel() {
        mockModel = null;
        parser = null;
    }

    /**
     * A Model stub that allows calling of certain methods used in the test,
     * including some required accessors and addPerson
     */
    private class ModelStubWithRequiredMethods extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        @Override
        public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
            personsAdded.add(new Person(person));
        }

        public void addAllPersons(List<ReadOnlyPerson> persons) throws DuplicatePersonException {
            for (ReadOnlyPerson person : persons) {
                this.addPerson(person);
            }
        }

        @Override
        public List<String> getAllNamesInAddressBook() {
            return personsAdded.stream()
                    .map(person -> person.getName().toString())
                    .collect(Collectors.toList());
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
