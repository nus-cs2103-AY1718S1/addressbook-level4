package seedu.address.logic.commands.imports;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.SwitchToEventsListEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.commons.util.UrlUtil;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Event;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.property.Address;
import seedu.address.model.property.DateTime;
import seedu.address.model.property.Name;
import seedu.address.model.property.exceptions.PropertyNotFoundException;

//@@author yunpengn
/**
 * Imports data from the URL of a NUSMods timetable.
 *
 * @see <a href="https://nusmods.com/">https://nusmods.com/</a>
 */
public class ImportNusmodsCommand extends ImportCommand {
    /* Messages displayed to the user (ready for use). */
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Imports data from NUSMods timetable URL.\n"
            + "Examples:\n"
            + COMMAND_WORD + " --nusmods https://nusmods.com/timetable/2017-2018/sem1?CS2103T[TUT]=C01";
    public static final String INVALID_URL = "The URL provided is not from NUSMods website. \n%1$s";
    public static final String YEAR_OFFSET_BY_ONE =
            "The start/end year of the same academic year must offset by 1";
    public static final String MESSAGE_SUCCESS = "%1$d examinations have been added as events.";

    private static final String YEAR_INVALID =
            "Maybe you modify the part regarding academic year and semester.";
    private static final String INVALID_ENCODING = "The URL encoding is not supported. Please use UTF-8.";
    private static final String MODULE_INFO_JSON_URL_FORMAT =
            "http://api.nusmods.com/%1$s-%2$s/%3$s/modules/%4$s.json";
    private static final String UNABLE_FETCH_MODULE_INFO = "Unable to fetch the information of module %1$s.";
    private static final String EXAM_EVENT_NAME = "%1$s Examination";
    private static final String UNABLE_CREATE_EXAM_EVENT = "Unable to create exam event for %1$s.";
    private static final String EXAM_EVENT_DEFAULT_ADDRESS = "NUS";
    private static final String EXAM_EVENT_EXIST_DUPLICATE =
            "The examination event for %1$s already exists in the application.";

    private static final String SOME_EXAMS_NOT_ADDED =
            "\nHowever, some examination were not added since they already exist in the application.";
    private static final String TO_STRING_FORMAT = "AY%1$d-%2$d Semester %3$d";

    // Semester should be a one-digit number from 1 to 4, year must be after 2000.
    private static final Pattern URL_SEMESTER_INFO_FORMAT  =
            Pattern.compile("/timetable/(?<year1>20\\d{2})[-](?<year2>20\\d{2})/sem(?<semester>[1-4])");

    private static final Logger logger = LogsCenter.getLogger(ImportNusmodsCommand.class);

    private URL url;
    private int yearStart;
    private int yearEnd;
    private int semester;

    // A counter for how many examinations have been added to the application as events.
    private int eventsAdded;
    // Signals whether we fail to add any examination.
    private boolean failToAdd;

    /**
     * Only checks the academic year and semester information in the constructor. If the query part (module information)
     * is wrong, they will simply not be added to event when executed.
     */
    public ImportNusmodsCommand(URL url) throws ParseException {
        super(url.toString(), ImportType.NUSMODS);
        this.url = url;
        matchSemesterInformation();
        eventsCenter.registerHandler(this);
        eventsAdded = 0;
        failToAdd = false;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        // Get all the module codes.
        Set<String> modules;
        try {
            modules = fetchModuleCodes();
        } catch (UnsupportedEncodingException e) {
            throw new CommandException(String.format(INVALID_URL, INVALID_ENCODING));
        }

        // Retrieve information of all modules from NUSMods JSON API.
        Set<ModuleInfo> moduleInfo = new HashSet<>();
        for (String moduleCode: modules) {
            try {
                moduleInfo.add(getModuleInfo(moduleCode));
            } catch (IOException e) {
                throw new CommandException(String.format(UNABLE_FETCH_MODULE_INFO, moduleCode));
            }
        }

        // Add an event for the final examination of each module.
        for (ModuleInfo module: moduleInfo) {
            addExamEvent(module).ifPresent(e -> {
                try {
                    model.addEvent(e);
                    incrementEventsAddedCount();
                } catch (DuplicateEventException e1) {
                    failToAdd = true;
                    logger.info(String.format(EXAM_EVENT_EXIST_DUPLICATE, module.getModuleCode()));
                }
            });
        }

        // Do not switch to event listing if there is no change.
        if (eventsAdded > 0) {
            raise(new SwitchToEventsListEvent());
        }

        String successMessage = String.format(MESSAGE_SUCCESS, eventsAdded);
        if (failToAdd) {
            return new CommandResult(successMessage + SOME_EXAMS_NOT_ADDED);
        } else {
            return new CommandResult(successMessage);
        }
    }

