# angtianlannus
###### /java/seedu/address/logic/commands/FindCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model;
    private Model expectedModel;
    private List<String> keywords;
    private List<ReadOnlyLesson> expectedList;
    private String expectedMessage;
    private final ListingUnit beginningListingUnit = ListingUnit.getCurrentListingUnit();


    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        keywords = new ArrayList<>();
        expectedList = new ArrayList<>();
        expectedMessage = new String();
    }


    @Test
    public void equals() {

        List<String> keywordOne = new ArrayList<String>() {
        };
        List<String> keywordTwo = new ArrayList<String>() {
        };

        keywordOne.add("111");
        keywordOne.add("222");
        keywordOne.add("333");

        keywordTwo.add("aaa");
        keywordTwo.add("bbb");
        keywordTwo.add("bbb");

        FindCommand firstFindCommand = new FindCommand(keywordOne);
        FindCommand secondFindCommand = new FindCommand(keywordTwo);

        // same object -> returns true
        assertTrue(firstFindCommand.equals(firstFindCommand));

        // same values -> returns true
        FindCommand firstFindCommandCopy = new FindCommand(keywordOne);
        assertTrue(firstFindCommand.equals(firstFindCommandCopy));

        // different types -> returns false
        assertFalse(firstFindCommand.equals(1));

        // null -> returns false
        assertFalse(firstFindCommand.equals(null));

        // different find command -> returns false
        assertFalse(firstFindCommand.equals(secondFindCommand));
    }

    @Test
    public void execute_findByValidModuleCode_moduleFound() {
        ListingUnit.setCurrentListingUnit(ListingUnit.MODULE);
        keywords.add("MA1101");
        FindCommand findByModule = new FindCommand(keywords);
        findByModule.setData(model, new CommandHistory(), new UndoRedoStack());
        expectedMessage = FindCommand.MESSAGE_SUCCESS;
        model.updateFilteredLessonList(new ModuleContainsKeywordsPredicate(keywords));
        expectedList = model.getFilteredLessonList();

        assertCommandSuccess(findByModule, expectedMessage, expectedList);
    }

    @Test
    public void execute_findByValidLocation_locationFound() {
        ListingUnit.setCurrentListingUnit(ListingUnit.LOCATION);
        keywords.add("LT27");
        FindCommand findByLocation = new FindCommand(keywords);
        findByLocation.setData(model, new CommandHistory(), new UndoRedoStack());
        expectedMessage = FindCommand.MESSAGE_SUCCESS;
        model.updateFilteredLessonList(new LocationContainsKeywordsPredicate(keywords));
        expectedList = model.getFilteredLessonList();

        assertCommandSuccess(findByLocation, expectedMessage, expectedList);
    }

    @Test
    public void execute_findByValidLessonDetails_lessonsFound() {
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);
        model.setViewingPanelAttribute("module");
        model.setCurrentViewingLesson(MA1101R_L1);
        keywords.add("FRI");
        FindCommand findByLesson = new FindCommand(keywords);
        findByLesson.setData(model, new CommandHistory(), new UndoRedoStack());
        expectedMessage = FindCommand.MESSAGE_SUCCESS;
        model.updateFilteredLessonList(new LessonContainsKeywordsPredicate(keywords, MA1101R_L1, "module"));
        expectedList = model.getFilteredLessonList();

        assertCommandSuccess(findByLesson, expectedMessage, expectedList);
    }

    @Test
    public void execute_findByValidLessonDetailsInMarkedList_noLessonsFound() {
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);
        model.setViewingPanelAttribute("marked");
        model.setCurrentViewingLesson(MA1101R_L1);
        keywords.add("FRI");
        FindCommand findByMarkedLesson = new FindCommand(keywords);
        findByMarkedLesson.setData(model, new CommandHistory(), new UndoRedoStack());
        expectedMessage = FindCommand.MESSAGE_SUCCESS;
        model.updateFilteredLessonList(new MarkedLessonContainsKeywordsPredicate(keywords));
        expectedList = model.getFilteredLessonList();

        assertCommandSuccess(findByMarkedLesson, expectedMessage, expectedList);
    }
```
###### /java/seedu/address/logic/commands/FindCommandTest.java
``` java
    @After
    public void wrapUp() {
        ListingUnit.setCurrentListingUnit(beginningListingUnit);
        model.setViewingPanelAttribute("default");
    }
}
```
###### /java/seedu/address/logic/commands/SortCommandTest.java
``` java
public class SortCommandTest {

