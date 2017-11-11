package seedu.address.logic.parser;

import static seedu.address.logic.parser.AddCommandParser.arePrefixesPresent;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.ParserUtil.isParsableAddressTillEnd;
import static seedu.address.logic.parser.ParserUtil.isParsableEmail;
import static seedu.address.logic.parser.ParserUtil.isParsableIndex;
import static seedu.address.logic.parser.ParserUtil.isParsableName;
import static seedu.address.logic.parser.ParserUtil.isParsablePhone;
import static seedu.address.logic.parser.ParserUtil.parseAddressTillEnd;
import static seedu.address.logic.parser.ParserUtil.parseFirstEmail;
import static seedu.address.logic.parser.ParserUtil.parseFirstIndex;
import static seedu.address.logic.parser.ParserUtil.parseFirstPhone;
import static seedu.address.logic.parser.ParserUtil.parseRemainingName;
import static seedu.address.logic.parser.ParserUtil.parseRemoveAddressTillEnd;
import static seedu.address.logic.parser.ParserUtil.parseRemoveFirstEmail;
import static seedu.address.logic.parser.ParserUtil.parseRemoveFirstIndex;
import static seedu.address.logic.parser.ParserUtil.parseRemoveFirstPhone;
import static seedu.address.logic.parser.ParserUtil.parseRemoveTags;
import static seedu.address.model.ModelManager.hasAnyExistingTags;
import static seedu.address.model.ModelManager.isKnownTag;
import static seedu.address.model.tag.Tag.TAG_REPLACEMENT_REGEX;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javafx.util.Pair;

/**
 * Utility methods for parsing unformatted model data for command execution.
 */
public class ModelParserUtil {

    /**
     * Returns an optional index string after parsing a mandatory index.
     */
    public static Optional<Pair<String, String>> parseMandatoryIndex(String indexString, int size) {
        if (isParsableIndex(indexString, size)) {
            String index = Integer.toString(parseFirstIndex(indexString, size));
            return Optional.of(new Pair<>(index, parseRemoveFirstIndex(indexString, size).trim()));
        }
        return Optional.empty();
    }

    /**
     * Returns an optional pair of phone string and the remaining string after parsing a mandatory phone.
     */
    public static Optional<Pair<String, String>> parseMandatoryPhone(String remaining) {
        if (isParsablePhone(remaining)) {
            return Optional.of(new Pair<>(parseFirstPhone(remaining),
                    parseRemoveFirstPhone(remaining).trim().replaceAll(PREFIX_PHONE.toString(), "")));
        }
        return Optional.empty();
    }

    /**
     * Returns an optional pair of email string and the remaining string after parsing a mandatory email.
     */
    public static Optional<Pair<String, String>> parseMandatoryEmail(String remaining) {
        if (isParsableEmail(remaining)) {
            return Optional.of(new Pair<>(parseFirstEmail(remaining),
                    parseRemoveFirstEmail(remaining).trim().replaceAll(PREFIX_EMAIL.toString(), "")));
        }
        return Optional.empty();
    }

    /**
     * Returns an optional pair of a string that could contain tags and the remaining string after parsing the possible.
     */
    public static Optional<Pair<String, String>> parsePossibleTagWords(String remaining) {
        StringBuilder tagBuilder = new StringBuilder();
        String[] words = remaining.split(" ");
        if (hasAnyExistingTags(words)) {
            List<String> tagsAdded = new ArrayList<>();
            Arrays.stream(words).forEach(word -> {
                if (isKnownTag(word) && !tagsAdded.contains(word)) {
                    tagBuilder.append(" " + PREFIX_TAG + word.trim());
                    tagsAdded.add(word);
                }
            });
            return Optional.of(new Pair<>(tagBuilder.toString(), parseRemoveTags(remaining, tagsAdded)));
        }
        return Optional.empty();
    }

