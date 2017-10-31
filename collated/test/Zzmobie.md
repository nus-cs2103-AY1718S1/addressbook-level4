# Zzmobie
###### \java\seedu\address\logic\commands\FindTagCommandTest.java
``` java
    @Test
    public void execute_singleKeywords_singlePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FindTagCommand command = prepareCommand("owesMoney");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BENSON));
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        FindTagCommand command = prepareCommand("owesMoney owesLunch");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BENSON, CARL));
    }

```
###### \java\seedu\address\logic\commands\ToggleAccessDisplayCommandTest.java
``` java
    @Test
    public void execute_toggle_off_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        assertCommandSuccess(prepareCommand(model,false), model,
                ToggleAccessDisplayCommand.MESSAGE_SUCCESS + "off. ", model);
    }
```
