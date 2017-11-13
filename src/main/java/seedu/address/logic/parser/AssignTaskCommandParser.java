package seedu.address.logic.parser;

//@@author Esilocke

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TARGET;

import java.util.ArrayList;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AssignCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/** Parses input arguments, and creates a new {@code AssignCommand} object**/
public class AssignTaskCommandParser implements Parser<AssignCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AssignCommand
     * and returns an AssignCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AssignCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TARGET);
        if (!argMultimap.getValue(PREFIX_TARGET).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignCommand.MESSAGE_USAGE));
        }
        String target = argMultimap.getValue(PREFIX_TARGET).get();
        String persons = argMultimap.getPreamble();
        ArrayList<Index> targetIndexes = ParserUtil.parseIndexes(target);
        ArrayList<Index> personIndexes = ParserUtil.parseIndexes(persons);
        if (targetIndexes.size() != 1) {
            throw new ParseException(AssignCommand.MESSAGE_INVALID_TARGET_ARGS);
        } else if (personIndexes.size() < 1) {
            throw new ParseException(AssignCommand.MESSAGE_INVALID_PERSONS_ARGS);
        }
        Index taskIndex = targetIndexes.get(0);
        return new AssignCommand(personIndexes, taskIndex);
    }
}
