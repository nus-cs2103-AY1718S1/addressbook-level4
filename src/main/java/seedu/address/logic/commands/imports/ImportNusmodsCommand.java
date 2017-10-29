package seedu.address.logic.commands.imports;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import seedu.address.commons.util.JsonUtil;
import seedu.address.commons.util.UrlUtil;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

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
    private static final String INVALID_URL = "The URL provided is not from NUSMods website. \n%1$s";
    private static final String YEAR_INVALID =
            "Maybe you modify the part regarding academic year and semester.";
    private static final String YEAR_OFFSET_BY_ONE =
            "The start/end year of the same academic year must offset by 1";
    private static final String SEMESTER_INVALID =
            "Semester number must be an integer from 1 to 4. Use 3/4 for Special Terms Part 1/2.";
    private static final String INVALID_ENCODING = "The URL encoding is not supported. Please use UTF-8.";
    private static final String MODULE_INFO_JSON_URL_FORMAT =
            "http://api.nusmods.com/%1$s-%2$s/%3$s/modules/%4$s.json";

    // Semester should be a one-digit number from 1 to 4, year must be after 2000.
    private static final Pattern URL_SEMESTER_INFO_FORMAT  =
            Pattern.compile("/timetable/(?<year1>20\\d{2})[-](?<year2>20\\d{2})/sem(?<semester>[1-4])");

    private URL url;
    private int yearStart;
    private int yearEnd;
    private int semester;

    /**
     * Only checks the academic year and semester information in the constructor. If the query part (module information)
     * is wrong, they will simply not be added to event when executed.
     */
    public ImportNusmodsCommand(URL url) throws ParseException {
        super(url.toString(), ImportType.NUSMODS);
        this.url = url;
        matchSemesterInformation();
    }

    @Override
    public CommandResult execute() throws CommandException {
        Set<String> modules;
        try {
            modules = fetchModuleCodes();
        } catch (UnsupportedEncodingException e) {
            throw new CommandException(String.format(INVALID_URL, INVALID_ENCODING));
        }

        return null;
    }

    /**
     * Extracts and matches the academic year and semester information from user input URL.
     *
     * @throws ParseException if the given URL does not obey NUSMods convention.
     */
    private void matchSemesterInformation() throws ParseException {
        Matcher matcher = URL_SEMESTER_INFO_FORMAT.matcher(url.getPath());
        if (!matcher.matches()) {
            throw  new ParseException(INVALID_URL);
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

        if (semester <= 0 || semester >= 5) {
            throw  new ParseException(String.format(INVALID_URL, SEMESTER_INVALID));
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

    private ModuleInfo getModuleInfo(String moduleCode) throws IOException {
        URL url = new URL(String.format(MODULE_INFO_JSON_URL_FORMAT, yearStart, yearEnd, semester, moduleCode));
        return JsonUtil.fromJsonUrl(url, ModuleInfo.class);
    }
}
