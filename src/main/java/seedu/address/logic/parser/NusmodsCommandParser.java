package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.NusmodsCommand;
import seedu.address.logic.commands.NusmodsCommand.NusmodsDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author ritchielq
/**
 * Parses input arguments and creates a new NusmodsCommand object
 */
public class NusmodsCommandParser implements Parser<NusmodsCommand> {
    public static final Prefix PREFIX_TYPE = new Prefix("t/");
    public static final Prefix PREFIX_MODULE_CODE = new Prefix("m/");
    public static final Prefix PREFIX_DESIGN_LECTURE = new Prefix("dlec/");
    public static final Prefix PREFIX_LABORATORY = new Prefix("lab/");
    public static final Prefix PREFIX_LECTURE = new Prefix("lec/");
    public static final Prefix PREFIX_PACKAGED_LECTURE = new Prefix("plec/");
    public static final Prefix PREFIX_PACKAGED_TUTORIAL = new Prefix("ptut/");
    public static final Prefix PREFIX_RECITATION = new Prefix("rec/");
    public static final Prefix PREFIX_SECTIONAL_TEACHING = new Prefix("sec/");
    public static final Prefix PREFIX_SEMINAR = new Prefix("sem/");
    public static final Prefix PREFIX_TUTORIAL = new Prefix("tut/");
    public static final Prefix PREFIX_TUTORIAL2 = new Prefix("tut2/");
    public static final Prefix PREFIX_TUTORIAL3 = new Prefix("tut3/");

    /**
     * Parses the given {@code String} of arguments in the context of the NusmodsCommand
     * and returns an NusmodsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public NusmodsCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TYPE, PREFIX_MODULE_CODE, PREFIX_DESIGN_LECTURE,
                        PREFIX_LABORATORY, PREFIX_LECTURE, PREFIX_PACKAGED_LECTURE, PREFIX_PACKAGED_TUTORIAL,
                        PREFIX_RECITATION, PREFIX_SECTIONAL_TEACHING, PREFIX_SEMINAR, PREFIX_TUTORIAL,
                        PREFIX_TUTORIAL2, PREFIX_TUTORIAL3);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NusmodsCommand.MESSAGE_USAGE));
        }

        NusmodsDescriptor nusmodsDescriptor = new NusmodsDescriptor();
        argMultimap.getValue(PREFIX_TYPE).ifPresent(nusmodsDescriptor::setType);
        argMultimap.getValue(PREFIX_MODULE_CODE).ifPresent(nusmodsDescriptor::setModuleCode);
        argMultimap.getValue(PREFIX_DESIGN_LECTURE).ifPresent(nusmodsDescriptor::setDesignLecture);
        argMultimap.getValue(PREFIX_LABORATORY).ifPresent(nusmodsDescriptor::setLaboratory);
        argMultimap.getValue(PREFIX_LECTURE).ifPresent(nusmodsDescriptor::setLecture);
        argMultimap.getValue(PREFIX_PACKAGED_LECTURE).ifPresent(nusmodsDescriptor::setPackagedLecture);
        argMultimap.getValue(PREFIX_PACKAGED_TUTORIAL).ifPresent(nusmodsDescriptor::setPackagedTutorial);
        argMultimap.getValue(PREFIX_RECITATION).ifPresent(nusmodsDescriptor::setRecitation);
        argMultimap.getValue(PREFIX_SECTIONAL_TEACHING).ifPresent(nusmodsDescriptor::setSectionalTeaching);
        argMultimap.getValue(PREFIX_SEMINAR).ifPresent(nusmodsDescriptor::setSeminar);
        argMultimap.getValue(PREFIX_TUTORIAL).ifPresent(nusmodsDescriptor::setTutorial);
        argMultimap.getValue(PREFIX_TUTORIAL2).ifPresent(nusmodsDescriptor::setTutorial2);
        argMultimap.getValue(PREFIX_TUTORIAL3).ifPresent(nusmodsDescriptor::setTutorial3);

        if (!nusmodsDescriptor.isValidType()) {
            throw new ParseException(NusmodsCommand.MESSAGE_INVALID_TYPE);
        }

        return new NusmodsCommand(index, nusmodsDescriptor);
    }

}
