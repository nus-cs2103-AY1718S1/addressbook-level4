# zacharytang
###### \java\seedu\address\commons\events\ui\PersonSelectedEvent.java
``` java
/**
 * Represents a person being selected
 */
public class PersonSelectedEvent extends BaseEvent {

    public final ReadOnlyPerson person;

    public PersonSelectedEvent(ReadOnlyPerson person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "Person selected: " + person.getName().toString();
    }
}
```
###### \java\seedu\address\commons\events\ui\TimetableDisplayEvent.java
``` java
/**
 * Represents a request to display timetables in the UI
 */
public class TimetableDisplayEvent extends BaseEvent {

    public final List<ReadOnlyPerson> personsToDisplay;

    public TimetableDisplayEvent(List<ReadOnlyPerson> personList) {
        this.personsToDisplay = personList;
    }

    @Override
    public String toString() {
        StringBuilder msg = new StringBuilder();

        msg.append("Timetables displayed for the selected people: ");

        for (ReadOnlyPerson person : personsToDisplay) {
            msg.append("[");
            msg.append(person.getName().toString());
            msg.append("] ");
        }

        return msg.toString();
    }
}
```
###### \java\seedu\address\commons\exceptions\NoInternetConnectionException.java
``` java
/**
 * Signals that there is no internet connection for reading from web APIs
 */
public class NoInternetConnectionException extends Exception {
    public NoInternetConnectionException(String message) {
        super(message);
    }
}
```
###### \java\seedu\address\commons\util\timetable\Lesson.java
``` java
/**
 * Represents a lesson that a module has
 */
public class Lesson {

    private final String classNo;
    private final String lessonType;
    private final String weekType;
    private final String day;
    private final String startTime;
    private final String endTime;

    public Lesson(String classNo, String lessonType, String weekType,
                  String day, String startTime, String endTime) {
        this.classNo = classNo;
        this.lessonType = lessonType;
        this.weekType = weekType;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getClassNo() {
        return classNo;
    }

    public String getLessonType() {
        return lessonType;
    }

    public String getWeekType() {
        return weekType;
    }

    public String getDay() {
        return day;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
}
```
###### \java\seedu\address\commons\util\timetable\ModuleInfoFromUrl.java
``` java
/**
 * Represents lesson for a specific module parsed from a NUSMods url
 */
public class ModuleInfoFromUrl {

    private String modCode;
    private HashMap<String, String> lessonInfo;

    public ModuleInfoFromUrl(String modCode) {
        this.modCode = modCode;
        lessonInfo = new HashMap<>();
    }

    /**
     * Adds information for a specific lesson
     */
    public void addLesson(String classType, String classNo) {
        lessonInfo.put(classType, classNo);

    }

    public String getModCode() {
        return modCode;
    }

    public HashMap<String, String> getLessonInfo() {
        return lessonInfo;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {

            return true;
        }

        if (!(other instanceof ModuleInfoFromUrl)) {
            return false;
        }

        return this.modCode.equals(((ModuleInfoFromUrl) other).getModCode());
    }
}
```
###### \java\seedu\address\commons\util\timetable\TimetableInfoFromUrl.java
``` java
/**
 * Represents all timetable information parsed from a NUSMods url
 */
public class TimetableInfoFromUrl {

    private ArrayList<ModuleInfoFromUrl> moduleInfo;

    public TimetableInfoFromUrl() {
        moduleInfo = new ArrayList<>();
    }

    public ArrayList<ModuleInfoFromUrl> getModuleInfoList() {
        return moduleInfo;
    }

    /**
     * Gets lessons for a specific module code. If module does not exist, creates a new ModuleInfoFromUrl object
     */
    public ModuleInfoFromUrl getModuleInfo(String moduleCodeToGet) {
        for (ModuleInfoFromUrl currentModule : moduleInfo) {
            if (currentModule.getModCode().equals(moduleCodeToGet)) {
                return currentModule;
            }
        }

        return new ModuleInfoFromUrl(moduleCodeToGet);
    }

    /**
     * Adds lesson information for a specific module to the timetable information.
     */
    public void addModuleInfo(ModuleInfoFromUrl moduleInfoToAdd) {
        if (moduleInfo.contains(moduleInfoToAdd)) {
            moduleInfo.set(moduleInfo.indexOf(moduleInfoToAdd), moduleInfoToAdd);
        } else {
            moduleInfo.add(moduleInfoToAdd);
        }
    }
}
```
###### \java\seedu\address\commons\util\timetable\TimetableParserUtil.java
``` java
/**
 * Helper class that contains utilities to parse NUSMods urls.
 */
public class TimetableParserUtil {

    public static final String MESSAGE_INVALID_CLASS_TYPE = "Invalid class type provided!";
    public static final String MESSAGE_INVALID_WEEK_TYPE = "Invald week type!";
    public static final String MESSAGE_INVALID_TIME = "Invalid timing provided!";
    public static final String MESSAGE_INVALID_DAY = "Invalid day provided";

    private static final int INDEX_ACAD_YEAR = 4;
    private static final int INDEX_MODULE_INFO = 5;
    private static final int INDEX_SEMESTER_INFO = 0;
    private static final int INDEX_CLASS_INFO = 1;

    private static final String SPLIT_BACKWARDS_SLASH = "/";
    private static final String SPLIT_QUESTION_MARK = "\\?";
    private static final String SPLIT_AMPERSAND = "&";
    private static final String SPLIT_EQUALS_SIGN = "=";
    private static final String SPLIT_LEFT_SQAURE_BRACKET = "%5B";
    private static final String SPLIT_RIGHT_SQUARE_BRACKET = "%5D";

    /**
     * Takes in a valid timetable URL and attempts to parse it
     */
    public static TimetableInfo parseUrl(String url) throws IllegalValueException {
        if (!isValidUrl(url)) {
            throw new IllegalValueException(MESSAGE_TIMETABLE_URL_CONSTRAINTS);
        }

        try {
            String newUrl = expandUrl(url);
            return parseLongUrl(newUrl);
        } catch (IOException e) {
            throw new ParseException("Url cannot be accessed");
        } catch (NoInternetConnectionException nice) {
            return new TimetableInfo();
        }
    }

    /**
     * Parses a full expanded NUSMods url
     */
    private static TimetableInfo parseLongUrl(String url) throws IllegalValueException {
        String acadYear;
        String semester;

        String[] splitUrl = url.split(SPLIT_BACKWARDS_SLASH);

        acadYear = splitUrl[INDEX_ACAD_YEAR];
        String toParse = splitUrl[INDEX_MODULE_INFO];
        String[] modInfo = toParse.split(SPLIT_QUESTION_MARK);

        if (modInfo.length != 2) {
            return new TimetableInfo();
        }

        semester = modInfo[INDEX_SEMESTER_INFO].substring(3);

        TimetableInfoFromUrl timetableInfo = parseModuleInfo(modInfo[INDEX_CLASS_INFO]);

        try {
            return constructTimetable(acadYear, semester, timetableInfo);
        } catch (JsonMappingException e) {
            return new TimetableInfo();
        }
    }

    /**
     * Parses a string of module info from an expanded NUSMods link
     * Returns a hashmap where module code is the key, and values are a hashmap of class types and class numbers
     */
    private static TimetableInfoFromUrl parseModuleInfo(String modInfoFromString) {
        TimetableInfoFromUrl modules = new TimetableInfoFromUrl();
        String[] classes = modInfoFromString.split(SPLIT_AMPERSAND);

        // Split a string of format "MODULE_CODE[CLASS_TYPE]=CLASS_NO"
        for (String classInfo : classes) {
            // Checks if class info exists
            if (classInfo.split(SPLIT_LEFT_SQAURE_BRACKET).length != 2) {
                continue;
            }

            String moduleCode = classInfo.split(SPLIT_LEFT_SQAURE_BRACKET)[0];
            String classType = classInfo.split(SPLIT_LEFT_SQAURE_BRACKET)[1].split(SPLIT_RIGHT_SQUARE_BRACKET)[0];
            String classNo = classInfo.split(SPLIT_EQUALS_SIGN)[1];

            ModuleInfoFromUrl moduleInfo = modules.getModuleInfo(moduleCode);
            moduleInfo.addLesson(classType, classNo);
            modules.addModuleInfo(moduleInfo);
        }

        return modules;
    }

    /**
     * Uses NUSMods API to obtain all classes a module has, and returns it in
     * an arraylist of classes. Each class is represented by a hash map, storing the information about the class
     * @return list of all lessons a module has
     */
    private static ArrayList<Lesson> getLessonInfoFromApi(String acadYear, String semester, String modCode)
            throws ParseException, JsonMappingException {
        String uri = "http://api.nusmods.com/" + acadYear + "/" + semester + "/modules/" + modCode + ".json";
        ObjectMapper mapper = new ObjectMapper();

        try {
            URL url = new URL(uri);
            @SuppressWarnings("unchecked")
            Map<String, Object> mappedJson = mapper.readValue(url, HashMap.class);
            @SuppressWarnings("unchecked")
            ArrayList<HashMap<String, String>> lessonInfo = (ArrayList<HashMap<String, String>>)
                    mappedJson.get("Timetable");

            ArrayList<Lesson> lessons = new ArrayList<>();
            for (HashMap<String, String> lesson : lessonInfo) {
                Lesson lessonToAdd = new Lesson(lesson.get("ClassNo"), lesson.get("LessonType"),
                        lesson.get("WeekText"), lesson.get("DayText"),
                        lesson.get("StartTime"), lesson.get("EndTime"));
                lessons.add(lessonToAdd);
            }

            return lessons;
        } catch (IOException exception) {
            throw new ParseException("Cannot retrieve module information");
        }
    }

    /**
     * Takes a shortened URL and returns the full length URL as a string
     */
    private static String expandUrl(String shortenedUrl)
            throws IOException, ParseException, NoInternetConnectionException {
        URL url = new URL(shortenedUrl);
        // open connection
        HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection(Proxy.NO_PROXY);

        // stop following browser redirect
        httpUrlConnection.setInstanceFollowRedirects(false);

        // extract location header containing the actual destination URL
        String expandedUrl = httpUrlConnection.getHeaderField("Location");
        httpUrlConnection.disconnect();

        if (expandedUrl == null) {
            throw new NoInternetConnectionException("No internet connection, starting with empty timetables");
        } else if (expandedUrl.equals("http://modsn.us")) {
            throw new ParseException(MESSAGE_INVALID_SHORT_URL);
        }

        return expandedUrl;
    }

    /**
     * Constructs a timetable array using information parsed from a url
     * @param acadYear academic year
     * @param semester semester
     * @param timetableInfo Class information parsed from url, stored by module code
     */
    private static TimetableInfo constructTimetable(String acadYear, String semester,
                                                    TimetableInfoFromUrl timetableInfo)
            throws IllegalValueException, JsonMappingException {
        TimetableInfo timetable = new TimetableInfo();

        ArrayList<ModuleInfoFromUrl> lessonInfoByModules = timetableInfo.getModuleInfoList();

        for (ModuleInfoFromUrl moduleInfoFromTimetable : lessonInfoByModules) {
            constructTimetableForModule(acadYear, semester, timetable, moduleInfoFromTimetable);
        }

        return timetable;
    }

    /**
     * Adds all lessons for a specific module found in the timetable information parsed from URL
     * Timings for every lesson from a specific module will be added to the timetable information
     * @param acadYear academic year
     * @param semester semester
     * @param timetable timetable to be updated
     * @param moduleInfo lessons for a module parsed from url
     */
    private static void constructTimetableForModule(String acadYear, String semester, TimetableInfo timetable,
                                                    ModuleInfoFromUrl moduleInfo)
            throws IllegalValueException, JsonMappingException {
        ArrayList<Lesson> lessons = getLessonInfoFromApi(acadYear, semester, moduleInfo.getModCode());
        HashMap<String, String> lessonsForModule = moduleInfo.getLessonInfo();

        for (String classType : lessonsForModule.keySet()) {
            String classNo = lessonsForModule.get(classType);

            addLessonToTimetable(timetable, lessons, classType, classNo);
        }
    }

    /**
     * Finds the timings for a lesson in the URL parsed info, and adds it to the timetable
     * @param timetable to be returned
     * @param lessons list of all lessons for a module
     * @param classType type of class parsed
     * @param classNo identifier for class parsed
     */
    private static void addLessonToTimetable(TimetableInfo timetable, ArrayList<Lesson> lessons, String classType,
                                             String classNo) throws IllegalValueException {
        for (Lesson lesson : lessons) {
            if (lessonExistsInParsedInfo(classType, classNo, lesson)) {
                timetable.updateSlotsWithLesson(lesson.getWeekType(), lesson.getDay(), lesson.getStartTime(),
                        lesson.getEndTime());
            }
        }
    }

    /**
     * Checks if lesson info that was parsed from URL is equivalent to a lesson
     */
    private static boolean lessonExistsInParsedInfo(String classType, String classNo, Lesson lesson)
            throws IllegalValueException {
        return parseSlotType(classType).equals(lesson.getLessonType()) && classNo.equals(lesson.getClassNo());
    }

    /* ------------------------------------------- Helper methods ----------------------------------------------------*/

    /**
     * Converts string representing day of class to integer representation
     * Returns -1 if day cannot be found;
     */
    public static int parseDay(String day) throws IllegalValueException {
        switch (day) {
        case "Monday":
            return DAY_MONDAY;

        case "Tuesday":
            return DAY_TUESDAY;

        case "Wednesday":
            return DAY_WEDNESDAY;

        case "Thursday":
            return DAY_THURSDAY;

        case "Friday":
            return DAY_FRIDAY;

        default:
            throw new IllegalValueException(MESSAGE_INVALID_DAY);
        }
    }

    /**
     * Takes in String representing start/end timing of lessons, and returns respective index to be used for array
     */
    public static int parseStartEndTime(String timing) throws IllegalValueException {
        try {
            return (int) Math.ceil(((Integer.parseInt(timing) - 800) * 2) / 100.0);
        } catch (NumberFormatException e) {
            throw new IllegalValueException(MESSAGE_INVALID_TIME);
        }
    }

    /**
     * Converts shortened slot type in URL to full slot-type string used in API
     */
    public static String parseSlotType(String slotType) throws IllegalValueException {
        switch (slotType) {
        case "LEC":
            return "Lecture";

        case "TUT":
            return "Tutorial";

        case "LAB":
            return "Laboratory";

        case "SEM":
            return "Seminar-Style Module Class";

        case "SEC":
            return "Sectional Teaching";

        case "REC":
            return "Recitation";

        case "TUT2":
            return "Tutorial Type 2";

        case "TUT3":
            return "Tutorial Type 3";

        default:
            throw new IllegalValueException(MESSAGE_INVALID_CLASS_TYPE);
        }
    }

    /**
     * Converts week type from string used in api to integer index for use in URL
     */
    public static int parseWeekType(String weekType) throws IllegalValueException {
        switch (weekType) {
        case "Odd Week":
            return WEEK_ODD;
        case "Even Week":
            return WEEK_EVEN;
        case "Every Week":
            return WEEK_BOTH;
        default:
            throw new IllegalValueException(MESSAGE_INVALID_WEEK_TYPE);
        }
    }
}
```
###### \java\seedu\address\logic\commands\TimetableCommand.java
``` java
/**
 * Selects persons identified using their last displayed indexes and displays a combined timetable
 * of the selected persons
 */
public class TimetableCommand extends Command {

    public static final String COMMAND_WORD = "whenfree";
    public static final String COMMAND_ALIAS = "wf";
    public static final String COMMAND_SECONDARY = "timetable";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays a combined timetable of persons, identified using their last displayed indexes\n"
            + "Parameters: INDEX (must be positive integers)\n"
            + "Example: " + COMMAND_WORD + " 1\n"
            + "         " + COMMAND_WORD + " 1, 2, 3\n"
            + "         " + COMMAND_WORD + " 2 4";

    public static final String MESSAGE_DISPLAY_SUCCESS = "Displayed timetables: ";

    private final ArrayList<Index> targetIndexes;

    public TimetableCommand(ArrayList<Index> targetIndexes) {
        this.targetIndexes = targetIndexes;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        List<ReadOnlyPerson> personsToDisplay = new ArrayList<>();

        for (Index index : targetIndexes) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            ReadOnlyPerson personSelected = lastShownList.get(index.getZeroBased());
            personsToDisplay.add(personSelected);
        }

        EventsCenter.getInstance().post(new TimetableDisplayEvent(personsToDisplay));
        return new CommandResult(generateResultMsg(personsToDisplay));
    }

    /**
     * Generates the success message for the timetable command
     */
    private String generateResultMsg(List<ReadOnlyPerson> personList) {
        StringBuilder msg = new StringBuilder();

        msg.append(MESSAGE_DISPLAY_SUCCESS);

        for (ReadOnlyPerson person : personList) {
            msg.append("[");
            msg.append(person.getName().toString());
            msg.append("] ");
        }

        return msg.toString();
    }
}
```
###### \java\seedu\address\logic\parser\TimetableCommandParser.java
``` java

/**
 * Parses input arguments and creates a new TimetableCommand object
 */
public class TimetableCommandParser implements Parser<TimetableCommand> {

    public static final String DISPLAY_ONE_PERSON_VALIDATION_REGEX = "-?\\d+";

    public static final String DISPLAY_MULTIPLE_PERSON_COMMA_VALIDATION_REGEX =
            "((-?\\d([\\s+]*)\\,([\\s+]*)(?=-?\\d))|-?\\d)+";

    public static final String DISPLAY_MULTIPLE_PERSON_WHITESPACE_VALIDATION_REGEX =
            "(((-?\\d)([\\s]+)(?=-?\\d))|-?\\d)+";

    /**
     * Parses the given {@code String} of arguments in the context of the TimetableCommand
     * and returns a TimetableCommand object for execution
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public TimetableCommand parse(String args) throws ParseException {

        String preamble = ArgumentTokenizer.tokenize(args).getPreamble();

        if (preamble.matches(DISPLAY_ONE_PERSON_VALIDATION_REGEX)) {
            try {
                ArrayList<Index> indexList = new ArrayList<>();
                Index index = ParserUtil.parseIndex(args);
                indexList.add(index);

                return new TimetableCommand(indexList);
            } catch (IllegalValueException ive) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, TimetableCommand.MESSAGE_USAGE));
            }
        } else if (preamble.matches(DISPLAY_MULTIPLE_PERSON_COMMA_VALIDATION_REGEX)) {
            try {
                ArrayList<Index> indexesList = ParserUtil.parseIndexes(args, ",");
                return new TimetableCommand(indexesList);
            } catch (IllegalValueException ive) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, TimetableCommand.MESSAGE_USAGE));
            }
        } else if (preamble.matches(DISPLAY_MULTIPLE_PERSON_WHITESPACE_VALIDATION_REGEX)) {
            try {
                ArrayList<Index> indexesList = ParserUtil.parseIndexes(args, " ");
                return new TimetableCommand(indexesList);
            } catch (IllegalValueException ive) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, TimetableCommand.MESSAGE_USAGE));
            }
        }

        throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TimetableCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\model\person\timetable\Timetable.java
``` java
/**
 * Represents a person's timetable in the address book
 * Guarantees: Immutable
 */
