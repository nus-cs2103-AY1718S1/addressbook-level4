//@@author hthjthtrh
package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;

/**
 * abstract class to assist UndoableCommand
 */
abstract class GroupTypeUndoableCommand extends UndoableCommand {

    protected Index undoGroupIndex;
}
//@@author
