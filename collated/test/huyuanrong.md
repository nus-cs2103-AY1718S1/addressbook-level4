# huyuanrong
###### \java\seedu\address\logic\commands\ContactsCommandTest.java
``` java
public class ContactsCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_help_success() {
        CommandResult result = new ContactsCommand().execute();
        assertEquals(SHOWING_CONTACT_MESSAGE, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowContactsEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_findTag() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + FindCommand.COMMAND_WORD_TAG_PREFIX
                        + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new TagContainsKeywordsPredicate(keywords)), command);
    }
```
###### \java\seedu\address\model\place\EmailTest.java
``` java
        assertFalse(Email.isValidEmail("max()min@ggg.com")); //invalid email
```
###### \java\seedu\address\model\place\EmailTest.java
``` java
        assertTrue(Email.isValidEmail("this-example@example-test.com")); //dashes
        assertTrue(Email.isValidEmail("sss.ppp@example.com")); //dots
        assertTrue(Email.isValidEmail("sss.ppp@example.com.sg")); // two TLD domain
        assertTrue(Email.isValidEmail("sss.ppp-qqq@example.edu.sg")); //dots and dashes
        assertTrue(Email.isValidEmail("example@1.com")); //domain name is a number with valid TLD
```
###### \java\seedu\address\model\place\NameTest.java
``` java
        assertTrue(Name.isValidName("ëëëëéééé")); //Weird Characters
```