public class Timetable {

    public static final int WEEK_ODD = 0;
    public static final int WEEK_EVEN = 1;
    public static final int WEEK_BOTH = -1;

    public static final int DAY_MONDAY = 0;
    public static final int DAY_TUESDAY = 1;
    public static final int DAY_WEDNESDAY = 2;
    public static final int DAY_THURSDAY = 3;
    public static final int DAY_FRIDAY = 4;

    public static final String MESSAGE_TIMETABLE_URL_CONSTRAINTS =
            "Timetable URLs should be a valid shortened NUSMods URL";
    public static final String MESSAGE_INVALID_SHORT_URL = "Invalid shortened URL provided";

    public static final String[] ARRAY_DAYS = {
        "Monday",
        "Tuesday",
        "Wednesday",
        "Thursday",
        "Friday"
    };

    public static final String[] ARRAY_WEEKS = {
        "Odd Week",
        "Even Week"
    };

    public static final String[] ARRAY_TIMES = {
        "0800", "0830", "0900", "0930", "1000", "1030", "1100", "1130",
        "1200", "1230", "1300", "1330", "1400", "1430", "1500", "1530",
        "1600", "1630", "1700", "1730", "1800", "1830", "1900", "1930",
        "2000", "2030", "2100", "2130", "2200", "2230", "2300", "2330"
    };

