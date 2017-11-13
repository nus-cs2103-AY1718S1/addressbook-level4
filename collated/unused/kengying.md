# kengying
###### \ListCommandTest.java
``` java

@Test
public void execute_zeroKeyword_noPersonFound(){
    ListCommand command=prepareCommand(" ");
    assertCommandSuccess(command,expectedMessage,Collections.emptyList());
}

@Test
public void execute_oneTags_onePersonFound(){
    String expectedMessage=String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,1);
    ListCommand command=prepareCommand("owesMoney");
    assertCommandSuccess(command,expectedMessage,Arrays.asList(BENSON));
}

@Test
public void execute_oneGroup_onePersonFound(){
    String expectedMessage=String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,1);
    ListCommand command=prepareCommand("Car");
    assertCommandSuccess(command,expectedMessage,Arrays.asList(ELLE));
}

@Test
public void execute_twoTags_sevenPersonFound(){
    String expectedMessage=String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,7);
    ListCommand command=prepareCommand("owesMoney friend");
    assertCommandSuccess(command,expectedMessage,Arrays.asList(ALICE,BENSON,CARL,DANIEL,ELLE,FIONA,GEORGE));
}

@Test
public void execute_twoGroups_sevenPersonFound(){
    String expectedMessage=String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,7);
    ListCommand command=prepareCommand("Car Savings");
    assertCommandSuccess(command,expectedMessage,Arrays.asList(ALICE,BENSON,CARL,DANIEL,ELLE,FIONA,GEORGE));
}

@Test
public void execute_oneTagGroup_sevenPersonFound(){
    String expectedMessage=String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,2);
    ListCommand command=prepareCommand("Car owesMoney");
    assertCommandSuccess(command,expectedMessage,Arrays.asList(BENSON,ELLE));
}

@Test
public void execute_wrongKeyword_zeroPersonFound(){
    String expectedMessage=String.format(MESSAGE_PERSONS_LISTED_OVERVIEW,0);
    ListCommand command=prepareCommand("sdfsdf");
    assertCommandSuccess(command,expectedMessage,Collections.emptyList());
}

```
###### \PersonContainsTagsPredicate.java
``` java
/**
 * Tests that any of {@code ReadOnlyPerson}'s Name, Phone, Email or Address matches any of the keywords given.
 */
public class PersonContainsTagsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public PersonContainsTagsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return (keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getTags().toString()
                        .replace("[", "").replace("]", "")
                        .replace(",", ""), keyword)
                        || StringUtil.containsWordIgnoreCase(person.getGroup().groupName, keyword)));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonContainsTagsPredicate // instanceof handles nulls
                && this.keywords.equals(((PersonContainsTagsPredicate) other).keywords)); // state check
    }
```