    private Model model;
    private Model expectedModel;
    private List<ReadOnlyLesson> expectedList;
    private String expectedMessage;
    private final ListingUnit beginningListingUnit = ListingUnit.getCurrentListingUnit();


    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedList = new ArrayList<>();
        expectedMessage = new String();
    }

    @Test
    public void execute_sortInModuleList_sortListByModule() {

        ListingUnit.setCurrentListingUnit(ListingUnit.MODULE);
        SortCommand sortByModule = new SortCommand();
        sortByModule.setData(model, new CommandHistory(), new UndoRedoStack());
        expectedMessage = SortCommand.MESSAGE_SORT_LESSON_SUCCESS;
        model.sortLessons();
        expectedList = model.getFilteredLessonList();
        assertCommandSuccess(sortByModule, expectedMessage, expectedList);

    }

    @Test
    public void execute_sortInLocationList_sortListByLocation() {

        ListingUnit.setCurrentListingUnit(ListingUnit.LOCATION);
        SortCommand sortByModule = new SortCommand();
        sortByModule.setData(model, new CommandHistory(), new UndoRedoStack());
        expectedMessage = SortCommand.MESSAGE_SORT_LESSON_SUCCESS;
        model.sortLessons();
        expectedList = model.getFilteredLessonList();
        assertCommandSuccess(sortByModule, expectedMessage, expectedList);

    }

    @Test
    public void execute_sortInLessonList_sortListByLesson() {

        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);
        SortCommand sortByModule = new SortCommand();
        sortByModule.setData(model, new CommandHistory(), new UndoRedoStack());
        expectedMessage = SortCommand.MESSAGE_SORT_LESSON_SUCCESS;
        model.sortLessons();
        expectedList = model.getFilteredLessonList();
        assertCommandSuccess(sortByModule, expectedMessage, expectedList);

    }
