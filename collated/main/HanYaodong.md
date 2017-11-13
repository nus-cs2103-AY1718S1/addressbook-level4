# HanYaodong
###### /java/seedu/address/commons/util/StringUtil.java
``` java
    /**
     * Returns the command words in the program as an Array.
     */
    public static String[] getSystemCommandWords() {
        return new String[]{AddCommand.COMMAND_WORD,
                            ClearCommand.COMMAND_WORD,
                            DeleteCommand.COMMAND_WORD,
                            EditCommand.COMMAND_WORD,
                            ExitCommand.COMMAND_WORD,
                            ExportCommand.COMMAND_WORD,
                            FindCommand.COMMAND_WORD,
                            HelpCommand.COMMAND_WORD,
                            HistoryCommand.COMMAND_WORD,
                            ImportCommand.COMMAND_WORD,
                            ListCommand.COMMAND_WORD,
                            LoginCommand.COMMAND_WORD,
                            RedoCommand.COMMAND_WORD,
                            RemarkCommand.COMMAND_WORD,
                            SearchCommand.COMMAND_WORD,
                            SelectCommand.COMMAND_WORD,
                            SortCommand.COMMAND_WORD,
                            UndoCommand.COMMAND_WORD};
    }
}
```
###### /java/seedu/address/logic/AutoComplete.java
``` java
package seedu.address.logic;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.StringUtil.getSystemCommandWords;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS_STRING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY_STRING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL_STRING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FACEBOOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FACEBOOK_STRING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME_STRING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD_STRING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE_STRING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK_STRING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_STRING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME_STRING;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Utilities for auto completion for command.
 */
public class AutoComplete {

    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Auto-completes the input String.
     */
    public static String autoComplete(String userInput, List<ReadOnlyPerson> filteredPersonList) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return unknownCommandAutoComplete(userInput);
        }

        final String commandWord = matcher.group("commandWord").toLowerCase();
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
        case AddCommand.COMMAND_ALIAS:
            return addCommandAutoComplete(arguments);

        case EditCommand.COMMAND_WORD:
        case EditCommand.COMMAND_ALIAS:
            return editCommandAutoComplete(arguments, filteredPersonList);

        case LoginCommand.COMMAND_WORD:
            return loginCommandAutoComplete(arguments);

        case SelectCommand.COMMAND_WORD:
        case SelectCommand.COMMAND_ALIAS:
            return selectCommandAutoComplete(arguments);

        case DeleteCommand.COMMAND_WORD:
        case DeleteCommand.COMMAND_ALIAS:
            return deleteCommandAutoComplete(arguments);

        case RemarkCommand.COMMAND_WORD:
        case RemarkCommand.COMMAND_ALIAS:
            return remarkCommandAutoComplete(arguments, filteredPersonList);

        case SortCommand.COMMAND_WORD:
            return sortCommandAutoComplete(arguments);

        case ExportCommand.COMMAND_WORD:
            return exportCommandAutoComplete(arguments);

        case ImportCommand.COMMAND_WORD:
            return importCommandAutoComplete(arguments);

        default:
            return unknownCommandAutoComplete(commandWord, arguments);

        }
    }

    /**
     * Auto-completes the prefix field that is not entered.
     */
    private static String addCommandAutoComplete(String args) {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE,
            PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_BIRTHDAY, PREFIX_FACEBOOK, PREFIX_TAG);
        return AddCommand.COMMAND_WORD + " "
            + formatPrefixWithArgs(argMultimap, PREFIX_NAME) + " "
            + formatPrefixWithArgs(argMultimap, PREFIX_PHONE) + " "
            + formatPrefixWithArgs(argMultimap, PREFIX_EMAIL) + " "
            + formatPrefixWithArgs(argMultimap, PREFIX_ADDRESS) + " "
            + formatPrefixWithArgs(argMultimap, PREFIX_BIRTHDAY) + " "
            + formatPrefixWithArgs(argMultimap, PREFIX_FACEBOOK) + " "
            + formatPrefixWithArgs(argMultimap, PREFIX_TAG);
    }

    /**
     * Auto-completes delete command.
     */
    private static String deleteCommandAutoComplete(String args) {
        return DeleteCommand.COMMAND_WORD +  " " + formatSingleIndexString(args);
    }

    /**
     * Auto-completes edit command.
     */
    private static String editCommandAutoComplete(String args, List<ReadOnlyPerson> filteredPersonList) {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE,
            PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_BIRTHDAY, PREFIX_FACEBOOK, PREFIX_TAG);

        String indexString = argMultimap.getPreamble();
        Index index;
        try {
            index = ParserUtil.parseIndex(indexString);
        } catch (IllegalValueException ive) {
            // if the index is invalid
            String restArgs = args.replaceFirst(indexString, "").trim();
            return EditCommand.COMMAND_WORD + " " + formatSingleIndexString(indexString) + " " + restArgs;
        }

        // auto fill all fields
        String prefixWithArgs;
        try {
            ReadOnlyPerson person = filteredPersonList.get(index.getZeroBased());
            prefixWithArgs = formatPrefixWithArgs(argMultimap, PREFIX_NAME, person) + " "
                + formatPrefixWithArgs(argMultimap, PREFIX_PHONE, person) + " "
                + formatPrefixWithArgs(argMultimap, PREFIX_EMAIL, person) + " "
                + formatPrefixWithArgs(argMultimap, PREFIX_ADDRESS, person) + " "
                + formatPrefixWithArgs(argMultimap, PREFIX_BIRTHDAY, person) + " "
                + formatPrefixWithArgs(argMultimap, PREFIX_FACEBOOK, person) + " "
                + formatPrefixWithArgs(argMultimap, PREFIX_TAG, person);
        } catch (IndexOutOfBoundsException e) {
            String restArgs = args.replaceFirst(indexString, "").trim();
            return EditCommand.COMMAND_WORD + " " + formatSingleIndexString(indexString) + " " + restArgs;
        }

        return EditCommand.COMMAND_WORD + " " + indexString +  " " + prefixWithArgs;
    }

    /**
     * Auto-completes export command.
     */
    private static String exportCommandAutoComplete(String args) {
        if (args.trim().isEmpty()) {
            return ExportCommand.COMMAND_WORD + " ";
        }

        String possibleIndexListString;
        String possibleFilePath = "";
        if (args.contains(";")) { // contains delimiter ";"
            possibleIndexListString = args.substring(0, args.indexOf(';'));
            possibleFilePath = args.substring(args.indexOf(';') + 1);
        } else { // try to find the index part and file part
            int possibleDelimiterIndex = Math.max(args.lastIndexOf(' '), args.lastIndexOf(','));
            possibleIndexListString = args.substring(0, possibleDelimiterIndex);
            if (possibleIndexListString.trim().isEmpty()) { // the only argument should be index
                possibleIndexListString = args;
            } else { // the last argument should be filePath
                possibleFilePath = args.substring(possibleDelimiterIndex + 1);
            }
        }

        // format Index List
        try {
            possibleIndexListString = wrapRangeIndexList(ParserUtil.parseRangeIndexList(possibleIndexListString));
        } catch (IllegalValueException ive) {
            possibleIndexListString = Arrays.stream(possibleIndexListString.split("(\\s|,)+"))
                .map(AutoComplete::formatRangeIndexString).filter(p -> !p.isEmpty()).collect(Collectors.joining(", "));
        }

        // format file path
        possibleFilePath = possibleFilePath.trim();

        return ExportCommand.COMMAND_WORD + " " + possibleIndexListString + "; " + possibleFilePath;
    }

    /**
     * Auto-completes import command.
     */
    private static String importCommandAutoComplete(String args) {
        return ImportCommand.COMMAND_WORD + " " + args.trim();
    }

    /**
     * Auto-completes login command.
     */
    private static String loginCommandAutoComplete(String args) {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_USERNAME, PREFIX_PASSWORD);
        return LoginCommand.COMMAND_WORD + " "
            + formatPrefixWithArgs(argumentMultimap, PREFIX_USERNAME) + " "
            + formatPrefixWithArgs(argumentMultimap, PREFIX_PASSWORD);
    }

    /**
     * Auto-completes remark command.
     */
    private static String remarkCommandAutoComplete(String args, List<ReadOnlyPerson> filteredPersonList) {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_REMARK);

        String indexString = argMultimap.getPreamble();
        Index index;
        try {
            index = ParserUtil.parseIndex(indexString);
        } catch (IllegalValueException ive) {
            // if the index is invalid
            String restArgs = args.replaceFirst(indexString, "").trim();
            return RemarkCommand.COMMAND_WORD + " " + formatSingleIndexString(indexString) + " " + restArgs;
        }

        // auto fill remark field
        ReadOnlyPerson person = filteredPersonList.get(index.getZeroBased());
        String prefixWithArgs = formatPrefixWithArgs(argMultimap, PREFIX_REMARK, person);

        return RemarkCommand.COMMAND_WORD + " " + indexString +  " " + prefixWithArgs;
    }

    /**
     * Auto-completes select command.
     */
    private static String selectCommandAutoComplete(String args) {
        return SelectCommand.COMMAND_WORD +  " " + formatSingleIndexString(args);
    }

    /**
     * Auto-completes sort command.
     */
    private static String sortCommandAutoComplete(String args) {
        StringBuilder formattedArg = new StringBuilder();
        if (args.contains(PREFIX_NAME_STRING)) {
            formattedArg.append(PREFIX_NAME_STRING);
        } else if (args.contains(PREFIX_PHONE_STRING)) {
            formattedArg.append(PREFIX_PHONE_STRING);
        }
        if (args.contains("reverse")) {
            formattedArg.append(" ").append("reverse");
        }
        return SortCommand.COMMAND_WORD + " " + formattedArg.toString();
    }

    /**
     * Auto-completes unknown command without arguments.
     */
    private static String unknownCommandAutoComplete(String input) {
        String trimmedInput = input.trim();
        if (trimmedInput.isEmpty()) {
            return "";
        } else {
            return unknownCommandAutoComplete(trimmedInput);
        }
    }

    /**
     * Auto-completes unknown command.
     */
    private static String unknownCommandAutoComplete(String command, String args) {
        List<String> prefixMatch = prefixMatch(getSystemCommandWords(), command);
        if (prefixMatch.size() == 1) { // only prefix-match
            return prefixMatch.get(0) + " " + args.trim();
        } else if (prefixMatch.size() > 1) { // multiple prefix-matches
            prefixMatch.sort(String::compareTo);
            return promptForPossibleCommand(prefixMatch);
        } else { // no prefix-match
            List<String> fuzzyMatch = fuzzyMatch(getSystemCommandWords(), command);
            if (fuzzyMatch.isEmpty()) {
                return command + args;
            }
            fuzzyMatch.sort(String::compareTo);
            return promptForPossibleCommand(fuzzyMatch);
        }
    }

    /**
     * Formats the arguments into "PREFIX/ARGS" form. If the argument is invalid, it will be dropped.
     */
    private static String formatPrefixWithArgs(ArgumentMultimap argMultimap, final Prefix prefix) {
        try {
            return parseArgOfPrefix(argMultimap.getAllValues(prefix), prefix);
        } catch (IllegalValueException ive) {
            return prefix.getPrefix();
        }
    }

    /**
     * Formats the argument into "PREFIX/ARGS" form. If the {@code ArgumentMultimap} does not contain the field
     * or the argument is invalid, replace the argument with {@code ReadOnlyPerson}'s corresponding field.
     */
    private static String formatPrefixWithArgs(ArgumentMultimap argMultimap, Prefix prefix, ReadOnlyPerson person) {
        String prefixWithArgs = formatPrefixWithArgs(argMultimap, prefix);
        if (prefixWithArgs.equals(prefix.getPrefix())) { // no input, read field info from person
            prefixWithArgs += getPersonFieldOfPrefix(person, prefix);
        }
        if (prefix.equals(PREFIX_TAG)) {
            // insert tag prefix into each tag
            prefixWithArgs = prefixWithArgs.replace(" ", " " + PREFIX_TAG_STRING);
        }
        return prefixWithArgs;
    }

    /**
     * Tests whether the argument is valid for certain {@code Prefix}.
     */
    private static String parseArgOfPrefix(List<String> argList, Prefix prefix) throws IllegalValueException {
        if (argList.isEmpty()) {
            return prefix.getPrefix();
        }
        Optional<String> firstArg = Optional.of(argList.get(0));
        switch (prefix.getPrefix()) {
        case PREFIX_ADDRESS_STRING:
            return prefix.getPrefix() + ParserUtil.parseAddress(firstArg).map(p -> p.value).orElse("");
        case PREFIX_BIRTHDAY_STRING:
            return prefix.getPrefix() + ParserUtil.parseBirthday(firstArg).map(p -> p.value).orElse("");
        case PREFIX_EMAIL_STRING:
            return prefix.getPrefix() + ParserUtil.parseEmail(firstArg).map(p -> p.value).orElse("");
        case PREFIX_FACEBOOK_STRING:
            return prefix.getPrefix() + ParserUtil.parseFacebook(firstArg).map(p -> p.value).orElse("");
        case PREFIX_NAME_STRING:
            return prefix.getPrefix() + ParserUtil.parseName(firstArg).map(p -> p.fullName).orElse("");
        case PREFIX_PHONE_STRING:
            return prefix.getPrefix() + ParserUtil.parsePhone(firstArg).map(p -> p.value).orElse("");
        case PREFIX_TAG_STRING:
            String tagString = ParserUtil.parseTags(argList).stream().map(p -> (prefix + p.tagName))
                .collect(Collectors.joining(" "));
            return tagString.isEmpty() ? PREFIX_TAG_STRING : tagString;
        case PREFIX_REMARK_STRING:
            return prefix.getPrefix() + firstArg.get().trim();
        case PREFIX_USERNAME_STRING:
            return prefix.getPrefix() + firstArg.orElse("");
        case PREFIX_PASSWORD_STRING:
            return prefix.getPrefix() + firstArg.orElse("");
        default:
            return prefix.getPrefix();
        }
    }

    /**
     * @return the corresponding field of a {@code ReadOnlyPerson} based on a {@code Prefix}.
     * If there are multiple values in one field, they will be separated by a space.
     */
    private static String getPersonFieldOfPrefix(ReadOnlyPerson person, Prefix prefix) {
        requireNonNull(person);
        requireNonNull(prefix);

        switch (prefix.getPrefix()) {
        case PREFIX_NAME_STRING:
            return person.getName().fullName;
        case PREFIX_PHONE_STRING:
            return person.getPhone().value;
        case PREFIX_EMAIL_STRING:
            return person.getEmail().value;
        case PREFIX_ADDRESS_STRING:
            return person.getAddress().value;
        case PREFIX_BIRTHDAY_STRING:
            return person.getBirthday().value;
        case PREFIX_FACEBOOK_STRING:
            return  person.getFacebook().value;
        case PREFIX_REMARK_STRING:
            return person.getRemark().value;
        case PREFIX_TAG_STRING:
            return person.getTags().stream().map(t -> t.tagName).collect(Collectors.joining(" "));
        default:
            return "";
        }
    }

    /**
     * Formats the argument into all-digit form.
     */
    private static String formatSingleIndexString(String arg) {
        return arg.replaceAll("\\D", "");
    }

    /**
     * Formats the argument into range index list form.
     */
    private static String formatRangeIndexString(String arg) {
        String numString = arg.replaceAll("((?!\\d|-).)+", "");
        Matcher rangeIndexMatcher = Pattern.compile("(?<first>\\d+)-(?<second>\\d+).*").matcher(numString);
        if (rangeIndexMatcher.matches()) { // range index
            int firstNum = Integer.parseInt(rangeIndexMatcher.group("first"));
            int secondNum = Integer.parseInt(rangeIndexMatcher.group("second"));
            return Math.min(firstNum, secondNum) + "-" + Math.max(firstNum, secondNum);
        } else { // single index, including number only on one side of '-'
            return numString.replaceAll("\\D+", "");
        }
    }

    /**
     * Wraps the Index list by range index.
     */
    private static String wrapRangeIndexList(List<Index> indexList) {
        if (indexList.isEmpty()) {
            return "";
        }
        List<Integer> oneBasedIndexList = indexList.stream().map(Index::getOneBased).collect(Collectors.toList());
        Collections.sort(oneBasedIndexList);

        List<List<Integer>> splitList = new ArrayList<>();
        List<Integer> currentList = new ArrayList<>();
        for (int i = 0; i < oneBasedIndexList.size() - 1; i++) {
            currentList.add(oneBasedIndexList.get(i));
            if (oneBasedIndexList.get(i + 1) != oneBasedIndexList.get(i) + 1) {
                splitList.add(currentList);
                currentList = new ArrayList<>();
            }
        }
        currentList.add(oneBasedIndexList.get(oneBasedIndexList.size() - 1));
        splitList.add(currentList);

        List<String> oneBasedRangeIndexStrings = new ArrayList<>();
        for (List<Integer> oneBasedRangeIndex : splitList) {
            if (oneBasedRangeIndex.size() == 1) { // single index
                oneBasedRangeIndexStrings.add(String.valueOf(oneBasedRangeIndex.get(0)));
            } else {
                int first = oneBasedRangeIndex.get(0);
                int last = oneBasedRangeIndex.get(oneBasedRangeIndex.size() - 1);
                oneBasedRangeIndexStrings.add(first + "-" + last);
            }
        }

        return oneBasedRangeIndexStrings.stream().collect(Collectors.joining(", "));
    }

    /**
     * Formats the suggestion {@code String} of possible commands.
     */
    private static String promptForPossibleCommand(List<String> possibleCommands) {
        String suggestionFormat = "Do you mean: %1$s ?";
        return String.format(suggestionFormat, possibleCommands.stream().collect(Collectors.joining(" or ")));
    }

    /**
     * Finds all test in array that have prefix of tester and returns the result as a List.
     */
    private static List<String> prefixMatch(String[] tests, String tester) {
        HashSet<String> prefixMatchesSet = new HashSet<>();
        Pattern prefixPattern = Pattern.compile("^" + tester + ".*$");
        prefixMatchesSet.addAll(Arrays.stream(tests).filter(p -> prefixPattern.matcher(p).matches())
            .collect(Collectors.toList()));
        List<String> prefixMatches = new ArrayList<>();
        prefixMatches.addAll(prefixMatchesSet);
        return prefixMatches;
    }

    /**
     * Finds the fuzzy match of tester in the Array.
     */
    private static List<String> fuzzyMatch(String[] tests, String tester) {
        HashSet<String> bestMatchesSet = new HashSet<>();
        bestMatchesSet.addAll(Arrays.stream(tests)
            .filter(p -> levenshteinDistance(tester, p) <= (p.length() / 2)) // fuzzy match cut-off
            .collect(Collectors.toList()));
        List<String> bestMatches = new ArrayList<>();
        bestMatches.addAll(bestMatchesSet);
        return bestMatches;
    }

    /**
     * Calculate the levenshtein Distance of two {@code String}s.
     */
    private static int levenshteinDistance(String s, String t) {
        if (s.length() == 0) {
            return t.length();
        } else if (t.length() == 0) {
            return s.length();
        } else if (s.charAt(0) == t.charAt(0)) {
            return levenshteinDistance(s.substring(1), t.substring(1));
        }
        int substitute = levenshteinDistance(s.substring(1), t.substring(1));
        int delete = levenshteinDistance(s, t.substring(1));
        int insert = levenshteinDistance(s.substring(1), t);
        return minimum(substitute, delete, insert) + 1;
    }

    /**
     * @return minimum number of Integers.
     */
    private static int minimum(Integer... integers) {
        return Arrays.stream(integers).reduce(Math::min).orElse(-1);
    }
}
```
###### /java/seedu/address/logic/commands/ExportCommand.java
``` java
package seedu.address.logic.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.storage.XmlPersonListStorage;

/**
 * Export a person list to a specified save file.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Exports persons identified by the index numbers used in the last person listing to a specified save file."
        + "\n" + "Parameters: INDEXES; FILE_PATH\n"
        + "(Indexes must be positive integers or two indexes linked by \"-\" separated by commas or spaces)\n"
        + "Example: " + COMMAND_WORD + " 1-3, 4 6; /Users/[User Name]/Desktop/persons.xml";

    public static final String MESSAGE_EXPORT_PERSON_SUCCESS = "Your contacts: %1$shave been exported to file: %2$s";

    private final List<Index> targetIndexes;
    private final String filePath;

    public ExportCommand(List<Index> targetIndexes, String filePath) {
        this.targetIndexes = targetIndexes;
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyPerson> personsToSave = null;

        try {
            personsToSave = getPersonsToSave();
        } catch (PersonNotFoundException pnfe) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        XmlPersonListStorage xmlPersonListStorage = new XmlPersonListStorage(this.filePath);
        try {
            xmlPersonListStorage.savePersonList(personsToSave);
        } catch (IOException ioe) {
            throw new CommandException(ioe.getMessage());
            //TODO: better error handling
        }
        return new CommandResult(String.format(
            MESSAGE_EXPORT_PERSON_SUCCESS, getPersonNameList(personsToSave), this.filePath));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ExportCommand // instanceof handles nulls
            && this.targetIndexes.equals(((ExportCommand) other).targetIndexes) // state check
            && this.filePath.equals(((ExportCommand) other).filePath));
    }

    private List<ReadOnlyPerson> getPersonsToSave() throws PersonNotFoundException {
        List<ReadOnlyPerson> lastShowList = model.getFilteredPersonList();
        List<ReadOnlyPerson> personsToSave = new ArrayList<>();
        for (Index index : this.targetIndexes) {
            if (index.getZeroBased() >= lastShowList.size()) {
                throw new PersonNotFoundException();
            }
            personsToSave.add(lastShowList.get(index.getZeroBased()));
        }
        return personsToSave;
    }

    private String getPersonNameList(List<ReadOnlyPerson> personsToSave) {
        StringBuilder personNameBuilder = new StringBuilder();
        for (ReadOnlyPerson person : personsToSave) {
            personNameBuilder.append(person.getName().fullName).append(", ");
        }
        return personNameBuilder.deleteCharAt(personNameBuilder.lastIndexOf(",")).toString();
    }
}
```
###### /java/seedu/address/logic/commands/ImportCommand.java
``` java
package seedu.address.logic.commands;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.storage.XmlPersonListStorage;

/**
 * Imports a list of persons in a save file generated by Export command.
 */
public class ImportCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": adds a list of persons in an export XML file.\n"
        + "Parameters: FILEPATH\n"
        + "Example: " + COMMAND_WORD + " /Users/[USER NAME]/Documents/sharePersons.xml";

    public static final String MESSAGE_IMPORT_SUCCESS =  "%1$s contacts have been imported into your AddressBook!";
    public static final String MESSAGE_DUPLICATED_PERSON_IN_ADDRESS_BOOK_WARNING =
        "Duplicated persons are found in import process. Duplicated information is ignored.\n";
    public static final String MESSAGE_DUPLICATED_PERSON_IN_FILE =
        "The file: %1$s contains duplicated person information!";
    public static final String MESSAGE_EMPTY_FILE = "No person list data are found in file: %1$s";
    public static final String MESSAGE_INVALID_XML_FILE =
        "The file: %1$s is not a valid XML file!\n" + "Make sure you are inputting the right file!";
    public static final String MESSAGE_MISSING_FILE =
        "The file: %1$s cannot be found!\n" + "Make sure you are inputting the right file!";

    private final String filePath;

    public ImportCommand(String filePath) {
        this.filePath = filePath;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        boolean foundDuplicatedPersonsInFile = false;
        XmlPersonListStorage personListStorage = new XmlPersonListStorage(this.filePath);
        Optional<UniquePersonList> optionalPersonList = Optional.empty();
        try {
            optionalPersonList = personListStorage.readPersonList();
        } catch (DataConversionException dce) {
            throw new CommandException(String.format(MESSAGE_INVALID_XML_FILE, filePath));
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(String.format(MESSAGE_DUPLICATED_PERSON_IN_FILE, filePath));
        } catch (FileNotFoundException fnfe) {
            throw new CommandException(String.format(MESSAGE_MISSING_FILE, filePath));
        }
        if (!optionalPersonList.isPresent()) {
            throw new CommandException(String.format(MESSAGE_EMPTY_FILE, filePath));
        }

        List<ReadOnlyPerson> personList = optionalPersonList.get().asObservableList();
        for (ReadOnlyPerson person : personList) {
            try {
                this.model.addPerson(person);
            } catch (DuplicatePersonException dpe) {
                foundDuplicatedPersonsInFile = true;
            }
        }

        return new CommandResult(getDuplicatedPersonWarning(foundDuplicatedPersonsInFile)
            + String.format(MESSAGE_IMPORT_SUCCESS, personList.size()));
    }

    @Override
    public boolean equals(Object other) {
        return this == other
            || other instanceof ImportCommand
            && this.filePath.equals(((ImportCommand) other).filePath);
    }

    private String getDuplicatedPersonWarning(boolean foundDuplicatedPerson) {
        return foundDuplicatedPerson
               ? MESSAGE_DUPLICATED_PERSON_IN_ADDRESS_BOOK_WARNING
               : "";
    }

}
```
###### /java/seedu/address/logic/LogicManager.java
``` java
    @Override
    public String autoCompleteCommand(String command) {
        return AutoComplete.autoComplete(command, model.getFilteredPersonList());
    }
```
###### /java/seedu/address/logic/parser/ExportCommandParser.java
``` java
/**
 * Parses input argument to an index list and file path and creates a new ImportCommand.
 */
public class ExportCommandParser implements Parser<ExportCommand> {

    public static final String MISSING_FILE_PATH = "Missing file path!\n";

    private static final Pattern INDEXES_AND_FILEPATH =
        Pattern.compile("(?<oneBasedIndexListString>.(((\\d)*(,)*(\\s)*(-)*)+));(?<filePath>.*)");

    @Override
    public ExportCommand parse(String args) throws ParseException {
        final Matcher matcher = INDEXES_AND_FILEPATH.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        }
        final String oneBasedIndexListString = matcher.group("oneBasedIndexListString");
        final String filePath = matcher.group("filePath").trim();

        // parse indexes
        List<Index> indexes;
        try {
            indexes = ParserUtil.parseRangeIndexList(oneBasedIndexListString);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        }

        // check filePath
        if (filePath.isEmpty()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MISSING_FILE_PATH + ExportCommand.MESSAGE_USAGE));
        }

        return new ExportCommand(indexes, addXmlExtensionToFilePath(filePath));
    }

    /**
     *  Add ".xml" extension if input filePath do not have one.
     */
    private String addXmlExtensionToFilePath(String filePath) {

        if (!filePath.contains(".")) {
            return filePath + ".xml";
        }
        String extension = "";
        if (filePath.charAt(filePath.lastIndexOf('.') - 1) != '/'
            && filePath.charAt(filePath.lastIndexOf('.') - 1) != '\\') {
            extension = filePath.substring(filePath.lastIndexOf('.') + 1, filePath.length());
        }
        if (!extension.equalsIgnoreCase("xml")) {
            return filePath + ".xml";
        }
        return filePath;
    }
}
```
###### /java/seedu/address/logic/parser/ImportCommandParser.java
``` java
/**
 * Parses input argument to a file path and creates a new ImportCommand.
 */
public class ImportCommandParser implements Parser<ImportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of ImportCommand
     * and returns an ImportCommand object for execution.
     * @throws ParseException if user's input does not confirm the expected format
     */
    public ImportCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
        }

        return new ImportCommand(trimmedArgs);
    }
}
```
###### /java/seedu/address/model/person/Address.java
``` java
    /**
     * Returns an empty Address object.
     */
    public static Address getEmptyAddress() {
        return new Address();
    }
```
###### /java/seedu/address/model/person/Birthday.java
``` java
    /**
     * Returns an empty Birthday object.
     */
    public static Birthday getEmptyBirthday() {
        return new Birthday();
    }
```
###### /java/seedu/address/model/person/Facebook.java
``` java
/**
 * Represents a Person's Facebook ID in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidFacebookId(String)}
 */
public class Facebook {
    public static final String MESSAGE_FACEBOOK_CONSTRAINTS =
        "Person facebook should be in the format ";
    public static final String FACEBOOK_ID_VALIDATION_REGEX = "\\d+";

    public final String value;

    /**
     * Validates given facebook.
     *
     * @throws IllegalValueException if given facebook string is invalid.
     */
    public Facebook(String facebookId) throws IllegalValueException {
        requireNonNull(facebookId);
        if (facebookId.isEmpty()) {
            this.value = "";
            return;
        }
        String trimmedFacebookId = facebookId.trim();
        if (!isValidFacebookId(trimmedFacebookId)) {
            throw new IllegalValueException(MESSAGE_FACEBOOK_CONSTRAINTS);
        }
        this.value = trimmedFacebookId;
    }

    /**
     * Creates Birthday that is empty.
     */
    private Facebook() {
        this.value = "";
    }

    /**
     * Returns if a given string is a valid person email.
     */
    public static boolean isValidFacebookId(String test) {
        return test.matches(FACEBOOK_ID_VALIDATION_REGEX);
    }

    /**
     * Returns an empty Birthday object.
     */
    public static Facebook getEmptyFacebook() {
        return new Facebook();
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Facebook // instanceof handles nulls
            && this.value.equals(((Facebook) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### /java/seedu/address/storage/PersonListStorage.java
``` java
package seedu.address.storage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * Represents a storage for a list of {@link seedu.address.model.person.Person}.
 */
