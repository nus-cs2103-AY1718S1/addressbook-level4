//@@author hxy0229
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_GROUP_NAME_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.GroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.Group;
import seedu.address.model.person.Name;

/** checkstyle comment */
public class GroupCommandParser implements Parser<GroupCommand> {
    @Override
    public GroupCommand parse(String args) throws ParseException {
        try {
            String trimmedArgs = args.trim();
            if (trimmedArgs.isEmpty()) {
                //System.out.println("Caught you up!!!");
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupCommand.MESSAGE_USAGE));
            }
            String[] indices = trimmedArgs.split("\\s+");

            String groupName = indices[0];

            if (indices[0].matches("\\d")) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_GROUP_NAME_FORMAT, GroupCommand.MESSAGE_USAGE));
            }

            String[] realIndices = new String[indices.length - 1];

            for (int i = 0; i < indices.length - 1; i++) {
                realIndices[i] = indices[i + 1];
            }

            List<String> inputInString = Arrays.asList(realIndices);

            List<Index> input = new ArrayList<Index>();

            for (String ind : inputInString) {
                Index index = ParserUtil.parseIndex(ind);
                input.add(index);
            }

            Name name = new Name(groupName);
            return new GroupCommand(new Group(name), input);

        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupCommand.MESSAGE_USAGE));
        }
    }
}
//@@author