```
###### /java/seedu/address/logic/commands/SortCommandTest.java
``` java
    @After
    public void wrapUp() {
        ListingUnit.setCurrentListingUnit(beginningListingUnit);
    }
}
```
###### /java/seedu/address/logic/parser/AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("MA1101R", "CS1010", "CS2100");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(keywords), command);
    }
```
###### /java/seedu/address/logic/parser/FindCommandParserTest.java
``` java
    @Test
    public void parse_validTrimmedKeywordsToList_returnsFindCommand() {
        List<String> keywordsInputs = new ArrayList<>();
        keywordsInputs.add("MA1101A");
        keywordsInputs.add("MA1101B");
        keywordsInputs.add("MA1101C");
        assertParseSuccess(parser, "MA1101A   MA1101B   MA1101C ", new FindCommand(keywordsInputs));
    }

}
```
###### /java/seedu/address/model/lesson/predicate/LessonContainsKeywordsPredicateTest.java
``` java
public class LessonContainsKeywordsPredicateTest {

    public static final ReadOnlyLesson LESSON = new LessonBuilder().build();
    public static final String ATTRIBUTE = "module";

    @Test
    public void equals() {

        List<String> keywordOne = new ArrayList<String>() {
        };
        List<String> keywordTwo = new ArrayList<String>() {
        };
        List<String> keywordNull = new ArrayList<String>() {
        };

        keywordOne.add("111");
        keywordOne.add("222");
        keywordOne.add("333");

        keywordTwo.add("aaa");
        keywordTwo.add("bbb");
        keywordTwo.add("bbb");

        LessonContainsKeywordsPredicate predicateOne = new LessonContainsKeywordsPredicate(keywordOne, LESSON,
                ATTRIBUTE);
        LessonContainsKeywordsPredicate predicateTwo = new LessonContainsKeywordsPredicate(keywordTwo, LESSON,
                ATTRIBUTE);
        LessonContainsKeywordsPredicate predicateNull = new LessonContainsKeywordsPredicate(keywordNull, LESSON,
                ATTRIBUTE);

        // same object -> returns true
        assertTrue(predicateOne.equals(predicateOne));

        // same values -> returns true
        LessonContainsKeywordsPredicate predicateTestOne = new LessonContainsKeywordsPredicate(keywordOne,
                LESSON, ATTRIBUTE);
        assertTrue(predicateOne.equals(predicateTestOne));

        // different types -> returns false
        assertFalse(predicateOne.equals(1));

        // null -> returns false
        assertFalse(predicateOne.equals(null));

        // different keywords -> returns false
        assertFalse(predicateOne.equals(predicateTwo));

        // one predicate with keywords compare with another predicate without keywords -> returns false
        assertFalse(predicateOne.equals(predicateNull));
    }

    @Test
    public void test_lessonAllAttributeContainsKeywords_returnsTrue() {
        // One keyword to find location
        LessonContainsKeywordsPredicate predicate = new LessonContainsKeywordsPredicate(Collections.singletonList(
                "LT27"), LESSON, ATTRIBUTE);
        assertTrue(predicate.test(new LessonBuilder().withLocation("LT27").build()));

        // One keyword to find group
        predicate = new LessonContainsKeywordsPredicate(Collections.singletonList(
                "12"), LESSON, ATTRIBUTE);
        assertTrue(predicate.test(new LessonBuilder().withGroup("12").build()));

        // One keyword to find class type
        predicate = new LessonContainsKeywordsPredicate(Collections.singletonList(
                "LEC"), LESSON, ATTRIBUTE);
        assertTrue(predicate.test(new LessonBuilder().withClassType("LEC").build()));

        // One keyword to find time slot
        predicate = new LessonContainsKeywordsPredicate(Collections.singletonList(
                "MON[1200-1300]"), LESSON, ATTRIBUTE);
        assertTrue(predicate.test(new LessonBuilder().withTimeSlot("MON[1200-1300]").build()));

        // Only one matching keyword to find location
        predicate = new LessonContainsKeywordsPredicate(Arrays.asList("LT26", "LT27"), LESSON,
                ATTRIBUTE);
        assertTrue(predicate.test(new LessonBuilder().withLocation("LT27").build()));

        // Only one matching keyword to find group
        predicate = new LessonContainsKeywordsPredicate(Arrays.asList("1", "2"), LESSON, ATTRIBUTE);
        assertTrue(predicate.test(new LessonBuilder().withGroup("1").build()));

        // Only one matching keyword to find class type
        predicate = new LessonContainsKeywordsPredicate(Arrays.asList("TUT", "LEC"), LESSON, ATTRIBUTE);
        assertTrue(predicate.test(new LessonBuilder().withClassType("LEC").build()));

        // Only one matching keyword to find time slot
        predicate = new LessonContainsKeywordsPredicate(Arrays.asList("MON[1200-1300]", "TUE[0900-1000]"),
                LESSON, ATTRIBUTE);
        assertTrue(predicate.test(new LessonBuilder().withTimeSlot("MON[1200-1300]").build()));

        // Mixed-case keywords to find location
        predicate = new LessonContainsKeywordsPredicate(Arrays.asList("Lt12"),
                LESSON, ATTRIBUTE);
        assertTrue(predicate.test(new LessonBuilder().withLocation("LT12").build()));

        // Mixed-case keywords to find class type
        predicate = new LessonContainsKeywordsPredicate(Arrays.asList("LeC"),
                LESSON, ATTRIBUTE);
        assertTrue(predicate.test(new LessonBuilder().withClassType("LEC").build()));

        // Mixed-case keywords to find time slot
        predicate = new LessonContainsKeywordsPredicate(Arrays.asList("mOn[1200-1300]"),
                LESSON, ATTRIBUTE);
        assertTrue(predicate.test(new LessonBuilder().withTimeSlot("MON[1200-1300]").build()));

        // partial keywords that is a substring of the location that user intend to find
        predicate = new LessonContainsKeywordsPredicate(Arrays.asList("LT"), LESSON, ATTRIBUTE);
        assertTrue(predicate.test(new LessonBuilder().withLocation("LT12").build()));

        // partial keywords that is a substring of the time slot that user intend to find
        predicate = new LessonContainsKeywordsPredicate(Arrays.asList("MON"), LESSON, ATTRIBUTE);
        assertTrue(predicate.test(new LessonBuilder().withTimeSlot("MON[1200-1300]").build()));

        // partial keywords that is a substring of the class type that user intend to find
        predicate = new LessonContainsKeywordsPredicate(Arrays.asList("T"), LESSON, ATTRIBUTE);
        assertTrue(predicate.test(new LessonBuilder().withClassType("TUT").build()));

    }

    @Test
    public void test_lessonAllAttributeDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        LessonContainsKeywordsPredicate predicate = new LessonContainsKeywordsPredicate(Collections.emptyList(),
                LESSON, ATTRIBUTE);
        assertFalse(predicate.test(LESSON));

        // Non-matching keyword for all attributes
        predicate = new LessonContainsKeywordsPredicate(Arrays.asList("COM1"), LESSON, ATTRIBUTE);
        assertFalse(predicate.test(LESSON));

    }

    @Test
    public void test_differentModuleCode_returnsFalse() {
        LessonContainsKeywordsPredicate predicate = new LessonContainsKeywordsPredicate(Arrays.asList("COM1"),
                LESSON, ATTRIBUTE);
        assertFalse(predicate.test(new LessonBuilder().withCode("CS2100").withLocation("LT22").withClassType("LEC")
                .withTimeSlot("MON[1200-1300]").withGroup("1").build()));
    }
}
```
###### /java/seedu/address/model/lesson/predicate/LocationContainsKeywordsPredicateTest.java
``` java
public class LocationContainsKeywordsPredicateTest {

    @Test
    public void equals() {

        List<String> keywordOne = new ArrayList<String>() {
        };
        List<String> keywordTwo = new ArrayList<String>() {
        };
        List<String> keywordNull = new ArrayList<String>() {
        };

        keywordOne.add("111");
        keywordOne.add("222");
        keywordOne.add("333");

        keywordTwo.add("aaa");
        keywordTwo.add("bbb");
        keywordTwo.add("bbb");


        LocationContainsKeywordsPredicate predicateOne = new LocationContainsKeywordsPredicate(keywordOne);
        LocationContainsKeywordsPredicate predicateTwo = new LocationContainsKeywordsPredicate(keywordTwo);
        LocationContainsKeywordsPredicate predicateNull = new LocationContainsKeywordsPredicate(keywordNull);

        // same object -> returns true
        assertTrue(predicateOne.equals(predicateOne));

        // same values -> returns true
        LocationContainsKeywordsPredicate predicateTestOne = new LocationContainsKeywordsPredicate(keywordOne);
        assertTrue(predicateOne.equals(predicateTestOne));

        // different types -> returns false
        assertFalse(predicateOne.equals(1));

        // null -> returns false
        assertFalse(predicateOne.equals(null));

        // different keywords -> returns false
        assertFalse(predicateOne.equals(predicateTwo));

        // one predicate with keywords compare with another predicate without keywords -> returns false
        assertFalse(predicateOne.equals(predicateNull));
    }

    @Test
    public void test_locationContainsKeywords_returnsTrue() {
        // One keyword
        LocationContainsKeywordsPredicate predicate = new LocationContainsKeywordsPredicate(Collections.singletonList(
                "LT27"));
        assertTrue(predicate.test(new LessonBuilder().withLocation("LT27").build()));

        // Only one matching keyword
        predicate = new LocationContainsKeywordsPredicate(Arrays.asList("LT26", "LT27"));
        assertTrue(predicate.test(new LessonBuilder().withLocation("LT27").build()));

        // Mixed-case keywords
        predicate = new LocationContainsKeywordsPredicate(Arrays.asList("lT27", "Lt28"));
        assertTrue(predicate.test(new LessonBuilder().withLocation("LT27").build()));

        // partial keywords that is a substring of the moduleCode
        predicate = new LocationContainsKeywordsPredicate(Arrays.asList("28"));
        assertTrue(predicate.test(new LessonBuilder().withLocation("LT28").build()));

    }

    @Test
    public void test_locationDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        LocationContainsKeywordsPredicate predicate = new LocationContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new LessonBuilder().withLocation("LT27").build()));

        // Non-matching keyword
        predicate = new LocationContainsKeywordsPredicate(Arrays.asList("COM1"));
        assertFalse(predicate.test(new LessonBuilder().withLocation("LT22").build()));

    }

}
```
###### /java/seedu/address/model/lesson/predicate/MarkedLessonsContainsKeywordsPredicateTest.java
``` java
public class MarkedLessonsContainsKeywordsPredicateTest {

    @Test
    public void equals() {

        List<String> keywordOne = new ArrayList<String>() {
        };
        List<String> keywordTwo = new ArrayList<String>() {
        };
        List<String> keywordNull = new ArrayList<String>() {
        };

        keywordOne.add("111");
        keywordOne.add("222");
        keywordOne.add("333");

        keywordTwo.add("aaa");
        keywordTwo.add("bbb");
        keywordTwo.add("bbb");

        MarkedLessonContainsKeywordsPredicate predicateOne = new MarkedLessonContainsKeywordsPredicate(keywordOne);
        MarkedLessonContainsKeywordsPredicate predicateTwo = new MarkedLessonContainsKeywordsPredicate(keywordTwo);
        MarkedLessonContainsKeywordsPredicate predicateNull = new MarkedLessonContainsKeywordsPredicate(keywordNull);

        // same object -> returns true
        assertTrue(predicateOne.equals(predicateOne));

        // same values -> returns true
        MarkedLessonContainsKeywordsPredicate predicateTestOne = new MarkedLessonContainsKeywordsPredicate(keywordOne);
        assertTrue(predicateOne.equals(predicateTestOne));

        // different types -> returns false
        assertFalse(predicateOne.equals(1));

        // null -> returns false
        assertFalse(predicateOne.equals(null));

        // different keywords -> returns false
        assertFalse(predicateOne.equals(predicateTwo));

        // one predicate with keywords compare with another predicate without keywords -> returns false
        assertFalse(predicateOne.equals(predicateNull));
    }

    @Test
    public void test_markedLessonsAllAttributeContainsKeywords_returnsTrue() {
        // One keyword to find location
        MarkedLessonContainsKeywordsPredicate predicate =
                new MarkedLessonContainsKeywordsPredicate(Collections.singletonList("LT27"));
        assertTrue(predicate.test(new LessonBuilder().withLocation("LT27").withMarked().build()));

        // One keyword to find group
        predicate = new MarkedLessonContainsKeywordsPredicate(Collections.singletonList("12"));
        assertTrue(predicate.test(new LessonBuilder().withGroup("12").withMarked().build()));

        // One keyword to find class type
        predicate = new MarkedLessonContainsKeywordsPredicate(Collections.singletonList("LEC"));
        assertTrue(predicate.test(new LessonBuilder().withClassType("LEC").withMarked().build()));

        // One keyword to find time slot
        predicate = new MarkedLessonContainsKeywordsPredicate(Collections.singletonList("MON[1200-1300]"));
        assertTrue(predicate.test(new LessonBuilder().withTimeSlot("MON[1200-1300]").withMarked().build()));

        // Only one matching keyword to find location
        predicate = new MarkedLessonContainsKeywordsPredicate(Arrays.asList("LT26", "LT27"));
        assertTrue(predicate.test(new LessonBuilder().withLocation("LT27").withMarked().build()));

        // Only one matching keyword to find group
        predicate = new MarkedLessonContainsKeywordsPredicate(Arrays.asList("1", "2"));
        assertTrue(predicate.test(new LessonBuilder().withGroup("1").withMarked().build()));

        // Only one matching keyword to find class type
        predicate = new MarkedLessonContainsKeywordsPredicate(Arrays.asList("TUT", "LEC"));
        assertTrue(predicate.test(new LessonBuilder().withClassType("TUT").withMarked().build()));

        // Only one matching keyword to find time slot
        predicate = new MarkedLessonContainsKeywordsPredicate(Arrays.asList("MON[1200-1300]", "TUE[0900-1000]"));
        assertTrue(predicate.test(new LessonBuilder().withTimeSlot("MON[1200-1300]").withMarked().build()));

        // Mixed-case keywords to find location
        predicate = new MarkedLessonContainsKeywordsPredicate(Arrays.asList("Lt12"));
        assertTrue(predicate.test(new LessonBuilder().withLocation("LT12").withMarked().build()));

        // Mixed-case keywords to find class type
        predicate = new MarkedLessonContainsKeywordsPredicate(Arrays.asList("LeC"));
        assertTrue(predicate.test(new LessonBuilder().withClassType("LEC").withMarked().build()));

        // Mixed-case keywords to find time slot
        predicate = new MarkedLessonContainsKeywordsPredicate(Arrays.asList("mOn[1200-1300]"));
        assertTrue(predicate.test(new LessonBuilder().withTimeSlot("MON[1200-1300]").withMarked().build()));

        // partial keywords that is a substring of the location that user intend to find
        predicate = new MarkedLessonContainsKeywordsPredicate(Arrays.asList("LT"));
        assertTrue(predicate.test(new LessonBuilder().withLocation("LT12").withMarked().build()));

        // partial keywords that is a substring of the time slot that user intend to find
        predicate = new MarkedLessonContainsKeywordsPredicate(Arrays.asList("MON"));
        assertTrue(predicate.test(new LessonBuilder().withTimeSlot("MON[1200-1300]").withMarked().build()));

        // partial keywords that is a substring of the class type that user intend to find
        predicate = new MarkedLessonContainsKeywordsPredicate(Arrays.asList("T"));
        assertTrue(predicate.test(new LessonBuilder().withClassType("TUT").withMarked().build()));

    }

    @Test
    public void test_markedLessonDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        MarkedLessonContainsKeywordsPredicate predicate =
                new MarkedLessonContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test((new LessonBuilder().withMarked().build())));

        // Non-matching keyword for all attributes
        predicate = new MarkedLessonContainsKeywordsPredicate(Arrays.asList("COM1"));
        assertFalse(predicate.test(new LessonBuilder().withMarked().build()));

    }

    @Test
    public void test_unmarkedLesson_returnsFalse() {
        MarkedLessonContainsKeywordsPredicate predicate =
                new MarkedLessonContainsKeywordsPredicate(Arrays.asList("LEC"));
        assertFalse(predicate.test(new LessonBuilder().withUnmarked().build()));
    }
}
```
###### /java/seedu/address/model/lesson/predicate/ModuleContainsKeywordsPredicateTest.java
``` java
public class ModuleContainsKeywordsPredicateTest {

    @Test
    public void equals() {

        List<String> keywordOne = new ArrayList<String>() {
        };
        List<String> keywordTwo = new ArrayList<String>() {
        };
        List<String> keywordNull = new ArrayList<String>() {
        };

        keywordOne.add("111");
        keywordOne.add("222");
        keywordOne.add("333");

        keywordTwo.add("aaa");
        keywordTwo.add("bbb");
        keywordTwo.add("bbb");


        ModuleContainsKeywordsPredicate predicateOne = new ModuleContainsKeywordsPredicate(keywordOne);
        ModuleContainsKeywordsPredicate predicateTwo = new ModuleContainsKeywordsPredicate(keywordTwo);
        ModuleContainsKeywordsPredicate predicateNull = new ModuleContainsKeywordsPredicate(keywordNull);

        // same object -> returns true
        assertTrue(predicateOne.equals(predicateOne));

        // same values -> returns true
        ModuleContainsKeywordsPredicate predicateTestOne = new ModuleContainsKeywordsPredicate(keywordOne);
        assertTrue(predicateOne.equals(predicateTestOne));

        // different types -> returns false
        assertFalse(predicateOne.equals(1));

        // null -> returns false
        assertFalse(predicateOne.equals(null));

        // different keywords -> returns false
        assertFalse(predicateOne.equals(predicateTwo));

        // one predicate with keywords compare with another predicate without keywords -> returns false
        assertFalse(predicateOne.equals(predicateNull));
    }

    @Test
    public void test_moduleCodeContainsKeywords_returnsTrue() {
        // One keyword
        ModuleContainsKeywordsPredicate predicate = new ModuleContainsKeywordsPredicate(Collections.singletonList(
                "MA1101R"));
        assertTrue(predicate.test(new LessonBuilder().withCode("MA1101R").build()));

        // Only one matching keyword
        predicate = new ModuleContainsKeywordsPredicate(Arrays.asList("MA1101", "MA1101R"));
        assertTrue(predicate.test(new LessonBuilder().withCode("MA1101R").build()));

        // Mixed-case keywords
        predicate = new ModuleContainsKeywordsPredicate(Arrays.asList("ma1101r", "Ma1101R"));
        assertTrue(predicate.test(new LessonBuilder().withCode("MA1101R").build()));

        // partial keywords that is a substring of the moduleCode
        predicate = new ModuleContainsKeywordsPredicate(Arrays.asList("MA1101"));
        assertTrue(predicate.test(new LessonBuilder().withCode("MA1101Z").build()));

    }

    @Test
    public void test_moduleCodeDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        ModuleContainsKeywordsPredicate predicate = new ModuleContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new LessonBuilder().withCode("MA1101R").build()));

        // Non-matching keyword
        predicate = new ModuleContainsKeywordsPredicate(Arrays.asList("MA1101"));
        assertFalse(predicate.test(new LessonBuilder().withCode("CS2010").build()));

    }
}

```