    /**
     * Extracts and matches the academic year and semester information from user input URL.
     *
     * @throws ParseException if the given URL does not obey NUSMods convention.
     */
    private void matchSemesterInformation() throws ParseException {
        Matcher matcher = URL_SEMESTER_INFO_FORMAT.matcher(url.getPath());
        if (!matcher.matches()) {
            throw  new ParseException(String.format(INVALID_URL, ""));
        }

        try {
            yearStart = Integer.parseInt(matcher.group("year1"));
            yearEnd = Integer.parseInt(matcher.group("year2"));
            semester = Integer.parseInt(matcher.group("semester"));
        } catch (NumberFormatException nfe) {
            throw new ParseException(String.format(INVALID_URL, YEAR_INVALID));
        }

        if (yearEnd - yearStart != 1) {
            throw new ParseException(String.format(INVALID_URL, YEAR_OFFSET_BY_ONE));
        }
    }

    /**
     * Returns all the module codes embedded in the {@link #url} field. This fetching will ignore the type
     * of sessions (lecture, tutorial, etc.). Thus, it only returns all modules in the {@link #url}.
     */
    private Set<String> fetchModuleCodes() throws UnsupportedEncodingException {
        Set<String> keys = UrlUtil.fetchUrlParameterKeys(url);
        return keys.stream().map(key -> key.substring(0, key.indexOf("["))).collect(Collectors.toSet());
    }

    /**
     * Gets the module information from NUSMods API and convert to a {@link ModuleInfo} class.
     *
     * @see <a href="https://github.com/nusmodifications/nusmods-api#get-acadyearsemestermodulesmodulecodejson">
     *     NUSMods API official documentation</a>
     */
    private ModuleInfo getModuleInfo(String moduleCode) throws IOException {
        URL url = new URL(String.format(MODULE_INFO_JSON_URL_FORMAT, yearStart, yearEnd, semester, moduleCode));
        return JsonUtil.fromJsonUrl(url, ModuleInfo.class);
    }

    /**
     * Creates an {@link Event} representing the final examination according to information from {@link ModuleInfo}.
     *
     * @return an {@link Optional} that is present with an {@link Event} only if the given module has a final
     * examination (some modules at NUS are 100% continuous-assessment, like CFG1010).
     */
    private Optional<ReadOnlyEvent> addExamEvent(ModuleInfo module) throws CommandException {
        if (module.getExamDate() == null) {
            return Optional.empty();
        }

        try {
            Name eventName = new Name(String.format(EXAM_EVENT_NAME, module.getModuleCode()));
            DateTime eventDatetime = new DateTime(module.getExamDate());
            Address eventAddress = new Address(EXAM_EVENT_DEFAULT_ADDRESS);
            return Optional.of(new Event(eventName, eventDatetime, eventAddress));
        } catch (IllegalValueException | PropertyNotFoundException e) {
            throw new CommandException(String.format(UNABLE_CREATE_EXAM_EVENT, module.getModuleCode()));
        }
    }

    private void incrementEventsAddedCount() {
        eventsAdded++;
    }

    @Override
    public String toString() {
        return String.format(TO_STRING_FORMAT, yearStart, yearEnd, semester);
    }
}