public interface PersonListStorage {

    /**
     * Returns the file path of the data file.
     */
    public String getPersonListFilePath();

    /**
     * Returns person list data as {@link UniquePersonList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    public Optional<UniquePersonList> readPersonList()
        throws DataConversionException, IOException, DuplicatePersonException;

    /**
     * @see #readPersonList()
     */
    public Optional<UniquePersonList> readPersonList(String filePath)
        throws DataConversionException, FileNotFoundException, DuplicatePersonException;

    /**
     * Saves the given {@link ReadOnlyPerson} to the storage.
     * @param persons cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    public void savePersonList(List<ReadOnlyPerson> persons) throws IOException;

    /**
     * @see #savePersonList(List)
     */
    public void savePersonList(List<ReadOnlyPerson> persons, String filePath) throws IOException;

    /**
     * @see #savePersonList(List)
     */
    public void savePersonList(UniquePersonList persons) throws IOException;

    /**
     * @see #savePersonList(List)
     */
    public void savePersonList(UniquePersonList persons, String filePath) throws IOException;

}
```
###### /java/seedu/address/storage/XmlFileStorage.java
``` java
    /**
     * Returns person list in the file or an empty address book
     */
    public static XmlSerializablePersonList loadPersonListFromSaveFile(File file) throws DataConversionException,
        FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializablePersonList.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
