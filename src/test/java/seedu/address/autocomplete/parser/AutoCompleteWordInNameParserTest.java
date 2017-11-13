package seedu.address.autocomplete.parser;

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

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.autocomplete.AutoCompleteTestUtils;
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
            ALICE, AMY, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE);

    //@@author john19950730
    @Test
    public void testParsePossibilities() {

        mockModel = new ModelStubWithRequiredMethods();
        try {
            mockModel.addAllPersons(allPersonsAdded);
        } catch (DuplicatePersonException ex) {
            fail("This exception should not be thrown.");
        }
        parser = new AutoCompleteWordInNameParser(mockModel);

        // multiple matches
        String preamble = "find A";
        AutoCompleteTestUtils.assertParserPossibilities(preamble, parser,
                preamble + "lice",
                preamble + "my");

        // single match
        preamble = "find P";
        AutoCompleteTestUtils.assertParserPossibilities(preamble, parser,
                preamble + "auline");

        // single duplicate match
        preamble = "find Mey";
        AutoCompleteTestUtils.assertParserPossibilities(preamble, parser,
                preamble + "er");

        // no match
        preamble = "find Z";
        AutoCompleteTestUtils.assertParserPossibilities(preamble, parser);

        mockModel = null;
        parser = null;
    }

    //@@author
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
