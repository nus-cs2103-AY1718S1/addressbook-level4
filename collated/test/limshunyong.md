# limshunyong
###### /java/seedu/address/logic/commands/ImportCommandTest.java
``` java
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
```
###### /java/seedu/address/logic/commands/SortCommandTest.java
``` java
public class SortCommandTest {

    private LogicManager logic;
    private Model model;

    private Person alice = new PersonBuilder().withName("Alice").build();
    private Person bob = new PersonBuilder().withName("Bob").build();
    private Person charlie = new PersonBuilder().withName("Charlie").build();
    private Person zack = new PersonBuilder().withName("Zack").build();

    @Before
    public void setup() throws Exception {
        AddressBook unsorted = new AddressBook();
        unsorted.addPerson(charlie);
        unsorted.addPerson(bob);
        unsorted.addPerson(zack);
        unsorted.addPerson(alice);

        model = new ModelManager(unsorted, new UserPrefs());
        logic = new LogicManager(model);
    }

    @Test
    public void execute_sort_successful() throws Exception {
        AddressBook sorted = new AddressBook();

        sorted.addPerson(alice);
        sorted.addPerson(bob);
        sorted.addPerson(charlie);
        sorted.addPerson(zack);

        CommandResult r = logic.execute("sort");
        assertEquals(SortCommand.MESSAGE_SUCCESS, r.feedbackToUser);

        assertEquals(sorted, model.getAddressBook());

    }
}
```
