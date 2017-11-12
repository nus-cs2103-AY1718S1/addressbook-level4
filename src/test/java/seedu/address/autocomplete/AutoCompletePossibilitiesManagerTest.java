package seedu.address.autocomplete;

import static seedu.address.testutil.TypicalPersons.ALICE;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.autocomplete.parser.ModelStub;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.TypicalPersons;

//@@author john19950730
public class AutoCompletePossibilitiesManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AutoCompletePossibilitiesManager logic =
            new AutoCompletePossibilitiesManager(new ModelStubWithRequiredMethods(), 10);

    @Test
    public void testEmptyInput() {
        //parses empty string back
        assertPossibilities("");
    }

    @Test
    public void testSingleWordInput() {
        //chooses to parse commands -- command parser
        assertPossibilities("a",
                AddCommand.COMMAND_USAGE);
        //no commands starting with this -- command parser
        assertPossibilities("goofie");
    }

    @Test
    public void testDoubleWordInput() {
        //chooses to parse words in names -- word in name parser
        assertPossibilities("find a",
                "find Alice");
        //chooses to parse sort fields -- set string parser
        assertPossibilities("sort n",
                "sort name");
        //chooses to parse tags -- tag parser
        assertPossibilities("findtag f",
                "findtag friends", "findtag family");
        //chooses to parse by address prefix -- model parser
        assertPossibilities("edit 2 a/123",
                "edit 2 a/" + ALICE.getAddress().toString());
    }

    @Test
    public void testTripleWordInput() {
        //chooses to parse sort order -- set string parser
        assertPossibilities("sort email a",
                "sort email asc");
        //chooses to parse by last prefix found -- model parser
        assertPossibilities("add n/Sabin t/c",
                "add n/Sabin t/colleagues");
        //chooses to parse word in name, ignoring second word -- word in name parser
        assertPossibilities("find Alice b",
                "find Alice Benson", "find Alice Best");
        //chooses to parse tag, ignoring second word -- tag parser
        assertPossibilities("removetag prospective c",
                "removetag prospective colleagues");
    }

    private void assertPossibilities(String stub, String... possibilities) {
        AutoCompleteTestUtils.assertEqualsPossibilities(stub,
                logic.search(stub).getPossibilities(), possibilities);
    }

    //@@author
    /**
     * A Model stub that allows calling of certain methods used in the test,
     * including some required accessors and addPerson
     */
    private class ModelStubWithRequiredMethods extends ModelStub {
        final List<ReadOnlyPerson> personsAdded = TypicalPersons.getTypicalPersons();

        @Override
        public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
            personsAdded.add(new Person(person));
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