```
###### /java/seedu/address/storage/XmlPersonListStorage.java
``` java
package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * A class to access Person data stored as an xml file on a hard disk.
 */
public class XmlPersonListStorage implements PersonListStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlPersonListStorage.class);

    private String filePath;

    public XmlPersonListStorage(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getPersonListFilePath() {
        return this.filePath;
    }

    @Override
    public Optional<UniquePersonList> readPersonList()
        throws DataConversionException, FileNotFoundException,  DuplicatePersonException {
        return readPersonList(this.filePath);
    }

    @Override
    public Optional<UniquePersonList> readPersonList(String filePath)
        throws DataConversionException, FileNotFoundException, DuplicatePersonException {
        requireNonNull(filePath);

        File file = new File(filePath);
        if (!file.exists()) {
            logger.info("PersonList file " + filePath + " not found");
            throw new FileNotFoundException();
        }
        UniquePersonList persons;
        try {
            persons = XmlFileStorage.loadPersonListFromSaveFile(new File(filePath)).getPersons();
        } catch (DuplicatePersonException dpe) {
            logger.info("PersonList file " + filePath + " contains duplicated persons");
            throw dpe;
        }
        return Optional.of(persons);
    }

    @Override
    public void savePersonList(List<ReadOnlyPerson> persons) throws IOException {
        savePersonList(persons, this.filePath);
    }

    /**
     * Similar to {@link #savePersonList(List)}
     * @param filePath location of the data. Cannot be null
     */
    public void savePersonList(List<ReadOnlyPerson> persons, String filePath) throws IOException {
        requireNonNull(persons);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializablePersonList(persons));
    }

    /**
     * Similar to {@link #savePersonList(List)}
     * @param persons represents the {@link UniquePersonList} to be saved
     */
    public void savePersonList(UniquePersonList persons) throws IOException {
        savePersonList(persons, this.filePath);
    }

    /**
     * Similar to {@link #savePersonList(UniquePersonList)}
     * @param filePath location of the data. Cannot be null
     */
    public void savePersonList(UniquePersonList persons, String filePath) throws IOException {
        savePersonList(persons.asObservableList(), filePath);
    }
}
```
###### /java/seedu/address/storage/XmlSerializablePersonList.java
``` java
package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * An Immutable Person List that is serializable to xml file.
 */
@XmlRootElement(name = "personList")
public class XmlSerializablePersonList extends XmlSerializableData {

    @XmlElement
    private List<XmlAdaptedPerson> persons;

    /**
     * Creates an empty XmlSerializablePersonList.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializablePersonList() {
        persons = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializablePersonList(List<ReadOnlyPerson> persons) {
        this();
        this.persons.addAll(persons.stream().map(XmlAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Conversion
     */
    public XmlSerializablePersonList(UniquePersonList persons) {
        this(persons.asObservableList());
    }

    public UniquePersonList getPersons() throws DuplicatePersonException {
        final List<ReadOnlyPerson> persons = this.persons.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toList());
        UniquePersonList uniquePersons = new UniquePersonList();
        uniquePersons.setPersons(persons);
        return uniquePersons;
    }

}
```
###### /java/seedu/address/ui/CommandBox.java
``` java
    /**
     * Auto-completes the command in the command box.
     */
    private void autoComplete() {
        replaceText(logic.autoCompleteCommand(commandTextField.getText()));
    }
```
