package seedu.address.logic.parser.task;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.task.AddTaskCommand;
import seedu.address.logic.parser.DateTimeParserUtil;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddTaskCommand object
 */
public class AddTaskCommandParser implements Parser<AddTaskCommand> {

    private static final Pattern ADD_SCHEDULE_ARGS_REGEX = Pattern.compile("(?:.+?(?=(?:(?:(?i)by|from|to)\\s|$)))+?");
    private static final Pattern QUOTATION_REGEX = Pattern.compile("\'([^\']*)\'");

    private static final String ARGS_FROM = "from";
    private static final String ARGS_BY = "by";
    private static final String ARGS_TO = "to";
    private static final String FILLER_WORD = "FILLER ";

    private static final String SINGLE_QUOTE = "\'";
    private static final String[] PARSED_TIME_ARGS = new String[]{ARGS_FROM, ARGS_TO, ARGS_BY};

    /**
     * Parses the given {@code String} of arguments in the context of the AddTaskCommand
     * and returns an AddTaskCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTaskCommand parse(String args) throws ParseException {

        StringBuilder headerBuilder = new StringBuilder();
        HashMap<String, Optional<LocalDateTime>> dateTimeMap = new HashMap<>();

        Optional<String> checkedArgs = checkForQuotation(args);
        if (checkedArgs.isPresent()) {
            headerBuilder.append(checkedArgs.get().replace(SINGLE_QUOTE, ""));
            args = FILLER_WORD + args.replace(checkedArgs.get(), "");
        }

        Matcher matcher = ADD_SCHEDULE_ARGS_REGEX.matcher(args.trim());

        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
        }

        try {
            matcher.reset();
            matcher.find();
            if (headerBuilder.length() == 0) {
                headerBuilder.append(matcher.group(0));
            }

            BiConsumer<String, String> consumer = (matchedGroup, token) -> {
                String time = matchedGroup.substring(token.length(), matchedGroup.length());
                if (DateTimeParserUtil.containsTime(time)) {
                    dateTimeMap.put(token, DateTimeParserUtil.nattyParseDateTime(time));
                } else {
                    headerBuilder.append(matchedGroup);
                }
            };

            executeOnEveryMatcherToken(matcher, consumer);

            String header = headerBuilder.toString();

            if (header.length() == 0) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
            }
            boolean hasDeadlineKeyword = dateTimeMap.containsKey(ARGS_BY);
            boolean hasStartTimeKeyword = dateTimeMap.containsKey(ARGS_FROM);
            boolean hasEndTimeKeyword = dateTimeMap.containsKey(ARGS_TO);

            if (hasDeadlineKeyword && !hasStartTimeKeyword && !hasEndTimeKeyword) {
                return new AddTaskCommand(header, dateTimeMap.get(ARGS_BY));
            }

            if (!hasDeadlineKeyword && hasStartTimeKeyword && hasEndTimeKeyword) {
                return new AddTaskCommand(header, dateTimeMap.get(ARGS_FROM), dateTimeMap.get(ARGS_TO));
            }

            if (!hasDeadlineKeyword && !hasStartTimeKeyword && !hasEndTimeKeyword) {
                return new AddTaskCommand(header);
            }

            return new AddTaskCommand();

        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Parses arguments according to the tokens defined in  AddCommandTask
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

    /**
     * Parses args for quotation marks , signifying header of task
     *
     * @return returns parsed string within quotation tokens
     */
    private Optional<String> checkForQuotation(String args) {
        Matcher matcher = QUOTATION_REGEX.matcher(args.trim());
        if (!matcher.find()) {
            return Optional.empty();
        }
        return Optional.of(matcher.group(0));

    }

    @Override
    public String getCommandWord() {
        return AddTaskCommand.COMMAND_WORD;
    }
}
