package seedu.address.logic.parser.task;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.task.RescheduleTaskCommand;
import seedu.address.logic.parser.DateTimeParserUtil;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author deep4k
/**
 * Parses input arguments and creates a new RescheduleTaskCommand object
 */
public class RescheduleTaskCommandParser implements Parser<RescheduleTaskCommand> {

    private static final Pattern ADD_SCHEDULE_ARGS_REGEX = Pattern.compile("(?:.+?(?=(?:(?:(?i)by|from|to)\\s|$)))+?");

    private static final String ARGS_FROM = "from";
    private static final String ARGS_BY = "by";
    private static final String ARGS_TO = "to";

    private static final String[] PARSED_TIME_ARGS = new String[]{ARGS_FROM, ARGS_TO, ARGS_BY};

    /**
     * Parses the given {@code String} of arguments in the context of the RescheduleTaskCommand
     * and returns an RescheduleTaskCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public RescheduleTaskCommand parse(String args) throws ParseException {

        HashMap<String, Optional<LocalDateTime>> dateTimeMap = new HashMap<>();

        Matcher matcher = ADD_SCHEDULE_ARGS_REGEX.matcher(args.trim());

        if (!matcher.matches()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RescheduleTaskCommand.MESSAGE_USAGE));
        }

        matcher.reset();
        matcher.find();
        Index index;
        try {
            index = ParserUtil.parseIndex(matcher.group(0));
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RescheduleTaskCommand.MESSAGE_USAGE));
        }
        try {
            BiConsumer<String, String> consumer = (matchedGroup, token) -> {
                String time = matchedGroup.substring(token.length(), matchedGroup.length());
                if (DateTimeParserUtil.containsTime(time)) {
                    dateTimeMap.put(token, DateTimeParserUtil.nattyParseDateTime(time));
                }
            };

            executeOnEveryMatcherToken(matcher, consumer);

            boolean hasDeadlineKeyword = dateTimeMap.containsKey(ARGS_BY);
            boolean hasStartTimeKeyword = dateTimeMap.containsKey(ARGS_FROM);
            boolean hasEndTimeKeyword = dateTimeMap.containsKey(ARGS_TO);

            if (hasDeadlineKeyword && !hasStartTimeKeyword && !hasEndTimeKeyword) {
                return new RescheduleTaskCommand(index, Optional.empty(), dateTimeMap.get(ARGS_BY));
            }

            if (!hasDeadlineKeyword && hasStartTimeKeyword && hasEndTimeKeyword) {
                return new RescheduleTaskCommand(index, dateTimeMap.get(ARGS_FROM), dateTimeMap.get(ARGS_TO));
            }

            if (!hasDeadlineKeyword && !hasStartTimeKeyword && !hasEndTimeKeyword) {
                return new RescheduleTaskCommand(index, Optional.empty(), Optional.empty());
            }

            return new RescheduleTaskCommand();
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Parses arguments according to the tokens defined in  RescheduleTaskCommand
     */
    private void executeOnEveryMatcherToken(Matcher matcher, BiConsumer<String, String> consumer) {
        while (matcher.find()) {
            for (String token : PARSED_TIME_ARGS) {
                String matchedGroup = matcher.group(0).toLowerCase();
                if (matchedGroup.startsWith(token)) {
                    consumer.accept(matchedGroup, token);
                }
            }
        }
    }

    @Override
    public String getCommandWord() {
        return RescheduleTaskCommand.COMMAND_WORD;
    }
}
