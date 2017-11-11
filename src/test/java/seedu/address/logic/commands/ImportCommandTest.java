package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.LogicManager;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;

//@@author limshunyong
public class ImportCommandTest {

    private LogicManager logic;
    private Model model;

    @Before
    public void setup() throws Exception {
        AddressBook ab = new AddressBook();
        model = new ModelManager(ab, new UserPrefs());
        logic = new LogicManager(model);

    }

    @Test
    public void execute_import_fail() throws Exception {
        AddressBook ab = new AddressBook();

        CommandResult r = logic.execute("import wrongfilename.vcf");
        assertEquals(ImportCommand.MESSAGE_FAILURE, r.feedbackToUser);

        // addressbook should be empty
        assertEquals(ab, model.getAddressBook());

    }

    @Test
    public void execute_import_success() throws Exception {

        AddressBook expected = new AddressBook();

        Name name = new Name("TestData");
        Phone phone = new Phone("11111111");
        Email email = new Email("testData@example.com");
        Address address = new Address("111 Test Data Road, Singapore(111111)");
        Set<Tag> tags = new HashSet<>();
        ReadOnlyPerson p = new Person(name, phone, email, address, tags);

        expected.addPerson(p);

        CommandResult r = logic.execute("import test.vcf");
        assertEquals(ImportCommand.MESSAGE_SUCCESS, r.feedbackToUser);

        assertEquals(expected, model.getAddressBook());
    }
}