    private static final String NUSMODS_SHORT = "modsn.us";
    private static final String URL_HOST_REGEX = "\\/\\/.*?\\/";

    public final String value;

    private final TimetableInfo timetable;

    public Timetable(String url) throws IllegalValueException {
        requireNonNull(url);
        String trimmedUrl = url.trim();
        if (trimmedUrl == "") {
            trimmedUrl = "http://modsn.us/5tN3z";
        }
        if (!isValidUrl(trimmedUrl)) {
            throw new IllegalValueException(MESSAGE_TIMETABLE_URL_CONSTRAINTS);
        }
        this.value = trimmedUrl;
        this.timetable = parseUrl(trimmedUrl);
    }

    /**
     * Returns if a url is a valid NUSMods url
     */
    public static boolean isValidUrl(String test) {
        Matcher matcher = Pattern.compile(URL_HOST_REGEX).matcher(test);
        if (!matcher.find()) {
            return false;
        }

        String hostName = matcher.group()
                .substring(2, matcher.group().length() - 1);

        return hostName.equals(NUSMODS_SHORT);
    }

    /**
     * Checks if a timeslot specified has a lesson
     */
    public boolean doesSlotHaveLesson(String weekType, String day, String timing) throws IllegalValueException {
        return timetable.doesSlotHaveLesson(weekType, day, timing);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Timetable // instanceof handles nulls
                && this.value.equals(((Timetable) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\person\timetable\TimetableDay.java
``` java
/**
 * Represents a single day in a timetable
 */
public class TimetableDay {

    private static final int ARRAY_NUM_TIMESLOTS = 32;

    private TimetableSlot[] slots;

    public TimetableDay() {
        slots = new TimetableSlot[ARRAY_NUM_TIMESLOTS];
        for (int i = 0; i < ARRAY_NUM_TIMESLOTS; i++) {
            slots[i] = new TimetableSlot();
        }
    }

    /**
     * Sets all slots between two timings to have lessons
     */
    public void updateSlotsWithLesson(String startTime, String endTime) throws IllegalValueException {
        int startTimeIndex = parseStartEndTime(startTime);
        int endTimeIndex = parseStartEndTime(endTime);

        for (int i = startTimeIndex; i < endTimeIndex; i++) {
            slots[i].setLesson();
        }
    }

    public boolean doesSlotHaveLesson(String timing) throws IllegalValueException {
        return slots[parseStartEndTime(timing)].hasLesson();
    }

    private TimetableSlot getTimetableSlot(int index) {
        return slots[index];
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TimetableDay)) {
            return false;
        }

        for (int i = 0; i < ARRAY_NUM_TIMESLOTS; i++) {
            if (!this.slots[i].equals(((TimetableDay) other).getTimetableSlot(i))) {
                return false;
            }
        }

        return true;
    }
}
```
###### \java\seedu\address\model\person\timetable\TimetableInfo.java
``` java
/**
 * Fully represents all information about a person's timetable slots
 */
public class TimetableInfo {

    public static final int ARRAY_NUM_EVEN_ODD = 2;

    private TimetableWeek[] timetable;

    public TimetableInfo() {
        timetable = new TimetableWeek[ARRAY_NUM_EVEN_ODD];
        for (int i = 0; i < ARRAY_NUM_EVEN_ODD; i++) {
            timetable[i] = new TimetableWeek();
        }
    }

    /**
     * Checks if a specific slot has a lesson
     */
    public boolean doesSlotHaveLesson(String weekType, String day, String timing) throws IllegalValueException {
        int weekIndex = parseWeekType(weekType);
        if (weekIndex != WEEK_ODD && weekIndex != WEEK_EVEN) {
            throw new IllegalValueException("Please specify a week type!");
        }
        return timetable[weekIndex].doesSlotHaveLesson(day, timing);
    }

    /**
     * Updates the timetable using lesson information provided
     */
    public void updateSlotsWithLesson(String weekType, String day, String startTime, String endTime)
            throws IllegalValueException {
        int weekIndex = parseWeekType(weekType);
        if (weekIndex == WEEK_BOTH) {
            timetable[WEEK_ODD].updateSlotsWithLesson(day, startTime, endTime);
            timetable[WEEK_EVEN].updateSlotsWithLesson(day, startTime, endTime);
        } else {
            timetable[weekIndex].updateSlotsWithLesson(day, startTime, endTime);
        }
    }

    private TimetableWeek getWeek(int weekIndex) {
        return timetable[weekIndex];
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TimetableInfo)) {
            return false;
        }

        for (int i = 0; i < ARRAY_NUM_EVEN_ODD; i++) {
            if (!this.timetable[i].equals(((TimetableInfo) other).getWeek(i))) {
                return false;
            }
        }

        return true;
    }
}
```
###### \java\seedu\address\model\person\timetable\TimetableSlot.java
``` java
/**
 * Represents a single slot in a timetable
 */
public class TimetableSlot {

    private boolean hasLesson;

    public TimetableSlot() {
        this.hasLesson = false;
    }

    public void setLesson() {
        hasLesson = true;
    }

    public boolean hasLesson() {
        return hasLesson;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TimetableSlot // instanceof handles nulls
                && this.hasLesson == ((TimetableSlot) other).hasLesson());
    }
}
```
###### \java\seedu\address\model\person\timetable\TimetableWeek.java
``` java
/**
 * Represents a full timetable for a week
 */
public class TimetableWeek {

    private static final int ARRAY_NUM_DAYS = 5;

    private TimetableDay[] days;

    public TimetableWeek() {
        days = new TimetableDay[ARRAY_NUM_DAYS];
        for (int i = 0; i < ARRAY_NUM_DAYS; i++) {
            days[i] = new TimetableDay();
        }
    }

    public boolean doesSlotHaveLesson(String day, String timing) throws IllegalValueException {
        return days[parseDay(day)].doesSlotHaveLesson(timing);
    }

    public void updateSlotsWithLesson(String day, String startTime, String endTime) throws IllegalValueException {
        days[parseDay(day)].updateSlotsWithLesson(startTime, endTime);
    }

    private TimetableDay getDay(int dayIndex) {
        return days[dayIndex];
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TimetableWeek)) {
            return false;
        }

        for (int i = 0; i < ARRAY_NUM_DAYS; i++) {
            if (!this.days[i].equals(((TimetableWeek) other).getDay(i))) {
                return false;
            }
        }

        return true;
    }
}
```
###### \java\seedu\address\ui\InfoPanel.java
``` java
/**
 * Container for both browser panel and person information panel
 */
public class InfoPanel extends UiPart<Region> {

    private static final String FXML = "InfoPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private BrowserPanel browserPanel;
    private TimetableDisplay timetableDisplay;

    @FXML
    private StackPane browserPlaceholder;

    @FXML
    private StackPane timetablePlaceholder;

    public InfoPanel() {
        super(FXML);

        browserPanel = new BrowserPanel();
        browserPlaceholder.getChildren().add(browserPanel.getRoot());

        timetableDisplay = new TimetableDisplay(null);
        timetablePlaceholder.getChildren().add(timetableDisplay.getRoot());

        timetablePlaceholder.toFront();
        registerAsAnEventHandler(this);
    }

    public void freeResources() {
        browserPanel.freeResources();
    }

    @Subscribe
    public void handlePersonSelectedEvent(PersonSelectedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));

        timetablePlaceholder.getChildren().removeAll();

        ArrayList<Timetable> timetableToDisplay = new ArrayList<>();
        timetableToDisplay.add(event.person.getTimetable());
        timetableDisplay = new TimetableDisplay(timetableToDisplay);
        timetablePlaceholder.getChildren().add(timetableDisplay.getRoot());

        timetablePlaceholder.toFront();
    }

    @Subscribe
    public void handlePersonPanelSelectionChangeEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));

        timetablePlaceholder.getChildren().removeAll();

        ArrayList<Timetable> timetableToDisplay = new ArrayList<>();
        timetableToDisplay.add(event.getNewSelection().person.getTimetable());
        timetableDisplay = new TimetableDisplay(timetableToDisplay);
        timetablePlaceholder.getChildren().add(timetableDisplay.getRoot());

        timetablePlaceholder.toFront();
    }

    @Subscribe
    private void handleTimetableDisplayEvent(TimetableDisplayEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));

        ArrayList<Timetable> timetablesToDisplay = new ArrayList<>();

        for (ReadOnlyPerson person : event.personsToDisplay) {
            timetablesToDisplay.add(person.getTimetable());
        }

        timetablePlaceholder.getChildren().removeAll();
        timetableDisplay = new TimetableDisplay(timetablesToDisplay);
        timetablePlaceholder.getChildren().add(timetableDisplay.getRoot());

        timetablePlaceholder.toFront();
    }

    @Subscribe
    public void handlePersonAddressDisplayDirectionsEvent(PersonAddressDisplayDirectionsEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        browserPlaceholder.toFront();
    }

    @Subscribe
    public void handlePersonAddressDisplayMapEvent(PersonAddressDisplayMapEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        browserPlaceholder.toFront();
    }
}
```
###### \java\seedu\address\ui\PersonInfoPanel.java
``` java
    @Subscribe
    private void handlePersonSelectedEvent(PersonSelectedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPerson(event.person);
    }

```
###### \java\seedu\address\ui\TimetableDisplay.java
``` java
/**
 * Display for timetables in the UI
 */
public class TimetableDisplay extends UiPart<Region> {

    private static final String FXML = "TimetableDisplay.fxml";
    private ArrayList<Timetable> timetables;

    @FXML
    private GridPane oddGrid;

    @FXML
    private GridPane evenGrid;

    public TimetableDisplay(ArrayList<Timetable> timetables) {
        super(FXML);

        this.timetables = timetables;
        fillInitialGrid();
        fillTimetables();
    }

    /**
     * Initializes the timetable grid to an empty grid not containing any lessons
     */
    private void fillInitialGrid() {
        for (int weekType = 0; weekType < ARRAY_WEEKS.length; weekType++) {
            for (int day = 0; day < ARRAY_DAYS.length; day++) {
                for (int time = 0; time < ARRAY_TIMES.length; time++) {
                    markSlot(weekType, day, time, false);
                }
            }
        }
    }

    /**
     * Populates the initialized grid with lessons according to timetables passed
     */
    private void fillTimetables() {
        if (timetables == null) {
            return;
        }

        for (Timetable timetable : timetables) {
            fillSingleTimetable(timetable);
        }
    }

    /**
     * Fills the timetable grid with panes according to the timetable oject given
     */
    private void fillSingleTimetable(Timetable timetable) {
        for (int weekType = 0; weekType < ARRAY_WEEKS.length; weekType++) {
            for (int day = 0; day < ARRAY_DAYS.length; day++) {
                for (int time = 0; time < ARRAY_TIMES.length; time++) {
                    if (hasLesson(weekType, day, time, timetable)) {
                        markSlot(weekType, day, time, true);
                    }
                }
            }
        }
    }

    /**
     * Fills a slot in the timetable grid with a lesson
     */
    private void markSlot(int weekIndex, int dayIndex, int timeIndex, boolean hasLesson) {

        Pane pane = new Pane();
        GridPane.setColumnIndex(pane, timeIndex + 1);
        GridPane.setRowIndex(pane, dayIndex + 1);

        // Sets the borders such that half hour slots are combined into an hour
        String borderStyle = timeIndex % 2 == 0 ? "solid none solid solid" : "solid solid solid none";

        // Fixes the widths to be even across the grid
        String topWidth = dayIndex == 0 ? "2" : "1";
        String leftWidth = timeIndex == 0 ? "2" : "1";
        String bottomWidth = dayIndex == 4 ? "2" : "1";
        String rightWidth = timeIndex == 31 ? "2" : "1";
        String borderWidths = topWidth + " " + rightWidth + " " + bottomWidth + " " + leftWidth;

        pane.getStyleClass().add(hasLesson ? "timetable-cell-filled" : "timetable-cell-empty");
        pane.setStyle("-fx-border-width: " + borderWidths + ";\n"
                + "-fx-border-style: " + borderStyle);

        if (weekIndex == WEEK_ODD) {
            oddGrid.getChildren().add(pane);
        } else {
            evenGrid.getChildren().add(pane);
        }
    }

    /**
     * Checks if a lesson exists in the timetable at the given parameters
     */
    private boolean hasLesson(int weekIndex, int dayIndex, int timeIndex, Timetable timetable) {
        try {
            return timetable.doesSlotHaveLesson(ARRAY_WEEKS[weekIndex], ARRAY_DAYS[dayIndex], ARRAY_TIMES[timeIndex]);
        } catch (IllegalValueException e) {
            e.printStackTrace();
            return false;
        }
    }
}
```
###### \resources\view\DarkTheme.css
``` css
.split-pane:horizontal .split-pane-divider {
    -fx-background-color: #383838;
    -fx-border-color: transparent transparent transparent transparent;
}

.split-pane:vertical .split-pane-divider {
    -fx-background-color: #383838;
    -fx-border-color: transparent transparent transparent transparent;
}

.split-pane {
    -fx-border-radius: 0;
    -fx-border-width: 0;
    -fx-background-color: #383838;
}

.info-panel {
    -fx-border-radius: 0;
    -fx-border-width: 0;
    -fx-border-color: #383838;
}

.browser-panel {
    -fx-border-radius: 0;
    -fx-border-width: 0;
    -fx-border-color: #383838;
}

```
###### \resources\view\DarkTheme.css
``` css
.display_big_label {
    -fx-font-family: "Segoe UI";
    -fx-font-size: 24px;
    -fx-font-weight: bold;
    -fx-text-fill: #ffffff; //ignore
}

.display_small_label {
    -fx-font-weight: 500;
    -fx-font-family: "Segoe UI Semibold";
    -fx-font-size: 15px;
    -fx-text-fill: #ffffff; //ignore
}

.display_small_value {
    -fx-font-family: "Segoe UI Semibold";
    -fx-font-size: 15px;
    -fx-text-fill: #ffffff;
}

.timetable-label {
    -fx-font-family: "Segoe UI Semibold";
    -fx-font-weight: 700; // font weight aka boldness of the font
    -fx-text-fill: #FFFFFF; // Mon-Fri and Time Labels of The Timetable
}

.tab-pane .tab-header-area .tab-header-background {
    -fx-opacity: 0;
}

.tab-pane .tab {
    -fx-background-color: #383838;
}

.tab-pane .tab:selected {
    -fx-background-color: #303030;
}

.tab .tab-label {
    -fx-alignment: CENTER;
    -fx-text-fill: #FFFFFF; //
    -fx-font-size: 12px;
    -fx-font-family: "Segoe UI";
}

.tab:selected .tab-label {
    -fx-alignment: CENTER;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: #FFFFFF;
}

.info-name-cell {
    -fx-background-color: #303030;
}

.info-cell {
    -fx-background-color: #424242;
}

.timetable-tab-pane {
    -fx-background-color: #383838;
}

.timetable-cell-filled {
    -fx-background-color: #272727;
}

.timetable-cell-empty {
    -fx-background-color: #383838;
}

.timetable-cell-empty, .timetable-cell-filled {
    -fx-border-color: #9E9E9E;
}

.combined-timetable-label {
    -fx-background-color: #303030;
}

.combined-timetable-main-label {
    -fx-text-fill: #FFFFFF;
    -fx-font-size: 22px;
    -fx-font-family: "Segoe UI Semibold";
}

.combined-timetable-names {
    -fx-text-fill: #BDBDBD;
    -fx-font-size: 22px;
    -fx-font-family: "Segoe UI";
}

```
###### \resources\view\InfoPanel.fxml
``` fxml
<?import javafx.scene.layout.StackPane?>
<StackPane prefHeight="700.0" prefWidth="800.0" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
    <StackPane fx:id="browserPlaceholder" prefHeight="150.0" prefWidth="200.0" />
    <StackPane fx:id="timetablePlaceholder" prefHeight="150.0" prefWidth="200.0" />
</StackPane>
```
###### \resources\view\LightTheme.css
``` css
.split-pane:horizontal .split-pane-divider {
    -fx-background-color: #FAFAFA;
    -fx-border-color: transparent transparent transparent transparent;
}

.split-pane:vertical .split-pane-divider {
    -fx-background-color: #FAFAFA;
    -fx-border-color: transparent transparent transparent transparent;
}

.split-pane {
    -fx-border-radius: 0;
    -fx-border-width: 0;
    -fx-background-color: #FAFAFA;
}

.info-panel {
    -fx-border-radius: 0;
    -fx-border-width: 0;
    -fx-border-color: #FAFAFA;
}

.browser-panel {
    -fx-border-radius: 0;
    -fx-border-width: 0;
    -fx-border-color: #FAFAFA;
}

```
###### \resources\view\LightTheme.css
``` css
.display_big_label {
    -fx-font-family: "Segoe UI";
    -fx-font-size: 24px;
    -fx-font-weight: bold;
    -fx-text-fill: #262626;
}

.display_small_label {
    -fx-font-family: "Segoe UI Semibold";
    -fx-font-size: 15px;
    -fx-text-fill: #262626;
}

.display_small_value {
    -fx-font-family: "Segoe UI Semibold";
    -fx-font-size: 15px;
    -fx-text-fill: #262626;
}

.timetable-label {
    -fx-font-family: "Segoe UI Semibold";
    -fx-font-weight: 700; // font weight aka boldness of the font
    -fx-text-fill: #232323; // Mon-Fri and Time Labels of The Timetable
}

.tab-pane .tab-header-area .tab-header-background {
    -fx-opacity: 0;
}

.tab-pane .tab {
    -fx-background-color: #FAFAFA;
}

.tab-pane .tab:selected {
    -fx-background-color: #E0E0E0;
}

.tab .tab-label {
    -fx-alignment: CENTER;
    -fx-text-fill: #232323;
    -fx-font-size: 12px;
    -fx-font-family: "Segoe UI";
}

.tab:selected .tab-label {
    -fx-alignment: CENTER;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: #232323;
}

.info-name-cell {
    -fx-background-color: #E0E0E0;
}

.info-cell {
    -fx-background-color: #BDBDBD;
}

.timetable-tab-pane {
    -fx-background-color: #FAFAFA;
}

.timetable-cell-filled {
    -fx-background-color: #E0E0E0;
}

.timetable-cell-empty {
    -fx-background-color: #FAFAFA;
}

.timetable-cell-empty, .timetable-cell-filled {
    -fx-border-color: #BDBDBD;
}

.combined-timetable-label {
    -fx-background-color: #E0E0E0;
}

.combined-timetable-main-label {
    -fx-text-fill: #212121;
    -fx-font-size: 22px;
    -fx-font-family: "Segoe UI";
}

.combined-timetable-names {
    -fx-text-fill: #565656;
    -fx-font-size: 22px;
    -fx-font-family: "Segoe UI";
}

```
###### \resources\view\MainWindow.fxml
``` fxml
                <StackPane fx:id="infoPlaceholder" styleClass="pane-with-border">
                    <padding>
                        <Insets bottom="10" left="10" right="10" top="10"/>
                    </padding>
                </StackPane>
            </SplitPane>
        </AnchorPane>
    </HBox>
    <StackPane fx:id="statusbarPlaceholder" VBox.vgrow="NEVER"/>
</VBox>
```
###### \resources\view\TimetableDisplay.fxml
``` fxml

<StackPane xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
    <AnchorPane styleClass="timetable-display">
        <TabPane layoutX="-1.600000023841858" layoutY="-2.4000000953674316" styleClass="timetable-tab-pane"
                 tabClosingPolicy="UNAVAILABLE" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0"
                 AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0">
            <Tab fx:id="oddWeekTab" closable="false" text="Odd Week">
                <GridPane fx:id="oddGrid" alignment="CENTER">
                    <columnConstraints>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="65.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                    </columnConstraints>
                    <rowConstraints>
                        <RowConstraints minHeight="10.0" prefHeight="10.0" vgrow="SOMETIMES"/>
                        <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES"/>
                        <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES"/>
                        <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES"/>
                        <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES"/>
                        <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES"/>
                    </rowConstraints>
                    <VBox alignment="CENTER" prefHeight="200.0" prefWidth="100.0" GridPane.rowIndex="5">
                        <Label styleClass="timetable-label" text="Fri"/>
                    </VBox>
                    <VBox alignment="CENTER" prefHeight="200.0" prefWidth="100.0" GridPane.rowIndex="1">
                        <Label styleClass="timetable-label" text="Mon"/>
                    </VBox>
                    <VBox alignment="CENTER" prefHeight="200.0" prefWidth="100.0" GridPane.rowIndex="4">
                        <Label styleClass="timetable-label" text="Thur"/>
                    </VBox>
                    <VBox alignment="CENTER" prefHeight="200.0" prefWidth="100.0" GridPane.rowIndex="3">
                        <Label styleClass="timetable-label" text="Wed"/>
                    </VBox>
                    <VBox alignment="CENTER" prefHeight="200.0" prefWidth="100.0" GridPane.rowIndex="2">
                        <Label styleClass="timetable-label" text="Tues"/>
                    </VBox>
                    <HBox alignment="CENTER" prefHeight="100.0" prefWidth="200.0" GridPane.columnIndex="1"
                          GridPane.columnSpan="2">
                        <Label styleClass="timetable-label" text="0800"/>
                    </HBox>
                    <HBox alignment="CENTER" prefHeight="100.0" prefWidth="200.0" GridPane.columnIndex="3"
                          GridPane.columnSpan="2">
                        <Label styleClass="timetable-label" text="0900"/>
                    </HBox>
                    <HBox alignment="CENTER" prefHeight="100.0" prefWidth="200.0" GridPane.columnIndex="5"
                          GridPane.columnSpan="2">
                        <Label styleClass="timetable-label" text="1000"/>
                    </HBox>
                    <HBox alignment="CENTER" prefHeight="100.0" prefWidth="200.0" GridPane.columnIndex="7"
                          GridPane.columnSpan="2">
                        <Label styleClass="timetable-label" text="1100"/>
                    </HBox>
                    <HBox alignment="CENTER" prefHeight="100.0" prefWidth="200.0" GridPane.columnIndex="9"
                          GridPane.columnSpan="2">
                        <Label styleClass="timetable-label" text="1200"/>
                    </HBox>
                    <HBox alignment="CENTER" prefHeight="100.0" prefWidth="200.0" GridPane.columnIndex="11"
                          GridPane.columnSpan="2">
                        <Label styleClass="timetable-label" text="1300"/>
                    </HBox>
                    <HBox alignment="CENTER" prefHeight="100.0" prefWidth="200.0" GridPane.columnIndex="13"
                          GridPane.columnSpan="2">
                        <Label styleClass="timetable-label" text="1400"/>
                    </HBox>
                    <HBox alignment="CENTER" prefHeight="100.0" prefWidth="200.0" GridPane.columnIndex="15"
                          GridPane.columnSpan="2">
                        <Label styleClass="timetable-label" text="1500"/>
                    </HBox>
                    <HBox alignment="CENTER" prefHeight="100.0" prefWidth="200.0" GridPane.columnIndex="17"
                          GridPane.columnSpan="2">
                        <Label styleClass="timetable-label" text="1600"/>
                    </HBox>
                    <HBox alignment="CENTER" prefHeight="100.0" prefWidth="200.0" GridPane.columnIndex="19"
                          GridPane.columnSpan="2">
                        <Label styleClass="timetable-label" text="1700"/>
                    </HBox>
                    <HBox alignment="CENTER" prefHeight="100.0" prefWidth="200.0" GridPane.columnIndex="21"
                          GridPane.columnSpan="2">
                        <Label styleClass="timetable-label" text="1800"/>
                    </HBox>
                    <HBox alignment="CENTER" prefHeight="100.0" prefWidth="200.0" GridPane.columnIndex="23"
                          GridPane.columnSpan="2">
                        <Label styleClass="timetable-label" text="1900"/>
                    </HBox>
                    <HBox alignment="CENTER" prefHeight="100.0" prefWidth="200.0" GridPane.columnIndex="25"
                          GridPane.columnSpan="2">
                        <Label styleClass="timetable-label" text="2000"/>
                    </HBox>
                    <HBox alignment="CENTER" prefHeight="100.0" prefWidth="200.0" GridPane.columnIndex="27"
                          GridPane.columnSpan="2">
                        <Label styleClass="timetable-label" text="2100"/>
                    </HBox>
                    <HBox alignment="CENTER" prefHeight="100.0" prefWidth="200.0" GridPane.columnIndex="29"
                          GridPane.columnSpan="2">
                        <Label styleClass="timetable-label" text="2200"/>
                    </HBox>
                    <HBox alignment="CENTER" prefHeight="100.0" prefWidth="200.0" GridPane.columnIndex="31"
                          GridPane.columnSpan="2">
                        <Label styleClass="timetable-label" text="2300"/>
                    </HBox>
                    <padding>
                        <Insets bottom="10.0" left="0.0" right="10.0" top="0.0"/>
                    </padding>
                </GridPane>
            </Tab>
            <Tab fx:id="evenWeekTab" closable="false" text="Even Week">
                <GridPane fx:id="evenGrid" alignment="CENTER">
                    <columnConstraints>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="65.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                        <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0"/>
                    </columnConstraints>
                    <rowConstraints>
                        <RowConstraints minHeight="10.0" prefHeight="10.0" vgrow="SOMETIMES"/>
                        <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES"/>
                        <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES"/>
                        <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES"/>
                        <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES"/>
                        <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES"/>
                    </rowConstraints>
                    <VBox alignment="CENTER" prefHeight="200.0" prefWidth="100.0" GridPane.rowIndex="5">
                        <Label styleClass="timetable-label" text="Fri"/>
                    </VBox>
                    <VBox alignment="CENTER" prefHeight="200.0" prefWidth="100.0" GridPane.rowIndex="1">
                        <Label styleClass="timetable-label" text="Mon"/>
                    </VBox>
                    <VBox alignment="CENTER" prefHeight="200.0" prefWidth="100.0" GridPane.rowIndex="4">
                        <Label styleClass="timetable-label" text="Thur"/>
                    </VBox>
                    <VBox alignment="CENTER" prefHeight="200.0" prefWidth="100.0" GridPane.rowIndex="3">
                        <Label styleClass="timetable-label" text="Wed"/>
                    </VBox>
                    <VBox alignment="CENTER" prefHeight="200.0" prefWidth="100.0" GridPane.rowIndex="2">
                        <Label styleClass="timetable-label" text="Tues"/>
                    </VBox>
                    <HBox alignment="CENTER" prefHeight="100.0" prefWidth="200.0" GridPane.columnIndex="1"
                          GridPane.columnSpan="2">
                        <Label styleClass="timetable-label" text="0800"/>
                    </HBox>
                    <HBox alignment="CENTER" prefHeight="100.0" prefWidth="200.0" GridPane.columnIndex="3"
                          GridPane.columnSpan="2">
                        <Label styleClass="timetable-label" text="0900"/>
                    </HBox>
                    <HBox alignment="CENTER" prefHeight="100.0" prefWidth="200.0" GridPane.columnIndex="5"
                          GridPane.columnSpan="2">
                        <Label styleClass="timetable-label" text="1000"/>
                    </HBox>
                    <HBox alignment="CENTER" prefHeight="100.0" prefWidth="200.0" GridPane.columnIndex="7"
                          GridPane.columnSpan="2">
                        <Label styleClass="timetable-label" text="1100"/>
                    </HBox>
                    <HBox alignment="CENTER" prefHeight="100.0" prefWidth="200.0" GridPane.columnIndex="9"
                          GridPane.columnSpan="2">
                        <Label styleClass="timetable-label" text="1200"/>
                    </HBox>
                    <HBox alignment="CENTER" prefHeight="100.0" prefWidth="200.0" GridPane.columnIndex="11"
                          GridPane.columnSpan="2">
                        <Label styleClass="timetable-label" text="1300"/>
                    </HBox>
                    <HBox alignment="CENTER" prefHeight="100.0" prefWidth="200.0" GridPane.columnIndex="13"
                          GridPane.columnSpan="2">
                        <Label styleClass="timetable-label" text="1400"/>
                    </HBox>
                    <HBox alignment="CENTER" prefHeight="100.0" prefWidth="200.0" GridPane.columnIndex="15"
                          GridPane.columnSpan="2">
                        <Label styleClass="timetable-label" text="1500"/>
                    </HBox>
                    <HBox alignment="CENTER" prefHeight="100.0" prefWidth="200.0" GridPane.columnIndex="17"
                          GridPane.columnSpan="2">
                        <Label styleClass="timetable-label" text="1600"/>
                    </HBox>
                    <HBox alignment="CENTER" prefHeight="100.0" prefWidth="200.0" GridPane.columnIndex="19"
                          GridPane.columnSpan="2">
                        <Label styleClass="timetable-label" text="1700"/>
                    </HBox>
                    <HBox alignment="CENTER" prefHeight="100.0" prefWidth="200.0" GridPane.columnIndex="21"
                          GridPane.columnSpan="2">
                        <Label styleClass="timetable-label" text="1800"/>
                    </HBox>
                    <HBox alignment="CENTER" prefHeight="100.0" prefWidth="200.0" GridPane.columnIndex="23"
                          GridPane.columnSpan="2">
                        <Label styleClass="timetable-label" text="1900"/>
                    </HBox>
                    <HBox alignment="CENTER" prefHeight="100.0" prefWidth="200.0" GridPane.columnIndex="25"
                          GridPane.columnSpan="2">
                        <Label styleClass="timetable-label" text="2000"/>
                    </HBox>
                    <HBox alignment="CENTER" prefHeight="100.0" prefWidth="200.0" GridPane.columnIndex="27"
                          GridPane.columnSpan="2">
                        <Label styleClass="timetable-label" text="2100"/>
                    </HBox>
                    <HBox alignment="CENTER" prefHeight="100.0" prefWidth="200.0" GridPane.columnIndex="29"
                          GridPane.columnSpan="2">
                        <Label styleClass="timetable-label" text="2200"/>
                    </HBox>
                    <HBox alignment="CENTER" prefHeight="100.0" prefWidth="200.0" GridPane.columnIndex="31"
                          GridPane.columnSpan="2">
                        <Label styleClass="timetable-label" text="2300"/>
                    </HBox>
                    <padding>
                        <Insets bottom="10.0" left="0.0" right="10.0" top="0.0"/>
                    </padding>
                </GridPane>
            </Tab>
        </TabPane>
    </AnchorPane>
</StackPane>
```
