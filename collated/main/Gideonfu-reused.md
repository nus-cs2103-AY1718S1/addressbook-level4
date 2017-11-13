# Gideonfu-reused
###### \java\seedu\address\logic\parser\DeleteCommandParser.java
``` java
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        try {
            Index[] index = ParserUtil.parseDeleteIndex(args);
            return new DeleteCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
    }

}
```
###### \java\seedu\address\ui\AddressPanel.java
``` java
    /**
     * Loads the address in Google Maps of the selected Person
     * @param person
     */
    private void loadPersonAddress(ReadOnlyPerson person) {

        if (person.getAddress().value.equals("~")) {
            loadDefaultPage();
        } else {
            loadPage(GOOGLE_SEARCH_URL_PREFIX
                    + person.getAddress().value.replaceAll(" ", "+").replaceAll(UNIT_NUMBER_REGEX, "")
                    + GOOGLE_SEARCH_URL_SUFFIX);
        }
        this.person = person;
        bindListeners(person);
    }

```
