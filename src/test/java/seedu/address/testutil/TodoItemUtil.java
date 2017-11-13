//@@author Hailinx
package seedu.address.testutil;

import java.time.LocalDateTime;

import seedu.address.model.person.TodoItem;

/**
 * A utility class for {@code LocalDateTime}
 */
public class TodoItemUtil {

    public static final LocalDateTime EARLY_TIME_ONE = LocalDateTime.of(2017, 1, 1, 12, 0);

    public static final LocalDateTime LATE_TIME_ONE = LocalDateTime.of(2017, 12, 1, 12, 0);

    public static final LocalDateTime EARLY_TIME_TWO = LocalDateTime.of(2018, 1, 1, 12, 0);

    public static final LocalDateTime LATE_TIME_TWO = LocalDateTime.of(2018, 12, 1, 12, 0);

    public static final LocalDateTime TIME_THREE = LocalDateTime.of(2017, 1, 1, 12, 0);

    /**
     * @return a todoItem object
     */
    public static TodoItem getTodoItemOne() {
        TodoItem todoItem = null;
        try {
            todoItem =  new TodoItem(EARLY_TIME_ONE, LATE_TIME_ONE, "task: item one");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return todoItem;
    }

    /**
     * @return a todoItem object
     */
    public static TodoItem getTodoItemTwo() {
        TodoItem todoItem = null;
        try {
            todoItem =  new TodoItem(EARLY_TIME_TWO, LATE_TIME_TWO, "task: item two");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return todoItem;
    }

    /**
     * @return a todoItem object with field {@code end} null.
     */
    public static TodoItem getTodoItemThree() {
        TodoItem todoItem = null;
        try {
            todoItem =  new TodoItem(TIME_THREE, null, "task: item three");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return todoItem;
    }

}
