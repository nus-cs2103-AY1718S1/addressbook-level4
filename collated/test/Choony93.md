# Choony93
###### /java/seedu/address/logic/parser/GmapCommandParserTest.java
``` java
    @Test
    public void parse_validArgs_returnsSelectCommand() {
        assertParseSuccess(parser, "1", new GmapCommand(INDEX_FIRST_PERSON));

        GmapCommand expectedFindCommand =
                new GmapCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "0", String.format(MESSAGE_INVALID_COMMAND_FORMAT, GmapCommand.MESSAGE_USAGE));
    }
```
###### /java/seedu/address/logic/parser/ThemeCommandParserTest.java
``` java
    @Test
    public void parse_validArgs_returnsSelectCommand() {
        assertParseSuccess(parser, "1", new ThemeCommand(Index.fromOneBased(1)));

        assertParseSuccess(parser, "caspian", new ThemeCommand("caspian"));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "0", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ThemeCommand.MESSAGE_USAGE));
    }
```
###### /java/seedu/address/ui/BrowserPanelTest.java
``` java
        // associated google map page of a person
        postNow(gmapEventStub);

        waitUntilBrowserLoaded(browserPanelHandle);
        assertTrue(browserPanelHandle.getLoadedUrl().toString(), browserPanelHandle.getLoadedUrl()
                .toString().contains("https://www.google.com/maps/search/"));
```
