package seedu.address.logic;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.ui.CalendarView;

/**
 * Methods to update the calendar view
 */
public class UpdateCalendarView {

    /**
     * Update the view state for commands like
     * list, add, delete, edit, order
     *
     * @param calendarView
     */
    public static void updateViewState(CalendarView calendarView) {
        calendarView.setCurrentYearMonth(YearMonth.now());
        calendarView.populateCalendar(calendarView.getCurrentYearMonth(), null);
    }

    /**
     * Update the view state for find event command
     *
     * @param calendarView
     * @param model
     */
    public static void updateFindState(CalendarView calendarView, Model model) {
        List<ReadOnlyEvent> events = model.getFilteredEventList();
        if (events.size() != 0) {
            String findYearMonth = events.get(0).getDatetime().value.substring(3, 10);
            //Compare the initial YearMonth value whether is it the same as others on the list
            boolean changeSelectedYearMonth = events.stream()
                    .allMatch(e -> e.getDatetime().value.substring(3, 10).equals(findYearMonth));

            if (changeSelectedYearMonth) {
                calendarView.setCurrentYearMonth(YearMonth.parse(findYearMonth,
                        DateTimeFormatter.ofPattern("MM-yyyy")));
                calendarView.populateCalendar(calendarView.getCurrentYearMonth(), null);
            }
        }
    }

    /**
     * update the view state for select event command
     *
     * @param calendarView
     * @param model
     * @param targetIndex
     */
    public static void updateSelectState(CalendarView calendarView, Model model, Index targetIndex) {

        List<ReadOnlyEvent> lastShownList = model.getFilteredEventList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            return;
        }

        ReadOnlyEvent selectedEvent = lastShownList.get(targetIndex.getZeroBased());
        String yearMonthValue = selectedEvent.getDatetime().value.substring(3, 10);
        calendarView.setCurrentYearMonth(YearMonth.parse(yearMonthValue,
                DateTimeFormatter.ofPattern("MM-yyyy")));
        calendarView.populateCalendar(calendarView.getCurrentYearMonth(), targetIndex);
    }
}
