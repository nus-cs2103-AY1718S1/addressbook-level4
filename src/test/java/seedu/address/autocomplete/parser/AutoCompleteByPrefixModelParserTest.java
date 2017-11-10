package seedu.address.autocomplete.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.BOB;
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
import seedu.address.model.tag.Tag;

//@@author john19950730
public class AutoCompleteByPrefixModelParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private AutoCompleteByPrefixModelParser parser;
    private ModelStubWithRequiredMethods mockModel;
    private final List<ReadOnlyPerson> allPersonsAdded = Arrays.asList(
            new ReadOnlyPerson[]{ALICE, AMY, BENSON, BOB, CARL, DANIEL, ELLE, FIONA, GEORGE});

    @Before
    public void fillMockModel() {
        mockModel = new ModelStubWithRequiredMethods();
        try {
            mockModel.addAllPersons(allPersonsAdded);
        } catch (DuplicatePersonException ex) {
            fail("This exception should not be thrown.");
        }
        parser = new AutoCompleteByPrefixModelParser(mockModel);
    }

    @Test
    public void testParseName() {
        parser.setPrefix(PREFIX_NAME);
        //multiple possibilities matched
        assertPossibilities("add n/a",
                "add n/" + ALICE.getName().toString(),
                "add n/" + AMY.getName().toString());

        //single possibility matched
        assertPossibilities("add n/f",
                "add n/" + FIONA.getName().toString());

        //no possibility matched
        assertPossibilities("add n/r");
    }

    @Test
    public void testParsePhone() {
        parser.setPrefix(PREFIX_PHONE);
        //multiple possibilities matched
        assertPossibilities("edit 1 p/8",
                "edit 1 p/" + ALICE.getPhone().toString(),
                "edit 1 p/" + DANIEL.getPhone().toString());

        //single possibility matched
        assertPossibilities("add p/111",
                "add p/" + AMY.getPhone().toString());

        //no possibility matched, even if some phone numbers contain the sequence
        assertPossibilities("add p/482");
    }

    @Test
    public void testParseEmail() {
        parser.setPrefix(PREFIX_EMAIL);
        //multiple possibilities matched
        assertPossibilities("edit 5 e/a",
                "edit 5 e/" + ALICE.getEmail().toString(),
                "edit 5 e/" + AMY.getEmail().toString(),
                "edit 5 e/" + GEORGE.getEmail().toString());

        //single possibility matched
        assertPossibilities("edit 12 e/corn",
                "edit 12 e/" + DANIEL.getEmail().toString());

        //no possibility matched
        assertPossibilities("add e/example.com");
    }

    @Test
    public void testParseAddress() {
        parser.setPrefix(PREFIX_ADDRESS);
        //multiple possibilities matched
        assertPossibilities("add a/1",
                "add a/" + ALICE.getAddress().toString(),
                "add a/" + DANIEL.getAddress().toString());

        //single possibility matched
        assertPossibilities("edit 2 a/10",
                "edit 2 a/" + DANIEL.getAddress().toString());

        //no possibility matched
        assertPossibilities("add a/serangoon");
    }

    @Test
    public void testParseTags() {
        parser.setPrefix(PREFIX_TAG);
        //multiple possibilities matched
        assertPossibilities("removetag t/f",
                "removetag t/friends",
                "removetag t/friend",
                "removetag t/family");

        //single possibility matched
        assertPossibilities("edit 1 t/fa",
                "edit 1 t/family");

        //no possibility matched
        assertPossibilities("add n/Goatman t/enemy t/to");
    }

    @Test
    public void testParseRemark() {
        parser.setPrefix(PREFIX_REMARK);

        //single possibility matched
        assertPossibilities("remark 1 r/Like",
                "remark 1 r/" + GEORGE.getRemark().toString());

        //no possibility matched
        assertPossibilities("remark 1 r/Not in list");
    }

    @Test
    public void testUpdateNames() {
        parser.setPrefix(PREFIX_NAME);

        String preamble = "add n/";
        List<String> expected = allPersonsAdded.stream()
                .map(person -> preamble + person.getName().toString())
                .collect(Collectors.toList());
        expected.add(preamble);

        assertEquals(parser.parseForPossibilities(preamble), expected);
    }

    @Test
    public void testUpdatePhones() {
        parser.setPrefix(PREFIX_PHONE);

        String preamble = "edit 1 p/";
        List<String> expected = allPersonsAdded.stream()
                .map(person -> preamble + person.getPhone().toString())
                .collect(Collectors.toList());
        expected.add(preamble);

        assertEquals(parser.parseForPossibilities(preamble), expected);
    }

    @Test
    public void testUpdateEmails() {
        parser.setPrefix(PREFIX_EMAIL);

        String preamble = "add n/Jane Dope e/";
        List<String> expected = allPersonsAdded.stream()
                .map(person -> preamble + person.getEmail().toString())
                .collect(Collectors.toList());
        expected.add(preamble);

        assertEquals(parser.parseForPossibilities(preamble), expected);
    }

    @Test
    public void testUpdateTags() {
        parser.setPrefix(PREFIX_TAG);

        String preamble = "add n/Da Mythic t/";

        assertPossibilities(preamble,
                preamble + "friends",
                preamble + "friend",
                preamble + "owesMoney",
                preamble + "husband",
                preamble + "colleagues",
                preamble + "family");
    }

    @After
    public void cleanUpMockModel() {
        mockModel = null;
        parser = null;
    }

    //@@author
    /**
     * Tests the possibilities generated by the stub against the list of possibilities expected
     * @param stub test input
     * @param possibilities expected possibilities output
     */
    private void assertPossibilities(String stub, String... possibilities) {
        List<String> expected = new ArrayList<String>();
        expected.addAll(Arrays.asList(possibilities));
        expected.add(stub);
        assertEquals(parser.parseForPossibilities(stub), expected);
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
        public List<String> getAllPhonesInAddressBook() {
            return personsAdded.stream()
                    .map(person -> person.getPhone().toString())
                    .collect(Collectors.toList());
        }

        @Override
        public List<String> getAllEmailsInAddressBook() {
            return personsAdded.stream()
                    .map(person -> person.getEmail().toString())
                    .collect(Collectors.toList());
        }

        @Override
        public List<String> getAllAddressesInAddressBook() {
            return personsAdded.stream()
                    .map(person -> person.getAddress().toString())
                    .collect(Collectors.toList());
        }

        //@@author john19950730
        @Override
        public List<String> getAllTagsInAddressBook() {
            //generate a unique tag list first
            final ArrayList<Tag> tagsList = new ArrayList<Tag>();
            for (ReadOnlyPerson person : personsAdded) {
                for (Tag tag : person.getTags()) {
                    if (tagsList.indexOf(tag) == -1) {
                        tagsList.add(tag);
                    }
                }
            }

            return tagsList.stream()
                    .map(tag -> tag.toString().substring(1, tag.toString().length() - 1))
                    .collect(Collectors.toList());
        }

        //@@author
        @Override
        public List<String> getAllRemarksInAddressBook() {
            return personsAdded.stream()
                    .map(person -> person.getRemark().toString())
                    .filter(remark -> !remark.equals(""))
                    .collect(Collectors.toList());
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
