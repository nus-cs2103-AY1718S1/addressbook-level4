# limshunyong
###### /java/seedu/address/logic/commands/ExportCommandTest.java
``` java
public class ExportCommandTest {

    private LogicManager logic;
    private ModelManager model;

    @Before
    public void setup() throws Exception {
        AddressBook ab = new AddressBook();
        model = new ModelManager(ab, new UserPrefs());
        logic = new LogicManager(model);

    }

    @Test
    public void execute_export_emptyList() throws Exception {

        CommandResult r = logic.execute("export");
        assertEquals(ExportCommand.MESSAGE_EMPTY_AB, r.feedbackToUser);

    }

    @Test
    public void execute_export_fail() throws Exception {
        PersonBuilder testPerson = new PersonBuilder();

        model.addPerson(testPerson.build());

        // to throw IOException
        File f = new File("output.vcf");
        Boolean cannotWrite = f.setWritable(false);

        CommandResult r = logic.execute("export");
        assertEquals(ExportCommand.MESSAGE_FAIL, r.feedbackToUser);

    }

    @Test
    public void execute_export_success() throws Exception {
        PersonBuilder testPerson = new PersonBuilder();

        model.addPerson(testPerson.build());

        // reset file permission from execute_export_fail()
        File f = new File("output.vcf");
        Boolean canWrite = f.setWritable(true);

        CommandResult r = logic.execute("export");
        assertEquals(ExportCommand.MESSAGE_SUCCESS, r.feedbackToUser);


        Path f1 = Paths.get("output.vcf");
        Path f2 = Paths.get("testExport.vcf");

        byte[] file1 = Files.readAllBytes(f1);
        byte[] file2 = Files.readAllBytes(f2);

        assertArrayEquals(file1, file2);

    }

}
```
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

    @Test
    public void execute_importNameOnlySuccess() throws Exception {

        AddressBook expected = new AddressBook();

        Name name = new Name("Brendan Richard");
        Phone phone = new Phone("11111111");
        Email email = new Email("BrendanRichard@example.com");
        Address address = new Address("13 Computing Drive");
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("containsDummyemail"));
        tags.add(new Tag("containsDummyaddress"));
        tags.add(new Tag("containsDummyphone"));

        ReadOnlyPerson p = new Person(name, phone, email, address, tags);

        expected.addPerson(p);

        CommandResult r = logic.execute("import nameOnly.vcf");
        assertEquals(ImportCommand.MESSAGE_SUCCESS, r.feedbackToUser);

        assertEquals(expected, model.getAddressBook());

    }

    @Test
    public void execute_importNoEmailSuccess() throws Exception {

        AddressBook expected = new AddressBook();

        Name name = new Name("Brendan Richard");
        Phone phone = new Phone("91234566");
        Email email = new Email("BrendanRichard@example.com");
        Address address = new Address("Some Address");
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("containsDummyemail"));

        ReadOnlyPerson p = new Person(name, phone, email, address, tags);

        expected.addPerson(p);

        CommandResult r = logic.execute("import noEmail.vcf");
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

    private Person alice = new PersonBuilder().withName("Alice").withEmail("alice@example.com").build();
    private Person bob = new PersonBuilder().withName("Bob").withEmail("bob@example.com").build();
    private Person charlie = new PersonBuilder().withName("Charlie").withEmail("charlie@example.com").build();
    private Person zack = new PersonBuilder().withName("Zack").withEmail("zack@example.com").build();

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