    /**
     * Returns an optional pair of a string that contains tag prefixes and
     * the remaining string after parsing the remaining.
     */
    public static Optional<Pair<String, String>> parseExistingTagPrefixes(String remaining) {
        StringBuilder tagBuilder = new StringBuilder();
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                remaining, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_REMARK, PREFIX_TAG);
        if (arePrefixesPresent(argMultimap, PREFIX_TAG) && argMultimap.getValue(PREFIX_TAG).isPresent()) {
            List<String> tagsAdded = new ArrayList<>();
            argMultimap.getAllValues(PREFIX_TAG).stream().forEach(rawTag -> {
                String parsedTag = rawTag.replaceAll(TAG_REPLACEMENT_REGEX, "");
                tagBuilder.append(" " + PREFIX_TAG + parsedTag);
                tagsAdded.add(PREFIX_TAG + rawTag);
            });
            return Optional.of(new Pair<>(tagBuilder.toString(), parseRemoveTags(remaining, tagsAdded)));
        }
        return Optional.empty();
    }

    /**
     * Returns an optional pair of a string that contains remark prefixes and
     * the remaining string after parsing the remaining.
     */
    public static Optional<Pair<String, String>> parseExistingRemarkPrefixes(String remaining) {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                remaining, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_REMARK, PREFIX_TAG);
        if (arePrefixesPresent(argMultimap, PREFIX_REMARK) && argMultimap.getValue(PREFIX_REMARK).isPresent()) {
            String remark = argMultimap.getValue(PREFIX_REMARK).get();
            return Optional.of(new Pair<>(remark,
                    remaining.replace(PREFIX_REMARK.toString().concat(remark), "").trim()));
        }
        return Optional.empty();
    }

    /**
     * Returns an optional pair of address string and the remaining string after parsing a mandatory address.
     */
    public static Optional<Pair<String, String>> parseMandatoryAddress(String remaining) {
        if (isParsableAddressTillEnd(remaining)) {
            return Optional.of(new Pair<>(parseAddressTillEnd(remaining),
                    parseRemoveAddressTillEnd(remaining).trim().replaceAll(PREFIX_ADDRESS.toString(), "")));
        }
        return Optional.empty();
    }

    /**
     * Returns an optional name string after parsing a mandatory name.
     */
    public static Optional<String> parseMandatoryName(String remaining) {
        if (isParsableName(remaining)) {
            return Optional.of(parseRemainingName(remaining));
        }
        return Optional.empty();
    }

    /**
     * Returns a parsed argument string containing pre-parsed arguments.
     */
    public static String buildParsedArguments(String name, String phone, String email,
                                              String remark, String address, String tags) {
        return buildParsedArguments("", name, phone, email, remark, address, tags);
    }

    /**
     * Returns a parsed argument string containing pre-parsed arguments or null if there are no supplied values.
     */
    public static String buildParsedArguments(String index, String name, String phone, String email,
                                               String remark, String address, String tags) {
        if (name.trim().isEmpty() && phone.trim().isEmpty() && email.trim().isEmpty()
                && remark.trim().isEmpty() && address.trim().isEmpty() && tags.trim().isEmpty()) {
            return null;
        }
        StringBuilder parsedArgs = new StringBuilder();
        if (!index.trim().isEmpty()) {
            parsedArgs.append(" ".concat(index));
        }
        if (!name.trim().isEmpty()) {
            parsedArgs.append(" ".concat(PREFIX_NAME.toString()).concat(name));
        }
        if (!phone.trim().isEmpty()) {
            parsedArgs.append(" ".concat(PREFIX_PHONE.toString()).concat(phone));
        }
        if (!email.trim().isEmpty()) {
            parsedArgs.append(" ".concat(PREFIX_EMAIL.toString()).concat(email));
        }
        if (!remark.trim().isEmpty()) {
            parsedArgs.append(" ".concat(PREFIX_REMARK.toString()).concat(remark));
        }
        if (!address.isEmpty()) {
            parsedArgs.append(" ".concat(PREFIX_ADDRESS.toString()).concat(address));
        }
        if (!tags.trim().isEmpty()) {
            parsedArgs.append(tags);
        }
        return parsedArgs.toString();
    }
}
